package com.nexters.moodumdum.views;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.nexters.moodumdum.R;
import com.nexters.moodumdum.adpater.CommentAdapter;
import com.nexters.moodumdum.anim.RecyclerViewDecoration;
import com.nexters.moodumdum.api.MooDumDumService;
import com.nexters.moodumdum.common.DoubleClickListener;
import com.nexters.moodumdum.common.PropertyManagement;
import com.nexters.moodumdum.model.CardDataModel;
import com.nexters.moodumdum.model.CommentModel;
import com.nexters.moodumdum.model.ServerResponse;
import com.nexters.moodumdum.utils.CustomDialog;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kimhyehyeon on 2018. 11. 6..
 */

public class DetailCardActivity extends AppCompatActivity {
    private CardDataModel cardData;
    private CommentAdapter mCommentAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    ScrollingMovementMethod scroll;
    private String uuid;
    private int StatusBarHeight;
    CustomDialog dialog;

    @BindView(R.id.topFrame)
    ConstraintLayout topFrame;
    @BindView(R.id.backImage)
    ImageView backImage;
    @BindView(R.id.contents_comment)
    ImageView contents_comment;
    @BindView(R.id.commentsCount)
    TextView commentsCount;
    @BindView(R.id.contents_like)
    ImageView contents_like;
    @BindView(R.id.likeCount)
    TextView likeCount;
    @BindView(R.id.CommentListView)
    RecyclerView CommentListView;
    @BindView(R.id.footerComment)
    ConstraintLayout footerComment;
    @BindView(R.id.contentsTest)
    EditText contentsTest;
    @BindView(R.id.commenttest)
    ConstraintLayout commenttest;
    @BindView(R.id.btn_back)
    ImageButton btn_back;
    @BindView(R.id.contents)
    TextView contents;
    @BindView(R.id.motionLikeView)
    FrameLayout motionView;
    @BindView(R.id.motionImage)
    ImageView motionImg;
    @BindView(R.id.backlayout)
    LinearLayout backlayout;
    @BindView(R.id.sliding)
    SlidingUpPanelLayout sliding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_detailcard );
        ButterKnife.bind( this );

        getStatusBarHeight();
        setActionbarMarginTop(topFrame);
        setActionbarMarginTop2(sliding);

        uuid = PropertyManagement.getUserId(this);
        Intent intent = getIntent();
        cardData = (CardDataModel) intent.getSerializableExtra( "cardInfo" );

        initView();
    }

    private void initView() {
        Glide.with( this ).load(cardData.image_url).crossFade().into(backImage);
        String currentColor = cardData.color;
        contents.setText(cardData.description);
        contents.setTextColor(Color.parseColor(currentColor));
        scroll  = new ScrollingMovementMethod();
        contents.setMovementMethod(scroll);
        contents.setOnClickListener(new DoubleClickListener() {
            @Override
            public void onSingleClick(View v) { }

            @Override
            public void onDoubleClick(View v) {
                motionLikeAnimation();

                if(!cardData.is_liked)
                {
                    postDoLike();
                }
            }
        });
        btn_back.setColorFilter(Color.parseColor(currentColor));
        getCommentHeader();



        mLinearLayoutManager = new LinearLayoutManager( this );
        mCommentAdapter = new CommentAdapter( DetailCardActivity.this, Glide.with( this ) , likeCount, commentsCount);
        CommentListView.setAdapter( mCommentAdapter );
        CommentListView.setNestedScrollingEnabled( false );
        CommentListView.setHasFixedSize( false );
        CommentListView.setLayoutManager( mLinearLayoutManager );
        CommentListView.setItemAnimator( new DefaultItemAnimator() );
        CommentListView.addItemDecoration( new RecyclerViewDecoration( 2 ) );


        getCommentContent();

        sliding.addPanelSlideListener( new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                //판낼 상태에 따른 컨텐츠 스크롤,백버튼 활성 비활성화
                if(slideOffset < 0.7) {
                    contents.setMovementMethod(scroll);
                    contents.setVisibility(View.VISIBLE);
                    btn_back.setVisibility(View.VISIBLE);
                }
                else {
                    contents.setMovementMethod(null);
                    contents.setVisibility(View.INVISIBLE);
                    btn_back.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
            }
        } );


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
    public void setActionbarMarginTop2(final View view){
        ConstraintLayout.LayoutParams topLayoutParams = (ConstraintLayout.LayoutParams) view.getLayoutParams();
        topLayoutParams.topMargin = StatusBarHeight;
        view.setLayoutParams(topLayoutParams);
    }

    public void getCommentHeader() {
        String board_id = cardData.id.toString();

        MooDumDumService.of().getContentsSelected( board_id, uuid ).enqueue( new Callback<CardDataModel>() {
            @Override
            public void onResponse(Call<CardDataModel> call, Response<CardDataModel> response) {
                CardDataModel items = response.body();
                likeCount.setText( items.like_count + "");
                commentsCount.setText( String.valueOf( items.comment_count ) );
                contents_like.setSelected(items.is_liked);
                cardData = items;
                if( items.is_liked ){
                    contents_like.setColorFilter(null);
                }
            }

            @Override
            public void onFailure(Call<CardDataModel> call, Throwable t) {

            }
        } );
    }
    public void PostComment() {
        String description = contentsTest.getText().toString();

        MooDumDumService.of().postComment( uuid, cardData.id, description ).enqueue( new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                Toast.makeText( getBaseContext(), "조문글을 남겼어요.", Toast.LENGTH_SHORT ).show();
                getCommentContent();
                getCommentHeader();
                contentsTest.setText( null );
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText( getBaseContext(), "댓글 등록에 실패했습니다.", Toast.LENGTH_SHORT ).show();
                Log.d( "@CommentSaveError", "댓글 등록 실패" );
            }
        } );
    }
    public void getCommentContent() {
        MooDumDumService.of().getComment( cardData.id, uuid ).enqueue(new Callback<CommentModel>() {
            @Override
            public void onResponse(Call<CommentModel> call, Response<CommentModel> response) {
                if (response.isSuccessful()) {
                    final CommentModel items = response.body();
                    mCommentAdapter.setPostList( items.getResult() );
                }
                else
                    Toast.makeText( getBaseContext(), uuid, Toast.LENGTH_SHORT ).show();
            }

            @Override
            public void onFailure(Call<CommentModel> call, Throwable t) {
                Log.d("getCommentContent()","ServerFailure");
            }
        } );
    }
    public void motionLikeAnimation(){
        motionView.setVisibility(View.VISIBLE);
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(motionImg,1);
        Glide.with( this ).load(R.raw.motion_like).into(imageViewTarget);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                motionView.setVisibility(View.INVISIBLE);
            }
        },2800);
    }
    public void postDoLike()
    {
        MooDumDumService.of().postDoLike( cardData.id, uuid ).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("postDoLike()","Success!");
                    getCommentHeader();
                }
                else{
                    Log.e("postDoLike()Fail",response.message());
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.e( "PostDoLike Fail",  t.toString() );
            }
        } );
    }
    public void cancelContentsLike(){
        MooDumDumService.of().deleteContentsLike( uuid, cardData.id ).enqueue( new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
//                getCommentContent();
                getCommentHeader();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.d( "CancelLikeOnFailure", t.toString() );
            }
        } );
    }

    @Override
    public void onBackPressed() {
        closeDetailCard();
    }

    @OnClick(R.id.btn_back)
    public void closeDetailCard() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("IS_LIKE",cardData.is_liked);
        resultIntent.putExtra("COUNT_LIKE",Integer.parseInt(likeCount.getText().toString()));
        resultIntent.putExtra("COUNT_COMMENT",Integer.parseInt(commentsCount.getText().toString()));
        setResult(RESULT_OK,resultIntent);
        finish();
        overridePendingTransition(R.anim.load_fadein,R.anim.load_fadeout);
    }

    @OnClick(R.id.onClickToPostComment)
    public void onViewClicked() {
        PostComment();
    }

    @OnClick({R.id.contents_like,R.id.likeCount})
    public void setLike() {
        if (cardData.is_liked) {
            cancelContentsLike();
        } else {
            motionLikeAnimation();
            postDoLike();
        }
    }

    @OnClick(R.id.btn_more)
    public void onClickBtnMore()
    {
        dialog = CustomDialog.closeDialog(dialog);
        dialog = new CustomDialog(this, R.string.dialog_delete_title, R.string.dialog_delete, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = CustomDialog.closeDialog(dialog);
                //글 삭제
            }
        });
        dialog.show();
    }
}
