package fragment.Heroes.HeroesDetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.nicolite.palm300heroes.R;

/**
 * Created by NICOLITE on 2016/11/7 0007.
 */

public class HeroesSoundFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R. layout.activity_main, container, false);
        return view;
    }
}
