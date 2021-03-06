package com.nexters.moodumdum.adpater;

import android.content.Context;
import android.graphics.Color;
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
    private CardAdapter.OnItemClickListener onClickListener;

    public interface SimpleOnGestureListener {
        void onSingleTapConfirmed(CardDataModel cardInfo, int position);
        void onDoubleTap(int position);
    }

    public interface OnItemClickListener {
        void onItemClick(CardDataModel cardInfo, int position);
    }

    public CardAdapter(Context context, List<CardDataModel> cardList) {
        this.context = context;
        this.cardList = cardList;
    }

    public void setOnItemGestureListener(CardAdapter.SimpleOnGestureListener listener) {
        gestureListener = listener;
    }
    public void setOnItemClickListener(CardAdapter.OnItemClickListener listener) {
        onClickListener = listener;
    }
    public void setData(List<CardDataModel> data) {
        cardList = data;
    }

    public List<CardDataModel> getData() {
        return cardList;
    }

    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        return new CardAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false));
    }

    @Override
    public void onBindViewHolder(CardAdapter.ViewHolder holder, final int position) {
        final CardAdapter.ViewHolder viewHolder = (CardAdapter.ViewHolder) holder;
        viewHolder.bind(context, cardList.get(position), gestureListener, onClickListener,position);
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
        @BindView(R.id.line)
        View line;
        @BindView(R.id.backImage)
        ImageView backImage;
        @BindView(R.id.contents_like)
        ImageButton contents_like;
        @BindView(R.id.contents_comment)
        ImageView contents_comment;
        @BindView(R.id.tv_writer)
        TextView tv_writer;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            ButterKnife.bind( this, view );
        }

        void bind (final Context context, final CardDataModel cardInfo, final CardAdapter.SimpleOnGestureListener gestureListener,
                   final CardAdapter.OnItemClickListener clickListener, final int position){
            Glide.with(context).load(cardInfo.image_url).into(backImage);
            contents.setText( cardInfo.description );

            int fontColor = Color.parseColor(cardInfo.color);

            UserModel user = cardInfo.user;
            tv_writer.setText(user.name);
            tv_writer.setTextColor(fontColor);

            contents.setTextColor(fontColor);
            commentCount.setText( String.valueOf( cardInfo.comment_count ) );
            commentCount.setTextColor(fontColor);
            likeCount.setText( String.valueOf( cardInfo.like_count ) );
            likeCount.setTextColor(fontColor);
            if(cardInfo.is_liked) {
                contents_like.setSelected(true);
                contents_like.setColorFilter(null);
            } else {
                contents_like.setColorFilter(fontColor);
            }
            contents_comment.setColorFilter(fontColor);
            line.setBackgroundColor(fontColor);

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

            if (clickListener !=  null)
            {
                contents_like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickListener.onItemClick(cardInfo, position);
                    }
                });
            }
        }
    }
}
