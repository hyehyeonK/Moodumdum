package com.nexters.moodumdum;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by User on 2018-01-24.
 */

// 내가 쓴 글
class MypageMywriteAdapter extends RecyclerView.Adapter<MypageMywriteAdapter.ViewHolder> {
    private ArrayList<MywriteData> mMywriteDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView myWriting;

        public ViewHolder(View view) {
            super(view);
            myWriting = (TextView) view.findViewById(R.id.myWriting );
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MypageMywriteAdapter(ArrayList<MywriteData> mmMywriteDataset) {
        mMywriteDataset = mmMywriteDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MypageMywriteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate( R.layout.item_mywrite, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.myWriting.setText( mMywriteDataset.get( position ).Writing );
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mMywriteDataset.size();
    }
}

class MywriteData {
    public String Writing;

    public MywriteData(String Writing) {
        this.Writing = Writing;
    }
}


// 내가 조문한 글
class MypageMyjomunAdapter extends RecyclerView.Adapter<MypageMyjomunAdapter.ViewHolder> {
    private ArrayList<MyjomunData> mMyjomunDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView myJomun;

        public ViewHolder(View view) {
            super(view);
            myJomun = (TextView) view.findViewById(R.id.myJomun );
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MypageMyjomunAdapter(ArrayList<MyjomunData> myDataset) {
        mMyjomunDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MypageMyjomunAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate( R.layout.item_myjomun, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.myJomun.setText( mMyjomunDataset.get( position ).Jomun );
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mMyjomunDataset.size();
    }
}

class MyjomunData{
    public String Jomun;

    public MyjomunData(String Jomun) {
        this.Jomun = Jomun;
    }
}