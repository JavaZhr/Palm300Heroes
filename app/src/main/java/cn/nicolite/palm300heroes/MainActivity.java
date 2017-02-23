package cn.nicolite.palm300heroes;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.WindowManager;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.pgyersdk.crash.PgyCrashManager;

import fragment.AboutFragment;
import fragment.GameFragment;
import fragment.HeroesFragment;
import fragment.NewsFragment;
import utilty.LogUtil;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {

    int lastSelectedPosition = 0; //底部菜单栏默认选中

    /*Fragment类*/
    private NewsFragment newsFragment;
    private HeroesFragment heroesFragment;
    private GameFragment gameFragment;
    private AboutFragment aboutFragment;
    private ActionBar actionBar;

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(true);
        setContentView(R.layout.bottom_navigation_bar);

        //蒲公英Crash捕获注册
        PgyCrashManager.register(this);

        //新版本检查
       // PgyUpdateManager.register(this);

        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar
                //.addItem(new BottomNavigationItem(R.drawable.ic_home_white_24dp, "资讯"))
                .addItem(new BottomNavigationItem(R.drawable.ic_book_white_24dp, "英雄"))
                .addItem(new BottomNavigationItem(R.drawable.ic_game_asset_white_24dp, "游戏"))
                .addItem(new BottomNavigationItem(R.drawable.ic_favorite_white_24dp, "关于"))
                .setFirstSelectedPosition(lastSelectedPosition)
                .initialise();
        bottomNavigationBar.setTabSelectedListener(this);
        hideFragments(transaction);
        setDefaultFragment();
    }
    private void setDefaultFragment() {
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        if (newsFragment == null) {
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
           /* case 0 :
                //点击资讯
                LogUtil.d("BottomNavigationBar", "点击了资讯");
                //actionBar.setTitle("资讯");
                if (newsFragment == null) {
                    //如果NewsFragment为空，则创建一个
                    newsFragment = new NewsFragment();
                    transaction.add(R.id.content, newsFragment);
                }else {
                    //如果不为空，则直接显示出来
                    transaction.show(newsFragment);
                }
                break;*/
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
            case 2 :
                /*点击关于*/
                LogUtil.d("BottomNavigationBar", "点击了关于");
                //actionBar.setTitle("关于");
                if (aboutFragment== null) {
                   /*如果AboutFragment为空，则创建一个*/
                    aboutFragment = new AboutFragment();
                    transaction.add(R.id.content, aboutFragment);
                }else {
                    /*如果不为空，则直接显示出来*/
                    transaction.show(aboutFragment);
                }
                break;
            default:break;
        }
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
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
      /* if (newsFragment != null) {
            transaction.hide(newsFragment);
        }*/
        if (heroesFragment != null) {
            transaction.hide(heroesFragment);
        }
        if (gameFragment != null) {
            transaction.hide(gameFragment);
        }
        if (aboutFragment != null) {
            transaction.hide(aboutFragment);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除蒲公英Crash捕获注册
        PgyCrashManager.unregister();
    }
}
