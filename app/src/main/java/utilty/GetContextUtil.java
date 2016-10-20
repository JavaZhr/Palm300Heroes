package utilty;

import android.app.Application;
import android.content.Context;

/**
 * Created by NICOLITE on 2016/10/8 0008.
 * 用于获取Context
 */

public class GetContextUtil extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
    }

    public static Context getContex() {
        return context;
    }
}
