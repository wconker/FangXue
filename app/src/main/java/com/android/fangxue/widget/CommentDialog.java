package com.android.fangxue.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


import com.android.fangxue.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wukanghui on 2017/12/20.
 */

public class CommentDialog extends Dialog  implements View.OnClickListener  {
    @Bind(R.id.tv_commit)
    TextView tv_commit;//提交
    @Bind(R.id.et_comment)
    EditText et_comment;//评论内容
    @Bind(R.id.tv_location)
    TextView tv_location;//定位
    @Bind(R.id.view_line)
    View view_line;//竖线
    @Bind(R.id.ib_delete)
    ImageButton ib_delete;//删除按钮
    @Bind(R.id.cb_anonymous)
    CheckBox cb_anonymous;//匿名
    private Context context;
    private OnCommitListener listener;

    public CommentDialog(Context context) {
        this(context, R.style.inputDialog);
        this.context = context;
    }

    public CommentDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_dialog_layout);
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        //设置显示对话框时的返回键的监听
        this.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getRepeatCount() == 0)
                    CommentDialog.this.cancel();
                return false;
            }
        });
        //设置EditText内容改变的监听
        et_comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (!TextUtils.isEmpty(s)) {
//                    tv_commit.setBackgroundDrawable(
//                            DrawableUtil.getImageDrawable(context, R.drawable.comment_dialog_btn_process_shape));
//                    tv_commit.setClickable(true);
//                } else {
//                    tv_commit.setBackgroundDrawable(
//                            DrawableUtil.getImageDrawable(context, R.drawable.comment_dialog_btn_normal_shape));
//                    tv_commit.setClickable(false);
//                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        //匿名
        cb_anonymous.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (null != listener) {
                    listener.onAnonymousChecked(buttonView, isChecked);
                }
            }
        });
        tv_commit.setOnClickListener(this);//提交
        tv_location.setOnClickListener(this);//定位
        ib_delete.setOnClickListener(this);//删除
    }

    public void setOnCommitListener(OnCommitListener listener) {
        this.listener = listener;
    }

    public interface OnCommitListener {

        void onCommit(EditText et, View v);//提交数据

        void onGetLocation();//定位

        void onDeleteLocation();//删除定位

        void onAnonymousChecked(CompoundButton buttonView, boolean isChecked);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_commit:
                if (null != listener) {
                    listener.onCommit(et_comment, v);
                }
                break;
            case R.id.tv_location:
                if (null != listener) {
                    listener.onGetLocation();
                }
                break;
            case R.id.ib_delete:
                if (null != listener) {
                    listener.onDeleteLocation();
                }
                break;
        }
    }

    public void setLocationState(boolean state, String str) {
        if (state) {//定位状态
//            tv_location.setCompoundDrawables(DrawableUtil.
//                    setDrawableLeft(context, R.mipmap.icon_location_bg_pressed), null, null, null);
//            view_line.setVisibility(View.VISIBLE);
//            ib_delete.setVisibility(View.VISIBLE);
//            tv_location.setText(str);
        } else {
//            //设置drawableLeft
//            tv_location.setCompoundDrawables(DrawableUtil.
//                    setDrawableLeft(context, R.mipmap.icon_location_bg), null, null, null);
//            view_line.setVisibility(View.GONE);
//            ib_delete.setVisibility(View.GONE);
//            tv_location.setText("点击获取位置");
        }
    }
}
