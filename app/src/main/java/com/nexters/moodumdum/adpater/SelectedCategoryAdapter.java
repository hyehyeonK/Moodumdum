package com.nexters.moodumdum.adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nexters.moodumdum.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kimhyehyeon on 2018. 2. 19..
 */

public class SelectedCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;

    public SelectedCategoryAdapter(Context context) { this.context = context; }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contents, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ItemViewHolder viewHolder = (ItemViewHolder) holder;
        viewHolder.nickName.setText("지나가는 영혼");
        viewHolder.contents.setText("나 어제 ㄹㅇ 개 쪽팔린 일 있었음 궁금함? 일단 밥 먹고 오겠음 ㅇㅇ 좀만 기다리셈");
        viewHolder.commentsCount.setText("120");
        viewHolder.likeCount.setText("231");
    }

    @Override
    public int getItemCount() {
        return 100;
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

        public ItemViewHolder(final View itemView) {
            super (itemView);
            this.view = itemView;
            ButterKnife.bind(this,view);
        }


    }
}
