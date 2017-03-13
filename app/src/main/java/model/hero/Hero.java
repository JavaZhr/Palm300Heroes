package model.hero;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by NICOLITE on 2017/3/10 0010.
 */

public class Hero {
    public String status;
    @SerializedName("info")
    public List<HeroInfo > heroinfoList;

    public class HeroInfo implements Serializable {
        public String heroName;
        public String heroType;
        public String heroCode;
        public String background;
        public String avatarUrl;
        public String pictureUrl;
        public String coinsPrice;
        public String diamondPrice;
        public BaseData baseData;
        public class BaseData{
            public String healthValue;
            public String magicPointValue;
            public String physicalAttackValue;
            public String magicAttackValue;
            public String physicalDefenseValue;
            public String magicDefenseValue ;
            public String critValue;
            public String attackSpeedValue;
            public String attackRangeValue;
            public String movementSpeedValue;
        }
    }
}
