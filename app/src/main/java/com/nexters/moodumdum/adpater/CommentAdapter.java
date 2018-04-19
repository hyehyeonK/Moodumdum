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
        final CommentModel.Result item = results.get( position );
        viewHolder.WriterOfComment.setText( item.getUser().getNickName() );
        viewHolder.contentOfComment.setText( item.getDescription() );
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
            CommentModel.Result item = results.get( position );
            BigInteger comment_id = item.getId();

            // 임시로
            switch (v.getId()) {
                case R.id.btnDel:
                    MooDumDumService.of().delComment( comment_id ).enqueue( new Callback<ServerResponse>() {
                        @Override
                        public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                            Toast.makeText( context, "조문글을 삭제했어요.", Toast.LENGTH_SHORT ).show();
                        }

                        @Override
                        public void onFailure(Call<ServerResponse> call, Throwable t) {

                        }
                    } );

                case R.id.btnLike:
//                    postCommentLike.PostCommentLike( comment_id, view,glideRequestManager);
                        String user = ((MainActivity) MainActivity.MainAct).getUUID();
                    MooDumDumService.of().postCommentLike(comment_id, user ).enqueue( new Callback<ServerResponse>() {
                        @Override
                        public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                            Toast.makeText( context, "댓글 조아여.", Toast.LENGTH_SHORT ).show();
                        }

                        @Override
                        public void onFailure(Call<ServerResponse> call, Throwable t) {

                        }
                    } );

            }
        }
    }
}
