package activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
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

import adapter.FragAdapter;
import cn.nicolite.palm300heroes.R;
import fragment.Game.MatchListFragment;
import fragment.Game.ServerRankingFragment;
import myInterface.HttpCallbackListener;
import utilty.HttpUtil;
import utilty.LogUtil;
import utilty.Utilty;


/**
 * Created by NICOLITE on 2017/2/5 0005.
 */

public class RecordLoggerActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener{
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

    private TextView matchList;
    private TextView serverRanking;

    private LinearLayout baseDate;
    private LinearLayout otherDate;

    private ViewPager viewPager;

    List<Fragment> fragments = new ArrayList<>();
    private MatchListFragment matchListFragment;
    private ServerRankingFragment serverRankingFragment;

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

        matchList = (TextView) findViewById(R.id.record_logger_match_list);
        serverRanking = (TextView) findViewById(R.id.record_logger_server_ranking);

        baseDate = (LinearLayout) findViewById(R.id.record_logger_base_date);
        otherDate = (LinearLayout) findViewById(R.id.record_logger_other_date);

        viewPager = (ViewPager) findViewById(R.id.record_logger_viewpager);

        FragAdapter adapter = new FragAdapter(getSupportFragmentManager(), getFragments());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);

        matchList.setBackgroundColor(getResources().getColor(R.color.orange));

        matchList.setOnClickListener(this);
        serverRanking.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    private List<Fragment> getFragments() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        /*开启一个Fragment事务*/
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        fragments.clear();

        if (matchListFragment == null) {
            matchListFragment  = new MatchListFragment();
        }
        if (serverRankingFragment == null) {
            serverRankingFragment = new ServerRankingFragment();
        }
        transaction.commit();

        fragments.add(matchListFragment);
        fragments.add(serverRankingFragment);

        return fragments;
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
                }, 3000);
                Toast.makeText(this, "点击了查询", Toast.LENGTH_SHORT).show();

                /*if (roleNameInput.getText().toString().equals("")){

                }else {

                }*/
                break;
            case R.id.record_logger_match_list :
                resetSelectColor();
                matchList.setBackgroundColor(getResources().getColor(R.color.orange));
                viewPager.setCurrentItem(0);
                break;
            case  R.id.record_logger_server_ranking :
                resetSelectColor();
                serverRanking.setBackgroundColor(getResources().getColor(R.color.orange));
                viewPager.setCurrentItem(1);
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        resetSelectColor();
        switch (position) {
            case 0 :
                matchList.setBackgroundColor(getResources().getColor(R.color.orange));
                break;
            case 1 :
                serverRanking.setBackgroundColor(getResources().getColor(R.color.orange));
                break;
            default: break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void resetSelectColor() {
        matchList.setBackgroundColor(getResources().getColor(R.color.white));
        serverRanking.setBackgroundColor(getResources().getColor(R.color.white));
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
        }else {
            Toast.makeText(this, "找不到角色信息！", Toast.LENGTH_SHORT).show();
        }
    }
}
