package com.nexters.moodumdum;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nexters.moodumdum.model.ImageModel;

import java.util.ArrayList;

/**
 * Created by User on 2018-02-19.
 */

public class PlusBackimgAdapter extends BaseAdapter {
    ImageModel image = new ImageModel();
    Context context = null;

    //int[] imageIDs = null;

    ArrayList<ImageModel.Result> imagemodels = null;


    public PlusBackimgAdapter(Context context, ArrayList<ImageModel.Result> imageIDs) {
        this.context = context;
        this.imagemodels = imageIDs;
    }

    @Override
    public int getCount() {
        return (null != imagemodels) ? imagemodels.size() : 0;

    }

    @Override
    public Object getItem(int i) {
        return (null != imagemodels) ? imagemodels.get(i) : 0;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView = null;

//        Bitmap bmp = BitmapFactory.decodeResource( context.getResources(), imageIDs.get( i ) );
//        bmp = Bitmap.createScaledBitmap( bmp, 300, 300, false );
//        imageView = new ImageView( context );
//        imageView.setAdjustViewBounds( true );
//
//        imageView.setImageBitmap( bmp );
        return imageView;
    }

}
