package com.nexters.moodumdum.fragment;

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

import com.nexters.moodumdum.R;
import com.nexters.moodumdum.adapter.MypageAdapter;
import com.nexters.moodumdum.model.Contents;

import java.util.ArrayList;

/**
 * Created by kimhyehyeon on 2018. 2. 16..
 */

public class FragmentOne extends Fragment {

    private RecyclerView mRecyclerView;
    private MypageAdapter mMypageAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Contents> mMyData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        // 리사이클러뷰 레이아웃매니저

        initDataset();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_one, container, false );

        mRecyclerView = (RecyclerView) view.findViewById( R.id.recyclerView );
        mRecyclerView.setHasFixedSize( true );
        mLayoutManager = new GridLayoutManager( getActivity(), 2 );
        mRecyclerView.setLayoutManager( mLayoutManager );
        mRecyclerView.scrollToPosition( 0 );
        mMypageAdapter = new MypageAdapter( mMyData );
        mRecyclerView.setAdapter( mMypageAdapter );
        mRecyclerView.setItemAnimator( new DefaultItemAnimator() );
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    private void initDataset() {
        //for Test
        mMyData = new ArrayList<>(  );
        for (int i = 0 ; i <10 ; i ++){
            Contents contets = new Contents();
            contets.setDescription("Test >>> " + i);
            mMyData.add(contets);
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

