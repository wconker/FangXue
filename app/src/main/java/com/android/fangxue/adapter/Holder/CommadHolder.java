package com.android.fangxue.adapter.Holder;

import android.view.View;
import android.widget.TextView;

import com.android.fangxue.R;
import com.android.fangxue.base.BaseHolder;
import com.android.fangxue.base.BaseHolder_two;
import com.android.fangxue.entity.Comment;

/**
 * Created by softsea on 17/11/16.
 */

public class CommadHolder extends BaseHolder_two<Comment.DataBean> {


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
