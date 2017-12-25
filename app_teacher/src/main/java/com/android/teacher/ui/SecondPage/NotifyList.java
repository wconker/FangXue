package com.android.teacher.ui.SecondPage;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.teacher.R;
import com.android.teacher.adapter.IndexViewAdapter.ListAdapter;
import com.android.teacher.base.BaseActivity;
import com.android.teacher.callback.MessageCallBack;
import com.android.teacher.callback.mClickInterface;
import com.android.teacher.entity.MessageDetailBean;
import com.android.teacher.newwork.MessageCenter;
import com.android.teacher.utils.JSONUtils;
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

import static android.R.id.list;

public class NotifyList extends BaseActivity implements MessageCallBack, mClickInterface {
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
    @Bind(R.id.search_img)
    ImageView searchImg;
    private List<MessageDetailBean.DataBean> list = new ArrayList<>();
    private ListAdapter listAdapter;
    private Observer<String> observer;
    private MessageCenter messageCenter;
    private int RequestId = 0;

    @Override
    public void setButterKnife() {

        ButterKnife.bind(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        messageCenter = new MessageCenter();
        messageCenter.setCallBackInterFace(this);
        SwitchSendMessage();
    }
    @Override
    public int getLayoutId() {
        return R.layout.ui_secondpage_notify_list;
    }


    @OnClick({R.id.back_btn})
    void Click(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
                finish();
                break;

        }
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {


        //初始化控件
        searchImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        RequestId = getIntent().getIntExtra("ConkerData", 0);

        searchEdit.setHint("请输入检索关键字");


        searchImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SwitchSendMessage();
            }
        });
        listAdapter = new ListAdapter(NotifyList.this, list);
        listAdapter.setMyClickListener(this);
        homeworkList.setAdapter(listAdapter);
        homeworkList.setLayoutManager(new LinearLayoutManager(NotifyList.this));
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

                JSONObject cmd = JSONUtils.StringToJSON(s);
                if (JSONUtils.getString(cmd, "cmd").equals("message.gethomeworklist")) {
                    list.clear();
                    Gson gson = new Gson();
                    Type type = new TypeToken<MessageDetailBean>() {
                    }.getType();
                    MessageDetailBean dataBean = gson.fromJson(String.valueOf(cmd), type);
                    list.addAll(dataBean.getData());
                    refresh.setRefreshing(false);
                    listAdapter.notifyDataSetChanged();
                }
                if (JSONUtils.getString(cmd, "cmd").equals("message.getnotifylist")) {
                    list.clear();
                    Gson gson = new Gson();
                    Type type = new TypeToken<MessageDetailBean>() {
                    }.getType();
                    MessageDetailBean dataBean = gson.fromJson(String.valueOf(cmd), type);
                    list.addAll(dataBean.getData());
                    refresh.setRefreshing(false);
                    listAdapter.notifyDataSetChanged();
                }
                  if (JSONUtils.getString(cmd, "cmd").equals("message.deleteinfo")) {

                }


            }
        };

    }

    void SwitchSendMessage() {

        if (RequestId == 1) {//通知
            messageCenter.SendYouMessage(messageCenter.ChooseCommand().getmessagelist_HomeWork());

        } else if (RequestId == 2) {//作业
            messageCenter.SendYouMessage(messageCenter.ChooseCommand().getlist_HomeWork());
        }


    }

    @Override
    public void onMessage(String str) {
        Observable.just(str)
                .observeOn(AndroidSchedulers
                        .mainThread())
                .subscribe(observer);
        Log.e("HomeWork", str);
    }

    @Override
    public void doClick() {

    }

    @Override
    public void doClick(int pos, View vi) {
        switch (vi.getId()) {
            case R.id.btnDelete:


                messageCenter.SendYouMessage(messageCenter.ChooseCommand().message_deleteitem(list.get(pos).getId()));

                list.remove(pos);
                listAdapter.notifyItemRemoved(pos);
                listAdapter.notifyDataSetChanged();
                break;
        }

    }
}
