package com.android.fangxue.ui.Detail;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.fangxue.R;
import com.android.fangxue.base.BaseActivity;
import com.android.fangxue.callback.MessageCallBack;
import com.android.fangxue.newwork.MessageCenter;
import com.android.fangxue.utils.SharedPrefsUtil;
import com.android.fangxue.utils.Toast;
import com.android.fangxue.widget.CircleImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class ChildInfo extends BaseActivity implements MessageCallBack {

    @Bind(R.id.back_btn)
    ImageView backBtn;
    @Bind(R.id.tx)
    CircleImageView tx;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.child_name)
    EditText childName;
    @Bind(R.id.child_school)
    EditText childSchool;
    @Bind(R.id.child_class)
    EditText childClass;
    @Bind(R.id.child_submit)
    Button childSubmit;
    private MessageCenter messageCenter;
    private Observer<String> observer;

    @Override
    public void setButterKnife() {
        ButterKnife.bind(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.ui_detail_child_info;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        childName.setText(SharedPrefsUtil.getValue(this, "userXML", "studentName", ""));
        childSchool.setText(SharedPrefsUtil.getValue(this, "userXML", "studentSchoolnm", ""));
        childClass.setText(SharedPrefsUtil.getValue(this, "userXML", "studentClassname", ""));
        messageCenter = new MessageCenter();
        messageCenter.setCallBackInterFace(this);
        observer =new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

            }
        };
    }

    @OnClick({R.id.child_submit, R.id.back_btn})
    public void onViewClicked(View v) {

        if (v.getId() == R.id.submit) {
            if (!childName.getText().toString().isEmpty() ) {


            } else {
                Toast.FangXueToast(this, "请检查是否有不完整信息！");
            }
        } else { //返回按钮
            this.finish();

        }
    }

    @Override
    public void onMessage(String str) {
        DealMessageForMe(str, observer);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
