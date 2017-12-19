package com.android.fangxue.ui.Detail;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.fangxue.R;
import com.android.fangxue.callback.MessageCallBack;
import com.android.fangxue.newwork.MessageCenter;
import com.android.fangxue.widget.CircleImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class ReSetPwd extends Activity implements MessageCallBack, Observer<String> {


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
    @Bind(R.id.pwd)
    EditText pwd;
    @Bind(R.id.re_pwd)
    EditText rePwd;
    @Bind(R.id.et_code)
    EditText etCode;
    @Bind(R.id.yzCode)
    Button yzCode;
    @Bind(R.id.login_ll)
    LinearLayout loginLl;
    @Bind(R.id.update_pwd)
    Button updatePwd;

    private MessageCenter messageCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_activity_re_set_pwd);
        ButterKnife.bind(this);
        messageCenter=new MessageCenter();
    }

    @Override
    public void onMessage(String str) {
        Observable.just(str)
                .observeOn(AndroidSchedulers
                        .mainThread())
                .subscribe(ReSetPwd.this);
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(String s) {



    }
}
