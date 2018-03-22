package com.nexters.moodumdum;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.amar.library.ui.StickyScrollView;
import com.bumptech.glide.Glide;
import com.nexters.moodumdum.adpater.CategoryTabAdapter;
import com.nexters.moodumdum.api.MooDumDumService;
import com.nexters.moodumdum.model.CategoryInfoModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kimhyehyeon on 2018. 2. 19..
 */

public class CategorySelectedActivity extends AppCompatActivity {
//    private SelectedCategoryAdapter selectedCategoryAdapter;
    private CategoryTabAdapter tabPagerAdapter;
    String categoryID ="";
    @BindView(R.id.scrollView)
    StickyScrollView scrollView;
    @BindView(R.id.category_title)
    TextView categoryTitle;
    @BindView(R.id.categoryBanner)
    ImageView categoryBanner;
    @BindView(R.id.contentsTab)
    TabLayout tabLayout;
    @BindView(R.id.viewPagerCategory)
    ViewPager viewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selected);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        categoryID = intent.getStringExtra("categoryID");

        initView();
        getCategoryInfo();
    }

    private void initView() {
        tabLayout.setupWithViewPager(viewPager);
        tabPagerAdapter = new CategoryTabAdapter(getSupportFragmentManager());
        tabPagerAdapter.setCategoryId(categoryID);
        viewPager.setAdapter(tabPagerAdapter);

    }

    private void getCategoryInfo() {
        MooDumDumService.of().getCategoryInfo(categoryID).enqueue(new Callback<CategoryInfoModel>() {
            @Override
            public void onResponse(Call<CategoryInfoModel> call, Response<CategoryInfoModel> response) {
                if(response.isSuccessful()) {
                    final CategoryInfoModel category = response.body();
                    Log.d("결과", category + "");
                    categoryTitle.setText(category.getTitle() + " 무덤");
                    Glide.with(CategorySelectedActivity.this).load(category.getBanner()).into(categoryBanner);

                }
            }

            @Override
            public void onFailure(Call<CategoryInfoModel> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.btn_back)
    public void onBtnBackClicked() {
        this.finish();
    }

    @OnClick(R.id.onClickToPlus)
    public void onViewClicked() {
        Intent intent = new Intent( getApplicationContext(), PlusActivity.class );
        startActivity( intent );
    }
}

