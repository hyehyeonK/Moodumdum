//package com.nexters.moodumdum.utils;
//
//import android.content.Context;
//import android.os.Handler;
//import android.util.Log;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.bumptech.glide.RequestManager;
//import com.nexters.moodumdum.api.MooDumDumService;
//import com.nexters.moodumdum.common.PropertyManagement;
//import com.nexters.moodumdum.model.DetailCardInfoDAO;
//import com.nexters.moodumdum.model.ServerResponse;
//
//import java.math.BigInteger;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
///**
// * Created by kimhyehyeon on 2018. 4. 18..
// */
//
//public class PostLike {
//    private static PostLike singletonInstance;
//    private boolean isSuccess;
//    RequestManager glideRequestManager;
//    int count;
//    ImageView imageView;
//    TextView textView;
//    static DetailCardInfoDAO detailCardInfo;
//
//    public static PostLike getInstance(){
//        if(singletonInstance==null){
//            singletonInstance = new PostLike();
//        }
//        return singletonInstance;
//    }
//    public void PostComment(BigInteger board_id, DetailCardInfoDAO detailCardInfo, Context context) {
//        this.detailCardInfo = detailCardInfo;
//        String uuid = PropertyManagement.getUserId(context);
//        MooDumDumService.of().postDoLike( board_id, uuid ).enqueue(new Callback<ServerResponse>() {
//            @Override
//            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
//                if (response.isSuccessful()) {
//                    Log.d("postDoLike()Sucess","Success!");
//                    setIsLikeDAO();
//                }
//                else{
//                    Log.e("postDoLike()Fail",response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ServerResponse> call, Throwable t) {
//                Log.e( "@CommentSaveError", "좋아요 실패" );
//            }
//        } );
//    }
//    public void setIsLikeDAO(){
//        detailCardInfo.setIsLike(true);
//    }
//    public void PostComment(BigInteger board_id, int count, ImageView imageView, TextView textView, RequestManager glideRequestManager, Context context) {
//        this.count = count;
//        this.imageView = imageView;
//        this.textView = textView;
//        this.glideRequestManager = glideRequestManager;
//        String uuid = PropertyManagement.getUserId(context);
//        boolean result = false;
//        MooDumDumService.of().postDoLike( board_id, uuid ).enqueue(new Callback<ServerResponse>() {
//            @Override
//            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
//                if (response.isSuccessful()) {
//                    Log.d("postDoLike()Sucess","Success!");
//                    returnResult();
//                }
//                else{
//                    Log.e("postDoLike()Fail",response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ServerResponse> call, Throwable t) {
//                Log.e( "@CommentSaveError", "좋아요 실패" );
//            }
//        } );
//    }
//    public void returnResult(){
//        Log.d("SDWEWEW222222","result!Suces!dsf@!@!@ ");
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                imageView.setSelected(true);
////                glideRequestManager.load(R.drawable.like_after).into(imageView);
//                imageView.setColorFilter(null);
//                textView.setText((count + 1) +"");
//            }
//        },2700);
//    }
//}
