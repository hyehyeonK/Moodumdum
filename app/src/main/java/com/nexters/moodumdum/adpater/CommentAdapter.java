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
import com.nexters.moodumdum.MainActivity;
import com.nexters.moodumdum.PostCommentLike;
import com.nexters.moodumdum.R;
import com.nexters.moodumdum.api.MooDumDumService;
import com.nexters.moodumdum.model.CommentModel;
import com.nexters.moodumdum.model.ServerResponse;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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

    public CommentAdapter(Context context, RequestManager glideRequestManager) {
        this.context = context;
        this.glideRequestManager = glideRequestManager;
        postCommentLike = new PostCommentLike();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder( LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_comment, parent, false ) );
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ItemViewHolder viewHolder = (ItemViewHolder) holder;
//        final CommentUserModel.Result commentUserItem = CommentUserResults.get( position );
        final CommentModel.Result item = results.get( position );
        viewHolder.WriterOfComment.setText( item.getUser().getNickName() );
        viewHolder.contentOfComment.setText( item.getDescription() );

        String user = ((MainActivity) MainActivity.MainAct).getUUID();

        if(!item.getUser().getUuid().equals( user )) viewHolder.swipeMenu.setSwipeEnable( false );
        else viewHolder.swipeMenu.setSwipeEnable( true );

        // 여기 수정
        if (item.isIs_liked()) {
            viewHolder.imageLike.setImageResource( R.drawable.like_after );
//            glideRequestManager.load( R.drawable.like_after ).into( viewHolder.imageLike );
            viewHolder.imageLike.setColorFilter( null );
        }
//
        viewHolder.likeCount.setText( "공감 " + item.getLike_count() + "개" );

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void setPostList(List<CommentModel.Result> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View view;

        @BindView(R.id.WriterOfComment)
        TextView WriterOfComment;
        @BindView(R.id.contentOfComment)
        TextView contentOfComment;
        @BindView(R.id.btnDel)
        Button btnDel;
        @BindView(R.id.btnLike)
        ImageView imageLike;
        @BindView(R.id.likeCount)
        TextView likeCount;
        @BindView(R.id.swipeMenu)
        SwipeMenuLayout swipeMenu;

        public ItemViewHolder(final View itemView) {
            super( itemView );
            this.view = itemView;
            ButterKnife.bind( this, view );

            btnDel.setOnClickListener( this );
            imageLike.setOnClickListener( this );
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            final CommentModel.Result item = results.get( position );
            BigInteger comment_id = item.getId();
            String user = ((MainActivity) MainActivity.MainAct).getUUID();

            switch (v.getId()) {
                case R.id.btnDel:
                        MooDumDumService.of().delComment( comment_id ).enqueue( new Callback<ServerResponse>() {
                            @Override
                            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                                Toast.makeText( context, "조문글을 삭제했어요.", Toast.LENGTH_SHORT ).show();

                                MooDumDumService.of().getComment( item.getBoard_id() ).enqueue( new Callback<CommentModel>() {
                                    @Override
                                    public void onResponse(Call<CommentModel> call, Response<CommentModel> response) {
                                        setPostList( response.body().getResult() );
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

                case R.id.btnLike:
                    postCommentLike.PostCommentLike( comment_id, imageLike, likeCount, glideRequestManager );

            }
        }
    }
}

