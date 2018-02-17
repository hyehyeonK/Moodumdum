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

//        mTextView.setText(contents);

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
            }

        }
    }
}
