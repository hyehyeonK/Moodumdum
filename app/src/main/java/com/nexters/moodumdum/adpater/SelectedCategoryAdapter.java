package com.nexters.moodumdum.adpater;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.nexters.moodumdum.DetailContentsActivity;
import com.nexters.moodumdum.R;
import com.nexters.moodumdum.model.ContentsModel;
import com.nexters.moodumdum.model.DetailCardInfoDAO;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kimhyehyeon on 2018. 2. 19..
 */

public class SelectedCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static RequestManager glideRequestManager;
    static private Activity activity;
    static private Context context;
    static private View currentView;
    private List<ContentsModel.Result> results = new ArrayList<>();

    public SelectedCategoryAdapter(Context context, Activity activity) { this.context = context; this.activity = activity; }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contents, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ItemViewHolder viewHolder = (ItemViewHolder) holder;
        ContentsModel.Result item = results.get(position);
        Log.d("ADSDSDSAD",item.toString());
        String fontColor = "#e27171";
        if (item.getColor() != ""){
            fontColor = item.getColor();
        }
        ContentsModel.Result.UserDataModel user = item.getUser();
        Glide.with(context).load(item.getImage_url()).into(viewHolder.backImage);
        viewHolder.nickName.setText(user.getNickName());
        viewHolder.nickName.setTextColor(Color.parseColor(fontColor));
        viewHolder.contents.setText(item.getDescription());
        viewHolder.contents.setTextColor(Color.parseColor(fontColor));
        viewHolder.commentsCount.setText(item.getComment_count()+"");
        viewHolder.commentsCount.setTextColor(Color.parseColor(fontColor));
        viewHolder.likeCount.setText(item.getLike_count()+"");
        viewHolder.likeCount.setTextColor(Color.parseColor(fontColor));
        viewHolder.commentImg.setColorFilter(Color.parseColor(fontColor));
        if (item.isIs_liked()) {
            Glide.with(context).load(R.drawable.like_after).into(viewHolder.favoriteImg);
            viewHolder.favoriteImg.setColorFilter(null);
        } else {
            viewHolder.favoriteImg.setColorFilter(Color.parseColor(fontColor));
        }

        viewHolder.detailCardInfo.setBoard_id( item.getId() );
        viewHolder.detailCardInfo.setDescription(item.getDescription());
        viewHolder.detailCardInfo.setColor(fontColor);
        viewHolder.detailCardInfo.setBackImagUrl(item.getImage_url());
        viewHolder.detailCardInfo.setCommentCount( item.getComment_count() );
        viewHolder.detailCardInfo.setLikeCount( item.getLike_count() );
        viewHolder.detailCardInfo.setIsLike( item.isIs_liked() );
    }
    public void reloadInfo(DetailCardInfoDAO newInfo){
        Log.d("#$$#$",newInfo.getLikeCount()+"");
        ImageView imageView = currentView.findViewById(R.id.favoriteImg);
        TextView like = currentView.findViewById(R.id.likeCount);
        TextView comment = currentView.findViewById(R.id.commentsCount);
        if( newInfo.getIsLike()){
            Glide.with(context).load(R.drawable.like_after)
                    .into(imageView);
            imageView.setColorFilter(null);
        }
        like.setText(newInfo.getLikeCount() + "");
        comment.setText(newInfo.getCommentCount() + "");
    }
    @Override
    public int getItemCount() {
        return results.size();
    }
    public void setPostList(List<ContentsModel.Result> results) {
        this.results = results;
        notifyDataSetChanged();
    }
    public static class ItemViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{
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

        DetailCardInfoDAO detailCardInfo;

        public ItemViewHolder(final View itemView) {
            super (itemView);
            this.view = itemView;
            ButterKnife.bind(this,view);
            detailCardInfo = new DetailCardInfoDAO();
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            currentView = view;
            Intent intent = new Intent( context, DetailContentsActivity.class );
            intent.putExtra( "cardInfo", detailCardInfo);
            intent.putExtra( "beforeAct", "Category");
            context.startActivity(intent);

            //shared element transition 애니메이션
            //            Pair<View, String> p1 = Pair.create((View)contents, contents.getTransitionName());
            //            Pair<View, String> p2 = Pair.create((View)commentsCount, commentsCount.getTransitionName());
            //            Pair<View, String> p3 = Pair.create((View)likeCount, likeCount.getTransitionName());
            //            Pair<View, String> p4 = Pair.create((View)backImage, backImage.getTransitionName());
            //            Pair<View, String> p5 = Pair.create((View)commentImg, commentImg.getTransitionName());
            //            Pair<View, String> p6 = Pair.create((View)favoriteImg, favoriteImg.getTransitionName());
            //            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,p1,p2,p3,p4,p5,p6);
            //            context.startActivity(intent, options.toBundle());
        }
    }
}
