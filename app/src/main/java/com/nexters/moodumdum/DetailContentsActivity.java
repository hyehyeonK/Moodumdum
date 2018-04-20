package com.nexters.moodumdum;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
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
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.nexters.moodumdum.adpater.CommentAdapter;
import com.nexters.moodumdum.adpater.StackCardAdapter;
import com.nexters.moodumdum.anim.RecyclerViewDecoration;
import com.nexters.moodumdum.api.MooDumDumService;
import com.nexters.moodumdum.factory.DeviceUuidFactory;
import com.nexters.moodumdum.model.CommentModel;
import com.nexters.moodumdum.model.ContentsModel;
import com.nexters.moodumdum.model.DetailCardInfoDAO;
import com.nexters.moodumdum.model.ServerResponse;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailContentsActivity extends AppCompatActivity {
    private GestureDetector gestureDetector;
    public RequestManager mGlideRequestManager;
    static PostLike postLike;
    ScrollingMovementMethod scroll;
    String currentColor;
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
    ImageView motionView;
    @BindView(R.id.backlayout)
    LinearLayout backlayout;
    String beforeAct;
    DetailCardInfoDAO detailCardInfo;
    @BindView(R.id.sliding)
    SlidingUpPanelLayout sliding;

    private StackCardAdapter stackCardAdapter;
    List<ContentsModel.Result> results = new ArrayList<>();

    private CommentAdapter mCommentAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    BigInteger board_id;
    String uuid;
    List<ContentsModel.Result> contentsResults = new ArrayList<>();
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
        detailCardInfo = (DetailCardInfoDAO) intent.getSerializableExtra( "cardInfo" );
        beforeAct = intent.getStringExtra("beforeAct");
//        contentsTest.getBackground().setColorFilter(Color.parseColor("#ffffffff"), PorterDuff.Mode.SRC_ATOP);

        stackCardAdapter = new StackCardAdapter( DetailContentsActivity.this, mGlideRequestManager );
        mGlideRequestManager = Glide.with( this );
        gestureDetector = new GestureDetector(this, new GestureListener());
        postLike = new PostLike();
        uuid = ((MainActivity) MainActivity.MainAct).getUUID();
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

//        currentView = detailCardInfo.getCurrnetView();
        //컨텐츠텍스트 초기화 및 스크롤 생성
        currentColor = detailCardInfo.getColor();
        contents.setText(detailCardInfo.getDescription());
        contents.setTextColor(Color.parseColor(currentColor));
        scroll  = new ScrollingMovementMethod();
        contents.setMovementMethod(scroll);
        View.OnTouchListener gestureListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent evnet) {
                return gestureDetector.onTouchEvent(evnet);

            }
        };
        backlayout.setOnTouchListener(gestureListener);

        btn_back.setColorFilter(Color.parseColor(currentColor));
        getCommentHeader();

        if(detailCardInfo.getBackImagUrl() !=null){
            backImage.setVisibility(View.VISIBLE);
            mGlideRequestManager.load(detailCardInfo.getBackImagUrl())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)// 디스크 캐시 저장 off
                    .skipMemoryCache(true)// 메모리 캐시 저장 off
                    .into(backImage);
        }

        mLinearLayoutManager = new LinearLayoutManager( this );
        mCommentAdapter = new CommentAdapter( DetailContentsActivity.this, mGlideRequestManager );
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

    public void getCommentHeader() {
        String board_id = detailCardInfo.getBoard_id().toString();

        MooDumDumService.of().getContentsSelected( board_id, uuid ).enqueue( new Callback<ContentsModel.Result>() {
            @Override
            public void onResponse(Call<ContentsModel.Result> call, Response<ContentsModel.Result> response) {
                ContentsModel.Result items = response.body();
                Log.d("SDDSF###", items.toString());
                likeCount.setText( items.getLike_count() + "");
                commentsCount.setText( String.valueOf( items.getComment_count() ) );
                if( items.isIs_liked()){
                    mGlideRequestManager.load(R.drawable.like_after)
                            .into(contents_like);
                    contents_like.setColorFilter(null);
                }
            }

            @Override
            public void onFailure(Call<ContentsModel.Result> call, Throwable t) {

            }
        } );
    }


    public void getCommentContent() {
        board_id = detailCardInfo.getBoard_id();
        MooDumDumService.of().getComment( board_id ).enqueue( new Callback<CommentModel>() {
            @Override
            public void onResponse(Call<CommentModel> call, Response<CommentModel> response) {
                if (response.isSuccessful()) {
                    final CommentModel items = response.body();
                    mCommentAdapter.setPostList( items.getResult() );
                }
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
        board_id = detailCardInfo.getBoard_id();
        DeviceUuidFactory uuidFactory = new DeviceUuidFactory( this );
        UUID uuid = uuidFactory.getDeviceUuid();

        String user = uuid + "";
        String description = contentsTest.getText().toString();

        MooDumDumService.of().postComment( user, board_id, description ).enqueue( new Callback<ServerResponse>() {
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

            motionLikeAnimation();
            return true;
        }
    }
    public void motionLikeAnimation(){
        postLike.PostComment(detailCardInfo.getBoard_id(), detailCardInfo);
        motionView.setVisibility(View.VISIBLE);
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(motionView,1);
        mGlideRequestManager.load(R.raw.motion_like)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)// 디스크 캐시 저장 off
//                .skipMemoryCache(true)// 메모리 캐시 저장 off
                .into(imageViewTarget);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                motionView.setVisibility(View.GONE);
                getCommentHeader();
            }
        },2800);
    }

    public void finishDeatailCard(){
        detailCardInfo.setLikeCount(Integer.parseInt(likeCount.getText().toString()));
        detailCardInfo.setCommentCount(Integer.parseInt(commentsCount.getText().toString()));

        switch (beforeAct){
            case "Main":
                ((MainCardStackFragment) MainCardStackFragment.MainCardFragment).setRefreshInfo(detailCardInfo); break;
            case "Category":
                ((CategorySelectedActivityRV) CategorySelectedActivityRV.activity).setRefreshInfo(detailCardInfo); break;
            case  "MyPage":
                break;
        }

        this.finish();
    }
    @Override
    public void onBackPressed() {
        finishDeatailCard();
    }
    @OnClick(R.id.btn_back)
    public void closeDetaileCard(){
        finishDeatailCard();
    }


    @OnClick({R.id.contents_like,R.id.likeCount})
    public void setLike() {
        motionLikeAnimation();
    }
}


