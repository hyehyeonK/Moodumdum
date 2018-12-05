package com.nexters.moodumdum.views;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.fashare.stack_layout.StackLayout;
import com.fashare.stack_layout.transformer.AngleTransformer;
import com.nexters.moodumdum.R;
import com.nexters.moodumdum.adpater.CardAdapter;
import com.nexters.moodumdum.api.MooDumDumService;
import com.nexters.moodumdum.common.PropertyManagement;
import com.nexters.moodumdum.model.CardDataModel;
import com.nexters.moodumdum.model.CardListModel;
import com.nexters.moodumdum.model.ServerResponse;
import com.nexters.moodumdum.utils.Constants;
import com.nexters.moodumdum.utils.MyAlphaTransformer;
import com.nexters.moodumdum.utils.MyStackPageTransformer;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kimhyehyeon on 2018. 10. 30..
 */

public class StackCardActivity extends AppCompatActivity {
    StackLayout mStackLayout;
    CardAdapter stackCardAdapter;
    List<CardDataModel> results = new ArrayList<>();
    int curPage = 0;
    int curPosition = 0;
    int StatusBarHeight;
    @BindView(R.id.topFrame)
    ConstraintLayout topFrame;
    @BindView(R.id.motionLikeView)
    FrameLayout motionLikeView;
    @BindView(R.id.motionImage)
    ImageView motionImg;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        ButterKnife.bind(this);
        getStatusBarHeight();
        setActionbarMarginTop(topFrame);
        initView();
        loadData(0);
    }
    public void getStatusBarHeight(){
        int statusHeight = 0;
        int screenSizeType = (this.getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK);

        if(screenSizeType != Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            int resourceId = this.getResources().getIdentifier("status_bar_height", "dimen", "android");

            if (resourceId > 0) {
                statusHeight = this.getResources().getDimensionPixelSize(resourceId);
            }
        }
        StatusBarHeight = statusHeight;
    }

    public void setActionbarMarginTop(final View view){
        FrameLayout.LayoutParams topLayoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        topLayoutParams.topMargin = StatusBarHeight;
        view.setLayoutParams(topLayoutParams);
    }
    private void initView() {
        mStackLayout = (StackLayout) findViewById(R.id.stack_layout);
        stackCardAdapter = new CardAdapter( this,results = new ArrayList<>());
        stackCardAdapter.setOnItemGestureListener(new CardAdapter.SimpleOnGestureListener() {
            @Override
            public void onSingleTapConfirmed(CardDataModel cardInfo, int position) {
                curPosition = position;
                Intent intent = new Intent( getBaseContext(), DetailCardActivity.class );
                intent.putExtra( "cardInfo", cardInfo);
                intent.putExtra( "beforeAct", Constants.ACTIVITY_STACKCARD);
                startActivityForResult(intent,Constants.ACTIVITY_RESULT_STACKCARD);
                overridePendingTransition(R.anim.load_fadein,R.anim.load_fadeout);
            }

            @Override
            public void onDoubleTap(final int position) {
                CardDataModel currData = results.get(position);

                likeMotion();
                if(!currData.is_liked)
                {
                    postDoLike(currData.id, position);
                }

            }

        });
        stackCardAdapter.setOnItemClickListener(new CardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CardDataModel cardInfo, int position) {
                CardDataModel currData = results.get(position);
                if (currData.is_liked) {
                    cancelDoLike(currData.id, position);
                }
                else
                {
                    likeMotion();
                    postDoLike(currData.id, position);
                }
            }
        });

        mStackLayout.setAdapter(stackCardAdapter);
        mStackLayout.addPageTransformer(
                new MyStackPageTransformer(),
                new MyAlphaTransformer(),
                new AngleTransformer()
        );

        mStackLayout.setOnSwipeListener(new StackLayout.OnSwipeListener() {
            @Override
            public void onSwiped(View swipedView, int swipedItemPos, boolean isSwipeLeft, int itemLeft) {
                if(itemLeft < 5) {
                    curPage += 10;
                    loadData( curPage );
                }
            }
        });
    }

    private void likeMotion()
    {
        motionLikeView.setVisibility(View.VISIBLE);
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(motionImg,1);
        Glide.with(getBaseContext()).load(R.raw.motion_like)
                .crossFade()
                .into(imageViewTarget);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                motionLikeView.setVisibility(View.INVISIBLE);

            }
        },2500);
    }

    private void postDoLike(final BigInteger cardId, final int position)
    {
        String uuid =  PropertyManagement.getUserId(getBaseContext());
        MooDumDumService.of().postDoLike( cardId, uuid ).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("postDoLike()","Success!");
                    results.get(position).is_liked = true;
                    results.get(position).like_count ++;
                    stackCardAdapter.notifyDataSetChanged();
                }
                else{
                    Log.e("postDoLike()Fail",response.message());
                    Toast.makeText(StackCardActivity.this ,"국화 주기에 문제가 발생했습니다.",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.e( "postDoLikeError", "좋아요 실패" );
                Toast.makeText(StackCardActivity.this ,"국화Err : " + t,Toast.LENGTH_LONG).show();
            }
        } );
    }

    private void cancelDoLike(final BigInteger cardId, final int position) {
        String uuid =  PropertyManagement.getUserId(getBaseContext());
        MooDumDumService.of().deleteContentsLike( uuid, cardId ).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("cancelDoLike()","Success!");
                    results.get(position).is_liked = false;
                    results.get(position).like_count --;
                    stackCardAdapter.notifyDataSetChanged();
                }
                else{
                    Log.e("cancelDoLike()Fail",response.message());
                    Toast.makeText(StackCardActivity.this ,"국화 취소에 문제가 발생했습니다.",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.d( "cancelLikeError", "좋아요 취소 실패" );
                Toast.makeText(StackCardActivity.this ,"LikeErr : " + t,Toast.LENGTH_LONG).show();
            }
        } );

    }

    public void loadData(final int dataOffset) {
        String uuid =  PropertyManagement.getUserId(this);
        MooDumDumService.of().getContents( uuid , dataOffset ).enqueue(new Callback<CardListModel>() {
            @Override
            public void onResponse(Call<CardListModel> call, final Response<CardListModel> response) {
                if (response.isSuccessful()) {
                    final CardListModel items = response.body();
//                    results = items.getResult();
                    if( items.next == null) {
//                        noMoreData = true;
                    }
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            stackCardAdapter.getData().addAll( items.result );
                            stackCardAdapter.notifyDataSetChanged();
                        }
                    }, 1000 );

                }
                Log.d( "GET CardList Result ", response.message() );
            }

            @Override
            public void onFailure(Call<CardListModel> call, Throwable t) {
                Log.e( "GET CardList Result ", "Fail, " + t );
            }
        } );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == Constants.ACTIVITY_RESULT_STACKCARD) {
            boolean isLike = data.getBooleanExtra("IS_LIKE",false);
            int countLike = data.getIntExtra("COUNT_LIKE",0);
            int countComment = data.getIntExtra("COUNT_COMMENT",0);
            results.get(curPosition).is_liked = isLike;
            results.get(curPosition).like_count = countLike;
            results.get(curPosition).comment_count = countComment;
            stackCardAdapter.notifyDataSetChanged();

        }
    }

    @OnClick(R.id.onClickToMyPage)
    void onClickToMyPage() {

        Intent intent = new Intent( this, Mypage.class );
        intent.putExtra( "plusContents", "no" );
        startActivity( intent );
        overridePendingTransition(R.anim.load_fadein,R.anim.load_fadeout);
    }

    @OnClick(R.id.onClickToMenu)
    void onClickToMenu() {
        Intent intent = new Intent( this, CategoryActivity.class );
        startActivity( intent );
        overridePendingTransition(R.anim.load_fadein,R.anim.load_fadeout);
    }

    @OnClick(R.id.onClickToPlus)
    public void onViewClicked() {
        Intent intent = new Intent( this, PlusActivity.class );
        startActivity( intent );
        overridePendingTransition(R.anim.load_fadein,R.anim.load_fadeout);
    }


//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
////        null.unbind();
//    }
}