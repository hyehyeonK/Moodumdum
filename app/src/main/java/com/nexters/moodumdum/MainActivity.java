package com.nexters.moodumdum;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nexters.moodumdum.model.Contents;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    Contents contentsData;

    @BindView(R.id.stack_layout)
    LinearLayout stack_layout;

    @BindView(R.id.firstContents)
    TextView contents;

    @BindView(R.id.firstBackground)
    ImageView backImgUrl;

    @BindView(nick)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        initData();

    }

    private void initData() {

        contents.setText("하하하하하하");
        //api test data좀 주세요 호동님... ㅠㅠ
//        Glide.with(this).load(contentsData.getImage_url()).into(backImgUrl);
//        contents.setText(contentsData.getDescription());



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
