package com.android.fangxue.ui.Center;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.android.fangxue.Default.FinalField;
import com.android.fangxue.R;
import com.android.fangxue.base.BaseFragment;
import com.android.fangxue.callback.MessageCallBack;
import com.android.fangxue.callback.mClickInterface;
import com.android.fangxue.newwork.HttpCenter;
import com.android.fangxue.newwork.MessageCenter;

import com.android.fangxue.ui.Auth.Login;
import com.android.fangxue.ui.Contact.ContactList;
import com.android.fangxue.ui.Detail.FeedBack;
import com.android.fangxue.ui.Detail.ParentsInfo;
import com.android.fangxue.ui.Us;
import com.android.fangxue.utils.ACache;
import com.android.fangxue.utils.JSONUtils;
import com.android.fangxue.utils.SharedPrefsUtil;
import com.android.fangxue.utils.Toast;
import com.android.fangxue.widget.CircleImageView;
import com.bumptech.glide.Glide;
import com.foamtrace.photopicker.ImageCaptureManager;
import com.foamtrace.photopicker.ImageConfig;
import com.foamtrace.photopicker.PhotoPickerActivity;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;

import org.json.JSONObject;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class settingFragment extends BaseFragment implements MessageCallBack {

    @Bind(R.id.ChildName)
    TextView ChildName;
    @Bind(R.id.mobile)
    TextView mobile;
    @Bind(R.id.versionOfcurrent)
    TextView versionOfcurrent;
    @Bind(R.id.relationship_txt)
    TextView relationship_txt;
    @Bind(R.id.Childs)
    LinearLayout Childs;
    @Bind(R.id.parent_mobile)
    LinearLayout parent_mobile;
    @Bind(R.id.parent)
    LinearLayout parent;
    @Bind(R.id.cleancache)
    LinearLayout cleancache;
    @Bind(R.id.relationship)
    LinearLayout relationship;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.rightBtn)
    TextView rightBtn;
    @Bind(R.id.back_btn)
    ImageView backBtn;
    @Bind(R.id.school)
    TextView school;
    @Bind(R.id.classinfo)
    TextView classinfo;
    @Bind(R.id.parentName)
    TextView parentName;
    @Bind(R.id.feedbacks)
    LinearLayout feedbacks;
    @Bind(R.id.loginoout)
    LinearLayout loginoout;
    @Bind(R.id.tx_layout)
    LinearLayout txLayout;
    @Bind(R.id.aboutus)
    LinearLayout aboutus;
    @Bind(R.id.tx_img)
    CircleImageView txImg;

    @Bind(R.id.cache)
    TextView cache;
    @Bind(R.id.refresh)
    SwipeRefreshLayout refresh;

    private Context context = getActivity();
    @Bind(R.id.sw_switch)
    Switch swSwitch;
    private MessageCenter messageCenter;
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
                Toast.FangXueToast(getActivity(), JSONUtils.getString(cmd, "message"));
            }
            if (JSONUtils.getString(cmd, "cmd").equals("upload")) {
                if (JSONUtils.getInt(cmd, "code", 0) == 1) {
                    JSONObject object = JSONUtils.getSingleJSON(cmd, "data", 0);
                    String path = JSONUtils.getString(object, "path");
                    Glide.with(getActivity()).load(path).into(txImg);
                    SharedPrefsUtil.putValue(getActivity(), "userXML", "headerimg", path);
                    Toast.FangXueToast(getActivity(), JSONUtils.getString(cmd, "message"));
                }
            }
        }
    };
    String Token = "";

    public static settingFragment newInstance() {

        return new settingFragment();
    }

    @Override
    protected void initView(View view) {


    }


    //照片选择后的回调函数
    private void refreshAdpater(ArrayList<String> paths) {

        messageCenter.setCallBackInterFace(this);
        messageCenter.SendYouMessage(messageCenter.ChooseCommand().system_token(SharedPrefsUtil.getValue(getActivity(), "userXML", "studentid", "")));
        prepareImg = paths.get(0);
    }


    private void SetImage() {
        txLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });
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

    @OnClick({R.id.parent, R.id.loginoout, R.id.relationship, R.id.parent_mobile, R.id.feedbacks, R.id.Childs, R.id.aboutus})
    void click(View v) {

        switch (v.getId()) {
            case R.id.parent:
            case R.id.parent_mobile:
            case R.id.relationship:
                startActivity(new Intent(getActivity(), ParentsInfo.class));
                break;
            case R.id.Childs:
                startActivity(new Intent(getActivity(), ContactList.class));
                break;
            case R.id.feedbacks:
                startActivity(new Intent(getActivity(), FeedBack.class));
                break;
            case R.id.aboutus:
                startActivity(new Intent(getActivity(), Us.class));
                break;
            case R.id.loginoout:
                SharedPrefsUtil.putValue(getActivity(), "loginXML", "UserName", ""); //用户登录账号
                SharedPrefsUtil.putValue(getActivity(), "userXML", "studentid", "");

                startActivity(new Intent(getActivity(), Login.class));
                getActivity().finish();
                break;
            default:
                Toast.FangXueToast(getActivity(), "敬请期待");
        }


    }


    @Override
    public void onResume() {
        super.onResume();
        String imgUrl = SharedPrefsUtil.getValue(getActivity(), "userXML", "headerimg", "");
        if (imgUrl.isEmpty()) {
            Glide.with(getActivity()).load(R.drawable.username).into(txImg);
        } else {
            Glide.with(getActivity()).load(imgUrl).into(txImg);
        }
        ChildName.setText(SharedPrefsUtil.getValue(getActivity(), "userXML", "studentName", ""));
        school.setText(SharedPrefsUtil.getValue(getActivity(), "userXML", "studentSchoolnm", ""));
        classinfo.setText(SharedPrefsUtil.getValue(getActivity(), "userXML", "studentClassname", ""));
        parentName.setText(SharedPrefsUtil.getValue(getActivity(), "userXML", "parentname", ""));
        mobile.setText(SharedPrefsUtil.getValue(getActivity(), "userXML", "mobile", ""));
        relationship_txt.setText(SharedPrefsUtil.getValue(getActivity(), "userXML", "relationship", ""));
        refresh.setRefreshing(false);

        versionOfcurrent.setText(SharedPrefsUtil.getValue(getActivity(), "userXML", "version", "维护中"));
    }

    //点击底部tab页面切换时候调用
    public void onPageChange() {
        messageCenter.setCallBackInterFace(this);
        try {
            String CacheSize = getTotalCacheSize(getActivity());
            cache.setText(CacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }

        onResume();
    }

    /**
     * 获取缓存大小
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static String getTotalCacheSize(Context context) throws Exception {
        long cacheSize = getFolderSize(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(context.getExternalCacheDir());
        }
        return getFormatSize(cacheSize);
    }

    /**
     * 清除缓存
     *
     * @param context
     */
    public static void clearAllCache(Context context) {
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalCacheDir());
        }
    }

    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }


    // 获取文件大小
    //Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
    //Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }


    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
