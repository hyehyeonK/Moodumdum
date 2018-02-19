package com.nexters.moodumdum;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * Created by User on 2018-02-19.
 */

public class PlusBackimgAdapter extends BaseAdapter {

    Context context = null;

    int[] imageIDs = null;

    public PlusBackimgAdapter(Context context, int[] imageIDs) {
        this.context = context;
        this.imageIDs = imageIDs;
    }

    @Override
    public int getCount() {
        return (null != imageIDs) ? imageIDs.length : 0;

    }

    @Override
    public Object getItem(int i) {
        return (null != imageIDs) ? imageIDs[i] : 0;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView = null;

        Bitmap bmp
                = BitmapFactory.decodeResource( context.getResources(), imageIDs[i] );
        bmp = Bitmap.createScaledBitmap( bmp, 300, 300, false );
        imageView = new ImageView( context );
        imageView.setAdjustViewBounds( true );

        imageView.setImageBitmap( bmp );
        return imageView;
    }
}
