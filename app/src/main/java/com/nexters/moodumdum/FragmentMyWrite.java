package com.nexters.moodumdum;

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

import com.nexters.moodumdum.adpater.MyPageRecyclerViewAdapter;
import com.nexters.moodumdum.api.MooDumDumService;
import com.nexters.moodumdum.model.ContentsModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentMyWrite extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView myPageRecyclerView;
    Unbinder unbinder;
    @BindView(R.id.nullWriteImg)
    ImageView nullWriteImg;
    @BindView(R.id.nullWriteText)
    TextView nullWriteText;

    private MyPageRecyclerViewAdapter myPageMyContentsAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
//    private ArrayList<MywriteData> mMywriteData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

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
        myPageMyContentsAdapter = new MyPageRecyclerViewAdapter( getContext() );
        myPageRecyclerView.setAdapter( myPageMyContentsAdapter );
        myPageRecyclerView.setItemAnimator( new DefaultItemAnimator() );
        unbinder = ButterKnife.bind( this, view );

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated( savedInstanceState );
    }


    private void initDataset() {
        //for Test
        String Uuid = "KHH";
        MooDumDumService.of().getMyContents(Uuid).enqueue(new Callback<ContentsModel>() {
            @Override
            public void onResponse(Call<ContentsModel> call, Response<ContentsModel> response) {
                if (response.isSuccessful()) {
                    final ContentsModel items = response.body();
                    if (items.getResult().isEmpty()) {
                        nullWriteImg.setVisibility(View.VISIBLE);
                        nullWriteText.setVisibility( View.VISIBLE );
                    }
                    myPageMyContentsAdapter.setMyContentsList(items.getResult());
                }
            }

            @Override
            public void onFailure(Call<ContentsModel> call, Throwable t) {

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
