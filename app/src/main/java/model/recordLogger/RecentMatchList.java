package model.recordLogger;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by NICOLITE on 2017/3/10 0010.
 */

public class RecentMatchList {
    public String Result;
    @SerializedName("List")
    public List<MatchList> matchListList;

    public class MatchList {
        public int MatchID;
        public int MatchType;
        public int HeroLevel;
        public int Result;
        public String MatchDate;
        @SerializedName("Hero")
        public Hero hero;
        public class Hero{
            public int ID;
            public String Name;
            public String IconFile;
        }
    }
}
