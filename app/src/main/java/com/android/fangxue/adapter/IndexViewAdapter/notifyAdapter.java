package com.android.fangxue.adapter.IndexViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.fangxue.Default.FinalField;
import com.android.fangxue.R;
import com.android.fangxue.adapter.Holder.HomeWork;
import com.android.fangxue.base.BaseHolder;
import com.android.fangxue.callback.mClickInterface;
import com.android.fangxue.entity.Homework;
import com.android.fangxue.ui.Detail.HomeworkDetail;
import com.android.fangxue.ui.Detail.NotifyDetail;
import com.android.fangxue.utils.Toast;

import java.util.List;

/**
 * Created by softsea on 17/10/24.
 */

public class notifyAdapter extends RecyclerView.Adapter<BaseHolder> implements mClickInterface {
    private Context context;
    private List<Homework.DataBean> list;
    private final int TITLE = 0;
    private final int BOX = 1;
    private int Foot = 1;

    public notifyAdapter(Context context, List<Homework.DataBean> dataSource) {

        this.list = dataSource;
        this.context = context;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view;
        if (i == FinalField.TYPE_FOOTER) {
            view = LayoutInflater.from(context).inflate(R.layout.footload,
                    viewGroup, false);
            return new HomeWork(view, this.context);
        } else if (i == FinalField.TYPE_BODY) {
            view = LayoutInflater.from(context).inflate(R.layout.ui_center_homework_item,
                    viewGroup, false);
            return new HomeWork(view, this.context);
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.foot_no_data,
                    viewGroup, false);
        }
        return new HomeWork(view, this.context);


    }

    @Override
    public void onBindViewHolder(BaseHolder baseHolder, int i) {
        if (getItemViewType(i) == FinalField.TYPE_BODY) {
            baseHolder.getData(list.get(i));
            ((HomeWork) baseHolder).setPostion(i);
            ((HomeWork) baseHolder).setmClickInterface(this);
        }

    }

    public void setFoot(int state) {
        this.Foot = state;

    }

    @Override
    public int getItemCount() {

        //最后一个是显示加载进度条，即数据总数+进度条
        return list.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            if (Foot == 1 && getItemCount() > 6) {
                return FinalField.TYPE_FOOTER;
            } else {
                return 0;
            }
        } else {
            return FinalField.TYPE_BODY;
        }
    }

    @Override
    public void doClick() {

    }

    @Override
    public void doClick(int pos, View view) {
        Intent i = new Intent(context, NotifyDetail.class);
        i.putExtra("ConkerData", list.get(pos).getId());
        context.startActivity(i);


    }
}
