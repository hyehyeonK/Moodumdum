package com.nexters.moodumdum;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nexters.moodumdum.adpater.CommentAdapter;
import com.nexters.moodumdum.api.MooDumDumService;
import com.nexters.moodumdum.model.CommentModel;
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

    PostCommentModel commentModel;

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
    @BindView(R.id.onClickToPostComment)
    ImageButton onClickToPostComment;

    private CommentAdapter mCommentAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    String board_id = "";

    List<CommentModel.Result> results = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_comment );
        ButterKnife.bind( this );

//        Intent intent = getIntent();
//        board_id = intent.getStringExtra( "board_id" );

        initView();
        getCommentTest();

//        commentsCount.setText( intent.getStringExtra( "board_id" ) );
    }

    public void initView() {
        mLinearLayoutManager = new LinearLayoutManager( this );
        mCommentAdapter = new CommentAdapter( CommentActivity.this );
        CommentListView.setAdapter( mCommentAdapter );
        CommentListView.setNestedScrollingEnabled( false );
        CommentListView.setHasFixedSize( true );
        CommentListView.setLayoutManager( mLinearLayoutManager );
        CommentListView.setItemAnimator( new DefaultItemAnimator() );
        CommentListView.addItemDecoration( new RecyclerViwDecoraiton( 2 ) );

    }

    public void getCommentTest() {
        Intent intent = getIntent();
        board_id = intent.getStringExtra( "board_id" );
        MooDumDumService.of().getComment( board_id ).enqueue( new Callback<CommentModel>() {
            @Override
            public void onResponse(Call<CommentModel> call, Response<CommentModel> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getBaseContext(), "댓글 불러오기 성공!", Toast.LENGTH_SHORT).show();
                    final CommentModel items = response.body();
                    mCommentAdapter.setPostList( items.getResult() );
                }
            }

            @Override
            public void onFailure(Call<CommentModel> call, Throwable t) {
                Toast.makeText(getBaseContext(), "댓글 불러오기 실패!", Toast.LENGTH_SHORT).show();
            }
        });
//
//        MooDumDumService.of().getCommentAll().enqueue( new Callback<CommentModel>() {
//            @Override
//            public void onResponse(Call<CommentModel> call, Response<CommentModel> response) {
//                final CommentModel items = response.body();
//                results = items.getResult();
//                Gson gson = new Gson();
//                String data = gson.toJson( results );
//
//                Log.d( "RESULT@@@@@", data );
//
//                mCommentAdapter.setPostList( results );
//                likeCount.setText( board_id );
//            }
//
//            @Override
//            public void onFailure(Call<CommentModel> call, Throwable t) {
//
//            }
//        } );
    }

    @OnClick(R.id.onClickToPostComment)
    public void onViewClicked() {
        PostComment();
    }

    private void PostComment() {
        BigInteger id = commentModel.getBoard_id();
        String user = commentModel.getUser();
        String name = commentModel.getName();
        String description = commentModel.getDescription();

        MooDumDumService.of().postComment( commentModel ).enqueue( new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                Toast.makeText(getBaseContext(), "댓글 작성 성공!", Toast.LENGTH_SHORT).show();
                Log.d("postComment",response.message());
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.d("postCommentError","error");
            }
        } );
    }

}







