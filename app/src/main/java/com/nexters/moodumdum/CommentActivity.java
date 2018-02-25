package com.nexters.moodumdum;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nexters.moodumdum.adpater.CommentAdapter;
import com.nexters.moodumdum.api.MooDumDumService;
import com.nexters.moodumdum.model.CommentModel;
import com.nexters.moodumdum.model.ContentsModel;
import com.nexters.moodumdum.model.PostCommentModel;
import com.nexters.moodumdum.model.ServerResponse;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentActivity extends AppCompatActivity {
    @BindView(R.id.imageView1)
    ImageView imageView1;
    @BindView(R.id.commentsCount)
    TextView commentsCount;
    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.likeCount)
    TextView likeCount;
    @BindView(R.id.headerComment)
    LinearLayout headerComment;
    @BindView(R.id.CommentListView)
    RecyclerView CommentListView;
    @BindView(R.id.footerComment)
    LinearLayout footerComment;
    @BindView(R.id.contentsTest)
    EditText contentsTest;
    @BindView(R.id.commenttest)
    ConstraintLayout commenttest;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.onClickToPostComment)
    Button onClickToPostComment;

    PostCommentModel commentModel;

    private CommentAdapter mCommentAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    BigInteger board_id;

    List<ContentsModel.Result> contentsResults = new ArrayList<>();
    List<CommentModel.Result> commentResults = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_comment );
        ButterKnife.bind( this );


        Intent intent = getIntent();
        commentModel = (PostCommentModel) intent.getSerializableExtra("newComment");

        initView();

    }

    public void initView() {


        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        commenttest.setMaxHeight( height / 2 );
        mLinearLayoutManager = new LinearLayoutManager( this );
        mCommentAdapter = new CommentAdapter( CommentActivity.this );
        CommentListView.setAdapter( mCommentAdapter );
        CommentListView.setNestedScrollingEnabled( false );
        CommentListView.setHasFixedSize( false );
        CommentListView.setLayoutManager( mLinearLayoutManager );
        CommentListView.setItemAnimator( new DefaultItemAnimator() );
        CommentListView.addItemDecoration( new RecyclerViwDecoraiton( 2 ) );

        getCommentContent();

    }

//    public void getCommentHeader() {
//        Intent intent = getIntent();
//        board_id = intent.getStringExtra( "board_id" );
//        MooDumDumService.of().getContentsSelected( "22" ).enqueue( new Callback<ContentsModel.Result>() {
//            @Override
//            public void onResponse(Call<ContentsModel.Result> call, Response<ContentsModel.Result> response) {
//                if (response.isSuccessful()) {
//
//                    final ContentsModel items = response.body();
//
////                    Gson gson = new Gson();
//
////                    Toast.makeText( getBaseContext(),contentsResults, Toast.LENGTH_SHORT ).show();
////
//                }
//            }
//            @Override
//            public void onFailure(Call<ContentsModel> call, Throwable t) {
//
//            }
//        } );
//
//    }

    public void getCommentContent() {
        board_id = commentModel.getBoard_id();
        MooDumDumService.of().getComment( board_id ).enqueue( new Callback<CommentModel>() {
            @Override
            public void onResponse(Call<CommentModel> call, Response<CommentModel> response) {
                if (response.isSuccessful()) {
                    Toast.makeText( getBaseContext(), commentModel.getBoard_id().toString(), Toast.LENGTH_SHORT ).show();
                    final CommentModel items = response.body();
                    mCommentAdapter.setPostList( items.getResult() );
                }
            }

            @Override
            public void onFailure(Call<CommentModel> call, Throwable t) {
                Toast.makeText( getBaseContext(), "댓글 불러오기 실패!", Toast.LENGTH_SHORT ).show();
            }
        } );
    }

    @OnClick(R.id.onClickToPostComment)
    public void onViewClicked() {
        PostComment();
    }


//    }
    public void PostComment() {
        board_id = commentModel.getBoard_id();
        String user = "KSY";
        String name = "소연이에유";
        String description = contentsTest.getText().toString();

        MooDumDumService.of().postComment( board_id, user, name, description ).enqueue( new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                Toast.makeText( getBaseContext(), "댓글을 등록했습니다.", Toast.LENGTH_SHORT ).show();
                getCommentContent();

                contentsTest.setText( null );
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

            }
        } );
    }



}







