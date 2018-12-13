package com.nexters.moodumdum;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kimhyehyeon on 2018. 2. 10..
 */

public class InitialSecondFragment extends Fragment {
    @BindView(R.id.titleImg)
    ImageView titleImg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_initial_two, container, false);
        ButterKnife.bind( this, view );
        initView();
        return view;
    }

    public void initView(){
        titleImg.setColorFilter(Color.WHITE);
    }

    @OnClick(R.id.startBtn)
    public void startMooDumDum(){
        MainActivity mainActivity = (MainActivity) getActivity();
//        mainActivity.setCardFragment();
    }
}
