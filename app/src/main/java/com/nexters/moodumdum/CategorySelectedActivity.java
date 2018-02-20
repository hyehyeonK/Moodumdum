package com.nexters.moodumdum;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.amar.library.ui.StickyScrollView;
import com.nexters.moodumdum.adpater.SelectedCategoryAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kimhyehyeon on 2018. 2. 19..
 */

public class CategorySelectedActivity extends AppCompatActivity {
    private SelectedCategoryAdapter selectedCategoryAdapter;
    LinearLayoutManager linearLayoutManager;

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
