package com.android.teacher.utils.photoPickerUtil;

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

import com.android.teacher.R;
import com.foamtrace.photopicker.intent.PhotoPreviewIntent;

import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;

/**
 * Created by wu on 2017/3/27.
 */

public class PhotoT extends LinearLayout {
    private ArrayList<String> selectedPhotos = new ArrayList<>();
    private ArrayList<String> previewPhoto = new ArrayList<>();
    private PhotoAdapterT photoAdapter;
    private View viewAppTitle;
    private Activity activity;
    private PhotoPreviewIntent Preview;


    public PhotoT(Context context) {
        super(context);
        init();
    }

    public PhotoT(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PhotoT(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public PhotoT(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    public void setPreviewPhoto(ArrayList<String> previewPhoto) {
        this.previewPhoto = previewPhoto;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
        Preview = new PhotoPreviewIntent(activity);

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
        photoAdapter = new PhotoAdapterT(getContext(), selectedPhotos);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));
        recyclerView.setAdapter(photoAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(activity, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Preview.setCurrentItem(position); // 当前选中照片的下标
                Preview.setPhotoPaths(previewPhoto); // 已选中的照片地址
                activity.startActivityForResult(Preview, 100);


            }
        }));
        this.addView(viewAppTitle, layoutParams);
    }
}