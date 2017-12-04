package com.android.fangxue.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.fangxue.R;

public class NewsInfo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_news_info);
        ImageView back = this.findViewById(R.id.back_btn);
        TextView TITLE = this.findViewById(R.id.toolbar_title);
        TITLE.setText("新闻");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        WebView view = this.findViewById(R.id.webview);
        String url = "http://" + getIntent().getStringExtra("newInfo");
        Log.e("C",url);
        //设置WebView属性，能够执行Javascript脚本
        view.getSettings().setJavaScriptEnabled(true);
        //加载需要显示的网页
        view.loadUrl(url);
    }
}
