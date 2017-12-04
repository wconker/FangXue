package com.android.fangxue.adapter.commonAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.fangxue.R;
import com.android.fangxue.adapter.Holder.TeacherHolder;
import com.android.fangxue.base.BaseHolder;
import com.android.fangxue.entity.TeacherBean;

import java.util.List;

/**
 * Created by softsea on 17/11/1.
 */

public abstract class RecycCommomAdapter extends RecyclerView.Adapter<BaseHolder> {

    private List<?> list;
    private Context context;
    protected BaseHolder viewholder;

    public RecycCommomAdapter(Context context, List<?> dataList) {
        list = dataList;
        this.context = context;
    }

    public abstract void setViewHolder(ViewGroup parent);

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(context).inflate(R.layout.ui_center_teacher_item, parent, false);
        viewholder = new TeacherHolder(v, context);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {

        Log.e("List.Position", ((TeacherBean.DataBean) list.get(position)).getTeachername());
        viewholder.getData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
