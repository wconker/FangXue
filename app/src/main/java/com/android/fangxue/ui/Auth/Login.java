package com.android.fangxue.ui.Auth;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.fangxue.R;
import com.android.fangxue.base.BaseActivity;
import com.android.fangxue.callback.MessageCallBack;
import com.android.fangxue.newwork.MessageCenter;
import com.android.fangxue.ui.Center.ActivityCenter;
import com.android.fangxue.ui.Contact.ContactListFist;
import com.android.fangxue.utils.CommonUtil;
import com.android.fangxue.utils.JSONUtils;
import com.android.fangxue.utils.SharedPrefsUtil;
import com.android.fangxue.utils.TimeUtil;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.tencent.bugly.crashreport.CrashReport;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class Login extends BaseActivity implements MessageCallBack {


    @Bind(R.id.flash_start)
    ImageView flashStart;
    @Bind(R.id.login_view)
    LinearLayout loginView;
    private Context context = this;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.et_username)
    EditText et_username;
    @Bind(R.id.et_password)
    EditText et_password;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.demologin)
    TextView demologin;
    @Bind(R.id.back_btn)
    ImageView backBtn;

    @Bind(R.id.login_ll)
    LinearLayout loginLl;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.yzCode)
    Button yzCode;
    private Context mContext = this;

    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 303;
    private int time = 0;
    private Observer<String> ober = new Observer<String>() {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(String s) {
            setValue(s);
        }
    };
    ;
    private MessageCenter messageCenter;

    @Override
    public void setButterKnife() {

        ButterKnife.bind(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        backBtn.setVisibility(View.GONE);
        toolbarTitle.setText("放学神器登录");
        if (SharedPrefsUtil.getValue(this, "loginXML", "username", "").equals("13312345678")) {
            SharedPrefsUtil.putValue(Login.this, "userXML", "studentid", "");
        }
        messageCenter = new MessageCenter(this);

    }

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

    /*
    如果没有账号走这里登录
    可以看到测试数据
    */
    private void DemoLogin() {

        demologin.setVisibility(View.VISIBLE);
        demologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //演示账号登录
                messageCenter.SendYouMessage(
                        messageCenter.ChooseCommand().login("13312345678",
                                "0720",
                                MathineCode(),
                                "P"));
            }
        });
    }

    void CheckPermission() {
        //需要的权限
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_DENIED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WAKE_LOCK) == PackageManager.PERMISSION_DENIED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_BOOT_COMPLETED) == PackageManager.PERMISSION_DENIED
                ) {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.WAKE_LOCK,
                            Manifest.permission.CALL_PHONE,
                            Manifest.permission.RECEIVE_BOOT_COMPLETED, //开机自启
                            Manifest.permission.READ_PHONE_STATE,  //为止
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,  //SD卡读取
                            Manifest.permission.READ_PHONE_STATE}, //电话
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        } else {

            try {
                Thread.sleep(1000);//休眠1秒
                sendMess();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        messageCenter.setCallBackInterFace(this);
        CheckPermission();//权限判断
    }

    @Override
    public int getLayoutId() {
        return R.layout.ui_auth_login;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

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


    //根据返回数据操作
    void setValue(String s) {
        JSONObject cmd = JSONUtils.StringToJSON(s);
        if (JSONUtils.getInt(cmd, "code", -1) == 1) {

            switch (JSONUtils.getString(cmd, "cmd")) {
                case "system.machinecode":
                    checkMachineCode();
                    break;
                case "system.login":
                    JSONObject LoginObj = JSONUtils.getSingleJSON(cmd, "data", 0);
                    SharedPrefsUtil.putValue(context, "userXML", "userId", JSONUtils.getString(LoginObj, "parentid")); //用户id
                    SharedPrefsUtil.putValue(context, "userXML", "version", JSONUtils.getString(LoginObj, "version")); //版本信息
                    if (SharedPrefsUtil.getValue(Login.this, "userXML", "studentid", "").equals("")) {//id 为空
                        getUserStudentList();
                    }
                    break;
                case "parent.getstudentlist":
                    JSONArray students = JSONUtils.getJSONArray(cmd, "data");
                    //这里只判断studentid 为空的情况,防止和http中的重连重复发送导致crash

                    try {

                        if (students.length() > 1) {

                            startActivity(new Intent(Login.this, ContactListFist.class));
                            finish();
                            //
                        } else {
                            JSONObject Somestudent = (JSONObject) students.get(0);
                            messageCenter.SendYouMessage(messageCenter.ChooseCommand().selectStudent(JSONUtils.getInt(Somestudent, "studentid", 0)));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;
                case "parent.selectstudent":
                    JSONObject studentObj = JSONUtils.getSingleJSON(cmd, "data", 0);
                    SharedPrefsUtil.putValue(context, "userXML", "studentid", JSONUtils.getString(studentObj, "studentid")); //用户信息
                    SharedPrefsUtil.putValue(context, "userXML", "studentName", JSONUtils.getString(studentObj, "studentname")); //用户信息
                    SharedPrefsUtil.putValue(context, "userXML", "headerimg", JSONUtils.getString(studentObj, "headerimg")); //头像信息
                    SharedPrefsUtil.putValue(context, "userXML", "studentSchoolnm", JSONUtils.getString(studentObj, "schoolnm")); //用户信息
                    SharedPrefsUtil.putValue(context, "userXML", "studentClassname", JSONUtils.getString(studentObj, "classname")); //用户信息
                    SharedPrefsUtil.putValue(context, "userXML", "parentname", JSONUtils.getString(studentObj, "parentname")); //家长姓名
                    SharedPrefsUtil.putValue(context, "userXML", "relationship", JSONUtils.getString(studentObj, "relationship")); //家长关系
                    SharedPrefsUtil.putValue(context, "userXML", "mobile", JSONUtils.getString(studentObj, "mobile")); //家长电话
                    startActivity(new Intent(Login.this, ActivityCenter.class));
                    finish();
                    break;

            }

        } else {
            loginView.setVisibility(View.VISIBLE);
            flashStart.setVisibility(View.GONE);
            Toast.makeText(mContext, JSONUtils.getString(cmd, "message"), Toast.LENGTH_SHORT).show();
        }
    }

    //发送只有机器代码的登录信息
    private void sendMess() {
        //看看本地有没有存登录信息，有的话就填充到输入框
        if (!SharedPrefsUtil.getValue(this, "loginXML", "UserName", "").equals("")) {
            et_username.setText(SharedPrefsUtil.getValue(this, "loginXML", "UserName", ""));
            et_password.setText(SharedPrefsUtil.getValue(this, "loginXML", "UserPWD", ""));
            messageCenter.SendYouMessage(
                    messageCenter.ChooseCommand().login(et_username.getText().toString(),
                            "",
                            MathineCode(),
                            "P"));
        } else {
            loginView.setVisibility(View.VISIBLE);
            flashStart.setVisibility(View.GONE);
            DemoLogin();
        }

    }

    //发送带有验证码的登录信息
    private void sendMLogin() {

        messageCenter.SendYouMessage(
                messageCenter.ChooseCommand().login(et_username.getText().toString(),
                        et_password.getText().toString(),
                        MathineCode(),
                        "P"));


    }

    private void sendCode() {
        messageCenter.SendYouMessage(messageCenter.ChooseCommand().sendcode(et_username.getText().toString(), "P"));
    }

    @OnClick({R.id.btn_login, R.id.yzCode})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                //登录
                boolean isNetConnected = CommonUtil.isNetworkAvailable(this);
                if (!isNetConnected) {
                    return;
                } else {//
                    sendMLogin();
                    //如果是点击按钮登录的则清空studentid避免当前账号对应到上一个账号的studentid中
                    SharedPrefsUtil.putValue(Login.this, "userXML", "studentid", "");
                }
                break;
            case R.id.yzCode:
                if (et_username.getText().toString().isEmpty()) {
                    com.android.fangxue.utils.Toast.FangXueToast(context, "请检查手机号码！");
                } else {
                    countDownTimer.start();
                    sendCode();
                }
                break;

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendMess();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

    private void checkMachineCode() {

        messageCenter.SendYouMessage(messageCenter.ChooseCommand().getstudentlist());

    }

    //登录成功后调用获取孩子列表
    private void getUserStudentList() {
        SharedPrefsUtil.putValue(this, "loginXML", "UserName", et_username.getText().toString()); //用户登录账号
        SharedPrefsUtil.putValue(this, "loginXML", "mathinecode", MathineCode()); //用户登录账号
        messageCenter.SendYouMessage(messageCenter.ChooseCommand().getstudentlist());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimer.onFinish();
    }

    @Override
    public void onMessage(String str) {
        Log.e("登录信息", str);
        Observable.just(str).observeOn(AndroidSchedulers.mainThread()).subscribe(ober);
    }


}





