package com.nexters.moodumdum;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

    @BindView(R.id.category_title)
    TextView categoryTitle;
    @BindView(R.id.categoryBanner)
    ImageView categoryBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selected);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        categoryID = intent.getStringExtra("categoryID");

        initView();
        getCategoryInfo();
        getPost();
    }

    private void initView() {
//        switch (categoryID) {
//            case "1" : categoryTitle.setText("흑역사"); break;
//            case "2" : categoryTitle.setText("기타"); break;
//            case "3" : categoryTitle.setText("가정사"); break;
//            case "4" : categoryTitle.setText("연애사"); break;
//            case "5" : categoryTitle.setText("자존감"); break;
//            case "6" : categoryTitle.setText("직장사"); break;
//        }


        linearLayoutManager = new LinearLayoutManager(this);
        selectedCategoryAdapter = new SelectedCategoryAdapter(CategorySelectedActivity.this);
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
                    categoryTitle.setText(category.getTitle());
                    Glide.with(CategorySelectedActivity.this).load(category.getBanner()).into(categoryBanner);

                }
            }

            @Override
            public void onFailure(Call<CategoryInfoModel> call, Throwable t) {

            }
        });
    }
    private void getPost() {
        MooDumDumService.of().getCategoryContentsInOrderOfPriority(categoryID).enqueue(new Callback<ContentsModel>() {
            @Override
            public void onResponse(Call<ContentsModel> call, Response<ContentsModel> response) {
                if (response.isSuccessful()) {
                    final ContentsModel items = response.body();
//                    for(int i = 0; i < items.getResult().size(); i++){
//                        String back = items.getResult().get(i).getImage_url();
//                        Random random = new Random();
//                        if(back.length()<13){
//                            int num = random.nextInt(50)+1;
//                            if(num < 10){
//                                back =  "http://13.125.76.112/statics/board_background/0"+ num +".png";
//                            } else {
//                                back =  "http://13.125.76.112/statics/board_background/"+ num +".png";
//                            }
//                        }
//                        items.getResult().get(i).setImage_url(back);
//
//                    }
                    selectedCategoryAdapter.setPostList(items.getResult());
                }
            }

            @Override
            public void onFailure(Call<ContentsModel> call, Throwable t) {

            }
        });
    }

}

