package com.android.teacher.ui.Info;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.teacher.R;
import com.android.teacher.base.BaseActivity;
import com.android.teacher.base.CommonAdapter;
import com.android.teacher.base.CommonViewHolder;
import com.android.teacher.callback.MessageCallBack;
import com.android.teacher.callback.mClickInterface;
import com.android.teacher.newwork.MessageCenter;
import com.android.teacher.ui.Center.ActivityCenter;
import com.android.teacher.ui.auth.BindRegisterInfo;
import com.android.teacher.ui.auth.GetPhoneForRegister;
import com.android.teacher.utils.Dialog;
import com.android.teacher.utils.JSONUtils;
import com.android.teacher.utils.SharedPrefsUtil;
import com.android.teacher.utils.Toast;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;

public class SwitchClassFirst extends BaseActivity implements MessageCallBack {

    private Observer<String> observer;
    private MessageCenter messageCenter;
    @Bind(R.id.back_btn)
    ImageView back;
    @Bind(R.id.toolbar_title)
    TextView title;
    @Bind(R.id.rightBtn)
    TextView rightBtn;
    @Bind(R.id.listview_switchclass)
    ListView listView;


    private SwitchClassAsAdatper adapter;
    private List<String> list = new ArrayList<>();
    private SparseArray<String> arrayList = new SparseArray<>();

    @Override
    public void setButterKnife() {

        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        messageCenter.setCallBackInterFace(this);
        messageCenter.SendYouMessage(messageCenter.ChooseCommand().teacher_GetClassList());
    }

    @Override
    public int getLayoutId() {
        return R.layout.ui_info_activity_switch_class;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        rightBtn.setText("新增班级");
        rightBtn.setVisibility(View.VISIBLE);
        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SwitchClassFirst.this, BindRegisterInfo.class));
            }
        });

        back.setVisibility(View.GONE);
        title.setText("我的班级");
        messageCenter = new MessageCenter();
        DealWithData();
        adapter = new SwitchClassAsAdatper(this, list, R.layout.utils_gridview_item);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


            }
        });
    }

    private void DealWithData() {

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
                if (JSONUtils.getString(cmd, "cmd").equals("teacher.getClassList")) {

                    list.clear();
                    JSONArray arr = JSONUtils.getArrInJson(cmd, "data");
                    for (int j = 0; j < arr.length(); j++) {
                        JSONObject ob = JSONUtils.getSingleJSON(cmd, "data", j);
                        arrayList.put(j, JSONUtils.getString(ob, "classid"));
                        list.add(JSONUtils.getString(ob, "classname"));
                    }
                    adapter.notifyDataSetChanged();
                }
                if (JSONUtils.getString(cmd, "cmd").equals("teacher.selectClass")) {
                    JSONObject arr = JSONUtils.getSingleJSON(cmd, "data", 0);
                    SharedPrefsUtil.putValue(SwitchClassFirst.this, "teacherXML", "schoolname", JSONUtils.getString(arr, "schoolname")); //学校名称
                    SharedPrefsUtil.putValue(SwitchClassFirst.this, "teacherXML", "classname", JSONUtils.getString(arr, "classname")); //登錄手機
                    SharedPrefsUtil.putValue(SwitchClassFirst.this, "teacherXML", "lesson", JSONUtils.getString(arr, "lesson")); //登錄课程
                    SharedPrefsUtil.putValue(SwitchClassFirst.this, "teacherXML", "teachername", JSONUtils.getString(arr, "teachername")); //老师名称
                    SharedPrefsUtil.putValue(SwitchClassFirst.this, "teacherXML", "classid", JSONUtils.getString(arr, "classid")); //班级id
                    SharedPrefsUtil.putValue(SwitchClassFirst.this, "teacherXML", "roly", JSONUtils.getString(arr, "adminflag")); //登錄手機
                    startActivity(new Intent(SwitchClassFirst.this, ActivityCenter.class));
                    finish();
                    Toast.FangXueToast(SwitchClassFirst.this, JSONUtils.getString(cmd, "message"));
                }
                if (JSONUtils.getString(cmd, "cmd").equals("class.deleteInfo")) {
                    messageCenter.SendYouMessage(messageCenter.ChooseCommand().teacher_GetClassList());
                    Toast.FangXueToast(SwitchClassFirst.this, JSONUtils.getString(cmd, "message"));
                }


            }
        };
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        return;
    }

    @Override
    public void onMessage(String str) {
        DealMessageForMe(str, observer);
    }

    class SwitchClassAsAdatper extends CommonAdapter<String> {
        private final Context mContext;

        private List<String> mList;

        public SwitchClassAsAdatper(Context context, List<String> list, int layoutId) {
            super(context, list, layoutId);
            this.mContext = context;
            this.mList = list;

        }

        @Override
        public void setViewContent(final CommonViewHolder viewHolder, String s) {

            Button btnEdit = viewHolder.getView(R.id.btnEdit);
            Button btnDelete = viewHolder.getView(R.id.btnDelete);
            SwipeMenuLayout swipeMenuLayout =viewHolder.getView(R.id.SwipeMenuLayout);
            swipeMenuLayout.setSwipeEnable(false);
            final LinearLayout lis = viewHolder.getView(R.id.class_item);
            lis.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pressPos = viewHolder.getPostion();
                    messageCenter.SendYouMessage(messageCenter.ChooseCommand().teacher_SelectClass(Integer.parseInt(arrayList.get(pressPos))));
                }
            });
//            btnDelete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    Dialog.showDialog(mContext, "提示", "是否确认删除？", new mClickInterface() {
//                        @Override
//                        public void doClick() {
//                            int pressPos = viewHolder.getPostion();
//                            messageCenter.SendYouMessage(messageCenter.ChooseCommand().class_deleteInfo(Integer.parseInt(arrayList.get(pressPos))));
//
//                        }
//
//                        @Override
//                        public void doClick(int pos, View vi) {
//
//                        }
//                    });
//                }
//            });
//
//            btnEdit.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                }
//            });

            viewHolder.setText(R.id.gv_title, s );
        }
    }
}
