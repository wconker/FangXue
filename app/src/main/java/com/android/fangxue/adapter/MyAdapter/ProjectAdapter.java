package com.android.fangxue.adapter.MyAdapter;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.fangxue.R;
import com.android.fangxue.callback.mClickInterface;
import com.android.fangxue.entity.ProjectBean;
import com.android.fangxue.ui.MainActivity;
import com.android.fangxue.ui.Pro.ScrollingActivity;

import java.util.List;

/**
 * Created by fangxue on 17/9/5.
 */

public class ProjectAdapter extends RecyclerView.Adapter<projectHolder> implements mClickInterface {
    private Context mcontext;
    List<ProjectBean.DataBean> mlist;
    List<Integer> mheight;

    public ProjectAdapter(Context context, List<ProjectBean.DataBean> list) {
        mcontext = context;
        mlist = list;

    }

    @Override
    public int getItemCount() {

        return mlist.size();
    }

    @Override
    public projectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new projectHolder(LayoutInflater.from(mcontext).inflate(R.layout.ui_project_pro_item, parent, false));
    }

    @Override
    public void onBindViewHolder(projectHolder holder, int position) {

        holder.getData(mlist.get(position));
        holder.setOnclick(this);
        holder.getPost(position);
    }

    @Override
    public void doClick() {

    }

    @Override
    public void doClick(int pos, View view) {
        Intent i = new Intent(mcontext, ScrollingActivity.class);
        i.putExtra("proId", mlist.get(pos).getId());
        i.putExtra("proName", mlist.get(pos).getName());
        MainActivity mainActivity = (MainActivity) mcontext;
        mcontext.startActivity(i, ActivityOptions.makeSceneTransitionAnimation(mainActivity, view, "shareE").toBundle());
    }


//    public void add(int pos) {
//        mlist.add(pos, ProjectBean);
//        mheight.add((int) (100 + Math.random() * 300));
//        notifyItemInserted(pos);
//    }
//
//
//    public void del(int pos) {
//        mlist.remove(pos);
//        notifyItemRemoved(pos);
//    }


}