package activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.TextView;

import cn.nicolite.palm300heros.R;
import model.Heros;
import utilty.LogUtil;

/**
 * Created by NICOLITE on 2016/11/4 0004.
 */

public class HerosDetailActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private Heros heros;
    private TextView herosDetailName;
    private TextView herosDetailType;
    private SwipeRefreshLayout swipeRefreshLayout = null;
    private static final int REFRESH_COMPLETE_TIME = 2000;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0 :
                    swipeRefreshLayout.setRefreshing(false);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                    break;
                default:break;
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

        setContentView(R.layout.heros_detail_activity);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.heros_swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.orange));
        swipeRefreshLayout.setOnRefreshListener(this);

        heros = (Heros) getIntent().getSerializableExtra("heros_data");
        LogUtil.d("HerosDaetailActivity", heros.getName());

        herosDetailName = (TextView) findViewById(R.id.heros_detail_name);
        herosDetailType = (TextView) findViewById(R.id.heros_detail_type);
        herosDetailName.setText(heros.getName());
        herosDetailType.setText(heros.getType());
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }


    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessageDelayed(0, REFRESH_COMPLETE_TIME);
                heros = (Heros) getIntent().getSerializableExtra("heros_data");
            }
        }).start();
    }
}
