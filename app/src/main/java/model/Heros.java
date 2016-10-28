package model;

/**
 * Created by NICOLITE on 2016/10/26 0026.
 */

public class Heros {
    private int id;
    private String name;
    private String type;
    private String background;
    private String data;

    public Heros() {
    }

    public Heros(String type, String name, String background, String data) {
        this.type = type;
        this.name = name;
        this.background = background;
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
