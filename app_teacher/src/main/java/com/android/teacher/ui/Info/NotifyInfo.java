package com.android.teacher.ui.Info;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.teacher.R;
import com.android.teacher.adapter.Holder.CommadHolder;
import com.android.teacher.adapter.Holder.Reader;
import com.android.teacher.adapter.commonAdapter.RecycCommomAdapter;
import com.android.teacher.base.BaseActivity;
import com.android.teacher.base.BaseHolder;
import com.android.teacher.callback.MessageCallBack;
import com.android.teacher.callback.mClickInterface;
import com.android.teacher.entity.Comment;
import com.android.teacher.entity.Homework;
import com.android.teacher.entity.HomeworkDetail;
import com.android.teacher.entity.ReaderBean;
import com.android.teacher.newwork.MessageCenter;
import com.android.teacher.utils.JSONUtils;
import com.android.teacher.utils.Preview;
import com.android.teacher.utils.photoPickerUtil.PhotoT;
import com.android.teacher.widget.CircleImageView;
import com.android.teacher.widget.CommentDialog;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
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
    @Bind(R.id.previewImage)
    Preview previewImage;
    @Bind(R.id.icon_font)
    CircleImageView icon_font;
    @Bind(R.id.photo)
    PhotoT photo;
    @Bind(R.id.reviewList)
    RecyclerView reviewList;
    @Bind(R.id.readNum_tv)
    TextView num;
    @Bind(R.id.t1)
    LinearLayout t1;
    @Bind(R.id.t2)
    LinearLayout t2;
    @Bind(R.id.recyc)
    RecyclerView recyc;
    private NotifyInfo.readerAdapter readerAdapter;

    private NotifyAdapater adapater;
    List<Comment.DataBean> list = new ArrayList<>();
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

        previewImage.init(NotifyInfo.this, preview, imgc);
        title.setText("详情");
        notifyId = getIntent().getIntExtra("notify", 0);
        messageCenter = new MessageCenter();

        messageCenter.setCallBackInterFace(this);
        messageCenter.SendYouMessage(messageCenter.ChooseCommand().getmessageinfo_HomeWork(notifyId));
        adapater = new NotifyAdapater(this, list);
        reviewList.setNestedScrollingEnabled(false);
        reviewList.setAdapter(adapater);
        reviewList.setLayoutManager(new LinearLayoutManager(this));
        setObserver();
        setReadControl();
        dialog = new CommentDialog(this);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getRepeatCount() == 0)
                    dialog.cancel();
                return false;
            }
        });
        dialog.setOnCommitListener(new CommentDialog.OnCommitListener() {
            @Override
            public void onCommit(EditText et, View v) {
                if (!et.getText().toString().isEmpty())
                    messageCenter.SendYouMessage(messageCenter.ChooseCommand().message_addComment(notifyId, et.getText().toString()));
            }

            @Override
            public void onGetLocation() {

            }

            @Override
            public void onDeleteLocation() {

            }

            @Override
            public void onAnonymousChecked(CompoundButton buttonView, boolean isChecked) {

            }
        });
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
    void SimulationData(String s) {

        Gson gson = new Gson();
        Type type = new TypeToken<Comment>() {
        }.getType();
        //设置具体信息
        Comment comment = gson.fromJson(s, type);
        list.clear();
        list.addAll(comment.getData());
        adapater.notifyDataSetChanged();

    }

    private List<ReaderBean.DataBean> readList = new ArrayList<>();

    private void DealWithData(String s) {
        JSONObject cmd = JSONUtils.StringToJSON(s);
        if (JSONUtils.getString(cmd, "cmd").equals("message.getinfo")) {
            Gson gson = new Gson();
            Type type = new TypeToken<HomeworkDetail>() {
            }.getType();
            //设置具体信息
            HomeworkDetail homeworData = gson.fromJson(s, type);
            messageCenter.SendYouMessage(messageCenter.ChooseCommand().message_getCommentList(notifyId));
            setValue(homeworData);

        }
        if (JSONUtils.getString(cmd, "cmd").equals("message.readinfo")) {
            Gson gson = new Gson();
            Type type = new TypeToken<ReaderBean>() {
            }.getType();
            //设置具体信息
            ReaderBean readerBean = gson.fromJson(s, type);
            readList.addAll(readerBean.getData());
            readerAdapter.notifyDataSetChanged();

        }

        if (JSONUtils.getString(cmd, "cmd").equals("message.addComment")) {
            com.android.teacher.utils.Toast.FangXueToast(NotifyInfo.this, JSONUtils.getString(cmd, "message"));
            dialog.cancel();
            messageCenter.SendYouMessage(messageCenter.ChooseCommand().message_getCommentList(notifyId));
        }
        if (JSONUtils.getString(cmd, "cmd").equals("message.getCommentList")) {
            SimulationData(s);
        }
    }

    private void setValue(HomeworkDetail homeworkBean) {
        HomeworkDetail.DataBean dataBean = homeworkBean.getData().get(0);
        hoemwork_title.setText(dataBean.getWorktitle());
        homework_author.setText(dataBean.getLesson());
        homework_teachername.setText(dataBean.getAuthor());
        photo.setActivity(this);
        message_content.setText(dataBean.getWorkcontent());
        hoemwork_date.setText(dataBean.getReleasetime());
        num.setText(dataBean.getTotal() + "");
        for (int i = 0; i < dataBean.getPic().size(); i++) {
            preview.add(dataBean.getPic().get(i).getPicpath());
            imgc.add(dataBean.getPic().get(i).getThumbnail());
        }
        previewImage.setImgSource(preview, imgc);
        photo.setUrl(imgc);
        photo.setPreviewPhoto(preview);
        if (!dataBean.getHeaderimg().isEmpty() && dataBean.getHeaderimg() != null) {
            Glide.with(NotifyInfo.this).load(dataBean.getHeaderimg()).into(icon_font);
        } else {
            Glide.with(NotifyInfo.this).load(R.drawable.username).into(icon_font);
        }
    }

    CommentDialog dialog;

    @OnClick({R.id.back_btn, R.id.t1, R.id.t2})
    void Onclick(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
                this.finish();
                break;
            case R.id.t2:
                dialog.show();
                break;
            case R.id.t1:
                if (recyc.getVisibility() == View.VISIBLE) {
                    recyc.setVisibility(View.GONE);
                } else {
                    recyc.setVisibility(View.VISIBLE);
                }
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

    class NotifyAdapater extends RecycCommomAdapter {
        private final Context mContext;

        NotifyAdapater(Context context, List<Comment.DataBean> dataList) {
            super(context, dataList);
            this.mContext = context;
        }

        @Override
        public BaseHolder setViewHolder(ViewGroup parent) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.commad_item, parent, false);
            return new CommadHolder(view);
        }
    }

    void setReadControl() {
        SendMsg();
        readerAdapter = new NotifyInfo.readerAdapter(this, readList);
        recyc.setAdapter(readerAdapter);
        recyc.setLayoutManager(new StaggeredGridLayoutManager(6, StaggeredGridLayoutManager.VERTICAL));

    }

    void SendMsg() {
        messageCenter.SendYouMessage(messageCenter.ChooseCommand().message_readinfo(String.valueOf(notifyId)), this);
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
//            arr.put(list.get(reader.pos).getParentid()); //发送通知给未读人 暂时关闭
            reader.setListen(new mClickInterface() {
                @Override
                public void doClick() {
//                    new SweetAlertDialog(NotifyInfo.this, SweetAlertDialog.WARNING_TYPE)
//                            .setTitleText("确定发送通知到选择的用户?")
//                            .setContentText("确认后将发送信息到用户!")
//                            .setCancelText("再想想！")
//                            .setConfirmText("是的!")
//                            .showCancelButton(true)
//                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                @Override
//                                public void onClick(SweetAlertDialog sDialog) {
//                                    // reuse previous dialog instance, keep widget user state, reset them if you need
//                                    sDialog.setTitleText("取消!")
//                                            .setContentText("取消成功 :)")
//                                            .setConfirmText("好")
//                                            .showCancelButton(false)
//                                            .setCancelClickListener(null)
//                                            .setConfirmClickListener(null)
//                                            .changeAlertType(SweetAlertDialog.ERROR_TYPE);
//
//                                    // or you can new a SweetAlertDialog to show
//                               /* sDialog.dismiss();
//                                new SweetAlertDialog(SampleActivity.this, SweetAlertDialog.ERROR_TYPE)
//                                        .setTitleText("Cancelled!")
//                                        .setContentText("Your imaginary file is safe :)")
//                                        .setConfirmText("OK")
//                                        .show();*/
//                                }
//                            })
//                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                @Override
//                                public void onClick(SweetAlertDialog sDialog) {
//
//                                    messageCenter.SendYouMessage(messageCenter.ChooseCommand().message_pushnotice(arr, notifyId));
//                                    sDialog.setTitleText("发送成功!")
//                                            .setContentText("已将通知发送至用户！")
//                                            .setConfirmText("好")
//                                            .showCancelButton(false)
//                                            .setCancelClickListener(null)
//                                            .setConfirmClickListener(null)
//                                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
//                                }
//                            })
//                            .show();

                }

                @Override
                public void doClick(int pos, View vi) {


                }
            });
            return reader;
        }


    }

}
