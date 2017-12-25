package com.android.teacher.ui.Info;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.teacher.R;
import com.android.teacher.base.BaseActivity;
import com.android.teacher.callback.MessageCallBack;
import com.android.teacher.newwork.MessageCenter;
import com.android.teacher.utils.JSONUtils;
import com.android.teacher.utils.Toast;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;

public class CreateSchool extends BaseActivity implements MessageCallBack {


    @Bind(R.id.back_btn)
    ImageView backBtn;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.rightBtn)
    TextView rightBtn;
    @Bind(R.id.schoolName_et)
    EditText schoolNameEt;
    @Bind(R.id.submit)
    Button submit;
    private MessageCenter messageCenter;
    private Observer<String> observer = new Observer<String>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(String s) {

            DealWithData(s);
        }
    };

    private void DealWithData(String s) {


        JSONObject cmd = JSONUtils.StringToJSON(s);
        if (JSONUtils.getString(cmd, "cmd").equals("school.addinfo")) {

            if (JSONUtils.getInt(cmd, "code", -1) == 1) {

                finish();
            }


            Toast.FangXueToast(this, JSONUtils.getString(cmd, "message"));

        }

    }

    @Override
    public void setButterKnife() {

        ButterKnife.bind(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.ui_info_activity_create_school;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        toolbarTitle.setText("新增学校");

        messageCenter = new MessageCenter(this);
        messageCenter.setCallBackInterFace(this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messageCenter.SendYouMessage(messageCenter.ChooseCommand().school_addinfo(schoolNameEt.getText().toString()));
            }
        });

    }

    @Override
    public void onMessage(String str) {
        DealMessageForMe(str, observer);

    }


}
