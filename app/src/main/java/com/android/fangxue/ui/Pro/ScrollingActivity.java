package com.android.fangxue.ui.Pro;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.android.fangxue.base.BaseActivity;
import com.android.fangxue.callback.MessageCallBack;
import com.android.fangxue.entity.ProjectInfo;
import com.android.fangxue.newwork.MessageCenter;
import com.android.fangxue.R;
import com.android.fangxue.adapter.MyAdapter.ProjectInfoAdapter;
import com.android.fangxue.utils.JSONUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;

public class ScrollingActivity extends BaseActivity implements MessageCallBack {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @Bind(R.id.appBar)
    AppBarLayout appBar;
    @Bind(R.id.content)
    RecyclerView content;
    @Bind(R.id.refresh)
    SwipeRefreshLayout refresh;
    private String proId = "0";
    private MessageCenter messageCenter;
    private Observer<String> observer;
    private List<ProjectInfo.DataBean> mlist = new ArrayList<>();
    private ProjectInfoAdapter projectAdapter;



    @Override
    public void setButterKnife() {
        ButterKnife.bind(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.ui_pro_list;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DealMessageWithMe();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        String proName = getIntent().getStringExtra("proName");
        proId = getIntent().getStringExtra("proId");
        setTitle(proName);
        initRecyclerView();
    }
    @Override
    protected void onResume() {
        super.onResume();
        messageCenter = new MessageCenter(this);
        messageCenter.setCallBackInterFace(this);
        refresh.setRefreshing(true);
        messageCenter.SendYouMessage(messageCenter.ChooseCommand().project(String.valueOf(proId)));
        Log.e("e", messageCenter.ChooseCommand().project(String.valueOf(proId)));
    }
    private void setRefresh() {
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                messageCenter.SendYouMessage(messageCenter.ChooseCommand().project(String.valueOf(proId)));

            }
        });
    }

    private void initRecyclerView() {
        projectAdapter = new ProjectInfoAdapter(this, mlist);
        content.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        content.setAdapter(projectAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        /**
         *
         * 菜单set事件
         * */
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        /**
         * 返回键事件
         * */
        if (id == android.R.id.home) {
            this.finish();
            return false;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void DealMessageForMe(String s, Observer observer) {
        super.DealMessageForMe(s, observer);
    }

    private void DealMessageWithMe() {
        observer = new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                refresh.setRefreshing(false);
                JSONObject cmd = JSONUtils.StringToJSON(s);
                if (JSONUtils.getString(cmd, "cmd").equals("project")) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<ProjectInfo>() {
                    }.getType();
                    ProjectInfo projectInfo = gson.fromJson(String.valueOf(cmd), type);
                    mlist.clear();
                    mlist.addAll(projectInfo.getData());
                    projectAdapter.notifyDataSetChanged();
                }
            }
        };
    }

    @Override
    public void onMessage(String str) {
        Log.e(str, "");
        DealMessageForMe(str, observer);
    }
}
