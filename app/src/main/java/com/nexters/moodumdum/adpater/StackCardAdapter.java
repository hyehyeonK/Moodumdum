package com.nexters.moodumdum.adpater;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
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
    public static RequestManager glideRequestManager;

    private  boolean isFirst = true;


    private static Context context;
    private List<ContentsModel.Result> results = new ArrayList<>();

    public StackCardAdapter(Context context, RequestManager glideRequestManager) {
        this.context = context;
        this.glideRequestManager = glideRequestManager;
    }

    public List<ContentsModel.Result> getData() {
        return results;
    }

    @Override
    public StackLayout.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        return new ItemViewHolder( LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_card, parent, false ) );
    }


    // onBindViewHolder 내부에서 View.OnClickListener를 셋하지 않는게 좋음. onBindViewHolder는 데이터를 View에 바인딩하기 위해서만 사용
    @Override
    public void onBindViewHolder(StackLayout.ViewHolder holder, int position) {
        final ItemViewHolder viewHolder = (ItemViewHolder) holder;
        ContentsModel.Result item = results.get( position );

        String commentCount = String.valueOf( item.getComment_count() );
        String likeCount = String.valueOf( item.getLike_count() );

        glideRequestManager.load(item.getImage_url())
                .diskCacheStrategy(DiskCacheStrategy.NONE)// 디스크 캐시 저장 off
                .skipMemoryCache(true)// 메모리 캐시 저장 off
                .into(viewHolder.backImage);
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

    }


    @Override
    public int getItemCount() {
        return results.size();
    }

    public void setPostList(List<ContentsModel.Result> results) {
        this.results.clear();
        this.results.addAll(results);
        notifyDataSetChanged();
    }
    public static class ItemViewHolder extends StackLayout.ViewHolder implements View.OnTouchListener {
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
        @BindView(R.id.motion)
        ImageView motionView;

        PostCommentModel commentModel;

        public ItemViewHolder(View itemView) {
            super( itemView );
            this.view = itemView;
            ButterKnife.bind( this, view );
            itemView.setOnTouchListener(this);
        }
        final GestureDetector gd = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                Intent intent = new Intent( context, CommentActivity.class );
                intent.putExtra( "newComment", commentModel);
                context.startActivity(intent);
                return super.onSingleTapConfirmed( e );
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                // 좋아요 눌렀을 때 할 Action
                motionView.setVisibility(View.VISIBLE);
                GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(motionView,1);

                glideRequestManager.load(R.raw.motion_like)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)// 디스크 캐시 저장 off
                        .skipMemoryCache(true)// 메모리 캐시 저장 off
                        .into(imageViewTarget);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        motionView.setVisibility(View.GONE);
                    }
                },2800);
                return true;
            }

        });

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return gd.onTouchEvent( event );
        }
    }

}
