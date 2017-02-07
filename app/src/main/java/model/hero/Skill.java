package model.hero;

/**
 * Created by NICOLITE on 2016/10/26 0026.
 */

public class Skill {
    private int id;
    private String hero;
    private String pictureUrl;
    private String name;
    private String consumption;
    private String chilldown;
    private String shortcut;
    private String effectiveness;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHero() {
        return hero;
    }

    public void setHero(String hero) {
        this.hero = hero;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConsumption() {
        return consumption;
    }

    public void setConsumption(String consumption) {
        this.consumption = consumption;
    }

    public String getChilldown() {
        return chilldown;
    }

    public void setChilldown(String chilldown) {
        this.chilldown = chilldown;
    }

    public String getShortcut() {
        return shortcut;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    public String getEffectiveness() {
        return effectiveness;
    }

    public void setEffectiveness(String effectiveness) {
        this.effectiveness = effectiveness;
    }
}
