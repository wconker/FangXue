package com.android.teacher.ui.Center;


import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.teacher.R;
import com.android.teacher.base.BaseFragment;
import com.android.teacher.base.CommonAdapter;
import com.android.teacher.base.CommonViewHolder;
import com.android.teacher.callback.MessageCallBack;
import com.android.teacher.newwork.HttpCenter;
import com.android.teacher.newwork.MessageCenter;
import com.android.teacher.ui.AboutUs;
import com.android.teacher.ui.Info.AddParent;
import com.android.teacher.ui.Info.AddTeacher;
import com.android.teacher.ui.Info.StudentInfo;
import com.android.teacher.ui.Info.SwitchClass;
import com.android.teacher.ui.Schdule.SchduleList;
import com.android.teacher.ui.SecondPage.RePwd;
import com.android.teacher.ui.auth.BindRegisterInfo;
import com.android.teacher.ui.auth.GetPhoneForRegister;
import com.android.teacher.utils.JSONUtils;
import com.android.teacher.utils.SharedPrefsUtil;
import com.android.teacher.utils.Toast;
import com.android.teacher.widget.CircleImageView;
import com.bumptech.glide.Glide;
import com.foamtrace.photopicker.ImageCaptureManager;
import com.foamtrace.photopicker.ImageConfig;
import com.foamtrace.photopicker.PhotoPickerActivity;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;
import com.tbruyelle.rxpermissions.RxPermissions;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * A simple {@link Fragment} subclass.
 */
