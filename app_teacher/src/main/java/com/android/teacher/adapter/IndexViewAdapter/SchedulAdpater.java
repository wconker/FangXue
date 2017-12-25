package com.android.teacher.adapter.IndexViewAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.teacher.R;
import com.android.teacher.adapter.Holder.SchedulHolder;
import com.android.teacher.callback.mClickInterface;
import com.android.teacher.entity.ScheduleBean;

import java.util.List;

/**
 * Created by softsea on 17/11/24.
 */

public class SchedulAdpater extends RecyclerView.Adapter<SchedulHolder> {


    private final List<ScheduleBean.DataBean> adapterList;
    private final Context mContext;
    private mClickInterface cc;

    public SchedulAdpater(Context context, List<ScheduleBean.DataBean> list, mClickInterface click) {

        this.adapterList = list;
        this.mContext = context;
        this.cc = click;

    }

    @Override
    public SchedulHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SchedulHolder(LayoutInflater.from(mContext).inflate(R.layout.schedul_content, parent, false), mContext, cc);
    }

    @Override
    public void onBindViewHolder(SchedulHolder holder, int position) {
        holder.getData(adapterList.get(position));
    }

    @Override
    public int getItemCount() {
        return adapterList.size();
    }


}
