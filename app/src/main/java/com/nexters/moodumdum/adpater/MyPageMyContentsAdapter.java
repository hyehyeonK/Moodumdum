package com.nexters.moodumdum.adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
 * Created by kimhyehyeon on 2018. 2. 27..
 */

public class MyPageMyContentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<ContentsModel.Result> results = new ArrayList<>();

    public MyPageMyContentsAdapter(Context context) {
        this.context = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate( R.layout.item_mywrite, parent, false);
        ItemViewHolder rcv = new ItemViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ItemViewHolder viewHolder = (ItemViewHolder) holder;
        ContentsModel.Result item = results.get(position);

        Glide.with(context).load(item.getImage_url()).into(viewHolder.backImage);
        viewHolder.contentsText.setText(item.getDescription());
        viewHolder.bind(item);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }
    public void setMyContentsList(List<ContentsModel.Result> results) {
        this.results = results;
        notifyDataSetChanged();
    }
    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        View view;

        @BindView(R.id.backImage)
        ImageView backImage;
        @BindView(R.id.myWriting)
        TextView contentsText;

        private ContentsModel.Result selectedItem;

        public ItemViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this, view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.d("Item : ", selectedItem.getDescription()+"상세 보기 구현하기" );
                }
            });
        }

        public void bind(ContentsModel.Result setItem) {
            this.selectedItem = setItem;
            itemView.setTag(setItem);
        }
    }
}
