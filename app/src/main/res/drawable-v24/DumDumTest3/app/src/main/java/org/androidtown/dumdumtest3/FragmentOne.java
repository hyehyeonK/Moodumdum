package org.androidtown.dumdumtest3;

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

import java.util.ArrayList;

public class FragmentOne extends Fragment {

    private RecyclerView mRecyclerView;
    private MypageAdapter mMypageAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<MyData> mMyData;

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
        mMyData.add(new MyData( "오늘 비도 오고 완전 우울함ㅠㅠ" ));
        mMyData.add(new MyData( "저리강" ));

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
