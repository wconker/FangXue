package com.android.fangxue.ui.Auth;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.fangxue.R;
import com.android.fangxue.base.BaseActivity;
import com.android.fangxue.callback.MessageCallBack;
import com.android.fangxue.entity.SchoolBean;
import com.android.fangxue.newwork.MessageCenter;
import com.android.fangxue.ui.Auth.fragments.res_createChild;
import com.android.fangxue.ui.Auth.fragments.res_schoolOrclass;
import com.android.fangxue.ui.Contact.ContactListFist;
import com.android.fangxue.utils.JSONUtils;
import com.android.fangxue.utils.Toast;
import com.android.fangxue.widget.CircleImageView;
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

public class Guide_Res extends BaseActivity implements MessageCallBack {


    @Bind(R.id.back_btn)
    ImageView backBtn;
    @Bind(R.id.tx)
    CircleImageView tx;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.rightBtn)
    TextView rightBtn;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @Bind(R.id.rightB)
    Button rightB;
    @Bind(R.id.leftB)
    Button leftB;
    @Bind(R.id.rightF)
    Button rightF;
    @Bind(R.id.containerBox)
    FrameLayout containerBox;
    private List<String> list = new ArrayList<>();
    private SchoolBean schoolBean;
    private ArrayAdapter<String> adapter;
    private final String TAG = "c";
    int SCHOOLID = 0, GRADE = 0, CLASSID = 0; //保存选中的班级和学校
    private List<android.support.v4.app.Fragment> fragments = new ArrayList<>();
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


            JSONObject cmd = JSONUtils.StringToJSON(s);
            Gson gson = new Gson();
            switch (JSONUtils.getString(cmd, "cmd")) {
                case "class.addInfo":
                    if (JSONUtils.getInt(cmd, "code", -1) == 1) {
                        JSONObject data = JSONUtils.getSingleJSON(cmd, "data", 0);
                        CLASSID = JSONUtils.getInt(data, "classid", 0);
                        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                        android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();
                        transaction.replace(R.id.containerBox, fragments.get(1));
                        transaction.hide(fragments.get(0));
                        transaction.show(fragments.get(1));
                        rightB.setVisibility(View.GONE);
                        rightF.setVisibility(View.VISIBLE);
                        transaction.commit();
                    } else {
                        Toast.FangXueToast(Guide_Res.this, JSONUtils.getString(cmd, "message", ""));
                    }

                    break;
                case "student.addInfo":
                    if (JSONUtils.getInt(cmd, "code", 0) == 1) {
                        startActivity(new Intent(Guide_Res.this, ContactListFist.class));
                        finish();
                        Toast.FangXueToast(Guide_Res.this, JSONUtils.getString(cmd, "message"));
                    } else {
                        Toast.FangXueToast(Guide_Res.this, JSONUtils.getString(cmd, "message"));
                    }
                    break;
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

            }


        }
    };

    @Override
    public void setButterKnife() {
        ButterKnife.bind(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.ui_auth_activity_guide__res;
    }

    private res_schoolOrclass resSchoolOrclass = new res_schoolOrclass();
    private res_createChild resCreateChild = new res_createChild();

    @Override
    protected void initViews(Bundle savedInstanceState) {
        messageCenter = new MessageCenter();
        fragments.add(resSchoolOrclass);
        fragments.add(resCreateChild);
        toolbarTitle.setText("信息完善");
        DefaultFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();
        messageCenter.SendYouMessage(messageCenter.ChooseCommand().School_getList(), this);
        setSchoolValue();
    }

    void setSchoolValue() {
        Spinner schoolList = resSchoolOrclass.schoolList;
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
        resSchoolOrclass.gradeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                GRADE = Integer.valueOf(Guide_Res.this.getResources().getStringArray(R.array.grade)[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @OnClick({R.id.leftB, R.id.rightB, R.id.back_btn, R.id.rightF})
    void clickMethod(View view) {

        switch (view.getId()) {
            case R.id.rightB:

                AddClass();
                break;
            case R.id.leftB:
                android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.containerBox, fragments.get(0));
                transaction.hide(fragments.get(1));
                transaction.show(fragments.get(0));
                leftB.setEnabled(false);
                rightB.setEnabled(true);
                rightB.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                leftB.setBackgroundColor(getResources().getColor(R.color.gray));
                transaction.commit();
                break;
            case R.id.back_btn:
                finish();
                break;
            case R.id.rightF:
                AddStudent();
                break;
        }

    }


    void AddStudent() {

        messageCenter.SendYouMessage(messageCenter.ChooseCommand().student_addInfo(
                CLASSID,
                resCreateChild.studentnoEt.getText().toString(),
                resCreateChild.studentNameEt.getText().toString()));
    }

    void AddClass() {


        Log.e(TAG, SCHOOLID + "===" + GRADE + "++" + resSchoolOrclass.ClassNameEt.getText().toString());
        messageCenter.SendYouMessage(messageCenter.ChooseCommand().class_addInfo(
                SCHOOLID,
                GRADE,
                resSchoolOrclass.ClassNameEt.getText().toString()));
    }

    void DefaultFragment() {
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.containerBox, fragments.get(0));
        transaction.commit();
    }

    @Override
    public void onMessage(String str) {

        DealMessageForMe(str, observer);
    }

}
