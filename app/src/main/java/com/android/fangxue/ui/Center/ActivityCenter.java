package com.android.fangxue.ui.Center;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;

import com.android.fangxue.R;
import com.android.fangxue.base.BaseActivity;
import com.android.fangxue.entity.tabs.TabEntity;
import com.android.fangxue.service.MyService;
import com.android.fangxue.service.OnePixelReceiver;
import com.android.fangxue.ui.MainActivity;
import com.android.fangxue.utils.ACache;
import com.android.fangxue.utils.UpdateManager;
import com.android.fangxue.utils.ViewFindUtils;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;


public class ActivityCenter extends BaseActivity {
    private View mDecorView;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private String[] mTitles = {"首页", "信息", "老师", "设置"};
    private int[] iconShow = {R.mipmap.lesson_blue, R.mipmap.set_blue, R.mipmap.lesson_blue, R.mipmap.teacher_blue, R.mipmap.set_blue};
    private int[] iconHide = {R.mipmap.lesson_gray, R.mipmap.set_gray, R.mipmap.lesson_gray, R.mipmap.teacher_gray, R.mipmap.set_gray};

    public CommonTabLayout mTabLayout;
    protected ACache aCache;
    protected int CurrentPos;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //unregisterReceiver(mOnepxReceiver);
    }

    @Override
    public void setButterKnife() {

    }

    @Override
    protected void onResume() {
        super.onResume();


        if (CurrentPos == 1)
            ((MessageFragment) mFragments.get(CurrentPos)).setCallBackInterFace();
    }

    @Override
    public void onBackPressed() {

        return;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_center;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setFragmentList();

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], iconShow[i], iconHide[i]));
        }

        mDecorView = getWindow().getDecorView();

        mTabLayout = ViewFindUtils.find(mDecorView, R.id.tl);

        mTabLayout.setTabData(mTabEntities, this, R.id.container, mFragments);
        aCache = ACache.get(this);

        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mFragments.get(position).onResume();

                CurrentPos = position;
                switch (position) {
                    case 0:
                        //((indexFragment) mFragments.get(position)).setCallBackInterFace("center");
                        break;

                    case 1:
                        ((MessageFragment) mFragments.get(position)).setCallBackInterFace();
                        break;

                    case 2:
                        ((teacherFragment) mFragments.get(position)).setCallBackInterFace();
                        break;
                    case 3:
                        ((settingFragment) mFragments.get(position)).onPageChange();
                        break;
                }

            }

            @Override
            public void onTabReselect(int position) {


            }
        });

        startService(new Intent(ActivityCenter.this, MyService.class));
        OnPiexMothed();
        //检查版本信息
        CheckoutVersion();
    }

    OnePixelReceiver mOnepxReceiver;

    void CheckoutVersion() {

        UpdateManager manager = new UpdateManager(this);
        manager.checkUpdateInfo();


    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        //super.onSaveInstanceState(outState, outPersistentState);
    }

    //锁屏保留一个像素
    void OnPiexMothed() {

        mOnepxReceiver = new OnePixelReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        registerReceiver(mOnepxReceiver, intentFilter);
    }

    private void setFragmentList() {


        mFragments.add(indexFragment.newInstance());
        mFragments.add(MessageFragment.newInstance());
        mFragments.add(teacherFragment.newInstance());

        mFragments.add(settingFragment.newInstance());
    }


}
