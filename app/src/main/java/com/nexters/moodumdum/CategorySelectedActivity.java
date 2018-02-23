package com.nexters.moodumdum;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.amar.library.ui.StickyScrollView;
import com.nexters.moodumdum.adpater.SelectedCategoryAdapter;
import com.nexters.moodumdum.api.MooDumDumService;
import com.nexters.moodumdum.model.ContentsModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kimhyehyeon on 2018. 2. 19..
 */

public class CategorySelectedActivity extends AppCompatActivity {
    private SelectedCategoryAdapter selectedCategoryAdapter;
    LinearLayoutManager linearLayoutManager;
    String categoryID ="";
    @BindView(R.id.scrollView)
    StickyScrollView scrollView;

    @BindView(R.id.rv_contents)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selected);
        ButterKnife.bind(this);
        initView();
        getPost();
    }

    private void initView() {
        linearLayoutManager = new LinearLayoutManager(this);
        selectedCategoryAdapter = new SelectedCategoryAdapter(CategorySelectedActivity.this);
        recyclerView.setAdapter(selectedCategoryAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new RecyclerViwDecoraiton(12));
    }
    private void getPost() {
        Intent intent = getIntent();
        categoryID = intent.getStringExtra("categoryID");
        MooDumDumService.of().getCategoryContentsInOrderOfPriority(categoryID).enqueue(new Callback<ContentsModel>() {
            @Override
            public void onResponse(Call<ContentsModel> call, Response<ContentsModel> response) {
                if (response.isSuccessful()) {
                    final ContentsModel items = response.body();
                    selectedCategoryAdapter.setPostList(items.getResult());
                }
            }

            @Override
            public void onFailure(Call<ContentsModel> call, Throwable t) {

            }
        });
    }

}
class RecyclerViwDecoraiton extends RecyclerView.ItemDecoration {
    private final int divHeight;
    public RecyclerViwDecoraiton(int divHeight) {
        this.divHeight = divHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.top =divHeight;
    }

}