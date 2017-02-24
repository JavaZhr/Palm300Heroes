package activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.nicolite.palm300heroes.R;
import database.Palm300heroesDB;
import model.recordLogger.Role;
import util.Util;


/**
 * Created by NICOLITE on 2017/2/5 0005.
 */

public class RecordLoggerActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.record_logger_input) EditText roleNameInput;
    @BindView(R.id.record_logger_submit) Button submit;

    @BindView(R.id.record_logger_update_time) TextView updateTime;
    @BindView(R.id.record_logger_role_name) TextView roleName;
    @BindView(R.id.record_logger_role_level) TextView roleLevel;
    @BindView(R.id.record_logger_jump_value) TextView jumpValue;
    @BindView(R.id.record_logger_win_count) TextView winCount;
    @BindView(R.id.record_logger_match_count) TextView matchCount;
    @BindView(R.id.record_logger_rate) TextView rate;
    @BindView(R.id.record_logger_base_date) LinearLayout baseDate;

    @BindView(R.id.record_logger_match_list) Button matchList;
    @BindView(R.id.record_logger_role_rank) Button roleRank;

    @BindView(R.id.search_history_lv) ListView searchListView;
    @BindView(R.id.search_history_ll) LinearLayout serchListLayout;
    @BindView(R.id.clear_history_btn) Button clearHistoryBtn;

    private List<Role> dataList;
    private Palm300heroesDB palm300heroesDB = Palm300heroesDB.getInstance(this);
    private List<String> historyList = palm300heroesDB.loadRecentSearch();
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0 :
                    initView();
                    break;
                default: break;
            }
        }
    };

    private  ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);

            actionBar.setDisplayHomeAsUpEnabled(true);

            actionBar.setDisplayShowHomeEnabled(true);

            actionBar.setDisplayShowTitleEnabled(false);
        }
        //透明ActionBar
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));

        setContentView(R.layout.record_logger_layout);

        ButterKnife.bind(this);

        matchList.setOnClickListener(this);
        roleRank.setOnClickListener(this);
        submit.setOnClickListener(this);
        clearHistoryBtn.setOnClickListener(this);

        roleNameInput.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                baseDate.setVisibility(View.GONE);
                matchList.setVisibility(View.GONE);
                roleRank.setVisibility(View.GONE);
                serchListLayout.setVisibility(View.VISIBLE);
                return false;
    }
});

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onClick(View v) {
        String code = roleNameInput.getText().toString();
        Intent intent;
        switch (v.getId()) {
            case R.id.record_logger_submit :
                Util.handlerRecordLoggerResponse(this, code, 1);
                Util.handlerRecordLoggerResponse(this, code, 2);
                if (!palm300heroesDB.isExistence(code)) {
                    palm300heroesDB.saveRecentSearch(code);
                    initData();
                    arrayAdapter.notifyDataSetChanged();
                }
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dataList = palm300heroesDB.loadRole();
                        handler.sendEmptyMessage(0);
                    }
                }, 3000);
                Toast.makeText(this, "查询中...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.record_logger_match_list :
                intent = new Intent(this, LatestMatchActivity.class);
                startActivity(intent);
                break;
            case R.id.record_logger_role_rank :
                intent = new Intent(this, RoleRankActivity.class);
                startActivity(intent);
                break;
            case R.id.clear_history_btn :
                palm300heroesDB.deleteRecentSearch(null, null);
                initData();
                arrayAdapter.notifyDataSetChanged();
                break;
            default: break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initView() {
        if (dataList.size() > 0) {
            serchListLayout.setVisibility(View.GONE);
            Role role = dataList.get(0);
            baseDate.setVisibility(View.VISIBLE);
            updateTime.setText("更新时间：" + role.getUpdateTime());
            roleName.setText(role.getRoleName());
            roleLevel.setText("Lv" + role.getRoleLevel());
            jumpValue.setText("节操值：" + role.getJumpValue());
            winCount.setText("获胜场次：" + role.getWinCount());
            matchCount.setText("总场次：" + role.getMatchCount());

            double rate = (double)role.getWinCount()/(double)role.getMatchCount();
            /*BigDecimal b = new BigDecimal(rate);
            rate =   b.setScale(4, RoundingMode.UP).doubleValue();*/

            rate = (double)Math.round(rate * 10000) / 100;
            String rates = "胜率：" + /*String.format("%.2f", rate * 100)*/ rate  + "%";
            this.rate.setText(rates);

            matchList.setVisibility(View.VISIBLE);
            roleRank.setVisibility(View.VISIBLE);

        }else {
            Toast.makeText(this, "找不到角色信息！", Toast.LENGTH_SHORT).show();
        }
    }

    private void initData() {
        historyList.clear();
        historyList.addAll(palm300heroesDB.loadRecentSearch());
    }

    @Override
    protected void onResume() {
        super.onResume();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, historyList);
        searchListView.setAdapter(arrayAdapter);
        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                roleNameInput.setText(historyList.get(position));
            }
        });
    }
}
