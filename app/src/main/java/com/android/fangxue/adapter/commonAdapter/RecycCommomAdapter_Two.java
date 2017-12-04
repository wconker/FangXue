package com.android.fangxue.adapter.commonAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.android.fangxue.base.BaseHolder;
import com.android.fangxue.base.BaseHolder_two;

import java.util.List;

/**
 * Created by softsea on 17/11/1.
 */

public abstract class RecycCommomAdapter_Two extends RecyclerView.Adapter<BaseHolder_two> {

    private List<?> list;
    protected Context context;
    protected BaseHolder_two viewholder;

    public RecycCommomAdapter_Two(Context context, List<?> dataList) {
        list = dataList;
        this.context = context;
    }

    public abstract BaseHolder_two setViewHolder(ViewGroup parent);

    @Override
    public BaseHolder_two onCreateViewHolder(ViewGroup parent, int viewType) {

        return setViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(BaseHolder_two holder, int position) {
        holder.getPost(position);
        holder.getData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
