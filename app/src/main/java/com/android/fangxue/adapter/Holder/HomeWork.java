package com.android.fangxue.adapter.Holder;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.fangxue.R;
import com.android.fangxue.base.BaseHolder;
import com.android.fangxue.callback.mClickInterface;
import com.android.fangxue.entity.Homework;
import com.android.fangxue.ui.Center.ActivityCenter;
import com.android.fangxue.ui.Detail.NotifyDetail;
import com.android.fangxue.ui.Detail.NotifyInfo;
import com.android.fangxue.utils.photoPickerUtil.Photo;
import com.android.fangxue.utils.photoPickerUtil.PhotoT;
import com.bumptech.glide.Glide;


import java.util.ArrayList;


/**
 * Created by softsea on 17/10/23.
 */

public class HomeWork extends BaseHolder<Homework.DataBean> {


    private mClickInterface mClickInterface;
    private View item;
    private int Postion;
    private Context context;
    private PhotoT photo;
    private ArrayList<String> imgc = new ArrayList<>();
    private ArrayList<String> preview = new ArrayList<>();

    private Activity activity;


    public void setPostion(int Postion) {
        this.Postion = Postion;
    }

    public HomeWork(final View itemView, final Context context) {
        super(itemView);

        this.context = context;
        this.item = itemView;
        activity = (Activity) context;

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void getData(final Homework.DataBean d) {

        LinearLayout cardView_item = item.findViewById(R.id.cardView_item);
        TextView tv = item.findViewById(R.id.hoemwork_title);
        TextView author = item.findViewById(R.id.homework_author);
        TextView date = item.findViewById(R.id.hoemwork_date);
        ImageView img = item.findViewById(R.id.icon_font);
        TextView read = item.findViewById(R.id.readNum);
        TextView releaser = item.findViewById(R.id.releaser);
        TextView homework_teachername = item.findViewById(R.id.homework_teachername);
        TextView message_content = item.findViewById(R.id.message_content);
        homework_teachername.setText(d.getAuthor());
        releaser.setText(d.getReleaser() + "");
        read.setText(d.getReadnumber() + "");
        photo = item.findViewById(R.id.photo);
        author.setText(d.getLesson());
        tv.setText(d.getWorktitle());
        date.setText(d.getReleasetime());

        if (d.getHeaderimg() != null)
            Glide.with(context).load(d.getHeaderimg()).into(img);
        else
            img.setImageDrawable(context.getResources().getDrawable(R.drawable.username));

        photo.setActivity(activity);
        photo.setClickId(d.getId());
        imgc.clear();
        preview.clear();
        for (int i = 0; i < d.getPic().size(); i++) {
            preview.add(d.getPic().get(i).getPicpath());
            imgc.add(d.getPic().get(i).getThumbnail());
        }
        photo.setUrl(imgc);

        photo.setPreviewPhoto(preview);
        message_content.setText(d.getWorkcontent());

        cardView_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, NotifyInfo.class);
                intent.putExtra("notify", d.getId());
                context.startActivity(intent);
            }
        });
//        if (d.getShow() == 1) {

////            read.setBackground(null);
//        } else {
//            read.setVisibility(View.VISIBLE);
//        }

    }


    public void setmClickInterface(mClickInterface mclickInterface) {
        this.mClickInterface = mclickInterface;
    }
}
