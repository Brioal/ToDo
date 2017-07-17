package com.brioal.todo.guide;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * email:brioal@foxmail.com
 * github:https://github.com/Brioal
 * Created by Brioal on 2017/7/16.
 */

public class GuideViewPagerAdapter extends FragmentStatePagerAdapter {
    public GuideViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return GuideFragmentOne.getInstance();
            case 1:
                return GuideFragmentTwo.getInstance();
            case 2:
                return GuideFragmentThree.getInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
