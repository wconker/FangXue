package com.android.fangxue.ui.Detail;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.fangxue.R;
import com.android.fangxue.callback.MessageCallBack;
import com.android.fangxue.newwork.MessageCenter;
import com.android.fangxue.utils.JSONUtils;
import com.android.fangxue.utils.SharedPrefsUtil;
import com.android.fangxue.widget.CircleImageView;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class ReSetPwd extends Activity implements MessageCallBack, Observer<String> {


    @Bind(R.id.back_btn)
    ImageView backBtn;
    @Bind(R.id.tx)
    CircleImageView tx;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.rightBtn)
    TextView rightBtn;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @Bind(R.id.pwd)
    EditText pwd;
    @Bind(R.id.re_pwd)
    EditText rePwd;
    @Bind(R.id.et_code)
    EditText etCode;
    @Bind(R.id.yzCode)
    Button yzCode;
    @Bind(R.id.login_ll)
    LinearLayout loginLl;
    @Bind(R.id.update_pwd)
    Button updatePwd;
    private int time = 0;
    private CountDownTimer countDownTimer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long l) {
            yzCode.setEnabled(false);
            String InvelTime = 60 - time + "s";
            yzCode.setText(InvelTime);
            time++;

        }

        @Override
        public void onFinish() {
            yzCode.setEnabled(true);
            yzCode.setText("获取验证码");
            time = 1;
        }
    };

    private MessageCenter messageCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_activity_re_set_pwd);
        ButterKnife.bind(this);
        messageCenter = new MessageCenter();
        setOnButtonMothed();
        toolbarTitle.setText("密码修改");
    }

    @Override
    public void onMessage(String str) {
        Observable.just(str)
                .observeOn(AndroidSchedulers
                        .mainThread())
                .subscribe(ReSetPwd.this);
    }


    void setOnButtonMothed() {

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        yzCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Phone = SharedPrefsUtil.getValue(ReSetPwd.this, "loginXML", "UserName", "");
                if (Phone.isEmpty()) {
                    com.android.fangxue.utils.Toast.FangXueToast(ReSetPwd.this, "请检查手机号码！");
                } else {
                    countDownTimer.start();
                    sendCode(Phone);
                }
            }
        });


        updatePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pwd.getText().toString().equals(rePwd.getText().toString())) {
                    messageCenter.SendYouMessage(messageCenter.ChooseCommand().system_modifypassword(rePwd.getText().toString(), etCode.getText().toString()),
                            ReSetPwd.this);
                } else {
                    com.android.fangxue.utils.Toast.FangXueToast(ReSetPwd.this, "两次密码不相同！");
                }
            }
        });
    }

    private void sendCode(String Phone) {
        messageCenter.SendYouMessage(messageCenter.ChooseCommand().sendcode(Phone, "P"),
                this);
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(String s) {
        Log.e("密码修改", "onNext: " + s);
        JSONObject cmd = JSONUtils.StringToJSON(s);
        if (JSONUtils.getString(cmd, "cmd").equals("system.modifypassword")) {
            if (JSONUtils.getInt(cmd, "code", -1) == 1) {
                finish();
                com.android.fangxue.utils.Toast.FangXueToast(ReSetPwd.this, JSONUtils.getString(cmd, "message"));
            }
            com.android.fangxue.utils.Toast.FangXueToast(ReSetPwd.this, JSONUtils.getString(cmd, "message"));
        }
    }
}
