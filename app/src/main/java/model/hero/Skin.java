package model.hero;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by NICOLITE on 2017/3/10 0010.
 */

public class Skin {
    public String status;
    @SerializedName("info")
    public List<SkinInfo> skinInfoList;

    public class SkinInfo {
        public String hero;
        @SerializedName("skin")
        public List<SkinItem> skinItemList;

        public class SkinItem {
            public String UNCode;
            public String url;
            public String name;
            public String price;
        }
    }
}
