package com.nexters.moodumdum;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kimhyehyeon on 2018. 2. 19..
 */

public class SplashActivity extends Activity {

    @BindView(R.id.introGif)
    ImageView intro;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.intro_splash_gif);
        ButterKnife.bind(this);
        startLoding();
    }
    private void startLoding() {


        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(intro);
        Glide.with(this).load(R.raw.intro_splash_gif).into(imageViewTarget);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                finish();
                //부드럽게 사라지게하기
                overridePendingTransition(R.anim.load_fadein, R.anim.load_fadeout);
            }
        },5000);
    }

}