public class mineFragment extends BaseFragment implements MessageCallBack {
    private List<String> mData = new ArrayList<>();
    @Bind(R.id.gv)
    GridView gv;
    @Bind(R.id.tx)
    CircleImageView tx;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.category)
    TextView category;
    @Bind(R.id.mobile)
    TextView mobile;
    @Bind(R.id.className)
    TextView className;
    @Bind(R.id.schoolName)
    TextView schoolName;
    @Bind(R.id.currentversion)
    TextView currentversion;
    @Bind(R.id.loginOut)
    LinearLayout loginOut;
    @Bind(R.id.classlist)
    LinearLayout classlist;
    @Bind(R.id.rolyBox)
    LinearLayout rolyBox;
    @Bind(R.id.share)
    LinearLayout share;
    @Bind(R.id.setting_repwd)
    LinearLayout SettingRpwd;
    @Bind(R.id.schedul)
    LinearLayout schedul;
    @Bind(R.id.teacherlist)
    LinearLayout teacherlist;
    @Bind(R.id.setting_aboutus)
    LinearLayout setting_aboutus;
    @Bind(R.id.class_l)
    LinearLayout class_l;
    @Bind(R.id.ku)
    LinearLayout ku;
    @Bind(R.id.refresh)
    SwipeRefreshLayout refreshLayout;
    private String prepareImg = "";
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
            if (JSONUtils.getString(cmd, "cmd").equals("system.token")) {
                if (JSONUtils.getInt(cmd, "code", 0) == 1) {
                    JSONObject object = JSONUtils.getSingleJSON(cmd, "data", 0);
                    Token = JSONUtils.getString(object, "token");
                    Log.e("ConkerFile", prepareImg);
                    Map<String, String> map = new HashMap<>();
                    map.put("token", Token);
                    HttpCenter.send(new File(prepareImg), map);
                }
                Toast.FangXueToast(getActivity(), "正在上传。。。");
            }
            if (JSONUtils.getString(cmd, "cmd").equals("upload")) {
                if (JSONUtils.getInt(cmd, "code", 0) == 1) {
                    JSONObject object = JSONUtils.getSingleJSON(cmd, "data", 0);
                    String path = JSONUtils.getString(object, "path");
                    Glide.with(getActivity()).load(path).into(tx);
                    SharedPrefsUtil.putValue(getActivity(), "teacherXML", "headerimg", path);
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("成功")
                            .setContentText(JSONUtils.getString(cmd, "message"))
                            .show();
                }
            }
        }
    };
    String Token = "";
    private MessageCenter messageCenter;

    public static mineFragment newInstance() {
        return new mineFragment();
    }

    public void setCallBackInterFace() {

        messageCenter.setCallBackInterFace(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("mine的", "onResume: ");
        refreshData();
    }

    void refreshData() {

        if (((ActivityCenter) getActivity()).CurrentPos == 3) {
            messageCenter.setCallBackInterFace(this);
            name.setText(SharedPrefsUtil.getValue(getActivity(), "teacherXML", "teachername", ""));
            mobile.setText(SharedPrefsUtil.getValue(getActivity(), "teacherXML", "username", ""));
            category.setText(SharedPrefsUtil.getValue(getActivity(), "teacherXML", "lesson", ""));
            className.setText(SharedPrefsUtil.getValue(getActivity(), "teacherXML", "classname", ""));
            schoolName.setText(SharedPrefsUtil.getValue(getActivity(), "teacherXML", "schoolname", ""));

            //权限判断
            if (SharedPrefsUtil.getValue(getActivity(), "teacherXML", "roly", "0").equals("1")) {
                teacherlist.setVisibility(View.VISIBLE);
                teacherlist.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getActivity(), Teachers.class));
                    }
                });
                schedul.setVisibility(View.VISIBLE);
                schedul.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getActivity(), SchduleList.class));
                    }
                });
                classlist.setVisibility(View.VISIBLE);
                classlist.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent it = new Intent(getActivity(), BindRegisterInfo.class);
                        it.putExtra("classid", SharedPrefsUtil.getValue(getActivity(), "teacherXML", "teacherid", "null"));
                        startActivity(it);
                    }
                });

                rolyBox.setVisibility(View.VISIBLE);
            } else {
                schedul.setVisibility(View.GONE);
                teacherlist.setVisibility(View.GONE);
                classlist.setVisibility(View.GONE);
                rolyBox.setVisibility(View.GONE);
            }

            if (!SharedPrefsUtil.getValue(getActivity(), "teacherXML", "headerimg", "").equals("null") && !SharedPrefsUtil.getValue(getActivity(), "teacherXML", "headerimg", "").isEmpty()) {
                Glide.with(getActivity()).load(SharedPrefsUtil.getValue(getActivity(), "teacherXML", "headerimg", "")).into(tx);
            } else {
                Glide.with(getActivity()).load(R.drawable.username).into(tx);
            }

            currentversion.setText(SharedPrefsUtil.getValue(getActivity(), "teacherXML", "version", "维护中"));


        }

    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected int getLayout() {
        return R.layout.ui_center_minefragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        messageCenter = new MessageCenter();
        loginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPrefsUtil.putValue(getActivity(), "teacherXML", "lesson", ""); //登錄手機
                SharedPrefsUtil.putValue(getActivity(), "teacherXML", "teachername", ""); //登錄手機
                SharedPrefsUtil.putValue(getActivity(), "teacherXML", "schoolname", ""); //登錄手機
                SharedPrefsUtil.putValue(getActivity(), "teacherXML", "classname", ""); //登錄手機
                SharedPrefsUtil.putValue(getActivity(), "teacherXML", "classid", ""); //登錄手機
                SharedPrefsUtil.putValue(getActivity(), "teacherXML", "username", ""); //登錄手機
                startActivity(new Intent(getActivity(), GetPhoneForRegister.class));
            }
        });
        setting_aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AboutUs.class));
            }
        });

        classlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SwitchClass.class));
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
                refreshLayout.setRefreshing(false);
            }
        });

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Go = new Intent(getActivity(), AddTeacher.class);
                int tid = Integer.valueOf(SharedPrefsUtil.getValue(getActivity(), "teacherXML", "teacherid", "0"));
                Go.putExtra("teacherid", tid);
                startActivity(Go);
            }
        });

        ku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Go = new Intent(getActivity(), AddTeacher.class);
                int tid = Integer.valueOf(SharedPrefsUtil.getValue(getActivity(), "teacherXML", "teacherid", "0"));
                Go.putExtra("teacherid", tid);
                startActivity(Go);
            }
        });
        tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RxPermissions.getInstance(getActivity())
                        .request(Manifest.permission.CAMERA)//这里填写所需要的权限
                        .subscribe(new Action1<Boolean>() {
                            @Override
                            public void call(Boolean aBoolean) {
                                if (aBoolean) {//true表示获取权限成功（注意这里在android6.0以下默认为true）
                                    ChooseImage();
                                } else {
                                    Toast.FangXueToast(getActivity(), "请先允许访问您的摄像头及相册！");

                                }
                            }
                        });
            }
        });
        SettingRpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Go = new Intent(getActivity(), RePwd.class);
                startActivity(Go);
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Intent.ACTION_SEND);
                intent1.putExtra(Intent.EXTRA_TEXT, "放学神器教师版App，精准掌握孩子放学时间【http://fangxue.56pt.cn/fx/app/teacher.apk】");
                intent1.setType("text/plain");
                startActivity(Intent.createChooser(intent1, "放学神器教师版"));
                //图片分享
