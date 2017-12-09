package com.android.fangxue.adapter.MyAdapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.fangxue.R;
import com.android.fangxue.entity.CourseBean;
import com.android.fangxue.utils.TimeUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by wukanghui on 2017/9/2.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<CourseBean.DataBean> dataSource;
    private final int SIGN_IN = 100;
    private final int SIGN_OUT = 1000;
    private final int NORMAL = 0;
    private int Today = 0;

    public MyRecyclerViewAdapter(Context context, List<CourseBean.DataBean> data) {
        this.dataSource = data;
        this.mContext = context;
    }

    void setCurrentSchedule() {

        Today = 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType != -1) {
            return new viewH(LayoutInflater.from(mContext).inflate(R.layout.utils_gridview_item, parent, false));
        } else {

            return new viewH(LayoutInflater.from(mContext).inflate(R.layout.no_class, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        if (dataSource.size() > 0 && dataSource.get(0) != null) {
            viewH h = (viewH) holder;
            Time t = new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料
            t.setToNow(); // 取得系统时间。
            int hour = t.hour;    // 0-23
            int millin = t.minute;
            String str = dataSource.get(position).getFromtime() + "一" + dataSource.get(position).getTotime();
            if (h.time != null) {
                h.time.setText(str);
                h.teacher.setText(dataSource.get(position).getFromtime());
                h.describe.setText(dataSource.get(position).getCoursename());
                h.class_describe.setText(dataSource.get(position).getSectionname());


                if (TimeUtil.getCurrentTimeBySysbol(false).equals(dataSource.get(position).getStudyday())) {
                    if (position == 0) {
                        changeStyle(((viewH) holder), R.color.gray_80, R.color.gray_80);
                    }
                    if (TimeCompare(dataSource.get(position).getTotime(), hour + ":" + millin) == 2) {
                        if (TimeCompare(dataSource.get(position).getFromtime(), hour + ":" + millin) == 1)
                            changeStyle(((viewH) holder), R.color.orange, R.color.orange);
                        else {
                            GradientDrawable gradientDrawable = (GradientDrawable) ((viewH) holder).gv_image.getBackground();
                            gradientDrawable.setColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
                            ((viewH) holder).gv_image.setBackground(gradientDrawable);
                        }
                    } else {
                        changeStyle(((viewH) holder), R.color.gray_80, R.color.gray_80);
                    }
                }
            }
        }

    }

    private void changeStyle(viewH holder, int color1, int color2) {

        if (((viewH) holder).gv_image != null) {
            GradientDrawable gradientDrawable = (GradientDrawable) ((viewH) holder).gv_image.getBackground();
            gradientDrawable.setColor(ContextCompat.getColor(mContext, color1));
            holder.gv_image.setBackground(gradientDrawable);
            holder.describe.setTextColor(ContextCompat.getColor(mContext, color2));
            holder.teacher.setTextColor(ContextCompat.getColor(mContext, color2));
            holder.time.setTextColor(ContextCompat.getColor(mContext, color2));
            holder.class_describe.setTextColor(ContextCompat.getColor(mContext, color2));
        }
    }

    private int TimeCompare(String date1, String date2) {

        int res = 0;
        //格式化时间
        SimpleDateFormat CurrentTime = new SimpleDateFormat("HH:mm");
        try {
            Date beginTime = CurrentTime.parse(date1);
            Date endTime = CurrentTime.parse(date2);
            //判断是否大于两天
            if (((endTime.getTime() - beginTime.getTime()) / (1000)) >= 0) {   //
                res = 1; //已经过去的
            } else {
                res = 2;
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;

    }

    @Override
    public int getItemCount() {

        return dataSource.size();

    }

    @Override
    public int getItemViewType(int position) {


        if (dataSource.size() < 1) {
            return -1;
        }
            return 1;

    }

    class viewH extends RecyclerView.ViewHolder {

        Button gv_image;
        TextView describe;
        TextView teacher;
        TextView time;
        TextView class_describe;

        viewH(View itemView) {
            super(itemView);
            gv_image = itemView.findViewById(R.id.gv_image);
            describe = itemView.findViewById(R.id.describe);
            teacher = itemView.findViewById(R.id.teacher);
            time = itemView.findViewById(R.id.time);
            class_describe = itemView.findViewById(R.id.class_describe);
        }
    }
}
