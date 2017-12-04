package com.android.fangxue.utils.photoPickerUtil;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.fangxue.R;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

import me.iwf.photopicker.utils.AndroidLifecycleUtils;


/**
 * Created by donglua on 15/5/31.
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

    private ArrayList<String> photoPaths = new ArrayList<String>();
    private LayoutInflater inflater;

    private Context mContext;

    public final static int TYPE_ADD = 1;
    public final static int TYPE_PHOTO = 2;

    public final static int MAX = 9;

    public PhotoAdapter(Context mContext, ArrayList<String> photoPaths) {
        this.photoPaths = photoPaths;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);

    }

    public void setClick(ClickOnList clickOnList) {
        this.cc = clickOnList;
    }

    private ClickOnList cc;

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        switch (viewType) {
            case TYPE_ADD:
                itemView = inflater.inflate(R.layout.photopicker_item, parent, false);
                break;
            case TYPE_PHOTO:
                itemView = inflater.inflate(me.iwf.photopicker.R.layout.__picker_item_photo, parent, false);
                break;
        }
        return new PhotoViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final PhotoViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_ADD) {
            if (holder.textView != null) {
                holder.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cc.Box(position);
                    }
                });
            }
        }
        if (getItemViewType(position) == TYPE_PHOTO) {

            boolean canLoadImage = AndroidLifecycleUtils.canLoadImage(holder.ivPhoto.getContext());
            if (holder.vSelected != null) {
                holder.vSelected.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        cc.Close(position);
                    }
                });
            }
            if (holder.ivPhoto != null) {
                holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cc.Box(position);
                    }
                });
            }
            Object uri = null;

            if (photoPaths.get(position).substring(0, 4).equals("http")) {
                uri = photoPaths.get(position);
            } else {
                uri = Uri.fromFile(new File(photoPaths.get(position)));
            }

            if (canLoadImage) {
                Glide.with(mContext)
                        .load(uri)
                        .centerCrop()
                        .thumbnail(0.1f)
                        .placeholder(R.drawable.__picker_ic_photo_black_48dp)
                        .error(R.drawable.__picker_ic_broken_image_black_48dp)
                        .into(holder.ivPhoto);
            }
        }
    }


    @Override
    public int getItemCount() {
        int count = photoPaths.size() + 1;
        if (count > MAX) {
            count = MAX;
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == photoPaths.size() && position != MAX) ? TYPE_ADD : TYPE_PHOTO;
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPhoto;
        private ImageView vSelected;
        private TextView textView;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            ivPhoto = (ImageView) itemView.findViewById(R.id.iv_photo);
            vSelected = (ImageView) itemView.findViewById(R.id.v_selected);
            textView = (TextView) itemView.findViewById(R.id.textView);

        }
    }


    public interface ClickOnList {
        void Box(int pos);

        void Close(int pos);
    }


}
