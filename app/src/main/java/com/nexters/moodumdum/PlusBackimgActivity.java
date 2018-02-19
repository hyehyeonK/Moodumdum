package com.nexters.moodumdum;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlusBackimgActivity extends AppCompatActivity {

    @BindView(R.id.onClickToCancle)
    Button onClickToCancle;
    @BindView(R.id.test)
    FrameLayout test;
    @BindView(R.id.contentOfPlusBackimg)
    TextView contentOfPlusBackimg;
    @BindView(R.id.onClickToFinish)
    TextView onClickToFinish;
    @BindView(R.id.gridViewImages)
    GridView gridViewImages;

    private int[] imageIDs = new int[]{
            R.drawable.back_1,
            R.drawable.back_2,
            R.drawable.back_3,
            R.drawable.category_back_1,
            R.drawable.category_back_2,
            R.drawable.category_back_3,
            R.drawable.category_back_4,
            R.drawable.category_back_5,
            R.drawable.category_back_6,
            R.drawable.category_back_6,
            R.drawable.category_back_6,
            R.drawable.category_back_6,
            R.drawable.category_back_6,
            R.drawable.category_back_6,
            R.drawable.category_back_6,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_plus_backimg );
        ButterKnife.bind( this );

        Intent intent = getIntent();
        String contentOfPlus = intent.getStringExtra( "contentOfPlus" );

        contentOfPlusBackimg.setText( contentOfPlus );

        PlusBackimgAdapter plusBackimgAdapter = new PlusBackimgAdapter(this,imageIDs);
        gridViewImages.setAdapter( plusBackimgAdapter );
    }

    @OnClick(R.id.onClickToCancle)
    public void onOnClickToCancleClicked() {
        this.finish();
    }

    // 묻기 버튼 클릭
    @OnClick(R.id.onClickToFinish)
    public void onOnClickToFinishClicked() {

//        Intent intent = new Intent( this, Mypage.class );
//        startActivity( intent );
    }
}

