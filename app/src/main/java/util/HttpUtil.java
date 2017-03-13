package util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by NICOLITE on 2016/9/20 0020.
 */

public class HttpUtil {

    public static void sendOkHttpRequest (final String address, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
