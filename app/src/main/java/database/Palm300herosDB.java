package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import model.News;

/**
 * Created by NICOLITE on 2016/10/18 0018.
 */

public class Palm300herosDB {

    /**
     * 数据库名
     */

    public static final String DB_NAME = "palm300heros";

    /**
     * 数据库版本
     */

    public static int VERSION = 1;

    private static Palm300herosDB palm300herosDB;
    private SQLiteDatabase db;

    public Palm300herosDB(Context context) {
        Palm300HerosOpenHelper dbHelper = new Palm300HerosOpenHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }

    /**
     * 获取Palm300HerosDB实例
     */

    public synchronized static Palm300herosDB getInstance(Context context) {
        if (palm300herosDB == null) {
            palm300herosDB = new Palm300herosDB(context);
        }
        return palm300herosDB;
    }

    /**
     * 将News实例存储到数据库
     */

    public void saveNews(News news) {
        if (news != null) {
            ContentValues values = new ContentValues();
            values.put("news_category", news.getCategory());
            values.put("news_url", news.getUrl());
            values.put("news_title", news.getTitle());
            values.put("news_content", news.getContent());
            values.put("news_date", news.getDate());
            values.put("news_nickName", news.getNickName());
            values.put("news_imageUrl", news.getImageUrl());
            db.insert("News", null, values);
        }
    }

    /**
     * 从数据库中读取News数据
     */

    public List<News> loadNews() {
        List<News> list = new ArrayList<News>();
        Cursor cursor = db.query("News", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                News news = new News();
                news.setId(cursor.getInt(cursor.getColumnIndex("id")));
                news.setCategory(cursor.getString(cursor.getColumnIndex("news_category")));
                news.setUrl(cursor.getString(cursor.getColumnIndex("news_url")));
                news.setTitle(cursor.getString(cursor.getColumnIndex("news_title")));
                news.setContent(cursor.getString(cursor.getColumnIndex("news_content")));
                news.setDate(cursor.getString(cursor.getColumnIndex("news_date")));
                news.setNickName(cursor.getString(cursor.getColumnIndex("news_nickName")));
                news.setImageUrl(cursor.getString(cursor.getColumnIndex("news_imageUrl")));

                list.add(news);
            }while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }

        return list;
    }


    public static boolean isExistence(News news){
        if (news != null && palm300herosDB != null) {
            List<News> list = palm300herosDB.loadNews();
            for (News news1 : list) {
                if (news.getTitle().equals(news1.getTitle())) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }
}
