package com.android.teacher.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.teacher.R;
import com.android.teacher.callback.DataBack;
import com.android.teacher.callback.MessageCallBack;
import com.android.teacher.callback.mClickInterface;
import com.android.teacher.newwork.MessageCenter;
import com.android.teacher.ui.Info.AddParent;
import com.android.teacher.ui.Info.AddSchedul;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Observer;

import jp.wasabeef.glide.transformations.BlurTransformation;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.NewThreadScheduler;


/**
 * Created by softsea on 17/11/14.
 */

public class TakePhotoPopWin extends PopupWindow implements MessageCallBack {

    private Context mContext;

    private rx.Observer<String> observer = new rx.Observer<String>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(String s) {
            JSONObject cmd = JSONUtils.StringToJSON(s);
            if (JSONUtils.getString(cmd, "cmd").equals("class.delay")) {
                if (JSONUtils.getInt(cmd, "code", 0) == 1) {

                    dismiss();
                }
                Toast.FangXueToast(mContext, JSONUtils.getString(cmd, "message"));
            }
        }
    };
    private View view;
    TextView btn_take_time;
    private Button btn_ok;
    int mYear, mMonth, mDay, mHour, mMinutte;
    private TimePickerDialog.OnTimeSetListener mdateListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            display(timePicker);
        }
    };
    private DataBack dataBack;

    private String buildStr = "";

    public void setInterface(DataBack back) {
        this.dataBack = back;
    }

    private EditText message_delay;

    public TakePhotoPopWin(final Context mContext, View.OnClickListener itemsOnClick, final MessageCenter messageCenter) {

        final Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
        mHour = ca.get(Calendar.HOUR);
        mMinutte = ca.get(Calendar.MINUTE);
        this.view = LayoutInflater.from(mContext).inflate(R.layout.take_photo_pop, null);
        ImageView gose = view.findViewById(R.id.picforgrose);
        Glide.with(mContext).load(R.mipmap.tclass)
                .bitmapTransform(new BlurTransformation(mContext, 15), new CenterCrop(mContext))
                .into(gose);
        btn_take_time = (TextView) view.findViewById(R.id.SelectTime);
        message_delay = (EditText) view.findViewById(R.id.message_delay);
        btn_ok = (Button) view.findViewById(R.id.btn_ok);
        btn_take_time.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TimePickerDialog d = new TimePickerDialog(mContext, mdateListener, mHour, mMinutte, true);
                d.show();
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog.showDialog(mContext, "提示", "确定发送延迟信息？", new mClickInterface() {
                    @Override
                    public void doClick() {
                        int lengthH = String.valueOf(mHour).length();
                        int lengthM = String.valueOf(mMinutte).length();
                        messageCenter.setCallBackInterFace(TakePhotoPopWin.this);
                        messageCenter.SendYouMessage(messageCenter.ChooseCommand().delay(btn_take_time.getText().toString(), message_delay.getText().toString()));
                        buildStr = "【预计" + (lengthH == 2 ? mHour + "" : "0" + mHour) + ":" + (lengthM == 2 ? mMinutte + "" : "0" + mMinutte) + "放学】" + message_delay.getText().toString();


                    }

                    @Override
                    public void doClick(int pos, View vi) {
                    }
                });

            }
        });
        // 设置外部可点击
        this.setOutsideTouchable(true);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        this.view.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = view.findViewById(R.id.pop_layout).getTop();

                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });


    /* 设置弹出窗口特征 */
        // 设置视图
        this.setContentView(this.view);
        // 设置弹出窗体的宽和高
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);

        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        //窗口的宽度
        int screenWidth = dm.widthPixels - 100;
        this.setWidth(screenWidth);


        // 设置弹出窗体可点击
        this.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);

        // 设置弹出窗体显示时的动画，从底部向上弹出
        this.setAnimationStyle(R.style.take_photo_anim);

    }


    /**
     * 设置日期 利用StringBuffer追加
     */

    private void display(TimePicker timePicker) {
        StringBuilder b = new StringBuilder().
                append(mYear).
                append("-").
                append(mMonth + 1).
                append("-").
                append(mDay).
                append(" ");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            b.append(timePicker.getHour()).append(":").append(timePicker.getMinute());
            mMinutte = timePicker.getMinute();
            mHour = timePicker.getHour();
        } else {
            b.append(timePicker.getCurrentHour()).append(":").append(timePicker.getCurrentMinute());
            mHour = timePicker.getCurrentHour();
            mMinutte = timePicker.getCurrentMinute();
        }
        btn_take_time.setText(b);
    }

    @Override
    public void onMessage(String str) {

        dataBack.CallMe(buildStr);
        Observable.just(str)
                .observeOn(AndroidSchedulers
                        .mainThread())
                .subscribe(observer);


    }
}
