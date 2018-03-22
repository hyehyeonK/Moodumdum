package com.nexters.moodumdum;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nexters.moodumdum.factory.DeviceUuidFactory;
import com.nexters.moodumdum.model.PostContentsModel;

import java.math.BigInteger;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlusActivity extends AppCompatActivity {
    public static Activity plusActivity;
    private UUID uuid;
    PostContentsModel contentsModel = new PostContentsModel();
    @BindView(R.id.onClickToCancel)
    Button onClickToCancel;

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
        DeviceUuidFactory uuidFactory = new DeviceUuidFactory( this );
        uuid = uuidFactory.getDeviceUuid();
        // 키보드 강제 올리기
//        InputMethodManager imm = (InputMethodManager) getSystemService( Context.INPUT_METHOD_SERVICE);
//        imm.showSoftInput(contentOfPlus, InputMethodManager.SHOW_FORCED);


    }

    @OnClick(R.id.onClickToCancel)
    public void onOnClickToCancelClicked() {
        this.finish();
    }

    @OnClick(R.id.onClickToNext)
    public void onOnClickToNextClicked() {
        contentsModel.setDescription(contentOfPlus.getText() + "");
        contentsModel.setUser(uuid+""); //uuid
        contentsModel.setName("고통받는혠영혼");
        Intent intent = new Intent( this, PlusBackimgActivity.class );
        intent.putExtra( "newContents", contentsModel);
        startActivity( intent );
        overridePendingTransition(R.anim.rightin_activity,R.anim.not_move_activity);
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
        //여기 선택했을때 contentsModel.setImage_url() 추가하기
        button.setSelected( true );
    }


}
