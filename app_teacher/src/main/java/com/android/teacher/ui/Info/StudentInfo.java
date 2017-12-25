package com.android.teacher.ui.Info;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.teacher.R;
import com.android.teacher.adapter.Holder.StudentHolder;
import com.android.teacher.adapter.Holder.StudentInfoHolder;
import com.android.teacher.adapter.commonAdapter.RecycCommomAdapter;
import com.android.teacher.base.BaseActivity;
import com.android.teacher.base.BaseHolder;
import com.android.teacher.callback.MessageCallBack;
import com.android.teacher.callback.mClickInterface;
import com.android.teacher.entity.MessageDetailBean;
import com.android.teacher.newwork.MessageCenter;
import com.android.teacher.utils.JSONUtils;
import com.android.teacher.utils.SharedPrefsUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import rx.schedulers.NewThreadScheduler;

public class StudentInfo extends BaseActivity implements MessageCallBack {


    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.rightBtn)
    TextView rightBtn;
    @Bind(R.id.back_btn)
    ImageView backBtn;
    private int id = 0;
    private Observer<String> observe = new Observer<String>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(String s) {
            DealWithData(s);
        }
    };
    @Bind(R.id.Rectc_info)
    RecyclerView listView;
    private MessageCenter messageCenter;
    private StudentsInfoAdapter adapter;
    private List<com.android.teacher.entity.StudentInfo.DataBean> list = new ArrayList<>();

    @Override
    public void setButterKnife() {
        ButterKnife.bind(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.ui_info_student_info;
    }

    @Override
    protected void onResume() {
        super.onResume();
        messageCenter.setCallBackInterFace(this);
        messageCenter.SendYouMessage(messageCenter.ChooseCommand().student_getparentinfo(id));
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        id = getIntent().getIntExtra("studentid", 0);
        toolbarTitle.setText("信息");
        if (SharedPrefsUtil.getValue(StudentInfo.this, "teacherXML", "roly", "0").equals("1")) {
            rightBtn.setText("新增家长");
            rightBtn.setVisibility(View.VISIBLE);
            rightBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(StudentInfo.this, AddParent.class);
                    intent.putExtra("studentid", id);
                    startActivity(intent);
                }
            });
        }


        messageCenter = new MessageCenter();

        adapter = new StudentsInfoAdapter(this, list, id);
        adapter.setInterFace(new mClickInterface() {
            @Override
            public void doClick() {

                int parentId = list.get(adapter.getPos()).getParentid();
                if (parentId > 0) {
                    messageCenter.SendYouMessage(messageCenter.ChooseCommand().parent_deleteInfo(id, parentId));
                    list.remove(adapter.getPos());
                    adapter.notifyItemRemoved(adapter.getPos());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void doClick(int pos, View vi) {

            }
        });
        listView.setAdapter(adapter);
        listView.setLayoutManager(new LinearLayoutManager(this));


    }

    private void DealWithData(String s) {
        JSONObject cmd = JSONUtils.StringToJSON(s);
        if (JSONUtils.getString(cmd, "cmd").equals("student.getParentList")) {
            list.clear();
            Gson gson = new Gson();
            Type type = new TypeToken<com.android.teacher.entity.StudentInfo>() {
            }.getType();
            com.android.teacher.entity.StudentInfo dataBean = gson.fromJson(String.valueOf(cmd), type);
            list.addAll(dataBean.getData());
            adapter.notifyDataSetChanged();
        }
    }


    @OnClick({R.id.back_btn})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
                ThisFinis();
                break;
        }
    }

    @Override
    public void onMessage(String str) {

        DealMessageForMe(str, observe);
    }

    public class StudentsInfoAdapter extends RecycCommomAdapter {
        private final Context mContext;
        private mClickInterface clickI = null;
        private View view;
        private StudentInfoHolder studentInfo;
        private int studentId = 0;

        StudentsInfoAdapter(Context context, List<?> dataList, int stid) {
            super(context, dataList);
            this.mContext = context;

            this.studentId = stid;
        }


        void setInterFace(mClickInterface mClickInterface) {
            clickI = mClickInterface;
        }


        @Override
        public BaseHolder setViewHolder(ViewGroup parent) {

            view = LayoutInflater.from(context).inflate(R.layout.ui_studentinfo_item, parent, false);
            studentInfo = new StudentInfoHolder(view, studentId);
            studentInfo.setContext(this.context);
            studentInfo.setInterFace(clickI);
            return studentInfo;

        }

        public int getPos() {

            return studentInfo.getMyPos();
        }

    }
}
