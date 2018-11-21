package com.nexters.moodumdum;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nexters.moodumdum.adpater.CommentAdapter;
import com.nexters.moodumdum.adpater.StackCardAdapter;
import com.nexters.moodumdum.anim.RecyclerViewDecoration;
import com.nexters.moodumdum.api.MooDumDumService;
import com.nexters.moodumdum.common.PropertyManagement;
import com.nexters.moodumdum.model.CardDataModel;
import com.nexters.moodumdum.model.CommentModel;
import com.nexters.moodumdum.model.ContentsModel;
import com.nexters.moodumdum.model.ServerResponse;
import com.nexters.moodumdum.utils.PostLike;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailContentsActivity extends AppCompatActivity {
    private GestureDetector gestureDetector;
//    public RequestManager mGlideRequestManager;
    static PostLike postLike;
    ScrollingMovementMethod scroll;
    String currentColor;
    public TextView countLike, countComment;
    public int StatusBarHeight;
//    View currentView;

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
    @BindView(R.id.motion)
    ConstraintLayout motionView;
    @BindView(R.id.backlayout)
    LinearLayout backlayout;
    String beforeAct;
    CardDataModel detailCardInfo;
    @BindView(R.id.sliding)
    SlidingUpPanelLayout sliding;

    private StackCardAdapter stackCardAdapter;
    List<CardDataModel> results = new ArrayList<>();

    private CommentAdapter mCommentAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    BigInteger board_id;
    String uuid;
    List<CardDataModel> contentsResults = new ArrayList<>();
    List<CommentModel.Result> commentResults = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_detailcard );
        ButterKnife.bind( this );
        getStatusBarHeight();
        setActionbarMarginTop(topFrame);
        setActionbarMarginTop2(sliding);
        Intent intent = getIntent();
        detailCardInfo = (CardDataModel) intent.getSerializableExtra( "cardInfo" );
        beforeAct = intent.getStringExtra("beforeAct");
//        contentsTest.getBackground().setColorFilter(Color.parseColor("#ffffffff"), PorterDuff.Mode.SRC_ATOP);

        stackCardAdapter = new StackCardAdapter( DetailContentsActivity.this, Glide.with( this ) , this);
        board_id = detailCardInfo.id;
        gestureDetector = new GestureDetector(this, new GestureListener());
        postLike = new PostLike();
        uuid = PropertyManagement.getUserId(DetailContentsActivity.this);
        initView();
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
    public void initView() {
//        switch (beforeAct){
//            case "Main":
//                mGlideRequestManager = Glide.with( this );
//                break;
//            case "Category":
//                CategorySelectedActivityRV categorySelectedActivityRV = (CategorySelectedActivityRV) CategorySelectedActivityRV.activity;
//                mGlideRequestManager = categorySelectedActivityRV.mGlideRequestManager;
//                break;
//            case  "MyPage":
//                Mypage myPage = (Mypage) Mypage.activity;
//                mGlideRequestManager = myPage.glideRequestManager;
//                break;
//        }
        if(detailCardInfo.image_url !=null){
            Glide.with( this ).load(detailCardInfo.image_url).crossFade()
                    .into(backImage);
        } else {
            backImage.setVisibility(View.INVISIBLE);
        }


//        currentView = detailCardInfo.getCurrnetView();
        //컨텐츠텍스트 초기화 및 스크롤 생성
        currentColor = detailCardInfo.background_color;
        contents.setText(detailCardInfo.description);
        contents.setTextColor(Color.parseColor(currentColor));
        scroll  = new ScrollingMovementMethod();
        contents.setMovementMethod(scroll);

        btn_back.setColorFilter(Color.parseColor(currentColor));
        getCommentHeader();



        mLinearLayoutManager = new LinearLayoutManager( this );
        mCommentAdapter = new CommentAdapter( DetailContentsActivity.this, Glide.with( this ) , likeCount, commentsCount);
        CommentListView.setAdapter( mCommentAdapter );
        CommentListView.setNestedScrollingEnabled( false );
        CommentListView.setHasFixedSize( false );
        CommentListView.setLayoutManager( mLinearLayoutManager );
        CommentListView.setItemAnimator( new DefaultItemAnimator() );
        CommentListView.addItemDecoration( new RecyclerViewDecoration( 2 ) );

//        getCommentHeader();
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

//    public void getCommentHeader() {
//        String board_id = detailCardInfo.getBoard_id().toString();
//        MooDumDumService.of().getContentsSelected( board_id, uuid ).enqueue( new Callback<ContentsModel.Result>() {
//            @Override
//            public void onResponse(Call<ContentsModel.Result> call, Response<ContentsModel.Result> response) {
//                ContentsModel.Result items = response.body();
//                likeCount.setText( items.getLike_count() + "");
//                commentsCount.setText( String.valueOf( items.getComment_count() ) );
//                contents_like.setSelected(items.isIs_liked());
//                if( items.isIs_liked()){
////                    mGlideRequestManager.load(R.drawable.like_after)
////                            .into(contents_like);
//                    contents_like.setColorFilter(null);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ContentsModel.Result> call, Throwable t) {
//
//            }
//        } );
//    }
public void getCommentHeader() {
    String board_id = detailCardInfo.id.toString();

    MooDumDumService.of().getContentsSelected( board_id, uuid ).enqueue( new Callback<ContentsModel.Result>() {
        @Override
        public void onResponse(Call<ContentsModel.Result> call, Response<ContentsModel.Result> response) {
            ContentsModel.Result items = response.body();
            likeCount.setText( items.getLike_count() + "");
            commentsCount.setText( String.valueOf( items.getComment_count() ) );
            contents_like.setSelected(items.isIs_liked());
            if( items.isIs_liked()){
//                    mGlideRequestManager.load(R.drawable.like_after)
//                            .into(contents_like);
                contents_like.setColorFilter(null);
            }
        }

        @Override
        public void onFailure(Call<ContentsModel.Result> call, Throwable t) {

        }
    } );
}
    public void reLoadCommentCount(String board_id, TextView likeCount, TextView commentsCount ) {
        this.countLike = likeCount;
        this.countComment = commentsCount;
        Log.d(">?????",likeCount +"");
        MooDumDumService.of().getContentsSelected( board_id, uuid ).enqueue( new Callback<ContentsModel.Result>() {
            @Override
            public void onResponse(Call<ContentsModel.Result> call, Response<ContentsModel.Result> response) {
                ContentsModel.Result items = response.body();
                Log.d("count!!!!",items.getLike_count() +"" );
                countLike.setText( items.getLike_count() + "");
                countComment.setText( String.valueOf( items.getComment_count() ) );
//                PropertyManagement.putCommentLikeCount( getBaseContext(), countLike.getText().toString() );
            }

            @Override
            public void onFailure(Call<ContentsModel.Result> call, Throwable t) {

            }
        } );
    }

    public void setCommentLike(int count) {
        String cc = String.valueOf( count );
        commentsCount.setText( cc );
    }

    // uuid로 불러오기
    public void getCommentContent() {
        board_id = detailCardInfo.id;
        MooDumDumService.of().getComment( board_id,uuid).enqueue( new Callback<CommentModel>() {
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

    @OnClick(R.id.onClickToPostComment)
    public void onViewClicked() {
        PostComment();
    }

    public void PostComment() {
        board_id = detailCardInfo.id;
        String description = contentsTest.getText().toString();

        MooDumDumService.of().postComment( uuid, board_id, description ).enqueue( new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                Toast.makeText( getBaseContext(), "조문글을 남겼어요.", Toast.LENGTH_SHORT ).show();
                getCommentContent();
                getCommentHeader();
                contentsTest.setText( null );
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.d( "@CommentSaveError", "댓글 등록 실패" );
            }
        } );
    }
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return gestureDetector.onTouchEvent(e);
    }
    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
        // event when double tap occurs
        @Override
        public boolean onDoubleTap(MotionEvent e) {

//            motionLikeAnimation();
            return true;
        }
    }

    //글 좋아요
