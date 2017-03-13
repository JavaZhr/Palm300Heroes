package model.hero;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by NICOLITE on 2017/3/10 0010.
 */

public class Sound {
    public String status;
    @SerializedName("info")
    public List<SoundInfo> soundInfoList;

    public class SoundInfo {
        public String hero;
        @SerializedName("sound")
        public List<SoundItem> soundItemList;

        public class SoundItem {
            public String url;
            public String content;
        }
    }
}
