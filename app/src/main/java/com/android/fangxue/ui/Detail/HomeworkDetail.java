package com.android.fangxue.ui.Detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.fangxue.R;
import com.android.fangxue.adapter.MyAdapter.MyRecyclerViewAdapter;
import com.android.fangxue.base.BaseActivity;
import com.android.fangxue.callback.MessageCallBack;
import com.android.fangxue.entity.Homework;
import com.android.fangxue.entity.note.WorkBean;
import com.android.fangxue.newwork.MessageCenter;
import com.android.fangxue.ui.Pro.ShowBigImg;
import com.android.fangxue.utils.JSONUtils;
import com.bumptech.glide.Glide;
import com.foamtrace.photopicker.intent.PhotoPreviewIntent;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class HomeworkDetail extends BaseActivity implements View.OnClickListener, MessageCallBack {


    private com.android.fangxue.entity.HomeworkDetail homeworkDetail1;
    @Bind(R.id.back_btn)
    ImageView backBtn;
    @Bind(R.id.imageList)
    LinearLayout imageList;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.homework_detail_time)
    TextView homeworkDetailTime;
    @Bind(R.id.homework_detail_name)
    TextView homeworkDetailName;
    @Bind(R.id.homework_detail_content)
    TextView homeworkDetailContent;
    @Bind(R.id.homework_detail_read)
    TextView homeworkDetailRead;
    private PhotoPreviewIntent intent;
    private int requestId = 0;
    private Context context = this;
    private MessageCenter messageCenter;

    private Observer<String> observer;
    private WorkBean workBean;
    private MyRecyclerViewAdapter adapter;
    private List<WorkBean.DataBean> listdata = new ArrayList<>();
    private ArrayList<String> arrayList = new ArrayList<String>();

    @Override
    public void setButterKnife() {
        ButterKnife.bind(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.ui_note_note_list;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        messageCenter = new MessageCenter(this);
        toolbarTitle.setText("作业详情");
        backBtn.setOnClickListener(this);
        ControlCenter();
        intent = new PhotoPreviewIntent(HomeworkDetail.this);


        requestId = getIntent().getIntExtra("ConkerData", 0);

    }


    private void ControlCenter() {


        observer = new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(final String s) {
                JSONObject cmd = JSONUtils.StringToJSON(s);
                if (JSONUtils.getString(cmd, "cmd").equals("message.getinfo")) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<com.android.fangxue.entity.HomeworkDetail>() {
                    }.getType();
                    //设置具体信息
                    homeworkDetail1 = gson.fromJson(String.valueOf(cmd), type);
                    homeworkDetailName.setText(homeworkDetail1.getData().get(0).getAuthor());
                    homeworkDetailTime.setText(homeworkDetail1.getData().get(0).getReleasetime());
                    homeworkDetailContent.setText(homeworkDetail1.getData().get(0).getWorkcontent());
                    homeworkDetailRead.setText(homeworkDetail1.getData().get(0).getTotal() + "");
                    WindowManager wm = HomeworkDetail.this.getWindowManager();

                    arrayList.clear();
                    imageList.removeAllViews();
                    Log.e("ecsl", imageList.getChildCount() + "个");
                    int width = wm.getDefaultDisplay().getWidth() - 200;
                    int height = wm.getDefaultDisplay().getHeight() - 200;

                    if (homeworkDetail1.getData().get(0).getPic().size() > 0) {
                        for (int i = 0; i < homeworkDetail1.getData().get(0).getPic().size(); i++) {
                            ImageView imageView = new ImageView(HomeworkDetail.this);
                            LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(width, height);
                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                            l.setMargins(10, 10, 10, 10);
                            l.gravity = Gravity.CENTER_HORIZONTAL;
                            imageView.setLayoutParams(l);  //设置图片宽高
                            final int finalI = i;
                            arrayList.add(homeworkDetail1.getData().get(0).getPic().get(finalI).getPicpath());
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    intent.setCurrentItem(finalI); // 当前选中照片的下标
                                    intent.setPhotoPaths(arrayList); // 已选中的照片地址
                                    startActivityForResult(intent, 100);


                                }
                            });
                            Glide.with(HomeworkDetail.this).load(homeworkDetail1.getData().get(0).getPic().get(i).getThumbnail()).into(imageView);

                            imageList.addView(imageView);
                        }

                    }
                }
            }
        };
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (observer == null) {

        } else {
            ControlCenter();
        }
        messageCenter.setCallBackInterFace(this);
        messageCenter.SendYouMessage(messageCenter.ChooseCommand().getmessageinfo_HomeWork(requestId));


    }

    void sendObj(String s) {

        Observable.just(s)
                .observeOn(AndroidSchedulers
                        .mainThread())
                .subscribe(observer);
    }

    @Override
    public void onMessage(String str) {
        sendObj(str);
    }


    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {
            case R.id.back_btn:
                finish();
                break;


        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
