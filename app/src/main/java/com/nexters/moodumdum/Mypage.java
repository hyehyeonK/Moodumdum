package com.nexters.moodumdum;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Mypage extends AppCompatActivity implements FragmentMyJomun.OnFragmentInteractionListener, FragmentMyWrite.OnFragmentInteractionListener {

    @BindView(R.id.myProfilePic)
    ImageView myProfilePic;
    @BindView(R.id.btn_back)
    Button btnBack;
    private TabLayout tabLayout;
    private ViewPager viewPager;
//  private android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_mypage );
        ButterKnife.bind( this );

        tabLayout = (TabLayout) findViewById( R.id.tablayout );
        viewPager = (ViewPager) findViewById( R.id.viewPager );

        tabLayout.addTab( tabLayout.newTab().setText( "내가 쓴 글" ) );

        MyPageTabAdapter tabAdapter = new MyPageTabAdapter( getSupportFragmentManager() );
        viewPager.setAdapter( tabAdapter );

        tabLayout.setupWithViewPager( viewPager );
        Intent intent = getIntent();
        if(intent != null) {
            PlusBackimgActivity plusBackimgActivity = (PlusBackimgActivity)PlusBackimgActivity.plusBackimgActivity;
            plusBackimgActivity.finish();
            PlusActivity plusActivity = (PlusActivity)PlusActivity.plusActivity;
            plusActivity.finish();
        }
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @OnClick(R.id.btn_back)
    public void onViewClicked() {
        this.finish();
    }
}
