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
 * Created by kimhyehyeon on 2018. 2. 19..
 */

public class SelectedCategoryAdapter extends RecyclerView.Adapter<SelectedCategoryAdapter.ViewHolder> {
    static private Context context;
    private List<CardDataModel> cardList ;
    private SelectedCategoryAdapter.OnItemClickListener itemClickListener;

    public interface OnItemClickListener {
        void onItemClick(CardDataModel cardInfo, int position);
    }

    public SelectedCategoryAdapter(Context context,  List<CardDataModel> cardList) {
        this.context = context;
        this.cardList = cardList;
    }

    public void setOnItemClickListener(SelectedCategoryAdapter.OnItemClickListener listener) {
        itemClickListener = listener;
    }

    @Override
    public SelectedCategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contents, parent, false));
    }

    @Override
    public void onBindViewHolder(SelectedCategoryAdapter.ViewHolder holder, final int position) {
        final SelectedCategoryAdapter.ViewHolder viewHolder = (SelectedCategoryAdapter.ViewHolder) holder;
        viewHolder.bind(context, cardList.get(position), itemClickListener, position);

    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public void setData(List<CardDataModel> data) {
        cardList = data;
    }

    public List<CardDataModel> getData() {
        return cardList;
    }

    public void addMoreItem(List<CardDataModel> results){
        this.cardList.addAll(results);
        notifyDataSetChanged();
    }
    public static class ViewHolder extends  RecyclerView.ViewHolder {
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

        public ViewHolder(final View itemView) {
            super (itemView);
            this.view = itemView;
            ButterKnife.bind(this,view);
        }

        void bind (final Context context, final CardDataModel cardInfo, final SelectedCategoryAdapter.OnItemClickListener clickListener , final int position) {
            Glide.with(context).load(cardInfo.image_url).into(backImage);
            contents.setText( cardInfo.description );
            UserModel user = cardInfo.user;
            nickName.setText(user.name);

            String fontColor = cardInfo.color;
            contents.setTextColor(Color.parseColor(fontColor));
            nickName.setTextColor(Color.parseColor(fontColor));
            commentsCount.setText( String.valueOf( cardInfo.comment_count ) );
            commentsCount.setTextColor(Color.parseColor(fontColor));
            likeCount.setText( String.valueOf( cardInfo.like_count ) );
            likeCount.setTextColor(Color.parseColor(fontColor));
            if(cardInfo.is_liked) {
//            glideRequestManager.load(R.drawable.like_after).into(viewHolder.contents_like);
                favoriteImg.setSelected(true);
            } else {
                favoriteImg.setColorFilter(Color.parseColor(fontColor));
            }
            commentImg.setColorFilter(Color.parseColor(fontColor));

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
//        @Override
//        public void onClick(View v) {
//            currentView = view;
//            Intent intent = new Intent( context, DetailContentsActivity.class );
//            intent.putExtra( "cardInfo", detailCardInfo);
//            intent.putExtra( "beforeAct", "Category");
////            context.startActivity(intent);
//
//            //shared element transition 애니메이션
//                        Pair<View, String> p1 = Pair.create((View)contents, contents.getTransitionName());
//                        Pair<View, String> p2 = Pair.create((View)commentsCount, commentsCount.getTransitionName());
//                        Pair<View, String> p3 = Pair.create((View)likeCount, likeCount.getTransitionName());
//                        Pair<View, String> p4 = Pair.create((View)backImage, backImage.getTransitionName());
//                        Pair<View, String> p5 = Pair.create((View)commentImg, commentImg.getTransitionName());
//                        Pair<View, String> p6 = Pair.create((View)favoriteImg, favoriteImg.getTransitionName());
//                        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,p1, p2, p3, p4, p5, p6);
//                        context.startActivity(intent, options.toBundle());
//        }
    }
}
