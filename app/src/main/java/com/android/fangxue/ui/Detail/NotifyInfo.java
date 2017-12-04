package com.android.fangxue.ui.Detail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.fangxue.R;
import com.android.fangxue.adapter.Holder.CommadHolder;
import com.android.fangxue.adapter.commonAdapter.RecycCommomAdapter;
import com.android.fangxue.adapter.commonAdapter.RecycCommomAdapter_Two;
import com.android.fangxue.base.BaseActivity;
import com.android.fangxue.base.BaseHolder;
import com.android.fangxue.base.BaseHolder_two;
import com.android.fangxue.callback.MessageCallBack;
import com.android.fangxue.entity.Homework;
import com.android.fangxue.entity.HomeworkDetail;
import com.android.fangxue.newwork.MessageCenter;
import com.android.fangxue.utils.JSONUtils;
import com.android.fangxue.utils.photoPickerUtil.PhotoT;
import com.android.fangxue.widget.CircleImageView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class NotifyInfo extends BaseActivity implements MessageCallBack {


    @Bind(R.id.back_btn)
    ImageView back;
    @Bind(R.id.toolbar_title)
    TextView title;
    @Bind(R.id.hoemwork_title)
    TextView hoemwork_title;
    @Bind(R.id.hoemwork_date)
    TextView hoemwork_date;
    @Bind(R.id.homework_author)
    TextView homework_author;
    @Bind(R.id.message_content)
    TextView message_content;
    @Bind(R.id.homework_teachername)
    TextView homework_teachername;
    @Bind(R.id.icon_font)
    CircleImageView icon_font;
    @Bind(R.id.photo)
    PhotoT photo;
    @Bind(R.id.reviewList)
    RecyclerView reviewList;
    private NotifyAdapater adapater;
    List<String> list = new ArrayList<>();
    Context contex = this;

    private int notifyId = 0;
    private MessageCenter messageCenter;
    private Observer<String> observer;
    private ArrayList<String> imgc = new ArrayList<>();
    private ArrayList<String> preview = new ArrayList<>();

    @Override
    public void setButterKnife() {
        ButterKnife.bind(this);

    }

    @Override
    public int getLayoutId() {
        return R.layout.ui_info_activity_notify_info;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {


        title.setText("详情");
        notifyId = getIntent().getIntExtra("notify", 0);
        messageCenter = new MessageCenter();
        SimulationData();
        messageCenter.setCallBackInterFace(this);
        messageCenter.SendYouMessage(messageCenter.ChooseCommand().getmessageinfo_HomeWork(notifyId));
        adapater = new NotifyAdapater(this, list);
        reviewList.setNestedScrollingEnabled(false);
        reviewList.setAdapter(adapater);
        reviewList.setLayoutManager(new LinearLayoutManager(this));
        setObserver();
    }


    public void setObserver() {
        observer = new Observer<String>() {
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
    }

    /* 評論模塊內容預留*/
    void SimulationData() {
        list.add("张三");
        list.add("李四");
        list.add("王五");
        list.add("赵六");
        list.add("其他");
    }

    private void DealWithData(String s) {
        JSONObject cmd = JSONUtils.StringToJSON(s);

        if (JSONUtils.getString(cmd, "cmd").equals("message.getinfo")) {
            Gson gson = new Gson();
            Type type = new TypeToken<HomeworkDetail>() {
            }.getType();
            //设置具体信息
            HomeworkDetail homeworData = gson.fromJson(s, type);
            setValue(homeworData);
        }
    }

    private void setValue(HomeworkDetail homeworkBean) {
        HomeworkDetail.DataBean dataBean = homeworkBean.getData().get(0);
        hoemwork_title.setText(dataBean.getWorktitle());
        homework_author.setText(dataBean.getAuthor());
        photo.setActivity(this);
        message_content.setText(dataBean.getWorkcontent());
        hoemwork_date.setText(dataBean.getReleasetime());

        for (int i = 0; i < dataBean.getPic().size(); i++) {
            preview.add(dataBean.getPic().get(i).getPicpath());
            imgc.add(dataBean.getPic().get(i).getThumbnail());
        }
        photo.setUrl(imgc);
        photo.setPreviewPhoto(preview);
        if (!dataBean.getHeaderimg().isEmpty() && dataBean.getHeaderimg() != null) {
            Glide.with(NotifyInfo.this).load(dataBean.getHeaderimg()).into(icon_font);
        } else {
            Glide.with(NotifyInfo.this).load(R.drawable.username).into(icon_font);
        }
    }

    @OnClick({R.id.back_btn})
    void Onclick(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
                this.finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("通知详情页面销毁了", "");
    }

    @Override
    public void onMessage(String str) {
        Log.e("NotifyInfo", str);
        DealMessageForMe(str, observer);
    }

    class NotifyAdapater extends RecycCommomAdapter_Two {
        private final Context mContext;

        NotifyAdapater(Context context, List<String> dataList) {
            super(context, dataList);
            this.mContext = context;
        }

        @Override
        public BaseHolder_two setViewHolder(ViewGroup parent) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.commad_item, parent, false);
            return new CommadHolder(view);
        }
    }

}
