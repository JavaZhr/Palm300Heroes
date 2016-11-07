package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.nicolite.palm300heroes.R;
import model.Heroes;

/**
 * Created by NICOLITE on 2016/11/7 0007.
 */

public class HeroesDataFragment extends Fragment {
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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.heroes_detail_data_fragment, container, false);
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
        Intent intent = new Intent();
        heroes = (Heroes) intent.getSerializableExtra("heroes_data");

        heroesHealthValue.setText(heroes.getHealthValue());
        heroesMagicPointValue.setText(heroes.getMagicPointValue());
        heroesPhysicalAttackValue.setText(heroes.getPhysicalAttackValue());
        heroesMagicAttackValue.setText(heroes.getMagicAttackValue());
        heroesPhysicalDefenseValue.setText(heroes.getPhysicalDefenseValue());
        heroesMagicDefenseValue.setText(heroes.getMagicDefenseValue());
        heroesCritValue.setText(heroes.getCritValue());
        heroesAttackSpeedValue.setText(heroes.getAttackSpeedValue());
        heroesAttackRangeValue.setText(heroes.getAttackRangeValue());
        heroesMovementSpeedValue.setText(heroes.getMovementSpeedValue());
        heroesBackground.setText(heroes.getBackground());
    }
}
