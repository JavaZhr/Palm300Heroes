package activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import cn.nicolite.palm300heroes.MainActivity;
import cn.nicolite.palm300heroes.R;

/**
 * Created by NICOLITE on 2016/10/11 0011.
 */

public class SplashActivity extends Activity {
    private final int SPLASH_DISPLAY_TIME = 3000;
    /*Splash展示时间*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(intent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_TIME);
    }
}
