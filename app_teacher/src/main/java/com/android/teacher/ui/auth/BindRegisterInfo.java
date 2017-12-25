package com.android.teacher.ui.auth;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.teacher.R;
import com.android.teacher.base.BaseActivity;
import com.android.teacher.base.CommonAdapter;
import com.android.teacher.base.CommonViewHolder;
import com.android.teacher.callback.MessageCallBack;
import com.android.teacher.entity.SchoolBean;
import com.android.teacher.newwork.MessageCenter;
import com.android.teacher.ui.Info.CreateSchool;
import com.android.teacher.ui.Info.SwitchClass;
import com.android.teacher.utils.JSONUtils;
import com.android.teacher.utils.SharedPrefsUtil;
import com.android.teacher.utils.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class BindRegisterInfo extends BaseActivity implements MessageCallBack {

    @Bind(R.id.schoolBox)
    LinearLayout schoolBox;
    @Bind(R.id.schoolList)
    Spinner schoolList;
    @Bind(R.id.rightBtn)
    TextView rightBtn;
    @Bind(R.id.toTime)
    EditText toTime;
    @Bind(R.id.fromTime)
    EditText fromTime;
    @Bind(R.id.leaveStartTime)
    EditText leaveStartTime;
    @Bind(R.id.leaveEndTime)
    EditText leaveEndTime;
    @Bind(R.id.EndStartTime)
    EditText EndStartTime;
    @Bind(R.id.EndEndTime)
    EditText EndEndTime;
    @Bind(R.id.submit)
    Button submit;
    @Bind(R.id.back_btn)
    ImageView back;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    private MessageCenter messageCenter;
    private Observer<String> observer;
    @Bind(R.id.ClassName_et)
    EditText ClassName_et;
    String[] mItems;
    private ArrayAdapter<String> adapter;
    private List<String> list = new ArrayList<>();
    SchoolBean schoolBean;
    @Bind(R.id.gradeSpinner)
    Spinner gradeSpinner;
    private int witch = 0;
    int SCHOOLID = 0, GRADE = 0;
    private String sSelectClassid;

    private TimePickerDialog.OnTimeSetListener mdateListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            StringBuilder b = new StringBuilder();


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                b.append(String.valueOf(timePicker.getHour()).length() == 2 ? timePicker.getHour() : "0" + timePicker.getHour()).append(":").append(String.valueOf(timePicker.getMinute()).length() == 2 ? timePicker.getMinute() : "0" + timePicker.getMinute());
            } else {
                b.append(timePicker.getCurrentHour().toString().length() == 2 ? timePicker.getCurrentHour().toString() : "0" + timePicker.getCurrentHour().toString()).
                        append(":").
                        append(timePicker.getCurrentMinute().toString().length() == 2 ? timePicker.getCurrentMinute().toString() : "0" + timePicker.getCurrentMinute().toString());
            }

            switch (witch) {
                case 1:
                    fromTime.setText(b);
                    break;
                case 2:
                    toTime.setText(b);
                    break;
                case 3:
                    leaveStartTime.setText(b);
                    break;
                case 4:
                    leaveEndTime.setText(b);
                    break;
                case 5:
                    EndStartTime.setText(b);
                    break;
                case 6:
                    EndEndTime.setText(b);
                    break;
            }
        }
    };

    @Override
    public void setButterKnife() {

        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @OnClick({R.id.submit, R.id.back_btn, R.id.rightBtn})
    void onClickMethod(View v) {
        switch (v.getId()) {
            case R.id.submit:

                if (sSelectClassid != null && !sSelectClassid.equals("null")) {
                    messageCenter.SendYouMessage(messageCenter.ChooseCommand().class_updateInfo(
                            ClassName_et.getText().toString(),
                            String.valueOf(GRADE),
                            "",
                            fromTime.getText().toString(),
                            toTime.getText().toString(),
                            leaveStartTime.getText().toString(),
                            leaveEndTime.getText().toString(),
                            EndStartTime.getText().toString(),
                            EndEndTime.getText().toString()
                    ), this);
                } else {
                    int tid = Integer.valueOf(SharedPrefsUtil.getValue(this, "teacherXML", "teacherid", "0"));
                    messageCenter.SendYouMessage(messageCenter.ChooseCommand().class_addInfo(SCHOOLID,
                            GRADE,
                            tid,
                            ClassName_et.getText().toString()));
                }


                break;
            case R.id.rightBtn:
                startActivity(new Intent(BindRegisterInfo.this, CreateSchool.class));
                break;
            case R.id.back_btn:
                ThisFinis();
                break;
        }
    }

    @Override
    protected void setMyObserver() {
        super.setMyObserver();
        observer = new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {


                JSONObject cmd = JSONUtils.StringToJSON(s);
                Gson gson = new Gson();
                switch (JSONUtils.getString(cmd, "cmd")) {
                    case "school.getList":
                        list.clear();
                        Type type = new TypeToken<SchoolBean>() {
                        }.getType();
                        schoolBean = gson.fromJson(s, type);
                        for (int i = 0; i < schoolBean.getData().size(); i++) {
                            list.add(schoolBean.getData().get(i).getSchoolname());
                        }
                        adapter.notifyDataSetChanged();
                        break;
                    case "class.updateInfo":

                        if (JSONUtils.getInt(cmd, "code", -1) == 1) {
                            SharedPrefsUtil.putValue(BindRegisterInfo.this, "teacherXML", "classname", ClassName_et.getText().toString()); //登錄手機

                            finish();
                        }
                        Toast.FangXueToast(BindRegisterInfo.this, JSONUtils.getString(cmd, "message"));
                        break;
                    case "class.addInfo":

                        if (JSONUtils.getInt(cmd, "code", -1) == 1) {
                            finish();
                        }
                        Toast.FangXueToast(BindRegisterInfo.this, JSONUtils.getString(cmd, "message"));
                        break;

                    case "class.getinfo":

                        if (JSONUtils.getInt(cmd, "code", -1) == 1) {

                            JSONObject object = JSONUtils.getSingleJSON(cmd, "data", 0);
                            schoolList.setSelection(Integer.valueOf(JSONUtils.getString(object, "schoolid")));
                            gradeSpinner.setSelection(Integer.valueOf(JSONUtils.getString(object, "grade")) - 1);
                            ClassName_et.setText(JSONUtils.getString(object, "classname"));
                            fromTime.setText(JSONUtils.getString(object, "sintimefrom"));
                            toTime.setText(JSONUtils.getString(object, "sintimeto"));
                            EndStartTime.setText(JSONUtils.getString(object, "souttimefrom"));
                            EndEndTime.setText(JSONUtils.getString(object, "souttimeto"));
                            leaveStartTime.setText(JSONUtils.getString(object, "touttimefrom"));
                            leaveEndTime.setText(JSONUtils.getString(object, "touttimeto"));
                        }

                        break;
                }


            }
        };
    }

    @Override
    public int getLayoutId() {
        return R.layout.ui_auth_bind_register_info;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        // 建立数据源
        messageCenter = new MessageCenter();
        sSelectClassid = getIntent().getStringExtra("classid");

        if (sSelectClassid != null && !sSelectClassid.equals("null")) {
            schoolBox.setVisibility(View.GONE);
            messageCenter.SendYouMessage(messageCenter.ChooseCommand().class_getclassInfo(sSelectClassid), this);
            submit.setText("修改");

            toolbarTitle.setText("修改班级");
        } else {
            rightBtn.setVisibility(View.VISIBLE);
            rightBtn.setText("新增学校");
            toolbarTitle.setText("新增班级");
            messageCenter.SendYouMessage(messageCenter.ChooseCommand().School_getList(), this);
        }

        initController();

    }

    private void initController() {


        fromTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                witch = 1;
                TimePickerDialog d = new TimePickerDialog(BindRegisterInfo.this, mdateListener, 12, 12, true);
                d.show();

            }
        });
        toTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                witch = 2;
                TimePickerDialog d = new TimePickerDialog(BindRegisterInfo.this, mdateListener, 12, 12, true);
                d.show();

            }
        });
        leaveStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                witch = 3;
                TimePickerDialog d = new TimePickerDialog(BindRegisterInfo.this, mdateListener, 12, 12, true);
                d.show();

            }
        });
        leaveEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                witch = 4;
                TimePickerDialog d = new TimePickerDialog(BindRegisterInfo.this, mdateListener, 12, 12, true);
                d.show();

            }
        });
        EndStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                witch = 5;
                TimePickerDialog d = new TimePickerDialog(BindRegisterInfo.this, mdateListener, 12, 12, true);
                d.show();

            }
        });
        EndEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                witch = 6;
                TimePickerDialog d = new TimePickerDialog(BindRegisterInfo.this, mdateListener, 12, 12, true);
                d.show();

            }
        });




        gradeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                GRADE = Integer.valueOf(BindRegisterInfo.this.getResources().getStringArray(R.array.grade)[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        schoolList.setAdapter(adapter);
        schoolList.setSelection(2, true);
        schoolList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                SCHOOLID = schoolBean.getData().get(pos).getSchoolid();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
    }


    @Override
    public void onMessage(String str) {
        DealMessageForMe(str, observer);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    class SpinerAdapter extends CommonAdapter<SchoolBean.DataBean> {

        public SpinerAdapter(Context context, List<SchoolBean.DataBean> list, int layoutId) {
            super(context, list, layoutId);
        }

        @Override
        public void setViewContent(CommonViewHolder viewHolder, SchoolBean.DataBean dataBean) {
            viewHolder.setText(R.id.gv_title, dataBean.getSchoolname());
        }
    }

}
