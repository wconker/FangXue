package com.android.teacher.adapter.IndexViewAdapter;

import android.view.View;

import com.android.teacher.base.BaseHolder;
import com.android.teacher.callback.mClickInterface;

/**
 * Created by softsea on 17/8/10.
 */

public class listHolder extends BaseHolder implements View.OnClickListener {

    private mClickInterface mClick = null;

    public listHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
    }

    @Override
    public void getData(Object d) {

    }

    public void setOnclick(mClickInterface mClick) {
        this.mClick = mClick;
    }

    @Override
    public void onClick(View view) {
        mClick.doClick();
    }
}
