package com.android.fangxue.adapter.Holder;

import android.view.View;
import android.widget.TextView;

import com.android.fangxue.R;
import com.android.fangxue.base.BaseHolder;
import com.android.fangxue.base.BaseHolder_two;

/**
 * Created by softsea on 17/11/16.
 */

public class CommadHolder extends BaseHolder_two<String> {


    private final View item;

    public CommadHolder(View itemView) {
        super(itemView);
        this.item = itemView;
    }

    @Override
    public void getData(String d) {

        TextView t = item.findViewById(R.id.control2);
        t.setText(d);
    }
}
