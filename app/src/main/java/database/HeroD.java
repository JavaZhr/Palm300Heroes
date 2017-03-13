package database;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by NICOLITE on 2017/3/10 0010.
 */

public class HeroD extends DataSupport implements Serializable{
    @Column(unique = true)
    private String heroName;
    private String heroType;
    @Column(unique = true)
    private String UNCode;
    private String background;
    private String avatarUrl;
    private String pictureUrl;
    private String coinsPrice;
    private String diamondPrice;

    private String healthValue;
    private String magicPointValue;
    private String physicalAttackValue;
    private String magicAttackValue;
    private String physicalDefenseValue;
    private String magicDefenseValue ;
    private String critValue;
    private String attackSpeedValue;
    private String attackRangeValue;
    private String movementSpeedValue;

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    public String getHeroType() {
        return heroType;
    }

    public void setHeroType(String heroType) {
        this.heroType = heroType;
    }

    public String getUNCode() {
        return UNCode;
    }

    public void setUNCode(String UNCode) {
        this.UNCode = UNCode;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
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

    public String getHealthValue() {
        return healthValue;
    }

    public void setHealthValue(String healthValue) {
        this.healthValue = healthValue;
    }

    public String getMagicPointValue() {
        return magicPointValue;
    }

    public void setMagicPointValue(String magicPointValue) {
        this.magicPointValue = magicPointValue;
    }

    public String getPhysicalAttackValue() {
        return physicalAttackValue;
    }

    public void setPhysicalAttackValue(String physicalAttackValue) {
        this.physicalAttackValue = physicalAttackValue;
    }

    public String getMagicAttackValue() {
        return magicAttackValue;
    }

    public void setMagicAttackValue(String magicAttackValue) {
        this.magicAttackValue = magicAttackValue;
    }

    public String getPhysicalDefenseValue() {
        return physicalDefenseValue;
    }

    public void setPhysicalDefenseValue(String physicalDefenseValue) {
        this.physicalDefenseValue = physicalDefenseValue;
    }

    public String getMagicDefenseValue() {
        return magicDefenseValue;
    }

    public void setMagicDefenseValue(String magicDefenseValue) {
        this.magicDefenseValue = magicDefenseValue;
    }

    public String getCritValue() {
        return critValue;
    }

    public void setCritValue(String critValue) {
        this.critValue = critValue;
    }

    public String getAttackSpeedValue() {
        return attackSpeedValue;
    }

    public void setAttackSpeedValue(String attackSpeedValue) {
        this.attackSpeedValue = attackSpeedValue;
    }

    public String getAttackRangeValue() {
        return attackRangeValue;
    }

    public void setAttackRangeValue(String attackRangeValue) {
        this.attackRangeValue = attackRangeValue;
    }

    public String getMovementSpeedValue() {
        return movementSpeedValue;
    }

    public void setMovementSpeedValue(String movementSpeedValue) {
        this.movementSpeedValue = movementSpeedValue;
    }
}
