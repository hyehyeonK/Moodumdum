package com.nexters.moodumdum;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlusActivity extends AppCompatActivity {
    @BindView(R.id.onClickToCancle)
    Button onClickToCancle;

    @BindView(R.id.onClickToNext)
    TextView onClickToNext;

    @BindView(R.id.test)
    FrameLayout test;

    @BindView(R.id.horizontalScrollView)
    HorizontalScrollView horizontalScrollView;

    @BindView(R.id.contentOfPlus)
    EditText contentOfPlus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_plus );
        ButterKnife.bind( this );

        // 키보드 강제 올리기
//        InputMethodManager imm = (InputMethodManager) getSystemService( Context.INPUT_METHOD_SERVICE);
//        imm.showSoftInput(contentOfPlus, InputMethodManager.SHOW_FORCED);



    }

    @OnClick(R.id.onClickToCancle)
    public void onOnClickToCancleClicked() {
        this.finish();
    }

    @OnClick(R.id.onClickToNext)
    public void onOnClickToNextClicked() {
        Intent intent = new Intent( this, PlusBackimgActivity.class );
        intent.putExtra( "contentOfPlus", contentOfPlus.getText().toString() );
        startActivity( intent );
    }
}
