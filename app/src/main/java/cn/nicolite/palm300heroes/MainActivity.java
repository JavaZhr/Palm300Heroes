package cn.nicolite.palm300heroes;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.pgyersdk.feedback.PgyFeedback;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fragment.GameFragment;
import fragment.HeroesFragment;
import util.LogUtil;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {
    @BindView(R.id.main_drawer_layout)
    DrawerLayout drawerLayout;
    int lastSelectedPosition = 0; //底部菜单栏默认选中
    /*Fragment类*/
    private HeroesFragment heroesFragment;
    private GameFragment gameFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;
    @BindView(R.id.drawer_list_view)
    ListView listView;
    private List<String> dataList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(true);
        setContentView(R.layout.bottom_navigation_bar);
        ButterKnife.bind(this);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar
                //.addItem(new BottomNavigationItem(R.drawable.ic_home_white_24dp, "资讯"))
                .addItem(new BottomNavigationItem(R.drawable.ic_book_white_24dp, "英雄"))
                .addItem(new BottomNavigationItem(R.drawable.ic_game_asset_white_24dp, "游戏"))
                .setFirstSelectedPosition(lastSelectedPosition)
                .initialise();
        bottomNavigationBar.setTabSelectedListener(this);
        hideFragments(transaction);
        setDefaultFragment();
        dataList.add("问题反馈");
        dataList.add("检查更新");
        dataList.add("关于");
        }
    private void setDefaultFragment() {
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        if (heroesFragment == null) {
                    /*如果NewsFragment为空，则创建一个*/
            heroesFragment = new HeroesFragment();
            //actionBar.setTitle("资讯");
            transaction.add(R.id.content, heroesFragment);
        }else {
                    /*如果不为空，则直接显示出来*/
            transaction.show(heroesFragment);
        }
        transaction.commit();
    }
    @Override
    public void onTabSelected(int position) {
        fragmentManager = getSupportFragmentManager();

        /*开启一个Fragment事务*/
        transaction = fragmentManager.beginTransaction();
        /*先隐藏所有的Fragment，防止多个Fragment显示在界面上*/
        hideFragments(transaction);
        switch (position) {
            case 0 :
                /*点击英雄*/
                LogUtil.d("BottomNavigationBar", "点击了英雄");
               // actionBar.setTitle("英雄");
                if (heroesFragment == null) {
                    //如果HeroesFragment为空，则创建一个*/
                    heroesFragment = new HeroesFragment();
                    transaction.add(R.id.content, heroesFragment);
                }else {
                     /*如果不为空，则直接显示出来*/
                    transaction.show(heroesFragment);
                }
                break;
            case 1 :
                /*点击游戏*/
                LogUtil.d("BottomNavigationBar", "点击了游戏");
                //actionBar.setTitle("游戏");
                if (gameFragment == null) {
                   /*如果GameFragment为空，则创建一个*/
                    gameFragment = new GameFragment();
                    transaction.add(R.id.content, gameFragment);
                }else {
                    /*如果不为空，则直接显示出来*/
                    transaction.show(gameFragment);
                }
                break;
        }
        transaction.commit();
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    /**
     * 将所有的Fragment都设置隐藏状态
     * @param transaction
     * 用于对Fragment执行事务操作
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (heroesFragment != null) {
            transaction.hide(heroesFragment);
        }
        if (gameFragment != null) {
            transaction.hide(gameFragment);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)){
                drawerLayout.closeDrawers();
            }else {
                moveTaskToBack(false);
            }
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                switch (dataList.get(position)){
                    case "问题反馈":
                        PgyFeedback.getInstance().showDialog(MainActivity.this);
                        break;
                    case "检查更新":
                        updateApp();
                        break;
                    case "关于":
                        intent = new Intent(getApplicationContext(), AboutActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    private void updateApp(){
        PgyUpdateManager.register(MainActivity.this, "您自定义provider file值", new UpdateManagerListener() {
            @Override
            public void onUpdateAvailable(final String result) {
                // 将新版本信息封装到AppBean中
                final AppBean appBean = getAppBeanFromString(result);
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("更新 "+ appBean.getVersionName())
                        .setMessage(appBean.getReleaseNote())
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startDownloadTask(MainActivity.this, appBean.getDownloadURL());
                            }
                        })
                        .setNegativeButton(
                                "取消",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                        .show();
            }

            @Override
            public void onNoUpdateAvailable() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "已经是最新版", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PgyUpdateManager.unregister();
    }
}
