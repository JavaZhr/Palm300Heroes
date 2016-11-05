package model;

import java.io.Serializable;

/**
 * Created by NICOLITE on 2016/10/26 0026.
 */

public class Heros implements Serializable{
    private int id;
    private String name;
    private String type;
    private String background;
    private String avatarUrl;
    private String coinsPrice;
    private String diamondPrice;

    public Heros() {
    }

    public Heros(String name, String type, String background, String avatarUrl, String heros_coins_price, String heros_diamond_prive) {
        this.name = name;
        this.type = type;
        this.background = background;
        this.avatarUrl = avatarUrl;
        this.coinsPrice = heros_coins_price;
        this.diamondPrice = heros_diamond_prive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getCoinsPrice() {
        return coinsPrice;
    }

    public void setCoinsPrice(String coinsPrice) {
        this.coinsPrice = coinsPrice;
    }

    public String getDiamondPrice() {
        return diamondPrice;
    }

    public void setDiamondPrice(String diamondPrice) {
        this.diamondPrice = diamondPrice;
    }
}
