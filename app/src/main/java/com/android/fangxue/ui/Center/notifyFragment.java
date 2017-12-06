package com.android.fangxue.ui.Center;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.fangxue.R;
import com.android.fangxue.adapter.IndexViewAdapter.ListAdapter;
import com.android.fangxue.adapter.IndexViewAdapter.notifyAdapter;
import com.android.fangxue.callback.MessageCallBack;
import com.android.fangxue.entity.Homework;
import com.android.fangxue.newwork.MessageCenter;
import com.android.fangxue.utils.JSONUtils;
import com.android.fangxue.utils.Net;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
/**
 * A simple {@link Fragment} subclass.
 */
public class notifyFragment extends Fragment implements MessageCallBack {

    private static final int RESULT_OK = 1;
    @Bind(R.id.notify_list)
    RecyclerView notify_list;
    @Bind(R.id.back_btn)
    ImageView backBtn;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.search_img)
    ImageView searchImg;
    @Bind(R.id.refresh)
    SwipeRefreshLayout refresh;
    @Bind(R.id.search_edit)
    EditText search_edit;
    private List<Homework.DataBean> list = new ArrayList<>();
    private notifyAdapter listAdapter;
    private File output;
    private Uri imageUri;
    private MessageCenter messageCenter;
    private Observer<String> observer;
    public int firstLoad = 0;
    private ActivityCenter center;
    private int IsPull = 0;
    public int RequestId = 0;

    public static notifyFragment newInstance() {

        return new notifyFragment();
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    public void setCallBackInterFace(int typeId) {
        RequestId = typeId;
        Log.d("id", "typeId:"+RequestId);
        if (firstLoad == 0) {
            refresh.setRefreshing(true);
            messageCenter.SendYouMessage(messageCenter.ChooseCommand().getlist_message(RequestId, "", "", 0),this);
            firstLoad = 1;
        }
        if (!Net.isNetworkAvailable(getActivity())) {
            DealWithData(center.aCache.getAsString("notify"));
            refresh.setRefreshing(false);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.ui_center_notifyfragment, container, false);
        ButterKnife.bind(this, view);
        messageCenter = new MessageCenter();

        //搜索功能
        searchImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IsPull = 1;

                messageCenter.SendYouMessage(messageCenter.ChooseCommand().getlist_message(RequestId,
                        search_edit.getText().toString(), "", 0),notifyFragment.this);

            }
        });

        center = (ActivityCenter) getActivity();
        backBtn.setVisibility(View.GONE);
        toolbarTitle.setText("通知");
        observer = new Observer<String>() {
            @Override
            public void onCompleted() {

                refresh.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {


                DealWithData(s);

            }
        };

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                IsPull = 1;
                messageCenter.SendYouMessage(messageCenter.ChooseCommand().getlist_message(RequestId, "", "", 0),notifyFragment.this);
            }
        });

        listAdapter = new notifyAdapter(getActivity(), list);
        notify_list.setAdapter(listAdapter);
        notify_list.setLayoutManager(new LinearLayoutManager(getActivity()));

        notify_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //当前RecyclerView显示出来的最后一个的item的position
                int lastPosition = -1;

                //当前状态为停止滑动状态SCROLL_STATE_IDLE时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    if (layoutManager instanceof GridLayoutManager) {
                        //通过LayoutManager找到当前显示的最后的item的position
                        lastPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                    } else if (layoutManager instanceof LinearLayoutManager) {
                        lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                        //因为StaggeredGridLayoutManager的特殊性可能导致最后显示的item存在多个，所以这里取到的是一个数组
                        //得到这个数组后再取到数组中position值最大的那个就是最后显示的position值了
                        int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                        ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(lastPositions);

                    }

                    //时判断界面显示的最后item的position是否等于itemCount总数-1也就是最后一个item的position
                    //如果相等则说明已经滑动到最后了
                    if (list.size() >= 6) {
                        if (lastPosition == recyclerView.getLayoutManager().getItemCount() - 1) {
                            messageCenter.SendYouMessage(messageCenter.ChooseCommand().getlist_message(RequestId, "", String.valueOf(list.get(list.size() - 1).getId()), list.size()),notifyFragment.this);

                        }
                    }

                }
            }
        });

        return view;
    }

    private void DealWithData(String s) {
        JSONObject cmd = JSONUtils.StringToJSON(s);

        if (JSONUtils.getString(cmd, "cmd").equals("message.getlist")) {
            center.aCache.put("notify", s);
            Gson gson = new Gson();
            Type type = new TypeToken<Homework>() {
            }.getType();
            Homework dataBean = gson.fromJson(String.valueOf(cmd), type);
            if (IsPull == 1) { //如果是哦下拉刷新则清理
                list.clear();
            }
            if (dataBean.getData().size() > 0) {

                list.addAll(dataBean.getData());
                listAdapter.notifyDataSetChanged();
            } else {
                listAdapter.setFoot(100);
                listAdapter.notifyDataSetChanged();
            }

        }
        refresh.setRefreshing(false);
        IsPull = 0;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onMessage(String str) {
        rx.Observable.just(str)
                .observeOn(AndroidSchedulers
                        .mainThread())
                .subscribe(observer);
        Log.e("通知页面", firstLoad + "=" + str);

    }
}
