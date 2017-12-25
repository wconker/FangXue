package com.android.teacher.adapter.Holder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.teacher.R;
import com.android.teacher.base.BaseHolder;
import com.android.teacher.callback.mClickInterface;
import com.android.teacher.entity.ReaderBean;

/**
 * Created by softsea on 17/12/14.
 */

public class Reader extends BaseHolder<ReaderBean.DataBean> {
    private final View item;
    private final Context mContext;
    private mClickInterface mClickInterface;
    public int pos;

    public Reader(View itemView, Context context) {
        super(itemView);
        item = itemView;
        mContext = context;


    }

    public void setListen(mClickInterface clickListener) {

        mClickInterface = clickListener;
    }

    @Override
    public int getPost(int Pos) {
        this.pos = Pos;
        return Pos;
    }

    @Override
    public void getData(ReaderBean.DataBean d) {

        TextView name = item.findViewById(R.id.read_name);
        name.setText(d.getStudentname());
        if (mClickInterface != null) {
            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mClickInterface.doClick();
                }
            });
        }

        if (d.getReadflag() == 0) {
            name.setTextColor(mContext.getResources().getColor(R.color.gray_20));


        } else {
            name.setTextColor(mContext.getResources().getColor(R.color.black_80));

        }


    }
}
