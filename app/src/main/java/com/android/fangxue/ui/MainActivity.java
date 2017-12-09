package com.android.fangxue.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.fangxue.BuildConfig;
import com.android.fangxue.R;
import com.android.fangxue.adapter.MyAdapter.ProjectAdapter;
import com.android.fangxue.base.BaseActivity;
import com.android.fangxue.callback.MessageCallBack;
import com.android.fangxue.entity.ProjectBean;
import com.android.fangxue.newwork.MessageCenter;
import com.android.fangxue.ui.Contact.ContactList;
import com.android.fangxue.ui.Detail.HomeworkDetail;
import com.android.fangxue.utils.JSONUtils;
import com.android.fangxue.utils.SharedPrefsUtil;
import com.android.fangxue.utils.UpdateManager;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, MessageCallBack {
    private MessageCenter messageCenter;
    private TextView username, going;
    private Context mContext = this;
    private SharedPrefsUtil sharedPrefsUtil;
    private Observer<String> observer;
    private RecyclerView reList;
    private ImageView userhead;
    private List<ProjectBean.DataBean> mlist = new ArrayList<>();
    private ProjectAdapter projectAdapter;

    @Override
    public void setButterKnife() {

        ButterKnife.bind(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ControlCenter();
        reList = (RecyclerView) this.findViewById(R.id.reList);
        projectAdapter = new ProjectAdapter(this, mlist);
        reList.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        reList.setAdapter(projectAdapter);
        messageCenter = new MessageCenter(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        sharedPrefsUtil = new SharedPrefsUtil();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void ControlCenter() {
        observer = new Observer<String>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(String s) {
                JSONObject cmd = JSONUtils.StringToJSON(s);
                if (JSONUtils.getString(cmd, "cmd").equals("getMyInfo")) {
                    messageCenter.SendYouMessage(messageCenter.ChooseCommand().projectGetList());
                    JSONArray userDataList = JSONUtils.getJSONArray(cmd, "data");
                    for (int i = 0; i < userDataList.length(); i++) {
                        try {
                            JSONObject obj = userDataList.getJSONObject(i);
                            SharedPrefsUtil.putValue(mContext,
                                    "userXML",
                                    "UserImage",
                                    obj.getString("zp"));
                            SharedPrefsUtil.putValue(mContext,
                                    "userXML",
                                    "UserName",
                                    obj.getString("xm"));
                            SharedPrefsUtil.putValue(mContext,
                                    "userXML",
                                    "UserGoing",
                                    obj.getString("qx"));
                            username.setText(obj.getString("xm"));
                            going.setText(obj.getString("qx"));
                            Glide.with(mContext).load("http://www.fangxue.cn/test/pmer/help/photo/" + obj.getString("zp"))
                                    .placeholder(R.drawable.username)
                                    .into(userhead);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
                if (JSONUtils.getString(cmd, "cmd").equals("projectGetList")) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<ProjectBean>() {
                    }.getType();
                    ProjectBean ProjectBean = gson.fromJson(String.valueOf(cmd), type);
                    mlist.clear();
                    mlist.addAll(ProjectBean.getData());
                    projectAdapter.notifyDataSetChanged();
                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        messageCenter.setCallBackInterFace(this);
        messageCenter.SendYouMessage(messageCenter.ChooseCommand().getMyInfo());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.note_work) {
            startActivity(new Intent(MainActivity.this, HomeworkDetail.class));
        } else if (id == R.id.nav_gallery) {
            startActivity(new Intent(MainActivity.this, ContactList.class));
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.UpDate) {
            if (!BuildConfig.VERSION_NAME.equals(SharedPrefsUtil.getValue(this, "versionXML", "Version", "1.0"))) {
                new UpdateManager(this).checkUpdateInfo();
            } else {
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("提示信息").setMessage("当前没有更新").create().show();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMessage(String str) {


        Observable.just(str)
                .observeOn(AndroidSchedulers
                        .mainThread())
                .subscribe(observer);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
