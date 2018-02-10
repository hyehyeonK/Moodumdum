package org.androidtown.dumdumtest3;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class Mypage extends AppCompatActivity  implements FragmentTwo.OnFragmentInteractionListener, FragmentOne.OnFragmentInteractionListener{

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_mypage);



        tabLayout = (TabLayout) findViewById( R.id.tablayout );
        viewPager = (ViewPager) findViewById( R.id.viewPager );

        tabLayout.addTab( tabLayout.newTab().setText( "내가 쓴 글" ) );

        TabAdapter tabAdapter = new TabAdapter( getSupportFragmentManager() );
        viewPager.setAdapter( tabAdapter );

        tabLayout.setupWithViewPager( viewPager );


        toolbar = (android.support.v7.widget.Toolbar) findViewById( R.id.toolbar );
        //toolbar.setHorizontalFadingEdgeEnabled( true );
        //toolbar.setTitleMarginStart( 185 );
        toolbar.setTitleTextColor( Color.GRAY );
        setSupportActionBar( toolbar );
        //getSupportActionBar().setDisplayHomeAsUpEnabled( true );


        //blur();



    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
