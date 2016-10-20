package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by NICOLITE on 2016/10/17 0017.
 */

public class Palm300HerosOpenHelper extends SQLiteOpenHelper {
    public static final String CREATE_NEWS = "create table News("
            + "id integer primary key autoincrement, "
            + "news_category text, "
            + "news_url text, "
            + "news_title text, "
            + "news_content text, "
            + "news_date text,"
            + "news_nickName text, "
            + "news_imageUrl text )";

    public Palm300HerosOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       /*建立资讯数据表*/
        db.execSQL(CREATE_NEWS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
