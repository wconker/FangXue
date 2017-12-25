package com.android.teacher.adapter.IndexViewAdapter;

import android.content.Context;

import com.android.teacher.R;
import com.android.teacher.base.CommonAdapter;
import com.android.teacher.base.CommonViewHolder;
import com.android.teacher.entity.Carousel.Picture;

import java.util.List;

/**
 * Created by softsea on 17/8/10.
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
