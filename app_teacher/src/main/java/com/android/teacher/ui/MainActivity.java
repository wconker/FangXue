package com.android.teacher.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

import com.android.teacher.base.BaseActivity;
import com.android.teacher.R;
import com.android.teacher.callback.MessageCallBack;
import com.android.teacher.newwork.MessageCenter;
import com.android.teacher.ui.Center.ActivityCenter;
import com.android.teacher.ui.auth.GetPhoneForRegister;
import com.android.teacher.utils.JSONUtils;
import com.android.teacher.utils.SharedPrefsUtil;
import com.android.teacher.utils.Toast;
import com.tbruyelle.rxpermissions.RxPermissions;

import org.json.JSONObject;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

public class MainActivity extends BaseActivity implements MessageCallBack {


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

            Log.e("dd", s);
            JSONObject cmd = JSONUtils.StringToJSON(s);

            switch (JSONUtils.getString(cmd, "cmd")) {
                case "system.login":
                    //直接使用机器码和验证码登录的情况
                    if (JSONUtils.getInt(cmd, "code", -1) == 1) {
                        //根据后台返回的结果，如果是空的就挑到注册
                        JSONObject login = JSONUtils.getSingleJSON(cmd, "data", 0);
                        if (SharedPrefsUtil.getValue(MainActivity.this, "teacherXML", "classid", "").equals("")) {
                            messageCenter.SendYouMessage(messageCenter.ChooseCommand().teacher_GetClassList());
                        }

                    } else {
                        startActivity(new Intent(MainActivity.this, GetPhoneForRegister.class));
                    }
                    break;
            }
        }
    };

    @Override
    public void setButterKnife() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        Observable.timer(1,TimeUnit.SECONDS);

        messageCenter = new MessageCenter();



    }

    private String MathineCode() {
        final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDevice, tmSerial, tmPhone, androidId;


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        return deviceUuid.toString();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sendMessToLoginForNoCode();
    }

    //未登录前先发送无验证码的请求
    private void sendMessToLoginForNoCode() {

        if (SharedPrefsUtil.getValue(MainActivity.this, "teacherXML",
                "username", "").isEmpty()) {
            messageCenter.SendYouMessage(messageCenter.ChooseCommand().login(SharedPrefsUtil.getValue(MainActivity.this, "teacherXML",
                            "username", ""),
                            "",
                            MathineCode(),
                            "T"), this);
        }else {
            startActivity(new Intent(MainActivity.this, GetPhoneForRegister.class));
        }
    }

    @Override
    public void onMessage(String str) {
        DealMessageForMe(str, observer);
    }
}
