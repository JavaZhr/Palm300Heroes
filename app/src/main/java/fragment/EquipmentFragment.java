package fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pgyersdk.feedback.PgyFeedback;
import com.pgyersdk.update.PgyUpdateManager;

import cn.nicolite.palm300heroes.R;


/**
 * Created by NICOLITE on 2016/10/11 0011.
 */

public class EquipmentFragment extends Fragment implements View.OnClickListener {
    private TextView feedback;
    private TextView update;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.equipment_fragment, container, false);
        View view = inflater.inflate(R.layout.about, container, false);

        feedback = (TextView) view.findViewById(R.id.feedback);
        update = (TextView) view.findViewById(R.id.update);

        feedback.setOnClickListener(this);
        update.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.feedback :
                PgyFeedback.getInstance().showDialog(getActivity());
                break;
            case R.id.update :
                PgyUpdateManager.register(getActivity());
        }
    }
}