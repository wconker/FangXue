package com.android.fangxue.ui.Detail;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.fangxue.R;
import com.android.fangxue.base.BaseActivity;
import com.android.fangxue.callback.MessageCallBack;
import com.android.fangxue.newwork.MessageCenter;
import com.android.fangxue.utils.JSONUtils;
import com.android.fangxue.utils.SharedPrefsUtil;
import com.android.fangxue.utils.Toast;
import com.android.fangxue.utils.UpdateLoaclData;
import com.android.fangxue.widget.CircleImageView;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class ParentsInfo extends BaseActivity implements MessageCallBack {


    @Bind(R.id.back_btn)
    ImageView backBtn;
    @Bind(R.id.tx)
    CircleImageView tx;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.paremtName_et)
    EditText paremtNameEt;
    @Bind(R.id.parentMobile_et)
    EditText parentMobileEt;
    @Bind(R.id.submit)
    Button submit;
    @Bind(R.id.parentRelationship)
    Spinner parentRelationship;
    private MessageCenter messageCenter;
    private Observer<String> observer;
    private String RelationshipStr = "";
    String[] array;

    @Override
    public void setButterKnife() {
        ButterKnife.bind(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.ui_detail_parents_info;
    }


    @Override
    protected void initViews(Bundle savedInstanceState) {

        array = getResources().getStringArray(R.array.relationship);

        messageCenter = new MessageCenter();
        messageCenter.setCallBackInterFace(this);


        toolbarTitle.setText("信息设置");
        paremtNameEt.setText(SharedPrefsUtil.getValue(this, "userXML", "parentname", ""));
        parentMobileEt.setText(SharedPrefsUtil.getValue(this, "userXML", "mobile", ""));

        String relaTemp = SharedPrefsUtil.getValue(this, "userXML", "relationship", "");
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(relaTemp)) {
                parentRelationship.setSelection(i, true);// 默认选中项

            }
        }


        parentRelationship.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                RelationshipStr = array[i];

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        observer = new Observer<String>() {
            @Override
            public void onCompleted() {

            }
            @Override
            public void onError(Throwable e) {

            }
            @Override
            public void onNext(String s) {
                Log.e("s", s);
                JSONObject cmd = JSONUtils.StringToJSON(s);
                if (JSONUtils.getString(cmd, "cmd").equals("system.parentupdate")) {


                    if (JSONUtils.getInt(cmd, "code", -1) == 1) {
                        UpdateLoaclData.setParentInfoForMoble(ParentsInfo.this, parentMobileEt.getText().toString());
                        UpdateLoaclData.setParentInfoForName(ParentsInfo.this, paremtNameEt.getText().toString());
                        UpdateLoaclData.setParentInfoForRelationship(ParentsInfo.this, RelationshipStr);

                        finish();
                    }
                    Toast.FangXueToast(ParentsInfo.this, JSONUtils.getString(cmd, "message"));
                }

            }
        };

    }


    @OnClick({R.id.submit, R.id.back_btn})
    public void onViewClicked(View v) {

        if (v.getId() == R.id.submit) {
            if (!paremtNameEt.getText().toString().isEmpty() && !parentMobileEt.getText().toString().isEmpty()) {

                //提交发送协议
                messageCenter.SendYouMessage(messageCenter.ChooseCommand().updateInfo(Integer.valueOf(SharedPrefsUtil.getValue(this,
                        "userXML", "studentid", "0")),
                        Integer.valueOf(SharedPrefsUtil.getValue(this, "userXML", "userId", "0")),
                        paremtNameEt.getText().toString(), parentMobileEt.getText().toString(), RelationshipStr));
            } else {
                Toast.FangXueToast(this, "请检查是否有不完整信息！");
            }
        } else { //返回按钮
            this.finish();

        }
    }

    @Override
    public void onMessage(String str) {
        DealMessageForMe(str, observer);
    }
}
