package com.xwc.demo;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private String[] tableTitle = new String[] {"example1", "example2", "example3"};
    private Context mContext;
    private List<Fragment> mFragmentTab;
    private Example1Fragment example1Fragment1;
    private Example2Fragment example1Fragment2;
    private Example3Fragment example1Fragment3;

    public MyFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
        initFragmentTab();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentTab.get(position);
    }

    @Override
    public int getCount() {
        return tableTitle.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tableTitle[position];
    }

    private void initFragmentTab() {
        example1Fragment1 = new Example1Fragment();
        example1Fragment2 = new Example2Fragment();
        example1Fragment3 = new Example3Fragment();
        mFragmentTab = new ArrayList<Fragment>();
        mFragmentTab.add(example1Fragment1);
        mFragmentTab.add(example1Fragment2);
        mFragmentTab.add(example1Fragment3);
    }
}