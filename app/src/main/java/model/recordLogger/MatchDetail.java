package model.recordLogger;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by NICOLITE on 2017/3/10 0010.
 */

public class MatchDetail {
    public String Result;
    @SerializedName("Match")
    public Match match;

    public class Match{
        public int MatchType;
        public int WinSideKill;
        public int LoseSideKill;
        public int UsedTime;
        public String MatchDate;
        @SerializedName("WinSide")
        public List<WinSide> winSideList;
        @SerializedName("LoseSide")
        public List<LoseSide> loseSideList;
        public class WinSide {
            public String RoleName;
            public int RoleID;
            public int RoleLevel;
            public int HeroID;
            public int HeroLevel;
            public int Result;
            public int TeamResult;
            public int IsFirstWin;
            public int KillCount;
            public int DeathCount;
            public int AssistCount;
            public int TowerDestroy;
            public int KillUnitCount;
            public int TotalMoney;
            public int RewardMoney;
            public int RewardExp;
            public int JumpValue;
            public int WinCount;
            public int MatchCount;
            public int ELO;
            public int KDA;
            @SerializedName("Hero")
            public Hero hero;
            @SerializedName("Skill")
            public List<Skill> skillList;
            @SerializedName("Equip")
            public List<Equip> equipList;
            public class Hero {
                public int ID;
                public String Name;
                public String IconFile;
            }

            public class Skill {
                public int ID;
                public String Name;
                public String IconFile;
            }

            public class Equip {
                public int ID;
                public String Name;
                public String IconFile;
            }
        }

        public class LoseSide {
            public String RoleName;
            public int RoleID;
            public int RoleLevel;
            public int HeroID;
            public int HeroLevel;
            public int Result;
            public int TeamResult;
            public int IsFirstWin;
            public int KillCount;
            public int DeathCount;
            public int AssistCount;
            public int TowerDestroy;
            public int KillUnitCount;
            public int TotalMoney;
            public int RewardMoney;
            public int RewardExp;
            public int JumpValue;
            public int WinCount;
            public int MatchCount;
            public int ELO;
            public int KDA;
            @SerializedName("Hero")
            public Hero hero;
            @SerializedName("Skill")
            public List<Skill> skillList;
            @SerializedName("Equip")
            public List<Equip> equipList;
            public class Hero {
                public int ID;
                public String Name;
                public String IconFile;
            }

            public class Skill {
                public int ID;
                public String Name;
                public String IconFile;
            }

            public class Equip {
                public int ID;
                public String Name;
                public String IconFile;
            }
        }
    }
}
