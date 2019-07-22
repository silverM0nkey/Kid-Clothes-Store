package com.happybaby.kcs.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> mFragments;
    public static final String TAB_MY_PROFILE = "MY PROFILE";
    public static final String TAB_CATALOG = "CATALOG";
    public static final String TAB_STORES = "STORES";

    public SimpleFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        if (position>=0 && position<getCount()){
            return (Fragment)mFragments.get(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return TAB_MY_PROFILE;
            case 1:
                return TAB_CATALOG;
            case 2:
                return TAB_STORES;
        }
        return "";
    }

}
