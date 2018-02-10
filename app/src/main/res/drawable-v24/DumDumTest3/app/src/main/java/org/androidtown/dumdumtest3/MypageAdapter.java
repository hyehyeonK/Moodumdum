package org.androidtown.dumdumtest3;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by User on 2018-01-24.
 */


public class MypageAdapter extends RecyclerView.Adapter<MypageAdapter.ViewHolder> {
private ArrayList<MyData> mDataset;

public static class ViewHolder extends RecyclerView.ViewHolder {
    // each data item is just a string in this case

    public TextView myWriting;

    public ViewHolder(View view) {
        super(view);
        myWriting = (TextView) view.findViewById( R.id.myWriting );
    }
}

    // Provide a suitable constructor (depends on the kind of dataset)
    public MypageAdapter(ArrayList<MyData> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MypageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mywrite, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.myWriting.setText( mDataset.get( position ).Writing );
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

class MyData{
    public String Writing;

    public MyData(String Writing) {
        this.Writing = Writing;
    }
}