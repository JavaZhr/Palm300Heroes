package database;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by NICOLITE on 2017/3/10 0010.
 */

public class SkillD extends DataSupport {
    @Column(nullable = false)
    private String hero;
    @Column(unique = true)
    private String pictureUrl;
    private String name;
    private String consumption;
    private String chilldown;
    private String shortcut;
    private String effectiveness;

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
