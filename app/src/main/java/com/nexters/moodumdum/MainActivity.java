package com.nexters.moodumdum;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fashare.stack_layout.StackLayout;
import com.fashare.stack_layout.transformer.AngleTransformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    public static boolean isFirst = true;
    Adapter stackViewAdapter;
    List<String> mData;
    TextView mTextView;
    TextView mTextView2;

    @BindView(R.id.linearLayout)
    LinearLayout linear;

    @BindView(R.id.mainLayoutForCardView)
    LinearLayout linearLayoutMain;

    @BindView(R.id.stack_layout)
    StackLayout mainStackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        TopBarActivity.setHeader(this, getApplicationContext());
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
        public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
            return  new Adapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false));
        }

        @Override
        public void onBindViewHolder(Adapter.ViewHolder holder, final int position) {

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
                mTextView = (TextView) itemView.findViewById(R.id.contents);
                mTextView2 = (TextView) itemView.findViewById(R.id.writer);
            }

        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        if ( isFirst ){
                getToggleAnimation(linearLayoutMain,linearLayoutMain.getHeight(),linear.getHeight()).start();

            isFirst = false;
        }
        return true;

    }
    private ValueAnimator getToggleAnimation(final android.view.View view , int startHeight , int endHeight) {
        //We create the animator and setup the starting height and the final height. The animator
        //Will create smooth itnermediate values (based on duration) to go across these two values.
        ValueAnimator animator = ValueAnimator.ofInt(startHeight,endHeight);
        //Overriding updateListener so that we can tell the animator what to do at each update.
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //We get the value of the animatedValue, this will be between [startHeight,endHeight]
                int val = (Integer)animation.getAnimatedValue();
                //We retrieve the layout parameters and pick up the height of the View.
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
                params.height = val;
                //Once we have updated the height all we need to do is to call the set method.
                view.setLayoutParams(params);
            }
        });
        //A duration for the whole animation, this can easily become a function parameter if needed.
//        animator.setDuration(Constants.TOGGLE_ANIMATION_DURATION);
        return animator;
    }
    @OnClick(R.id.onClickToMyPage)
    void onClickToMyPage() {
        Toast.makeText (MainActivity.this, "onClickToMyPage 누름", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(), Mypage.class); // 다음 넘어갈 클래스 지정
        startActivity(intent);
    }
//    @OnClick(R.id.onClickToMenu)
//    void onClickToMenu() {
//        Toast.makeText (getApplicationContext(), "onClickToMenu 누름", Toast.LENGTH_SHORT).show();
//    }
}
