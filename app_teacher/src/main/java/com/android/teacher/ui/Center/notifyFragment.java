package com.android.teacher.ui.Center;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
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

import com.android.teacher.R;
import com.android.teacher.adapter.IndexViewAdapter.ListAdapter;
import com.android.teacher.callback.MessageCallBack;
import com.android.teacher.callback.mClickInterface;
import com.android.teacher.entity.MessageDetailBean;
import com.android.teacher.newwork.MessageCenter;
import com.android.teacher.ui.Schdule.SchduleList;
import com.android.teacher.ui.SecondPage.NotifyList;
import com.android.teacher.ui.SecondPage.SendHomeWork;
import com.android.teacher.utils.ACache;
import com.android.teacher.utils.Dialog;
import com.android.teacher.utils.JSONUtils;
import com.android.teacher.utils.Net;
import com.android.teacher.utils.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by softsea on 17/11/1.
 */

public class notifyFragment extends Fragment implements MessageCallBack, mClickInterface {

    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.homework_list)
    RecyclerView homeworkList;
    @Bind(R.id.refresh)
    SwipeRefreshLayout refresh;
    @Bind(R.id.back_btn)
    ImageView backBtn;
    @Bind(R.id.search_edit)
    EditText searchEdit;

    private int delId = 0;
    @Bind(R.id.search_img)
    ImageView searchImg;
    @Bind(R.id.floatButton)
    FloatingActionButton floatButton;
    public List<MessageDetailBean.DataBean> list = new ArrayList<>();
    private ListAdapter listAdapter;
    private Observer<String> observer = new Observer<String>() {
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
    private MessageCenter messageCenter;
    public int RequestId = 0;
    private ACache aCache;
    public int firstLoad = 0;
    private int delPos = 0;
    private int IsPull = 0;
    private int PULLPU = 1;
    private int PUUDWON = 2;

    public static notifyFragment newInstance() {
        return new notifyFragment();
    }

    public void setRequest(int requestId) {
        IsPull = PULLPU;
        this.RequestId = requestId;
        SwitchSendMessage();
        checkShowButton();
    }

    void SwitchSendMessage() {
        messageCenter.SendYouMessage(messageCenter.ChooseCommand().getlist_message(RequestId, "", "", 0), notifyFragment.this);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("notifyFragment", "");
        // messageCenter.setCallBackInterFace(this);

    }

    void checkShowButton() {

        if (RequestId > 0) {
            floatButton.setVisibility(View.VISIBLE);
        } else {
            floatButton.setVisibility(View.GONE);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.ui_secondpage_notify_list, container, false);
        ButterKnife.bind(this, view);
        messageCenter = new MessageCenter();
        //初始化缓存对象
        aCache = ACache.get(getActivity());
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                IsPull = PULLPU;
                SwitchSendMessage();
            }
        });
        listAdapter = new ListAdapter(getActivity(), list);
        listAdapter.setMyClickListener(this);
        homeworkList.setAdapter(listAdapter);
        homeworkList.setLayoutManager(new LinearLayoutManager(getActivity()));
        homeworkList.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                    //如果相等则说明已经滑动到最后了,
                    //如果一页不满的话就会发生下拉后调用这里的情况,因为检测到已经到底部
                    if (list.size() >= 6) {
                        if (lastPosition == recyclerView.getLayoutManager().getItemCount() - 1) {
                            messageCenter.SendYouMessage(messageCenter.ChooseCommand().getlist_message(RequestId,
                                    "", String.valueOf(list.get(list.size() - 1).getId()), list.size()), notifyFragment.this);
                        }
                        IsPull = PUUDWON;
                    }

                }
            }
        });
        // DealWithData(aCache.getAsString("notifyList"));
        searchImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IsPull = PULLPU;
                messageCenter.SendYouMessage(messageCenter.ChooseCommand().getlist_message(RequestId,
                        searchEdit.getText().toString(), "", 0), notifyFragment.this);


            }
        });

        return view;
    }

    private void DealWithData(String s) {

        JSONObject cmd = JSONUtils.StringToJSON(s);


        Log.e("信息", s);

        if (JSONUtils.getString(cmd, "cmd").equals("message.getlist")) {
            if (JSONUtils.getInt(cmd, "code", 0) == 1) {
                Gson gson = new Gson();
                Type type = new TypeToken<MessageDetailBean>() {
                }.getType();
                if (IsPull == PULLPU) { //如果是哦下拉刷新则清理数据项
                    list.clear();

                }
                MessageDetailBean dataBean = gson.fromJson(String.valueOf(cmd), type);
                if (dataBean.getData().size() > 0) {


                    list.addAll(dataBean.getData());
                    listAdapter.notifyDataSetChanged();
                } else {
                    listAdapter.setFoot(100);
                    listAdapter.notifyDataSetChanged();
                }

                refresh.setRefreshing(false);
                IsPull = 0;
            }
        }

        if (JSONUtils.getString(cmd, "cmd").equals("message.deleteinfo")) {
            if (JSONUtils.getInt(cmd, "code", 0) == 1) {
                list.remove(delPos);
                listAdapter.notifyDataSetChanged();
                Toast.FangXueToast(getActivity(), JSONUtils.getString(cmd, "message"));
            } else {
                Toast.FangXueToast(getActivity(), JSONUtils.getString(cmd, "message"));
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.floatButton})
    void CliskMothed(View v) {
        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(getActivity(), floatButton, "sharedView");
        switch (v.getId()) {
            case R.id.floatButton:
                Intent inte;
                inte = new Intent(getActivity(), SendHomeWork.class);
                inte.putExtra("ConkerData", RequestId);
                startActivityForResult(inte,100, activityOptions.toBundle());
                break;
        }
    }

    @Override
    public void onMessage(String str) {
        Observable.just(str)
                .observeOn(AndroidSchedulers
                        .mainThread())
                .subscribe(observer);
    }

    @Override
    public void doClick() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode==100)
        {

        }


    }

    //删除部分
    @Override
    public void doClick(final int pos, View vi) {
        switch (vi.getId()) {
            case R.id.btnDelete:
                Dialog.showDialog(getActivity(), "提示", "是否确定删除该信息？", new mClickInterface() {
                    @Override
                    public void doClick() {
                        delId = list.get(pos).getId();
                        delPos = pos;
                        messageCenter.SendYouMessage(messageCenter.ChooseCommand().message_deleteitem(delId),
                                notifyFragment.this);
                    }

                    @Override
                    public void doClick(int pos, View vi) {
                    }
                });

                break;
        }

    }


}
