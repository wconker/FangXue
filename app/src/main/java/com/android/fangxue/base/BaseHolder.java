package com.android.fangxue.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by fangxue on 17/8/9.
 */


public abstract class BaseHolder<T> extends RecyclerView.ViewHolder {

    public BaseHolder(View itemView) {
        super(itemView);
    }

    public abstract void getData(T d);
}
