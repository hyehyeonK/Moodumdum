package com.nexters.moodumdum;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

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


//    @OnClick(R.id.category_love)
//    void onClickToLoveOfCategory(){
//        Intent intent = new Intent(getApplicationContext(), Mypage.class);
//        startActivity(intent);
//    }
//    @OnClick(R.id.category_job)
//    void onClickToJobOfCategory(){
//        Intent intent = new Intent(getApplicationContext(), Mypage.class);
//        startActivity(intent);
//    }
//    @OnClick(R.id.category_badHistory)
//    void onClickToBadHistoryOfCategory(){
//        Intent intent = new Intent(getApplicationContext(), Mypage.class);
//        startActivity(intent);
//    }
//    @OnClick(R.id.category_selfEsteem)
//    void onClickToSelfEsteemOfCategory(){
//        Intent intent = new Intent(getApplicationContext(), Mypage.class);
//        startActivity(intent);
//    }
//    @OnClick(R.id.category_family)
//    void onClickToFamilyOfCategory(){
//        Intent intent = new Intent(getApplicationContext(), Mypage.class);
//        startActivity(intent);
//    }
//    @OnClick(R.id.category_etc)
//    void onClickToEtcOfCategory(){
//        Intent intent = new Intent(getApplicationContext(), Mypage.class);
//        startActivity(intent);
//    }
}
