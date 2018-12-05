package com.nexters.moodumdum.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.TextView;

import com.nexters.moodumdum.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kimhyehyeon on 2018. 2. 19..
 */

public class CategoryActivity extends AppCompatActivity {

    @BindView(R.id.category_title)
    TextView text;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);
        text.setText("");
    }

    @OnClick({R.id.category_love, R.id.category_job, R.id.category_badHistory, R.id.category_selfEsteem, R.id.category_family, R.id.category_etc})
    void onClickToLoveOfCategory(ImageButton button){
        Intent intent = new Intent(getApplicationContext(), CategorySelectedActivity.class);
        String selectBtn = button.getTag() + "";//카테고리 태그
        intent.putExtra("categoryID", selectBtn);
        startActivity(intent);
        overridePendingTransition(R.anim.load_fadein,R.anim.load_fadeout);
    }

    @Override
    public void onBackPressed() {
        gotoMain();
    }
    @OnClick(R.id.btn_back)
    public void gotoMain() {
        this.finish();
        overridePendingTransition(R.anim.load_fadein,R.anim.load_fadeout);
    }

    @OnClick(R.id.onClickToPlus)
    public void onViewClicked() {
        Intent intent = new Intent( getApplicationContext(), PlusActivity.class );
        startActivity( intent );
        overridePendingTransition(R.anim.load_fadein,R.anim.load_fadeout);
    }
}
