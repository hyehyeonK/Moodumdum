package com.nexters.moodumdum;

import android.view.View;

import com.fashare.stack_layout.StackLayout;

/**
 * Created by kimhyehyeon on 2018. 2. 10..
 */

public class MyAlphaTransformer extends StackLayout.PageTransformer{
    private float mMinAlpha = 0f;
    private float mMaxAlpha = 1f;

    public MyAlphaTransformer(float minAlpha, float maxAlpha) {
        mMinAlpha = minAlpha;
        mMaxAlpha = maxAlpha;
    }

    public MyAlphaTransformer() {
        this(0f, 1f);
    }

    @Override
    public void transformPage(View view, float position, boolean isSwipeLeft) {

        View contentView = view.findViewById(R.id.mainCard);

        contentView.setAlpha(mMaxAlpha);

        if (position > -1 && position <= 0) { // [-1,0]
            contentView.setVisibility( View.VISIBLE);

            // 渐变
            float diffAlpha = (mMaxAlpha-mMinAlpha) * Math.abs(position);
            contentView.setAlpha(mMaxAlpha - diffAlpha);

        } else{
            contentView.setAlpha(mMaxAlpha);
        }
    }
}
