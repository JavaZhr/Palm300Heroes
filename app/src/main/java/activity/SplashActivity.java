package activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.TextView;

import com.pgyersdk.crash.PgyCrashManager;

import cn.nicolite.palm300heroes.MainActivity;
import cn.nicolite.palm300heroes.R;
import utilty.Utilty;

/**
 * Created by NICOLITE on 2016/10/11 0011.
 */

public class SplashActivity extends Activity {
    private TextView splashCountDown;
    private MyCountDownTImer myCountDownTImer;
    private final int SPLASH_DISPLAY_TIME = 4000;
    /*Splash展示时间*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        //蒲公英Crash捕获注册
        PgyCrashManager.register(this);

        splashCountDown = (TextView) findViewById(R.id.splash_count_down_timer);

        myCountDownTImer = new MyCountDownTImer(SPLASH_DISPLAY_TIME, 1000);
        myCountDownTImer.start();

        /*初始化所有数据*/
        Utilty.initAllDate(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(intent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_TIME);
    }

    class MyCountDownTImer extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public MyCountDownTImer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            splashCountDown.setText("剩余 " + millisUntilFinished/1000);
        }

        @Override
        public void onFinish() {
            splashCountDown.setText("正在跳转");
        }
    }
}
