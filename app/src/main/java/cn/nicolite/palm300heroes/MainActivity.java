package cn.nicolite.palm300heroes;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapter.FragAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import fragment.Heroes.HeroesAllFragment;
import fragment.Heroes.HeroesAssassinFragment;
import fragment.Heroes.HeroesAssistFragment;
import fragment.Heroes.HeroesMasterFragment;
import fragment.Heroes.HeroesShooterFragment;
import fragment.Heroes.HeroesTankFragment;
import fragment.Heroes.HeroesWarriorFragment;
import model.UpdateTime;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import util.HttpUtil;
import util.Util;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.main_viewpager)
    ViewPager viewPager;
    @BindView(R.id.main_tab)
    TabLayout tabLayout;
    @BindView(R.id.main_toolbar)
    Toolbar toolbar;
    @BindView(R.id.main_drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.main_navigation_view)
    NavigationView navigationView;
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> heroTypeList = new ArrayList<>();
    private HeroesAllFragment heroesAllFragment;
    private HeroesAssassinFragment heroesAssassinFragment;
    private HeroesWarriorFragment heroesWarriorFragment;
    private HeroesTankFragment heroesTankFragment;
    private HeroesShooterFragment heroesShooterFragment;
    private HeroesMasterFragment heroesMasterFragment;
    private HeroesAssistFragment heroesAssistFragment;
    @BindView(R.id.main_coordinator_layout)
    CoordinatorLayout coordinatorLayout;
    private boolean status = true; //back key status mark
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        viewPager.setAdapter(new FragAdapter(getSupportFragmentManager(), getFragmentList(), getHeroTypeList()));
        tabLayout.setupWithViewPager(viewPager);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("英雄");
        }
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PgyUpdateManager.unregister();
    }

    private List<Fragment> getFragmentList() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        /*开启一个Fragment事务*/
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        fragmentList.clear();
        if (heroesAllFragment == null) {
            heroesAllFragment = new HeroesAllFragment();
        }
        if (heroesAssassinFragment == null) {
            heroesAssassinFragment = new HeroesAssassinFragment();
        }
        if (heroesWarriorFragment == null) {
            heroesWarriorFragment = new HeroesWarriorFragment();
        }
        if (heroesTankFragment == null) {
            heroesTankFragment = new HeroesTankFragment();
        }
        if (heroesShooterFragment == null) {
            heroesShooterFragment = new HeroesShooterFragment();
        }
        if (heroesMasterFragment == null) {
            heroesMasterFragment = new HeroesMasterFragment();
        }
        if (heroesAssistFragment == null) {
            heroesAssistFragment = new HeroesAssistFragment();
        }
        transaction.commit();

        fragmentList.add(heroesAllFragment);
        fragmentList.add(heroesAssassinFragment);
        fragmentList.add(heroesWarriorFragment);
        fragmentList.add(heroesTankFragment);
        fragmentList.add(heroesShooterFragment);
        fragmentList.add(heroesMasterFragment);
        fragmentList.add(heroesAssistFragment);

        return fragmentList;
    }

    private List<String> getHeroTypeList(){
        heroTypeList.clear();
        heroTypeList.add("全部");
        heroTypeList.add("刺客");
        heroTypeList.add("战士");
        heroTypeList.add("坦克");
        heroTypeList.add("射手");
        heroTypeList.add("法师");
        heroTypeList.add("辅助");
        return heroTypeList;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.fightSkill:
                Intent fightSkillActivity = new Intent(this, FightSkillActivity.class);
                startActivity(fightSkillActivity);
                break;
            case R.id.recordLogger:
                Intent recordLogger = new Intent(this, RecordLoggerActivity.class);
                startActivity(recordLogger);
                break;
            case R.id.updateData:
                dataUpdateDetecting();
                break;
            case R.id.update:
                updateApp();
                break;
            case R.id.about:
                Intent aboutActivity = new Intent(this, AboutActivity.class);
                startActivity(aboutActivity);
                break;
        }
        drawerLayout.closeDrawers();
        return true;
    }

    private void updateApp(){
        PgyUpdateManager.register(MainActivity.this, "应用更新", new UpdateManagerListener() {
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
                        Snackbar.make(coordinatorLayout, "已经是最新版本", Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
            if (status){
                Snackbar.make(coordinatorLayout, "再按一次退出", Snackbar.LENGTH_SHORT).show();
                status = false;
            }else {
                System.exit(0);
            }
        }
        return true;
    }

    public void dataUpdateDetecting(){
        String apiUpdateTime = "http://og0oucran.bkt.clouddn.com/api_update_time.json";
        HttpUtil.sendOkHttpRequest(apiUpdateTime, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                UpdateTime updateTime = Util.handleUpdateTimeResponse(response.body().string());
                if (updateTime != null && updateTime.status.equals("ok")){
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    String updateTimes = preferences.getString("times", null);
                    if (!TextUtils.isEmpty(updateTimes) && !updateTimes.equals(updateTime.times)){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new AlertDialog.Builder(MainActivity.this)
                                        .setMessage("更新数据需要重启应用")
                                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
                                                editor.putString("load","");
                                                editor.apply();
                                                Intent splashActivity = new Intent(getApplicationContext(), SplashActivity.class);
                                                startActivity(splashActivity);
                                                finish();
                                            }
                                        })
                                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        })
                                        .show();
                            }
                        });
                    }else {
                        Snackbar.make(coordinatorLayout, "已经是最新数据", Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
