package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by NICOLITE on 2016/10/17 0017.
 */

public class Palm300HeroesOpenHelper extends SQLiteOpenHelper {

    /**
     * 用于资讯功能
     */
    private static final String CREATE_NEWS = "create table News("
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
    private static final String CREATE_HEROES = "create table Heroes("
            + "id integer primary key autoincrement, "
            + "hero_name text, "
            + "hero_type text, "
            + "hero_background text, "
            + "hero_avatar_url text, "
            + "hero_picture_url text, "
            + "hero_coins_price text, "
            + "hero_diamond_price text,"
            + "hero_health_value text, "
            + "hero_magic_point_value text, "
            + "hero_physical_attack_value text, "
            + "hero_magic_attack_value text, "
            + "hero_physical_defense_value text, "
            + "hero_magic_defense_value text, "
            + "hero_crit_value text, "
            + "hero_attack_speed_value text, "
            + "hero_attack_range_value text, "
            + "hero_movement_speed_value text )";

    private static final String CREATE_SKILL = "create table Skill("
            + "id integer primary key autoincrement, "
            + "skill_hero text, "
            + "skill_pictureUrl text, "
            + "skill_name text, "
            + "skill_consumption text, "
            + "skill_chilldown text, "
            + "skill_shortcut text, "
            + "skill_effectiveness text )";

    private static final String CREATE_SKIN = "create table Skin("
            + "id integer primary key autoincrement, "
            + "skin_hero text, "
            + "skin_url text, "
            + "skin_name text, "
            + "skin_price text )";

    private static final String CREATE_SOUND = "create table Sound("
            + "id integer primary key autoincrement, "
            + "sound_hero text, "
            + "sound_url text, "
            + "sound_content text )";


    private static final String CREAT_ROLE = "create table Role ("
            + "id integer primary key autoincrement, "
            + "role_name text, "
            + "role_level integer, "
            + "role_id integer, "
            + "jump_value integer, "
            + "win_count integer, "
            + "match_count integer, "
            + "update_time text )";

    private static final String CREATE_ROLERANK = "create table RoleRank ("
            + "id integer primary key autoincrement, "
            + "type integer, "
            + "rank_name text, "
            + "value_name text, "
            + "rank integer, "
            + "value text, "
            + "rank_change integer, "
            + "rank_index )";

    private static final String CREATE_LATESTMATCH = "create table LatestMatch ("
            + "id integer primary key autoincrement, "
            + "match_id integer, "
            + "match_type integer, "
            + "hero_level integer, "
            + "result, integer, "
            + "match_date text, "
            + "hero_id integer, "
            + "hero_name text, "
            + "hero_icon text )";

    public Palm300HeroesOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*建立资讯数据表*/
        db.execSQL(CREATE_NEWS);  //资讯
        /*建立英雄相关数据表*/
        db.execSQL(CREATE_HEROES); //英雄
        db.execSQL(CREATE_SKILL); //技能
        db.execSQL(CREATE_SKIN); //皮肤
        db.execSQL(CREATE_SOUND); //配音

        db.execSQL(CREAT_ROLE);
        db.execSQL(CREATE_ROLERANK);
        db.execSQL(CREATE_LATESTMATCH);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
