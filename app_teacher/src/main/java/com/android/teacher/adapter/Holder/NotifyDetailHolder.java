package com.android.teacher.adapter.Holder;

import android.view.View;
import android.widget.TextView;

import com.android.teacher.R;
import com.android.teacher.base.BaseHolder;
import com.android.teacher.entity.HomeworkDetail;
import com.android.teacher.entity.WorkBean;

/**
 * Created by softsea on 17/11/16.
 */

public class NotifyDetailHolder extends BaseHolder<HomeworkDetail.DataBean> {
    private final View v;

    public NotifyDetailHolder(View itemView) {
        super(itemView);
        this.v=itemView;
    }

    @Override
    public void getData(HomeworkDetail.DataBean d) {
        TextView t= v.findViewById(R.id.homework_detail_content);
        t.setText(d.getWorkcontent());
    }

}
