package com.android.teacher.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.teacher.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AboutUs extends Activity {

    @Bind(R.id.back_btn)
    ImageView backBtn;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.web)
    WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ButterKnife.bind(this);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbarTitle.setText("关于我们");
        String url = "http://fangxue.56pt.cn/fx/AboutUs/teacher.html";
        //设置WebView属性，能够执行Javascript脚本
        web.getSettings().setJavaScriptEnabled(true);
        //加载需要显示的网页
        web.loadUrl(url);
    }
}
