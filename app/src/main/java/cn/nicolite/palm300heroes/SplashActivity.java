package cn.nicolite.palm300heroes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.text.TextUtils;

import java.io.IOException;

import database.DBUtil;
import model.hero.Hero;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import util.HttpUtil;
import util.Util;

public class SplashActivity extends Activity {
    private ProgressDialog progressDialog;
    private String heroAddress = "http://og0oucran.bkt.clouddn.com/hero.json";
    private String skillAddress = "http://og0oucran.bkt.clouddn.com/skill.json";
    private String skinAddress = "http://og0oucran.bkt.clouddn.com/skin.json";
    private String soundAddress = "http://og0oucran.bkt.clouddn.com/sound.json";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (TextUtils.isEmpty(preferences.getString("load", null))){
            updateHeroData();
        }else if (preferences.getString("load", null).equals("1")){
            updateSkillData();
        }else if (preferences.getString("load", null).equals("2")){
            updateSkinData();
        }else if (preferences.getString("load", null).equals("3")){
            updateSoundData();
        }else {
            startMainActivity();
        }
    }

    private void startMainActivity(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (preferences.getString("load", null).equals("4")){
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void showProgressDialog(String message){
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
        }
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    private void closeProgressDialog() {
        if (progressDialog != null){
            progressDialog.dismiss();
        }
    }

    private void updateHeroData(){
        showProgressDialog("正在下载英雄数据");
        HttpUtil.sendOkHttpRequest(heroAddress, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                DBUtil.saveHero(Util.handleHeroResponse(response.body().string()), DBUtil.SAVE);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this).edit();
                        editor.putString("load", "1");
                        editor.apply();
                        closeProgressDialog();
                        updateSkillData();
                    }
                });
            }
        });
    }

    private void updateSkillData(){
        showProgressDialog("正在下载技能数据");
        HttpUtil.sendOkHttpRequest(skillAddress, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                DBUtil.saveSkill(Util.handleSkillResponse(response.body().string()), DBUtil.SAVE);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this).edit();
                        editor.putString("load", "2");
                        editor.apply();
                        closeProgressDialog();
                        updateSkinData();
                    }
                });
            }
        });
    }

    private void updateSkinData(){
        showProgressDialog("正在下载皮肤数据");
        HttpUtil.sendOkHttpRequest(skinAddress, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                DBUtil.saveSkin(Util.handleSkinResponse(response.body().string()), DBUtil.SAVE);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this).edit();
                        editor.putString("load", "3");
                        editor.apply();
                        closeProgressDialog();
                        updateSoundData();
                    }
                });
            }
        });
    }

    private void updateSoundData(){
        showProgressDialog("正在下载配音数据");
        HttpUtil.sendOkHttpRequest(soundAddress, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                DBUtil.saveSound(Util.handleSoundResponse(response.body().string()), DBUtil.SAVE);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this).edit();
                        editor.putString("load", "4");
                        editor.apply();
                        closeProgressDialog();
                        startMainActivity();
                    }
                });
            }
        });
    }
}
