package com.android.fangxue.adapter.IndexViewAdapter;

import android.content.Context;

import com.android.fangxue.R;
import com.android.fangxue.adapter.commonAdapter.CommonAdapter;
import com.android.fangxue.adapter.commonAdapter.CommonViewHolder;
import com.android.fangxue.entity.Carousel.Picture;

import java.util.List;

/**
 * Created by fangxue on 17/8/10.
 */

public class gvAdapter extends CommonAdapter<Picture> {

    public gvAdapter(Context context, List list, int layoutId) {
        super(context, list, layoutId);
    }


    @Override
    public void setViewContent(CommonViewHolder viewHolder, Picture picture) {
            viewHolder.setText(R.id.gv_title, picture.getName())
                    .setImageResource(R.id.gv_image, picture.getDrawable());

    }
}
