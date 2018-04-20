package com.nexters.moodumdum;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amar.library.ui.StickyScrollView;
import com.bumptech.glide.Glide;
import com.nexters.moodumdum.adpater.SelectedCategoryAdapter;
import com.nexters.moodumdum.anim.RecyclerViewDecoration;
import com.nexters.moodumdum.api.MooDumDumService;
import com.nexters.moodumdum.model.CategoryInfoModel;
import com.nexters.moodumdum.model.ContentsModel;
import com.nexters.moodumdum.model.DetailCardInfoDAO;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kimhyehyeon on 2018. 2. 19..
 */

public class CategorySelectedActivityRV extends AppCompatActivity {
    private LinearLayoutManager linearLayoutManager;
    private SelectedCategoryAdapter currentAdapter;
    String uuid;
    private int currentState;
    final static int LATEST = 0;
    final static int FAVORIT = 1;
    int dataOffset;
    boolean noMoreData;
    int int_scrollViewPos; // 현재 스크롤 포지션
    int int_TextView_lines; // 스크롤뷰 크기

    public static Activity activity;
    String categoryID ="";
    @BindView(R.id.scrollView)
    StickyScrollView scrollView;

    @BindView(R.id.rv_contents)
    RecyclerView recyclerView;

    @BindView(R.id.category_title)
    TextView categoryTitle;
    @BindView(R.id.categoryBanner)
    ImageView categoryBanner;

    @BindView(R.id.favoriteBtn)
    Button favoritBtn;
    @BindView(R.id.latestBtn)
    Button latestBtn;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout mRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selected);
        ButterKnife.bind(this);
        activity = this;
        dataOffset = 0;
        currentState = LATEST;
        noMoreData = false;
        uuid = ((MainActivity) MainActivity.MainAct).getUUID();
        Intent intent = getIntent();
        categoryID = intent.getStringExtra("categoryID");
        currentAdapter = new SelectedCategoryAdapter(CategorySelectedActivityRV.this, activity);
        initView();
        getCategoryInfo();
        getLatestPost();
    }
    public void setRefreshInfo(DetailCardInfoDAO newInfo) {
        currentAdapter.reloadInfo(newInfo);
    }
    private void initView() {
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        currentAdapter = new SelectedCategoryAdapter(CategorySelectedActivityRV.this, activity);
                        recyclerView.setAdapter(currentAdapter);
                        dataOffset = 0;
                        noMoreData = false;
                        relode();
                        getLatestPost();
                        mRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(currentAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new RecyclerViewDecoration(12));
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int_scrollViewPos = scrollView.getScrollY();
                int_TextView_lines = scrollView.getChildAt(0).getBottom() - scrollView.getHeight();
                if(int_TextView_lines == int_scrollViewPos){
                    relode();
                }

            }
        });
    }
    private void getCategoryInfo() {
        MooDumDumService.of().getCategoryInfo(categoryID).enqueue(new Callback<CategoryInfoModel>() {
            @Override
            public void onResponse(Call<CategoryInfoModel> call, Response<CategoryInfoModel> response) {
                if(response.isSuccessful()) {
                    final CategoryInfoModel category = response.body();
                    Log.d("결과", category + "");
                    categoryTitle.setText(category.getTitle() + " 무덤");
                    Glide.with(CategorySelectedActivityRV.this).load(category.getBanner()).into(categoryBanner);

                }
            }

            @Override
            public void onFailure(Call<CategoryInfoModel> call, Throwable t) {

            }
        });
    }
    private void getLatestPost() {
        MooDumDumService.of().getCategoryContentsInOrderOfPriority(uuid, categoryID, dataOffset).enqueue(new Callback<ContentsModel>() {
            @Override
            public void onResponse(Call<ContentsModel> call, Response<ContentsModel> response) {
                if (response.isSuccessful()) {
                    final ContentsModel items = response.body();
                    if(items.getNext() == null){
                        noMoreData = true;
                    }
                    if(dataOffset == 0 ) {
                        currentAdapter.setPostList(items.getResult());
                    } else {
                        currentAdapter.addMoreItem(items.getResult());
                    }
                    dataOffset += 10;
                }
            }

            @Override
            public void onFailure(Call<ContentsModel> call, Throwable t) {
            }
        });
    }
    private void getFavoritePost() {
        MooDumDumService.of().getCategoryContentsInOrderOfPopularity(uuid, categoryID, dataOffset).enqueue(new Callback<ContentsModel>() {
            @Override
            public void onResponse(Call<ContentsModel> call, Response<ContentsModel> response) {
                if (response.isSuccessful()) {
                    final ContentsModel items = response.body();
                    if(items.getNext() == null){
                        noMoreData = true;
                    }
                    if(dataOffset == 0) {
                        currentAdapter.setPostList(items.getResult());
                    } else {
                        currentAdapter.addMoreItem(items.getResult());
                    }
                    dataOffset += 10;
                }
            }

            @Override
            public void onFailure(Call<ContentsModel> call, Throwable t) {
            }
        });
    }
    public void relode(){
        if(!noMoreData) {
            switch (currentState) {
                case LATEST:
                    latestBtn.setTextColor(Color.BLACK);
                    favoritBtn.setTextColor(Color.GRAY);
                    getLatestPost();
                    break;
                case FAVORIT:
                    latestBtn.setTextColor(Color.GRAY);
                    favoritBtn.setTextColor(Color.BLACK);
                    getFavoritePost();
                    break;
            }
        } else {
            Toast.makeText(getBaseContext(), "더이상 로드할 글이 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }
    @OnClick(R.id.latestBtn)
    public void latestList(){
        currentState = LATEST;
        dataOffset = 0;
        noMoreData = false;
        relode();
        scrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollView.scrollTo(0,0);
            }
        },500);
    }

    @OnClick(R.id.favoriteBtn)
    public void favoritList(){
        currentState = FAVORIT;
        dataOffset = 0;
        noMoreData = false;
        relode();
        scrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollView.scrollTo(0,0);
            }
        },500);
    }

    @OnClick(R.id.btn_back)
    public void onBtnBackClicked() {
        this.finish();
        overridePendingTransition(R.anim.not_move_activity,R.anim.leftout_activity);
    }

    @OnClick(R.id.onClickToPlus)
    public void onViewClicked() {
        Intent intent = new Intent( getApplicationContext(), PlusActivity.class );
        startActivity( intent );
    }
}