//    public void motionLikeAnimation(){
//        postLike.PostComment(detailCardInfo.id, detailCardInfo, DetailContentsActivity.this);
//        motionView.setVisibility(View.VISIBLE);
//        ImageView motionImageView = motionView.findViewById(R.id.motionImage);
//        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(motionImageView,1);
//        Glide.with( this ).load(R.raw.motion_like)
////                .diskCacheStrategy(DiskCacheStrategy.NONE)// 디스크 캐시 저장 off
////                .skipMemoryCache(true)// 메모리 캐시 저장 off
//                .into(imageViewTarget);
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                motionView.setVisibility(View.GONE);
//                getCommentHeader();
//            }
//        },2800);
//    }
    //글 좋아요 취소
    public void cancelContentsLike(){
        board_id = detailCardInfo.id;
        MooDumDumService.of().deleteContentsLike( uuid, board_id ).enqueue( new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                detailCardInfo.is_liked = false;
                getCommentContent();
                getCommentHeader();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.d( "@cancelLikeOnFailure", "좋아요 취소 실패" );
            }
        } );
    }

//    public void finishDeatailCard(){
//        detailCardInfo.setLikeCount(Integer.parseInt(likeCount.getText().toString()));
//        detailCardInfo.setCommentCount(Integer.parseInt(commentsCount.getText().toString()));
//        Log.d("LIKE####","???"+detailCardInfo.getIsLike());
//
//        switch (beforeAct){
//            case "Main":
////                ((MainCardStackFragment) MainCardStackFragment.MainCardFragment).setRefreshInfo(detailCardInfo); break;
//            case "Category":
//                ((CategorySelectedActivityRV) CategorySelectedActivityRV.activity).setRefreshInfo(detailCardInfo); break;
//            case  "MyPage":
//                break;
//        }
//        finishDeatailCard();
////        this.finish();
//        overridePendingTransition(R.anim.load_fadein, R.anim.load_fadeout);
//    }
    @Override
    public void onBackPressed() {
        this.finish();
    }
    @OnClick(R.id.btn_back)
    public void closeDetaileCard(){
        this.finish();
    }


    @OnClick({R.id.contents_like,R.id.likeCount})
    public void setLike() {
        if (contents_like.isSelected()) {
            cancelContentsLike();
        } else {
//            motionLikeAnimation();
        }
    }
}


