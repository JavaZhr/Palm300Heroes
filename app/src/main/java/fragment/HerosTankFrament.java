package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.nicolite.palm300heros.R;

/**
 * Created by NICOLITE on 2016/10/30 0030.
 */

public class HerosTankFrament extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.heros_detail_fragment, container, false);
        return view;
    }
}
