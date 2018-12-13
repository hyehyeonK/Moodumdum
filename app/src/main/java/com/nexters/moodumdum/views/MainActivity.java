package com.nexters.moodumdum.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.nexters.moodumdum.R;
import com.nexters.moodumdum.api.MooDumDumService;
import com.nexters.moodumdum.common.PropertyManagement;
import com.nexters.moodumdum.factory.DeviceUuidFactory;
import com.nexters.moodumdum.model.ServerResponse;

import java.util.Random;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kimhyehyeon on 2018. 12. 8..
 */

public class MainActivity extends AppCompatActivity {
    private String[] nickNameList = {"지나가는", "상처받은", "떠도는", "배회하는", "마음다친", "녹초가된", "기진맥진", "가여운", "굶주린", "끄적이는", "목마른"};

    @BindView(R.id.introGif)
    ImageView intro;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_splash_gif);
        ButterKnife.bind(this);

        startSplash();
        checkFirstRun();
    }
    private void startSplash() {
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(intro,1);
        Glide.with(this).load(R.raw.intro_splash_gif).into(imageViewTarget);
    }

    //첫 실행 확인
    private void checkFirstRun() {
        //첫 실행 이면
        if (PropertyManagement.getUserId(this) == null) {
            //UUID 생성
            DeviceUuidFactory uuidFactory = new DeviceUuidFactory();
            UUID uuid = uuidFactory.getDeviceUuid();

            //랜덤 닉네임 생성
            Random random = new Random();
            int randomNum = random.nextInt(nickNameList.length);
            String nickName = nickNameList[randomNum];
            PropertyManagement.putUserProfile(this, nickName);

            //서버에 보내기
            postUserData(uuid + "", nickName + " 영혼");
        }
        else
        {
           startCardActivity();
        }
    }

    private void reloadUUID(final String nickName){
        UUID uuid = new DeviceUuidFactory().getDeviceUuid();
        //서버에 보내기
        postUserData(uuid + "", nickName + " 영혼");
    }

    // 유저 서버에 등록
    private void postUserData(final String uuid, final String nickName) {
        MooDumDumService.of().postUserData(uuid, nickName)
                .enqueue(new Callback<ServerResponse>() {
                    @Override
                    public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                        if (response.isSuccessful()) {
                            //디바이스 DB에 저장
                            PropertyManagement.putUserId(getBaseContext(),uuid);
                            startCardActivity();
                        }
                        else {
                            Log.e("postUserDataErr", "유저 정보 저장 실패");
                            if(response.body() == null){
                                reloadUUID(nickName);
                            }
                            else { ErrAlert();}
                        }
                    }

                    @Override
                    public void onFailure(Call<ServerResponse> call, Throwable t) {
                        Log.e("postUserDataOnFailure", "유저 정보 저장 실패");
                        ErrAlert();
                    }
                });
    }

    private void ErrAlert() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

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
                                finish();
                            }
                        })
                .setNegativeButton("종료",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog, int id) {
                                // 다이얼로그를 취소한다
                                finish();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
//        스플래쉬 화면 동작 동안 뒤로가기 못가게 막기
    }

    private void startCardActivity() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, StackCardActivity.class)); //splash 화면 띄우기
                overridePendingTransition(R.anim.load_fadein, R.anim.load_fadeout);
                finish();
            }
        },5000);

    }
}
