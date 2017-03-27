package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by NICOLITE on 2016/10/28 0028.
 */

public class FragAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;
    private List<String> pagerTitleList;
    public FragAdapter(FragmentManager fragmentManager, List<Fragment> fragments) {
        super(fragmentManager);
        fragmentList = fragments;
    }
    public FragAdapter(FragmentManager fragmentManager, List<Fragment> fragments, List<String> pagerTitleList) {
        super(fragmentManager);
        fragmentList = fragments;
        this.pagerTitleList = pagerTitleList;
    }
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pagerTitleList.get(position);
    }
}
