package com.nexters.moodumdum.adpater;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.util.Log;
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
import com.nexters.moodumdum.DetailContentsActivity;
import com.nexters.moodumdum.PostLike;
import com.nexters.moodumdum.R;
import com.nexters.moodumdum.model.ContentsModel;
import com.nexters.moodumdum.model.DetailCardInfoDAO;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kimhyehyeon on 2018. 2. 22..
 */

public class StackCardAdapter extends StackLayout.Adapter<StackLayout.ViewHolder> {
    public static RequestManager glideRequestManager;
    static FragmentManager fragmentManager;
    static private  boolean detailShow = false;
    static private View currentView;
    static PostLike postLike;
    private static Context context;
    private List<ContentsModel.Result> results = new ArrayList<>();

    public StackCardAdapter(Context context, RequestManager glideRequestManager) {
        this.context = context;
        this.glideRequestManager = glideRequestManager;
        postLike = new PostLike();
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
        Log.d("SDSDSD", position+"");
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
        viewHolder.commentCount.setText( commentCount );
        viewHolder.commentCount.setTextColor(Color.parseColor(fontColor));
        viewHolder.likeCount.setText( likeCount );
        viewHolder.likeCount.setTextColor(Color.parseColor(fontColor));
        if(item.isIs_liked()) {
            glideRequestManager.load(R.drawable.like_after).into(viewHolder.contents_like);
        } else {
            viewHolder.contents_like.setColorFilter(Color.parseColor(fontColor));
        }
        viewHolder.contents_comment.setColorFilter(Color.parseColor(fontColor));
        viewHolder.line.setBackgroundColor(Color.parseColor(fontColor));



        viewHolder.detailCardInfo.setBoard_id( item.getId() );
        viewHolder.detailCardInfo.setDescription(item.getDescription());
        viewHolder.detailCardInfo.setColor(fontColor);
        viewHolder.detailCardInfo.setLikeCount(item.getLike_count());
        viewHolder.detailCardInfo.setCommentCount( item.getComment_count() );
        viewHolder.detailCardInfo.setLikeCount( item.getLike_count() );
        viewHolder.detailCardInfo.setIsLike( item.isIs_liked() );

    }

    @Override
    public int getItemCount() {
        return results.size();
    }
    public void showAgain(RequestManager glideRequestManager){
        if(this.glideRequestManager == null) {
            this.glideRequestManager = glideRequestManager;
        }
        Log.d("#$$#$","뿌에에에에에");
        if(detailShow == true) {
            currentView.findViewById(R.id.contents_like).setVisibility(View.VISIBLE);
            currentView.findViewById(R.id.contents_comment).setVisibility(View.VISIBLE);
            currentView.findViewById(R.id.commentCount).setVisibility(View.VISIBLE);
            currentView.findViewById(R.id.likeCount).setVisibility(View.VISIBLE);
            currentView.findViewById(R.id.contents).setVisibility(View.VISIBLE);
            detailShow = false;
        }
    }
    public void setPostList(List<ContentsModel.Result> results) {
        this.results.clear();
        this.results.addAll(results);
        notifyDataSetChanged();
    }
    public void setFragmentManagerCard(FragmentManager fragmentManager){
        this.fragmentManager = fragmentManager;
    }
    public static class ItemViewHolder extends StackLayout.ViewHolder implements View.OnTouchListener {
        View view;

        @BindView(R.id.contents)
        TextView contents;
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

        DetailCardInfoDAO detailCardInfo;

        public ItemViewHolder(View itemView) {
            super( itemView );
            this.view = itemView;
            ButterKnife.bind( this, view );
            itemView.setOnTouchListener(this);
            detailCardInfo = new DetailCardInfoDAO();
        }
        final GestureDetector gd = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                currentView = view;
//                detailCardInfo.setCurrnetView(view);
                detailShow = true;
                Intent intent = new Intent( context, DetailContentsActivity.class );
                intent.putExtra( "cardInfo", detailCardInfo);
                contents.setVisibility(View.INVISIBLE);
                commentCount.setVisibility(View.INVISIBLE);
                likeCount.setVisibility(View.INVISIBLE);
                contents_like.setVisibility(View.INVISIBLE);
                contents_comment.setVisibility(View.INVISIBLE);
                context.startActivity(intent);
                return super.onSingleTapConfirmed( e );
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                // 좋아요 눌렀을 때 할 Action
                postLike.PostComment(detailCardInfo.getBoard_id(), detailCardInfo.getLikeCount(),view,glideRequestManager);

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
