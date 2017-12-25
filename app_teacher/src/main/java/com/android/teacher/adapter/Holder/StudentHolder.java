package com.android.teacher.adapter.Holder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.teacher.R;
import com.android.teacher.base.BaseHolder;
import com.android.teacher.callback.mClickInterface;
import com.android.teacher.entity.SchoolBean;
import com.android.teacher.entity.StudentBean;
import com.android.teacher.ui.auth.GetPhoneForRegister;
import com.android.teacher.utils.Collect;
import com.android.teacher.utils.SharedPrefsUtil;
import com.android.teacher.widget.CircleImageView;
import com.bumptech.glide.Glide;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import org.w3c.dom.Text;

/**
 * Created by softsea on 17/11/1.
 */

public class StudentHolder extends BaseHolder<StudentBean.DataBean> {


    private View v;
    public int pos = 0;
    private mClickInterface clickInterface;
    private Context mContext;
    private TextView tName;

    public StudentHolder(View itemView) {
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
    public void getData(final StudentBean.DataBean d) {
        CircleImageView tx = v.findViewById(R.id.tx);
        ImageView flag = v.findViewById(R.id.arrFlag);
        tName = v.findViewById(R.id.student_name);
        SwipeMenuLayout swipeMenuLayout = v.findViewById(R.id.SwipeMenuLayout);

        //如果不是管理员就禁止使用侧滑删除修改的功能
        if (SharedPrefsUtil.getValue(mContext, "teacherXML", "roly", "").equals("1")) {
            swipeMenuLayout.setSwipeEnable(true);
        } else {
            swipeMenuLayout.setSwipeEnable(false);
        }

        //没有id或者名字说明是第一个元素 则定义为添加功能
        if (d.getStudentid() == 0 && d.getStudentname() == null) {
            Glide.with(mContext).load(R.drawable.imgadd).into(tx);
            flag.setVisibility(View.GONE);
            tName.setVisibility(View.GONE);
            swipeMenuLayout.setSwipeEnable(false);
        } else {
            tName.setText(d.getStudentname());
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
                        clickInterface.doClick(d.getStudentid(), view);
                    }
                }
            });
            if (d.getHeaderimg() == null) {
                Glide.with(mContext).load(R.drawable.username).into(tx);
            } else {
                Glide.with(mContext).load(d.getHeaderimg()).into(tx);
            }
            if (d.getArrivetime() != null && d.getArrivetime().equals("00:00")) {
                flag.setVisibility(View.GONE);
            } else {
                flag.setVisibility(View.VISIBLE);
            }



        }

    }
}
