package util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by NICOLITE on 2016/10/15 0015.
 */

public class Util {
    /**
     * 将服务器的JSON数据解析出来
     * @param response
     */
    public static void handleNewsResponse(Context context, String response) {
        List<String> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject category = jsonObject.getJSONObject("category");
            String categoryTitle = category.getString("title");

            LogUtil.d("handleNewsResponse", categoryTitle);
            JSONArray posts = jsonObject.getJSONArray("posts");

            for (int i = 0; i < posts.length(); i++) {
                JSONObject postItems = posts.getJSONObject(i);
                String url = postItems.getString("url");
                String title = postItems.getString("title");
                String content = postItems.getString("content");
                String date = postItems.getString("date");
                list.addAll(Arrays.asList(url, title, content, date));
                saveNewsInformation(context, categoryTitle, list);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void saveNewsInformation(Context context, String categoryTitle, List<String> postsList){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString("category", categoryTitle);
        editor.putString("url", postsList.get(0));
        editor.putString("title", postsList.get(1));
        editor.putString("content", postsList.get(2));
        editor.putString("date", postsList.get(3));
        editor.commit();
    }
}
