package model.recordLogger;

import java.io.Serializable;
import java.util.List;

/**
 * Created by NICOLITE on 2017/2/6 0006.
 */

public class Record implements Serializable{
    private List<String> roleDate;
    private List<ServerRanking> rankingList;
    private List<MatchList> matchLists;

    public List<String> getRoleDate() {
        return roleDate;
    }

    public void setRoleDate(List<String> roleDate) {
        this.roleDate = roleDate;
    }

    public List<ServerRanking> getRankingList() {
        return rankingList;
    }

    public void setRankingList(List<ServerRanking> rankingList) {
        this.rankingList = rankingList;
    }

    public List<MatchList> getMatchLists() {
        return matchLists;
    }

    public void setMatchLists(List<MatchList> matchLists) {
        this.matchLists = matchLists;
    }
}
