package model.recordLogger;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by NICOLITE on 2017/2/7 0007.
 */

public class Role implements Serializable{
    public String Result;
    @SerializedName("Role")
    public RoleInfo roleInfo;
    public List<Rank> Rank;

    public class RoleInfo implements Serializable{
        public String RoleName;
        public int RoleID;
        public int RoleLevel;
        public int JumpValue;
        public int WinCount;
        public int MatchCount;
        public String UpdateTime;
    }

    public class Rank implements Serializable{
        public int Type;
        public String RankName;
        public String ValueName;
        public int Rank;
        public String Value;
        public int RankChange;
        public int RankIndex;
    }
}
