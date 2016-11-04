package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by NICOLITE on 2016/10/17 0017.
 */

public class Palm300HerosOpenHelper extends SQLiteOpenHelper {

    /**
     * 用于资讯功能
     */
    public static final String CREATE_NEWS = "create table News("
            + "id integer primary key autoincrement, "
            + "news_category text, "
            + "news_url text, "
            + "news_title text, "
            + "news_content text, "
            + "news_date text,"
            + "news_nickName text, "
            + "news_imageUrl text )";


    /**
     * 以下4个用于英雄功能
     */
    public static final String CREATE_HEROS = "create table Heros("
            + "id integer primary key autoincrement, "
            + "hero_name text, "
            + "hero_type text, "
            + "hero_background text, "
            + "hero_avatar_url text, "
            + "hero_coins_price text, "
            + "hero_diamond_price text )";

    public static final String CREATE_SKILL = "create table Skill("
            + "id integer primary key autoincrement, "
            + "skill_hero text, "
            + "skill_Q text, "
            + "skill_W text, "
            + "skill_E text, "
            + "skill_R text, "
            + "skill_passive text )";

    public static final String CREATE_SKIN = "create table Skin("
            + "id integer primary key autoincrement, "
            + "skin_hero text, "
            + "skin_url text )";

    public static final String CREATE_SOUND = "create table Sound("
            + "id integer primary key autoincrement, "
            + "sound_hero text, "
            + "sound_url text )";

    public Palm300HerosOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*建立资讯数据表*/
        db.execSQL(CREATE_NEWS);  //资讯
        /*建立英雄相关数据表*/
        db.execSQL(CREATE_HEROS); //英雄
        db.execSQL(CREATE_SKILL); //技能
        db.execSQL(CREATE_SKIN); //皮肤
        db.execSQL(CREATE_SOUND); //配音
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
