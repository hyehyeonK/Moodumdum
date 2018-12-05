package com.nexters.moodumdum.adpater;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nexters.moodumdum.R;
import com.nexters.moodumdum.model.CardDataModel;
import com.nexters.moodumdum.model.UserModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kimhyehyeon on 2018. 11. 6..
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private static Context context;
    List<CardDataModel> cardList;

    private CategoryAdapter.OnItemClickListener itemClickListener;

    public interface OnItemClickListener {
        void onItemClick(CardDataModel cardInfo, int position);
    }

    public CategoryAdapter(Context context, List<CardDataModel> cardList) {
        this.context = context;
        this.cardList = cardList;
    }

    public void setOnItemClickListener(CategoryAdapter.OnItemClickListener listener) {
        itemClickListener = listener;
    }

    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CategoryAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contents, parent, false));
    }

    @Override
    public void onBindViewHolder(CategoryAdapter.ViewHolder holder, int position) {
        final CategoryAdapter.ViewHolder viewHolder = (CategoryAdapter.ViewHolder) holder;
        viewHolder.bind(context, cardList.get(position), itemClickListener, position);
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }
//    public void setPostList(List<CardDataModel> cardList) {
//        this.cardList = cardList;
//        notifyDataSetChanged();
//    }
    public List<CardDataModel> getData() {
        return cardList;
    }

    public void clearData()
    {
        cardList.clear();
    }
//    public void addMoreItem(List<CardDataModel> cardList){
//        this.cardList.addAll(cardList);
//        notifyDataSetChanged();
//    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        @BindView(R.id.contents)
        TextView contents;
        @BindView(R.id.nickName)
        TextView nickName;
        @BindView(R.id.commentsCount)
        TextView commentsCount;
        @BindView(R.id.likeCount)
        TextView likeCount;
        @BindView(R.id.backImage)
        ImageView backImage;
        @BindView(R.id.commentImg)
        ImageView commentImg;
        @BindView(R.id.favoriteImg)
        ImageView favoriteImg;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }

        void bind( final Context context, final CardDataModel cardInfo, final CategoryAdapter.OnItemClickListener clickListener, final int position) {

            int fontColor = Color.parseColor(cardInfo.color);
            UserModel user = cardInfo.user;
            Glide.with(context).load(cardInfo.image_url).into(backImage);
            nickName.setText(user.name);
            nickName.setTextColor(fontColor);
            contents.setText(cardInfo.description);
            contents.setTextColor(fontColor);
            commentsCount.setText(String.valueOf( cardInfo.comment_count ));
            commentsCount.setTextColor(fontColor);
            likeCount.setText(String.valueOf( cardInfo.like_count ));
            likeCount.setTextColor(fontColor);
            commentImg.setColorFilter(fontColor);
            favoriteImg.clearColorFilter();
            if (cardInfo.is_liked) {
                Glide.with(context).load(R.drawable.like_after).into(favoriteImg);
            }
            else {
                favoriteImg.setColorFilter(fontColor);//??
            }

            if (null != clickListener)
            {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickListener.onItemClick(cardInfo, position);
                    }
                });
            }

        }
    }


}
