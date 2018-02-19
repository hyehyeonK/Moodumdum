package com.nexters.moodumdum;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fashare.stack_layout.StackLayout;
import com.fashare.stack_layout.transformer.AngleTransformer;
import com.nexters.moodumdum.factory.DeviceUuidFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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
        startActivity(new Intent(this, SplashActivity.class));
        ButterKnife.bind(this);
        TopBarActivity.setHeader(this, getApplicationContext());

        Intent intent = getIntent();
        String contents = intent.getStringExtra( "contents" );

//        mTextView.setText(contents);

        //디바이스 UUID 가져오기
        DeviceUuidFactory uuidFactory = new DeviceUuidFactory( this );
        UUID uuid = uuidFactory.getDeviceUuid();
        Log.d( "UUID_Check", "" + uuid );
        // -> uuid 존재 -> 바로 activity_main 띄우기
        // 존재하지 않을 경우 새로 db에 등록 및 intro 화면 띄우기

        initView();
        loadData( 0 );

    }

    int curPage = 0;

    private void initView() {
        mainStackLayout.setAdapter( stackViewAdapter = new Adapter( mData = new ArrayList<>() ) );
        mainStackLayout.addPageTransformer(
                new MyStackPageTransformer(),
                new MyAlphaTransformer(),
                new AngleTransformer()
        );

        mainStackLayout.setOnSwipeListener( new StackLayout.OnSwipeListener() {
            @Override
            public void onSwiped(View swipedView, int swipedItemPos, boolean isSwipeLeft, int itemLeft) {
                if (itemLeft < 5) {
                    loadData( ++curPage );
                }
            }
        } );
    }

    private void loadData(final int page) {
        new Handler().postDelayed( new Runnable() {
            @Override
            public void run() {
                stackViewAdapter.getData().addAll( Arrays.asList( String.valueOf( page * 3 ), String.valueOf( page * 3 + 1 ), String.valueOf( page * 3 + 2 ) ) );
                stackViewAdapter.notifyDataSetChanged();
            }
        }, 1000 );
    }




    class Adapter extends StackLayout.Adapter<Adapter.ViewHolder> {
        List<String> mData;

        public void setData(List<String> data) {
            mData = data;
        }

        public List<String> getData() {
            return mData;
        }

        public Adapter(List<String> data) {
            mData = data;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
            return new ViewHolder( LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_card, parent, false ) );
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            holder.itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            } );
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        public class ViewHolder extends StackLayout.ViewHolder {
            public ViewHolder(View itemView) {
                super( itemView );
                mTextView = (TextView) itemView.findViewById( R.id.contents );
                mTextView2 = (TextView) itemView.findViewById( R.id.writer );
            }

        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (isFirst) {
            getToggleAnimation( linearLayoutMain, linearLayoutMain.getHeight(), linear.getHeight() ).start();

            isFirst = false;
        }
        return true;

    }

    private ValueAnimator getToggleAnimation(final View view, int startHeight, int endHeight) {
        ValueAnimator animator = ValueAnimator.ofInt( startHeight, endHeight );
        animator.addUpdateListener( new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int val = (Integer) animation.getAnimatedValue();
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
                params.height = val;
                view.setLayoutParams( params );
            }
        } );
        //A duration for the whole animation, this can easily become a function parameter if needed.
//        animator.setDuration(Constants.TOGGLE_ANIMATION_DURATION);
        return animator;
    }

    @OnClick(R.id.onClickToMyPage)
    void onClickToMyPage() {

        Intent intent = new Intent( getApplicationContext(), Mypage.class );
        startActivity( intent );
    }

    @OnClick(R.id.onClickToMenu)
    void onClickToMenu() {
        Intent intent = new Intent( getApplicationContext(), CategoryActivity.class );
        startActivity( intent );
    }

    @OnClick(R.id.onClickToPlus)
    public void onViewClicked() {
        Intent intent = new Intent( getApplicationContext(), PlusActivity.class);
        startActivity( intent );
    }

}
