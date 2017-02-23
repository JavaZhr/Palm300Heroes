package activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import adapter.FragAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.nicolite.palm300heroes.R;
import fragment.Heroes.HeroesDetail.HeroesDataFragment;
import fragment.Heroes.HeroesDetail.HeroesSkillFragment;
import fragment.Heroes.HeroesDetail.HeroesSkinFragment;
import fragment.Heroes.HeroesDetail.HeroesSoundFragment;
import model.hero.Heroes;
import utilty.LogUtil;

/**
 * Created by NICOLITE on 2016/11/4 0004.
 */

public class HeroesDetailActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private Heroes heroes;
    @BindView(R.id.heroes_detail_name) TextView heroesDetailName;
    @BindView(R.id.heroes_detail_type) TextView heroesDetailType;

    @BindView(R.id.heroes_detail_data) TextView heroesDataButton;
    @BindView(R.id.heroes_detail_skill)TextView heroesSkillButton;
    @BindView(R.id.heroes_detail_skin) TextView heroesSkinButton;
    @BindView(R.id.heroes_detail_sound)TextView heroesSoundButton;

    @BindView(R.id.heroes_detail_viewpager) ViewPager viewPager;
    private int selectedPositon; //用来记住导航的位置

    List<Fragment> fragments = new ArrayList<>();
    private HeroesDataFragment heroesDataFragment;
    private HeroesSkillFragment heroesSkillFragment;
    private HeroesSkinFragment heroesSkinFragment;
    private HeroesSoundFragment heroesSoundFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);

            actionBar.setDisplayHomeAsUpEnabled(true);

            actionBar.setDisplayShowHomeEnabled(true);

            actionBar.setDisplayShowTitleEnabled(false);
        }
        //透明ActionBar
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));

        setContentView(R.layout.heroes_detail_activity);

        ButterKnife.bind(this);

        heroes = (Heroes) getIntent().getSerializableExtra("heroes_data");
        LogUtil.d("heroesDetailActivity", heroes.getName());

        heroesDetailName.setText(heroes.getName());
        heroesDetailType.setText(heroes.getType());
        heroesDataButton.setOnClickListener(this);
        heroesSkillButton.setOnClickListener(this);
        heroesSkinButton.setOnClickListener(this);
        heroesSoundButton.setOnClickListener(this);

        FragAdapter adapter = new FragAdapter(getSupportFragmentManager(), getFragments());
        viewPager.setAdapter(adapter);
        //默认选择第一个，改变第一个fragment的状态
        heroesDataButton.setTextColor(getResources().getColor(R.color.orange));
        viewPager.addOnPageChangeListener(this);
    }

    private List<Fragment> getFragments() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        /*开启一个Fragment事务*/
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        fragments.clear();
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

        fragments.add(heroesDataFragment);
        fragments.add(heroesSkillFragment);
        fragments.add(heroesSkinFragment);
        fragments.add(heroesSoundFragment);

        return fragments;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onClick(View v) {
        resetButtonColor();
        switch (v.getId()) {
            case R.id.heroes_detail_data :
                heroesDataButton.setTextColor(getResources().getColor(R.color.orange));
                selectedPositon = 0;
                break;
            case R.id.heroes_detail_skill :
                heroesSkillButton.setTextColor(getResources().getColor(R.color.orange));
                selectedPositon = 1;
                break;
            case R.id.heroes_detail_skin:
                heroesSkinButton.setTextColor(getResources().getColor(R.color.orange));
                selectedPositon = 2;
                break;
            case R.id.heroes_detail_sound :
                heroesSoundButton.setTextColor(getResources().getColor(R.color.orange));
                selectedPositon = 3;
                break;
            default: break;
        }
        viewPager.setCurrentItem(selectedPositon);
    }

    private void resetButtonColor() {
        heroesDataButton.setTextColor(getResources().getColor(R.color.white));
        heroesSkillButton.setTextColor(getResources().getColor(R.color.white));
        heroesSkinButton.setTextColor(getResources().getColor(R.color.white));
        heroesSoundButton.setTextColor(getResources().getColor(R.color.white));
    }

    /**
     * viewpager
     * @param position
     * @param positionOffset
     * @param positionOffsetPixels
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        resetButtonColor();
        switch (position) {
            case 0 :
                heroesDataButton.setTextColor(getResources().getColor(R.color.orange));
                break;
            case 1:
                heroesSkillButton.setTextColor(getResources().getColor(R.color.orange));
                break;
            case 2:
                heroesSkinButton.setTextColor(getResources().getColor(R.color.orange));
                break;
            case 3:
                heroesSoundButton.setTextColor(getResources().getColor(R.color.orange));
                break;
            default: break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
