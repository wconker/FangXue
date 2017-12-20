package com.android.fangxue.ui.Detail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.fangxue.R;
import com.android.fangxue.base.BaseActivity;
import com.android.fangxue.callback.MessageCallBack;
import com.android.fangxue.newwork.MessageCenter;
import com.android.fangxue.ui.Contact.ContactList;
import com.android.fangxue.utils.JSONUtils;
import com.android.fangxue.utils.Toast;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class FeedBack extends BaseActivity implements MessageCallBack {


    @Bind(R.id.back_btn)
    ImageView backBtn;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.btn_submit)
    Button btn_submit;
    @Bind(R.id.feedContext)
    EditText feedContext;

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

            if (JSONUtils.getString(cmd, "cmd").equals("system.sendfeedback")) {
                if (JSONUtils.getInt(cmd, "code", -1) == 1) {
                    finish();
                    Toast.FangXueToast(FeedBack.this, JSONUtils.getString(cmd, "message"));
                }
                Toast.FangXueToast(FeedBack.this, JSONUtils.getString(cmd, "message"));
            }
        }
    };
    private MessageCenter messageCenter;

    @Override
    public void setButterKnife() {
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back_btn, R.id.btn_submit})
    void click(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
                finish();
                break;
            case R.id.btn_submit:
                messageCenter.SendYouMessage(messageCenter.ChooseCommand().sendfeedback(feedContext.getText().toString()), this);
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

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        toolbarTitle.setText("意见反馈");
        messageCenter = new MessageCenter();

    }

    @Override
    public void onMessage(String str) {
        Log.e("FeedBack", "onMessage: " + str);
        DealMessageForMe(str, observer);
    }
}
