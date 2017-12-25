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
import com.android.teacher.entity.StudentBean;
import com.android.teacher.newwork.MessageCenter;
import com.android.teacher.utils.JSONUtils;
import com.android.teacher.utils.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class AddParent extends Activity implements MessageCallBack {

    @Bind(R.id.back_btn)
    ImageView backBtn;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.paremtName_et)
    EditText paremtNameEt;
    @Bind(R.id.relationship_et)
    EditText relationshipEt;
    @Bind(R.id.parentMobile_et)
    EditText parentMobileEt;
    @Bind(R.id.submit)
    Button submit;

    private int parentid;
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
            if (JSONUtils.getString(cmd, "cmd").equals("parent.addInfo")) {
                if (JSONUtils.getInt(cmd, "code", 0) == 1) {
                    finish();
                }
                Toast.FangXueToast(AddParent.this, JSONUtils.getString(cmd, "message"));
            }
            if (JSONUtils.getString(cmd, "cmd").equals("parent.updateInfo")) {
                if (JSONUtils.getInt(cmd, "code", 0) == 1) {
                    finish();
                }
                Toast.FangXueToast(AddParent.this, JSONUtils.getString(cmd, "message"));
            }
            if (JSONUtils.getString(cmd, "cmd").equals("parent.getinfo")) {
                if (JSONUtils.getInt(cmd, "code", 0) == 1) {
                    JSONObject j = JSONUtils.getSingleJSON(cmd,"data",0);
                    parentMobileEt.setText(JSONUtils.getString(j,"mobile"));
                    paremtNameEt.setText(JSONUtils.getString(j,"parentname"));
                    relationshipEt.setText(JSONUtils.getString(j,"relationship"));
                }

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_info_activity_add_parent);
        ButterKnife.bind(this);

        messageCenter = new MessageCenter();
        messageCenter.setCallBackInterFace(this);
        initView();
    }

    private void initView() {

        final int StudentId = getIntent().getIntExtra("studentid", 0);


        parentid = getIntent().getIntExtra("parentid", 0);

        if (parentid > 0) {
            toolbarTitle.setText("修改家长");
            messageCenter.SendYouMessage(messageCenter.ChooseCommand().parent_getInfo(parentid, StudentId),AddParent.this);

            submit.setText("修改");

        }else {
            toolbarTitle.setText("添加家长");
        }

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (parentid > 0) {
                    messageCenter.SendYouMessage(messageCenter.ChooseCommand().parent_updateInfo(parentid,
                            StudentId,
                            parentMobileEt.getText().toString(),
                            paremtNameEt.getText().toString(),
                            relationshipEt.getText().toString()));
                } else {
                    messageCenter.SendYouMessage(messageCenter.ChooseCommand().parent_addInfo(
                            parentMobileEt.getText().toString(),
                            StudentId,
                            paremtNameEt.getText().toString(),
                            relationshipEt.getText().toString()));

                }
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onMessage(String str) {
        Log.e("ADDparent", str);
        Observable.just(str)
                .observeOn(AndroidSchedulers
                        .mainThread())
                .subscribe(observer);
    }
}
