package com.android.teacher.ui.Info;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.teacher.R;
import com.android.teacher.callback.MessageCallBack;
import com.android.teacher.entity.TeacherBean;
import com.android.teacher.newwork.MessageCenter;
import com.android.teacher.ui.Center.Teachers;
import com.android.teacher.utils.JSONUtils;
import com.android.teacher.utils.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class AddSchedul extends Activity implements MessageCallBack {
    @Bind(R.id.back_btn)
    ImageView backBtn;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.dayName_et)
    EditText dayNameEt;
    @Bind(R.id.coursename_et)
    EditText coursenameEt;
    @Bind(R.id.fromtime_et)
    EditText fromtimeEt;
    @Bind(R.id.to_et)
    EditText toEt;
    @Bind(R.id.section_et)
    EditText sectionEt;
    @Bind(R.id.submit)
    Button submit;
    @Bind(R.id.teacher_et)
    Spinner teacher_et;
    private int teacherid = 0;
    private int witch = 0;
    private MessageCenter messageCenter;
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
            Log.d("课程表时间", b + " ");
            if (witch == 1) {
                fromtimeEt.setText(b);
            } else {
                toEt.setText(b);
            }
        }
    };


    private String toChinese(String string) {
        String[] s1 = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        String[] s2 = {"十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千"};

        String result = "";

        int n = string.length();
        for (int i = 0; i < n; i++) {

            int num = string.charAt(i) - '0';

            if (i != n - 1 && num != 0) {
                result += s1[num] + s2[n - 2 - i];
            } else {
                result += s1[num];
            }
            System.out.println("  " + result);
        }

        return result;

    }

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

            if (JSONUtils.getString(cmd, "cmd").equals("schedule.addInfo")) {
                if (JSONUtils.getInt(cmd, "code", 0) == 1) {
                    finish();
                } else {
                    Toast.FangXueToast(AddSchedul.this, JSONUtils.getString(cmd, "message"));
                }
            }
            if (JSONUtils.getString(cmd, "cmd").equals("schedule.updateInfo")) {
                if (JSONUtils.getInt(cmd, "code", 0) == 1) {
                    finish();
                } else {
                    Toast.FangXueToast(AddSchedul.this, JSONUtils.getString(cmd, "message"));
                }
                Toast.FangXueToast(AddSchedul.this, JSONUtils.getString(cmd, "message"));
            }
            if (JSONUtils.getString(cmd, "cmd").equals("schedule.getinfo")) {

                if (JSONUtils.getInt(cmd, "code", 0) == 1) {
                    JSONObject object = JSONUtils.getSingleJSON(cmd, "data", 0);
                    dayNameEt.setText(JSONUtils.getString(object, "daynumber"));
                    coursenameEt.setText(JSONUtils.getString(object, "coursename"));
                    fromtimeEt.setText(JSONUtils.getString(object, "fromtime"));
                    toEt.setText(JSONUtils.getString(object, "totime"));
                    sectionEt.setText(JSONUtils.getString(object, "sectionname"));
                    TeacherSelectId = JSONUtils.getInt(object, "teacherid", 0);
                    for (int i = 0; i < listForId.size(); i++) {
                        if (listForId.get(i) == TeacherSelectId) {
                            teacher_et.setSelection(i);
                        }
                    }
                } else {
                    Toast.FangXueToast(AddSchedul.this, JSONUtils.getString(cmd, "message"));
                }
            }
            if (JSONUtils.getString(cmd, "cmd").equals("class.getteacherlist")) {
                if (id > 0) {
                    messageCenter.SendYouMessage(messageCenter.ChooseCommand().schedule_getInfo(id), AddSchedul.this);
                }
                if (JSONUtils.getInt(cmd, "code", 0) == 1) {
                    if (!JSONUtils.getString(cmd, "code").isEmpty()) {
                        list.clear();
                        Gson gson = new Gson();
                        Type type = new TypeToken<TeacherBean>() {
                        }.getType();
                        TeacherBean dataBean = gson.fromJson(String.valueOf(cmd), type);
                        for (int i = 0; i < dataBean.getData().size(); i++) {
                            list.add(dataBean.getData().get(i).getTeachername());
                            listForId.add(dataBean.getData().get(i).getTeacherid());
                        }
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.FangXueToast(AddSchedul.this, JSONUtils.getString(cmd, "message"));
                }
            }
        }
    };
    private List<String> list = new ArrayList<>();
    private List<Integer> listForId = new ArrayList<>();
    private int TeacherSelectId = 0;

    private int id;

    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_info_activity_add_schedul);
        id = getIntent().getIntExtra("id", 0);


        ButterKnife.bind(this);


        toEt.setFocusable(false);
        fromtimeEt.setFocusable(false);
        toEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                witch = 2;
                TimePickerDialog d = new TimePickerDialog(AddSchedul.this, mdateListener, 12, 12, true);
                d.show();
            }
        });
        fromtimeEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                witch = 1;
                TimePickerDialog d = new TimePickerDialog(AddSchedul.this, mdateListener, 12, 12, true);
                d.show();
            }
        });
        teacherid = getIntent().getIntExtra("teacherid", 0);
        messageCenter = new MessageCenter();
        messageCenter.setCallBackInterFace(this);

        initView();
//        if (id > 0) {
//            messageCenter.SendYouMessage(messageCenter.ChooseCommand().schedule_getInfo(id), AddSchedul.this);
//        }
        //先获取老师列表在获取详情
        messageCenter.SendYouMessage(messageCenter.ChooseCommand().class_getteacherlist(), AddSchedul.this);
    }

    private void initView() {


        if (id <= 0) {
            toolbarTitle.setText("添加课程表");
            submit.setText("添加");
        } else {
            toolbarTitle.setText("修改课程表");
            submit.setText("修改");
        }


        adapter = new ArrayAdapter(AddSchedul.this, android.R.layout.simple_list_item_1, list);
        teacher_et.setAdapter(adapter);
        teacher_et.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                TeacherSelectId = listForId.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id <= 0) {
                    messageCenter.SendYouMessage(messageCenter.ChooseCommand().schedule_addInfo(
                            TeacherSelectId,
                            dayNameEt.getText().toString(),
                            coursenameEt.getText().toString(),
                            fromtimeEt.getText().toString(),
                            toEt.getText().toString(),
                            sectionEt.getText().toString()
                    ));
                } else {
                    if (TeacherSelectId == 0) {
                        TeacherSelectId = listForId.get(0);
                    }
                    messageCenter.SendYouMessage(messageCenter.ChooseCommand().schedule_updateInfo(
                            id,
                            TeacherSelectId,
                            dayNameEt.getText().toString(),
                            coursenameEt.getText().toString(),
                            fromtimeEt.getText().toString(),
                            toEt.getText().toString(),
                            sectionEt.getText().toString()
                    ));
                }
            }
        });
    }

    @Override
    public void onMessage(String str) {

        Log.e("课程表添加", str);
        Observable.just(str)
                .observeOn(AndroidSchedulers
                        .mainThread())
                .subscribe(observer);
    }

}
