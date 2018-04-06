package com.nexters.moodumdum;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import butterknife.OnClick;

/**
 * Created by kimhyehyeon on 2018. 2. 19..
 */

public class CategorySelectedActivity extends AppCompatActivity {
//    private SelectedCategoryAdapter selectedCategoryAdapter;
//    private CategoryTabAdapter tabPagerAdapter;
//    String categoryID ="";
//    @BindView(R.id.scrollView)
//    StickyScrollView scrollView;
//    @BindView(R.id.category_title)
//    TextView categoryTitle;
//    @BindView(R.id.categoryBanner)
//    ImageView categoryBanner;
//    @BindView(R.id.contentsTab)
//    TabLayout tabLayout;
//    @BindView(R.id.viewPagerCategory)
//    ViewPager viewPager;


//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_category_selected);
//        ButterKnife.bind(this);
//
//        Intent intent = getIntent();
//        categoryID = intent.getStringExtra("categoryID");
//
//        initView();
//        getCategoryInfo();
//    }

    private void initView() {
//        tabLayout.setupWithViewPager(viewPager);
//        tabPagerAdapter = new CategoryTabAdapter(getSupportFragmentManager());
//        tabPagerAdapter.setCategoryId(categoryID);
//        viewPager.setAdapter(tabPagerAdapter);

    }

    private void getCategoryInfo() {
//        MooDumDumService.of().getCategoryInfo(categoryID).enqueue(new Callback<CategoryInfoModel>() {
//            @Override
//            public void onResponse(Call<CategoryInfoModel> call, Response<CategoryInfoModel> response) {
//                if(response.isSuccessful()) {
//                    final CategoryInfoModel category = response.body();
//                    Log.d("결과", category + "");
//                    categoryTitle.setText(category.getTitle() + " 무덤");
//                    Glide.with(CategorySelectedActivity.this).load(category.getBanner()).into(categoryBanner);
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<CategoryInfoModel> call, Throwable t) {
//
//            }
//        });
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

