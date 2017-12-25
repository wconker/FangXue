package com.android.teacher.ui.Info;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.teacher.R;
import com.android.teacher.callback.MessageCallBack;
import com.android.teacher.entity.CodeDic;
import com.android.teacher.entity.MessageDetailBean;
import com.android.teacher.newwork.MessageCenter;
import com.android.teacher.ui.auth.BindRegisterInfo;
import com.android.teacher.ui.auth.GetPhoneForRegister;
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
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class AddTeacher extends Activity implements MessageCallBack {
    @Bind(R.id.back_btn)
    ImageView backBtn;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.teacherName_et)
    EditText teacherName_et;
    @Bind(R.id.lesson_et)
    EditText lesson_et;
    @Bind(R.id.teacherMobile_et)
    EditText teacherMobile_et;
    @Bind(R.id.QQ_et)
    EditText qq_et;
    @Bind(R.id.submit)
    Button submit;
    @Bind(R.id.km)
    Spinner km;
    private int teacherid;
    private List<String> list = new ArrayList<>();
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
            JSONObject cmd = JSONUtils.StringToJSON(s);
            if (JSONUtils.getString(cmd, "cmd").equals("teacher.addInfo")) {
                if (JSONUtils.getInt(cmd, "code", 0) == 1) {
                    finish();
                    Toast.FangXueToast(AddTeacher.this, JSONUtils.getString(cmd, "message"));
                } else {
                    Toast.FangXueToast(AddTeacher.this, JSONUtils.getString(cmd, "message"));
                }
            }

            if (JSONUtils.getString(cmd, "cmd").equals("teacher.getinfo")) {

                if (JSONUtils.getInt(cmd, "code", 0) == 1) {
                    JSONObject object = JSONUtils.getSingleJSON(cmd, "data", 0);
                    teacherName_et.setText(JSONUtils.getString(object, "teachername"));
                    qq_et.setText(JSONUtils.getString(object, "qq"));
                    for (int c = 0; c < list.size(); c++) {
                        if (list.get(c).equals(JSONUtils.getString(object, "lesson"))) {
                            km.setSelection(c);
                        }
                    }
                    teacherMobile_et.setText(JSONUtils.getString(object, "mobile"));
                } else {
                    Toast.FangXueToast(AddTeacher.this, JSONUtils.getString(cmd, "message"));
                }

            }
            if (JSONUtils.getString(cmd, "cmd").equals("teacher.updateInfo")) {
                if (JSONUtils.getInt(cmd, "code", 0) == 1) {

                    //如果修改的信息是自己
                    if (String.valueOf(teacherid).equals(SharedPrefsUtil.getValue(AddTeacher.this, "teacherXML", "teacherid", ""))) {
                        SharedPrefsUtil.putValue(AddTeacher.this, "teacherXML", "lesson", km.getSelectedItem().toString()); //课程
                        SharedPrefsUtil.putValue(AddTeacher.this, "teacherXML", "teachername", teacherName_et.getText().toString()); //课程

                    }
                    finish();

                }
                Toast.FangXueToast(AddTeacher.this, JSONUtils.getString(cmd, "message"));
            }
            if (JSONUtils.getString(cmd, "cmd").equals("code.getcodelist")) {
                if (JSONUtils.getInt(cmd, "code", 0) == 1) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<CodeDic>() {
                    }.getType();
                    CodeDic codeDic = gson.fromJson(String.valueOf(cmd), type);
                    list.clear();
                    for (int i = 0; i < codeDic.getData().size(); i++)
                        list.add(codeDic.getData().get(i).getMc());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.FangXueToast(AddTeacher.this, JSONUtils.getString(cmd, "message"));
                }
                if (teacherid > 0)
                    messageCenter.SendYouMessage(messageCenter.ChooseCommand().teacher_getinfo(teacherid), AddTeacher.this);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_center_activity_add_teacher);
        ButterKnife.bind(this);
        messageCenter = new MessageCenter();
        messageCenter.SendYouMessage(messageCenter.ChooseCommand().code_getcodelist("任课"), AddTeacher.this);
        teacherid = getIntent().getIntExtra("teacherid", 0);
        toolbarTitle.setText("新增老师");
        if (teacherid > 0) {
            toolbarTitle.setText("修改老师信息");
            submit.setText("修改");
        } else {

            toolbarTitle.setText("新增老师");
            submit.setText("确定");
        }

        initView();
    }

    ArrayAdapter adapter;

    private void initView() {
        adapter = new ArrayAdapter(AddTeacher.this, android.R.layout.simple_list_item_1, list);
        km.setAdapter(adapter);

//        km.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                lesson_et.setText(list.get(i));
////                km.setSelection(0);
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (teacherid > 0) {
                    messageCenter.SendYouMessage(messageCenter.ChooseCommand().teacher_updateInfo(teacherid,
                            teacherName_et.getText().toString(),
                            teacherMobile_et.getText().toString(),
                            km.getSelectedItem().toString(), qq_et.getText().toString()), AddTeacher.this);
                } else {
                    messageCenter.SendYouMessage(messageCenter.ChooseCommand().teacher_addInfo(
                            "",
                            teacherName_et.getText().toString(),
                            teacherMobile_et.getText().toString(),
                            km.getSelectedItem().toString(), qq_et.getText().toString()), AddTeacher.this);
                }
            }
        });
    }

    @Override
    public void onMessage(String str) {

        Log.e("addTeacher", str);
        Observable.just(str)
                .observeOn(AndroidSchedulers
                        .mainThread())
                .subscribe(observer);

    }
}
