package com.nexters.moodumdum.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.nexters.moodumdum.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kimhyehyeon on 2018. 12. 6..
 */

public class AppInfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appinfo);

        ButterKnife.bind(this);
    }

    @Override
    public void onBackPressed() {
        onBtnBackClicked();
    }

    @OnClick(R.id.btn_back)
    public void onBtnBackClicked()
    {
        finish();
        overridePendingTransition(R.anim.load_fadein,R.anim.load_fadeout);
    }

}
