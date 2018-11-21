package com.nexters.moodumdum.views;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.nexters.moodumdum.fragments.FragmentMyJomun;
import com.nexters.moodumdum.fragments.FragmentMyWrite;
import com.nexters.moodumdum.PlusActivity;
import com.nexters.moodumdum.PlusBackimgActivity;
import com.nexters.moodumdum.R;
import com.nexters.moodumdum.adpater.MyPageTabAdapter;
import com.nexters.moodumdum.api.MooDumDumService;
import com.nexters.moodumdum.common.PropertyManagement;
import com.nexters.moodumdum.model.UserDataModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Mypage extends AppCompatActivity implements FragmentMyJomun.OnFragmentInteractionListener, FragmentMyWrite.OnFragmentInteractionListener {

    @BindView(R.id.myName)
    TextView myName;
    @BindView(R.id.mylikeCount)
    TextView mylikeCount;
    @BindView(R.id.myBoardCount)
    TextView myBoardCount;
    @BindView(R.id.btn_editName)
    ImageView btnEditName;

    public static Activity activity;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static RequestManager glideRequestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_mypage );
        ButterKnife.bind( this );
        activity =this;
//        ((MainCardStackFragment) MainCardStackFragment.MainCardFragment).refreshData();
        initView();
    }

    public void initView() {
        glideRequestManager = Glide.with(this);
        tabLayout = (TabLayout) findViewById( R.id.tablayout );
        viewPager = (ViewPager) findViewById( R.id.viewPager );
        tabLayout.addTab( tabLayout.newTab().setText( "내가 쓴 글" ) );

        MyPageTabAdapter tabAdapter = new MyPageTabAdapter( getSupportFragmentManager() );
        viewPager.setAdapter( tabAdapter );

        tabLayout.setupWithViewPager( viewPager );
        Intent intent = getIntent();
        String a = intent.getStringExtra( "plusContents" );
        if (!a.equals( "no" )) {
            PlusBackimgActivity plusBackimgActivity = (PlusBackimgActivity) PlusBackimgActivity.plusBackimgActivity;
            plusBackimgActivity.finish();
            PlusActivity plusActivity = (PlusActivity) PlusActivity.plusActivity;
            plusActivity.finish();
        }
        getMyData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        PropertyManagement.getUserId( this );
        myName.setText( PropertyManagement.getUserProfile( this ) );
    }

    public void getMyData() {
        PropertyManagement.getUserId( this );
        myName.setText( PropertyManagement.getUserProfile( this ) );

        String uuid = PropertyManagement.getUserId(Mypage.this);
        MooDumDumService.of().getUserData( uuid ).enqueue( new Callback<UserDataModel>() {
            @Override
            public void onResponse(Call<UserDataModel> call, Response<UserDataModel> response) {
                UserDataModel items = response.body();
                myName.setText( items.getNickName() );
                myBoardCount.setText( String.valueOf( items.getBoard_count() ) );
                mylikeCount.setText( String.valueOf( items.getLike_count() ) );
            }
            @Override
            public void onFailure(Call<UserDataModel> call, Throwable t) {
            }
        } );
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @OnClick(R.id.btn_back)
    public void onBtnBackClicked() {
        this.finish();
        overridePendingTransition( R.anim.not_move_activity, R.anim.leftout_activity );
    }

    @OnClick(R.id.onClickToPlus)
    public void onViewClicked() {
        Intent intent = new Intent( getApplicationContext(), PlusActivity.class );
        startActivity( intent );
        finish();
    }


    @OnClick(R.id.btn_editName)
    public void onBtnEditNameClicked() {
        Intent intent = new Intent( this, NameEditActivity.class );
        startActivityForResult( intent, 1  );
    }

}
