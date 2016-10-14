package cn.nicolite.palm300heros;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import fragment.EquipmentFragment;
import fragment.GameFragment;
import fragment.HerosFragment;
import fragment.NewsFragment;
import fragment.SettingFragment;
import util.LogUtil;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener{

    int lastSelectedPosition = 0; //底部菜单栏默认选中
    private String TAG = MainActivity.class.getSimpleName();

    /*Fragment类*/
    private NewsFragment newsFragment;
    private HerosFragment herosFragment;
    private EquipmentFragment equipmentFragment;
    private GameFragment gameFragment;
    private SettingFragment settingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.bottom_navigation_bar);
        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_home_white_24dp, "资讯"))
                .addItem(new BottomNavigationItem(R.drawable.ic_book_white_24dp, "英雄"))
                .addItem(new BottomNavigationItem(R.drawable.ic_music_note_white_24dp, "装备"))
                .addItem(new BottomNavigationItem(R.drawable.ic_game_asset_white_24dp, "游戏"))
                .addItem(new BottomNavigationItem(R.drawable.ic_tv_white_24dp,"设置"))
                .setFirstSelectedPosition(lastSelectedPosition)
                .initialise();
        bottomNavigationBar.setTabSelectedListener(this);
        setDefaultFragment();
    }

    private void setDefaultFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (newsFragment == null) {
                    /*如果NewsFragment为空，则创建一个*/
            newsFragment = new NewsFragment();
            transaction.add(R.id.content, newsFragment);
        }else {
                    /*如果不为空，则直接显示出来*/
            transaction.show(newsFragment);
        }
        transaction.commit();
    }
    @Override
    public void onTabSelected(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        /*开启一个Fragment事务*/
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        /*先隐藏所有的Fragment，防止多个Fragment显示在界面上*/
        hideFragments(transaction);
        switch (position) {
            case 0 :
                /*点击资讯*/
                LogUtil.d("BottomNavigationBar", "点击了资讯");
                if (newsFragment == null) {
                    /*如果NewsFragment为空，则创建一个*/
                    newsFragment = new NewsFragment();
                    transaction.add(R.id.content, newsFragment);
                }else {
                    /*如果不为空，则直接显示出来*/
                    transaction.show(newsFragment);
                }
                break;
            case 1 :
                /*点击英雄*/
                LogUtil.d("BottomNavigationBar", "点击了英雄");
                if (herosFragment == null) {
                    //如果HerosFragment为空，则创建一个*/
                    herosFragment = new HerosFragment();
                    transaction.add(R.id.content, herosFragment);
                }else {
                     /*如果不为空，则直接显示出来*/
                    transaction.show(herosFragment);
                }
                break;
            case 2 :
                /*点击装备*/
                LogUtil.d("BottomNavigationBar", "点击了装备");
                if (equipmentFragment == null) {
                    /*如果EquipmentFragment为空，则创建一个*/
                    equipmentFragment = new EquipmentFragment();
                    transaction.add(R.id.content, equipmentFragment);
                }else {
                    /*如果不为空，则直接显示出来*/
                    transaction.show(equipmentFragment);
                }
                break;
            case 3 :
                /*点击游戏*/
                LogUtil.d("BottomNavigationBar", "点击了游戏");
                if (gameFragment == null) {
                   /*如果GameFragment为空，则创建一个*/
                    gameFragment = new GameFragment();
                    transaction.add(R.id.content, gameFragment);
                }else {
                    /*如果不为空，则直接显示出来*/
                    transaction.show(gameFragment);
                }
                break;
            case 4 :
                /*点击设置*/
                LogUtil.d("BottomNavigationBar", "点击了设置");
                if (settingFragment == null) {
                    /*如果SettingFragment为空，则创建一个*/
                    settingFragment = new SettingFragment();
                    transaction.add(R.id.content, settingFragment);
                }else {
                    /*如果不为空，则直接显示出来*/
                    transaction.show(settingFragment);
                }
                break;
            default:break;
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
       if (newsFragment != null) {
            transaction.hide(newsFragment);
        }
        if (herosFragment != null) {
            transaction.hide(herosFragment);
        }
        if (equipmentFragment != null) {
            transaction.hide(equipmentFragment);
        }
        if (gameFragment != null) {
            transaction.hide(gameFragment);
        }
        if (settingFragment != null) {
            transaction.hide(settingFragment);
        }
    }
}
