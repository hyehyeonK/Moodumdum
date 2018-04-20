package com.nexters.moodumdum.adpater;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
 * Created by kimhyehyeon on 2018. 2. 27..
 */

public class MyPageRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static RequestManager glideRequestManager;
    static private Context context;
    private List<ContentsModel.Result> results = new ArrayList<>();
    static private View currentView;
    public MyPageRecyclerViewAdapter(Context context, RequestManager glideRequestManager) {
        this.context = context;
        this.glideRequestManager = glideRequestManager;
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
        ContentsModel.Result item = results.get(position);
        String fontColor = "#e27171";
        if (item.getColor() != ""){
            fontColor = item.getColor();
        }

        glideRequestManager.load(item.getImage_url()).into(viewHolder.backImage);
        viewHolder.contentsText.setText(item.getDescription());
        viewHolder.contentsText.setTextColor(Color.parseColor(fontColor));


        viewHolder.detailCardInfo.setBoard_id( item.getId() );
        viewHolder.detailCardInfo.setDescription(item.getDescription());
        viewHolder.detailCardInfo.setColor(fontColor);
        viewHolder.detailCardInfo.setBackImagUrl(item.getImage_url());
        viewHolder.detailCardInfo.setCommentCount( item.getComment_count() );
        viewHolder.detailCardInfo.setLikeCount( item.getLike_count() );
        viewHolder.detailCardInfo.setIsLike( item.isIs_liked() );
    }

    @Override
    public int getItemCount() {
        return results.size();
    }
    public void setMyContentsList(List<ContentsModel.Result> results) {
        this.results = results;
        notifyDataSetChanged();
    }
    public void addMoreItem(List<ContentsModel.Result> results){
        this.results.addAll(results);
        notifyDataSetChanged();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        View view;

        @BindView(R.id.backImage)
        ImageView backImage;
        @BindView(R.id.myWriting)
        TextView contentsText;

        DetailCardInfoDAO detailCardInfo;
        private ContentsModel.Result selectedItem;

        public ItemViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this, view);
            detailCardInfo = new DetailCardInfoDAO();
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            currentView = view;
            Intent intent = new Intent( context, DetailContentsActivity.class );
            intent.putExtra( "cardInfo", detailCardInfo);
            intent.putExtra( "beforeAct", "MyPage");
            context.startActivity(intent);
        }
    }
}
