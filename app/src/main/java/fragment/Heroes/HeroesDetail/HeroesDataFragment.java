package fragment.Heroes.HeroesDetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import cn.nicolite.palm300heroes.R;
import model.hero.Heroes;
import utilty.LogUtil;

/**
 * Created by NICOLITE on 2016/11/7 0007.
 */

public class HeroesDataFragment extends Fragment{
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

    private ImageView heroesImageView;

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
        heroesImageView = (ImageView) view.findViewById(R.id.heroes_pic);
        initView();
        return view;
    }

    private void initView() {
       Heroes heroes = (Heroes) getActivity().getIntent().getSerializableExtra("heroes_data");

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

        LogUtil.d("heroes_pic", heroes.getPictureUrl());
        if (!heroes.getPictureUrl().equals("")) {
            Glide
                    .with(getActivity())
                    .load(heroes.getPictureUrl())
                    .thumbnail(0.1f)
                    .skipMemoryCache(true)
                    .dontAnimate()
                    .into(heroesImageView);
        }

    }
}
