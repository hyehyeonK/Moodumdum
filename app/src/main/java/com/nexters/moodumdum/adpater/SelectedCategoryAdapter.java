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
import com.nexters.moodumdum.model.ContentsModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kimhyehyeon on 2018. 2. 19..
 */

public class SelectedCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;

    private List<ContentsModel.Result> results = new ArrayList<>();

    public SelectedCategoryAdapter(Context context) { this.context = context; }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contents, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ItemViewHolder viewHolder = (ItemViewHolder) holder;
        ContentsModel.Result item = results.get(position);

        String fontColor = "#e27171";
        if (item.getColor() != ""){
            fontColor = item.getColor();
        }
        Glide.with(context).load(item.getImage_url()).into(viewHolder.backImage);
        viewHolder.nickName.setText(item.getName());
        viewHolder.nickName.setTextColor(Color.parseColor(fontColor));
        viewHolder.contents.setText(item.getDescription());
        viewHolder.contents.setTextColor(Color.parseColor(fontColor));
        viewHolder.commentsCount.setText(item.getComment_count()+"");
        viewHolder.commentsCount.setTextColor(Color.parseColor(fontColor));
        viewHolder.likeCount.setText(item.getLike_count()+"");
        viewHolder.likeCount.setTextColor(Color.parseColor(fontColor));
        viewHolder.commentImg.setColorFilter(Color.parseColor(fontColor));
        viewHolder.favoriteImg.setColorFilter(Color.parseColor(fontColor));
    }

    @Override
    public int getItemCount() {
        return results.size();
    }
    public void setPostList(List<ContentsModel.Result> results) {
        this.results = results;
        notifyDataSetChanged();
    }
    public static class ItemViewHolder extends  RecyclerView.ViewHolder {
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

        public ItemViewHolder(final View itemView) {
            super (itemView);
            this.view = itemView;
            ButterKnife.bind(this,view);
        }


    }
}
