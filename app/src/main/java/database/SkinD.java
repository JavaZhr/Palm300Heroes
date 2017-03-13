package database;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by NICOLITE on 2017/3/10 0010.
 */

public class SkinD extends DataSupport{
    @Column(nullable = false)
    private String hero;
    @Column(unique = true)
    private String UNCode;
    private String url;
    private String name;
    private String price;

    public String getHero() {
        return hero;
    }

    public void setHero(String hero) {
        this.hero = hero;
    }

    public String getUNCode() {
        return UNCode;
    }

    public void setUNCode(String UNCode) {
        this.UNCode = UNCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
