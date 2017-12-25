package com.android.teacher.ui.auth;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.teacher.R;
import com.android.teacher.base.BaseActivity;
import com.android.teacher.callback.MessageCallBack;
import com.android.teacher.entity.SystemRegister;
import com.android.teacher.newwork.MessageCenter;
import com.android.teacher.ui.Center.ActivityCenter;
import com.android.teacher.ui.Info.SwitchClassFirst;
import com.android.teacher.utils.JSONUtils;
import com.android.teacher.utils.SharedPrefsUtil;
import com.android.teacher.utils.Toast;
import com.tencent.bugly.crashreport.CrashReport;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Observable;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Observer;
import rx.Scheduler;
import rx.observers.Observers;

public class GetPhoneForRegister extends BaseActivity implements MessageCallBack {

    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 303;
    @Bind(R.id.back_btn)
    ImageView backBtn;
    @Bind(R.id.demologin)
    TextView demologin;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.et_username)
    TextView et_username;
    @Bind(R.id.btn_next)
    Button btnNext;
    @Bind(R.id.et_teachername)
    EditText et_teachername;
    @Bind(R.id.et_password)
    EditText et_password;
    @Bind(R.id.rightBtn)
    TextView rightBtn;
    @Bind(R.id.yzCode)
    Button yzCode;
    @Bind(R.id.layout_regist)
    LinearLayout layoutRegist;
    @Bind(R.id.ImageShowAtFirstAll)
    ImageView ImageShowAtFirstAll;
    @Bind(R.id.password)
    EditText pwd;
    @Bind(R.id.login_yzcode)
    Button loginYzcode;
    @Bind(R.id.login_pwd)
    Button loginPwd;
    @Bind(R.id.loginType)
    LinearLayout loginType;


    private int timer = 90;
    private Observer<String> observer;

    private CountDownTimer countDownTimer = new CountDownTimer(90000, 1000) {
        @Override
        public void onTick(long l) {

            String showTime = timer-- + "s";
            yzCode.setText(showTime);
        }

        @Override
        public void onFinish() {
            yzCode.setText("获取验证码");
            yzCode.setClickable(true);
            yzCode.setEnabled(true);
            yzCode.setBackgroundColor(GetPhoneForRegister.this.getResources().getColor(R.color.colorPrimary));
            //设置显示效果
        }
    };
    private MessageCenter messageCenter;

    @Override
    public void setButterKnife() {
        ButterKnife.bind(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.ui_auth_get_phone_for_register;
    }


    void TryToLogin() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(1000);//休眠3秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sendMessToLoginForNoCode();

            }
        }.start();
    }


    void CheckPermission() {
        //需要的权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_DENIED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WAKE_LOCK) == PackageManager.PERMISSION_DENIED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_BOOT_COMPLETED) == PackageManager.PERMISSION_DENIED
                ) {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.WAKE_LOCK,
                            Manifest.permission.CALL_PHONE,
                            Manifest.permission.CAMERA,
                            Manifest.permission.RECEIVE_BOOT_COMPLETED, //开机自启
                            Manifest.permission.READ_PHONE_STATE,  //为止
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,  //SD卡读取
                            Manifest.permission.READ_PHONE_STATE}, //电话
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        }
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

    // 下一步
    @OnClick({R.id.btn_next})
    void overOnclick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                if (toolbarTitle.getText().equals("教师版登录")) {
                    messageCenter.SendYouMessage(messageCenter.ChooseCommand().login(et_username.getText().toString(),
                            pwd.getText().toString(),
                            et_password.getText().toString(),
                            MathineCode(), "T"));

                    SharedPrefsUtil.putValue(GetPhoneForRegister.this, "teacherXML", "MathineCode", MathineCode()); //机器码
                    SharedPrefsUtil.putValue(GetPhoneForRegister.this, "teacherXML", "classid", "");
                } else {

                    SystemRegister registerBean = new SystemRegister();
                    registerBean.setCode(et_password.getText().toString());
                    registerBean.setFinger(MathineCode());
                    registerBean.setMobile(et_username.getText().toString());
                    registerBean.setTeachername(et_teachername.getText().toString());
                    messageCenter.SendYouMessage(messageCenter.ChooseCommand().System_regist(registerBean));

                }
                break;
        }

    }

    //注册点击后
    void registerView() {
        layoutRegist.setVisibility(View.VISIBLE);
        toolbarTitle.setText("老师注册");
        btnNext.setText("下一步");
        backBtn.setVisibility(View.VISIBLE);
        rightBtn.setVisibility(View.GONE);
        loginType.setVisibility(View.GONE);
    }

    void BtnBind() {

        loginYzcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                et_password.setVisibility(View.VISIBLE);
                pwd.setVisibility(View.GONE);
                yzCode.setVisibility(View.VISIBLE);
                et_username.setText("");
                pwd.setText("");


            }
        });

        loginPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_password.setVisibility(View.GONE);
                pwd.setVisibility(View.VISIBLE);
                yzCode.setVisibility(View.GONE);
                et_username.setText("");
                et_password.setText("");

            }
        });


        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerView();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutRegist.setVisibility(View.GONE);
                toolbarTitle.setText("教师版登录");
                btnNext.setText("登录");
                backBtn.setVisibility(View.GONE);
                rightBtn.setVisibility(View.VISIBLE);
                loginType.setVisibility(View.VISIBLE);
            }
        });

        yzCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendCode();
                yzCode.setClickable(false);
                yzCode.setEnabled(false);
                yzCode.setBackgroundColor(GetPhoneForRegister.this.getResources().getColor(R.color.gray));
                countDownTimer.start();
            }
        });
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        backBtn.setVisibility(View.GONE);
        toolbarTitle.setText("教师版登录");
        rightBtn.setVisibility(View.VISIBLE);
        rightBtn.setText("注册");
        if (SharedPrefsUtil.getValue(this, "teacherXML", "username", "").equals("13312345678")) {
            SharedPrefsUtil.putValue(GetPhoneForRegister.this, "teacherXML", "classid", "");
        }
        BtnBind();
        messageCenter = new MessageCenter();
        DealWithData();
    }

    private void DealWithData() {

        observer = new Observer<String>() {
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
    }

    /*
    如果没有账号走这里登录
    可以看到测试数据
    */
    private void DemoLogin() {
        demologin.setVisibility(View.VISIBLE);
        demologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sweetAlertDialog = new SweetAlertDialog(GetPhoneForRegister.this, SweetAlertDialog.PROGRESS_TYPE)
                        .setTitleText("登录中。。。");
                sweetAlertDialog.show();
                messageCenter.SendYouMessage(messageCenter.ChooseCommand().login("13312345678", "0720", MathineCode(), "T"));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sweetAlertDialog != null)
            sweetAlertDialog.dismiss();
    }

    SweetAlertDialog sweetAlertDialog;

    //未登录前先发送无验证码的请求
    private void sendMessToLoginForNoCode() {

        if (!et_username.getText().toString().isEmpty()) {
            messageCenter.SendYouMessage(
                    messageCenter.ChooseCommand().login(et_username.getText().toString(),
                            pwd.getText().toString(),
                            "",
                            MathineCode(),
                            "T"));
        }
    }

    //获取验证码
    private void sendCode() {
        messageCenter.SendYouMessage(messageCenter.ChooseCommand().sendcode(et_username.getText().toString(), "T"));
    }

    //处理回掉的数据
    private void setValue(String s) {
        JSONObject cmd = JSONUtils.StringToJSON(s);

        switch (JSONUtils.getString(cmd, "cmd")) {
            case "system.login":
                //直接使用机器码和验证码登录的情况
                if (JSONUtils.getInt(cmd, "code", -1) == 1) {
                    //根据后台返回的结果，如果是空的就挑到注册
                    JSONObject login = JSONUtils.getSingleJSON(cmd, "data", 0);
                    SharedPrefsUtil.putValue(GetPhoneForRegister.this, "teacherXML", "username", et_username.getText().toString()); //登錄手機
                    SharedPrefsUtil.putValue(GetPhoneForRegister.this, "teacherXML", "teachername", JSONUtils.getString(login, "teachername")); //登錄手機
                    SharedPrefsUtil.putValue(GetPhoneForRegister.this, "teacherXML", "headerimg", JSONUtils.getString(login, "headerimg")); //头像
                    SharedPrefsUtil.putValue(GetPhoneForRegister.this, "teacherXML", "teacherid", JSONUtils.getString(login, "teacherid")); //老师id
                    SharedPrefsUtil.putValue(GetPhoneForRegister.this, "teacherXML", "version", JSONUtils.getString(login, "version")); //版本信息
                    SharedPrefsUtil.putValue(GetPhoneForRegister.this, "teacherXML", "UserPWD", pwd.getText().toString());
                    if (SharedPrefsUtil.getValue(GetPhoneForRegister.this, "teacherXML", "classid", "").equals("")) {
                        messageCenter.SendYouMessage(messageCenter.ChooseCommand().teacher_GetClassList());
                    }

                } else {
                    ImageShowAtFirstAll.setVisibility(View.GONE);
                    sweetAlertDialog = new SweetAlertDialog(GetPhoneForRegister.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("提示")
                            .setContentText(JSONUtils.getString(cmd, "message", "") + "!");
                    sweetAlertDialog.show();
                }
                break;
            case "teacher.getClassList":

                if (JSONUtils.getInt(cmd, "code", -1) == 1) {

                    int classid = 0;
                    if (JSONUtils.getJSONArray(cmd, "data").length() > 0) { //是否绑定班级,如果绑定了只有一个班级的情况 直接调用选择班级,如果大于1 进入选择班级界面,如果没有班级直接进入首页
                        if (JSONUtils.getJSONArray(cmd, "data").length() == 1) {
                            JSONObject classObj = JSONUtils.getSingleJSON(cmd, "data", 0);
                            classid = JSONUtils.getInt(classObj, "classid", 0);
                            messageCenter.SendYouMessage(messageCenter.ChooseCommand().teacher_SelectClass(classid));
                        } else {
                            startActivity(new Intent(GetPhoneForRegister.this, SwitchClassFirst.class));
                            finish();
                        }
                    } else {
                        //只注册老师信息没有进行绑定的时候
                        startActivity(new Intent(GetPhoneForRegister.this, ActivityCenter.class));
                        ThisFinis();
                    }
                }
                break;
            case "teacher.selectClass":
                if (JSONUtils.getInt(cmd, "code", -1) == 1) {
                    JSONObject classInfo = JSONUtils.getSingleJSON(cmd, "data", 0);
                    SharedPrefsUtil.putValue(GetPhoneForRegister.this, "teacherXML", "username", JSONUtils.getString(classInfo, "mobile")); //登錄手機
                    SharedPrefsUtil.putValue(GetPhoneForRegister.this, "teacherXML", "schoolname", JSONUtils.getString(classInfo, "schoolname")); //登錄手機
                    SharedPrefsUtil.putValue(GetPhoneForRegister.this, "teacherXML", "classname", JSONUtils.getString(classInfo, "classname")); //登錄手機
                    SharedPrefsUtil.putValue(GetPhoneForRegister.this, "teacherXML", "classid", JSONUtils.getString(classInfo, "classid")); //classid
                    SharedPrefsUtil.putValue(GetPhoneForRegister.this, "teacherXML", "lesson", JSONUtils.getString(classInfo, "lesson")); //登錄手機
                    SharedPrefsUtil.putValue(GetPhoneForRegister.this, "teacherXML", "roly", JSONUtils.getString(classInfo, "adminflag")); //登錄手機
                    startActivity(new Intent(GetPhoneForRegister.this, ActivityCenter.class));
                    ThisFinis();
                    break;
                } else {
                    sweetAlertDialog = new SweetAlertDialog(GetPhoneForRegister.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("提示")
                            .setContentText(JSONUtils.getString(cmd, "message", "") + "!");
                    sweetAlertDialog.show();
                }

            case "system.regist":
                if (JSONUtils.getInt(cmd, "code", -1) == 1) { //表示注册成功
                    JSONObject classInfoReg = JSONUtils.getSingleJSON(cmd, "data", 0);
                    startActivity(new Intent(GetPhoneForRegister.this, BindRegisterInfo.class));
                    ThisFinis();
                } else {
                    Toast.FangXueToast(this, JSONUtils.getString(cmd, "message", ""));
                }
                break;

        }


    }


    @Override
    protected void onResume() {

        CheckPermission();

        messageCenter.setCallBackInterFace(this);
        super.onResume();

        //判断是不是曾经登录过或者是已经有账号的
        if (SharedPrefsUtil.getValue(GetPhoneForRegister.this, "teacherXML",
                "username", "").isEmpty()) {
            ImageShowAtFirstAll.setVisibility(View.GONE);
            DemoLogin();
        } else {
            et_username.setText(SharedPrefsUtil.getValue(GetPhoneForRegister.this,
                    "teacherXML",
                    "username", ""));
            pwd.setText(SharedPrefsUtil.getValue(this, "teacherXML", "UserPWD", ""));
            TryToLogin();
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


                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

    @Override
    public void onMessage(String str) {


        DealMessageForMe(str, observer);
    }
}
