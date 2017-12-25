package com.android.teacher.adapter.Holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.android.teacher.R;
import com.android.teacher.base.BaseHolder;
import com.android.teacher.entity.Comment;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.w3c.dom.Text;

/**
 * Created by softsea on 17/11/16.
 */

public class CommadHolder extends BaseHolder<Comment.DataBean> {


    private final View item;

    public CommadHolder(View itemView) {
        super(itemView);
        this.item = itemView;
    }

    @Override
    public void getData(Comment.DataBean d) {
        TextView t_name = item.findViewById(R.id.name);
        TextView time = item.findViewById(R.id.time);
        TextView content = item.findViewById(R.id.content);
        t_name.setText(d.getUsername());
        time.setText(d.getCommenttime());
        content.setText(d.getCommentcontent());

    }
}
