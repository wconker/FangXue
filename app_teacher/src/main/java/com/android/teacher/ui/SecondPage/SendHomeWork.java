package com.android.teacher.ui.SecondPage;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.teacher.R;
import com.android.teacher.base.BaseActivity;
import com.android.teacher.callback.MessageCallBack;
import com.android.teacher.callback.mClickInterface;
import com.android.teacher.newwork.HttpCenter;
import com.android.teacher.newwork.MessageCenter;
import com.android.teacher.utils.Dialog;
import com.android.teacher.utils.JSONUtils;
import com.android.teacher.utils.Toast;
import com.android.teacher.utils.photoPickerUtil.Photo;
import com.android.teacher.widget.CircleProgressView;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;
import rx.Observer;
import rx.functions.Action1;

public class SendHomeWork extends BaseActivity implements MessageCallBack {


    @Bind(R.id.back_btn)
    ImageView backBtn;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.photo)
    Photo photo;
    @Bind(R.id.BoxMain)
    LinearLayout BoxMain;
    @Bind(R.id.progress)
    LinearLayout progress;
    @Bind(R.id.rightBtn)
    TextView rightBtn;
    @Bind(R.id.title)
    EditText title;
    @Bind(R.id.content)
    EditText content;
    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 130;

    private int RequestType = 0;
    private ArrayList<String> selectedPhotos;
    private Observer<String> observer;
    MessageCenter messageCenter;

    @Override
    public void setButterKnife() {

        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        messageCenter = new MessageCenter();
        messageCenter.setCallBackInterFace(this);

    }

    @Override
    public int getLayoutId() {
        return R.layout.ui_secondpage_send_home_work;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        rightBtn.setText("提交");
        rightBtn.setVisibility(View.VISIBLE);
        photo.setActivity(this);
        RequestType = getIntent().getIntExtra("ConkerData", 0);
        if (RequestType == 2) {
            toolbarTitle.setText("布置作业");
        } else if (RequestType == 1) {
            toolbarTitle.setText("发送通知");
        } else {
            toolbarTitle.setText("分享");
        }
    }

    @OnClick({R.id.back_btn, R.id.rightBtn})
    void onClicked(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
                Dialog.showDialog(this, "提示", "是否确定退出？", new mClickInterface() {
                    @Override
                    public void doClick() {
                        finish();
                    }

                    @Override
                    public void doClick(int pos, View vi) {
                    }
                });
                break;
            case R.id.rightBtn:
                if (RequestType > 0) {
                    if (title.getText().toString().isEmpty()) {
                        Toast.FangXueToast(this, "输入标题，更规范哦");
                    } else if (content.getText().toString().isEmpty()) {
                        Toast.FangXueToast(this, "没有内容，同学很疑惑！");
                    } else {
                        messageCenter.SendYouMessage(messageCenter.ChooseCommand().message_addinfo(
                                title.getText().toString(),
                                content.getText().toString(), RequestType));
                        showProgress(1);
                    }
                }

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK &&
                (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE)) {
            photo.setResult(data);
            selectedPhotos = photo.getPhoto();
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

                    Toast.FangXueToast(this, "请先设置权限");
                    finish();
                }
                return;
            }
        }
    }


    @Override
    public void onBackPressed() {
        Dialog.showDialog(this, "提示", "是否确定退出？", new mClickInterface() {
            @Override
            public void doClick() {
                finish();
            }

            @Override
            public void doClick(int pos, View vi) {

            }
        });
    }

    private void showProgress(int pr) {


        if (pr == 1) {
            progress.setVisibility(View.VISIBLE);
            rightBtn.setEnabled(false);
            rightBtn.setClickable(false);
        } else {
            progress.setVisibility(View.GONE);

        }

    }


    @Override
    protected void setMyObserver() {
        super.setMyObserver();
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
                if (JSONUtils.getString(cmd, "cmd").equals("message.addinfo")) {
                    if (JSONUtils.getString(cmd, "code").equals("1")) {
                        if (selectedPhotos == null) {
                            showProgress(2);
                            Toast.FangXueToast(SendHomeWork.this, JSONUtils.getString(cmd, "message"));
                            finish();
                        } else {
                            JSONObject object = JSONUtils.getSingleJSON(cmd, "data", 0);
                            for (int c = 0; c < selectedPhotos.size(); c++) {
                                Map<String, String> map = new HashMap<>();
                                map.put("picid", String.valueOf(JSONUtils.getInt(object, "workid", 0)));
                                map.put("token", JSONUtils.getString(object, "token", ""));
                                HttpCenter.send(new File(selectedPhotos.get(c)), map);
                            }
                        }
                    } else {
                        Toast.FangXueToast(SendHomeWork.this, JSONUtils.getString(cmd, "message"));
                    }
                }
                if (JSONUtils.getString(cmd, "cmd").equals("upload")) {
                    Toast.FangXueToast(SendHomeWork.this, JSONUtils.getString(cmd, "message"));
                    showProgress(2);
                    finish();
                }

            }
        };
    }

    @Override
    public void onMessage(String str) {

        Log.e("上传信息", str);
        DealMessageForMe(str, observer);
    }


}
