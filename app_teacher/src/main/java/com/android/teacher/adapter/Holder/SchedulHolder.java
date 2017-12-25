package com.android.teacher.adapter.Holder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.teacher.R;
import com.android.teacher.adapter.commonAdapter.RecycCommomAdapter;
import com.android.teacher.base.BaseHolder;
import com.android.teacher.callback.mClickInterface;
import com.android.teacher.entity.Lesson;
import com.android.teacher.entity.ScheduleBean;
import com.android.teacher.newwork.MessageCenter;
import com.android.teacher.ui.Info.AddSchedul;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by softsea on 17/11/24.
 */

public class SchedulHolder extends BaseHolder<ScheduleBean.DataBean> {
    private View v;
    private Context mContext;
    private mClickInterface clickInterface;
    private MessageCenter messageCenter;


    public SchedulHolder(View itemView, Context context, mClickInterface click) {
        super(itemView);
        this.v = itemView;
        this.mContext = context;
        this.clickInterface = click;
    }

    @Override
    public void getData(ScheduleBean.DataBean d) {

        setInnerAdapter(d);
        TextView tv = v.findViewById(R.id.schedul_content);
        tv.setText(d.getDayname());


    }

    private void setInnerAdapter(ScheduleBean.DataBean d) {
        List<ScheduleBean.DataBean.ListBean> lessons = new ArrayList<>();
        lessons.addAll(d.getList());
        SchedulInnerAdapter adapter = new SchedulInnerAdapter(mContext, lessons);
        RecyclerView recyclerView = v.findViewById(R.id.inner_recyc);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

    }

    class SchedulInnerAdapter extends RecycCommomAdapter {
        SchedulInnerAdapter(Context context, List<ScheduleBean.DataBean.ListBean> dataList) {
            super(context, dataList);
        }

        @Override
        public BaseHolder setViewHolder(ViewGroup parent) {
            return new viewHolderForInner(LayoutInflater.from(mContext).inflate(R.layout.schedul_title, parent, false));

        }

        class viewHolderForInner extends BaseHolder<ScheduleBean.DataBean.ListBean> {

            private final View v;
            TextView lesson, t, sectionname, from, to;
            Button del,edit;
            private int pos;

            viewHolderForInner(View itemView) {
                super(itemView);
                t = itemView.findViewById(R.id.schedul_title);
                lesson = itemView.findViewById(R.id.lesson);
                from = itemView.findViewById(R.id.from);
                to = itemView.findViewById(R.id.to);
                sectionname = itemView.findViewById(R.id.sectionname);
                del = itemView.findViewById(R.id.del);
                edit = itemView.findViewById(R.id.edit);
                this.v = itemView;
            }

            @Override
            public int getPost(int Pos) {
                this.pos = Pos;
                return Pos;
            }


            @Override
            public void getData(final ScheduleBean.DataBean.ListBean d) {
                t.setText(d.getTeachername());
                lesson.setText(d.getCoursename());
                from.setText(d.getFromtime());
                to.setText(d.getTotime());
                sectionname.setText(d.getSectionname());
                del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View o) {
                        int postId = d.getId();
                        clickInterface.doClick(postId, o);
                    }
                });

                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View o) {
                        int postId = d.getId();
                        clickInterface.doClick(postId, o);
                    }
                });


            }
        }
    }
}
