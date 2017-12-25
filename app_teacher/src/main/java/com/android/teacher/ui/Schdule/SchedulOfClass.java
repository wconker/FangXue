package com.android.teacher.ui.Schdule;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.teacher.R;
import com.android.teacher.base.CommonAdapter;
import com.android.teacher.base.CommonViewHolder;
import com.android.teacher.callback.MessageCallBack;
import com.android.teacher.callback.mClickInterface;
import com.android.teacher.entity.MessageDetailBean;
import com.android.teacher.entity.scheduleofclass;
import com.android.teacher.newwork.MessageCenter;
import com.android.teacher.ui.Info.AddTeacher;
import com.android.teacher.ui.Info.SwitchClass;
import com.android.teacher.utils.Dialog;
import com.android.teacher.utils.JSONUtils;
import com.android.teacher.utils.SharedPrefsUtil;
import com.android.teacher.utils.Toast;
import com.android.teacher.widget.CircleImageView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class SchedulOfClass extends Activity implements MessageCallBack {


    @Bind(R.id.back_btn)
    ImageView backBtn;

    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.rightBtn)
    TextView rightBtn;

    @Bind(R.id.schedullist)
    ListView schedullist;
    @Bind(R.id.refresh)
    SwipeRefreshLayout refresh;
    private int FULLDATA=100;
    private ClassAdatper adapter;
    private List<scheduleofclass.DataBean> list = new ArrayList<>();
    private MessageCenter message;
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
            if (JSONUtils.getInt(cmd, "code", 0) == 1) {
                if (JSONUtils.getString(cmd, "cmd").equals("schedule.getClassList")) {
                    list.clear();
                    JSONArray arr = JSONUtils.getArrInJson(cmd, "data");
                    Gson gson = new Gson();
                    Type type = new TypeToken<scheduleofclass>() {
                    }.getType();
                    scheduleofclass dataBean = gson.fromJson(String.valueOf(cmd), type);
                    list.addAll(dataBean.getData());
                    adapter.notifyDataSetChanged();
                }
                if (JSONUtils.getString(cmd, "cmd").equals("schedule.copyfromclass")) {
                    finish();
                    Toast.FangXueToast(SchedulOfClass.this, JSONUtils.getString(cmd, "message"));
                }

            } else {
                Toast.FangXueToast(SchedulOfClass.this, JSONUtils.getString(cmd, "message"));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_schdule_activity_schedul_of_class);
        ButterKnife.bind(this);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbarTitle.setText("班级列表");
        initView();
        message = new MessageCenter();
        message.setCallBackInterFace(this);
        message.SendYouMessage(message.ChooseCommand().schedule_getClassList());

    }

    private void initView() {

        adapter = new SchedulOfClass.ClassAdatper(this, list, R.layout.utils_gridview_item);
        adapter.setInterface(new mClickInterface() {
            @Override
            public void doClick() {

            }

            @Override
            public void doClick(final int pos, View vi) {


                int Flag = getIntent().getIntExtra("data",0);
                if(Flag==FULLDATA) {
                    Dialog.showDialog(SchedulOfClass.this, "提示", "当前操作将会清空现有课程表，是否继续？", new mClickInterface() {
                        @Override
                        public void doClick() {

                            message.SendYouMessage(message.ChooseCommand().schedule_copyfromclass(pos));

                        }

                        @Override
                        public void doClick(int pos, View vi) {

                        }
                    });

                }else {
                    message.SendYouMessage(message.ChooseCommand().schedule_copyfromclass(pos));

                }
            }
        });
        schedullist.setAdapter(adapter);
    }

    @Override
    public void onMessage(String str) {

        Observable.just(str)
                .observeOn(AndroidSchedulers
                        .mainThread())
                .subscribe(observer);

        Log.e("课程库里", str);
    }

    class ClassAdatper extends CommonAdapter<scheduleofclass.DataBean> {
        private final Context mContext;
        private mClickInterface clickInterface;

        ClassAdatper(Context context, List<scheduleofclass.DataBean> list, int layoutId) {
            super(context, list, layoutId);
            this.mContext = context;
        }

        public void setInterface(mClickInterface clickInterface) {
            this.clickInterface = clickInterface;
        }

        @Override
        public void setViewContent(final CommonViewHolder viewHolder, final scheduleofclass.DataBean dataBean) {
            SwipeMenuLayout swipeMenuLayout = viewHolder.getView(R.id.SwipeMenuLayout);
            LinearLayout classItem = viewHolder.getView(R.id.class_item);
            swipeMenuLayout.setSwipeEnable(false);
            classItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickInterface.doClick(dataBean.getClassid(), null);
                }
            });
            viewHolder.setText(R.id.gv_title, dataBean.getClassname() + "班");
        }


    }
}
