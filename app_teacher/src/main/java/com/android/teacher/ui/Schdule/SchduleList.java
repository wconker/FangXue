package com.android.teacher.ui.Schdule;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.teacher.R;
import com.android.teacher.adapter.IndexViewAdapter.SchedulAdpater;
import com.android.teacher.base.BaseActivity;
import com.android.teacher.callback.MessageCallBack;
import com.android.teacher.callback.mClickInterface;
import com.android.teacher.entity.MessageDetailBean;
import com.android.teacher.entity.ScheduleBean;
import com.android.teacher.newwork.MessageCenter;
import com.android.teacher.ui.Info.AddParent;
import com.android.teacher.ui.Info.AddSchedul;
import com.android.teacher.ui.auth.GetPhoneForRegister;
import com.android.teacher.utils.ACache;
import com.android.teacher.utils.Dialog;
import com.android.teacher.utils.JSONUtils;
import com.android.teacher.utils.SharedPrefsUtil;
import com.android.teacher.utils.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;

public class SchduleList extends BaseActivity implements MessageCallBack {

    @Bind(R.id.back_btn)
    ImageView backBtn;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.recycler_view)
    RecyclerView recycler_view;
    @Bind(R.id.rightBtn)
    TextView rightbtn;
    @Bind(R.id.reFresh)
    SwipeRefreshLayout reFresh;
    private List<ScheduleBean.DataBean> list = new ArrayList<>();
    private SchedulAdpater adpater;
    private MessageCenter messageCenter;
    ACache aCache;
    int teacherId = 0;
    private int FULLDATA = 100;
    private Observer<String> observer = new Observer<String>() {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(String s) {

            reFresh.setRefreshing(false);
            DealWithData(s);

        }

    };

    @Override
    protected void onResume() {
        super.onResume();
        messageCenter.setCallBackInterFace(this);
        FistLoadMothed();
    }

    private int FistLoad = 0;

    void FistLoadMothed() {
        if (FistLoad == 0) {
            LoadCache();
            FistLoad = 1;
        } else {
            reFresh.setRefreshing(true);
            messageCenter.SendYouMessage(messageCenter.ChooseCommand().class_getSchduleList(""),SchduleList.this);
        }
    }

    //新增menu模块
    private void showPopupMenu(View view) {

        PopupMenu popupMenu = new PopupMenu(this, view); // View当前PopupMenu显示的相对View的位置

        popupMenu.getMenuInflater().inflate(R.menu.schedule, popupMenu.getMenu());   // menu布局

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() { // menu的item点击事件
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                int vid = item.getItemId();
                switch (vid) {

                    case R.id.add:
                        Intent ii = new Intent(SchduleList.this, AddSchedul.class);
                        ii.putExtra("teacherid", teacherId);
                        startActivity(ii);
                        break;
                    case R.id.fromClass:
                        Intent classForschedul = new Intent(SchduleList.this, SchedulOfClass.class);
                        if (list.size() > 0) {
                            classForschedul.putExtra("data", FULLDATA);
                        }
                        startActivity(classForschedul);
                        break;
                    case R.id.fromDay:
                        Intent fromDay = new Intent(SchduleList.this, SchedulFromDay.class);
                        startActivity(fromDay);
                        break;


                }


                return false;
            }
        });
        // PopupMenu关闭事件
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {

            }
        });
        popupMenu.show();
    }


    private void DealWithData(String s) {

        reFresh.setRefreshing(false);
        JSONObject cmd = JSONUtils.StringToJSON(s);
        if (JSONUtils.getInt(cmd, "code", 0) == 1) {
            if (JSONUtils.getString(cmd, "cmd").equals("class.getSchedulelist")) {

                aCache.put("schedullist", s);
                list.clear();
                Gson gson = new Gson();
                Type type = new TypeToken<ScheduleBean>() {
                }.getType();
                ScheduleBean dataBean = gson.fromJson(String.valueOf(cmd), type);
                list.addAll(dataBean.getData());
                adpater.notifyDataSetChanged();

                //Toast.FangXueToast(SchduleList.this, JSONUtils.getString(cmd, "message"));
            }
            if (JSONUtils.getString(cmd, "cmd").equals("schedule.deleteInfo")) {
                messageCenter.SendYouMessage(messageCenter.ChooseCommand().class_getSchduleList(""),SchduleList.this);
                Toast.FangXueToast(SchduleList.this, JSONUtils.getString(cmd, "message"));
            }
        }
    }

    @Override
    public void setButterKnife() {

        ButterKnife.bind(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.ui_schdule_activity_schdule_list;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        aCache = ACache.get(this);
        toolbarTitle.setText("课程表");
        rightbtn.setText("新增");
        rightbtn.setVisibility(View.VISIBLE);
        openOptionsMenu();
        messageCenter = new MessageCenter();
        messageCenter.setCallBackInterFace(this);
        teacherId = Integer.valueOf(SharedPrefsUtil.getValue(SchduleList.this, "teacherXML", "teacherid", "0"));
        adpater = new SchedulAdpater(this, list, new mClickInterface() {
            @Override
            public void doClick() {

            }

            @Override
            public void doClick(final int pos, View vi) {
                if (vi.getId() == R.id.del) {
                    Dialog.showDialog(SchduleList.this, "提示", "是否确认删除？", new mClickInterface() {
                        @Override
                        public void doClick() {
                            messageCenter.SendYouMessage(messageCenter.ChooseCommand().schedule_deleteInfo(pos),SchduleList.this);
                        }
                        @Override
                        public void doClick(int pos, View vi) {
                        }
                    });

                } else {
                    Intent i = new Intent(SchduleList.this, AddSchedul.class);
                    i.putExtra("id", pos);
                    i.putExtra("teacherid", teacherId);
                    startActivity(i);
                }

            }
        });
        recycler_view.setAdapter(adpater);
        reFresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                messageCenter.SendYouMessage(messageCenter.ChooseCommand().class_getSchduleList(""),SchduleList.this);
            }
        });
        rightbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(rightbtn);

            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recycler_view.setLayoutManager(gridLayoutManager);
        btnSet();

    }

    void LoadCache() {
        messageCenter.SendYouMessage(messageCenter.ChooseCommand().class_getSchduleList(""),SchduleList.this);
//        if (aCache.getAsString("schedullist") != null && !aCache.getAsString("schedullist").isEmpty()) {
//            DealWithData(aCache.getAsString("schedullist"));
//        } else {
//            messageCenter.SendYouMessage(messageCenter.ChooseCommand().class_getSchduleList(""),SchduleList.this);
//        }
    }

    private void btnSet() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public void onMessage(String str) {

        DealMessageForMe(str, observer);
    }
}