//                Intent intent2 = new Intent(Intent.ACTION_SEND);
//                Uri uri = Uri.fromFile(new File(path));
//                intent2.putExtra(Intent.EXTRA_STREAM, uri);
//                intent2.setType("image/*");
//                startActivity(Intent.createChooser(intent2, "share"));


//

//
//                Uri uri = Uri.fromFile(new File("/storage/emulated/0/Pictures/Screenshots/Screenshot_2017-12-14-11-14-47.png"));
//                Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                if (uri != null) {
//                    shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
//                    shareIntent.setType("image/*");
//                    //当用户选择短信时使用sms_body取得文字
//                    shareIntent.putExtra("sms_body", "内容");
//                } else {
//                    shareIntent.setType("text/plain");
//                }
//                shareIntent.putExtra(Intent.EXTRA_TEXT,"内容");
//                //自定义选择框的标题
//                startActivity(Intent.createChooser(shareIntent, "邀请好友"));
                //系统默认标题

            }
        });

        gv.setAdapter(new CommonAdapter<String>(getActivity(), mData, R.layout.common_gv_layout) {
            @Override
            public void setViewContent(CommonViewHolder viewHolder, String s) {
                viewHolder.setText(R.id.title, s);
            }
        });

        return rootView;
    }


    private void ChooseImage() {

        ImageConfig config = new ImageConfig();
        config.minHeight = 400;
        config.minWidth = 400;
        config.mimeType = new String[]{"image/jpeg", "image/png"}; // 图片类型 image/gif ...
        config.minSize = 1 * 1024 * 1024; // 1Mb 图片大小
        PhotoPickerIntent intent = new PhotoPickerIntent(getActivity());
        intent.setSelectModel(SelectModel.SINGLE);
        intent.setShowCarema(true); // 是否显示拍照， 默认false
        // intent.setImageConfig(config);
        startActivityForResult(intent, 120);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == 120) {
            switch (requestCode) {
                // 选择照片
                case 120:
                    refreshAdpater(data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT));
                    break;
                // 拍照
                case ImageCaptureManager.REQUEST_TAKE_PHOTO:
//                    if(captureManager.getCurrentPhotoPath() != null) {
//                        captureManager.galleryAddPic();
//                        // 照片地址
//                        String imagePaht = captureManager.getCurrentPhotoPath();
//                        // ...
//                    }
                    break;
                // 预览
//                case REQUEST_PREVIEW_CODE:
//                    refreshAdpater(data.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT));
//                    break;
            }
        }

    }

    //开始上传头像  1 先获取token
    private void refreshAdpater(ArrayList<String> paths) {
        messageCenter.setCallBackInterFace(this);
        messageCenter.SendYouMessage(messageCenter.ChooseCommand().system_token(SharedPrefsUtil.getValue(getActivity(), "teacherXML", "teacherid", "")));
        prepareImg = paths.get(0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);

    }
    @Override
    public void onMessage(String str) {

        Observable.just(str)
                .observeOn(AndroidSchedulers
                        .mainThread())
                .subscribe(observer);
    }
}