//            return size + "Byte";
            return "0K";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "K";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "M";
        }
        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }

    @Override
    protected int getLayout() {
        return R.layout.ui_center_shoppingcardfragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        messageCenter = new MessageCenter();
        CheckNotifyStatus();
        CacheSetting();
        SetImage();
        toolbarTitle.setText("设置");
        backBtn.setVisibility(View.GONE);


        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onPageChange();
            }
        });
        return rootView;
    }


    // FIXME: 17/11/15 设置缓存
    private void CacheSetting() {

        ACache aCache = ACache.get(getActivity());

        //清理缓存区
        cleancache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                com.android.fangxue.widget.Dialog.showDialog(getActivity(), "操作确认", "是否确认清楚缓存？", new mClickInterface() {
                    @Override
                    public void doClick() {
                        clearAllCache(getActivity());
                        onPageChange();
                    }

                    @Override
                    public void doClick(int pos, View vi) {
                    }
                });

            }
        });
    }

    private void CheckNotifyStatus() {
        //设置是否开启通知按钮
        if (SharedPrefsUtil.getValue(getActivity(), "systemXML", "notify", FinalField.POWER_OPEN)) {
            swSwitch.setChecked(false);
        } else {
            swSwitch.setChecked(true);
        }
        swSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    Snackbar.make(swSwitch, "通知已打开！", Snackbar.LENGTH_SHORT).show();
                    SharedPrefsUtil.putValue(getActivity(), "systemXML", "notify", FinalField.POWER_OPEN);
                } else {
                    Snackbar.make(swSwitch, "通知已关闭！", Snackbar.LENGTH_SHORT).show();
                    SharedPrefsUtil.putValue(getActivity(), "systemXML", "notify", FinalField.POWER_CLOSE);
                }
            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onMessage(String str) {
        Log.e("setting", str);
        Observable.just(str)
                .observeOn(AndroidSchedulers
                        .mainThread())
                .subscribe(observer);
    }
}
