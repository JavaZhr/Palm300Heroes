package database;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by NICOLITE on 2017/3/11 0011.
 */

public class SearchHistoryD extends DataSupport{
    @Column(unique = true)
    private String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
