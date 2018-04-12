package com.nexters.moodumdum;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kimhyehyeon on 2018. 4. 13..
 */

public class FragmentMotionLike extends Fragment {
    @BindView(R.id.motionImage)
    ImageView motionImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.motion_like, container, false);
        ButterKnife.bind( this, view );
        startLoding();

        return view;
    }
    private void startLoding() {


        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(motionImage);
        Glide.with(this).load(R.raw.intro_splash_gif).into(imageViewTarget);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

//                finish();
//                //부드럽게 사라지게하기
//                FragT(R.anim.load_fadein, R.anim.load_fadeout);
            }
        },3000);
    }

}
