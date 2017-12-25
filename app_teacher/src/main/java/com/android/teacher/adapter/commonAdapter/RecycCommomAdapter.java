package com.android.teacher.adapter.commonAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.teacher.base.BaseHolder;
import com.android.teacher.callback.mClickInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.http.HEAD;

/**
 * Created by softsea on 17/11/1.
 */

public abstract class RecycCommomAdapter extends RecyclerView.Adapter<BaseHolder> {

    private List<?> list;
    protected Context context;
    protected BaseHolder viewholder;
    private int useType = 0;
    private final int HEAD = 100;
    private final int BODY = 200;

    public RecycCommomAdapter(Context context, List<?> dataList) {
        list = dataList;
        this.context = context;
    }

    public abstract BaseHolder setViewHolder(ViewGroup parent);

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return setViewHolder(parent);
    }

    protected void setUseType() {
        useType = 1;

    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {

        holder.getPost(position);
        holder.getData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (this.useType == 1) {
            if (position == 1) {
                return HEAD;
            } else {
                return BODY;
            }

        }

        return 0;
    }
}
