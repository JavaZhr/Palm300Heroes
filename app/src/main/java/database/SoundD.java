package database;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by NICOLITE on 2017/3/10 0010.
 */

public class SoundD extends DataSupport {
    @Column(nullable = false)
    private String hero;
    @Column(unique = true)
    private String url;
    private String content;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
