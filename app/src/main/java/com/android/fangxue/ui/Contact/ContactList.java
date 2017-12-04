package com.android.fangxue.ui.Contact;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.print.PageRange;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.fangxue.R;
import com.android.fangxue.adapter.commonAdapter.CommonAdapter;
import com.android.fangxue.adapter.commonAdapter.CommonViewHolder;
import com.android.fangxue.base.BaseActivity;
import com.android.fangxue.callback.MessageCallBack;
import com.android.fangxue.entity.Children;
import com.android.fangxue.newwork.HttpCenter;
import com.android.fangxue.newwork.MessageCenter;
import com.android.fangxue.ui.Auth.Login;
import com.android.fangxue.ui.Center.ActivityCenter;
import com.android.fangxue.utils.JSONUtils;
import com.android.fangxue.utils.SharedPrefsUtil;
import com.android.fangxue.utils.Toast;
import com.bumptech.glide.Glide;
import com.foamtrace.photopicker.ImageCaptureManager;
import com.foamtrace.photopicker.ImageConfig;
import com.foamtrace.photopicker.PhotoPickerActivity;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;

public class ContactList extends BaseActivity implements MessageCallBack {


    @Bind(R.id.gridview_select_student)
    GridView gridviewSelectStudent;

    private MessageCenter messageCenter;
    private static final int READ_PHONE_STATE = 100;
    @Bind(R.id.back_btn)
    ImageView backBtn;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;

    private Observer<String> observer;
    private SelectStudentAdapter adapter;
    private List<Children.DataBean> list = new ArrayList<>();

    @Override
    public void setButterKnife() {


    }

    @Override
    public int getLayoutId() {
        return R.layout.ui_contact_contact_list;
    }


    @Override
    protected void DealMessageForMe(String s, Observer observer) {
        super.DealMessageForMe(s, observer);


    }

    @Override
    protected void onResume() {
        super.onResume();

        setOberver();
        //发送获取学生列表协议
        messageCenter = new MessageCenter();
        messageCenter.setCallBackInterFace(this);
        messageCenter.SendYouMessage(messageCenter.ChooseCommand().getstudentlist());
    }


    private void setOberver() {
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
                if (JSONUtils.getString(cmd, "cmd").equals("parent.getstudentlist")) {
                    list.clear();
                    Gson gson = new Gson();
                    Type type = new TypeToken<Children>() {
                    }.getType();
                    Children children = gson.fromJson(String.valueOf(s), type);
                    list.addAll(children.getData());
                    adapter.notifyDataSetChanged();
                }
                if (JSONUtils.getString(cmd, "cmd").equals("parent.selectstudent")) {
                    JSONObject studentObj = JSONUtils.getSingleJSON(cmd, "data", 0);
                    SharedPrefsUtil.putValue(ContactList.this, "userXML", "headerimg", JSONUtils.getString(studentObj, "headerimg")); //头像信息
                    SharedPrefsUtil.putValue(ContactList.this, "userXML", "studentName", JSONUtils.getString(studentObj, "studentname")); //用户信息
                    SharedPrefsUtil.putValue(ContactList.this, "userXML", "studentSchoolnm", JSONUtils.getString(studentObj, "schoolnm")); //用户信息
                    SharedPrefsUtil.putValue(ContactList.this, "userXML", "studentClassname", JSONUtils.getString(studentObj, "classname")); //用户信息
                    SharedPrefsUtil.putValue(ContactList.this, "userXML", "parentname", JSONUtils.getString(studentObj, "parentname")); //家长姓名
                    SharedPrefsUtil.putValue(ContactList.this, "userXML", "relationship", JSONUtils.getString(studentObj, "relationship")); //家长关系
                    SharedPrefsUtil.putValue(ContactList.this, "userXML", "mobile", JSONUtils.getString(studentObj, "mobile")); //家长电话
                    SharedPrefsUtil.putValue(ContactList.this, "userXML", "studentid", JSONUtils.getString(studentObj, "studentid")); //用户信息
                    finish();
                }
            }
        };
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        toolbarTitle.setText("选择孩子");
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        adapter = new SelectStudentAdapter(this, list, R.layout.selectchidren_item);
        gridviewSelectStudent.setAdapter(adapter);
        gridviewSelectStudent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

    }


    @Override
    public void onMessage(String str) {


        Log.e("切換孩子", str);
        DealMessageForMe(str, observer);
    }


    private class SelectStudentAdapter extends CommonAdapter<Children.DataBean> {


        private Context mContext;

        SelectStudentAdapter(Context context, List<Children.DataBean> list, int layoutId) {
            super(context, list, layoutId);
            this.mContext = context;
        }

        @Override
        public void setViewContent(final CommonViewHolder viewHolder, Children.DataBean dataBean) {


            ImageView imageView = viewHolder.getView(R.id.img1);

            if (dataBean.getHeaderimg() != null) {
                Glide.with(mContext).load(dataBean.getHeaderimg()).into(imageView);
            }

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    messageCenter.SendYouMessage(messageCenter.ChooseCommand().selectStudent(list.get(viewHolder.getPostion()).getStudentid()));
                }
            });

            viewHolder.setText(R.id.name, dataBean.getStudentname()).setText(R.id.classname, dataBean.getClassname());
        }
    }

}
