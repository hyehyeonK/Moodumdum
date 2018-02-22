package com.nexters.moodumdum.adpater;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fashare.stack_layout.StackLayout;
import com.nexters.moodumdum.R;
import com.nexters.moodumdum.model.ContentsModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kimhyehyeon on 2018. 2. 22..
 */

public class StackCardAdapter extends StackLayout.Adapter<StackLayout.ViewHolder>{
    private Context context;
    private List<ContentsModel.Result> results = new ArrayList<>();
    public StackCardAdapter(Context context) { this.context = context; }

    public List<ContentsModel.Result>getData(){
        return results;
    }

    @Override
    public StackLayout.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        return new ItemViewHolder( LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_card, parent, false ) );
    }

    @Override
    public void onBindViewHolder(StackLayout.ViewHolder holder, int position) {
        final ItemViewHolder viewHolder = (ItemViewHolder) holder;
        ContentsModel.Result item = results.get(position);
        Log.d("testttttt@@@",item+"");
//        Gson gson = new Gson();
//        ContentsModel.Result = gson.fromJson(item);

//        String item = results.get(position);
        viewHolder.contnents.setText(item.getDescription());
        viewHolder.writer.setText(item.getName());
//                viewHolder.contnents.setText("testest");
//        viewHolder.writer.setText("testest");
    }

    @Override
    public int getItemCount() {
        return results.size();
    }
    public void setPostList(List<ContentsModel.Result> results) {
        this.results = results;
        Log.d("testttttt@@@",results+"");
        notifyDataSetChanged();
    }

    public static class ItemViewHolder extends StackLayout.ViewHolder {
        View view;

        @BindView(R.id.contents)
        TextView contnents;
        @BindView(R.id.writer)
        TextView writer;

        public ItemViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this,view);
        }
    }

}
