package com.nexters.moodumdum;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nexters.moodumdum.adpater.SelectViewOfBackgroundAdapter;
import com.nexters.moodumdum.api.MooDumDumService;
import com.nexters.moodumdum.model.ImageModel;
import com.nexters.moodumdum.model.PostContentsModel;
import com.nexters.moodumdum.model.ServerResponse;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlusBackimgActivity extends AppCompatActivity {

    private SelectViewOfBackgroundAdapter adapterBackImg;
    GridLayoutManager gridLayoutManager;
    PostContentsModel contentsModel;
    List<ImageModel.Result> images = new ArrayList<>();
    public static Activity plusBackimgActivity ;
    public static Context PlusBackimgActivity_context;

    @BindView(R.id.onClickToCancle)
    Button onClickToCancle;

    @BindView(R.id.contentOfPlus)
    TextView contentOfPlus;

    @BindView(R.id.onClickToFinish)
    TextView onClickToFinish;

    @BindView(R.id.recyclerBack)
    RecyclerView gridViewImagesRV;

    @BindView(R.id.selectedBackImg)
    ImageView selectedBackImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_plus_backimg );
        ButterKnife.bind( this );
        plusBackimgActivity = PlusBackimgActivity.this;
        PlusBackimgActivity_context = this;
        initView ();
    }
    private void initView (){
        //데이터 가져와서 뿌려주기
        Intent intent = getIntent();
        contentsModel = (PostContentsModel) intent.getSerializableExtra("newContents");
        contentOfPlus.setText( contentsModel.getDescription() + "" );

        //서버에서 이미지 가져오기 (랜덤으로 가져오는 걸로 변경 요청하기)
        getBackgroundImag();
        Collections.shuffle( images ); //호동오빠가 랜덤으로 불러오는거 만들어주면 없어도 되는 것


        //RecyclerView 연결
        gridLayoutManager = new GridLayoutManager(this,4);
        adapterBackImg = new SelectViewOfBackgroundAdapter(PlusBackimgActivity.this);
        gridViewImagesRV.setAdapter(adapterBackImg);
        gridViewImagesRV.setLayoutManager(gridLayoutManager);
        gridViewImagesRV.setItemAnimator(new DefaultItemAnimator());
    }

    @OnClick(R.id.onClickToCancle)
    public void onOnClickToCancleClicked() {
        this.finish();
        overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
    }

    // 묻기 버튼 클릭
    @OnClick(R.id.onClickToFinish)
    public void onOnClickToFinishClicked() {
//
//        Toast.makeText(this, "으앙아ㅏ아아아ㅏㅇ누름.", Toast.LENGTH_SHORT).show();
        postMyMemory(); // 데이터 서버로 보내기
//        Intent intent = new Intent( this, Mypage.class );
//        startActivity( intent );
    }

    private void postMyMemory() {
        String uuid = ((MainActivity) MainActivity.MainAct).getUUID();
        BigInteger category_id = contentsModel.getCategory_id();
//        String user = contentsModel.getUser();
//        String name = contentsModel.getName();
        String description = contentsModel.getDescription();
        String image_url = contentsModel.getImage_url();
        String font_color = contentsModel.getFontColor();
        MooDumDumService.of().postContents(uuid, category_id, description, image_url, font_color )
                .enqueue(new Callback<ServerResponse>() {

                    @Override
                    public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                        Log.d("@@@@@", response.message());
                        if (response.isSuccessful()) {
                            //요청사항 성공 페이지 만들어야 함
                            Toast.makeText(getBaseContext(), "당신의 기억을 묻었어요.", Toast.LENGTH_SHORT).show();
                            Log.d("postMyMemory",response.message());
                            Intent intent = new Intent(getApplication(), Mypage.class);
                            intent.putExtra("plusContents", "Success");
//                            ((MainCardStackFragment) MainCardStackFragment.MainCardFragment).getPost();
//                            ((MainCardStackFragment) MainCardStackFragment.MainCardFragment).initView();
//                            ((MainCardStackFragment) MainCardStackFragment.MainCardFragment).loadData(0);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<ServerResponse> call, Throwable t) {
                        Log.d("postMyMemoryErr","error");
                    }
                });
    }
    private void getBackgroundImag() {
        MooDumDumService.of().getBackgroundImage().enqueue( new Callback<ImageModel>() {
            @Override
            public void onResponse(Call<ImageModel> call, Response<ImageModel> response) {
                if (response.isSuccessful()) {
                    final ImageModel items = response.body();
                    images = items.getResult();
                    // 배경 선택 안 할 시 초기화로 들어갈 데이터
                    //나중에 랜덤으로 주기
                    contentsModel.setImage_url( images.get(0).getImage_url() );
                    contentsModel.setFontColor( images.get(0).getFont_color());
                    Log.d("##@#@##",contentsModel.getImage_url());

                    adapterBackImg.setImageList(images);
                }
            }

            @Override
            public void onFailure(Call<ImageModel> call, Throwable t) {
                Log.e( "RESULT@@@@@", "ERRR####" );
            }
        } );
    }

    public void setBackgroundImage (ImageModel.Result image) {
        Glide.with(getApplicationContext()).load(image.getImage_url()).into(selectedBackImg);
        contentOfPlus.setTextColor(Color.parseColor(image.getFont_color()));
        onClickToFinish.setTextColor(Color.parseColor(image.getFont_color()));
        contentsModel.setImage_url( image.getImage_url() );
        contentsModel.setFontColor( image.getFont_color());
    }
}
