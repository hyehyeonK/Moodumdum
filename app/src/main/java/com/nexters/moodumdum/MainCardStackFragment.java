package com.nexters.moodumdum;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.fashare.stack_layout.StackLayout;
import com.fashare.stack_layout.transformer.AngleTransformer;
import com.nexters.moodumdum.adpater.StackCardAdapter;
import com.nexters.moodumdum.api.MooDumDumService;
import com.nexters.moodumdum.model.ContentsModel;
import com.nexters.moodumdum.model.DetailCardInfoDAO;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kimhyehyeon on 2018. 2. 10..
 */

public class MainCardStackFragment extends Fragment {
    public RequestManager mGlideRequestManager;
    public static Fragment MainCardFragment ;
    public static Context MainCardFragment_context;
    public int StatusBarHeight;
    @BindView(R.id.topFrame)
    ConstraintLayout topFrame;
    @BindView(R.id.firstView)
    ConstraintLayout firstView;
    @BindView(R.id.imageView3)
    ImageView imageView3;
    @BindView(R.id.onClickToMenu)
    ImageButton onClickToMenu;
    @BindView(R.id.onClickToMyPage)
    ImageButton onClickToMyPage;
    @BindView(R.id.onClickToPlus)
    Button onClickToPlus;
    @BindView(R.id.firstbackImage)
    ImageView firstbackImage;
    @BindView(R.id.firstContents)
    TextView firstContents;
    @BindView(R.id.firstWriter)
    TextView firstWriter;
    @BindView(R.id.noDataImg)
    ImageView nodataImg;
    @BindView(R.id.noDataText)
    TextView nodataText;
    @BindView(R.id.topView)
    View topView;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout mRefreshLayout;
    public StackCardAdapter stackCardAdapter;
    private StackCardAdapter currentCardAdaper;
    List<ContentsModel.Result> results = new ArrayList<>();

    @BindView(R.id.linearLayout)
    LinearLayout linear;

    @BindView(R.id.mainLayoutForCardView)
    ConstraintLayout linearLayoutMain;

    @BindView(R.id.stack_layout)
    StackLayout mainStackLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind( this, view );
        MainCardFragment = MainCardStackFragment.this;
        MainCardFragment_context = getContext();
        //Menu top margin 주기
        getStatusBarHeight();
        setActionbarMarginTop(topFrame);
        mGlideRequestManager = Glide.with(this);
        getPost();
        initView();
//        loadData();
        return view;
    }
