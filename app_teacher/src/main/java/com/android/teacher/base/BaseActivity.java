package com.android.teacher.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Stack;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by fangxue on 17/8/9.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private static Stack<Activity> AList = new Stack<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        AList.push(this);

        //设置布局内容
        setContentView(getLayoutId());

        //绑定butterkife
        setButterKnife();

        //初始化控件
        initViews(savedInstanceState);


        setMyObserver();
        for (int i = 0; i < AList.size(); i++) {
            Log.e("ShowStack", AList.get(i).getClass().getName() + "个");
        }

    }

    /**
     * 关闭所有(前台、后台)Activity,注意：请已BaseActivity为父类
     */
    protected static void finishAll() {
        int len = AList.size();
        for (int i = 0; i < len; i++) {
            Activity activity = AList.pop();
            activity.finish();
        }
    }

    protected void ThisFinis(){
       AList.remove(this);
       this.finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 从栈中移除当前activity
        if (AList.contains(this)) {
            AList.remove(this);
        }

    }
    public abstract void setButterKnife();

    public abstract int getLayoutId();

    protected abstract void initViews(Bundle savedInstanceState);

    protected void setMyObserver() {

    }


    protected void DealMessageForMe(String s, Observer observer) {
        Observable.just(s)
                .observeOn(AndroidSchedulers
                        .mainThread())
                .subscribe(observer);
    }

}
