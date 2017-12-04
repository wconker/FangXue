package com.android.fangxue.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by fangxue on 17/8/9.
 */

public abstract class BaseActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);



        //设置布局内容
        setContentView(getLayoutId());

        //绑定butterkife
        setButterKnife();

        //初始化控件
        initViews(savedInstanceState);

    }


    public abstract void setButterKnife();

    public abstract int getLayoutId();

    protected abstract void initViews(Bundle savedInstanceState);

    protected void DealMessageForMe(String s, Observer observer){
            Observable.just(s)
                    .observeOn(AndroidSchedulers
                            .mainThread())
                    .subscribe(observer);
    }

}
