package com.nexters.moodumdum;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.nexters.moodumdum.api.MooDumDumService;
import com.nexters.moodumdum.common.PropertyManagement;
import com.nexters.moodumdum.factory.DeviceUuidFactory;
import com.nexters.moodumdum.model.ServerResponse;
import com.nexters.moodumdum.views.StackCardActivity;

import java.util.Random;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kimhyehyeon on 2018. 2. 10..
 */

public class MainActivity extends AppCompatActivity {
    private String[] nickNameList = {"지나가는", "상처받은", "떠도는", "배회하는", "마음다친", "녹초가된", "기진맥진", "가여운", "굶주린", "끄적이는", "목마른"};
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private String UUID;
    Fragment initialFragment;
    Fragment cardFragment;
    public static Activity MainAct;
    public static Context MainActivityContext;
    private DeviceUuidFactory uuidFactory;
    private String nickName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(this, SplashActivity.class)); //splash 화면 띄우기

        initView();
        checkFirstRun();
    }
    public void initView() {
        MainAct = MainActivity.this;
        MainActivityContext = getBaseContext();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
    }
    //첫 실행 확인
    public void checkFirstRun() {

        //첫 실행 이면
        if (PropertyManagement.getUserId(MainActivity.this) == null) {

            //UUID 생성
            uuidFactory = new DeviceUuidFactory();
            UUID uuid = uuidFactory.getDeviceUuid();

            //랜덤 닉네임 생성
            Random random = new Random();
            int randomNum = random.nextInt(nickNameList.length);
            nickName = nickNameList[randomNum];
            PropertyManagement.putUserProfile(MainActivityContext, nickName);

            //서버에 보내기
            postUserData(uuid + "", nickName + " 영혼");

            //앱 최초 실행 Fragment 추가하기
//            initialFragment = new InitialBaseFragment();
//            cardFragment = new MainCardStackFragment();
//            fragmentTransaction.add(R.id.fragment_container, cardFragment);
//            fragmentTransaction.add(R.id.fragment_container, initialFragment);
//            fragmentTransaction.commit();

        }
        Intent intent = new Intent( this, StackCardActivity.class );
        startActivity( intent );
//        else {
//            fragmentTransaction.add(R.id.fragment_container, new MainCardStackFragment());
//            fragmentTransaction.commit();
//        }
//        fragmentTransaction.add(R.id.fragment_container, new MainCardStackFragment());
//        fragmentTransaction.commit();
//        UUID = PropertyManagement.getUserId(MainActivity.this);
    }
    //    public String getUUID(){
//        return UUID;
//    }
    public void reloadUUID(){
        UUID uuid = uuidFactory.getDeviceUuid();
        //서버에 보내기
        postUserData(uuid + "", nickName + " 영혼");
    }

//    public void setCardFragment(){
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.remove( initialFragment );
//        fragmentTransaction.commit();
//    }

    // 유저 서버에 등록
    private void postUserData(final String uuid, String nickName) {
        MooDumDumService.of().postUserData(uuid, nickName)
                .enqueue(new Callback<ServerResponse>() {
                    @Override
                    public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                        if (response.isSuccessful()) {
                            //디바이스 DB에 저장
                            PropertyManagement.putUserId(MainActivityContext,uuid);
                        }
                        else {
                            Log.d("postUserDataErr", "유저 정보 저장 실패");
                            if(response.body() == null){
                                reloadUUID();
                            }
                            else { ErrAlert();}
                        }
                    }

                    @Override
                    public void onFailure(Call<ServerResponse> call, Throwable t) {
                        Log.d("postUserDataOnFailure", "유저 정보 저장 실패");
                        ErrAlert();
                    }
                });
    }

    private void ErrAlert() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                MainActivity.this);

        alertDialogBuilder.setTitle("앱 실행에 실패했습니다.");

        // AlertDialog 셋팅
        alertDialogBuilder
                .setMessage("오류를 보고하시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("네, 오류를 보고할게요",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog, int id) {
                                // 프로그램을 종료한다
                                MainActivity.this.finish();
                            }
                        })
                .setNegativeButton("종료",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog, int id) {
                                // 다이얼로그를 취소한다
                                MainActivity.this.finish();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
}
