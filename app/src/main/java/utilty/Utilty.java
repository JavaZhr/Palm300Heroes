package utilty;

import android.content.Context;
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

import database.Palm300heroesDB;
import model.Heroes;
import model.News;
import model.Skill;
import model.Skin;
import model.Sound;
import myInterface.HttpCallbackListener;

/**
 * Created by NICOLITE on 2016/10/15 0015.
 */


public class Utilty {
    private static Palm300heroesDB palm300heroesDB;
    /**
     * 解析和处理服务器返回的News数据
     */

    /*资讯信息部分*/
    public synchronized static boolean handleNewsResponse(Palm300heroesDB palm300heroesDB, String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");

                Log.d("handleNewsResponse", "News status : " + status  );
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
                        if (!palm300heroesDB.isExistence(news)) {
                            palm300heroesDB.saveNews(news);
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

    /*英雄信息部分*/
    public synchronized static boolean handleHeroesResponse(Palm300heroesDB palm300heroesDB, String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                LogUtil.d("handleHeroesResponse", "Hero status : " + status);

                JSONArray info = jsonObject.getJSONArray("info");

                for (int i = 0; i < info.length(); i++) {

                    JSONObject heroInfo = info.getJSONObject(i);

                    String heroName = heroInfo.getString("heroName");
                    String heroType = heroInfo.getString("heroType");
                    String background = heroInfo.getString("background");
                    String avatarUrl = heroInfo.getString("avatarUrl");
                    String coinsPrice = heroInfo.getString("coinsPrice");
                    String diamondPrice = heroInfo.getString("diamondPrice");
                    String pictureUrl = heroInfo.getString("pictureUrl");

                    JSONObject baseData = heroInfo.getJSONObject("baseData");
                    String healthValue = baseData.getString("healthValue");
                    String magicPointValue = baseData.getString("magicPointValue");
                    String physicalAttackValue = baseData.getString("physicalAttackValue");
                    String magicAttackValue = baseData.getString("magicAttackValue");
                    String physicalDefenseValue = baseData.getString("physicalDefenseValue");
                    String magicDefenseValue = baseData.getString("magicDefenseValue");
                    String critValue = baseData.getString("critValue");
                    String attackSpeedValue = baseData.getString("attackSpeedValue");
                    String attackRangeValue = baseData.getString("attackRangeValue");
                    String movementSpeedValue = baseData.getString("movementSpeedValue");

                    Heroes heroes = new Heroes();
                    heroes.setName(heroName);
                    heroes.setType(heroType);
                    heroes.setBackground(background);
                    heroes.setAvatarUrl(avatarUrl);
                    heroes.setPictureUrl(pictureUrl);
                    heroes.setCoinsPrice(coinsPrice);
                    heroes.setDiamondPrice(diamondPrice);

                    heroes.setHealthValue(healthValue);
                    heroes.setMagicPointValue(magicPointValue);
                    heroes.setPhysicalAttackValue(physicalAttackValue);
                    heroes.setMagicAttackValue(magicAttackValue);
                    heroes.setPhysicalDefenseValue(physicalDefenseValue);
                    heroes.setMagicDefenseValue(magicDefenseValue);
                    heroes.setCritValue(critValue);
                    heroes.setAttackSpeedValue(attackSpeedValue);
                    heroes.setAttackRangeValue(attackRangeValue);
                    heroes.setMovementSpeedValue(movementSpeedValue);

                    /*LogUtil.d("handleHeroesResponse", "HeroName : " + heroes.getName());
                    LogUtil.d("handleHeroesResponse", "AvatarUrl : " + heroes.getAvatarUrl());*/
                    if (!palm300heroesDB.isExistence(heroes)) {
                        palm300heroesDB.saveHeroes(heroes);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            return false;
        }
        return true;
    }

    /*技能部分*/
    public synchronized static boolean handleSkillResponse(Palm300heroesDB palm300heroesDB, String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                LogUtil.d("handleHeroesResponse", "Skill status : " + status);

                JSONArray info = jsonObject.getJSONArray("info");

                for (int i = 0; i < info .length(); i++) {

                    JSONObject skillInfo = info.getJSONObject(i);
                    String hero = skillInfo.getString("hero");

                    JSONArray skill = skillInfo.getJSONArray("skill");

                    for (int j = 0; j < skill.length(); j++) {
                        JSONObject content = skill.getJSONObject(j);
                        String pictureUrl = content.getString("pictureUrl");
                        String name = content.getString("name");
                        String consumption = content.getString("consumption");
                        String chilldown = content.getString("chilldown");
                        String shortcut = content.getString("shortcut");
                        String effectiveness = content.getString("effectiveness");

                        Skill skills = new Skill();
                        skills.setPictureUrl(pictureUrl);
                        skills.setName(name);
                        skills.setConsumption(consumption);
                        skills.setChilldown(chilldown);
                        skills.setShortcut(shortcut);
                        skills.setEffectiveness(effectiveness);
                        skills.setHero(hero);

                        /*LogUtil.d("handleHeroesResponse", "SkillName : " + skills.getName());
                        LogUtil.d("handleHeroesResponse", "skillPictureUrl : " + skills.getPictureUrl());*/
                        if (!palm300heroesDB.isExistence(skills)) {
                            palm300heroesDB.saveSkill(skills);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            return false;
        }
        return true;
    }

    /*皮肤部分*/
    public synchronized static  boolean handleSkinResponse(Palm300heroesDB palm300heroesDB, String response) {
        if (!TextUtils.isEmpty(response)){
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                LogUtil.d("handleHeroesResponse", "Skin status : " + status);

                JSONArray info = jsonObject.getJSONArray("info");

                for (int i = 0; i < info.length(); i++ ) {
                    JSONObject skinInfo = info.getJSONObject(i);
                    String hero = skinInfo.getString("hero");

                    JSONArray skin = skinInfo.getJSONArray("skin");

                    for (int j = 0; j < skin.length(); j++) {
                        JSONObject skinContent = skin.getJSONObject(j);
                        String skinUrl = skinContent.getString("url");
                        String skinName = skinContent.getString("name");
                        String skinPrice = skinContent.getString("price");

                        Skin skins = new Skin();
                        skins.setHero(hero);
                        skins.setUrl(skinUrl);
                        skins.setName(skinName);
                        skins.setPrice(skinPrice);

                       /* LogUtil.d("handleHeroesResponse", "skinHero : " + skins.getHero());
                        LogUtil.d("handleHeroesResponse", "skinName : " + skins.getName());
                        LogUtil.d("handleHeroesResponse", "SkinUrl: " + skins.getUrl());*/

                        if (!palm300heroesDB.isExistence(skins)) {
                            palm300heroesDB.saveSkin(skins);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            return false;
        }
        return true;
    }

    /*配音部分*/
    public synchronized static boolean handlerSoundResponse(Palm300heroesDB palm300heroesDB, String response) {
        if (!TextUtils.isEmpty(response)) {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                LogUtil.d("handleHeroesResponse", "Sound status : " + status);

                JSONArray info = jsonObject.getJSONArray("info");

                for (int i = 0; i < info.length(); i++) {
                    JSONObject soundInfo = info.getJSONObject(i);
                    String hero = soundInfo.getString("hero");

                    JSONArray sound = soundInfo.getJSONArray("sound");

                    for (int j = 0; j < sound.length(); j++) {
                        JSONObject  soundIn= sound.getJSONObject(j);
                        String soundUrl = soundIn.getString("url");
                        String soundContent = soundIn.getString("content");

                        Sound sounds = new Sound();
                        sounds.setHero(hero);
                        sounds.setUrl(soundUrl);
                        sounds.setContent(soundContent);

                       /* LogUtil.d("handleHeroesResponse", "soundContent : " + sounds.getContent());
                        LogUtil.d("handleHeroesResponse", "soundUrl: " + sounds.getUrl());*/
                        if (!palm300heroesDB.isExistence(sounds)) {
                            palm300heroesDB.saveSound(sounds);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            return false;
        }
        return true;
    }


    /*************************************数据初始化********************************/

    /*初始化资讯数据*/
    public static void initNewsDate(Context context) {
        final String ADDRESS = "http://nicolite.cn/api/get_category_posts/?slug=300heroes";
        palm300heroesDB = Palm300heroesDB.getInstance(context);
        HttpUtil.sendHttpRequest(ADDRESS, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Utilty.handleNewsResponse(palm300heroesDB, response);
            }
            @Override
            public void onError(Exception e) {
                LogUtil.d("initNewsDate", "返回数据错误");
            }
        });
    }

    /*初始化英雄基础数据*/
    public static void initHeroDate(Context context) {
        final String ADDRESS = "http://og0oucran.bkt.clouddn.com/hero.json";
        palm300heroesDB = Palm300heroesDB.getInstance(context);
        HttpUtil.sendHttpRequest(ADDRESS, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Utilty.handleHeroesResponse(palm300heroesDB, response);
            }

            @Override
            public void onError(Exception e) {
                LogUtil.d("initHerolData", "返回数据错误");
            }
        });
    }

    /*初始化技能数据*/
    public static void initSkillDate(Context context) {
        final String ADDRESS = "http://og0oucran.bkt.clouddn.com/skill.json";
        palm300heroesDB = Palm300heroesDB.getInstance(context);
        HttpUtil.sendHttpRequest(ADDRESS, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Utilty.handleSkillResponse(palm300heroesDB, response);
            }
            @Override
            public void onError(Exception e) {
                LogUtil.d("initSkillData", "返回数据错误");
            }
        });
    }

    /*初始化皮肤数据*/
    public static void initSkinDate(Context context) {
        final String ADDRESS = "http://og0oucran.bkt.clouddn.com/skin.json";
        palm300heroesDB = Palm300heroesDB.getInstance(context);
        HttpUtil.sendHttpRequest(ADDRESS, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Utilty.handleSkinResponse(palm300heroesDB, response);
            }

            @Override
            public void onError(Exception e) {
                LogUtil.d("initSkinData", "返回数据错误");
            }
        });
    }

    /*初始化配音数据*/
    public static void initSoundDate(Context context) {
        final String ADDRESS = "http://og0oucran.bkt.clouddn.com/sound.json";
        palm300heroesDB = Palm300heroesDB.getInstance(context);
        HttpUtil.sendHttpRequest(ADDRESS, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Utilty.handlerSoundResponse(palm300heroesDB, response);
            }

            @Override
            public void onError(Exception e) {
                LogUtil.d("initSoundData", "返回数据错误");
            }
        });
    }

    /*初始化所有数据*/
    public static void initAllDate(Context context) {
        //initNewsDate(context);
        initHeroDate(context);
        initSkillDate(context);
        initSkinDate(context);
        initSoundDate(context);
    }

    /* 按时间排序*/
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
