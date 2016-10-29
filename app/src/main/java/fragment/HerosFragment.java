package fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import adapter.FragAdapter;
import cn.nicolite.palm300heros.R;



/**
 * Created by NICOLITE on 2016/10/11 0011.
 */

public class HerosFragment extends Fragment implements View.OnClickListener{
    private Button buttonAll;
    private Button buttonAssassin;
    private Button buttonWarrior;
    private Button buttonTank;
    private Button buttonShooter;
    private Button buttonMaster;
    private Button buttonAssist;

    private ViewPager viewPager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.heros_fragment, container, false);
        buttonAll = (Button) view.findViewById(R.id.all_heros);
        buttonAssassin = (Button) view.findViewById(R.id.assassin_heros);
        buttonWarrior = (Button) view.findViewById(R.id.warrior_heros);
        buttonTank = (Button) view.findViewById(R.id.tank_heros);
        buttonShooter = (Button) view.findViewById(R.id.shooter_heros);
        buttonMaster = (Button) view.findViewById(R.id.master_heros);
        buttonAssist = (Button) view.findViewById(R.id.assist_heros);

        buttonAll.setOnClickListener(this);
        buttonAssassin.setOnClickListener(this);
        buttonWarrior.setOnClickListener(this);
        buttonTank.setOnClickListener(this);
        buttonShooter.setOnClickListener(this);
        buttonMaster.setOnClickListener(this);
        buttonAssist.setOnClickListener(this);
       List<Fragment> fragments=new ArrayList<Fragment>();
        fragments.add(new HerosAllFragment());
        fragments.add(new HerosAssassinFragment());
        fragments.add(new HerosWarriorFragment());
        fragments.add(new HerosTankFrament());
        fragments.add(new HerosShooterFragment());
        fragments.add(new HerosMasterFragment());
        fragments.add(new HerosAssistFragment());
        FragAdapter adapter = new FragAdapter(getChildFragmentManager(), fragments);
        viewPager = (ViewPager)view.findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        //默认选择第一个，改变第一个fragment的状态
        buttonAll.setTextColor(getResources().getColor(R.color.orange));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                resetButtonColor();
                switch (position) {
                    case 0 :
                        buttonAll.setTextColor(getResources().getColor(R.color.orange));
                        break;
                    case 1 :
                        buttonAssassin.setTextColor(getResources().getColor(R.color.orange));
                        break;
                    case 2 :
                        buttonWarrior.setTextColor(getResources().getColor(R.color.orange));
                        break;
                    case 3 :
                        buttonTank.setTextColor(getResources().getColor(R.color.orange));
                        break;
                    case 4 :
                        buttonShooter.setTextColor(getResources().getColor(R.color.orange));
                        break;
                    case 5 :
                        buttonMaster.setTextColor(getResources().getColor(R.color.orange));
                        break;
                    case 6 :
                        buttonAssist.setTextColor(getResources().getColor(R.color.orange));
                        break;
                    default: break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {
        resetButtonColor();
        switch (v.getId()) {
            case R.id.all_heros :
                buttonAll.setTextColor(getResources().getColor(R.color.orange));
                break;
            case R.id.assassin_heros :
                buttonAssassin.setTextColor(getResources().getColor(R.color.orange));
                break;
            case R.id.warrior_heros :
                buttonWarrior.setTextColor(getResources().getColor(R.color.orange));
                break;
            case R.id.tank_heros :
                buttonTank.setTextColor(getResources().getColor(R.color.orange));
                break;
            case R.id.shooter_heros :
                buttonShooter.setTextColor(getResources().getColor(R.color.orange));
                break;
            case R.id.master_heros :
                buttonMaster.setTextColor(getResources().getColor(R.color.orange));
                break;
            case R.id.assist_heros :
                buttonAssist.setTextColor(getResources().getColor(R.color.orange));
                break;
            default: break;
        }
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
}
