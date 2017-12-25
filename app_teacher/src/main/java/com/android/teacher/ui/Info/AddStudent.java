package com.android.teacher.ui.Info;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.teacher.R;
import com.android.teacher.callback.MessageCallBack;
import com.android.teacher.newwork.MessageCenter;
import com.android.teacher.utils.JSONUtils;
import com.android.teacher.utils.SharedPrefsUtil;
import com.android.teacher.utils.Toast;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class AddStudent extends Activity implements MessageCallBack {

    @Bind(R.id.back_btn)
    ImageView backBtn;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.studentName_et)
    EditText studentNameEt;
    @Bind(R.id.studentno_et)
    EditText studentnoEt;
    @Bind(R.id.submit)
    Button submit;
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
            Log.e("AddStudent", s);
            JSONObject cmd = JSONUtils.StringToJSON(s);

            if (JSONUtils.getString(cmd, "cmd").equals("student.updateInfo")) {
                if (JSONUtils.getInt(cmd, "code", 0) == 1) {
                    finish();
                    Toast.FangXueToast(AddStudent.this, JSONUtils.getString(cmd, "message"));
                } else {
                    Toast.FangXueToast(AddStudent.this, JSONUtils.getString(cmd, "message"));
                }
            }
            if (JSONUtils.getString(cmd, "cmd").equals("student.getinfo")) {
                if (JSONUtils.getInt(cmd, "code", 0) == 1) {
                    JSONObject object = JSONUtils.getSingleJSON(cmd, "data", 0);
                    studentNameEt.setText(JSONUtils.getString(object, "studentname"));
                    studentnoEt.setText(JSONUtils.getString(object, "studentno"));
                } else {
                    Toast.FangXueToast(AddStudent.this, JSONUtils.getString(cmd, "message"));
                }
            }
            if (JSONUtils.getString(cmd, "cmd").equals("student.addInfo")) {
                if (JSONUtils.getInt(cmd, "code", 0) == 1) {
                    finish();
                    Toast.FangXueToast(AddStudent.this, JSONUtils.getString(cmd, "message"));
                } else {
                    Toast.FangXueToast(AddStudent.this, JSONUtils.getString(cmd, "message"));
                }
            }


        }
    };
    private int studentid = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_info_activity_add_student);
        ButterKnife.bind(this);
        messageCenter = new MessageCenter();
        messageCenter.setCallBackInterFace(this);
        initView();
    }

    private void initView() {

        studentid = getIntent().getIntExtra("studentid", 0);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (studentid > 0) {
            toolbarTitle.setText("修改学生信息");
            messageCenter.SendYouMessage(messageCenter.ChooseCommand().student_getinfo(studentid));
            submit.setText("修改");
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    messageCenter.SendYouMessage(messageCenter.ChooseCommand().student_updateInfo(
                            studentid,
                            studentnoEt.getText().toString(),
                            studentNameEt.getText().toString()));
                }
            });
        } else {
            toolbarTitle.setText("新增学生");
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    messageCenter.SendYouMessage(messageCenter.ChooseCommand().student_addInfo(
                            studentnoEt.getText().toString(),
                            studentNameEt.getText().toString()));
                }
            });
        }
    }

    @Override
    public void onMessage(String str) {

        Observable.just(str)
                .observeOn(AndroidSchedulers
                        .mainThread())
                .subscribe(observer);

    }
}
