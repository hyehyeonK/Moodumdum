package com.nexters.moodumdum.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.nexters.moodumdum.R;
import com.nexters.moodumdum.adpater.MyPageRecyclerViewAdapter;
import com.nexters.moodumdum.api.MooDumDumService;
import com.nexters.moodumdum.common.PropertyManagement;
import com.nexters.moodumdum.model.CardDataModel;
import com.nexters.moodumdum.model.CardListModel;
import com.nexters.moodumdum.utils.Constants;
import com.nexters.moodumdum.views.DetailCardActivity;
import com.nexters.moodumdum.views.Mypage;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentMyWrite extends Fragment {
    public static Fragment _FragmentMyWrite;
    public RequestManager mGlideRequestManager;
    private UUID uuid;
    int dataOffset;
    boolean noMoreData;
    List<CardDataModel> cardList;

    @BindView(R.id.recyclerView)
    RecyclerView myPageRecyclerView;
    Unbinder unbinder;
    @BindView(R.id.nullWriteImg)
    ImageView nullWriteImg;
    @BindView(R.id.nullWriteText)
    TextView nullWriteText;

    private MyPageRecyclerViewAdapter myPageMyContentsAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        Mypage myPage = (Mypage) Mypage.activity;
        _FragmentMyWrite = this;
        mGlideRequestManager = myPage.glideRequestManager;
        dataOffset = 0;
        noMoreData = false;
        initDataset();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_recyclerview, container, false );

        myPageRecyclerView = (RecyclerView) view.findViewById( R.id.recyclerView);
        myPageRecyclerView.setHasFixedSize( true );
        mLayoutManager = new GridLayoutManager( getActivity(), 2 );
        myPageRecyclerView.setLayoutManager( mLayoutManager );
        myPageRecyclerView.scrollToPosition( 0 );
        myPageMyContentsAdapter = new MyPageRecyclerViewAdapter( getContext(), cardList = new ArrayList<>());
        myPageMyContentsAdapter.setOnItemClickListener(new MyPageRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CardDataModel cardInfo, int postion) {
                Intent intent = new Intent( getContext(), DetailCardActivity.class );
                intent.putExtra( "cardInfo", cardInfo);
                intent.putExtra("_position", postion);
                getActivity().startActivityForResult(intent, Constants.RESULT_MYWRITE);
            }
        });
        myPageRecyclerView.setAdapter( myPageMyContentsAdapter );
        myPageRecyclerView.setItemAnimator( new DefaultItemAnimator() );

        myPageRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(dy > 0) { //scroll down 이면
                    if(isGridBottom(myPageRecyclerView) && !noMoreData){ //스크롤 마지막이면
                        initDataset();
                    }
                }
            }
        });


        unbinder = ButterKnife.bind( this, view );

        return view;
    }

    public void deleteMyWrite(int position)
    {
        if(position != -1)
        {
           myPageMyContentsAdapter.deleteItem(position);
            Mypage myPage = (Mypage) Mypage.activity;
            myPage.resetWriteCount();
        }
    }

    public boolean isGridBottom(RecyclerView recyclerView) {
        GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        return visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1 && !recyclerView.canScrollVertically(1);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated( savedInstanceState );
    }


    private void initDataset() {
        String uuid = PropertyManagement.getUserId(getContext());
        MooDumDumService.of().getMyContents(uuid, dataOffset).enqueue(new Callback<CardListModel>() {
            @Override
            public void onResponse(Call<CardListModel> call, Response<CardListModel> response) {
                if (response.isSuccessful()) {
                    final CardListModel items = response.body();
                    if (0 == items.count) {
                        nullWriteImg.setVisibility(View.VISIBLE);
                        nullWriteText.setVisibility( View.VISIBLE );
                        return;
                    }
                    if(items.next == null){
                        noMoreData = true;
                    }
                    if(dataOffset == 0 ) {
                        myPageMyContentsAdapter.setMyContentsList(items.result);
                    } else {
                        myPageMyContentsAdapter.addMoreItem(items.result);
                    }
                    dataOffset += 10;

                }
            }

            @Override
            public void onFailure(Call<CardListModel> call, Throwable t) {

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
