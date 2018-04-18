package com.nexters.moodumdum;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.nexters.moodumdum.api.MooDumDumService;
import com.nexters.moodumdum.model.ServerResponse;

import java.math.BigInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kimhyehyeon on 2018. 4. 18..
 */

public class PostLike {
    private static PostLike singletonInstance;
    private boolean isSuccess;
    RequestManager glideRequestManager;
    int count;
    View currnetView;
    ImageView imageView;
    TextView textView;

    public static PostLike getInstance(){
        if(singletonInstance==null){
            singletonInstance = new PostLike();
        }
        return singletonInstance;
    }

    public void returnResult(){
        Log.d("SDWEWEW$$@E","result!Suces!dsf@!@!@ ");
        imageView = currnetView.findViewById(R.id.contents_like);
        textView = currnetView.findViewById(R.id.likeCount);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                glideRequestManager.load(R.drawable.like_after).into(imageView);
                imageView.setColorFilter(null);
                textView.setText((count + 1) +"");
            }
        },2700);
    }
    public void PostComment(BigInteger board_id, int count, View currnetView, RequestManager glideRequestManager) {
        this.count = count;
        this.currnetView = currnetView;
        this.glideRequestManager = glideRequestManager;
        String uuid = ((MainActivity) MainActivity.MainAct).getUUID();
        boolean result = false;
        MooDumDumService.of().postDoLike( board_id, uuid ).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("postDoLike()Sucess","Success!");
                    returnResult();
                }
                else{
                    Log.e("postDoLike()Fail",response.message());
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.e( "@CommentSaveError", "좋아요 실패" );
            }
        } );
    }
}
