package com.nexters.moodumdum;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kimhyehyeon on 2018. 2. 19..
 */

public class CategoryActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.category_love, R.id.category_job, R.id.category_badHistory, R.id.category_selfEsteem, R.id.category_family, R.id.category_etc})
    void onClickToLoveOfCategory(ImageButton button){
        Intent intent = new Intent(getApplicationContext(), CategorySelectedActivity.class);
        String selectBtn = button.getTag() + "";//카테고리 태그
        intent.putExtra("categoryID", selectBtn);
        startActivity(intent);
    }
}
