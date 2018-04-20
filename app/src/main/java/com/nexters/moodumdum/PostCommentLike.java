package com.nexters.moodumdum;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.nexters.moodumdum.api.MooDumDumService;
import com.nexters.moodumdum.model.CommentModel;
import com.nexters.moodumdum.model.ServerResponse;

import java.math.BigInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 2018-04-19.
 */

public class PostCommentLike {

    private static PostCommentLike singletonInstance;
    private boolean isSuccess;
    RequestManager glideRequestManager;
    CommentModel commentModel;
    int count;
    View currnetView;
    ImageView imageView;
    TextView textView;

    public static PostCommentLike getInstance(){
        if(singletonInstance==null){
            singletonInstance = new PostCommentLike();
        }
        return singletonInstance;
    }

    public void returnResult(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                glideRequestManager.load(R.drawable.like_after).into(imageView);
                imageView.setColorFilter(null);
                textView.setText("공감 "+(count + 1) +"개");
            }
        },200);
    }

    public void PostCommentLike(BigInteger comment_id, ImageView imageView, TextView textView, RequestManager glideRequestManager) {
        this.imageView = imageView;
        this.glideRequestManager = glideRequestManager;
        this.textView = textView;
        String uuid = ((MainActivity) MainActivity.MainAct).getUUID();
        boolean result = false;
        MooDumDumService.of().postCommentLike( comment_id, uuid ).enqueue( new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                returnResult();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

            }
        } );
    }
}

