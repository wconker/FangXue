package com.android.fangxue.ui.Center;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.android.fangxue.adapter.commonAdapter.CommonViewHolder;
import com.android.fangxue.R;
import com.android.fangxue.base.BaseFragment;
import com.android.fangxue.adapter.commonAdapter.CommonAdapter;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends BaseFragment implements OnTabSelectListener {


    private String[] mTitles = {"全部", "通知", "作业", "分享"};
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    @Bind(R.id.tl_1)
    SegmentTabLayout tabLayout_1;

    private int mPosition = 0;
    private int FirstLoad = 0;

    public static MessageFragment newInstance() {

        return new MessageFragment();
    }


    @Override
    protected void initView(View view) {

    }


    @Override
    protected int getLayout() {
        return R.layout.ui_center_minefragment;
    }

    void initFragment() {
        for (String title : mTitles) {
            mFragments.add(notifyFragment.newInstance());
            mFragments.add(notifyFragment.newInstance());
            mFragments.add(notifyFragment.newInstance());
            mFragments.add(notifyFragment.newInstance());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        initFragment();
        tabLayout_1.setTabData(mTitles);
        //显示未读消息
        //tabLayout_1.showDot(2);
        tabLayout_1.setTabData(mTitles, getActivity(), R.id.fl_change, mFragments);
        tabLayout_1.setOnTabSelectListener(this);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("MessageFragment", "MessageFragment");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void setCallBackInterFace() {
        Log.e("MessageFragment", FirstLoad + "");
        if (FirstLoad == 0 || FirstLoad == 1) {
            ((notifyFragment) mFragments.get(mPosition)).setCallBackInterFace(0);
            FirstLoad++;
        } else {
            ((notifyFragment) mFragments.get(mPosition)).setCallBackInterFace(((notifyFragment) mFragments.get(mPosition)).RequestId);
        }

    }

    @Override
    public void onTabSelect(int position) {


        mPosition = position;
        //type 0是全部 1通知 2 是作业 3是其他
        if (position == 0) {
            ((notifyFragment) mFragments.get(position)).setCallBackInterFace(0);
        }
        if (position == 1) {
            ((notifyFragment) mFragments.get(position)).setCallBackInterFace(1);
        }
        if (position == 2) {
            ((notifyFragment) mFragments.get(position)).setCallBackInterFace(2);

        }
        if (position == 3) {
            ((notifyFragment) mFragments.get(position)).setCallBackInterFace(3);
        }
    }

    @Override
    public void onTabReselect(int position) {
        Log.e("Conekr", "现在是位置" + position);
    }
}
