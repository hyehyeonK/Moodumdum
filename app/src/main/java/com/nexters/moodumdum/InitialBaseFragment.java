package com.nexters.moodumdum;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;


/**
 * Created by kimhyehyeon on 2018. 3. 28..
 */

public class InitialBaseFragment extends Fragment {
    public Fragment baseFragment = InitialBaseFragment.this;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.circle_indicator)
    CircleIndicator circleIndicator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_initial_base, container, false);
        ButterKnife.bind(this, view);
        setupViewPager();
        return view;
    }

    private void setupViewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager()); // 자식 매니저 설정 ***

        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(0);
        circleIndicator.setViewPager(viewPager);
        viewPagerAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position)
            {
                case 0:
                    return new InitialFirstFragment();
                case 1:
                    return new InitialSecondFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}