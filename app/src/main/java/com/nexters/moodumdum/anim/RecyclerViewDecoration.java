package com.nexters.moodumdum.anim;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by kimhyehyeon on 2018. 2. 27..
 */

public class RecyclerViewDecoration extends RecyclerView.ItemDecoration {
    private final int divHeight;

    public RecyclerViewDecoration (int divHeight) {
        this.divHeight = divHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.top = divHeight;
    }
}