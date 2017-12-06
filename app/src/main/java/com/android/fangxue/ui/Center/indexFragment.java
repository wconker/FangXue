package com.android.fangxue.ui.Center;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.PluralsRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.fangxue.R;
import com.android.fangxue.adapter.IndexViewAdapter.ListAdapter;
import com.android.fangxue.adapter.commonAdapter.CommonAdapter;
import com.android.fangxue.adapter.commonAdapter.CommonViewHolder;
import com.android.fangxue.adapter.commonAdapter.RecycCommomAdapter;
import com.android.fangxue.base.BaseFragment;
import com.android.fangxue.callback.MessageCallBack;
import com.android.fangxue.entity.Homework;
import com.android.fangxue.entity.News;
import com.android.fangxue.newwork.MessageCenter;
import com.android.fangxue.ui.Contact.ContactList;
import com.android.fangxue.ui.NewsInfo;
import com.android.fangxue.utils.JSONUtils;
import com.android.fangxue.utils.Net;
import com.android.fangxue.utils.SharedPrefsUtil;
import com.android.fangxue.utils.Toast;
import com.android.fangxue.widget.CircleImageView;
import com.android.fangxue.widget.HaveHeaderListView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class indexFragment extends BaseFragment implements ViewPager.PageTransformer, MessageCallBack {
    @Bind(R.id.back_btn)
    ImageView backBtn;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.vp)
    ViewPager vp;
    @Bind(R.id.newBanner)
    FrameLayout newBanner;
    @Bind(R.id.nest)
    NestedScrollView nest;
    @Bind(R.id.delay_layout)
    LinearLayout delay_layout;
    @Bind(R.id.tx)
    CircleImageView tx;
    @Bind(R.id.news)
    ListView news;
    @Bind(R.id.delayNotify)
    TextView delayNotify;
    @Bind(R.id.arrTempf)
    TextView arrTempf;
    @Bind(R.id.studentLeave)
    TextView studentLeave;
    @Bind(R.id.ref_getNotify)
    protected SwipeRefreshLayout ref_getNotify;
    @Bind(R.id.title_arrived)
    TextView titleArrived;
    @Bind(R.id.indexhomeworklist)
    RecyclerView indexhomeworklist;
    @Bind(R.id.indexlist)
    RecyclerView indexlist;

    private String USEBY = "";

    private int HomeworkFlag = 100;
    private List<News.DataBean> newsList;
    //    @Bind(R.id.TurnRight)
