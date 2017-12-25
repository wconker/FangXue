package com.android.teacher.adapter.listlinkadapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.teacher.R;
import com.android.teacher.ui.Good.GoodList;

import java.util.List;

/**
 * 项目名：  ListViewLinkage
 * 包名：    com.example.lilinxiong.listviewlinkage.Adapter
 * 文件名:   RightAdapter
 * 创建者:   LLX
 * 创建时间:  2017/4/17 19:03
 * 描述：    右侧ListViewAdapter
 */
public class RightAdapter extends CustomizeLVBaseAdapter {
    //上下文
    private Context mContext;
    //标题
    private List<String> leftStr;
    //内容
    private List<List<String>> rightStr;
    private LayoutInflater inflater;

    public RightAdapter(Context mContext, List<String> leftStr, List<List<String>> rightStr) {
        this.mContext = mContext;
        this.leftStr = leftStr;
        this.rightStr = rightStr;
        //系统服务
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Object getItem(int section, int position) {
        return rightStr.get(section).get(position);
    }

    @Override
    public long getItemId(int section, int position) {
        return position;
    }

    @Override
    public int getSectionCount() {
        return leftStr.size();
    }

    @Override
    public int getCountForSection(int section) {
        return rightStr.get(section).size();
    }

    @Override
    public View getItemView(final int section, final int position, View convertView, ViewGroup parent) {
        ChildViewHolder holder = null;
        if (convertView == null) {
            holder = new ChildViewHolder();
            //加载
            convertView = inflater.inflate(R.layout.lv_customize_item_right, parent, false);
            //绑定
            holder.lv_customize_item_image = (ImageView) convertView.findViewById(R.id.lv_customize_item_image);
            holder.lv_customize_item_text = (TextView) convertView.findViewById(R.id.lv_customize_item_text);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }
        //设置内容
        holder.lv_customize_item_text.setText(rightStr.get(section).get(position));
        //点击事件
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, rightStr.get(section).get(position), Toast.LENGTH_SHORT).show();
                mContext.startActivity(new Intent(mContext, GoodList.class));
            }
        });
        return convertView;
    }

    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        HeaderViewHolder holder = null;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            //加载
            convertView = inflater.inflate(R.layout.lv_customize_item_header, parent, false);
            //绑定
            holder.lv_customize_item_header_text = (TextView) convertView.findViewById(R.id.lv_customize_item_header_text);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        //不可点击
        convertView.setClickable(false);
        //设置标题
        holder.lv_customize_item_header_text.setText(leftStr.get(section));
        return convertView;
    }

    class ChildViewHolder {
        //Item图片
        private ImageView lv_customize_item_image;
        //Item内容
        private TextView lv_customize_item_text;
    }

    class HeaderViewHolder {
        //标题
        private TextView lv_customize_item_header_text;
    }
}

