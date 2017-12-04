package com.android.fangxue.ui.Center;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.fangxue.R;
import com.android.fangxue.adapter.Holder.TeacherHolder;
import com.android.fangxue.adapter.IndexViewAdapter.notifyAdapter;
import com.android.fangxue.adapter.commonAdapter.CommonAdapter;
import com.android.fangxue.adapter.commonAdapter.CommonViewHolder;
import com.android.fangxue.adapter.commonAdapter.RecycCommomAdapter;
import com.android.fangxue.callback.MessageCallBack;
import com.android.fangxue.entity.Homework;
import com.android.fangxue.entity.TeacherBean;
import com.android.fangxue.newwork.MessageCenter;
import com.android.fangxue.utils.ACache;
import com.android.fangxue.utils.JSONUtils;
import com.android.fangxue.utils.Net;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by softsea on 17/11/1.
 */

public class teacherFragment extends Fragment implements MessageCallBack {

    @Bind(R.id.teacher_list)
    ListView teacher_list;
    @Bind(R.id.back_btn)
    ImageView backBtn;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;

    @Bind(R.id.refresh)
    SwipeRefreshLayout refresh;
    private List<TeacherBean.DataBean> list = new ArrayList<>();
    private TeacherAdapter adapter;
    private File output;
    private Uri imageUri;
    private MessageCenter messageCenter;
    private Observer<String> observer;
    private ActivityCenter center;
    private int firstLoad = 0;


    public static teacherFragment newInstance() {
        return new teacherFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.ui_center_teacher, container, false);
        center = (ActivityCenter) getActivity();
        ButterKnife.bind(this, view);
        backBtn.setVisibility(View.GONE);
        toolbarTitle.setText("老师");
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                messageCenter.SendYouMessage(messageCenter.ChooseCommand().getteacherlist(),teacherFragment.this);
            }
        });
        messageCenter = new MessageCenter();

        observer = new Observer<String>() {
            @Override
            public void onCompleted() {

                refresh.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(String s) {
                String temp = s;
//                if (center.aCache.getAsString("teacher") != null) {
//                    temp = center.aCache.getAsString("teacher");
//                }
                DealWithData(temp);
                Log.e("Cache", center.aCache.getAsString("teacher"));
            }
        };
        adapter = new TeacherAdapter(getActivity(), list, R.layout.ui_center_teacher_item);
        teacher_list.setAdapter(adapter);

        return view;
    }

    private void DealWithData(String s) {
        JSONObject cmd = JSONUtils.StringToJSON(s);
        if (JSONUtils.getString(cmd, "cmd").equals("class.getteacherlist")) {
            center.aCache.put("teacher", s);
            Gson gson = new Gson();
            Type type = new TypeToken<TeacherBean>() {
            }.getType();
            TeacherBean dataBean = gson.fromJson(String.valueOf(cmd), type);
            if (dataBean.getData().size() > 0) {
                list.clear();
                list.addAll(dataBean.getData());
            }
        }
        adapter.notifyDataSetChanged();
        refresh.setRefreshing(false);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void setCallBackInterFace() {
        if (firstLoad == 0) {
            refresh.setRefreshing(true);
            messageCenter.SendYouMessage(messageCenter.ChooseCommand().getteacherlist(),this);
            firstLoad = 1;
        }
        if (!Net.isNetworkAvailable(getActivity())) {
            DealWithData(center.aCache.getAsString("teacher"));
            refresh.setRefreshing(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onMessage(String str) {

        rx.Observable.just(str)
                .observeOn(AndroidSchedulers
                        .mainThread())
                .subscribe(observer);
        Log.e("老师", str);
    }

    class TeacherAdapter extends CommonAdapter<TeacherBean.DataBean> {
        private Context context;

        TeacherAdapter(Context context, List<TeacherBean.DataBean> list, int layoutId) {
            super(context, list, layoutId);
            this.context = context;
        }


        @Override
        public void setViewContent(CommonViewHolder viewHolder, final TeacherBean.DataBean dataBean) {

            viewHolder.setText(R.id.teacher_category, dataBean.getTeachername()).setText(R.id.teacher_title, dataBean.getTeachername()).setText(R.id.teacher_category, dataBean.getLesson());
            ImageView phonecall = viewHolder.getView(R.id.call);
            ImageView tx = viewHolder.getView(R.id.tx);
            if (dataBean.getHeaderimg() != null) {
                Glide.with(context).load(dataBean.getHeaderimg()).into(tx);
            } else {
                tx.setImageDrawable(context.getResources().getDrawable(R.drawable.username));
            }
            phonecall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //用intent启动拨打电话
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + dataBean.getMobile()));
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    context.startActivity(intent);
                }
            });

        }
    }

    //
//    class TeacherAdapter extends RecycCommomAdapter {
//        private Context context;
//        TeacherAdapter(Context context, List<?> dataList) {
//            super(context, dataList);
//            this.context = context;
//        }
//
//        @Override
//        public void setViewHolder(ViewGroup parent) {
//
//
//        }
//    }

}
