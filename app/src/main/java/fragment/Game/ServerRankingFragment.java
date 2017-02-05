package fragment.Game;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.nicolite.palm300heroes.R;

/**
 * Created by NICOLITE on 2017/2/5 0005.
 */

public class ServerRankingFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.server_ranking_layout, container, false);
        return view;
    }

}
