package com.nexters.moodumdum.views;

import android.content.Context;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
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
import com.nexters.moodumdum.model.UserModel;
import com.nexters.moodumdum.utils.CustomDialog;
import com.nexters.moodumdum.utils.CustomReportDialog;
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
    private Context context;
    private CardDataModel cardData;
    private CommentAdapter mCommentAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    ScrollingMovementMethod scroll;
    private String uuid;
    private int StatusBarHeight;
    CustomDialog dialog;
    CustomReportDialog reportDialog;
    PopupMenu popup;

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
    @BindView(R.id.btn_more)
    ImageButton btn_more;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_detailcard );
        ButterKnife.bind( this );

        context = this;
        getStatusBarHeight();
        setActionbarMarginTop(topFrame);
        setActionbarMarginTop2(sliding);

        uuid = PropertyManagement.getUserId(context);
        Intent intent = getIntent();
        cardData = (CardDataModel) intent.getSerializableExtra( "cardInfo" );
        initView();
    }

    private void initView() {
        if(cardData.user.user.equals(uuid))
        {
            btn_more.setSelected(true);
        }
        popup = new PopupMenu(context, btn_more);
        popup.getMenuInflater().inflate(R.menu.menu_more, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.block_user:
                        dialog = CustomDialog.closeDialog(dialog);
                        dialog = new CustomDialog(context, R.string.dialog_block_title, R.string.dialog_block, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                blockUser();
                                dialog = CustomDialog.closeDialog(dialog);
                            }
                        });
                        dialog.show();
                        return true;
                    case R.id.report_post:
                        //신고 다이얼로그
                        reportDialog = CustomReportDialog.closeDialog(reportDialog);
                        reportDialog = new CustomReportDialog(getBaseContext(), cardData.user.user, cardData.id);
                        reportDialog.show();
                        return true;
                }
                return false;
            }
        });
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
        btn_more.setColorFilter(Color.parseColor(currentColor));
        getCommentHeader();



        mLinearLayoutManager = new LinearLayoutManager( context );
        mCommentAdapter = new CommentAdapter( context, Glide.with( this ) , likeCount, commentsCount);
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
                    btn_more.setVisibility(View.VISIBLE);
                }
                else {
                    contents.setMovementMethod(null);
                    contents.setVisibility(View.INVISIBLE);
                    btn_back.setVisibility(View.INVISIBLE);
                    btn_more.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
            }
        } );


    }
    private void getStatusBarHeight(){
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

    private void setActionbarMarginTop(final View view){
        FrameLayout.LayoutParams topLayoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        topLayoutParams.topMargin = StatusBarHeight;
        view.setLayoutParams(topLayoutParams);
    }
    private void setActionbarMarginTop2(final View view){
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
    private void PostComment() {
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
    private void getCommentContent() {
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
    private void motionLikeAnimation(){
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
    private void postDoLike()
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

    private void cancelContentsLike(){
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

    //차단하기
    private void blockUser()
    {
        final UserModel user = cardData.user;
        MooDumDumService.of().blockUser( uuid, user.user).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.isSuccessful())
                {
                    resetStackCard();
                    Toast.makeText(context,"'" + user.name + "'을 차단했습니다.",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(context,"'" + user.name + "'을 차단 실패."+response.message(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.e("BlockUserFailure", t.getMessage());
            }
        });
    }

    private  void deleteContents()
    {
        MooDumDumService.of().deleteMyContents(cardData.id).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.isSuccessful())
                {
                    resetStackCard();
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("_position",getIntent().getIntExtra("_position",-1));
                    setResult(RESULT_OK, resultIntent);
                    finish();
                    Toast.makeText(context,"당신의 기억을 영원히 묻었어요.",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.e("DeleteContentsFailure", t.getMessage());
            }
        });
    }


    private void resetStackCard()
    {
        StackCardActivity SA =(StackCardActivity)StackCardActivity._StackCardActivity;
        SA.refreshActivity();
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
        resultIntent.putExtra("_position",getIntent().getIntExtra("_position",-1));
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
        //MyContents
        if(btn_more.isSelected())
        {
            dialog = CustomDialog.closeDialog(dialog);
            dialog = new CustomDialog(context, R.string.dialog_delete_title, R.string.dialog_delete, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //글 삭제
                    deleteContents();
                    dialog = CustomDialog.closeDialog(dialog);
                }
            });
            dialog.show();
        }
        else
        {
            popup.show();
        }

    }
}
