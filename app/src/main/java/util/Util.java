package util;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import model.FightSkill;
import model.hero.Hero;
import model.hero.Skill;
import model.hero.Skin;
import model.hero.Sound;
import model.recordLogger.HostRank;
import model.recordLogger.MatchDetail;
import model.recordLogger.RecentMatchList;
import model.recordLogger.Role;

/**
 * Created by NICOLITE on 2016/10/15 0015.
 */

public class Util {

    /*英雄信息部分*/
    public static Hero handleHeroResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String hero = jsonObject.toString();
                return new Gson().fromJson(hero, Hero.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /*技能部分*/
    public static Skill handleSkillResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String skill = jsonObject.toString();
                return new Gson().fromJson(skill, Skill.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /*皮肤部分*/
    public static Skin handleSkinResponse(String response) {
        if (!TextUtils.isEmpty(response)){
            try {
                JSONObject jsonObject = new JSONObject(response);
                String skin = jsonObject.toString();
                return new Gson().fromJson(skin, Skin.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /*配音部分*/
    public static Sound handleSoundResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String sound = jsonObject.toString();
                return new Gson().fromJson(sound, Sound.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static FightSkill handleFightSkillResponse(String response){
        if (!TextUtils.isEmpty(response)){
            try {
                JSONObject jsonObject = new JSONObject(response);
                String fightSkill = jsonObject.toString();
                return new Gson().fromJson(fightSkill, FightSkill.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /* 按时间排序*/
    public static Date stringToDate(String date) {
        ParsePosition position = new ParsePosition(0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return simpleDateFormat.parse(date, position);
    }


    public static Role handleRoleResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String role = jsonObject.toString();
                return new Gson().fromJson(role, Role.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static RecentMatchList handleRecentMatchListResponse(String response) {
        if (!TextUtils.isEmpty(response)){
            try {
                JSONObject jsonObject = new JSONObject(response);
                String recentMatchList = jsonObject.toString();
                return new Gson().fromJson(recentMatchList, RecentMatchList.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static MatchDetail handleMatchDetailResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String matchDetail = jsonObject.toString();
                return new Gson().fromJson(matchDetail, MatchDetail.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static HostRank handleHostRankResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String hostRank = jsonObject.toString();
                return new Gson().fromJson(hostRank, HostRank.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
