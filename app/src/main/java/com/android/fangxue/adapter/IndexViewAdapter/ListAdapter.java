package com.android.fangxue.adapter.IndexViewAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.fangxue.adapter.Holder.HomeWork;
import com.android.fangxue.base.BaseHolder;
import com.android.fangxue.callback.mClickInterface;
import com.android.fangxue.R;
import com.android.fangxue.entity.Homework;
import com.android.fangxue.ui.Detail.HomeworkDetail;
import com.android.fangxue.utils.Toast;


import java.util.List;

/**
 * Created by fangxue on 17/8/10.
 */

public class ListAdapter extends RecyclerView.Adapter<BaseHolder> implements mClickInterface {
    private Context context;
    private List<Homework.DataBean> list;
    private final int TITLE = 0;
    private final int BOX = 1;
    private Activity ac;

    public ListAdapter(Context context, List<Homework.DataBean> dataSource) {

        this.list = dataSource;
        this.context = context;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.ui_center_homework_item, viewGroup, false);
        return new HomeWork(view, this.context);
    }

    void setActivity(Activity activity) {
        this.ac = activity;
    }

    @Override
    public void onBindViewHolder(BaseHolder baseHolder, int i) {

        baseHolder.getData(list.get(i));

        ((HomeWork) baseHolder).setPostion(i);
        ((HomeWork) baseHolder).setmClickInterface(this);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 || position == 7 || position == 8) {
            return TITLE;
        } else {
            return BOX;
        }
    }

    @Override
    public void doClick() {

    }

    @Override
    public void doClick(int pos, View view) {


        Intent i = new Intent(context, HomeworkDetail.class);
        i.putExtra("ConkerData", list.get(pos).getId());
        context.startActivity(i);


    }
}
