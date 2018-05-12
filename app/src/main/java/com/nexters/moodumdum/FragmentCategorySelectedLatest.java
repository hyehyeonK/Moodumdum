package com.nexters.moodumdum;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nexters.moodumdum.adpater.SelectedCategoryAdapter;
import com.nexters.moodumdum.anim.RecyclerViewDecoration;
import com.nexters.moodumdum.api.MooDumDumService;
import com.nexters.moodumdum.common.PropertyManagement;
import com.nexters.moodumdum.model.ContentsModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kimhyehyeon on 2018. 2. 28..
 */

public class FragmentCategorySelectedLatest extends Fragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;
    int dataOffset;
    private String categoryID;
    private SelectedCategoryAdapter selectedCategoryAdapter;
    RecyclerView.LayoutManager linearLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        dataOffset = 0;
        getPost();
    }

    public void setCategoryId(String categoryID){
        this.categoryID = categoryID;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_recyclerview, container, false );
        Context context = view.getContext();
        recyclerView = (RecyclerView) view.findViewById( R.id.recyclerView);
//        recyclerView.setHasFixedSize( true );
        linearLayoutManager = new LinearLayoutManager(getActivity());
//        selectedCategoryAdapter = new SelectedCategoryAdapter(getContext(), getActivity());
        recyclerView.setAdapter(selectedCategoryAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new RecyclerViewDecoration(12));
        unbinder = ButterKnife.bind( this, view );

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated( savedInstanceState );
    }


    private void getPost() {
        String uuid = PropertyManagement.getUserId(getContext());
        MooDumDumService.of().getCategoryContentsInOrderOfPriority(uuid, categoryID, dataOffset).enqueue(new Callback<ContentsModel>() {
            @Override
            public void onResponse(Call<ContentsModel> call, Response<ContentsModel> response) {
                if (response.isSuccessful()) {
                    final ContentsModel items = response.body();
                    selectedCategoryAdapter.setPostList(items.getResult());
                }
            }

            @Override
            public void onFailure(Call<ContentsModel> call, Throwable t) {
//                Toast.makeText("데이터를 가져오는 데 실패 했습니다.").getXOffset();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
