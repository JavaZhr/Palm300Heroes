package model;

import java.io.Serializable;

/**
 * Created by NICOLITE on 2016/10/15 0015.
 */

public class News implements Serializable{
    private int id;
    private String category;
    private String url;
    private String title;
    private String content;
    private String date;
    private String nickName;
    private String imageUrl;

    public News() {
    }

    public News(String category, String url, String title, String content, String date, String nickName, String imageUrl) {
        this.category = category;
        this.url = url;
        this.title = title;
        this.content = content;
        this.date = date;
        this.nickName = nickName;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
