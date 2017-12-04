package com.android.fangxue.ui.Detail;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.fangxue.base.BaseActivity;
import com.android.fangxue.callback.MessageCallBack;
import com.android.fangxue.entity.note.ProBean;
import com.android.fangxue.newwork.MessageCenter;
import com.android.fangxue.R;
import com.android.fangxue.utils.JSONUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class EditNode extends BaseActivity implements View.OnClickListener,MessageCallBack {


    @Bind(R.id.back_btn)
    ImageView backBtn;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.rightBtn)
    TextView rightBtn;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @Bind(R.id.editText)
    EditText editText;
    @Bind(R.id.timeSp)
    Button btn;
    @Bind(R.id.worktime)
    EditText worktime;
    private MessageCenter messageCenter;

    private AppCompatSpinner pro;
    private ProBean proBean;
    private Context mContext = this;
    private ArrayAdapter arrayAdapter;
    private Observer<String> observer;
    private ArrayAdapter adapter;
    private List<String> list = new ArrayList<>();
    final int DATE_DIALOG = 1;
    private String proId;
    int mYear, mMonth, mDay;

    @Override
    public void setButterKnife() {
        ButterKnife.bind(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.ui_node_edit_node;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        messageCenter =new MessageCenter(this);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG);
            }
        });

        final Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);

        rightBtn.setVisibility(View.VISIBLE);
        rightBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        toolbarTitle.setText("日志编写");
        pro = (AppCompatSpinner) this.findViewById(R.id.proSp);

        observer = new Observer<String>() {
            @Override
            public void onCompleted() {

            }
            @Override
            public void onError(Throwable e) {
                Log.e("Conker", e.getMessage() + "");
            }
            @Override
            public void onNext(String s) {
                JSONObject cmd = JSONUtils.StringToJSON(s);
                if (JSONUtils.getString(cmd, "cmd").equals("addWorkingLog")) {
                    addResult(JSONUtils.getString(cmd, "code"), JSONUtils.getString(cmd, "message"));
                }
                if (JSONUtils.getString(cmd, "cmd").equals("getLastProjectList")) {
                    setData(s);
                }
            }
        };

    }



    /**
     * 设置日期 利用StringBuffer追加
     */

    public void display() {
        btn.setText(new StringBuffer().
                append(mYear).
                append("-").
                append(mMonth + 1).
                append("-").
                append(mDay).
                append(" "));
    }

    private DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            display();
        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG:
                return new DatePickerDialog(mContext, mdateListener, mYear, mMonth, mDay);
        }
        return null;
    }

    void addResult(String code, String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
        if (code.equals("0")) {
            EditNode.this.finish();
        } else {

        }
    }

    void setData(String s) {
        JSONObject cmd = JSONUtils.StringToJSON(s);
        if (JSONUtils.getString(cmd, "cmd").equals("getLastProjectList")) {
            Gson gson = new Gson();
            Type type = new TypeToken<ProBean>() {
            }.getType();
            proBean = gson.fromJson(String.valueOf(cmd), type);
            for (ProBean.DataBean a : proBean.getData()) {
                list.add(a.getXmmc());
            }
            Log.e("Conker", list + "的数量==" + list.size());
            //传入的参数分别为 Context , 未选中项的textview , 数据源List
            adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, list);
            //第三步：为适配器设置下拉列表下拉时的菜单样式。
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //第四步：将适配器添加到下拉列表上
            pro.setAdapter(adapter);
            pro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//选择item的选择点击监听事件
                public void onItemSelected(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {
                    // TODO Auto-generated method stub
                    // 将所选mySpinner 的值带入myTextView 中
                    proId = proBean.getData().get(arg2).getXmbh();
                }
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub

                }
            });

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("onDestroy","被摧毁了");
    }

    private void submit() {

        Log.e("提交的信息", btn.getText() + "&" + editText.getText() + "&&" + proId + "&&" + worktime.getText().toString());
        messageCenter.SendYouMessage(messageCenter.ChooseCommand().addWorkingLog(editText.getText().toString(),
                btn.getText().toString(),
                worktime.getText().toString(),
                proId));
    }

    @Override
    public void onMessage(String str) {

        Log.e("Conker", "EditNode"  + mContext.getClass().getName()+"====");
        Observable.just(str)
                .observeOn(AndroidSchedulers
                        .mainThread())
                .subscribe(observer);

    }


    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.rightBtn:
                submit();
                break;
            case R.id.back_btn:
                finish();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

    }
}
