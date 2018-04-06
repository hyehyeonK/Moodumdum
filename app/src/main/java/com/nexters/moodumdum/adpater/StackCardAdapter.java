package com.nexters.moodumdum.adpater;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fashare.stack_layout.StackLayout;
import com.nexters.moodumdum.CommentActivity;
import com.nexters.moodumdum.R;
import com.nexters.moodumdum.model.ContentsModel;
import com.nexters.moodumdum.model.PostCommentModel;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kimhyehyeon on 2018. 2. 22..
 */

public class StackCardAdapter extends StackLayout.Adapter<StackLayout.ViewHolder> {

    private  boolean isFirst = true;


    private Context context;
    private List<ContentsModel.Result> results = new ArrayList<>();

    public StackCardAdapter(Context context) {
        this.context = context;
    }

    public List<ContentsModel.Result> getData() {
        return results;
    }

    @Override
    public StackLayout.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        return new ItemViewHolder( LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_card, parent, false ) );
    }

    @Override
    public void onBindViewHolder(StackLayout.ViewHolder holder, int position) {
        final ItemViewHolder viewHolder = (ItemViewHolder) holder;
        ContentsModel.Result item = results.get( position );

        String commentCount = String.valueOf( item.getComment_count() );
        String likeCount = String.valueOf( item.getLike_count() );

        Glide.with(context).load(item.getImage_url()).into(viewHolder.backImage);
        viewHolder.boardId.setText( item.getId().toString() );
        viewHolder.contents.setText( item.getDescription() );

        String fontColor = "#e27171";
        if (item.getColor() != ""){
            fontColor = item.getColor();
        }
        ContentsModel.Result.UserDataModel user = item.getUser();
        viewHolder.contents.setTextColor(Color.parseColor(fontColor));
        viewHolder.writer.setText( user.getNickName() );
        viewHolder.writer.setTextColor(Color.parseColor(fontColor));
        viewHolder.commentCount.setText( commentCount );
        viewHolder.commentCount.setTextColor(Color.parseColor(fontColor));
        viewHolder.likeCount.setText( likeCount );
        viewHolder.likeCount.setTextColor(Color.parseColor(fontColor));
        viewHolder.contents_like.setColorFilter(Color.parseColor(fontColor));
        viewHolder.contents_comment.setColorFilter(Color.parseColor(fontColor));
        viewHolder.commentModel = new PostCommentModel();
        viewHolder.line.setBackgroundColor(Color.parseColor(fontColor));
        String board_id = String.valueOf( viewHolder.boardId.getText() );

        final BigInteger BINT_board_id = new BigInteger(board_id);

        viewHolder.commentModel.setBoard_id( BINT_board_id );

        viewHolder.view.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( context, CommentActivity.class );
//                viewHolder.commentModel.setBoard_id( BINT_board_id );
                intent.putExtra( "newComment", viewHolder.commentModel);
                context.startActivity(intent);
            }
        } );
    }


    @Override
    public int getItemCount() {
        return results.size();
    }

    public void setPostList(List<ContentsModel.Result> results) {
        this.results = results;
        notifyDataSetChanged();
    }
    public static class ItemViewHolder extends StackLayout.ViewHolder {
        View view;

        @BindView(R.id.contents)
        TextView contents;
        @BindView(R.id.writer)
        TextView writer;
        @BindView(R.id.commentCount)
        TextView commentCount;
        @BindView(R.id.likeCount)
        TextView likeCount;
        @BindView(R.id.view)
        View line;
        @BindView(R.id.board_id)
        TextView boardId;
        @BindView(R.id.backImage)
        ImageView backImage;
        @BindView(R.id.contents_like)
        ImageView contents_like;
        @BindView(R.id.contents_comment)
        ImageView contents_comment;

        PostCommentModel commentModel;
//        @BindView(R.id.sliding_layout)
//        SlidingUpPanelLayout mSlidingPanelLayout;
//        private PanelSlideListener panelSlideListener;
//        @BindView(R.id.DrawerLayout)
//        android.support.v4.widget.DrawerLayout DrawerLayout;

        public ItemViewHolder(View itemView) {
            super( itemView );
            this.view = itemView;
            ButterKnife.bind( this, view );
        }
    }

}
