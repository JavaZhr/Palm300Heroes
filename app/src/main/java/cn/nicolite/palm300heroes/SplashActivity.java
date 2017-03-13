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
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import util.HttpUtil;
import util.Util;

public class SplashActivity extends Activity {
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (TextUtils.isEmpty(preferences.getString("first", null))){
            showProgressDialog();
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
            editor.putString("first", "1");
            editor.apply();
            String heroAddress = "http://og0oucran.bkt.clouddn.com/hero.json";
            String skillAddress = "http://og0oucran.bkt.clouddn.com/skill.json";
            String skinAddress = "http://og0oucran.bkt.clouddn.com/skin.json";
            String soundAddress = "http://og0oucran.bkt.clouddn.com/sound.json";
            HttpUtil.sendOkHttpRequest(heroAddress, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    DBUtil.saveHero(Util.handleHeroResponse(response.body().string()), DBUtil.SAVE);
                }
            });

        }
        closeProgressDialog();
        startMainActivity();
    }

    private void startMainActivity(){
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void showProgressDialog(){
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在加载...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    private void closeProgressDialog() {
        if (progressDialog != null){
            progressDialog.dismiss();
        }
    }
}
