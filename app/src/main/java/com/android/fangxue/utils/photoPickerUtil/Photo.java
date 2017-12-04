package com.android.fangxue.utils.photoPickerUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.android.fangxue.R;

import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

/**
 * Created by kanghui on 2017/3/17.
 */

public class Photo extends LinearLayout {
    private ArrayList<String> selectedPhotos = new ArrayList<>();
    private PhotoAdapter photoAdapter;
    private View viewAppTitle;
    private Activity activity;



    public boolean IfDele = false;



    public Photo(Context context) {
        super(context);
        init();
    }

    public Photo(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Photo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public Photo(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    public void setActivity(Activity _activity) {
        this.activity = _activity;

    }

    //设置图片
    public void setPhoto(ArrayList<String> selectedPhotos) {
        this.selectedPhotos = selectedPhotos;
    }

    //获取图片
    public ArrayList<String> getPhoto() {
        return this.selectedPhotos;
    }


    public void setResult(Intent data) {
        List<String> photos = null;
        if (data != null) {
            photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
        }
        selectedPhotos.clear();
        if (photos != null) {
            selectedPhotos.addAll(photos);
        }
        photoAdapter.notifyDataSetChanged();
    }

    public void setUrl(List<String> photos) {

        selectedPhotos.clear();
        if (photos != null) {
            selectedPhotos.addAll(photos);
        }
        photoAdapter.notifyDataSetChanged();
    }

    void init() {
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        viewAppTitle = inflater.inflate(R.layout.photoview, null);
        RecyclerView recyclerView = (RecyclerView) viewAppTitle.findViewById(R.id.recycler_view);
        photoAdapter = new PhotoAdapter(getContext(), selectedPhotos);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        recyclerView.setAdapter(photoAdapter);
        photoAdapter.setClick(new PhotoAdapter.ClickOnList() {
            @Override
            public void Box(int pos) {



                if (photoAdapter.getItemViewType(pos) == PhotoAdapter.TYPE_ADD) {
                    PhotoPicker.builder()
                            .setPhotoCount(PhotoAdapter.MAX)
                            .setShowCamera(true)
                            .setPreviewEnabled(false)
                            .setSelected(selectedPhotos)
                            .start(activity);
                } else {
                    PhotoPreview.builder()
                            .setPhotos(selectedPhotos)
                            .setCurrentItem(pos)
                            .start(activity);
                }
            }

            @Override
            public void Close(int pos) {
                if (IfDele) {
                    //var sCmd = { "cmd": "business.del_ftpimg", "data": { "id": e.currentTarget.id } };


                }
                selectedPhotos.remove(pos);
                photoAdapter.notifyDataSetChanged();


            }
        });

        this.addView(viewAppTitle, layoutParams);
    }
}
