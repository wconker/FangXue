package com.android.teacher.ui.Center;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.teacher.R;
import com.android.teacher.base.BaseFragment;
import com.android.teacher.base.CommonAdapter;
import com.android.teacher.base.CommonViewHolder;
import com.android.teacher.callback.DataBack;
import com.android.teacher.callback.MessageCallBack;
import com.android.teacher.callback.mClickInterface;
import com.android.teacher.newwork.MessageCenter;
import com.android.teacher.ui.Info.SwitchClass;
import com.android.teacher.ui.Schdule.SchduleList;
import com.android.teacher.ui.SecondPage.SendHomeWork;
import com.android.teacher.utils.Dialog;
import com.android.teacher.utils.JSONUtils;
import com.android.teacher.utils.SharedPrefsUtil;
import com.android.teacher.utils.TakePhotoPopWin;
import com.android.teacher.utils.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class indexFragment extends BaseFragment implements MessageCallBack, DataBack {


    @Bind(R.id.className)
    TextView className;
    @Bind(R.id.HomeBackTime)
    TextView HomeBackTime;
    @Bind(R.id.switchClass)
    ImageView switchClass;
    @Bind(R.id.boxMain)
    LinearLayout boxMain;
    @Bind(R.id.delayNotify)
    TextView delayNotify;
    @Bind(R.id.delay_layout)
    LinearLayout delayLayout;
    @Bind(R.id.button2)
    ImageView button2;
    @Bind(R.id.btn_delay)
    ImageView btnDelay;
    @Bind(R.id.classover)
    LinearLayout classover;
    @Bind(R.id.gv)
    GridView gv;
    @Bind(R.id.refresh)
    SwipeRefreshLayout refresh;
    private List<String> list = new ArrayList<>();
    private Intent inte;
    private GvAdapter adapter;
    private MessageCenter messageCenter;
    private Observer<String> dataDeal;
    private String deLayStr = "";
    TakePhotoPopWin takePhotoPopWin;

    public static indexFragment newInstance() {
        return new indexFragment();
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    public void onResume() {
        super.onResume();

        ActivityCenter main = ((ActivityCenter) getActivity());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = main.getWindow();

            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getActivity().getResources().getColor(R.color.green_450));
        }


        if (((ActivityCenter) getActivity()).CurrentPos == 0)
            stateSet();

    }

    private void stateSet() {


        String titleName = SharedPrefsUtil.getValue(getActivity(), "teacherXML", "schoolname", "") + " " + SharedPrefsUtil.getValue(getActivity(),
                "teacherXML", "classname", "");
        className.setText(titleName);
        try {
            Thread.sleep(400);
            getNotify();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        list.clear();
        list.add("延迟放学");
        list.add("留作业");
        list.add("发送通知");
        if (SharedPrefsUtil.getValue(getActivity(), "teacherXML", "roly", "0").equals("1")) {
            list.add("课程表维护");
            list.add("老师维护");
            list.add("其他");
        }
        adapter.notifyDataSetChanged();
    }

    void getNotify() {
        messageCenter.setCallBackInterFace(this);
        messageCenter.SendYouMessage(messageCenter.ChooseCommand().getnotify(), this);
    }

    @Override
    protected int getLayout() {
        return R.layout.ui_center_indexfragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        DataCenter();

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                stateSet();
            }
        });

        //一件放学按钮

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             /*   Dialog.showDialog(getActivity(), "提示", "点击按钮后将发送放学信息，是否确定？", new mClickInterface() {
                    @Override
                    public void doClick() {

                    }

                    @Override
                    public void doClick(int pos, View vi) {
                    }
                });
*/

                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("确定发送?")
                        .setContentText("确定将发送放学通知!")
                        .setCancelText("再等会!")
                        .setConfirmText("现在放学!")
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                // reuse previous dialog instance, keep widget user state, reset them if you need
                                sDialog.setTitleText("取消!")
                                        .setContentText("取消成功")
                                        .setConfirmText("好")
                                        .showCancelButton(false)
                                        .setCancelClickListener(null)
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {

                                messageCenter.SendYouMessage(messageCenter.ChooseCommand().sendNotify(), indexFragment.this);
                                sDialog.setTitleText("成功!")
                                        .setContentText("放学通知已发送!")
                                        .setConfirmText("好")
                                        .showCancelButton(false)
                                        .setCancelClickListener(null)
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                            }
                        })
                        .show();


            }
        });
        messageCenter = new MessageCenter();
        adapter = new GvAdapter(getActivity(), list, R.layout.utils_grid_item);
        switchClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SwitchClass.class);
                getActivity().startActivity(intent);
            }
        });
        btnDelay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopFormBottom(rootView);
            }
        });
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        showPopFormBottom(rootView);
                        break;
                    case 1://家庭作业
                        inte = new Intent(getActivity(), SendHomeWork.class);
                        inte.putExtra("ConkerData", 2);
                        startActivity(inte);
                        break;

                    case 2://通知列表
                        inte = new Intent(getActivity(), SendHomeWork.class);
                        inte.putExtra("ConkerData", 1);
                        startActivity(inte);
                        break;
                    case 3://通知列表
                        inte = new Intent(getActivity(), SchduleList.class);
                        startActivity(inte);
                        break;
                    case 4://通知列表
                        inte = new Intent(getActivity(), Teachers.class);
                        startActivity(inte);
                        break;
                    case 5://通知列表
                        Toast.FangXueToast(getActivity(), "敬请期待！");
                        break;

                }
            }
        });
        getNotify();
        return rootView;
    }

    public void showPopFormBottom(View view) {
        takePhotoPopWin = new TakePhotoPopWin(getActivity(), new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        }, messageCenter);

        // takePhotoPopWin.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // takePhotoPopWin.showAsDropDown(view.findViewById(R.id.sdrsTxt));

        //动态位置
        takePhotoPopWin.showAtLocation(view.findViewById(R.id.boxMain), Gravity.CENTER | Gravity.LEFT, 50, -40);

        takePhotoPopWin.setInterface(this);
        takePhotoPopWin.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (!deLayStr.isEmpty()) {
                    delayNotify.setText(deLayStr);
                    delayLayout.setVisibility(View.VISIBLE);
                }
            }
        });
