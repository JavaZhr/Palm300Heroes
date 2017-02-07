package activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pgyersdk.crash.PgyCrashManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cn.nicolite.palm300heroes.R;
import database.Palm300heroesDB;
import model.recordLogger.Role;
import utilty.Utilty;


/**
 * Created by NICOLITE on 2017/2/5 0005.
 */

public class RecordLoggerActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText roleNameInput;
    private Button submit;

    private List<Role> dataList = new ArrayList<>();
    private TextView updateTime;
    private TextView roleName;
    private TextView roleLevel;
    private TextView jumpValue;
    private TextView winCount;
    private TextView matchCount;
    private TextView rate;
    private LinearLayout baseDate;

    private Button matchList;
    private Button roleRank;

    private Palm300heroesDB palm300heroesDB = Palm300heroesDB.getInstance(this);

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        //透明ActionBar
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));

        setContentView(R.layout.record_logger_layout);

        //蒲公英Crash捕获注册
        PgyCrashManager.register(this);

        roleNameInput = (EditText) findViewById(R.id.record_logger_input);
        submit = (Button) findViewById(R.id.record_logger_submit);

        updateTime = (TextView) findViewById(R.id.record_logger_update_time);
        roleName = (TextView) findViewById(R.id.record_logger_role_name);
        roleLevel = (TextView) findViewById(R.id.record_logger_role_level);
        jumpValue = (TextView) findViewById(R.id.record_logger_jump_value);
        winCount = (TextView) findViewById(R.id.record_logger_win_count);
        matchCount = (TextView) findViewById(R.id.record_logger_match_count);
        rate = (TextView) findViewById(R.id.record_logger_rate);
        baseDate = (LinearLayout) findViewById(R.id.record_logger_base_date);

        matchList = (Button) findViewById(R.id.record_logger_match_list);
        roleRank = (Button) findViewById(R.id.record_logger_role_rank);

        matchList.setOnClickListener(this);
        roleRank.setOnClickListener(this);
        submit.setOnClickListener(this);
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
                Utilty.handlerRecordLoggerResponse(this, code, 1);
                Utilty.handlerRecordLoggerResponse(this, code, 2);
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
            default: break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除蒲公英Crash捕获注册
        PgyCrashManager.unregister();
    }

    private void initView() {
        if (dataList.size() > 0) {
            Role role = dataList.get(0);
            baseDate.setVisibility(View.VISIBLE);
            updateTime.setText("更新时间：" + role.getUpdateTime());
            roleName.setText(role.getRoleName());
            roleLevel.setText("Lv" + role.getRoleLevel());
            jumpValue.setText("节操值：" + role.getJumpValue());
            winCount.setText("获胜场次：" + role.getWinCount());
            matchCount.setText("总场次：" + role.getMatchCount());

            double rate = (double)role.getWinCount()/(double)role.getMatchCount();
            BigDecimal b = new BigDecimal(rate);
            rate =   b.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();

            String rates = "胜率：" + rate * 100 + "%";
            this.rate.setText(rates);

            matchList.setVisibility(View.VISIBLE);
            roleRank.setVisibility(View.VISIBLE);

        }else {
            Toast.makeText(this, "找不到角色信息！", Toast.LENGTH_SHORT).show();
        }
    }
}
