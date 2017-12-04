package com.android.fangxue.ui.Detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.fangxue.R;
import com.android.fangxue.base.BaseActivity;
import com.android.fangxue.ui.Contact.ContactList;
import com.android.fangxue.utils.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedBack extends BaseActivity {


    @Bind(R.id.back_btn)
    ImageView backBtn;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;

    @Override
    public void setButterKnife() {
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back_btn})
    void click(View v) {

        switch (v.getId()) {
            case R.id.back_btn:
                finish();
                break;
            default:
                Toast.FangXueToast(FeedBack.this, "敬请期待");
        }


    }


    @Override
    public int getLayoutId() {
        return R.layout.ui_detail_feed_back;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        toolbarTitle.setText("意见反馈");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
