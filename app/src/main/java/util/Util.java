package util;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import database.Palm300heroesDB;
import model.FightSkill;
import model.hero.Heroes;
import model.recordLogger.LatestMatch;
import model.recordLogger.MatchList;
import model.News;
import model.recordLogger.Record;
import model.recordLogger.Role;
import model.recordLogger.RoleRank;
import model.recordLogger.ServerRanking;
import model.hero.Skill;
import model.hero.Skin;
import model.hero.Sound;
import myInterface.HttpCallbackListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by NICOLITE on 2016/10/15 0015.
 */


public class Util {
    /**
     * 解析和处理服务器返回的News数据
     */

    /*资讯信息部分*/
    private static boolean handleNewsResponse(Palm300heroesDB palm300heroesDB, String response) {
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
    private static boolean handleHeroesResponse(Palm300heroesDB palm300heroesDB, String response) {
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
                    String avatarUrl = "http://ogbna06ji.bkt.clouddn.com/heroes/avatar/" + heroInfo.getString("avatarUrl");
                    String coinsPrice = heroInfo.getString("coinsPrice");
                    String diamondPrice = heroInfo.getString("diamondPrice");
                    String pictureUrl = "http://ogbna06ji.bkt.clouddn.com/heroes/picture/" + heroInfo.getString("pictureUrl");
                    if (pictureUrl.equals("http://ogbna06ji.bkt.clouddn.com/heroes/picture/")) {
                        pictureUrl += "undefined.jpg";
                    }


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
    private static boolean handleSkillResponse(Palm300heroesDB palm300heroesDB, String response) {
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
                        String pictureUrl ="http://ogbna06ji.bkt.clouddn.com/heroes/skill/" + content.getString("pictureUrl");
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
    private static  boolean handleSkinResponse(Palm300heroesDB palm300heroesDB, String response) {
        if (!TextUtils.isEmpty(response)){
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                LogUtil.d("handleHeroesResponse", "Skin status : " + status);

                JSONArray info = jsonObject.getJSONArray("info");

                for (int i = 0; i < info.length(); i++ ) {
                    JSONObject skinInfo = info.getJSONObject(i);
                    String hero = skinInfo.getString("hero");

                    JSONArray skin = skinInfo.getJSONArray("skin");

                    for (int j = 0; j < skin.length(); j++) {
                        JSONObject skinContent = skin.getJSONObject(j);
                        String skinUrl = "http://ogbna06ji.bkt.clouddn.com/heroes/skin/" + skinContent.getString("url");
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
    private static boolean handlerSoundResponse(Palm300heroesDB palm300heroesDB, String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONObject  jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                LogUtil.d("handleHeroesResponse", "Sound status : " + status);

                JSONArray info = jsonObject.getJSONArray("info");

                for (int i = 0; i < info.length(); i++) {
                    JSONObject soundInfo = info.getJSONObject(i);
                    String hero = soundInfo.getString("hero");

                    JSONArray sound = soundInfo.getJSONArray("sound");

                    for (int j = 0; j < sound.length(); j++) {
                        JSONObject  soundIn= sound.getJSONObject(j);
                        String soundUrl = "http://ogbna06ji.bkt.clouddn.com/heroes/sound/" + soundIn.getString("url");
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
    public static void initNewsData(Context context) {
        final String ADDRESS = "http://nicolite.cn/api/get_category_posts/?slug=300heroes";
        final Palm300heroesDB palm300heroesDB = Palm300heroesDB.getInstance(context);
        HttpUtil.sendHttpRequest(ADDRESS, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Util.handleNewsResponse(palm300heroesDB, response);
            }
            @Override
            public void onError(Exception e) {
                LogUtil.d("initNewsDate", "返回数据错误");
            }
        });
    }

    /*初始化英雄数据*/
    public static void initHeroData(Context context, final int type) {
        final String HEROADDRESS = "http://og0oucran.bkt.clouddn.com/hero.json";
        final String SKILLADDRESS = "http://og0oucran.bkt.clouddn.com/skill.json";
        final String SKINADDRESS = "http://og0oucran.bkt.clouddn.com/skin.json";
        final String SOUNDADDRESS = "http://og0oucran.bkt.clouddn.com/sound.json";
        String ADDRESS = null;
        final Palm300heroesDB palm300heroesDB = Palm300heroesDB.getInstance(context);

        switch(type) {
            case 1 :
                ADDRESS = HEROADDRESS;
                break;
            case 2 :
                ADDRESS = SKILLADDRESS;
                break;
            case 3 :
                ADDRESS = SKINADDRESS;
                break;
            case 4 :
                ADDRESS = SOUNDADDRESS;
                break;
         default: break;
        }
      /* final Context mcontext =context;
       final String finalADDRESS = ADDRESS;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    AssetManager assetManager = mcontext.getAssets();
                    InputStreamReader is = new InputStreamReader(assetManager.open(finalADDRESS), "utf-8");
                    BufferedReader br = new BufferedReader(is);
                    String line = "";
                    StringBuilder builder = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        builder.append(line);
                    }
                    String response = builder.toString();

                    if (type == 1) {
                        Utilty.handleHeroesResponse(palm300heroesDB, response);
                    }
                    if (type == 2) {
                        Utilty.handleSkillResponse(palm300heroesDB, response);
                    }
                    if (type == 3){
                        Utilty.handleSkinResponse(palm300heroesDB, response);
                    }
                    if (type == 4) {
                        Utilty.handlerSoundResponse(palm300heroesDB, response);
                    }
                    if (type < 1 || type > 4) {
                        LogUtil.d("initHeroDate", "type参数错误。1：英雄数据，2：技能数据，3：皮肤数据，4：语音数据");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();*/

       /* HttpUtil.sendHttpRequest(ADDRESS, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                if (type == 1) {
                    Util.handleHeroesResponse(palm300heroesDB, response);
                }
                if (type == 2) {
                    Util.handleSkillResponse(palm300heroesDB, response);
                }
                if (type == 3){
                    Util.handleSkinResponse(palm300heroesDB, response);
                }
                if (type == 4) {
                    Util.handlerSoundResponse(palm300heroesDB, response);
                }
                if (type < 1 || type > 4) {
                    LogUtil.d("initHeroDate", "type参数错误。1：英雄数据，2：技能数据，3：皮肤数据，4：语音数据");
                }
            }

            @Override
            public void onError(Exception e) {
                LogUtil.d("initHeroDate", "返回数据异常");
            }
        });*/

        HttpUtil.sendOkHttpRequest(ADDRESS, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.d("initHeroDate", "返回数据异常");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (type == 1) {
                    Util.handleHeroesResponse(palm300heroesDB, response.body().string());
                }
                if (type == 2) {
                    Util.handleSkillResponse(palm300heroesDB, response.body().string());
                }
                if (type == 3){
                    Util.handleSkinResponse(palm300heroesDB, response.body().string());
                }
                if (type == 4) {
                    Util.handlerSoundResponse(palm300heroesDB, response.body().string());
                }
                if (type < 1 || type > 4) {
                    LogUtil.d("initHeroDate", "type参数错误。1：英雄数据，2：技能数据，3：皮肤数据，4：语音数据");
                }
            }
        });
    }

    public static void initAllData(Context context) {
        initHeroData(context, 1);
        initHeroData(context, 2);
        initHeroData(context, 3);
        initHeroData(context, 4);

       // initNewsData(context);
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

/**************************战绩查询数据*************************/
    public static List<Record> getRecordLoggerResponse(String url, final int type) {
        final List<Record> list = new ArrayList<>();
        final  List<String> dataList = new ArrayList<>();
        final  List<ServerRanking> sDataList = new ArrayList<>();
        final List<MatchList> mDataList = new ArrayList<>();
        HttpUtil.sendHttpRequest(url, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Document document = Jsoup.parse(response);
                if (type == 1 || type == 0) {

                    Elements elements = document.select("table.info").select("tr");

                    for (int i = 0; i < elements.size(); i++) {
                        Elements tds = elements.get(i).select("td");
                        LogUtil.d("handlerRecordLoggerResponse", tds.text());
                        dataList.add(tds.text());
                    }
                }
                if (type == 2 || type == 0) {
                    Elements elements = document.select("table.datatable[width=50%]").select("tr[onClick*=javascript:window.open(']");
                    for (int i = 0; i < elements.size(); i++) {
                        Elements tds = elements.get(i).select("td");
                        String clickUrl = elements.get(i).attr("onClick").replace("javascript:window.open('", "");
                        clickUrl = "http://300report.jumpw.com/" + clickUrl.replace("');", "");
                        String[] infoR = tds.text().split(" ");
                        ServerRanking serverRanking = new ServerRanking();
                        serverRanking.setClickUrl(clickUrl);
                        serverRanking.setInfoR(infoR);

                        LogUtil.d("handlerRecordLoggerResponse", tds.text());
                        sDataList.add(serverRanking);
                    }
                }
                if (type == 3 || type == 0) {
                    Elements elements = document.select("table.datatable[width=100%]").select("tr[onClick*=javascript:window.open(]");
                    for (int i = 0; i < elements.size(); i++) {
                        Elements tds = elements.get(i).select("td");

                        String clickUrl = elements.get(i).attr("onClick").replace("javascript:window.open('", "");
                        clickUrl = "http://300report.jumpw.com/" + clickUrl.replace("');", "");

                        String imageUrl = "http://300report.jumpw.com/" + tds.select("img").attr("src");
                        String[] data = tds.text().split(" ");

                        MatchList matchList = new MatchList();
                        matchList.setClickUrl(clickUrl);
                        matchList.setImageUrl(imageUrl);
                        matchList.setData(data);

                        LogUtil.d("tds", imageUrl);
                        LogUtil.d("tds", clickUrl);
                        mDataList.add(matchList);
                    }
                }
                if (type < 0 || type > 3){
                    LogUtil.d("handlerRecordLoggerResponse", "type参数错误: " + type + " 0:获取全部，1：获取oleDate,2：获取ServerRanking，3：获取MatchList");
                }
            }

            @Override
            public void onError(Exception e) {

            }
        });

        Record record = new Record();
        record.setRoleDate(dataList);
        record.setRankingList(sDataList);
        record.setMatchLists(mDataList);

        list.clear();
        list.add(record);
        return list;
    }

    public static void handlerRecordLoggerResponse(Context context, String code, final int type) {
        final Palm300heroesDB palm300heroesDB = Palm300heroesDB.getInstance(context);
        palm300heroesDB.deleteRole(null, null);
        palm300heroesDB.deleteRoleRank(null, null);
        palm300heroesDB.deleteLatestMatch(null, null);
        String ADDRESS = null;
        String codeE = code;
        try {
            codeE  = URLEncoder.encode(code, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        switch (type) {
            case 1 :
                ADDRESS = "http://300report.jumpw.com/api/getrole?name=" + codeE;
                break;
            case 2 :
                ADDRESS = "http://300report.jumpw.com/api/getlist?name=" + codeE;
                break;
            case 3 :
                ADDRESS = "http://300report.jumpw.com/api/getmatch?id=" + codeE;
                break;
            /*case 4 :
                ADDRESS = " http://300report.jumpw.com/api/getrank?type" + codeE;
                break;*/
            default: break;
        }
        LogUtil.d("handlerRecordLoggerResponse", "ADDRESS " + ADDRESS);

       /* HttpUtil.sendHttpRequest(ADDRESS, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                if (type == 1) {
                    handlerRoleResponse(palm300heroesDB, response);
                }
                if (type == 2) {
                    handlerLatestMatchResponse(palm300heroesDB, response);
                }
            }

            @Override
            public void onError(Exception e) {

            }
        });*/

        HttpUtil.sendOkHttpRequest(ADDRESS, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (type == 1) {
                    handlerRoleResponse(palm300heroesDB, response.body().string());
                }
                if (type == 2) {
                    handlerLatestMatchResponse(palm300heroesDB, response.body().string());
                }
            }
        });
    }

    private static boolean handlerRoleResponse(Palm300heroesDB palm300heroesDB, String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String result = jsonObject.getString("Result");

                if (result.equals("OK")){
                    JSONObject role = jsonObject.getJSONObject("Role");

                    String roleName = role.getString("RoleName");
                    int roleLevel = role.getInt("RoleLevel");
                    int roleId = role.getInt("RoleID");
                    int jumpValue = role.getInt("JumpValue");
                    int winCount = role.getInt("WinCount");
                    int matchCount = role.getInt("MatchCount");
                    String updateTime = role.getString("UpdateTime");

                    Role roles = new Role();
                    roles.setRoleName(roleName);
                    roles.setRoleLevel(roleLevel);
                    roles.setRoleId(roleId);
                    roles.setJumpValue(jumpValue);
                    roles.setWinCount(winCount);
                    roles.setMatchCount(matchCount);
                    roles.setUpdateTime(updateTime);

                    palm300heroesDB.saveRole(roles);

                    JSONArray rankList = jsonObject.getJSONArray("Rank");
                    for (int i = 0; i < rankList.length(); i++) {
                        JSONObject ranks = rankList.getJSONObject(i);

                        int type = ranks.getInt("Type");
                        String rankName = ranks.getString("RankName");
                        String valueName = ranks.getString("ValueName");
                        int rank = ranks.getInt("Rank");
                        String value = ranks.getString("Value");
                        int rankChange = ranks.getInt("RankChange");
                        int rankIndex = ranks.getInt("RankIndex");

                        RoleRank roleRank = new RoleRank();
                        roleRank.setType(type);
                        roleRank.setRankName(rankName);
                        roleRank.setValueName(valueName);
                        roleRank.setRank(rank);
                        roleRank.setValue(value);
                        roleRank.setRankChange(rankChange);
                        roleRank.setRankIndex(rankIndex);

                        LogUtil.d("handlerRoleResponse", "RoleName：" + roleName);
                        palm300heroesDB.saveRoleRank(roleRank);
                    }
                }else {
                    LogUtil.d("handlerRoleResponse", "返回数据异常 " + result + "\n" + response);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            return false;
        }
        return true;
    }

    private static boolean handlerLatestMatchResponse(Palm300heroesDB palm300heroesDB, String response) {
        if (!TextUtils.isEmpty(response)){
            try {
                JSONObject jsonObject = new JSONObject(response);
                String results = jsonObject.getString("Result");
                if (results.equals("OK")) {
                    JSONArray list = jsonObject.getJSONArray("List");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject match = list.getJSONObject(i);
                        int matchId = match.getInt("MatchID");
                        int matchType = match.getInt("MatchType");
                        int heroLevel = match.getInt("HeroLevel");
                        int result = match.getInt("Result");
                        String matchDate = match.getString("MatchDate");

                        JSONObject hero = match.getJSONObject("Hero");

                        int heroId = hero.getInt("ID");
                        String heroName = hero.getString("Name");
                        String heroIcon = "http://300report.jumpw.com/static/images/" + hero.getString("IconFile");

                        LatestMatch latestMatch = new LatestMatch();
                        latestMatch.setMatchId(matchId);
                        latestMatch.setMatchType(matchType);
                        latestMatch.setHeroLevel(heroLevel);
                        latestMatch.setResult(result);
                        latestMatch.setMatchDate(matchDate);
                        latestMatch.setHeroId(heroId);
                        latestMatch.setHeroName(heroName);
                        latestMatch.setHeroIcon(heroIcon);

                        LogUtil.d("handlerLatestMatchResponse", "heroIcon：" + heroIcon);
                       palm300heroesDB.saveLatestMatch(latestMatch);
                    }
                }else {
                    LogUtil.d("handlerLatestMatchResponse", "返回数据异常");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            return false;
        }
        return true;
    }

    private static boolean handlerMatchResponse(Palm300heroesDB palm300heroesDB, String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String results = jsonObject.getString("Result");
                if (results.equals("OK")){
                    JSONObject match = jsonObject.getJSONObject("Match");
                    int matchType = match.getInt("MatchType");
                    int winSideKill = match.getInt("WinSideKill");
                    int loseSideKill = match.getInt("LoseSideKill");
                    int useTime = match.getInt("UsedTime");
                    String matchDate = match.getString("MatchDate");

                    JSONArray winSide = match.getJSONArray("WinSide");
                    for (int i = 0; i < winSide.length(); i++) {
                        JSONObject winSideI = winSide.getJSONObject(i);
                        String roleName = winSideI.getString("RoleName");
                        int roleId = winSideI.getInt("RoleID");
                        int roleLevel = winSideI.getInt("RoleLevel");
                        int heroId = winSideI.getInt("HeroID");
                        int heroLevel = winSideI.getInt("HeroLevel");
                        int result = winSideI.getInt("Result");
                        int teamResult = winSideI.getInt("TeamResult");
                        int isFirstWin = winSideI.getInt("IsFirstWin");
                        int killCount = winSideI.getInt("KillCount");
                        int deathCount = winSideI.getInt("DeathCount");
                        int assistCount = winSideI.getInt("AssistCount");
                        int towerDestory = winSideI.getInt("TowerDestroy");
                        int killUnitCount = winSideI.getInt("KillUnitCount");
                        int totalMoney = winSideI.getInt("TotalMoney");

                        JSONArray skillId = winSideI.getJSONArray("SkillID");
                        JSONArray equipId = winSideI.getJSONArray("EquipID");
                        int rewardMoney = winSideI.getInt("RewardMoney");
                        int rewardExp = winSideI.getInt("RewardExp");
                        int jumpValue = winSideI.getInt("JumpValue");
                        int winCount = winSideI.getInt("WinCount");
                        int matchCount = winSideI.getInt("MatchCount");
                        int ELO = winSideI.getInt("ELO");
                        int KDA = winSideI.getInt("KDA");

                        JSONObject hero = winSideI.getJSONObject("Hero");
                        int id = hero.getInt("ID");
                        String name = hero.getString("Name");
                        String iconFile = hero.getString("IconFile");

                        JSONArray skill = hero.getJSONArray("skill");

                        for (int k = 0; k < skill.length(); i++) {
                            JSONObject skillI = skill.getJSONObject(i);
                            int skillIds = skillI.getInt("ID");
                            String skillName = skillI.getString("Name");
                            String skillIconFile = skillI.getString("IconFile");
                        }

                        JSONArray equip = hero.getJSONArray("Equip");
                        for (int l = 0; l < equip.length(); l++) {
                            JSONObject equipI = equip.getJSONObject(i);
                            int equipIds = equipI.getInt("ID");
                            String equipName = equipI.getString("Name");
                            String equipIconFile = equipI.getString("IconFile");
                        }
                    }

                    JSONArray loseSide = match.getJSONArray("LoseSide");
                    for (int i = 0; i < loseSide.length(); i++) {
                        JSONObject loseSideI = loseSide.getJSONObject(i);
                        String roleName = loseSideI.getString("RoleName");
                        int roleId = loseSideI.getInt("RoleID");
                        int roleLevel = loseSideI.getInt("RoleLevel");
                        int heroId = loseSideI.getInt("HeroID");
                        int heroLevel = loseSideI.getInt("HeroLevel");
                        int result = loseSideI.getInt("Result");
                        int teamResult = loseSideI.getInt("TeamResult");
                        int isFirstWin = loseSideI.getInt("IsFirstWin");
                        int killCount = loseSideI.getInt("KillCount");
                        int deathCount = loseSideI.getInt("DeathCount");
                        int assistCount = loseSideI.getInt("AssistCount");
                        int towerDestory = loseSideI.getInt("TowerDestroy");
                        int killUnitCount = loseSideI.getInt("KillUnitCount");
                        int totalMoney = loseSideI.getInt("TotalMoney");

                        JSONArray skillId = loseSideI.getJSONArray("SkillID");
                        JSONArray equipId = loseSideI.getJSONArray("EquipID");
                        int rewardMoney = loseSideI.getInt("RewardMoney");
                        int rewardExp = loseSideI.getInt("RewardExp");
                        int jumpValue = loseSideI.getInt("JumpValue");
                        int winCount = loseSideI.getInt("WinCount");
                        int matchCount = loseSideI.getInt("MatchCount");
                        int ELO = loseSideI.getInt("ELO");
                        int KDA = loseSideI.getInt("KDA");

                        JSONObject hero = loseSideI.getJSONObject("Hero");
                        int id = hero.getInt("ID");
                        String name = hero.getString("Name");
                        String iconFile = hero.getString("IconFile");

                        JSONArray skill = hero.getJSONArray("skill");

                        for (int k = 0; k < skill.length(); i++) {
                            JSONObject skillI = skill.getJSONObject(i);
                            int skillIds = skillI.getInt("ID");
                            String skillName = skillI.getString("Name");
                            String skillIconFile = skillI.getString("IconFile");
                        }

                        JSONArray equip = hero.getJSONArray("Equip");
                        for (int l = 0; l < equip.length(); l++) {
                            JSONObject equipI = equip.getJSONObject(i);
                            int equipIds = equipI.getInt("ID");
                            String equipName = equipI.getString("Name");
                            String equipIconFile = equipI.getString("IconFile");
                        }
                    }
                }else {
                    LogUtil.d("handlerMatchResponse", "返回数据异常 " + results + "\n" + response);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            return false;
        }
        return true;
    }

    public static List<FightSkill> getFightSkill(Context context) {
        List<FightSkill> list = new ArrayList<>();
        AssetManager assetManager = context.getAssets();
        try {
            InputStreamReader is = new InputStreamReader(assetManager.open("fight_skill.json"), "utf-8");
            BufferedReader br = new BufferedReader(is);
            String line = "";
            StringBuilder builder = new StringBuilder();
            while ((line = br.readLine()) != null){
                builder.append(line);
            }
            br.close();
            is.close();
            JSONObject jsonObject = new JSONObject(builder.toString());
            String status = jsonObject.getString("status");
            if (status.equals("ok")){
                JSONArray info = jsonObject.getJSONArray("info");
                for (int i = 0 ; i < info.length(); i++) {
                    JSONObject skill = info.getJSONObject(i);
                    String name = skill.getString("name");
                    String picture = "http://ogbna06ji.bkt.clouddn.com/heroes/fightSkill/" + URLEncoder.encode(skill.getString("picture"), "utf-8");
                    String content = skill.getString("content");

                    FightSkill fightSkill = new FightSkill();
                    fightSkill.setName(name);
                    fightSkill.setPicture(picture);
                    fightSkill.setContent(content);

                    LogUtil.d("handlerFightSkillResponse", "name：" + name);
                    list.add(fightSkill);
                }
            }else {
                LogUtil.d("handlerFightSkillResponse", "返回信息失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
