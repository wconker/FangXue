package com.android.fangxue.adapter.IndexViewAdapter;

import android.view.View;

import com.android.fangxue.base.BaseHolder;
import com.android.fangxue.callback.mClickInterface;

/**
 * Created by fangxue on 17/8/10.
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
