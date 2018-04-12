package com.nexters.moodumdum;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amar.library.ui.StickyScrollView;
import com.bumptech.glide.Glide;
import com.nexters.moodumdum.adpater.SelectedCategoryAdapter;
import com.nexters.moodumdum.anim.RecyclerViewDecoration;
import com.nexters.moodumdum.api.MooDumDumService;
import com.nexters.moodumdum.model.CategoryInfoModel;
import com.nexters.moodumdum.model.ContentsModel;

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
    private SelectedCategoryAdapter selectedCategoryAdapter;
    private SelectedCategoryAdapter reStartAdapter;
    private LinearLayoutManager linearLayoutManager;
    private SelectedCategoryAdapter currentAdapter;
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

        Intent intent = getIntent();
        categoryID = intent.getStringExtra("categoryID");
        initView();
        getCategoryInfo();
        getLatestPost(selectedCategoryAdapter);
    }

    private void initView() {
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        latestBtn.setTextColor(Color.BLACK);
                        favoritBtn.setTextColor(Color.GRAY);
                        reStartAdapter = new SelectedCategoryAdapter(CategorySelectedActivityRV.this);
                        recyclerView.setAdapter(reStartAdapter);
                        getLatestPost(reStartAdapter);
                        mRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });
        linearLayoutManager = new LinearLayoutManager(this);
        selectedCategoryAdapter = new SelectedCategoryAdapter(CategorySelectedActivityRV.this);
        recyclerView.setAdapter(selectedCategoryAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new RecyclerViewDecoration(12));
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
    private void getLatestPost(SelectedCategoryAdapter adapter) {
        currentAdapter = adapter;
        MooDumDumService.of().getCategoryContentsInOrderOfPriority(categoryID).enqueue(new Callback<ContentsModel>() {
            @Override
            public void onResponse(Call<ContentsModel> call, Response<ContentsModel> response) {
                if (response.isSuccessful()) {
                    final ContentsModel items = response.body();
                    currentAdapter.setPostList(items.getResult());
                }
            }

            @Override
            public void onFailure(Call<ContentsModel> call, Throwable t) {
            }
        });
    }
    private void getFavoritePost() {
        MooDumDumService.of().getCategoryContentsInOrderOfPopularity(categoryID).enqueue(new Callback<ContentsModel>() {
            @Override
            public void onResponse(Call<ContentsModel> call, Response<ContentsModel> response) {
                if (response.isSuccessful()) {
                    final ContentsModel items = response.body();
                    currentAdapter.setPostList(items.getResult());
                }
            }

            @Override
            public void onFailure(Call<ContentsModel> call, Throwable t) {
            }
        });
    }

    @OnClick(R.id.latestBtn)
        public void latestList(){
        latestBtn.setTextColor(Color.BLACK);
        favoritBtn.setTextColor(Color.GRAY);
        getLatestPost(currentAdapter);
    }
    @OnClick(R.id.favoriteBtn)
        public void favoritList(){
        latestBtn.setTextColor(Color.GRAY);
        favoritBtn.setTextColor(Color.BLACK);
        getFavoritePost();
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

