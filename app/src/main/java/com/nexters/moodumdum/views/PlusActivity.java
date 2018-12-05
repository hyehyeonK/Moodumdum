package com.nexters.moodumdum.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nexters.moodumdum.R;
import com.nexters.moodumdum.model.PostContentsModel;

import java.math.BigInteger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlusActivity extends AppCompatActivity {
    public static Activity plusActivity;
    PostContentsModel contentsModel = new PostContentsModel();
    @BindView(R.id.onClickToCancle)
    ImageButton onClickToCancel;

    @BindView(R.id.onClickToNext)
    TextView onClickToNext;

    @BindView(R.id.test)
    FrameLayout test;

    @BindView(R.id.horizontalScrollView)
    HorizontalScrollView horizontalScrollView;

    @BindView(R.id.contentOfPlus)
    EditText contentOfPlus;
    @BindView(R.id.categorySelLayout)
    LinearLayout categorySelLayout;
    @BindView(R.id.btn_relationship)
    Button btnRelationship;
    @BindView(R.id.btn_family)
    Button btnFamily;
    @BindView(R.id.btn_job)
    Button btnJob;
    @BindView(R.id.btn_selfesteem)
    Button btnSelfesteem;
    @BindView(R.id.btn_darkhistory)
    Button btnDarkhistory;
    @BindView(R.id.btn_ect)
    Button btnEct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_plus );
        ButterKnife.bind( this );

        plusActivity = PlusActivity.this;
        // 키보드 강제 올리기
//        InputMethodManager imm = (InputMethodManager) getSystemService( Context.INPUT_METHOD_SERVICE);
//        imm.showSoftInput(contentOfPlus, InputMethodManager.SHOW_FORCED);

        //카테고리 초기화
        btnEct.setSelected(true);
        contentsModel.setCategory_id(new BigInteger(btnEct.getTag()+""));

    }

    @Override
    public void onBackPressed() {
        onOnClickToCancelClicked();
    }

    @OnClick(R.id.onClickToCancle)
    public void onOnClickToCancelClicked() {
        this.finish();
        overridePendingTransition(R.anim.load_fadein,R.anim.load_fadeout);
    }

    @OnClick(R.id.onClickToNext)
    public void onOnClickToNextClicked() {

        if (contentOfPlus.getText().toString().length() > 0 ) {
            contentsModel.setDescription(contentOfPlus.getText() + "");
            Intent intent = new Intent( this, PlusBackimgActivity.class );
            intent.putExtra( "newContents", contentsModel);
            startActivity( intent );
            overridePendingTransition(R.anim.rightin_activity,R.anim.not_move_activity);
        }
        else {
            Toast.makeText(getBaseContext(), "묻을 기억을 작성 해 주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick({R.id.btn_relationship, R.id.btn_family, R.id.btn_job, R.id.btn_selfesteem, R.id.btn_darkhistory, R.id.btn_ect})
    public void onViewClicked(Button button) {
        String selectBtn = button.getTag() + "";//카테고리 태그
        BigInteger categoryId = new BigInteger(selectBtn);

        btnRelationship.setSelected( false );
        btnFamily.setSelected( false );
        btnJob.setSelected( false );
        btnSelfesteem.setSelected( false );
        btnDarkhistory.setSelected( false );
        btnEct.setSelected( false );

        contentsModel.setCategory_id(categoryId);
        button.setSelected( true );
    }

}