//    public void animateTransaction(View view) {
//        Toast.makeText(getContext(), "클리이이익.", Toast.LENGTH_SHORT).show();
//        getFragmentManager()
//                .beginTransaction()
//                .addSharedElement(mainStackLayout.findViewById(R.id.backImage), getString(R.string.ts_backImg))
//                .addSharedElement(mainStackLayout.findViewById(R.id.commentCount), getString(R.string.ts_countTxt))
//                .addSharedElement(mainStackLayout.findViewById(R.id.likeCount), getString(R.string.ts_likeTxt))
//                .addSharedElement(mainStackLayout.findViewById(R.id.contents_like), getString(R.string.ts_likeImg))
//                .addSharedElement(mainStackLayout.findViewById(R.id.contents_comment), getString(R.string.ts_countImg))
//                .replace(R.id.fragment_container, new DetailContentsActivity())
//                .commit();
//    }
//    @Override
//    public void onResume() {
//        super.onResume();
////        stackCardAdapter.notifyDataSetChanged();
//        getPost();
//        initView();
//        loadData( 0 );
//    }

    int curPage = 0;

    public void getStatusBarHeight(){
        int statusHeight = 0;
        int screenSizeType = (getContext().getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK);

        if(screenSizeType != Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            int resourceId = getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");

            if (resourceId > 0) {
                statusHeight = getContext().getResources().getDimensionPixelSize(resourceId);
            }
        }
        StatusBarHeight = statusHeight;
    }

    public void setActionbarMarginTop(final View view){
        FrameLayout.LayoutParams topLayoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        topLayoutParams.topMargin = StatusBarHeight;
        view.setLayoutParams(topLayoutParams);
    }
    //다시 보여질 때
    @Override
    public void onResume() {
        stackCardAdapter.showAgain(mGlideRequestManager);
        topView.setVisibility(View.VISIBLE);
        super.onResume();
    }
    @Override
    public void onPause() {
        topView.setVisibility(View.INVISIBLE);
        super.onPause();
    }

    public void setRefreshInfo(DetailCardInfoDAO newInfo) {
        Log.d("ADSADASD@@#33","SSSS");
        currentCardAdaper.reloadInfo(mGlideRequestManager, newInfo);
    }

    public void initView() {

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
        stackCardAdapter = new StackCardAdapter(getContext(), mGlideRequestManager);
        currentCardAdaper = stackCardAdapter;
        mainStackLayout.setAdapter( stackCardAdapter );
        mainStackLayout.invalidate();
        mainStackLayout.addPageTransformer(
                new MyStackPageTransformer(),
                new MyAlphaTransformer(),
                new AngleTransformer()
        );

        mainStackLayout.setOnSwipeListener( new StackLayout.OnSwipeListener() {
            @Override
            public void onSwiped(View swipedView, int swipedItemPos, boolean isSwipeLeft, int itemLeft) {
                Log.d("itemLeft@@@@@",itemLeft+" , " + results.size());
                if (itemLeft == 3) {
                    Log.d("재로드!!!!!!@@@@",itemLeft+" , " + results.size());
//                    getPost();
                    loadData();
                }
            }
        } );
        stackCardAdapter.setFragmentManagerCard(getFragmentManager());
    }

    public void refreshData(){
        loadData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                StackCardAdapter reStartAdapter = new StackCardAdapter(getContext(), mGlideRequestManager);
                currentCardAdaper = reStartAdapter;
                mainStackLayout.setAdapter(reStartAdapter);
                reStartAdapter.setPostList(results);
                mRefreshLayout.setRefreshing(false);
            }
        }, 1000);


    }
    public void loadData() {
        String uuid = ((MainActivity)getActivity()).getUUID();
        MooDumDumService.of().getContents(uuid).enqueue( new Callback<ContentsModel>() {
            @Override
            public void onResponse(Call<ContentsModel> call, Response<ContentsModel> response) {
                if (response.isSuccessful()) {
                    final ContentsModel items = response.body();
                    results = items.getResult();
                    new Handler().postDelayed( new Runnable() {

                        @Override
                        public void run() {
                            currentCardAdaper.getData().addAll(results);
                            currentCardAdaper.notifyDataSetChanged();
                        }
                    }, 1000 );

                }
                Log.d( "RESULT@@@@@", response.message() );
            }

            @Override
            public void onFailure(Call<ContentsModel> call, Throwable t) {
                Log.e( "RESULT@@@@@", "ERRR####" + t );
            }
        } );
    }

    /**
     * 시작 touch view
     */
    @OnClick(R.id.firstView)
    public void desableView() {

        getToggleAnimation( linearLayoutMain, linearLayoutMain.getHeight(), linear.getHeight() ).start();
        Animation alphaAnim = AnimationUtils.loadAnimation(getContext(),R.anim.load_fadeout);
        firstView.startAnimation(alphaAnim);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                firstView.setVisibility(View.GONE);
            }
        },500);


    }
    // touch view animation
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

    public void getPost() {
        String uuid = ((MainActivity)getActivity()).getUUID();
        MooDumDumService.of().getContents(uuid).enqueue( new Callback<ContentsModel>() {
            @Override
            public void onResponse(Call<ContentsModel> call, Response<ContentsModel> response) {
                if (response.isSuccessful()) {
                    final ContentsModel items = response.body();
                    results = items.getResult();
                    if(results.size()>0){
                        firstView.setVisibility(View.VISIBLE);
                        nodataImg.setVisibility(View.GONE);
                        nodataText.setVisibility(View.GONE);
                        ContentsModel.Result firstItem = results.get(0);
                        ContentsModel.Result.UserDataModel user = firstItem.getUser();
                        mGlideRequestManager.load(firstItem.getImage_url()).into(firstbackImage);
                        firstContents.setText(firstItem.getDescription());
                        firstContents.setTextColor(Color.parseColor(firstItem.getColor()));
                        firstWriter.setText(user.getNickName());
                        firstWriter.setTextColor(Color.parseColor(firstItem.getColor()));

                        stackCardAdapter.setPostList(results);
                    } else {
                        firstView.setVisibility(View.GONE);
                        nodataImg.setVisibility(View.VISIBLE);
                        nodataText.setVisibility(View.VISIBLE);
                    }
                }
                Log.d( "RESULT@@@@@", response.message() );
            }

            @Override
            public void onFailure(Call<ContentsModel> call, Throwable t) {
                Log.e( "RESULT@@@@@", "ERRR####" + t );
            }
        } );
    }
    @OnClick(R.id.onClickToMyPage)
    void onClickToMyPage() {

        Intent intent = new Intent( getContext(), Mypage.class );
        intent.putExtra("plusContents", "no");
        startActivity( intent );
    }

    @OnClick(R.id.onClickToMenu)
    void onClickToMenu() {
        Intent intent = new Intent( getContext(), CategoryActivity.class );
        startActivity( intent );
    }

    @OnClick(R.id.onClickToPlus)
    public void onViewClicked() {
        Intent intent = new Intent( getContext(), PlusActivity.class );
        startActivity( intent );
    }



}
