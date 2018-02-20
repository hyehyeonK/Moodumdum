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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class FragmentMyWrite extends Fragment {


    @BindView(R.id.mywriteRecyclerView)
    RecyclerView mywriteRecyclerView;
    Unbinder unbinder;

    private MypageMywriteAdapter mMypageMywriteAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<MywriteData> mMywriteData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        initDataset();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_mywrite, container, false );

        mywriteRecyclerView = (RecyclerView) view.findViewById( R.id.mywriteRecyclerView );
        mywriteRecyclerView.setHasFixedSize( true );
        mLayoutManager = new GridLayoutManager( getActivity(), 2 );
        mywriteRecyclerView.setLayoutManager( mLayoutManager );
        mywriteRecyclerView.scrollToPosition( 0 );
        mMypageMywriteAdapter = new MypageMywriteAdapter( mMywriteData );
        mywriteRecyclerView.setAdapter( mMypageMywriteAdapter );
        mywriteRecyclerView.setItemAnimator( new DefaultItemAnimator() );
        unbinder = ButterKnife.bind( this, view );
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated( savedInstanceState );
    }


    private void initDataset() {
        //for Test
        mMywriteData = new ArrayList<>();
        mMywriteData.add( new MywriteData( "오늘 비도 오고 완전 우울함ㅠㅠ" ) );

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