//
    }

    private void DataCenter() {
        dataDeal = new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                System.out.print("onError" + e.getMessage());
            }

            @Override
            public void onNext(String s) {
                DealWithData(s);
                //加入缓存中

            }
        };
    }


    void indexFragmentCallBackSet() {

    }

    private void DealWithData(String s) {
        JSONObject cmd = JSONUtils.StringToJSON(s);
        //教师版一键放学
        if (JSONUtils.getString(cmd, "cmd").equals("class.leave")) {
            messageCenter.SendYouMessage(messageCenter.ChooseCommand().getnotify(), this);
            Toast.FangXueToast(getActivity(), JSONUtils.getString(cmd, "message"));
        }
        if (JSONUtils.getString(cmd, "cmd").equals("system.getnotify")) {
            JSONObject data = JSONUtils.getSingleJSON(cmd, "data", 0);
            String classtime = JSONUtils.getString(data, "classtime");
            String timeofplan = JSONUtils.getString(data, "timeofplan");
            if (classtime != null && !classtime.equals("null") && !classtime.isEmpty()) {
                HomeBackTime.setText(classtime);
            } else {
                HomeBackTime.setText("--:--");
            }
            if (timeofplan != null && !JSONUtils.getString(data, "reason").equals("null") && !timeofplan.isEmpty()) {
                String message = "【预计" + timeofplan + "放学】" + ":" + JSONUtils.getString(data, "reason");
                delayNotify.setText(message);
                delayLayout.setVisibility(View.VISIBLE);
            } else {
                delayNotify.setText("");
                delayLayout.setVisibility(View.GONE);
            }

            refresh.setRefreshing(false);
        }
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
                .subscribe(dataDeal);
        Log.e("首页", str);
    }

    @Override
    public void CallMe(String s) {


        deLayStr = s;


    }

    class GvAdapter extends CommonAdapter<String> {


        GvAdapter(Context context, List<String> list, int layoutId) {
            super(context, list, layoutId);
        }

        @Override
        public void setViewContent(CommonViewHolder viewHolder, String s) {


            ImageView imageView = viewHolder.getView(R.id.gv_image);

            switch (s) {
                case "延迟放学":
                    imageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.delay));
                    break;
                case "留作业":
                    imageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.teacher_homework));
                    break;
                case "发送通知":
                    imageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.teacher_notify));
                    break;
                case "课程表维护":
                    imageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.classes));
                    break;
                case "老师维护":
                    imageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.teachers));
                    break;
            }

            viewHolder.setText(R.id.gv_title, s);
        }
    }


}
