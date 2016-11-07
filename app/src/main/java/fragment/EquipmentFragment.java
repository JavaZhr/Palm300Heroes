package fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.nicolite.palm300heroes.R;


/**
 * Created by NICOLITE on 2016/10/11 0011.
 */

public class EquipmentFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.equipment_fragment, container, false);
        return view;
    }
}
