package database;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by NICOLITE on 2017/3/19 0019.
 */

public class FightSkillD extends DataSupport {
    @Column(unique = true)
    private String name;
    private String picture;
    private String content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
