package model.recordLogger;

/**
 * Created by NICOLITE on 2017/2/7 0007.
 */

public class LatestMatch {
    private int id;
    private int matchId;
    private int matchType;
    private int HeroLevel;
    private int result;
    private String matchDate;

    private int heroId;
    private String heroName;
    private String heroIcon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public int getMatchType() {
        return matchType;
    }

    public void setMatchType(int matchType) {
        this.matchType = matchType;
    }

    public int getHeroLevel() {
        return HeroLevel;
    }

    public void setHeroLevel(int heroLevel) {
        HeroLevel = heroLevel;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }

    public int getHeroId() {
        return heroId;
    }

    public void setHeroId(int heroId) {
        this.heroId = heroId;
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    public String getHeroIcon() {
        return heroIcon;
    }

    public void setHeroIcon(String heroIcon) {
        this.heroIcon = heroIcon;
    }
}
