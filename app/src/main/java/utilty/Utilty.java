package utilty;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import database.Palm300herosDB;
import model.News;

/**
 * Created by NICOLITE on 2016/10/15 0015.
 */


//暂时不使用
public class Utilty {
    /**
     * 解析和处理服务器返回的News数据
     */
    public synchronized static boolean handleNewsResponse(Palm300herosDB palm300herosDB, String response) {

        if (!TextUtils.isEmpty(response)) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");

                Log.d("handleNewsResponse", "JSON status : " + status  );
                if (status.equals("ok")) {
                    JSONObject category = jsonObject.getJSONObject("category");
                    String categoryTitle = category.getString("title");

                    JSONArray posts = jsonObject.getJSONArray("posts");

                    for (int i = 0; i < posts.length(); i++) {
                        JSONObject postItems = posts.getJSONObject(i);
                        String url = postItems.getString("url");
                        String title = postItems.getString("title");
                        String content = postItems.getString("content");
                        String date = postItems.getString("date");

                        JSONObject author = postItems.getJSONObject("author");
                        String nickName = author.getString("nickname");

                        JSONObject customFields = postItems.getJSONObject("custom_fields");
                        String imageUrl = customFields.getString("git_thumb");
                        imageUrl = imageUrl.replace("[\"", "");
                        imageUrl = imageUrl.replace("\"]", "");
                        imageUrl = imageUrl.replace("\\", "");

                        News news = new News();
                        news.setCategory(categoryTitle);
                        news.setUrl(url);
                        news.setTitle(title);
                        news.setContent(content);
                        news.setDate(date);
                        news.setNickName(nickName);
                        news.setImageUrl(imageUrl);
                        if (!palm300herosDB.isExistence(news)) {
                            palm300herosDB.saveNews(news);
                        }
                     }
                }else {

                    return false;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {

            return false;
        }
        return true;
    }

    public static Date stringToDate(String date) {
        ParsePosition position = new ParsePosition(0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateValue = simpleDateFormat.parse(date, position);
        return dateValue;
    }

    public static List<News> sortByDate(List<News> list) {
        List<News> newList = list;
        Collections.sort(newList, new Comparator<News>() {
            @Override
            public int compare(News o1, News o2) {
                Date date1 = stringToDate(o1.getDate());
                Date date2 = stringToDate(o2.getDate());
                if (date1.after(date2)) {
                    return -1;
                }
                return 1;
            }
        });
        return newList;
    }
}
