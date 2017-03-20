package database;

import org.litepal.crud.DataSupport;

import model.FightSkill;
import model.hero.Hero;
import model.hero.Skill;
import model.hero.Skin;
import model.hero.Sound;

/**
 * Created by NICOLITE on 2017/3/11 0011.
 */

public class DBUtil {
    public static void saveHero(Hero hero) {
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
            if (DataSupport.isExist(HeroD.class, "heroname = ?", heroD.getHeroName())){
                heroD.saveOrUpdate("heroname = ?", heroD.getHeroName());
            }else {
                heroD.save();
            }
        }
    }

    public static void saveSkill(Skill skill) {
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
                if (DataSupport.isExist(SkillD.class, "pictureurl = ?", skillD.getPictureUrl())){
                    skillD.saveOrUpdate("pictureurl = ?", skillD.getPictureUrl());
                }else {
                    skillD.save();
                }
            }
        }
    }

    public static void saveSkin(Skin skin) {
        for (int i = 0; i < skin.skinInfoList.size(); i++) {
            for (int j=0 ;j<skin.skinInfoList.get(i).skinItemList.size();j++){
                SkinD skinD=new SkinD();
                skinD.setHero(skin.skinInfoList.get(i).hero);
                skinD.setUNCode(skin.skinInfoList.get(i).skinItemList.get(j).UNCode);
                skinD.setUrl(skin.skinInfoList.get(i).skinItemList.get(j).url);
                skinD.setName(skin.skinInfoList.get(i).skinItemList.get(j).name);
                skinD.setPrice(skin.skinInfoList.get(i).skinItemList.get(j).price);
                if (DataSupport.isExist(SkinD.class, "uncode = ?", skinD.getUNCode())){
                    skinD.saveOrUpdate("uncode = ?", skinD.getUNCode());
                }else {
                    skinD.save();
                }
            }
        }
    }

    public static void saveSound(Sound sound){
        for (int i = 0;i < sound.soundInfoList.size();i++){
            for (int j=0;j<sound.soundInfoList.get(i).soundItemList.size();j++){
                SoundD soundD = new SoundD();
                soundD.setHero(sound.soundInfoList.get(i).hero);
                soundD.setUrl(sound.soundInfoList.get(i).soundItemList.get(j).url);
                soundD.setContent(sound.soundInfoList.get(i).soundItemList.get(j).content);
                if (DataSupport.isExist(SoundD.class, "url = ?", soundD.getUrl())){
                    soundD.saveOrUpdate("url = ?", soundD.getUrl());
                }else {
                    soundD.save();
                }
            }
        }
    }

    public static void saveFightSkill(FightSkill fightSkill){
        for (int i = 0; i < fightSkill.fightSkillInfoList.size(); i++){
            FightSkillD fightSkillD = new FightSkillD();
            fightSkillD.setName(fightSkill.fightSkillInfoList.get(i).name);
            fightSkillD.setPicture(fightSkill.fightSkillInfoList.get(i).picture);
            fightSkillD.setContent(fightSkill.fightSkillInfoList.get(i).content);
            if (DataSupport.isExist(FightSkillD.class, "name = ?", fightSkillD.getName())){
                fightSkillD.saveOrUpdate("name = ?", fightSkillD.getName());
            }else {
                fightSkillD.save();
            }
        }
    }
}
