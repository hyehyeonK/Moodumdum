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
import com.nexters.moodumdum.api.MooDumDumService;
import com.nexters.moodumdum.factory.DeviceUuidFactory;
import com.nexters.moodumdum.model.ContentsModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static boolean isFirst = true;
    Adapter stackViewAdapter;
    List<String> mData;
    List<ContentsModel.Result> results = new ArrayList<>();
    ContentsModel contentsModel = new ContentsModel();

    @BindView(R.id.linearLayout)
    LinearLayout linear;

    @BindView(R.id.mainLayoutForCardView)
    LinearLayout linearLayoutMain;

    @BindView(R.id.stack_layout)
    StackLayout mainStackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
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
        setContentView(R.layout.activity_main);
        startActivity(new Intent(this, SplashActivity.class));
        ButterKnife.bind(this);
//        TopBarActivity.setHeader(this, getApplicationContext());

        Intent intent = getIntent();
        String contents = intent.getStringExtra( "contents" );

//        mTextView.setText(contents);

        //디바이스 UUID 가져오기
        DeviceUuidFactory uuidFactory = new DeviceUuidFactory( this );
        UUID uuid = uuidFactory.getDeviceUuid();
        Log.d( "UUID_Check", "" + uuid );
        // -> uuid 존재 -> 바로 activity_main 띄우기
        // 존재하지 않을 경우 새로 db에 등록 및 intro 화면 띄우기
        getPost();
        initView();
        loadData( 0 );

    }
//    private void setPostList() {
////        notifyDataSetChanged();
////        Glide.with(this).load(detail.getCoverUrl()).into(coverUrl);
//
//    }
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
                if (itemLeft < 3) {
                    getPost();
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




    class Adapter extends StackLayout.Adapter<Adapter.ItemViewHolder> {
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
        public ItemViewHolder onCreateViewHolder(ViewGroup parent, int position) {
            return new ItemViewHolder( LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_card, parent, false ) );
        }

        @Override
        public void onBindViewHolder(ItemViewHolder holder, final int position) {
            ContentsModel.Result item = results.get(position);
            holder.contentsText.setText(item.getDescription());
            holder.nickname.setText(item.getUser());
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

        public class ItemViewHolder extends StackLayout.ViewHolder {
            View view;
            @BindView(R.id.contents)
            TextView contentsText;
            @BindView(R.id.writer)
            TextView nickname;

            public ItemViewHolder(View itemView) {
                super( itemView );
                this.view =itemView;
                ButterKnife.bind(this,view);
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

    private void getPost() {
        MooDumDumService.of().getContents().enqueue(new Callback<ContentsModel>() {
            @Override
            public void onResponse(Call<ContentsModel> call, Response<ContentsModel> response) {
                if (response.isSuccessful()) {
                    Log.d("APIresult", response.message());
                    Log.d("APIresult", response.body() + "");
                    final ContentsModel items = response.body();
                    results = items.getResult();
//                    setPostList();
                }
            }

            @Override
            public void onFailure(Call<ContentsModel> call, Throwable t) {

            }
        });
    }
}
