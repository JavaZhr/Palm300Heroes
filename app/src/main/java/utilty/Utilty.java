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
                    LogUtil.d("handleHeroesResponse", "AvatarUrl : " + heroes.getAvatarUrl());;
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
