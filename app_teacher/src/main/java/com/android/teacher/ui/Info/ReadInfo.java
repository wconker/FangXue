package com.android.teacher.ui.Info;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.teacher.R;
import com.android.teacher.adapter.Holder.CommadHolder;
import com.android.teacher.adapter.Holder.Reader;
import com.android.teacher.adapter.commonAdapter.RecycCommomAdapter;
import com.android.teacher.base.BaseActivity;
import com.android.teacher.base.BaseHolder;
import com.android.teacher.callback.MessageCallBack;
import com.android.teacher.callback.mClickInterface;
import com.android.teacher.entity.HomeworkDetail;
import com.android.teacher.entity.ReaderBean;
import com.android.teacher.newwork.MessageCenter;
import com.android.teacher.utils.JSONUtils;
import com.android.teacher.widget.CircleImageView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Observer;

public class ReadInfo extends BaseActivity implements MessageCallBack {


    @Bind(R.id.back_btn)
    ImageView backBtn;

    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;

    @Bind(R.id.recyc)
    RecyclerView recyc;
    @Bind(R.id.refresh)
    SwipeRefreshLayout refresh;

    private readerAdapter adapter;

    private List<ReaderBean.DataBean> list = new ArrayList<>();
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

            refresh.setRefreshing(false);
            if (JSONUtils.getString(cmd, "cmd").equals("message.readinfo")) {
                Gson gson = new Gson();
                Type type = new TypeToken<ReaderBean>() {
                }.getType();
                //设置具体信息
                ReaderBean readerBean = gson.fromJson(s, type);
                list.addAll(readerBean.getData());
                adapter.notifyDataSetChanged();

            }
        }
    };

    @Override
    public void setButterKnife() {
        ButterKnife.bind(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.ui_info_activity_read_info;
    }

    @OnClick({R.id.back_btn})
    void Onclick(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
                this.finish();
                break;

        }
    }

    private MessageCenter messageCenter;
    private int notifyId = 0;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        notifyId = getIntent().getIntExtra("id", 0);
        toolbarTitle.setText("阅读人");
        messageCenter = new MessageCenter();
        SendMsg();
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SendMsg();
            }
        });
        adapter = new readerAdapter(this, list);
        recyc.setAdapter(adapter);
        recyc.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));

    }

    void SendMsg() {
        messageCenter.SendYouMessage(messageCenter.ChooseCommand().message_readinfo(String.valueOf(notifyId)), this);
    }

    @Override
    public void onMessage(String str) {
        Log.e("ReadInfo", "onMessage: " + str);
        DealMessageForMe(str, observer);
    }


    class readerAdapter extends RecycCommomAdapter {
        private Context mContext;

        readerAdapter(Context context, List<ReaderBean.DataBean> dataList) {
            super(context, dataList);
            mContext = context;
        }

        @Override
        public BaseHolder setViewHolder(ViewGroup parent) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.reader_item, parent, false);
            Reader reader = new Reader(view, mContext);

            final JSONArray arr = new JSONArray();
            arr.put(list.get(reader.pos).getParentid());
            reader.setListen(new mClickInterface() {
                @Override
                public void doClick() {
                    new SweetAlertDialog(ReadInfo.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("确定发送通知到选择的用户?")
                            .setContentText("确认后将发送信息到用户!")
                            .setCancelText("再想想！")
                            .setConfirmText("是的!")
                            .showCancelButton(true)
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    // reuse previous dialog instance, keep widget user state, reset them if you need
                                    sDialog.setTitleText("取消!")
                                            .setContentText("取消成功 :)")
                                            .setConfirmText("好")
                                            .showCancelButton(false)
                                            .setCancelClickListener(null)
                                            .setConfirmClickListener(null)
                                            .changeAlertType(SweetAlertDialog.ERROR_TYPE);

                                    // or you can new a SweetAlertDialog to show
                               /* sDialog.dismiss();
                                new SweetAlertDialog(SampleActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Cancelled!")
                                        .setContentText("Your imaginary file is safe :)")
                                        .setConfirmText("OK")
                                        .show();*/
                                }
                            })
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {

                                    messageCenter.SendYouMessage(messageCenter.ChooseCommand().message_pushnotice(arr, notifyId));
                                    sDialog.setTitleText("发送成功!")
                                            .setContentText("已将通知发送至用户！")
                                            .setConfirmText("好")
                                            .showCancelButton(false)
                                            .setCancelClickListener(null)
                                            .setConfirmClickListener(null)
                                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                }
                            })
                            .show();

                }

                @Override
                public void doClick(int pos, View vi) {


                }
            });
            return reader;
        }


    }
}
