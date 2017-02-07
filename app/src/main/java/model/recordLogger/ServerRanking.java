package model.recordLogger;

import java.io.Serializable;

/**
 * Created by NICOLITE on 2017/2/6 0006.
 */

public class ServerRanking implements Serializable {
    private String clickUrl;
    private String[] infoR;

    public String getClickUrl() {
        return clickUrl;
    }

    public void setClickUrl(String clickUrl) {
        this.clickUrl = clickUrl;
    }

    public String[] getInfoR() {
        return infoR;
    }

    public void setInfoR(String[] infoR) {
        this.infoR = infoR;
    }
}