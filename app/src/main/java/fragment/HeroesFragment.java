package fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import adapter.FragAdapter;
import cn.nicolite.palm300heroes.R;
import fragment.Heroes.HeroesAllFragment;
import fragment.Heroes.HeroesAssassinFragment;
import fragment.Heroes.HeroesAssistFragment;
import fragment.Heroes.HeroesMasterFragment;
import fragment.Heroes.HeroesShooterFragment;
import fragment.Heroes.HeroesTankFragment;
import fragment.Heroes.HeroesWarriorFragment;


/**
 * Created by NICOLITE on 2016/10/11 0011.
 */

public class HeroesFragment extends Fragment implements View.OnClickListener, ViewPager.OnPageChangeListener{
    private Button buttonAll;
    private Button buttonAssassin;
    private Button buttonWarrior;
    private Button buttonTank;
    private Button buttonShooter;
    private Button buttonMaster;
    private Button buttonAssist;
    private ViewPager viewPager;
    private int selectedPositon; //记录Fragment

    List<Fragment> fragments=new ArrayList<>();
    private HeroesAllFragment heroesAllFragment;
    private HeroesAssassinFragment heroesAssassinFragment;
    private HeroesWarriorFragment heroesWarriorFragment;
    private HeroesTankFragment heroesTankFrament;
    private HeroesShooterFragment heroesShooterFragment;
    private HeroesMasterFragment heroesMasterFragment;
    private HeroesAssistFragment heroesAssistFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            FragmentManager manager = getChildFragmentManager();
            manager.popBackStackImmediate(null, 1);
        }

        View view = inflater.inflate(R.layout.heroes_fragment, container, false);
        buttonAll = (Button) view.findViewById(R.id.all_heroes);
        buttonAssassin = (Button) view.findViewById(R.id.assassin_heroes);
        buttonWarrior = (Button) view.findViewById(R.id.warrior_heroes);
        buttonTank = (Button) view.findViewById(R.id.tank_heroes);
        buttonShooter = (Button) view.findViewById(R.id.shooter_heroes);
        buttonMaster = (Button) view.findViewById(R.id.master_heroes);
        buttonAssist = (Button) view.findViewById(R.id.assist_heroes);

        buttonAll.setOnClickListener(this);
        buttonAssassin.setOnClickListener(this);
        buttonWarrior.setOnClickListener(this);
        buttonTank.setOnClickListener(this);
        buttonShooter.setOnClickListener(this);
        buttonMaster.setOnClickListener(this);
        buttonAssist.setOnClickListener(this);

        FragAdapter adapter = new FragAdapter(getChildFragmentManager(), getFragments());
        viewPager = (ViewPager)view.findViewById(R.id.heroes_viewpager);
        viewPager.setAdapter(adapter);
        //默认选择第一个，改变第一个fragment的状态
        buttonAll.setTextColor(getResources().getColor(R.color.orange));
        viewPager.addOnPageChangeListener(this);

        return view;
    }


    private List<Fragment> getFragments() {
        FragmentManager fragmentManager = getChildFragmentManager();

        /*开启一个Fragment事务*/
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        fragments.clear();
        if (heroesAllFragment == null) {
           heroesAllFragment = new HeroesAllFragment();
        }
        if (heroesAssassinFragment == null) {
            heroesAssassinFragment = new HeroesAssassinFragment();
        }
        if (heroesWarriorFragment == null) {
            heroesWarriorFragment = new HeroesWarriorFragment();
        }
        if (heroesTankFrament == null) {
            heroesTankFrament = new HeroesTankFragment();
        }
        if (heroesShooterFragment == null) {
            heroesShooterFragment = new HeroesShooterFragment();
        }
        if (heroesMasterFragment == null) {
            heroesMasterFragment = new HeroesMasterFragment();
        }
        if (heroesAssistFragment == null) {
            heroesAssistFragment = new HeroesAssistFragment();
        }
        transaction.commit();

        fragments.add(heroesAllFragment);
        fragments.add(heroesAssassinFragment);
        fragments.add(heroesWarriorFragment);
        fragments.add(heroesTankFrament);
        fragments.add(heroesShooterFragment);
        fragments.add(heroesMasterFragment);
        fragments.add(heroesAssistFragment);

        return fragments;
    }

    /**
     * 按钮点击监听器
     * @param v
     */
    @Override
    public void onClick(View v) {
        resetButtonColor();
        switch (v.getId()) {
            case R.id.all_heroes :
                buttonAll.setTextColor(getResources().getColor(R.color.orange));
                selectedPositon = 0;
                break;
            case R.id.assassin_heroes :
                buttonAssassin.setTextColor(getResources().getColor(R.color.orange));
                selectedPositon = 1;
                break;
            case R.id.warrior_heroes :
                buttonWarrior.setTextColor(getResources().getColor(R.color.orange));
                selectedPositon = 2;
                break;
            case R.id.tank_heroes :
                buttonTank.setTextColor(getResources().getColor(R.color.orange));
                selectedPositon = 3;
                break;
            case R.id.shooter_heroes :
                buttonShooter.setTextColor(getResources().getColor(R.color.orange));
                selectedPositon = 4;
                break;
            case R.id.master_heroes :
                buttonMaster.setTextColor(getResources().getColor(R.color.orange));
                selectedPositon = 5;
                break;
            case R.id.assist_heroes :
                buttonAssist.setTextColor(getResources().getColor(R.color.orange));
                selectedPositon = 6;
                break;
            default: break;
        }
        viewPager.setCurrentItem(selectedPositon);
    }

    private void resetButtonColor() {
        buttonAll.setTextColor(getResources().getColor(R.color.white));
        buttonAssassin.setTextColor(getResources().getColor(R.color.white));
        buttonWarrior.setTextColor(getResources().getColor(R.color.white));
        buttonTank.setTextColor(getResources().getColor(R.color.white));
        buttonShooter.setTextColor(getResources().getColor(R.color.white));
        buttonMaster.setTextColor(getResources().getColor(R.color.white));
        buttonAssist.setTextColor(getResources().getColor(R.color.white));
    }

    /**
     * viewPager活动监听
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
            case 0:
                buttonAll.setTextColor(getResources().getColor(R.color.orange));
                break;
            case 1:
                buttonAssassin.setTextColor(getResources().getColor(R.color.orange));
                break;
            case 2:
                buttonWarrior.setTextColor(getResources().getColor(R.color.orange));
                break;
            case 3:
                buttonTank.setTextColor(getResources().getColor(R.color.orange));
                break;
            case 4:
                buttonShooter.setTextColor(getResources().getColor(R.color.orange));
                break;
            case 5:
                buttonMaster.setTextColor(getResources().getColor(R.color.orange));
                break;
            case 6:
                buttonAssist.setTextColor(getResources().getColor(R.color.orange));
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
