package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import model.Heroes;
import model.News;
import model.Skill;
import model.Skin;
import model.Sound;
import utilty.LogUtil;

/**
 * Created by NICOLITE on 2016/10/18 0018.
 */

public class Palm300heroesDB {

    /**
     * 数据库名
     */

    public static final String DB_NAME = "palm300heroes";

    /**
     * 数据库版本
     */

    public static int VERSION = 1;

    private static Palm300heroesDB palm300heroesDB;
    private static SQLiteDatabase db;

    public Palm300heroesDB(Context context) {
        Palm300HeroesOpenHelper dbHelper = new Palm300HeroesOpenHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }

    /**
     * 获取Palm300HeroesDB实例
     */

    public synchronized static Palm300heroesDB getInstance(Context context) {
        if (palm300heroesDB == null) {
            palm300heroesDB = new Palm300heroesDB(context);
        }
        return palm300heroesDB;
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


    public void updateNews(News news) {
        if (news != null) {
            ContentValues values = new ContentValues();
            values.put("news_category", news.getCategory());
            values.put("news_url", news.getUrl());
            values.put("news_title", news.getTitle());
            values.put("news_content", news.getContent());
            values.put("news_date", news.getDate());
            values.put("news_nickName", news.getNickName());
            values.put("news_imageUrl", news.getImageUrl());
            db.update("News", values, "news_title=?", new String[]{news.getTitle()});
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


    public void saveHeroes(Heroes heroes) {
        if (heroes != null) {
            ContentValues values = new ContentValues();
            values.put("hero_name", heroes.getName());
            values.put("hero_type", heroes.getType());
            values.put("hero_background", heroes.getBackground());
            values.put("hero_avatar_url", heroes.getAvatarUrl());
            values.put("hero_picture_url", heroes.getPictureUrl());
            values.put("hero_coins_price", heroes.getCoinsPrice());
            values.put("hero_diamond_price", heroes.getDiamondPrice());

            /*英雄基础数据*/
            values.put("hero_health_value", heroes.getHealthValue());
            values.put("hero_magic_point_value", heroes.getMagicPointValue());
            values.put("hero_physical_attack_value", heroes.getPhysicalAttackValue());
            values.put("hero_magic_attack_value", heroes.getMagicAttackValue());
            values.put("hero_physical_defense_value", heroes.getPhysicalDefenseValue());
            values.put("hero_magic_defense_value", heroes.getMagicDefenseValue());
            values.put("hero_crit_value", heroes.getCritValue());
            values.put("hero_attack_speed_value", heroes.getAttackSpeedValue());
            values.put("hero_attack_range_value", heroes.getAttackRangeValue());
            values.put("hero_movement_speed_value", heroes.getMovementSpeedValue());

            db.insert("Heroes", null, values);
        }else {
            LogUtil.d("saveHeroes", "heroes : null");
        }
    }

    public void updateHeroes(Heroes heroes) {
        if (heroes != null) {
            ContentValues values = new ContentValues();
            values.put("hero_name", heroes.getName());
            values.put("hero_type", heroes.getType());
            values.put("hero_background", heroes.getBackground());
            values.put("hero_avatar_url", heroes.getAvatarUrl());
            values.put("hero_picture_url", heroes.getPictureUrl());
            values.put("hero_coins_price", heroes.getCoinsPrice());
            values.put("hero_diamond_price", heroes.getDiamondPrice());

            /*英雄基础数据*/
            values.put("hero_health_value", heroes.getHealthValue());
            values.put("hero_magic_point_value", heroes.getMagicPointValue());
            values.put("hero_physical_attack_value", heroes.getPhysicalAttackValue());
            values.put("hero_magic_attack_value", heroes.getMagicAttackValue());
            values.put("hero_physical_defense_value", heroes.getPhysicalDefenseValue());
            values.put("hero_magic_defense_value", heroes.getMagicDefenseValue());
            values.put("hero_crit_value", heroes.getCritValue());
            values.put("hero_attack_speed_value", heroes.getAttackSpeedValue());
            values.put("hero_attack_range_value", heroes.getAttackRangeValue());
            values.put("hero_movement_speed_value", heroes.getMovementSpeedValue());

            db.update("Heroes", values, "hero_name=?", new String[]{heroes.getName()});
        }else {
            LogUtil.d("saveHeroes", "heroes : null");
        }
    }

    public List<Heroes> loadHeroes() {
        List<Heroes> list = new ArrayList<Heroes>();
        Cursor cursor = db.query("Heroes", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Heroes heroes = new Heroes();
                heroes.setId(cursor.getInt(cursor.getColumnIndex("id")));
                heroes.setName(cursor.getString(cursor.getColumnIndex("hero_name")));
                heroes.setType(cursor.getString(cursor.getColumnIndex("hero_type")));
                heroes.setBackground(cursor.getString(cursor.getColumnIndex("hero_background")));
                heroes.setAvatarUrl(cursor.getString(cursor.getColumnIndex("hero_avatar_url")));
                heroes.setPictureUrl(cursor.getString(cursor.getColumnIndex("hero_picture_url")));
                heroes.setCoinsPrice(cursor.getString(cursor.getColumnIndex("hero_coins_price")));
                heroes.setDiamondPrice(cursor.getString(cursor.getColumnIndex("hero_diamond_price")));

                /*英雄基础数据*/
                heroes.setHealthValue(cursor.getString(cursor.getColumnIndex("hero_health_value")));
                heroes.setMagicPointValue(cursor.getString(cursor.getColumnIndex("hero_magic_point_value")));
                heroes.setPhysicalAttackValue(cursor.getString(cursor.getColumnIndex("hero_physical_attack_value")));
                heroes.setMagicAttackValue(cursor.getString(cursor.getColumnIndex("hero_magic_attack_value")));
                heroes.setPhysicalDefenseValue(cursor.getString(cursor.getColumnIndex("hero_physical_defense_value")));
                heroes.setMagicDefenseValue(cursor.getString(cursor.getColumnIndex("hero_magic_defense_value")));
                heroes.setCritValue(cursor.getString(cursor.getColumnIndex("hero_crit_value")));
                heroes.setAttackSpeedValue(cursor.getString(cursor.getColumnIndex("hero_attack_speed_value")));
                heroes.setAttackRangeValue(cursor.getString(cursor.getColumnIndex("hero_attack_range_value")));
                heroes.setMovementSpeedValue(cursor.getString(cursor.getColumnIndex("hero_movement_speed_value")));

                list.add(heroes);
            }while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }

    public static List<Heroes> getHeroesTypeDate(String heroesType){
        List<Heroes> list = new ArrayList<>();
        Cursor cursor = db.query("Heroes", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Heroes heroes = new Heroes();
                heroes.setId(cursor.getInt(cursor.getColumnIndex("id")));
                heroes.setName(cursor.getString(cursor.getColumnIndex("hero_name")));
                heroes.setType(cursor.getString(cursor.getColumnIndex("hero_type")));
                heroes.setBackground(cursor.getString(cursor.getColumnIndex("hero_background")));
                heroes.setAvatarUrl(cursor.getString(cursor.getColumnIndex("hero_avatar_url")));
                heroes.setPictureUrl(cursor.getString(cursor.getColumnIndex("hero_picture_url")));
                heroes.setCoinsPrice(cursor.getString(cursor.getColumnIndex("hero_coins_price")));
                heroes.setDiamondPrice(cursor.getString(cursor.getColumnIndex("hero_diamond_price")));

                /*英雄基础数据*/
                heroes.setHealthValue(cursor.getString(cursor.getColumnIndex("hero_health_value")));
                heroes.setMagicPointValue(cursor.getString(cursor.getColumnIndex("hero_magic_point_value")));
                heroes.setPhysicalAttackValue(cursor.getString(cursor.getColumnIndex("hero_physical_attack_value")));
                heroes.setMagicAttackValue(cursor.getString(cursor.getColumnIndex("hero_magic_attack_value")));
                heroes.setPhysicalDefenseValue(cursor.getString(cursor.getColumnIndex("hero_physical_defense_value")));
                heroes.setMagicDefenseValue(cursor.getString(cursor.getColumnIndex("hero_magic_defense_value")));
                heroes.setCritValue(cursor.getString(cursor.getColumnIndex("hero_crit_value")));
                heroes.setAttackSpeedValue(cursor.getString(cursor.getColumnIndex("hero_attack_speed_value")));
                heroes.setAttackRangeValue(cursor.getString(cursor.getColumnIndex("hero_attack_range_value")));
                heroes.setMovementSpeedValue(cursor.getString(cursor.getColumnIndex("hero_movement_speed_value")));

                if (heroes.getType().contains(heroesType)) {
                    list.add(heroes);
                }
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
            values.put("skill_name", skill.getName());
            values.put("skill_operation", skill.getOperation());
            values.put("skill_describe", skill.getDescribe());
            values.put("skill_pictureUrl", skill.getPictureUrl());
            db.insert("Skill", null, values);
        }else {
            LogUtil.d("saveSkill", "skill : null");
        }
    }

    public void updateSkill(Skill skill) {
        if (skill != null) {
            ContentValues values = new ContentValues();
            values.put("skill_hero", skill.getHero());
            values.put("skill_name", skill.getName());
            values.put("skill_operation", skill.getOperation());
            values.put("skill_describe", skill.getDescribe());
            values.put("skill_pictureUrl", skill.getPictureUrl());
            db.update("Skill", values, "skill_name=?", new String[]{skill.getName()});
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
                skill.setName(cursor.getString(cursor.getColumnIndex("skill_name")));
                skill.setOperation(cursor.getString(cursor.getColumnIndex("skill_operation")));
                skill.setDescribe(cursor.getString(cursor.getColumnIndex("skill_describe")));
                skill.setPictureUrl(cursor.getString(cursor.getColumnIndex("skill_pictureUrl")));
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
            values.put("skin_name", skin.getName());
            values.put("skin_price", skin.getPrice());
            db.insert("Skin", null, values);
        }else {
            LogUtil.d("saveSkin", "skin : null");
        }
    }

    public void updateSkin(Skin skin) {
        if (skin != null) {
            ContentValues values = new ContentValues();
            values.put("skin_hero", skin.getHero());
            values.put("skin_url", skin.getUrl());
            values.put("skin_name", skin.getName());
            values.put("skin_price", skin.getPrice());
            db.update("Skin", values, "skin_name=? and skin_hero=?", new String[]{skin.getName(),skin.getHero()});
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
                skin.setName(cursor.getString(cursor.getColumnIndex("skin_name")));
                skin.setPrice(cursor.getString(cursor.getColumnIndex("skin_price")));
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
            values.put("sound_content", sound.getContent());
            db.insert("Sound", null, values);
        }else {
            LogUtil.d("saveSound", "skin : null");
        }
    }

    public void updateSound(Sound sound) {
        if (sound != null) {
            ContentValues values = new ContentValues();
            values.put("sound_hero", sound.getHero());
            values.put("sound_url", sound.getUrl());
            values.put("sound_content", sound.getContent());
            db.update("Sound", values, "sound_content=?", new String[]{sound.getContent()});
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
                sound.setContent(cursor.getString(cursor.getColumnIndex("sound_content")));
                list.add(sound);
            }while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }

    public boolean isExistence(Object object){
        if (object instanceof News) {
            News news = (News) object;
            if (news != null && palm300heroesDB != null) {
                List<News> list = palm300heroesDB.loadNews();
                for (News news1 : list) {
                    if (news.getTitle().equals(news1.getTitle())) {
                        if (!news.equals(news1)){
                            updateNews(news);
                        }
                        return true;
                    }
                }
                return false;
            }
        }

        if (object instanceof Heroes) {
            Heroes heroes = (Heroes) object;
            if (palm300heroesDB != null) {
                List<Heroes> list = palm300heroesDB.loadHeroes();
                for (Heroes heroes1 : list) {
                    if (heroes.getName().equals(heroes1.getName())) {
                        if (!heroes.equals(heroes1)) {
                            //如果有部分数据被改变，更新数据库相应的行
                            updateHeroes(heroes);
                        }
                        return true;
                    }
                }
                return false;
            }
        }

        if (object instanceof Skill) {
            Skill skill = (Skill) object;
            if (palm300heroesDB != null) {
                List<Skill> list = palm300heroesDB.loadSkill();

                for (Skill skill2 : list) {
                    if (skill.getName().equals(skill2.getName())) {
                        if (!skill.equals(skill2)) {
                            updateSkill(skill);
                        }
                        return true;
                    }
                }
                return false;
            }
        }

        if (object instanceof  Skin) {
            Skin skin = (Skin) object;
            if (palm300heroesDB != null) {
                List<Skin> list = palm300heroesDB.loadSkin();

                for (Skin skin1 : list) {
                    if (skin.getName().equals(skin1.getName()) && skin.getHero().equals(skin1.getHero())) {
                        if (!skin.equals(skin1)) {
                            updateSkin(skin);
                        }
                        return true;
                    }
                }
                return false;
            }
        }

        if (object instanceof  Sound) {
            Sound sound = (Sound) object;
            if (palm300heroesDB != null){
                List<Sound> list = palm300heroesDB.loadSound();
                for (Sound sound1 : list) {
                    if (sound.getContent().equals(sound1.getContent())) {
                        if (!sound.equals(sound1)) {
                            updateSound(sound);
                        }
                    return true;
                }
        }
        return false;
    }
}
        return false;
    }
}
