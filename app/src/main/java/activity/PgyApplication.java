package activity;

import android.app.Application;

import com.pgyersdk.crash.PgyCrashManager;

/**
 * Created by NICOLITE on 2017/2/3 0003.
 */

public class PgyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PgyCrashManager.register(this);
    }
}
