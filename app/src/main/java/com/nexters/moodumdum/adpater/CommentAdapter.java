package com.nexters.moodumdum.adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nexters.moodumdum.R;
import com.nexters.moodumdum.model.CommentModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by User on 2018-02-22.
 */

public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    private Context context;

    private List<CommentModel.Result> results = new ArrayList<>();

    public CommentAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from( parent.getContext() )
//                .inflate( R.layout.item_comment, parent, false );
//        ViewHolder vh = new ViewHolder( v );
//        return vh;

        return new ItemViewHolder( LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_comment, parent, false ) );

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ItemViewHolder viewHolder = (ItemViewHolder) holder;
        final CommentModel.Result item = results.get( position );
        viewHolder.WriterOfComment.setText( item.getUser().getNickName() );
        viewHolder.contentOfComment.setText( item.getDescription() );
//        viewHolder.btnDel.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                BigInteger comment_id = item.getId();
//                MooDumDumService.of().delComment( comment_id ).enqueue( new Callback<ServerResponse>() {
//                    @Override
//                    public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
//                        Toast.makeText( context, "조문글을 삭제했어요.", Toast.LENGTH_SHORT ).show();
//                    }
//
//                    @Override
//                    public void onFailure(Call<ServerResponse> call, Throwable t) {
//
//                    }
//                } );
//            }
//        } );
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

        public ItemViewHolder(final View itemView) {
            super( itemView );
            this.view = itemView;
            ButterKnife.bind( this, view );
        }


    }
}
