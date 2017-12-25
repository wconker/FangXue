package com.android.teacher.ui.Center;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.teacher.R;
import com.android.teacher.adapter.Holder.StudentHolder;
import com.android.teacher.adapter.Holder.TeacherHolder;
import com.android.teacher.adapter.commonAdapter.RecycCommomAdapter;
import com.android.teacher.base.BaseHolder;
import com.android.teacher.callback.MessageCallBack;
import com.android.teacher.callback.mClickInterface;
import com.android.teacher.entity.StudentBean;
import com.android.teacher.entity.TeacherBean;
import com.android.teacher.newwork.MessageCenter;
import com.android.teacher.ui.Info.AddStudent;
import com.android.teacher.ui.Info.AddTeacher;
import com.android.teacher.ui.Info.StudentInfo;
import com.android.teacher.utils.ACache;
import com.android.teacher.utils.Dialog;
import com.android.teacher.utils.JSONUtils;
import com.android.teacher.utils.SharedPrefsUtil;
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
 * A simple {@link Fragment} subclass.
 */
public class Teachers extends Activity implements MessageCallBack {
    @Bind(R.id.back_btn)
    ImageView backBtn;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.byId)
    TextView byId;
    @Bind(R.id.rightBtn)
    TextView rightBtn;
    @Bind(R.id.byArray)
    TextView byArray;
    @Bind(R.id.byName)
    TextView byName;
    @Bind(R.id.bg1)
    RelativeLayout bg1;
    @Bind(R.id.bg2)
    RelativeLayout bg2;
    @Bind(R.id.bg3)
    RelativeLayout bg3;
    @Bind(R.id.refresh)
    SwipeRefreshLayout refreshLayout;
    private ACache center;
    private MessageCenter messageCenter;
    private Observer<String> observer = new Observer<String>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(String s) {

            refreshLayout.setRefreshing(false);
            Gson gson = new Gson();
            JSONObject cmd = JSONUtils.StringToJSON(s);

            if (JSONUtils.getString(cmd, "cmd").equals("class.getteacherlist")) {
                if (JSONUtils.getInt(cmd, "code", 0) == 1) {
                    center.put(TeacherNameForCache, s);
                    list.clear();
                    Type type = new TypeToken<TeacherBean>() {
                    }.getType();
                    TeacherBean dataBean = gson.fromJson(String.valueOf(cmd), type);
                    list.addAll(dataBean.getData());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.FangXueToast(Teachers.this, JSONUtils.getString(cmd, "message"));
                }
            }
            if (JSONUtils.getString(cmd, "cmd").equals("teacher.deleteInfo")) {
                if (JSONUtils.getInt(cmd, "code", 0) == 1) {
                    messageCenter.SendYouMessage(messageCenter.ChooseCommand().class_getteacherlist());
                    Toast.FangXueToast(Teachers.this, JSONUtils.getString(cmd, "message"));
                } else {
                    Toast.FangXueToast(Teachers.this, JSONUtils.getString(cmd, "message"));
                }
            }

        }
    };
    private TeacherrecycAdapter adapter;
    private List<TeacherBean.DataBean> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private String TeacherNameForCache;

    @Override
    public void onResume() {
        super.onResume();
        SendMessageToServer();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_center_sutdent_list);
        ButterKnife.bind(this);
        toolbarTitle.setText("老师列表");
        //缓存实例化
        center = ACache.get(this);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (SharedPrefsUtil.getValue(Teachers.this, "teacherXML", "roly", "0").equals("1")) {
            rightBtn.setVisibility(View.VISIBLE);
            rightBtn.setText("新增老师");
        }
        messageCenter = new MessageCenter();
        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 添加学生逻辑
                startActivity(new Intent(Teachers.this, AddTeacher.class));
            }
        });

        recyclerView = findViewById(R.id.studentList);
        adapter = new TeacherrecycAdapter(Teachers.this, list);
        recyclerView.setAdapter(adapter);
        GridLayoutManager gm = new GridLayoutManager(Teachers.this, 3);
        recyclerView.setLayoutManager(gm);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SendMessageToServer();
            }
        });

        doRefrsh();
    }


    void SendMessageToServer() {
        messageCenter.setCallBackInterFace(Teachers.this);
        messageCenter.SendYouMessage(messageCenter.ChooseCommand().class_getteacherlist());
    }


    @OnClick({R.id.byArray, R.id.byId, R.id.byName})
    void ClickMothed(View v) {
        PressButton(v.getId());
    }

    @SuppressLint("ResourceAsColor")
    void PressButton(int id) {
        byArray.setTextColor(R.color.gray);
        byName.setTextColor(R.color.gray);
        byId.setTextColor(R.color.gray);
        byArray.setTextSize(14);
        byName.setTextSize(14);
        byId.setTextSize(14);

        switch (id) {
            case R.id.byArray:
                byArray.setTextColor(R.color.black_80);
                byArray.setTextSize(18);
                break;
            case R.id.byId:
                byId.setTextSize(18);
                byId.setTextColor(R.color.black_80);
                break;
            case R.id.byName:
                byName.setTextSize(18);
                byName.setTextColor(R.color.black_80);
                break;
        }
    }

    void doRefrsh() {
        TeacherNameForCache = "TeacherList" + SharedPrefsUtil.getValue(Teachers.this, "teacherXML", "classid", "");
        String value = center.getAsString(TeacherNameForCache);
        if (value != null) {
            observer.onNext(value);
        } else {
            messageCenter.setCallBackInterFace(this);
            messageCenter.SendYouMessage(messageCenter.ChooseCommand().class_getteacherlist());
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
    protected void onDestroy() {
        super.onDestroy();
    }

    class TeacherrecycAdapter extends RecycCommomAdapter {
        private MessageCenter mc;
        private Context mContext;

        TeacherrecycAdapter(Context context, List<?> dataList) {
            super(context, dataList);
            mContext = context;
        }

        public void setMc(MessageCenter messageCenter) {
            this.mc = messageCenter;
        }

        @Override
        public BaseHolder setViewHolder(final ViewGroup parent) {
            View view = LayoutInflater.from(Teachers.this).inflate(R.layout.ui_student_item, parent, false);
            final TeacherHolder v = new TeacherHolder(view);
            v.setContext(context);
            v.setListener(new mClickInterface() {
                @Override
                public void doClick() {
                    Dialog.showDialog(mContext, "提示", "是否确认删除？", new mClickInterface() {
                        @Override
                        public void doClick() {
                            messageCenter.SendYouMessage(messageCenter.ChooseCommand().teacher_deleteInfo(list.get(v.pos).getTeacherid()));
                        }

                        @Override
                        public void doClick(int pos, View vi) {
                        }
                    });
                }

                @Override
                public void doClick(final int pos, View vi) {

                    if (vi.getId() == R.id.edit) {
                        Intent Go = new Intent(Teachers.this, AddTeacher.class);
                        Go.putExtra("teacherid", list.get(pos).getTeacherid());
                        Teachers.this.startActivity(Go);
                    } else {
                        Dialog.showDialog(mContext, "提示", "是否拨打:" + list.get(pos).getMobile() + "？", new mClickInterface() {
                            @Override
                            public void doClick() {
                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + list.get(pos).getMobile()));
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }

                            @Override
                            public void doClick(int pos, View vi) {
                            }
                        });
                    }
                }
            });
            return v;
        }
    }
}
