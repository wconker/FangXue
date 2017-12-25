package com.android.teacher.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

import com.android.teacher.R;
import com.android.teacher.utils.photoPickerUtil.PhotoAdapter;
import com.hitomi.tilibrary.style.index.NumberIndexIndicator;
import com.hitomi.tilibrary.style.progress.ProgressPieIndicator;
import com.hitomi.tilibrary.transfer.TransferConfig;
import com.hitomi.tilibrary.transfer.Transferee;
import com.hitomi.universalloader.UniversalImageLoader;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

/**
 * Created by softsea on 17/12/18.
 */

public class Preview extends LinearLayout {


    private View viewAppTitle;
    private Activity activity;
    private Context mContext;
    private GridView gvImages;
    private List<String> preview;
    private List<String> imgc;
    protected Transferee transferee;
    protected TransferConfig config;
    private DisplayImageOptions options;

    private class NineGridAdapter extends CommonAdapter<String> {

        public NineGridAdapter() {
            super(activity, R.layout.item_grid_image, imgc);
        }

        @Override
        protected void convert(ViewHolder viewHolder, String item, final int position) {
            final ImageView imageView = viewHolder.getView(R.id.image_view);
            ImageLoader.getInstance().displayImage(item, imageView, options, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    bindTransferee(imageView, position);

                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                }
            });
        }
    }


    private NineGridAdapter a;
    protected static final int READ_EXTERNAL_STORAGE = 100;
    protected static final int WRITE_EXTERNAL_STORAGE = 101;

    public Preview(Context context) {
        super(context);

    }

    public Preview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    public Preview(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public Preview(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

    }

    public void setImgSource(List<String> _preview, List<String> _imgc) {
        imgc = _imgc;
        preview = _preview;
        a.notifyDataSetChanged();
    }

    public void init(Activity _activity, List<String> _preview, List<String> _imgc) {

        imgc = _imgc;
        preview = _preview;
        activity = _activity;
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        viewAppTitle = inflater.inflate(R.layout.preview_item, null);
        testTransferee();
        gvImages = (GridView) viewAppTitle.findViewById(R.id.gv_images);
        a = new NineGridAdapter();
        gvImages.setAdapter(a);
        this.addView(viewAppTitle, layoutParams);
    }

    private void testTransferee() {
        transferee = Transferee.getDefault(activity);

        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(activity));
        options = new DisplayImageOptions
                .Builder()
                .showImageOnLoading(R.drawable.loadicon)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .resetViewBeforeLoading(true)
                .build();

        config = TransferConfig.build()
                .setSourceImageList(preview)
                .setThumbnailImageList(imgc)
                .setMissPlaceHolder(R.drawable.dismiss)
                .setErrorPlaceHolder(R.drawable.error)
                .setProgressIndicator(new ProgressPieIndicator())
                .setIndexIndicator(new NumberIndexIndicator())
                .setJustLoadHitImage(true)
                .setImageLoader(UniversalImageLoader.with(activity.getApplicationContext()))
                .setOnLongClcikListener(new Transferee.OnTransfereeLongClickListener() {
                    @Override
                    public void onLongClick(ImageView imageView, int pos) {
                        saveImageByUniversal(imageView);
                    }
                }).create();


    }

    /**
     * 使用 Universal 作为图片加载器时，保存图片到相册使用的方法
     *
     * @param imageView
     */
    protected void saveImageByUniversal(ImageView imageView) {
        if (checkWriteStoragePermission()) {
            BitmapDrawable bmpDrawable = (BitmapDrawable) imageView.getDrawable();
            MediaStore.Images.Media.insertImage(
                    activity.getContentResolver(),
                    bmpDrawable.getBitmap(),
                    String.valueOf(System.currentTimeMillis()),
                    "");
            android.widget.Toast.makeText(activity, "", android.widget.Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkWriteStoragePermission() {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_EXTERNAL_STORAGE);
            return false;
        }
        return true;
    }

    private void bindTransferee(ImageView imageView, final int position) {
        // 如果指定了缩略图，那么缩略图一定要先加载完毕
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                config.setNowThumbnailIndex(position);
                config.setOriginImageList(wrapOriginImageViewList(imgc.size()));
                transferee.apply(config).show();
            }
        });
    }

    /**
     * 包装缩略图 ImageView 集合
     * <p>
     * 注意：此方法只是为了收集 Activity 列表中所有可见 ImageView 好传递给 transferee。
     * 如果你添加了一些图片路径，扩展了列表图片个数，让列表超出屏幕，导致一些 ImageViwe 不
     * 可见，那么有可能这个方法会报错。这种情况，可以自己根据实际情况，来设置 transferee 的
     * originImageList 属性值
     *
     * @return
     */
    @NonNull
    protected List<ImageView> wrapOriginImageViewList(int size) {
        List<ImageView> originImgList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            ImageView thumImg = (ImageView) ((LinearLayout) gvImages.getChildAt(i)).getChildAt(0);
            originImgList.add(thumImg);
        }
        return originImgList;
    }

}
