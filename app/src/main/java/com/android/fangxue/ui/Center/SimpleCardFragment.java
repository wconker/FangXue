package com.android.fangxue.ui.Center;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.fangxue.R;
import com.android.fangxue.adapter.MyAdapter.MyRecyclerViewAdapter;
import com.android.fangxue.callback.MessageCallBack;
import com.android.fangxue.entity.CourseBean;
import com.android.fangxue.newwork.MessageCenter;
import com.android.fangxue.utils.ACache;
import com.android.fangxue.utils.JSONUtils;
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


@SuppressLint("ValidFragment")
public class SimpleCardFragment extends Fragment implements MessageCallBack {
    @Bind(R.id.timeFull)
    RecyclerView timeFull;
    @Bind(R.id.showDate)
    TextView showDate;
    @Bind(R.id.tran)
    LinearLayout tran;
    private String mTitle;
    private Observer<String> dataDeal;
    private MyRecyclerViewAdapter adapter;
    private indexFragment fragment;
    private List<CourseBean.DataBean> list = new ArrayList<>();
    private indexFragment indexFragment;
    private MessageCenter messageCenter;
    private String SelectDay = "";
    private ActivityCenter center;


    public static SimpleCardFragment getInstance(String title) {
        SimpleCardFragment sf = new SimpleCardFragment();
        sf.mTitle = title;
        return sf;
    }


    @Override
    public void onStop() {
        super.onStop();

    }


    @Override
    public void onResume() {
        super.onResume();


    }


    public void lanchMessage(int classid, String day, String Message) {

//        String value = center.aCache.getAsString(day);
        SelectDay = day;
//        Log.e("Day", value + "--" + day);
//        if (value == null) {

        tran.setVisibility(View.GONE);
        messageCenter.SendYouMessage(messageCenter.ChooseCommand().actual_getList( day),this);
//     } else {
//            DealWithData(value);
//        }
        showDate.setText(Message);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ui_center_course, null);
        ButterKnife.bind(this, view);
        center = (ActivityCenter) getActivity();
        center.aCache = ACache.get(getActivity());
        timeFull.setLayoutManager(new LinearLayoutManager(getActivity()));

        messageCenter = new MessageCenter();
        adapter = new MyRecyclerViewAdapter(getActivity(), list);
        timeFull.setAdapter(adapter);
        DataCenter();
        indexFragment = (com.android.fangxue.ui.Center.indexFragment) getParentFragment();

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    private void DataCenter() {
        dataDeal = new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                System.out.print("onError" + e.getMessage());
            }

            @Override
            public void onNext(String s) {
                DealWithData(s);
                //加入缓存中

            }
        };
    }

    private void DealWithData(String s) {
        JSONObject cmd = JSONUtils.StringToJSON(s);
        if (JSONUtils.getString(cmd, "cmd").equals("actual.getList")) {
            Log.e("课程表数据",s);
            list.clear();
            if (JSONUtils.getJSONArray(cmd, "data").length() > 0) {
              Log.e("课程表》0",JSONUtils.getJSONArray(cmd, "data").length() +"个");
                Gson gson = new Gson();
                Type type = new TypeToken<CourseBean>() {
                }.getType();
                CourseBean dataBean = gson.fromJson(String.valueOf(cmd), type);
                list.addAll(dataBean.getData());
                timeFull.setVisibility(View.VISIBLE);
            }else {
                list.clear();
                timeFull.setVisibility(View.GONE);
            }
            adapter.notifyDataSetChanged();
        }

        //服务器主动推送通知，用于放铃声和记录时间
        if (JSONUtils.getString(cmd, "cmd").equals("system.notify")) {
            messageCenter.SendYouMessage(messageCenter.ChooseCommand().getnotify(),this);
        }

        //客户端主动调取放学及其他时间
        if (JSONUtils.getString(cmd, "cmd").equals("system.getnotify")) {
            JSONObject data = JSONUtils.getSingleJSON(cmd, "data", 0);
            String arrivetime = JSONUtils.getString(data, "arrivetime");
            String classtime = JSONUtils.getString(data, "classtime");
            String leavetime = JSONUtils.getString(data, "leavetime");
            if (classtime != null && !classtime.equals("null") && !classtime.isEmpty()) {
                indexFragment.arrTempf.setText(classtime);
            } else {
                indexFragment.arrTempf.setText("--:--");
            }
            if (arrivetime != null && !arrivetime.equals("null") && !arrivetime.isEmpty())
                indexFragment.titleArrived.setText(arrivetime);
            else {
                indexFragment.titleArrived.setText("--:--");
            }

            if (leavetime != null && !leavetime.equals("null") && !leavetime.isEmpty())
                indexFragment.studentLeave.setText(leavetime);
            else {
                indexFragment.studentLeave.setText("--:--");
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onMessage(String str) {

        Observable.just(str)
                .observeOn(AndroidSchedulers
                        .mainThread())
                .subscribe(dataDeal);

    }
}
