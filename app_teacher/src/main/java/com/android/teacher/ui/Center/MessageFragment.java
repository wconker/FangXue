package com.android.teacher.ui.Center;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.teacher.R;
import com.android.teacher.base.BaseFragment;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends BaseFragment implements OnTabSelectListener {


    private String[] mTitles = {"综合", "通知", "作业", "分享"};
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    @Bind(R.id.tl_1)
    SegmentTabLayout tabLayout_1;
    private int FirstLoad = 0;
    private int mPosition = 0;

    public static MessageFragment newInstance() {

        return new MessageFragment();
    }


    @Override
    protected void initView(View view) {

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("MessageFragment", "");


    }

    @Override
    protected int getLayout() {
        return R.layout.ui_center_message;
    }

    void initFragment() {
        for (String title : mTitles) {
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
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onTabSelect(int position) {
        mPosition = position;
        if (position == 0) {
            ((notifyFragment) mFragments.get(position)).setRequest(0);
        }

        if (position == 1) {
            ((notifyFragment) mFragments.get(position)).setRequest(1);
        }
        if (position == 2) {
            ((notifyFragment) mFragments.get(position)).setRequest(2);
        }
        if (position == 3) {
            ((notifyFragment) mFragments.get(position)).setRequest(3);
        }
    }

    public void setCallBackInterFace() {
        if (FirstLoad == 0 || FirstLoad == 1) {
            ((notifyFragment) mFragments.get(mPosition)).list.clear();
            ((notifyFragment) mFragments.get(mPosition)).setRequest(mPosition);
            FirstLoad++;
        } else {
            ((notifyFragment) mFragments.get(mPosition)).setRequest(mPosition);
        }

    }

    @Override
    public void onTabReselect(int position) {

    }
}
