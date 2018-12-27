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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kimhyehyeon on 2018. 2. 27..
 */

public class MyPageRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    static private Context context;
    private List<CardDataModel> cardList;
    private MyPageRecyclerViewAdapter.OnItemClickListener itemClickListener;

    public interface OnItemClickListener {
        void onItemClick(CardDataModel cardInfo, int position);
    }
    public MyPageRecyclerViewAdapter(Context context, List<CardDataModel> cardList) {
        this.context = context;
        this.cardList = cardList;
    }
    public void setOnItemClickListener(MyPageRecyclerViewAdapter.OnItemClickListener listener) {
        itemClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate( R.layout.item_mypage, parent, false);
        ItemViewHolder rcv = new ItemViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ItemViewHolder viewHolder = (ItemViewHolder) holder;
        viewHolder.bind(context, cardList.get(position), itemClickListener, position);
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public void setMyContentsList(List<CardDataModel> cardList) {
        this.cardList = cardList;
        notifyDataSetChanged();
    }
    public void addMoreItem(List<CardDataModel> newCardList){
        this.cardList.addAll(newCardList);
        notifyDataSetChanged();
    }
    public void deleteItem(int position)
    {
        this.cardList.remove(position);
        notifyDataSetChanged();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        View view;

        @BindView(R.id.backImage)
        ImageView backImage;
        @BindView(R.id.myWriting)
        TextView contentsText;

        public ItemViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this, view);
        }
        void bind (final Context context, final CardDataModel cardInfo, final MyPageRecyclerViewAdapter.OnItemClickListener clickListener , final int position) {
            Glide.with(context).load(cardInfo.image_url).into(backImage);
            contentsText.setText(cardInfo.description);

            String fontColor = cardInfo.color;
            contentsText.setTextColor(Color.parseColor(fontColor));

            if(clickListener != null)
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
