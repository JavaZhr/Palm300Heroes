package cn.nicolite.palm300heroes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import adapter.FragAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import database.HeroD;
import fragment.Heroes.HeroesDetail.HeroesDataFragment;
import fragment.Heroes.HeroesDetail.HeroesSkillFragment;
import fragment.Heroes.HeroesDetail.HeroesSkinFragment;
import fragment.Heroes.HeroesDetail.HeroesSoundFragment;
import util.LogUtil;

/**
 * Created by NICOLITE on 2016/11/4 0004.
 */

public class HeroesDetailActivity extends BaseActivity {
    @BindView(R.id.heroes_detail_name)
    TextView heroesDetailName;
    @BindView(R.id.heroes_detail_type)
    TextView heroesDetailType;
    @BindView(R.id.heroes_detail_viewpager)
    ViewPager viewPager;
    @BindView(R.id.heroes_detail_tab)
    TabLayout tabLayout;
    @BindView(R.id.heroes_detail_back_button)
    Button backButton;
    List<Fragment> fragmentList = new ArrayList<>();
    List<String> typeList = new ArrayList<>();
    private HeroesDataFragment heroesDataFragment;
    private HeroesSkillFragment heroesSkillFragment;
    private HeroesSkinFragment heroesSkinFragment;
    private HeroesSoundFragment heroesSoundFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.heroes_detail_activity);
        ButterKnife.bind(this);
        HeroD heroes = (HeroD) getIntent().getSerializableExtra("heroes_data");
        LogUtil.d("heroesDetailActivity", heroes.getHeroName());
        heroesDetailName.setText(heroes.getHeroName());
        heroesDetailType.setText(heroes.getHeroType());
        FragAdapter adapter = new FragAdapter(getSupportFragmentManager(), getFragmentList(), getTypeList());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private List<Fragment> getFragmentList() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        /*开启一个Fragment事务*/
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        fragmentList.clear();
        if (heroesDataFragment == null) {
            heroesDataFragment = new HeroesDataFragment();
        }
        if (heroesSkillFragment == null) {
            heroesSkillFragment = new HeroesSkillFragment();
        }
        if (heroesSkinFragment == null) {
            heroesSkinFragment = new HeroesSkinFragment();
        }
        if (heroesSoundFragment == null) {
            heroesSoundFragment = new HeroesSoundFragment();
        }
        transaction.commit();
        fragmentList.add(heroesDataFragment);
        fragmentList.add(heroesSkillFragment);
        fragmentList.add(heroesSkinFragment);
        fragmentList.add(heroesSoundFragment);
        return fragmentList;
    }

    private List<String> getTypeList(){
        typeList.clear();
        typeList.add("数据");
        typeList.add("技能");
        typeList.add("皮肤");
        typeList.add("语音");
        return typeList;
    }
}
