package model.recordLogger;

/**
 * Created by NICOLITE on 2017/2/7 0007.
 */

public class RoleRank {
    private int id;
    private int type;
    private String rankName;
    private String valueName;
    private int rank;
    private String value;
    private int rankChange;
    private int rankIndex;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getRankChange() {
        return rankChange;
    }

    public void setRankChange(int rankChange) {
        this.rankChange = rankChange;
    }

    public int getRankIndex() {
        return rankIndex;
    }

    public void setRankIndex(int rankIndex) {
        this.rankIndex = rankIndex;
    }
}
