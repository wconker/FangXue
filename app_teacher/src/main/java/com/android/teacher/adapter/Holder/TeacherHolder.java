package com.android.teacher.adapter.Holder;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.teacher.R;
import com.android.teacher.base.BaseHolder;
import com.android.teacher.callback.mClickInterface;
import com.android.teacher.entity.StudentBean;
import com.android.teacher.entity.TeacherBean;
import com.android.teacher.utils.SharedPrefsUtil;
import com.android.teacher.widget.CircleImageView;
import com.bumptech.glide.Glide;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

/**
 * Created by softsea on 17/11/1.
 */

public class TeacherHolder extends BaseHolder<TeacherBean.DataBean> {


    private View v;
    public int pos = 0;
    private mClickInterface clickInterface;
    private Context mContext;

    public TeacherHolder(View itemView) {
        super(itemView);
        v = itemView;
    }

    @Override
    public int getPost(int Pos) {
        this.pos = Pos;
        return Pos;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public void setListener(mClickInterface mClickInterface) {
        this.clickInterface = mClickInterface;
        LinearLayout cardView = v.findViewById(R.id.cv_img_activity);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickInterface.doClick(pos, v);
            }
        });
    }


    @Override
    public void getData(TeacherBean.DataBean d) {
        ImageView flag = v.findViewById(R.id.arrFlag);
        CircleImageView tx = v.findViewById(R.id.tx);
        TextView t = v.findViewById(R.id.student_name);
        SwipeMenuLayout swipeMenuLayout = v.findViewById(R.id.SwipeMenuLayout);
        if (SharedPrefsUtil.getValue(mContext, "teacherXML", "roly", "0").equals("1")) {
            swipeMenuLayout.setSwipeEnable(true);
        } else {
            swipeMenuLayout.setSwipeEnable(false);
        }

        flag.setVisibility(View.GONE);
        Button edit, del;
        edit = v.findViewById(R.id.edit);
        del = v.findViewById(R.id.del);

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickInterface != null) {
                    clickInterface.doClick();
                }
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickInterface != null) {
                    clickInterface.doClick(pos, view);
                }
            }
        });


        if (d.getHeaderimg() == null) {
            Glide.with(mContext).load(R.drawable.username).into(tx);
        } else {
            Glide.with(mContext).load(d.getHeaderimg()).into(tx);
        }

        t.setText(d.getTeachername());

    }
}
