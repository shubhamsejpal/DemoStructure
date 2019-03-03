package com.bazmashop.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class VpHomeAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    private ArrayList<String> tabNames = new ArrayList<String>();

    public VpHomeAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragmentTabName(Fragment fragment, String tabName) {
        this.fragments.add(fragment);
        this.tabNames.add(tabName);
    }

    public Fragment getItem(int position) {
        return (Fragment) this.fragments.get(position);

    }

    public CharSequence getPageTitle(int position) {
        return (CharSequence) this.tabNames.get(position);
    }

    public int getCount() {
        return fragments.size();
    }


}