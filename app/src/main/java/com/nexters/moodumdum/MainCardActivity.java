package com.nexters.moodumdum;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fashare.stack_layout.StackLayout;
import com.fashare.stack_layout.transformer.AngleTransformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kimhyehyeon on 2018. 2. 11..
 */

public class MainCardActivity extends AppCompatActivity {

    Adapter stackViewAdapter;
    List<String> mData;
    TextView mContents;
    TextView mWriter;
    static TextView mBoard_id;

    @BindView(R.id.mainLayoutForCardView)
    LinearLayout constraintLayoutMain;

    @BindView(R.id.stack_layout)
    StackLayout mainStackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        Intent intent = getIntent();
        String contents = intent.getStringExtra("contents");

//
//        boolean shouldChangeStatusBarTintToDark = true;
//
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//
//        View rootView = getWindow().getDecorView();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
//        {
//            rootView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
//        {
//            if (shouldChangeStatusBarTintToDark)
//            {
//                // 상태바의 아이콘 생각을 어두운 색으로 사용
//                rootView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//            }
//            else
//            {
//                // 상태바의 아이콘 색상을 밝은 색으로 사용
//                rootView.setSystemUiVisibility(0);
//            }
//        }
//        else
//        {
//            shouldChangeStatusBarTintToDark = false;
//        }

        initView();
        loadData(0);

    }
    int curPage = 0;

    private void initView() {
        mainStackLayout.setAdapter(stackViewAdapter = new Adapter(mData = new ArrayList<>()));
        mainStackLayout.addPageTransformer(
                new MyStackPageTransformer(),
                new MyAlphaTransformer(),
                new AngleTransformer()
        );

        mainStackLayout.setOnSwipeListener(new StackLayout.OnSwipeListener() {
            @Override
            public void onSwiped(View swipedView, int swipedItemPos, boolean isSwipeLeft, int itemLeft) {
                if(itemLeft < 5) {
                    loadData(++ curPage);
                }
            }
        });

        mainStackLayout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getApplicationContext(), CommentActivity.class );
//        String selectBtn = button.getTag() + "";//카테고리 태그
//        String BtnTest =
                String board_id = (String) MainCardActivity.mBoard_id.getText();
                intent.putExtra( "board_id", board_id );
                startActivity( intent );

            }

        } );
    }

    private void loadData(final int page) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                stackViewAdapter.getData().addAll(Arrays.asList(String.valueOf(page*3), String.valueOf(page*3+1), String.valueOf(page*3+2)));
                stackViewAdapter.notifyDataSetChanged();
            }
        }, 1000);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        Intent intent = new Intent(getApplicationContext(), CommentActivity.class);
////        String selectBtn = button.getTag() + "";//카테고리 태그
////        String BtnTest =
//        String board_id = (String) mBoard_id.getText();
//        intent.putExtra("board_id", board_id);
//        startActivity(intent);
//    }

    class Adapter extends  StackLayout.Adapter<Adapter.ViewHolder>{
        List<String> mData;

        public  void  setData(List<String> data) { mData = data; }

        public List<String> getData() { return  mData; }

        public  Adapter(List<String> data) { mData = data; }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
            return  new ViewHolder (LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                }
            });
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        public  class ViewHolder extends  StackLayout.ViewHolder{
            public  ViewHolder(View itemView) {
                super (itemView);
                mContents = (TextView) itemView.findViewById(R.id.contents);
                mWriter = (TextView) itemView.findViewById(R.id.writer);
                mBoard_id = (TextView) itemView.findViewById( R.id.board_id );
            }

        }

    }
}
