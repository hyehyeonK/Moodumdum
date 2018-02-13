package com.nexters.moodumdum;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class Mypage extends AppCompatActivity  implements FragmentMyJomun.OnFragmentInteractionListener, FragmentMyWrite.OnFragmentInteractionListener{

    private TabLayout tabLayout;
    private ViewPager viewPager;
//  private android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_mypage);

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
