package com.android.fangxue.ui.Auth;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.fangxue.R;
import com.android.fangxue.callback.MessageCallBack;
import com.android.fangxue.newwork.MessageCenter;
import com.android.fangxue.utils.JSONUtils;
import com.android.fangxue.utils.SharedPrefsUtil;
import com.android.fangxue.utils.Toast;
import com.android.fangxue.widget.CircleImageView;

import org.json.JSONObject;

import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class Reg extends Activity implements MessageCallBack {

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
    @Bind(R.id.et_xm)
    EditText etXm;
    @Bind(R.id.et_pwd)
    EditText etPwd;
    @Bind(R.id.et_mobile)
    EditText etMobile;
    @Bind(R.id.et_code)
    EditText etCode;
    @Bind(R.id.yzCode)
    Button yzCode;
    @Bind(R.id.login_ll)
    LinearLayout loginLl;
    @Bind(R.id.btn_login)
    Button btnLogin;
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

            switch (JSONUtils.getString(cmd, "cmd")) {

                case "system.regist":
                    if (JSONUtils.getInt(cmd, "code", -1) == 1) {
                        SharedPrefsUtil.putValue(Reg.this, "loginXML", "UserName", etMobile.getText().toString());
                        SharedPrefsUtil.putValue(Reg.this, "loginXML", "password", etPwd.getText().toString());

                        finish();
//                        startActivity(new Intent(Reg.this, Guide_Res.class));
//
                    } else {
                        Toast.FangXueToast(Reg.this, JSONUtils.getString(cmd, "message", ""));
                    }
                    break;
            }


        }
    };
    private MessageCenter messageCenter;
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

    @SuppressLint("MissingPermission")
    private String MathineCode() {
        final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDevice, tmSerial, tmPhone, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        return deviceUuid.toString();
    }


    @OnClick({R.id.back_btn, R.id.btn_login, R.id.yzCode})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                finish();
                break;
            case R.id.btn_login:
                messageCenter.SendYouMessage(messageCenter.ChooseCommand().parent_regiest(etMobile.getText().toString(),
                        etCode.getText().toString(),
                        etXm.getText().toString(),
                        etPwd.getText().toString(),
                        MathineCode()
                ), this);

                break;
            case R.id.yzCode:
                if (etMobile.getText().toString().isEmpty()) {
                    com.android.fangxue.utils.Toast.FangXueToast(this, "请检查手机号码！");
                } else {
                    countDownTimer.start();
                    sendCode();
                }
                break;
        }
    }

    private void sendCode() {
        messageCenter.SendYouMessage(messageCenter.ChooseCommand().sendcode(etMobile.getText().toString(), "P"), this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_auth_activity_reg);
        ButterKnife.bind(this);
        messageCenter = new MessageCenter();
        toolbarTitle.setText("家长注册");
    }

    @Override
    public void onMessage(String str) {
        Log.e("Reg", str);
        Observable.just(str)
                .observeOn(AndroidSchedulers
                        .mainThread())
                .subscribe(observer);
    }
}
