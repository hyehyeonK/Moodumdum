package com.nexters.moodumdum;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kimhyehyeon on 2018. 2. 10..
 */

public class StartActivity extends AppCompatActivity {

    @BindView(R.id.stack_layout)
    FrameLayout stack_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Intent intent = new Intent(this, MainActivity.class);
// Pass data object in the bundle and populate details activity.
//        intent.putExtra(MainActivity.EXTRA_CONTACT, contact);
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, (View)stack_layout, "cardStack");
        startActivity(intent, options.toBundle());

    }


}
