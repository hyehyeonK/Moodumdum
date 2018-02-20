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

public class FragmentMyJomun extends Fragment {

    @BindView(R.id.myjomunRecyclerview)
    RecyclerView myjomunRecyclerview;
    Unbinder unbinder;
    private MypageMyjomunAdapter mMypageAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<MyjomunData> mMyData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        initDataset();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_myjomun, container, false );

        myjomunRecyclerview = (RecyclerView) view.findViewById( R.id.myjomunRecyclerview );
        myjomunRecyclerview.setHasFixedSize( true );
        mLayoutManager = new GridLayoutManager( getActivity(), 2 );
        myjomunRecyclerview.setLayoutManager( mLayoutManager );
        myjomunRecyclerview.scrollToPosition( 0 );
        mMypageAdapter = new MypageMyjomunAdapter( mMyData );
        myjomunRecyclerview.setAdapter( mMypageAdapter );
        myjomunRecyclerview.setItemAnimator( new DefaultItemAnimator() );
        unbinder = ButterKnife.bind( this, view );
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated( savedInstanceState );
    }


    private void initDataset() {
        //for Test
        mMyData = new ArrayList<>();
        mMyData.add( new MyjomunData( "오늘 비도 오고 완전 우울함ㅠㅠ" ) );
        mMyData.add( new MyjomunData( "내용2" ) );
        mMyData.add( new MyjomunData( "내용3" ) );

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
