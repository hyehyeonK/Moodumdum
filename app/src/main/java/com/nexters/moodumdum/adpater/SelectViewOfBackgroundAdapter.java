package com.nexters.moodumdum.adpater;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nexters.moodumdum.views.PlusBackimgActivity;
import com.nexters.moodumdum.R;
import com.nexters.moodumdum.model.ImageModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kimhyehyeon on 2018. 2. 24..
 */


public class SelectViewOfBackgroundAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<ImageModel.Result> results = new ArrayList<>();

    public SelectViewOfBackgroundAdapter(Context context) {
        this.context = context;
    }


//
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_background, null);
        ItemViewHolder rcv = new ItemViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ItemViewHolder viewHolder = (ItemViewHolder) holder;
        ImageModel.Result item = results.get(position);

        Glide.with(context).load(item.getImage_url()).into(viewHolder.thumnail);
        viewHolder.bind(item);
//        ViewGroup.LayoutParams params =viewHolder.layout.getLayoutParams();
//        int height = params.width;
//        params.height = height;
//        viewHolder.layout.setLayoutParams(params);


    }

    @Override
    public int getItemCount() {
        return results.size();
    }
    public void setImageList(List<ImageModel.Result> results) {
        this.results = results;
        notifyDataSetChanged();
    }
    public static class ItemViewHolder extends  RecyclerView.ViewHolder {
        View view;
        @BindView(R.id.backImageItemLayout)
        ConstraintLayout layout;
        @BindView(R.id.backImageItem)
        ImageView thumnail;

        private ImageModel.Result seletedImage;

        public ItemViewHolder(final View itemView) {
            super (itemView);
            this.view = itemView;
            ButterKnife.bind(this,view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Image : ", seletedImage.getImage_url() );
                    ((PlusBackimgActivity)PlusBackimgActivity.PlusBackimgActivity_context).setBackgroundImage(seletedImage);
                }
            });
        }
        public void bind(ImageModel.Result setItem) {
            this.seletedImage = setItem;
            itemView.setTag(setItem);
        }
//        @Override
//        public void onClick(View view) {
//            ((PlusBackimgActivity)PlusBackimgActivity.PlusBackimgActivity_context).setBackgroundImage(seletedImage);
//        }

    }
}
