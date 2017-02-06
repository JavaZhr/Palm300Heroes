package activity;

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
import android.widget.TextView;
import android.widget.Toast;

import com.pgyersdk.crash.PgyCrashManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cn.nicolite.palm300heroes.R;
import utilty.LogUtil;
import utilty.Utilty;


/**
 * Created by NICOLITE on 2017/2/5 0005.
 */

public class RecordLoggerActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText roleNameInput;
    private Button submit;

    private List<String> dataList = new ArrayList<>();
    private TextView updatingDate;
    private TextView roleName;
    private TextView roleLevel;
    private TextView jiecao;
    private TextView totalVictoryTimes;
    private TextView totalTimes;
    private TextView winningRate;

    private Button matchList;
    private Button serverRanking;

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

        updatingDate = (TextView) findViewById(R.id.record_logger_updating_date);
        roleName = (TextView) findViewById(R.id.record_logger_role_name);
        roleLevel = (TextView) findViewById(R.id.record_logger_role_level);
        jiecao = (TextView) findViewById(R.id.record_logger_jiecao);
        totalVictoryTimes = (TextView) findViewById(R.id.record_logger_total_victory_times);
        totalTimes = (TextView) findViewById(R.id.record_logger_total_times);
        winningRate = (TextView) findViewById(R.id.record_logger_winning_rate);

        matchList = (Button) findViewById(R.id.record_logger_match_list);
        serverRanking = (Button) findViewById(R.id.record_logger_server_ranking);

        matchList.setOnClickListener(this);
        serverRanking.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onClick(View v) {
        String url = "http://300report.jumpw.com/list.html?name=" + roleNameInput.getText();
        switch (v.getId()) {
            case R.id.record_logger_submit :
                dataList = Utilty.handlerRoleDataResponse(url);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        handler.sendEmptyMessage(0);
                    }
                }, 4000);
                Toast.makeText(this, "查询中...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.record_logger_match_list :
                break;
            case R.id.record_logger_server_ranking :
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
        LogUtil.d("xxx", " 1 " + dataList.size());
        if (dataList.size() > 4) {
            updatingDate.setText(dataList.get(5));
            roleName.setText(dataList.get(0));
            roleLevel.setText(dataList.get(1));
            jiecao.setText(dataList.get(2));
            totalVictoryTimes.setText(dataList.get(3));
            totalTimes.setText(dataList.get(4));

            float i = Float.parseFloat(dataList.get(3).replace("总胜场: ", ""));
            float j = Float.parseFloat(dataList.get(4).replace("总场次: ", ""));

            double rate = i/j;
            BigDecimal b = new BigDecimal(rate);
            rate =   b.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();

            String rates = "胜率：" + rate * 100 + "%";
            winningRate.setText(rates);

            matchList.setVisibility(View.VISIBLE);
            serverRanking.setVisibility(View.VISIBLE);

        }else {
            Toast.makeText(this, "找不到角色信息！", Toast.LENGTH_SHORT).show();
        }
    }
}
