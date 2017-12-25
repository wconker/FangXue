package com.android.teacher.adapter.IndexViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.teacher.Default.FinalField;
import com.android.teacher.R;
import com.android.teacher.adapter.Holder.HomeWork;
import com.android.teacher.base.BaseHolder;
import com.android.teacher.callback.mClickInterface;
import com.android.teacher.entity.MessageDetailBean;
import com.android.teacher.ui.SecondPage.SendHomeWork;

import java.util.List;

/**
 * Created by fangxue on 17/8/10.
 */

public class ListAdapter extends RecyclerView.Adapter<BaseHolder> {
    private Context context;
    private List<MessageDetailBean.DataBean> list;
    private final int TITLE = 0;
    private final int BOX = 1;
    private mClickInterface clicklistener;
    private int Foot = 1;

    public ListAdapter(Context context, List<MessageDetailBean.DataBean> dataSource) {
        this.list = dataSource;
        this.context = context;
    }


    public void setMyClickListener(mClickInterface myClickListener) {
        this.clicklistener = myClickListener;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view ;

        if (i == FinalField.TYPE_FOOTER) {
            view = LayoutInflater.from(context).inflate(R.layout.footload,
                    viewGroup, false);
            return new HomeWork(view, this.context);
        } else if (i == FinalField.TYPE_BODY) {
            view = LayoutInflater.from(context).inflate(R.layout.ui_center_message_item,
                    viewGroup, false);
            return new HomeWork(view, this.context);
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.foot_no_data,
                    viewGroup, false);
        }
        return new HomeWork(view, this.context);
    }
    public void setFoot(int state) {
        this.Foot = state;
    }

    @Override
    public void onBindViewHolder(BaseHolder baseHolder, int i) {
        if (getItemViewType(i) == FinalField.TYPE_BODY) {
            baseHolder.getData(list.get(i));
            ((HomeWork) baseHolder).setPostion(i);
            ((HomeWork) baseHolder).setmClickInterface(this.clicklistener);
        }

    }

    @Override
    public int getItemCount() {

        return list.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {

        if (position + 1 == getItemCount()) {//当前索引（末尾数据）+1（尾部视图）的数值等于总条数，则说明已经是底部
            if (Foot == 1 && getItemCount() > 10) {
                return FinalField.TYPE_FOOTER;
            } else {
                return 0;
            }
        } else {
            return FinalField.TYPE_BODY;
        }
    }


}
