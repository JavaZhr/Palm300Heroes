package model;

/**
 * Created by NICOLITE on 2016/10/26 0026.
 */

public class Skill {
    private int id;
    private String hero;
    private String Q;
    private String W;
    private String E;
    private String R;
    private String passive;

    public Skill() {
    }

    public Skill(String hero, String q, String w, String e, String r, String passive) {
        this.hero = hero;
        Q = q;
        W = w;
        E = e;
        R = r;
        this.passive = passive;
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

    public String getQ() {
        return Q;
    }

    public void setQ(String q) {
        Q = q;
    }

    public String getW() {
        return W;
    }

    public void setW(String w) {
        W = w;
    }

    public String getE() {
        return E;
    }

    public void setE(String e) {
        E = e;
    }

    public String getR() {
        return R;
    }

    public void setR(String r) {
        R = r;
    }

    public String getPassive() {
        return passive;
    }

    public void setPassive(String passive) {
        this.passive = passive;
    }
}
