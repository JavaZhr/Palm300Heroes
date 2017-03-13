package model.hero;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by NICOLITE on 2017/3/10 0010.
 */

public class Skill {
    public String status;
    @SerializedName("info")
    public List<SkillInfo> skillInfoList;

    public class SkillInfo {
        public String hero;
        @SerializedName("skill")
        public List<SkillItem> skillItemList;
        public class SkillItem {
            public String pictureUrl;
            public String name;
            public String consumption;
            public String chilldown;
            public String shortcut;
            public String effectiveness;
        }
    }
}
