package com.nexters.moodumdum.adpater;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fashare.stack_layout.StackLayout;
import com.nexters.moodumdum.R;
import com.nexters.moodumdum.common.DoubleClickListener;
import com.nexters.moodumdum.model.CardDataModel;
import com.nexters.moodumdum.model.UserModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kimhyehyeon on 2018. 10. 30..
 */

public class CardAdapter extends StackLayout.Adapter<CardAdapter.ViewHolder>{
    private static Context context;
    List<CardDataModel> cardList;
    private CardAdapter.SimpleOnGestureListener gestureListener;

    public interface SimpleOnGestureListener {
        void onSingleTapConfirmed(CardDataModel cardInfo, int position);
        void onDoubleTap(int position);
    }


    public void setData(List<CardDataModel> data) {
        cardList = data;
    }

    public List<CardDataModel> getData() {
        return cardList;
    }

    public CardAdapter(Context context, List<CardDataModel> cardList) {
        this.context = context;
        this.cardList = cardList;
    }

    public void setOnItemGestureListener(CardAdapter.SimpleOnGestureListener listener) {
        gestureListener = listener;
    }
    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        return new CardAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false));
    }

    @Override
    public void onBindViewHolder(CardAdapter.ViewHolder holder, final int position) {
        final CardAdapter.ViewHolder viewHolder = (CardAdapter.ViewHolder) holder;
        viewHolder.bind(context, cardList.get(position), gestureListener, position);
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public class ViewHolder extends StackLayout.ViewHolder{
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
        ImageButton contents_like;
        @BindView(R.id.contents_comment)
        ImageView contents_comment;
        @BindView(R.id.motion)
        ConstraintLayout motionView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            ButterKnife.bind( this, view );
        }

        void bind (final Context context, final CardDataModel cardInfo, final CardAdapter.SimpleOnGestureListener gestureListener, final int position){
            Glide.with(context).load(cardInfo.image_url).into(backImage);
            boardId.setText( cardInfo.id.toString() );
            contents.setText( cardInfo.description );

            String fontColor = "#e27171";
            if (cardInfo.color != ""){
                fontColor = cardInfo.color;
            }
            UserModel user = cardInfo.user;
            contents.setTextColor(Color.parseColor(fontColor));
            commentCount.setText( String.valueOf( cardInfo.comment_count ) );
            commentCount.setTextColor(Color.parseColor(fontColor));
            likeCount.setText( String.valueOf( cardInfo.like_count ) );
            likeCount.setTextColor(Color.parseColor(fontColor));
            if(cardInfo.is_liked) {
//            glideRequestManager.load(R.drawable.like_after).into(viewHolder.contents_like);
                contents_like.setSelected(true);
            } else {
                contents_like.setColorFilter(Color.parseColor(fontColor));
            }
            contents_comment.setColorFilter(Color.parseColor(fontColor));
            line.setBackgroundColor(Color.parseColor(fontColor));

            if (gestureListener != null) {
                itemView.setOnClickListener(new DoubleClickListener() {
                    @Override
                    public void onSingleClick(View v) {
                        gestureListener.onSingleTapConfirmed(cardInfo, position);
                    }

                    @Override
                    public void onDoubleClick(View v) {
                        gestureListener.onDoubleTap(position);
                    }
                });
            }
        }
    }
}
