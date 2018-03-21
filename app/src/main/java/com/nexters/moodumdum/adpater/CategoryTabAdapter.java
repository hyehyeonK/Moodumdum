package com.nexters.moodumdum.adpater;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.nexters.moodumdum.FragmentCategorySelectedFavorite;
import com.nexters.moodumdum.FragmentCategorySelectedLatest;

/**
 * Created by kimhyehyeon on 2018. 3. 14..
 */

public class CategoryTabAdapter extends FragmentStatePagerAdapter {
    private final static int tabCount = 2;

    private String categoryID;

    public CategoryTabAdapter(FragmentManager fm) {
        super( fm );
    }

    public void setCategoryId(String categoryID){
        this.categoryID = categoryID;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                FragmentCategorySelectedLatest fragmentLatest= new FragmentCategorySelectedLatest();
                fragmentLatest.setCategoryId(categoryID);
                return fragmentLatest;
            case 1:
                FragmentCategorySelectedFavorite fragmentFavorite = new FragmentCategorySelectedFavorite();
                fragmentFavorite.setCategoryId(categoryID);
                return fragmentFavorite;
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
                return "최신순";
            case 1:
                return "인기순";
            default:
                return null;
        }
    }
}
