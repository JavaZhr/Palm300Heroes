package fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.nicolite.palm300heroes.R;
import model.Heroes;

/**
 * Created by NICOLITE on 2016/11/7 0007.
 */

public class HeroesDataFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private TextView heroesHealthValue;
    private TextView heroesMagicPointValue;
    private TextView heroesPhysicalAttackValue;
    private TextView heroesMagicAttackValue;
    private TextView heroesPhysicalDefenseValue;
    private TextView heroesMagicDefenseValue;
    private TextView heroesCritValue;
    private TextView heroesAttackSpeedValue;
    private TextView heroesAttackRangeValue;
    private TextView heroesMovementSpeedValue;
    private TextView heroesBackground;

    private Heroes heroes;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final int REFRESH_COMPLETE_TIME = 2000;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0 :
                    swipeRefreshLayout.setRefreshing(false);
                    initView();
                    break;
                default:break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.heroes_detail_data_fragment, container, false);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.heroes_detail_data_wipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.orange));
        swipeRefreshLayout.setOnRefreshListener(this);

        heroesHealthValue = (TextView) view.findViewById(R.id.heroes_health_value);
        heroesMagicPointValue = (TextView) view.findViewById(R.id.heroes_magic_point_value);
        heroesPhysicalAttackValue = (TextView) view.findViewById(R.id.heroes_physical_attack_value);
        heroesMagicAttackValue = (TextView) view.findViewById(R.id.heroes_magic_attack_value);
        heroesPhysicalDefenseValue = (TextView) view.findViewById(R.id.heroes_physical_defense_value);
        heroesMagicDefenseValue = (TextView) view.findViewById(R.id.heroes_magic_defense_value);
        heroesCritValue = (TextView) view.findViewById(R.id.heroes_crit_value);
        heroesAttackSpeedValue = (TextView) view.findViewById(R.id.heroes_attack_speed_value);
        heroesAttackRangeValue = (TextView) view.findViewById(R.id.heroes_attack_range_value);
        heroesMovementSpeedValue = (TextView) view.findViewById(R.id.heroes_movement_speed_value);
        heroesBackground = (TextView) view.findViewById(R.id.heroes_background);

        initView();
        return view;
    }

    private void initView() {
        heroes = (Heroes) getActivity().getIntent().getSerializableExtra("heroes_data");

        heroesHealthValue.setText("生命值：" + heroes.getHealthValue());
        heroesMagicPointValue.setText("魔法值：" + heroes.getMagicPointValue());
        heroesPhysicalAttackValue.setText("物理攻击：" + heroes.getPhysicalAttackValue());
        heroesMagicAttackValue.setText("法术强度：" + heroes.getMagicAttackValue());
        heroesPhysicalDefenseValue.setText("物理防御：" + heroes.getPhysicalDefenseValue());
        heroesMagicDefenseValue.setText("魔法抗性：" + heroes.getMagicDefenseValue());
        heroesCritValue.setText("暴击：" + heroes.getCritValue());
        heroesAttackSpeedValue.setText("攻击速度：" + heroes.getAttackSpeedValue());
        heroesAttackRangeValue.setText("攻击范围：" + heroes.getAttackRangeValue());
        heroesMovementSpeedValue.setText("移动速度：" + heroes.getMovementSpeedValue());
        heroesBackground.setText(heroes.getBackground());
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                handler.sendEmptyMessageDelayed(0, REFRESH_COMPLETE_TIME);
            }
        }).start();
    }
}
