package com.nexters.moodumdum;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.nexters.moodumdum.api.MooDumDumService;
import com.nexters.moodumdum.model.PostContentsModel;
import com.nexters.moodumdum.model.ServerResponse;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlusBackimgActivity extends AppCompatActivity {
    PostContentsModel contentsModel;

    @BindView(R.id.onClickToCancle)
    Button onClickToCancle;
    @BindView(R.id.test)
    FrameLayout test;
    @BindView(R.id.contentOfPlusBackimg)
    TextView contentOfPlusBackimg;
    @BindView(R.id.onClickToFinish)
    TextView onClickToFinish;
    @BindView(R.id.gridViewImages)
    GridView gridViewImages;
    @BindView(R.id.plusBackimgLayout)
    ConstraintLayout plusBackimgLayout;

    ArrayList<Integer> imageIDs = new ArrayList<Integer>(  );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_plus_backimg );
        ButterKnife.bind( this );

        Intent intent = getIntent();
        contentsModel = (PostContentsModel) intent.getSerializableExtra("newContents");
//        String contentOfPlus = intent.getStringExtra( "contentOfPlus" );

        contentOfPlusBackimg.setText( contentsModel.getDescription() + "" );

        imageIDs.add( R.drawable.back_1 );
        imageIDs.add( R.drawable.back_2 );
        imageIDs.add( R.drawable.back_3 );
        imageIDs.add( R.drawable.back_1 );
        imageIDs.add( R.drawable.back_2 );
        imageIDs.add( R.drawable.back_3 );
        imageIDs.add( R.drawable.back_1 );
        imageIDs.add( R.drawable.back_2 );
        imageIDs.add( R.drawable.back_3 );
        imageIDs.add( R.drawable.back_1 );

        // 이미지 랜덤
        Collections.shuffle( imageIDs );

        PlusBackimgAdapter plusBackimgAdapter = new PlusBackimgAdapter( this, imageIDs );
        gridViewImages.setAdapter( plusBackimgAdapter );

        // 이미지 클릭 시 배경 적용
        gridViewImages.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                plusBackimgLayout.setBackground(getDrawable( imageIDs.get( i )));
                contentsModel.setImage_url("url_test_" + i );

            }
        } );

    }

    @OnClick(R.id.onClickToCancle)
    public void onOnClickToCancleClicked() {

        this.finish();
    }

    // 묻기 버튼 클릭
    @OnClick(R.id.onClickToFinish)
    public void onOnClickToFinishClicked() {
        postMyMemory(); // 데이터 서버로 보내기
//        Intent intent = new Intent( this, Mypage.class );
//        startActivity( intent );
    }

    private void postMyMemory() {
        MooDumDumService.of().postContents(contentsModel)
                .enqueue(new Callback<ServerResponse>() {

                    @Override
                    public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                        if (response.isSuccessful()) {
                            //요청사항 성공 페이지 만들어야 함
                            Toast.makeText(getBaseContext(), "기억 묻기 성공!", Toast.LENGTH_SHORT).show();
                            Log.d("postMyMemory",response.message());
                            Intent intent = new Intent(getBaseContext(), Mypage.class);
                            startActivity(intent);
                        }
                        Log.d("postMyMemory",response.message());

                    }

                    @Override
                    public void onFailure(Call<ServerResponse> call, Throwable t) {
                        Log.d("postMyMemory","error");
                    }
                });
    }
}
