package model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by NICOLITE on 2017/3/10 0010.
 */

public class FightSkill {
    public String status;
    @SerializedName("info")
    public List<FightSkillInfo> fightSkillInfoList;

    public class FightSkillInfo {
        public String name;
        public String picture;
        public String content;
    }
}
