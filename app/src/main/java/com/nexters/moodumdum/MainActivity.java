package com.nexters.moodumdum;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.stack_layout)
    FrameLayout stack_layout;

    @BindView(R.id.startContents)
    TextView contents;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        contents.setText("하하하하하하");


    }

    public boolean onTouchEvent(MotionEvent event) {
        Intent intent = new Intent(this, MainCardActivity.class);
        intent.putExtra("contents", contents.getText());
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, (View)stack_layout, "cardStack");
        startActivity(intent, options.toBundle());
        return true;

    }




}
