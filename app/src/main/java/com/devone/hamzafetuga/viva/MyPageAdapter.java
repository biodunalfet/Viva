package com.devone.hamzafetuga.viva;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Hamza Fetuga on 11/4/2014.
 */
public class MyPageAdapter extends FragmentPagerAdapter {
    public MyPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new frag_guide_help_1();
            case 1:
                return new frag_guide_help_2();
            case 2:
                return new frag_guide_help_3();
            case 3:
                return new frag_guide_help_4();
            case 4:
                return new frag_guide_help_5();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 5;
    }
}
