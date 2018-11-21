package com.nexters.moodumdum.adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.RequestManager;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.nexters.moodumdum.R;
import com.nexters.moodumdum.api.MooDumDumService;
import com.nexters.moodumdum.common.PropertyManagement;
import com.nexters.moodumdum.model.CommentModel;
import com.nexters.moodumdum.model.ServerResponse;
import com.nexters.moodumdum.utils.PostCommentLike;
import com.nexters.moodumdum.views.DetailCardActivity;

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
 * Created by User on 2018-02-22.
 */

public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<CommentModel.Result> results = new ArrayList<>();
    static PostCommentLike postCommentLike;
    public static RequestManager glideRequestManager;
    private TextView like,comment;
    public CommentAdapter(Context context, RequestManager glideRequestManager, TextView like, TextView comment) {
        this.context = context;
        this.glideRequestManager = glideRequestManager;
        postCommentLike = new PostCommentLike();
        this.like = like;
        this.comment = comment;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder( LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_comment, parent, false ) );
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ItemViewHolder viewHolder = (ItemViewHolder) holder;
        final CommentModel.Result item = results.get( position );

        viewHolder.WriterOfComment.setText( item.getUser().getNickName() );
        viewHolder.contentOfComment.setText( item.getDescription() );
        String user = PropertyManagement.getUserId( context );

        if (!item.getUser().getUuid().equals( user ))
            viewHolder.swipeMenu.setSwipeEnable( false );
        else
            viewHolder.swipeMenu.setSwipeEnable( true );

        viewHolder.likeCount.setText( "공감 " + item.getLike_count() + "개" );
        viewHolder.btnLike.setSelected( item.isIs_liked() );
        if (item.isIs_liked()) {
            viewHolder.btnLike.setColorFilter( null );
        }
        viewHolder.contentOfComment.setTag(item.getBoard_id());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void setPostList(List<CommentModel.Result> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        View view;
        @BindView(R.id.WriterOfComment)
        TextView WriterOfComment;
        @BindView(R.id.contentOfComment)
        TextView contentOfComment;
        @BindView(R.id.btnDel)
        Button btnDel;
        @BindView(R.id.btnLike)
        ImageView btnLike;
        @BindView(R.id.likeCount)
        TextView likeCount;
        @BindView(R.id.swipeMenu)
        SwipeMenuLayout swipeMenu;

        public ItemViewHolder(final View itemView) {
            super( itemView );
            this.view = itemView;
            ButterKnife.bind( this, view );
        }


        @OnClick(R.id.btnLike)
        public void onBtnLikeClicked() {
            int position = getAdapterPosition();
            final CommentModel.Result item = results.get( position );
            BigInteger comment_id = item.getId();
            final String user = PropertyManagement.getUserId( context );

            if(!item.isIs_liked()) {
                postCommentLike.PostCommentLike( comment_id, item.getLike_count(), btnLike, likeCount, glideRequestManager, context );
                MooDumDumService.of().getComment( item.getBoard_id(), user ).enqueue( new Callback<CommentModel>() {
                    @Override
                    public void onResponse(Call<CommentModel> call, Response<CommentModel> response) {
                        setPostList( response.body().getResult() );
                        btnLike.setSelected( true );
                    }

                    @Override
                    public void onFailure(Call<CommentModel> call, Throwable t) {
                    }
                } );
            }
            else {
                MooDumDumService.of().deleteCommentLike( user, comment_id ).enqueue( new Callback<ServerResponse>() {
                    @Override
                    public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                        int like = item.getLike_count() - 1;
                        likeCount.setText("공감 " + like + "개");
                        btnLike.setImageResource( R.drawable.btn_like );
                        btnLike.setSelected( false );
                    }

                    @Override
                    public void onFailure(Call<ServerResponse> call, Throwable t) {

                    }
                } );
            }
        }

        @OnClick(R.id.btnDel)
        public void onBtnDelClicked() {
            int position = getAdapterPosition();
            final CommentModel.Result item = results.get( position );
            BigInteger comment_id = item.getId();
            final String user = PropertyManagement.getUserId( context );

                MooDumDumService.of().delComment( comment_id ).enqueue( new Callback<ServerResponse>() {
                    @Override
                    public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                        MooDumDumService.of().getComment( item.getBoard_id(), user ).enqueue( new Callback<CommentModel>() {
                            @Override
                            public void onResponse(Call<CommentModel> call, Response<CommentModel> response) {
                                setPostList( response.body().getResult() );
                                String count = String.valueOf( results.size() );
//                            PropertyManagement.putCommentLikeCount( context, String.valueOf( item.getLike_count() ) );
                                DetailCardActivity detailCardActivity= new DetailCardActivity();
//                                detailCardActivity.reLoadCommentCount( contentOfComment.getTag().toString(), like, comment );
                                detailCardActivity.getCommentHeader();
                                Toast.makeText( context, "조문글을 삭제했어요.", Toast.LENGTH_SHORT ).show();
                            }

                            @Override
                            public void onFailure(Call<CommentModel> call, Throwable t) {
                            }
                        } );
                    }

                    @Override
                    public void onFailure(Call<ServerResponse> call, Throwable t) {
                    }
                } );

        }
    }
}


