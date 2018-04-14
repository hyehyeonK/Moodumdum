package com.nexters.moodumdum;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nexters.moodumdum.adpater.MyPageTabAdapter;
import com.nexters.moodumdum.api.MooDumDumService;
import com.nexters.moodumdum.factory.DeviceUuidFactory;
import com.nexters.moodumdum.model.UserDataModel;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Mypage extends AppCompatActivity implements FragmentMyJomun.OnFragmentInteractionListener, FragmentMyWrite.OnFragmentInteractionListener {


    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.myName)
    TextView myName;
    @BindView(R.id.mylikeCount)
    TextView mylikeCount;
    @BindView(R.id.myBoardCount)
    TextView myBoardCount;
    @BindView(R.id.btn_editName)
    ImageView btnEditName;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    UserDataModel userDataModel = new UserDataModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_mypage );
        ButterKnife.bind( this );

        initView();
    }

    public void initView() {
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

    public void getMyData() {
        DeviceUuidFactory uuidFactory = new DeviceUuidFactory( this );
        UUID uid = uuidFactory.getDeviceUuid();
        String uuid = uid.toString();

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

//    @OnClick(R.id.myName)
//    public void onMyNameClicked() {
//        Intent intent = new Intent( this, NameEditActivity.class );
//        intent.putExtra( "myName", myName.getText() );
//        startActivity( intent );
//    }

    @OnClick(R.id.onClickToPlus)
    public void onViewClicked() {
        Intent intent = new Intent( getApplicationContext(), PlusActivity.class );
        startActivity( intent );
        finish();
    }


    @OnClick(R.id.btn_editName)
    public void onBtnEditNameClicked() {
        Intent intent = new Intent( this, NameEditActivity.class );
        intent.putExtra( "myName", myName.getText() );
        startActivityForResult( intent, RESULT_OK  );
    }

}
