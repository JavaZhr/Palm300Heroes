package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import model.Heros;
import model.News;
import model.Skill;
import model.Skin;
import model.Sound;
import utilty.LogUtil;

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
        }else {
            LogUtil.d("saveNews", "news : null");
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


    public void saveHeros(Heros heros) {
        if (heros != null) {
            ContentValues values = new ContentValues();
            values.put("hero_name", heros.getName());
            values.put("hero_type", heros.getType());
            values.put("hero_background", heros.getBackground());
            values.put("hero_data", heros.getData());
            db.insert("Heros", null, values);
        }else {
            LogUtil.d("saveHeros", "heros : null");
        }
    }


    public List<Heros> loadHeros() {
        List<Heros> list = new ArrayList<Heros>();
        Cursor cursor = db.query("Heros", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Heros heros = new Heros();
                heros.setId(cursor.getInt(cursor.getColumnIndex("id")));
                heros.setName(cursor.getString(cursor.getColumnIndex("hero_name")));
                heros.setType(cursor.getString(cursor.getColumnIndex("hero_type")));
                heros.setBackground(cursor.getString(cursor.getColumnIndex("hero_background")));
                heros.setData(cursor.getString(cursor.getColumnIndex("hero_data")));
                list.add(heros);
            }while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }

    public void saveSkill(Skill skill) {
        if (skill != null) {
            ContentValues values = new ContentValues();
            values.put("skill_hero", skill.getHero());
            values.put("skill_Q", skill.getQ());
            values.put("skill_W", skill.getW());
            values.put("skill_E", skill.getE());
            values.put("skill_R", skill.getR());
            values.put("skill_passive", skill.getPassive());

            db.insert("Skill", null, values);
        }else {
            LogUtil.d("saveSkill", "skill : null");
        }
    }

    public List<Skill> loadSkill(){
        List<Skill> list = new ArrayList<Skill>();
        Cursor cursor = db.query("Skill", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Skill skill = new Skill();
                skill.setId(cursor.getInt(cursor.getColumnIndex("id")));
                skill.setHero(cursor.getString(cursor.getColumnIndex("skill_hero")));
                skill.setQ(cursor.getString(cursor.getColumnIndex("skill_Q")));
                skill.setW(cursor.getString(cursor.getColumnIndex("skill_W")));
                skill.setE(cursor.getString(cursor.getColumnIndex("skill_E")));
                skill.setR(cursor.getString(cursor.getColumnIndex("skill_R")));
                skill.setPassive(cursor.getString(cursor.getColumnIndex("skill_passive")));

                list.add(skill);
            }while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }


    public void saveSkin(Skin skin) {
        if (skin != null) {
            ContentValues values = new ContentValues();
            values.put("skin_hero", skin.getHero());
            values.put("skin_url", skin.getUrl());
            db.insert("Skin", null, values);
        }else {
            LogUtil.d("saveSkin", "skin : null");
        }
    }

    public List<Skin> loadSkin(){
        List<Skin> list = new ArrayList<Skin>();
        Cursor cursor = db.query("Skin", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Skin skin = new Skin();
                skin.setId(cursor.getInt(cursor.getColumnIndex("id")));
                skin.setHero(cursor.getString(cursor.getColumnIndex("skin_hero")));
                skin.setUrl(cursor.getString(cursor.getColumnIndex("skin_url")));
                list.add(skin);
            }while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }


    public void saveSound(Sound sound) {
        if (sound != null) {
            ContentValues values = new ContentValues();
            values.put("sound_hero", sound.getHero());
            values.put("sound_url", sound.getUrl());
            db.insert("Sound", null, values);
        }else {
            LogUtil.d("saveSound", "skin : null");
        }
    }

    public List<Sound> loadSound(){
        List<Sound> list = new ArrayList<Sound>();
        Cursor cursor = db.query("Sound", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Sound sound = new Sound();
                sound.setId(cursor.getInt(cursor.getColumnIndex("id")));
                sound.setHero(cursor.getString(cursor.getColumnIndex("sound_hero")));
                sound.setUrl(cursor.getString(cursor.getColumnIndex("sound_url")));
                list.add(sound);
            }while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }
}
