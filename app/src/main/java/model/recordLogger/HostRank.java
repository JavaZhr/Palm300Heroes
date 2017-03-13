package model.recordLogger;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by NICOLITE on 2017/3/10 0010.
 */

public class HostRank {
    public String Result;
    @SerializedName("Rank")
    public Rank rank;

    public class Rank {
        public String Title;
        public String IndexName;
        public String NameName;
        public String ValueName;
        public String ChangeName;
        @SerializedName("Rank")
        public List<RankList> rankList;
        public class RankList{
            public int Index;
            public String Url;
            public String Name;
            public String Value;
            public int RankChange;
        }
    }
}