//    LinearLayout TurnRight;
//    @Bind(R.id.showDate)
//    TextView showDate;
//    @Bind(R.id.TurnLeft)
//    LinearLayout TrunLeft;
    private String week = "";
    private ListAdapter indexAdapter;
    private ListAdapter indexAdapter2;

    private int atFirst = 0;
    private Observer<String> ober;
    private List<Homework.DataBean> indexhomeworkBean = new ArrayList<>();
    protected MessageCenter messageCenter;
    private int fistLoad = 0;
    private int Current = 0;

    private MessageCallBack messageCallBack = this;

    private NewsAdapter newsAdapter;
    private MyPagerAdapter mAdapter;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles = {
            "一", "一", "一", "一", "一", "一", "一", "一", "一", "一", "一", "一", "一"
    };

    public static indexFragment newInstance() {
        return new indexFragment();
    }


    @Override
    protected void initView(View rootView) {

    }

    @Override
    protected int getLayout() {
        return R.layout.ui_center_indexfragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public String StringData(int day) {
        int dayCount = (day > 7 ? day % 7 : day);
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
        String mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        String mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH) + day);// 获取当前月份的日期号码
        String mWay = String.valueOf((c.get(Calendar.DAY_OF_WEEK) + dayCount) <= 7 ? (c.get(Calendar.DAY_OF_WEEK) + dayCount) : (c.get(Calendar.DAY_OF_WEEK) + dayCount) % 7);
        String result = "";

        if ("1".equals(mWay)) {
            result = "日";
        } else if ("2".equals(mWay)) {
            result = "一";
        } else if ("3".equals(mWay)) {
            result = "二";
        } else if ("4".equals(mWay)) {
            result = "三";
        } else if ("5".equals(mWay)) {
            result = "四";
        } else if ("6".equals(mWay)) {
            result = "五";
        } else if ("7".equals(mWay)) {
            result = "六";
        }
        return result;
    }

    //发送通知
    public void setCallBackInterFace(String UserWho) {

        if (SharedPrefsUtil.getValue(getActivity(), "loginXML", "UserName", "").equals("13312345678")) {
            //只有展示账号显示新闻信息
            newBanner.setVisibility(View.VISIBLE);
            news.setVisibility(View.VISIBLE);
            BindNews();
        } else {

            messageCenter.SendYouMessage(messageCenter.ChooseCommand().getnotify(),this);
        }
        if (atFirst == 0) {

            atFirst = 1;
        } else {

        }


    }

    public String getDataTime(int day) {
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
        String mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        int dataInfcat = c.get(Calendar.DAY_OF_MONTH);
        String mDay = String.valueOf(dataInfcat + day);// 获取当前月份的日期号码
        String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        int CompareDay = dataInfcat + day;
        if (mMonth.equals("1") || mMonth.equals("3") || mMonth.equals("5") || mMonth.equals("7") || mMonth.equals("8") || mMonth.equals("10") || mMonth.equals("12")) {
            if (CompareDay > 31) {
                mDay = String.valueOf((dataInfcat + day) - 31);
                mMonth = String.valueOf(Integer.valueOf(mMonth) + 1);
            }
        } else if (mMonth.equals("4") || mMonth.equals("6") || mMonth.equals("9") || mMonth.equals("11")) {
            if (CompareDay > 30) {
                mDay = String.valueOf((dataInfcat + day) - 30);
                mMonth = String.valueOf(Integer.valueOf(mMonth) + 1);
            }

        } else {
            if (CompareDay > 28) {
                mDay = String.valueOf((dataInfcat + day) - 28);
                mMonth = String.valueOf(Integer.valueOf(mMonth) + 1);
            }
        }
        return mYear + "-" + mMonth + "-" + mDay;
    }

    @Override
    public void onResume() {
        super.onResume();
        initHeader();
        Log.d("哈哈哈哈哈", "onResume: "+((ActivityCenter) getActivity()).CurrentPos);
        if (((ActivityCenter) getActivity()).CurrentPos == 0) {
            setCallBackInterFace("me");
        }
    }

    //初始化头部信息
    private void initHeader() {

        String StrTitle = SharedPrefsUtil.getValue(getActivity(), "userXML", "studentClassname", "") +
                " " + SharedPrefsUtil.getValue(getActivity(), "userXML", "studentName", "");
        toolbarTitle.setText(StrTitle);
        backBtn.setVisibility(View.GONE);
        tx.setVisibility(View.VISIBLE);
        String imgUrl = SharedPrefsUtil.getValue(getActivity(), "userXML", "headerimg", "");
        if (imgUrl.isEmpty()) {
            Glide.with(getActivity()).load(R.drawable.username).into(tx);
        } else {
            Glide.with(getActivity()).load(imgUrl).into(tx);
        }
        tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ContactList.class));
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);

        messageCenter = new MessageCenter(this);


        ref_getNotify.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                setCallBackInterFace("me");
                ref_getNotify.setRefreshing(false);
            }
        });
        for (String title : mTitles) {
            mFragments.add(SimpleCardFragment.getInstance(title));
        }

        mAdapter = new MyPagerAdapter(getChildFragmentManager());

        vp.setAdapter(mAdapter);
        vp.setCurrentItem(1);
