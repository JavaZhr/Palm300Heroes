package cn.nicolite.palm300heroes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import database.DBUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import util.HttpUtil;
import util.Util;

public class SplashActivity extends BaseActivity{
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.progress_message)
    TextView progressMessage;
    private String heroAddress = "http://og0oucran.bkt.clouddn.com/hero.json";
    private String skillAddress = "http://og0oucran.bkt.clouddn.com/skill.json";
    private String skinAddress = "http://og0oucran.bkt.clouddn.com/skin.json";
    private String soundAddress = "http://og0oucran.bkt.clouddn.com/sound.json";
    private String fightSkillAddress = "http://og0oucran.bkt.clouddn.com/fight_skill.json";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String load = preferences.getString("load", null);
        if (TextUtils.isEmpty(preferences.getString("load", null))){
            updateHeroData();
        }else if (load.equals("1")){
            progressBar.setProgress(20);
            updateSkillData();
        }else if (load.equals("2")){
            progressBar.setProgress(40);
            updateSkinData();
        }else if (load.equals("3")){
            progressBar.setProgress(60);
            updateSoundData();
        }else if (load.equals("4")){
            progressBar.setProgress(80);
            updateFightSkillData();
        }else {
            startMainActivity();
        }
    }

    private void startMainActivity(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (preferences.getString("load", null).equals("5")){
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }


    private void updateHeroData(){
        progressMessage.setText("正在下载英雄数据");
        HttpUtil.sendOkHttpRequest(heroAddress, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                DBUtil.saveHero(Util.handleHeroResponse(response.body().string()));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this).edit();
                        editor.putString("load", "1");
                        editor.apply();
                        progressBar.setProgress(20);
                        updateSkillData();
                    }
                });
            }
        });
    }

    private void updateSkillData(){
        progressMessage.setText("正在下载技能数据");
        HttpUtil.sendOkHttpRequest(skillAddress, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                DBUtil.saveSkill(Util.handleSkillResponse(response.body().string()));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this).edit();
                        editor.putString("load", "2");
                        editor.apply();
                        progressBar.setProgress(40);
                        updateSkinData();
                    }
                });
            }
        });
    }

    private void updateSkinData(){
        progressMessage.setText("正在下载皮肤数据");
        HttpUtil.sendOkHttpRequest(skinAddress, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                DBUtil.saveSkin(Util.handleSkinResponse(response.body().string()));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this).edit();
                        editor.putString("load", "3");
                        editor.apply();
                        progressBar.setProgress(60);
                        updateSoundData();
                    }
                });
            }
        });
    }

    private void updateSoundData(){
        progressMessage.setText("正在下载配音数据");
        HttpUtil.sendOkHttpRequest(soundAddress, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                DBUtil.saveSound(Util.handleSoundResponse(response.body().string()));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this).edit();
                        editor.putString("load", "4");
                        editor.apply();
                        progressBar.setProgress(80);
                        updateFightSkillData();
                    }
                });
            }
        });
    }

    private void updateFightSkillData(){
        progressMessage.setText("正在下载战斗技能数据");
        HttpUtil.sendOkHttpRequest(fightSkillAddress, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                DBUtil.saveFightSkill(Util.handleFightSkillResponse(response.body().string()));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this).edit();
                        editor.putString("load", "5");
                        editor.apply();
                        progressBar.setProgress(100);
                        startMainActivity();
                    }
                });
            }
        });
    }
}
