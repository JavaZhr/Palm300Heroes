package model;

/**
 * Created by NICOLITE on 2016/10/26 0026.
 */

public class Skin {
    private int id;
    private String hero;
    private String url;

    public Skin() {
    }

    public Skin(String hero, String url) {
        this.hero = hero;
        this.url = url;
    }

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
