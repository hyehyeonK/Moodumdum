package com.nexters.moodumdum;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kimhyehyeon on 2018. 2. 19..
 */

public class BottomBtnActivity {

    static Activity currentActivity ;
    static Context currentContext;

    static public  void setHeader(final Activity activity, final Context context){
        ButterKnife.bind(activity);

        currentActivity = activity;
        currentContext = context;

    }

    @OnClick(R.id.onClickToPlus)
    void onClickToPlusContents() {
        Toast.makeText (currentContext, "onClickToPlusContents 누름", Toast.LENGTH_SHORT).show();
    }
}
