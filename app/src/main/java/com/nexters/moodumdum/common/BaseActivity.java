package com.nexters.moodumdum.common;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by kimhyehyeon on 2018. 12. 10..
 */

public class BaseActivity extends AppCompatActivity {

    public void progressON() {
        BaseApplication.getInstance().progressON(this);
    }
    public void progressOFF() {
        BaseApplication.getInstance().progressOFF();
    }
}
