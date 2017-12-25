package com.android.teacher.ui.Center;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import com.android.teacher.adapter.commonAdapter.RecycCommomAdapter;
import com.android.teacher.base.BaseHolder;
import com.android.teacher.callback.MessageCallBack;
import com.android.teacher.callback.mClickInterface;
import com.android.teacher.entity.StudentBean;
import com.android.teacher.newwork.MessageCenter;
import com.android.teacher.ui.Info.AddStudent;
import com.android.teacher.ui.Info.StudentInfo;
import com.android.teacher.ui.auth.GetPhoneForRegister;
import com.android.teacher.utils.Dialog;
import com.android.teacher.utils.JSONUtils;
import com.android.teacher.utils.SharedPrefsUtil;
import com.android.teacher.utils.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.FileNotFoundException;
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
public class student extends Fragment implements MessageCallBack {
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
    private ActivityCenter center;
    StudentBean.DataBean fistDataBean = new StudentBean.DataBean();
    String ClassNameForCache = "";


    public static student newInstance() {
        return new student();
    }

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

            if (JSONUtils.getString(cmd, "cmd").equals("class.getstudentlist")) {
                if (JSONUtils.getInt(cmd, "code", 0) == 1) {
                    center.aCache.put(ClassNameForCache, s);
                    list.clear();
                    Type type = new TypeToken<StudentBean>() {
                    }.getType();
                    StudentBean dataBean = gson.fromJson(s, type);
                    if (dataBean.getData().size() > 0) {
                        list.addAll(dataBean.getData());
                    }
                    if (SharedPrefsUtil.getValue(getActivity(), "teacherXML", "roly", "0").equals("1")) {
                        list.add(0, fistDataBean);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.FangXueToast(getActivity(), JSONUtils.getString(cmd, "message"));
                }
            }
            if (JSONUtils.getString(cmd, "cmd").equals("student.deleteInfo")) {
                if (JSONUtils.getInt(cmd, "code", 0) == 1) {
                    messageCenter.SendYouMessage(messageCenter.ChooseCommand().getsutdentlist(), student.this);
                    Toast.FangXueToast(getActivity(), JSONUtils.getString(cmd, "message"));
                } else {
                    Toast.FangXueToast(getActivity(), JSONUtils.getString(cmd, "message"));
                }
            }

        }
    };
    private recycAdapter adapter;
    private List<StudentBean.DataBean> list = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    public void onResume() {
        super.onResume();
        if (((ActivityCenter) getActivity()).CurrentPos == 2)
            FistLoadMothed();
    }

    private int FistLoad = 0;

    void FistLoadMothed() {
        messageCenter.setCallBackInterFace(this);
        Log.e("student", FistLoad + "");
        if (FistLoad == 0) {
            doRefrsh();
            FistLoad = 1;
        } else {
            refreshLayout.setRefreshing(true);
            messageCenter.SendYouMessage(messageCenter.ChooseCommand().getsutdentlist(), student.this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.ui_center_sutdent_list, container, false);
        ButterKnife.bind(this, v);
        toolbarTitle.setText("班级通讯录");
        backBtn.setVisibility(View.GONE);
        center = (ActivityCenter) getActivity();

        rightBtn.setVisibility(View.VISIBLE);
        rightBtn.setText("班级老师");
        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Teachers.class));
            }
        });
        messageCenter = new MessageCenter();

        recyclerView = v.findViewById(R.id.studentList);
        adapter = new recycAdapter(getActivity(), list);
        recyclerView.setAdapter(adapter);
        GridLayoutManager gm = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(gm);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                messageCenter.SendYouMessage(messageCenter.ChooseCommand().getsutdentlist(), student.this);



            }
        });
        return v;
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
        ClassNameForCache = "StudentList" + SharedPrefsUtil.getValue(getActivity(), "teacherXML", "classid", "");
        String value = center.aCache.getAsString(ClassNameForCache);
        Log.e("学生信息缓存", value + "===" + ClassNameForCache);
        if (value != null && !value.isEmpty()) {
            observer.onNext(value);
        } else {
            messageCenter.SendYouMessage(messageCenter.ChooseCommand().getsutdentlist(), this);

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
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    class recycAdapter extends RecycCommomAdapter {
        private MessageCenter mc;
        private Context mContext;

        recycAdapter(Context context, List<?> dataList) {
            super(context, dataList);
            mContext = context;
        }

        public void setMc(MessageCenter messageCenter) {
            this.mc = messageCenter;
        }

        @Override
        public BaseHolder setViewHolder(final ViewGroup parent) {

            View view = LayoutInflater.from(getActivity()).inflate(R.layout.ui_student_item, parent, false);
            final StudentHolder v = new StudentHolder(view);
            v.setContext(context);
            v.setListener(new mClickInterface() {
                @Override
                public void doClick() {
                    Dialog.showDialog(mContext, "提示", "是否确认删除？", new mClickInterface() {
                        @Override
                        public void doClick() {
                            messageCenter.SendYouMessage(messageCenter.ChooseCommand().student_deleteInfo(list.get(v.pos).getStudentid()), student.this);
                        }

                        @Override
                        public void doClick(int pos, View vi) {
                        }
                    });
                }

                @Override
                public void doClick(int pos, View vi) {

                    if (vi.getId() == R.id.edit) {
                        if (pos > 0) {
                            Intent c = new Intent(getActivity(), AddStudent.class);
                            c.putExtra("studentid", pos);
                            startActivity(c);
                        }

                    } else {
                        //把列表中的第一个元素定义为添加按钮，再有权限的情况下显示
                        if (pos == 0 && SharedPrefsUtil.getValue(mContext, "teacherXML", "roly", "0").equals("1")) {
                            startActivity(new Intent(getActivity(), AddStudent.class));
                        } else {
                            Intent Go = new Intent(getActivity(), StudentInfo.class);
                            Go.putExtra("studentid", list.get(pos).getStudentid());
                            getActivity().startActivity(Go);
                        }
                    }


                }
            });
            return v;
        }
    }
}
