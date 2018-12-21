package com.nexters.moodumdum.common;

import android.app.Activity;
import android.app.Application;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatDialog;
import android.widget.ImageView;

import com.nexters.moodumdum.R;

/**
 * Created by kimhyehyeon on 2018. 12. 10..
 */
public class BaseApplication extends Application {
    private static BaseApplication baseApplication;
    AppCompatDialog progressDialog;

    public static BaseApplication getInstance() {
        return baseApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
    }

    public void progressON(Activity activity) {
        if (activity == null || activity.isFinishing()) {
            return;
        }

        progressDialog = new AppCompatDialog(activity);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.test);
        progressDialog.show();

        final ImageView img_loading_frame = (ImageView) progressDialog.findViewById(R.id.iv_frame_loading);
        final AnimationDrawable frameAnimation = (AnimationDrawable) img_loading_frame.getBackground();
        img_loading_frame.post(new Runnable() {
            @Override public void run() {
                frameAnimation.start();
            }
        });
    }


    public void progressOFF() {
        if (progressDialog != null && progressDialog.isShowing()) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    progressDialog.dismiss();
                }
            },500);
        }
    }
}
