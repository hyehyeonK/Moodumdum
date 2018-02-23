package com.nexters.moodumdum.adpater;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fashare.stack_layout.StackLayout;
import com.nexters.moodumdum.CommentActivity;
import com.nexters.moodumdum.R;
import com.nexters.moodumdum.model.ContentsModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kimhyehyeon on 2018. 2. 22..
 */

public class StackCardAdapter extends StackLayout.Adapter<StackLayout.ViewHolder> {


    private Context context;
    private List<ContentsModel.Result> results = new ArrayList<>();

    public StackCardAdapter(Context context) {
        this.context = context;
    }

    public List<ContentsModel.Result> getData() {
        return results;
    }

    @Override
    public StackLayout.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        return new ItemViewHolder( LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_card, parent, false ) );
    }

    @Override
    public void onBindViewHolder(StackLayout.ViewHolder holder, int position) {
        final ItemViewHolder viewHolder = (ItemViewHolder) holder;
        ContentsModel.Result item = results.get( position );
        Log.d( "testttttt@@@", item + "" );
//        Gson gson = new Gson();
//        ContentsModel.Result = gson.fromJson(item);

//        String item = results.get(position);
        viewHolder.boardId.setText( item.getId().toString() );
        viewHolder.contnents.setText( item.getDescription() );
        viewHolder.writer.setText( item.getName() );
//                viewHolder.contnents.setText("testest");
//        viewHolder.writer.setText("testest")

        final String board_id = viewHolder.boardId.getText().toString();

        viewHolder.view.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( context, CommentActivity.class );
                intent.putExtra( "board_id", board_id );
                context.startActivity( intent );
            }
        } );
    }


    @Override
    public int getItemCount() {
        return results.size();
    }

    public void setPostList(List<ContentsModel.Result> results) {
        this.results = results;
        Log.d( "testttttt@@@", results + "" );
        notifyDataSetChanged();
    }

    public static class ItemViewHolder extends StackLayout.ViewHolder {
        View view;

        @BindView(R.id.contents)
        TextView contnents;
        @BindView(R.id.writer)
        TextView writer;
        @BindView(R.id.board_id)
        TextView boardId;
//        @BindView(R.id.sliding_layout)
//        SlidingUpPanelLayout mSlidingPanelLayout;
//        private PanelSlideListener panelSlideListener;
//        @BindView(R.id.DrawerLayout)
//        android.support.v4.widget.DrawerLayout DrawerLayout;

        public ItemViewHolder(View itemView) {
            super( itemView );
            this.view = itemView;
            ButterKnife.bind( this, view );
        }
    }

}
