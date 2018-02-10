package com.nexters.moodumdum;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.transition.AutoTransition;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    // In your activity
    ConstraintSet constraint;
    AutoTransition transition;
    int parentHeight;
    int childHeight;
    float childScaleFactor;

    @BindView(R.id.constraintMain)
    ConstraintLayout constraintLayoutMain;

    @BindView(R.id.imageViewLogo)
    ImageView logoImg;

    @BindView(R.id.imageViewBottom)
    ImageView bottomImg;

    @BindView(R.id.bottomLayout)
    FrameLayout bottomLayout;

    @BindView(R.id.textBottom)
    TextView bottomText;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        initData();

    }

    private void initData() {{
        bottomText.setText("으에에에에에엥 글짜아아아아아아 Testttttt");
    }}

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        constraint = new ConstraintSet();
        constraint.clone(constraintLayoutMain);

        transition = new AutoTransition();
        transition.setDuration(1500);

        return true;
    }


}
