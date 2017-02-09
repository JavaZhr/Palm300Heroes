package fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.pgyersdk.feedback.PgyFeedback;
import com.pgyersdk.update.PgyUpdateManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.nicolite.palm300heroes.R;


/**
 * Created by NICOLITE on 2016/10/11 0011.
 */

public class AboutFragment extends Fragment implements View.OnClickListener {
   @BindView(R.id.feedback) TextView feedback;
   @BindView(R.id.update) TextView update;
    private Unbinder unbinder;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.equipment_fragment, container, false);
        View view = inflater.inflate(R.layout.about_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}