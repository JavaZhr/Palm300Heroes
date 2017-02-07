package myInterface;

/**
 * Created by NICOLITE on 2016/9/20 0020.
 */

public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}

