package com.nexters.moodumdum;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Mypage extends AppCompatActivity implements FragmentMyJomun.OnFragmentInteractionListener, FragmentMyWrite.OnFragmentInteractionListener {

    @BindView(R.id.myProfilePic)
    ImageView myProfilePic;
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
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
