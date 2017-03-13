package fragment.Heroes.HeroesDetail;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.nicolite.palm300heroes.R;
import database.HeroD;

/**
 * Created by NICOLITE on 2016/11/7 0007.
 */

public class HeroesDataFragment extends Fragment{
    @BindView(R.id.heroes_health_value) TextView heroesHealthValue;
    @BindView(R.id.heroes_magic_point_value) TextView heroesMagicPointValue;
    @BindView(R.id.heroes_physical_attack_value) TextView heroesPhysicalAttackValue;
    @BindView(R.id.heroes_magic_attack_value) TextView heroesMagicAttackValue;
    @BindView(R.id.heroes_physical_defense_value) TextView heroesPhysicalDefenseValue;
    @BindView(R.id.heroes_magic_defense_value) TextView heroesMagicDefenseValue;
    @BindView(R.id.heroes_crit_value) TextView heroesCritValue;
    @BindView(R.id.heroes_attack_speed_value) TextView heroesAttackSpeedValue;
    @BindView(R.id.heroes_attack_range_value) TextView heroesAttackRangeValue;
    @BindView(R.id.heroes_movement_speed_value) TextView heroesMovementSpeedValue;
    @BindView(R.id.heroes_background) TextView heroesBackground;
    @BindView(R.id.heroes_pic) ImageView heroesImageView;

    private Unbinder unbinder;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.heroes_detail_data_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
       HeroD heroes = (HeroD) getActivity().getIntent().getSerializableExtra("heroes_data");

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

        String pictureUrl = "http://ogbna06ji.bkt.clouddn.com/heroes/picture/" + Uri.encode(heroes.getPictureUrl());
        if (TextUtils.isEmpty(heroes.getPictureUrl())) {
            pictureUrl = "http://ogbna06ji.bkt.clouddn.com/heroes/picture/undefined.jpg";
        }
        Glide
                .with(getActivity())
                .load(pictureUrl)
                .skipMemoryCache(true)
                .dontAnimate()
                .into(heroesImageView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
