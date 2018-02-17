package com.nexters.moodumdum;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kimhyehyeon on 2018. 2. 16..
 */

public class TopBarActivity {

    static Activity currentActivity ;
    static Context currentContext;

    static public  void setHeader(final Activity activity, final Context context){
        ButterKnife.bind(activity);

        currentActivity = activity;
        currentContext = context;

    }

    @OnClick(R.id.onClickToMyPage)
    void onClickToMyPage() {
        Toast.makeText (currentContext, "onClickToMyPage 누름", Toast.LENGTH_SHORT).show();
//
//        Intent intent = new Intent(
//                currentActivity, // 현재 화면의 제어권자
//                FragmentOne.class); // 다음 넘어갈 클래스 지정
//        currentActivity.startActivity(intent);
    }
    @OnClick(R.id.onClickToMenu)
    void onClickToMenu() {
        Toast.makeText (currentContext, "onClickToMenu 누름", Toast.LENGTH_SHORT).show();
    }
}
