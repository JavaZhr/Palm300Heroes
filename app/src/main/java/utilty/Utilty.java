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

import database.Palm300heroesDB;
import model.Heroes;
import model.News;
import model.Skill;
import model.Skin;
import model.Sound;

/**
 * Created by NICOLITE on 2016/10/15 0015.
 */


public class Utilty {
    /**
     * 解析和处理服务器返回的News数据
     */
    public synchronized static boolean handleNewsResponse(Palm300heroesDB palm300heroesDB, String response) {

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


    public synchronized static boolean handleHeroesResponse(Palm300heroesDB palm300heroesDB, String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                LogUtil.d("handleHeroesResponse", "Hero staus : " + status);
                int count = jsonObject.getInt("count");

                JSONArray info = jsonObject.getJSONArray("info");

                for (int i = 0; i < info.length(); i++) {
                    /*英雄信息部分*/
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

                    LogUtil.d("handleHeroesResponse", "HeroName : " + heroes.getName());
                    LogUtil.d("handleHeroesResponse", "AvatarUrl : " + heroes.getAvatarUrl());
                    if (!palm300heroesDB.isExistence(heroes)) {
                        palm300heroesDB.saveHeroes(heroes);
                    }

                    /*技能部分*/
                    JSONArray skill = heroInfo.getJSONArray("skill");

                    for (int j = 0; j < skill.length(); j++) {
                        JSONObject skillInfo = skill.getJSONObject(j);
                        String skillPictureUrl = skillInfo .getString("pictureUrl");
                        String skillName = skillInfo .getString("name");
                        String skillOperation = skillInfo .getString("operation");
                        String skillDescribe = skillInfo .getString("describe");

                        Skill skills = new Skill();
                        skills.setPictureUrl(skillPictureUrl);
                        skills.setName(skillName);
                        skills.setOperation(skillOperation);
                        skills.setDescribe(skillDescribe);
                        skills.setHero(heroName);

                        LogUtil.d("handleHeroesResponse", "SkillName : " + skills.getName());
                        LogUtil.d("handleHeroesResponse", "skillPictureUrl : " + skills.getPictureUrl());
                        if (!palm300heroesDB.isExistence(skills)) {
                            palm300heroesDB.saveSkill(skills);
                        }
                    }

                    /*皮肤部分*/
                    JSONArray skin = heroInfo.getJSONArray("skin");

                    for (int k = 0; k < skin.length(); k++) {
                        JSONObject skinInfo = skin.getJSONObject(k);
                        String skinUrl = skinInfo.getString("url");
                        String skinName = skinInfo.getString("name");
                        String skinPrice = skinInfo.getString("price");

                        Skin skins = new Skin();
                        skins.setHero(heroName);
                        skins.setUrl(skinUrl);
                        skins.setName(skinName);
                        skins.setPrice(skinPrice);

                        LogUtil.d("handleHeroesResponse", "skinName : " + skins.getName());
                        LogUtil.d("handleHeroesResponse", "SkinUrl: " + skins.getUrl());
                        if (!palm300heroesDB.isExistence(skins)) {
                            palm300heroesDB.saveSkin(skins);
                        }
                    }

                    /*配音部分*/
                    JSONArray sound = heroInfo.getJSONArray("sound");

                    for (int l = 0; l < sound.length(); l++) {
                        JSONObject soundInfo = sound.getJSONObject(l);
                        String soundUrl = soundInfo.getString("url");
                        String soundName = soundInfo.getString("name");

                        Sound sounds = new Sound();
                        sounds.setHero(heroName);
                        sounds.setUrl(soundUrl);
                        sounds.setName(soundName);

                        LogUtil.d("handleHeroesResponse", "soundName : " + sounds.getName());
                        LogUtil.d("handleHeroesResponse", "soundUrl: " + sounds.getUrl());
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

    /**
     * 按时间排序
     */
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
