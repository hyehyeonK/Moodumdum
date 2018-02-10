package org.androidtown.dumdumtest3;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by User on 2018-01-23.
 */

public class TabAdapter extends FragmentStatePagerAdapter{
    private final static int tabCount = 2;

    private Context mContext;

    public TabAdapter(FragmentManager fm) {
        super( fm );
        //this.mContext = context;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                FragmentOne fragmentOne = new FragmentOne();
                return fragmentOne;
            case 1:
                FragmentTwo fragmentTwo = new FragmentTwo();
                return fragmentTwo;
        }

        return null;
    }

    @Override
    public int getCount() {
        return tabCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case 0:
                return "내가 쓴 글";
            case 1:
                return "내가 조문한 글";
            default:
                return null;
        }
    }
}