//        tabLayout_9.setViewPager(vp);
//        tabLayout_9.setIndicatorColor(R.color.colorPrimary);
        nest.setSmoothScrollingEnabled(true);
        // nest.setNestedScrollingEnabled(false);
        indexAdapter = new ListAdapter(getActivity(), indexhomeworkBean);
        indexhomeworklist.setAdapter(indexAdapter);
        indexhomeworklist.setNestedScrollingEnabled(false);
        indexhomeworklist.setLayoutManager(new LinearLayoutManager(getActivity()));


        indexAdapter2 = new ListAdapter(getActivity(), indexhomeworkBean);
        indexlist.setNestedScrollingEnabled(false);
        indexlist.setAdapter(indexAdapter2);
        indexlist.setLayoutManager(new LinearLayoutManager(getActivity()));


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            vp.setNestedScrollingEnabled(false);
        }
        vp.setClipToPadding(false);
        // 让左右都留出一个item大小的边距
        vp.setPadding(100, 0, 100, 0);
        vp.setPageMargin(35);

        vp.setOffscreenPageLimit(4);
        vp.setPageTransformer(true, this);

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {


                int dataT = position - Current;

                ((SimpleCardFragment) mFragments.get(position)).lanchMessage(1, getDataTime(dataT), getDataTime(dataT) + "      星期" + StringData(position - Current));


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        DealWithData();

        return rootView;
    }

    private void setPage() {

        if (fistLoad == 0) {
            ((SimpleCardFragment) mFragments.get(1)).lanchMessage(1, getDataTime(0), getDataTime(Current) + "    星期" + StringData(Current));
            Current = 1;
        }
        fistLoad = 1;

    }

    private void BindNews() {

        newsList = new ArrayList<>();
        newsAdapter = new NewsAdapter(getActivity(), newsList, R.layout.news);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            news.setNestedScrollingEnabled(false);
        }
        news.setAdapter(newsAdapter);
        news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent iii = new Intent(getActivity(), NewsInfo.class);
                iii.putExtra("newInfo", newsList.get(i).getUrl().toString());
                startActivity(iii);
            }
        });
        messageCenter.SendYouMessage(messageCenter.ChooseCommand().getNews());


    }


    public void transformPage(View view, float position) {
        if (position < -1) {
            view.setScaleY(0.8f);
        } else if (position < 0) {
            view.setScaleY(0.2f * position + 1);
        } else if (position < 1) {
            view.setScaleY(-0.2f * position + 1);
        } else {
            view.setScaleY(0.8f);
        }
    }

    void DealWithData() {
        ober = new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

                JSONObject cmd = JSONUtils.StringToJSON(s);
                Gson gson = new Gson();
                switch (JSONUtils.getString(cmd, "cmd")) {
                    case "system.getnotify":   //客戶端主动调取放学通知
                        JSONObject data = JSONUtils.getSingleJSON(cmd, "data", 0);
                        String arrivetime = JSONUtils.getString(data, "arrivetime");
                        String classtime = JSONUtils.getString(data, "classtime");
                        String leavetime = JSONUtils.getString(data, "leavetime");
                        if (classtime != null && !classtime.equals("null") && !classtime.isEmpty()) {
                            arrTempf.setText(classtime);
                        } else {
                            arrTempf.setText("--:--");
                        }
                        if (arrivetime != null && !arrivetime.equals("null") && !arrivetime.isEmpty())
                            titleArrived.setText(arrivetime);
                        else {
                            titleArrived.setText("--:--");
                        }

                        if (leavetime != null && !leavetime.equals("null") && !leavetime.isEmpty())
                            studentLeave.setText(leavetime);
                        else {
                            studentLeave.setText("--:--");
                        }
                        String timeofplan = JSONUtils.getString(data, "timeofplan");
                        if (timeofplan != null && !JSONUtils.getString(data, "reason").equals("null") && !timeofplan.isEmpty()) {
                            delayNotify.setText("【" + timeofplan + "】：" + "" + JSONUtils.getString(data, "reason"));
                            delay_layout.setVisibility(View.VISIBLE);
                        } else {
                            delay_layout.setVisibility(View.GONE);
                            delayNotify.setText("");
                        }
                        messageCenter.SendYouMessage(messageCenter.ChooseCommand().getlist_message(2, "", "", 0)); //获取作业
                        break;
                    case "system.notify": //服务端推送通知
                        messageCenter.SendYouMessage(messageCenter.ChooseCommand().getnotify());
                        break;
                    case "message.getlist": //获取信息详情

                        Type type = new TypeToken<Homework>() {
                        }.getType();

                        if (HomeworkFlag == 100) {
                            Log.e("获取作业信息", "homework" + s);
                            Homework dataBean1 = gson.fromJson(String.valueOf(cmd), type);
                            indexhomeworkBean.clear();
                            if (dataBean1.getData().size() > 0) {

                                indexhomeworkBean.addAll(dataBean1.getData());


                            }
                            messageCenter.SendYouMessage(messageCenter.ChooseCommand().getlist_message(1, "", "", 0)); //获取作业
                            indexAdapter2.notifyDataSetChanged();
                            HomeworkFlag = 200;
                        } else {
                            Log.e("获取通知信息", "notify" + s);
                            Homework dataBean2 = gson.fromJson(String.valueOf(cmd), type);
                            indexhomeworkBean.clear();
                            if (dataBean2.getData().size() > 0) {

                                indexhomeworkBean.addAll(dataBean2.getData());


                            }
                            indexAdapter.notifyDataSetChanged();
                            HomeworkFlag = 100;
                            setPage();
                        }
                        break;
                    case "system.getnewslist":
                        newsList.clear();
                        Type typeNews = new TypeToken<News>() {
                        }.getType();
                        News dataBeanNews = gson.fromJson(String.valueOf(cmd), typeNews);
                        newsList.addAll(dataBeanNews.getData());
                        newsAdapter.notifyDataSetChanged();

                        break;
                }

            }
        };
    }

    @Override
    public void onMessage(String str) {
        Log.e("Index", str);
        rx.Observable.just(str)
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(ober);

    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    class NewsAdapter extends CommonAdapter<News.DataBean> {

        NewsAdapter(Context context, List<News.DataBean> list, int layoutId) {
            super(context, list, layoutId);
        }

        @Override
        public void setViewContent(CommonViewHolder viewHolder, News.DataBean dataBean) {
            viewHolder.setText(R.id.title_news, dataBean.getTitle());
        }
    }


}
