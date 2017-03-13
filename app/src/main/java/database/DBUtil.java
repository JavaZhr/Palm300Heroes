package database;

import model.hero.Hero;
import model.hero.Skill;
import model.hero.Skin;
import model.hero.Sound;

/**
 * Created by NICOLITE on 2017/3/11 0011.
 */

public class DBUtil {
    public final static String SAVE = "save";
    public final static String UPDATE = "update";
    public static void saveHero(Hero hero, String flags) {
        for (int i = 0; i < hero.heroinfoList.size(); i++) {
            HeroD heroD = new HeroD();
            heroD.setHeroName(hero.heroinfoList.get(i).heroName);
            heroD.setHeroType(hero.heroinfoList.get(i).heroType);
            heroD.setUNCode(hero.heroinfoList.get(i).heroCode);
            heroD.setBackground(hero.heroinfoList.get(i).background);
            heroD.setAvatarUrl(hero.heroinfoList.get(i).avatarUrl);
            heroD.setPictureUrl(hero.heroinfoList.get(i).pictureUrl);
            heroD.setCoinsPrice(hero.heroinfoList.get(i).coinsPrice);
            heroD.setDiamondPrice(hero.heroinfoList.get(i).diamondPrice);

            heroD.setHealthValue(hero.heroinfoList.get(i).baseData.healthValue);
            heroD.setMagicPointValue(hero.heroinfoList.get(i).baseData.magicPointValue);
            heroD.setPhysicalAttackValue(hero.heroinfoList.get(i).baseData.physicalAttackValue);
            heroD.setMagicAttackValue(hero.heroinfoList.get(i).baseData.magicAttackValue);
            heroD.setPhysicalDefenseValue(hero.heroinfoList.get(i).baseData.physicalDefenseValue);
            heroD.setMagicDefenseValue(hero.heroinfoList.get(i).baseData.magicDefenseValue);
            heroD.setCritValue(hero.heroinfoList.get(i).baseData.critValue);
            heroD.setAttackSpeedValue(hero.heroinfoList.get(i).baseData.attackSpeedValue);
            heroD.setAttackRangeValue(hero.heroinfoList.get(i).baseData.attackRangeValue);
            heroD.setMovementSpeedValue(hero.heroinfoList.get(i).baseData.movementSpeedValue);
            if (flags.equals(SAVE)) {
                heroD.save();
            }else if (flags.equals(UPDATE)){
                heroD.saveOrUpdate("heroname = ?", heroD.getHeroName());
            }
        }
    }

    public static void saveSkill(Skill skill, String flags) {
        for (int i = 0; i < skill.skillInfoList.size(); i++) {
            for (int j = 0; j < skill.skillInfoList.get(i).skillItemList.size(); j++) {
                SkillD skillD = new SkillD();
                skillD.setHero(skill.skillInfoList.get(i).hero);
                skillD.setPictureUrl(skill.skillInfoList.get(i).skillItemList.get(j).pictureUrl);
                skillD.setName(skill.skillInfoList.get(i).skillItemList.get(j).name);
                skillD.setConsumption(skill.skillInfoList.get(i).skillItemList.get(j).consumption);
                skillD.setChilldown(skill.skillInfoList.get(i).skillItemList.get(j).chilldown);
                skillD.setShortcut(skill.skillInfoList.get(i).skillItemList.get(j).shortcut);
                skillD.setEffectiveness(skill.skillInfoList.get(i).skillItemList.get(j).effectiveness);
                if (flags.equals(SAVE)) {
                    skillD.save();
                }else if (flags.equals(UPDATE)){
                    skillD.saveOrUpdate("pictureurl = ?", skillD.getPictureUrl());
                }
            }
        }
    }

    public static void saveSkin(Skin skin, String flags) {
        for (int i=0;i<skin.skinInfoList.size();i++) {
            for (int j=0 ;j<skin.skinInfoList.get(i).skinItemList.size();j++){
                SkinD skinD=new SkinD();
                skinD.setHero(skin.skinInfoList.get(i).hero);
                skinD.setUNCode(skin.skinInfoList.get(i).skinItemList.get(j).UNCode);
                skinD.setUrl(skin.skinInfoList.get(i).skinItemList.get(j).url);
                skinD.setName(skin.skinInfoList.get(i).skinItemList.get(j).name);
                skinD.setPrice(skin.skinInfoList.get(i).skinItemList.get(j).price);
                if (flags.equals(SAVE)) {
                    skinD.save();
                }else if (flags.equals(UPDATE)){
                    skinD.saveOrUpdate("uncode = ?", skinD.getUNCode());
                }
            }
        }
    }

    public static void saveSound(Sound sound, String flags){
        for (int i=0;i<sound.soundInfoList.size();i++){
            for (int j=0;j<sound.soundInfoList.get(i).soundItemList.size();j++){
                SoundD soundD = new SoundD();
                soundD.setHero(sound.soundInfoList.get(i).hero);
                soundD.setUrl(sound.soundInfoList.get(i).soundItemList.get(j).url);
                soundD.setContent(sound.soundInfoList.get(i).soundItemList.get(j).content);

                if (flags.equals(SAVE)) {
                    soundD.save();
                }else if (flags.equals(UPDATE)){
                    soundD.saveOrUpdate("url = ?", soundD.getUrl());
                }
            }
        }
    }
}
