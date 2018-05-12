package com.nexters.moodumdum;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

/**
 * Created by kimhyehyeon on 2018. 4. 20..
 */

public class ProgressviewHolder extends RecyclerView.ViewHolder{
    View view;
    public ProgressBar progressBar;

    public ProgressviewHolder(View itemView) {
        super(itemView);
        this.view = itemView;
        progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
    }
}
