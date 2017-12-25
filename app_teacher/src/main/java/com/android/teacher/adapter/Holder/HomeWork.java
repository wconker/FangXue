package com.android.teacher.adapter.Holder;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.teacher.callback.mClickInterface;
import com.android.teacher.R;
import com.android.teacher.base.BaseHolder;
import com.android.teacher.entity.MessageDetailBean;
import com.android.teacher.ui.Info.NotifyDetail;
import com.android.teacher.ui.Info.NotifyInfo;
import com.bumptech.glide.Glide;


/**
 * Created by softsea on 17/10/23.
 */

public class HomeWork extends BaseHolder<MessageDetailBean.DataBean> {


    private mClickInterface mClickInterface;
    private View item;
    private int Postion;
    private Context context;

    public void setPostion(int Postion) {
        this.Postion = Postion;
    }

    public HomeWork(final View itemView, Context context) {
        super(itemView);

        this.context = context;
        this.item = itemView;

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void getData(final MessageDetailBean.DataBean d) {


        CardView cardView_item = item.findViewById(R.id.cardView_item);
        View read = item.findViewById(R.id.read);
        Button btnDel = item.findViewById(R.id.btnDelete);
        final TextView tv = item.findViewById(R.id.hoemwork_title);
        TextView date = item.findViewById(R.id.hoemwork_date);
        TextView lesson = item.findViewById(R.id.homework_author);
        ImageView owner = item.findViewById(R.id.owner);
        ImageView img = item.findViewById(R.id.icon_font);

        cardView_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, NotifyInfo.class);
                intent.putExtra("notify", d.getId());
                context.startActivity(intent);
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mClickInterface!=null)
                    mClickInterface.doClick(Postion, view);
            }
        });
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mClickInterface!=null)
                    mClickInterface.doClick(Postion, view);
            }
        });
        lesson.setText(d.getLesson());
        if (d.getHeaderimg() == null) {
            Glide.with(context).load(R.drawable.username).into(img);
        } else {
            Glide.with(context).load(d.getHeaderimg()).into(img);
        }
        tv.setText(d.getWorktitle());
        date.setText(d.getReleasetime());

        if(d.getDelflag()==1)
        {
            owner.setVisibility(View.VISIBLE);
        }else {
            owner.setVisibility(View.GONE);
        }

    }

    public void setmClickInterface(mClickInterface mclickInterface) {
        this.mClickInterface = mclickInterface;
    }
}
