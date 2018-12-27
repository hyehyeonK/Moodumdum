package com.nexters.moodumdum.views;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.amar.library.ui.StickyScrollView;
import com.bumptech.glide.Glide;
import com.nexters.moodumdum.R;
import com.nexters.moodumdum.adpater.CategoryAdapter;
import com.nexters.moodumdum.anim.RecyclerViewDecoration;
import com.nexters.moodumdum.api.MooDumDumService;
import com.nexters.moodumdum.common.BaseActivity;
import com.nexters.moodumdum.common.PropertyManagement;
import com.nexters.moodumdum.model.CardDataModel;
import com.nexters.moodumdum.model.CardListModel;
import com.nexters.moodumdum.model.CategoryInfoModel;
import com.nexters.moodumdum.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kimhyehyeon on 2018. 2. 19..
 */

public class CategorySelectedActivity extends BaseActivity {
    private LinearLayoutManager linearLayoutManager;
    private CategoryAdapter categoryAdapter;
    List<CardDataModel> results;
    String uuid;

    private int curPosition;
    private int currentState;
    final static int LATEST = 0;
    final static int FAVORIT = 1;

    int dataOffset;
    boolean noMoreData;

    int int_scrollViewPos; // 현재 스크롤 포지션
    int int_TextView_lines; // 스크롤뷰 크기

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
    @BindView(R.id.progressBar)
    ProgressBar loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selected);
        ButterKnife.bind(this);
        dataOffset = 0;
        currentState = LATEST;
        noMoreData = false;
        uuid = PropertyManagement.getUserId(CategorySelectedActivity.this);

        Intent intent = getIntent();
        categoryID = intent.getStringExtra("categoryID");

        categoryAdapter = new CategoryAdapter(this, results = new ArrayList<>());

        initView();
        getCategoryInfo();
        getLatestPost();
    }
//    public void setRefreshInfo(DetailCardInfoDAO newInfo) {
//        categoryAdapter.reloadInfo(newInfo);
//    }
    private void initView() {
        //progressON();
        categoryAdapter.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CardDataModel cardInfo, int position) {
                curPosition = position;
                Intent intent = new Intent( getBaseContext(), DetailCardActivity.class );
                intent.putExtra( "cardInfo", cardInfo);
                intent.putExtra( "beforeAct", Constants.ACTIVITY_CATEGORY);
                startActivityForResult(intent,Constants.RESULT_CATEGORY);
            }
        });
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dataOffset = 0;
                        noMoreData = false;
                        relode();

                        mRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(categoryAdapter);
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
                    categoryTitle.setText(category.getTitle() + " 무덤");
                    Glide.with(getBaseContext()).load(category.getBanner()).into(categoryBanner);
                }
            }
            @Override
            public void onFailure(Call<CategoryInfoModel> call, Throwable t) {

            }
        });
    }
    private void getLatestPost() {
        MooDumDumService.of().getCategoryContentsInOrderOfPriority(uuid, categoryID, dataOffset).enqueue(new Callback<CardListModel>() {
            @Override
            public void onResponse(Call<CardListModel> call, Response<CardListModel> response) {
                if (response.isSuccessful()) {
                    final CardListModel items = response.body();
                    if(items.next == null){
                        noMoreData = true;
                    }
                    if(0 == dataOffset )
                    {
                        categoryAdapter.clearData();

                    }
                    categoryAdapter.getData().addAll( items.result );
                    categoryAdapter.notifyDataSetChanged();
                    dataOffset += 10;
                    loadingBar.setVisibility(View.INVISIBLE);
                    //progressOFF();
                }
            }

            @Override
            public void onFailure(Call<CardListModel> call, Throwable t) {
            }
        });
    }
    private void getFavoritePost() {
        MooDumDumService.of().getCategoryContentsInOrderOfPopularity(uuid, categoryID, dataOffset).enqueue(new Callback<CardListModel>() {
            @Override
            public void onResponse(Call<CardListModel> call, Response<CardListModel> response) {
                if (response.isSuccessful()) {
                    final CardListModel items = response.body();
                    if(items.next == null){
                        noMoreData = true;
                    }
                    if(0 == dataOffset )
                    {
                        categoryAdapter.clearData();
                    }

                    categoryAdapter.getData().addAll( items.result );
                    categoryAdapter.notifyDataSetChanged();
                    dataOffset += 10;
                    loadingBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<CardListModel> call, Throwable t) {
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == Constants.RESULT_CATEGORY) {
            boolean isLike = data.getBooleanExtra("IS_LIKE",false);
            int countLike = data.getIntExtra("COUNT_LIKE",0);
            int countComment = data.getIntExtra("COUNT_COMMENT",0);
            results.get(curPosition).is_liked = isLike;
            results.get(curPosition).like_count = countLike;
            results.get(curPosition).comment_count = countComment;
            categoryAdapter.notifyDataSetChanged();

        }
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
            Toast.makeText(getBaseContext(), "더이상 불러 올 기억이 없어요.", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.latestBtn)
    public void latestList(){
        loadingBar.setVisibility(View.VISIBLE);
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
        loadingBar.setVisibility(View.VISIBLE);
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

    @Override
    public void onBackPressed() {
        onBtnBackClicked();
    }

    @OnClick(R.id.btn_back)
    public void onBtnBackClicked() {
        this.finish();
        overridePendingTransition(R.anim.load_fadein,R.anim.load_fadeout);
    }

    @OnClick(R.id.onClickToPlus)
    public void onViewClicked() {
        Intent intent = new Intent( getApplicationContext(), PlusActivity.class );
        startActivity( intent );
        overridePendingTransition(R.anim.load_fadein,R.anim.load_fadeout);
    }
}

