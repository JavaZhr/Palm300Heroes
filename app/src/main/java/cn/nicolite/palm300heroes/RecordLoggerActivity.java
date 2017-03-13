package cn.nicolite.palm300heroes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
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

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import database.SearchHistoryD;
import model.recordLogger.Role;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import util.HttpUtil;
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
    @BindView(R.id.search_history_ll) LinearLayout searchListLayout;
    @BindView(R.id.clear_history_btn) Button clearHistoryBtn;

    private List<Role> dataList = new ArrayList<>();
    private List<SearchHistoryD> historyList = new ArrayList<>();
    private  ArrayAdapter<SearchHistoryD> arrayAdapter;
    private ProgressDialog progressDialog;

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
                searchListLayout.setVisibility(View.VISIBLE);
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
        String roleName = roleNameInput.getText().toString();
        String apiAddress = "http://300report.jumpw.com/api/getrole?name=" + Uri.encode(roleName);
        Intent intent;
        switch (v.getId()) {
            case R.id.record_logger_submit :
                showProgressDialog();
                SearchHistoryD searchHistoryD = new SearchHistoryD();
                searchHistoryD.setKeyword(roleName);
                searchHistoryD.saveOrUpdate();
                HttpUtil.sendOkHttpRequest(apiAddress, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                closeProgressDialog();
                                Toast.makeText(getApplicationContext(), "查询失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        dataList.clear();
                        dataList.add(Util.handleRoleResponse(response.body().string()));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                closeProgressDialog();
                                updateView();
                            }
                        });
                    }
                });
                historyList.clear();
                historyList = DataSupport.findAll(SearchHistoryD.class);
                arrayAdapter.notifyDataSetChanged();
                break;
            case R.id.record_logger_match_list :
                intent = new Intent(this, RecentMatchActivity.class);
                intent.putExtra("role_name", roleName);
                startActivity(intent);
                break;
            case R.id.record_logger_role_rank :
                intent = new Intent(this, RoleRankActivity.class);
                intent.putExtra("role_data", dataList.get(0));
                startActivity(intent);
                break;
            case R.id.clear_history_btn :
                DataSupport.deleteAll(SearchHistoryD.class);
                dataList.clear();
                arrayAdapter.notifyDataSetChanged();
                break;
            default: break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void updateView() {
        if (dataList.size() > 0 && dataList.get(0).Result.equals("OK")) {
            searchListLayout.setVisibility(View.GONE);
            Role role = dataList.get(0);
            baseDate.setVisibility(View.VISIBLE);
            updateTime.setText("更新时间：" + role.roleInfo.UpdateTime);
            roleName.setText(role.roleInfo.RoleName);
            roleLevel.setText("Lv" + role.roleInfo.RoleLevel);
            jumpValue.setText("节操值：" + role.roleInfo.JumpValue);
            winCount.setText("获胜场次：" + role.roleInfo.WinCount);
            matchCount.setText("总场次：" + role.roleInfo.MatchCount);

            double rate = (double)role.roleInfo.WinCount/(double)role.roleInfo.MatchCount;
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

    @Override
    protected void onResume() {
        super.onResume();
        historyList = DataSupport.findAll(SearchHistoryD.class);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, historyList);
        searchListView.setAdapter(arrayAdapter);
        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                roleNameInput.setText(historyList.get(position).getKeyword());
            }
        });
    }

    private void showProgressDialog(){
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("查询中...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    private void closeProgressDialog() {
        if (progressDialog != null){
            progressDialog.dismiss();
        }
    }
}
