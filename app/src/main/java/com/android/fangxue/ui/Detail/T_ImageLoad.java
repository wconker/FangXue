package com.android.fangxue.ui.Detail;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.fangxue.R;
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

public class T_ImageLoad extends AppCompatActivity {

    protected static final int READ_EXTERNAL_STORAGE = 100;
    protected static final int WRITE_EXTERNAL_STORAGE = 101;

    protected Transferee transferee;
    protected TransferConfig config;

    protected GridView gvImages;
    protected List<String> thumbnailImageList;
    protected List<String> sourceImageList;

    private DisplayImageOptions options;

    {
        thumbnailImageList = new ArrayList<>();
        thumbnailImageList.add("http://static.fdc.com.cn/avatar/sns/1486263782969.png@233w_160h_20q");
        thumbnailImageList.add("http://static.fdc.com.cn/avatar/sns/1485055822651.png@233w_160h_20q");
        thumbnailImageList.add("http://static.fdc.com.cn/avatar/sns/1486194909983.png@233w_160h_20q");
        thumbnailImageList.add("http://static.fdc.com.cn/avatar/sns/1486194996586.png@233w_160h_20q");
        thumbnailImageList.add("http://static.fdc.com.cn/avatar/sns/1486195059137.png@233w_160h_20q");
        thumbnailImageList.add("http://static.fdc.com.cn/avatar/sns/1486173497249.png@233w_160h_20q");
        thumbnailImageList.add("http://static.fdc.com.cn/avatar/sns/1486173526402.png@233w_160h_20q");
        thumbnailImageList.add("http://static.fdc.com.cn/avatar/sns/1486173639603.png@233w_160h_20q");
        thumbnailImageList.add("http://static.fdc.com.cn/avatar/sns/1486172566083.png@233w_160h_20q");

        sourceImageList = new ArrayList<>();
        sourceImageList.add("http://static.fdc.com.cn/avatar/sns/1486263782969.png");
        sourceImageList.add("http://static.fdc.com.cn/avatar/sns/1485055822651.png");
        sourceImageList.add("http://static.fdc.com.cn/avatar/sns/1486194909983.png");
        sourceImageList.add("http://static.fdc.com.cn/avatar/sns/1486194996586.png");
        sourceImageList.add("http://static.fdc.com.cn/avatar/sns/1486195059137.png");
        sourceImageList.add("http://static.fdc.com.cn/avatar/sns/1486173497249.png");
        sourceImageList.add("http://static.fdc.com.cn/avatar/sns/1486173526402.png");
        sourceImageList.add("http://static.fdc.com.cn/avatar/sns/1486173639603.png");
        sourceImageList.add("http://static.fdc.com.cn/avatar/sns/1486172566083.png");
    }

    protected int getContentView() {
        return R.layout.activity_t__image_load;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getContentView());
        initView();

    }

    protected void initView() {

        transferee = Transferee.getDefault(this);
        gvImages = (GridView) findViewById(R.id.gv_images);
        testTransferee();
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

    /**
     * 使用 Glide 作为图片加载器时，保存图片到相册使用的方法
     *
     * @param imageView
     */
    protected void saveImageByGlide(ImageView imageView) {
        if (checkWriteStoragePermission()) {
//            GlideBitmapDrawable bmpDrawable = (GlideBitmapDrawable) imageView.getDrawable();
//            MediaStore.Images.Media.insertImage(
//                    getContentResolver(),
//                    bmpDrawable.getBitmap(),
//                    String.valueOf(System.currentTimeMillis()),
//                    "");
            Toast.makeText(this, "save success", Toast.LENGTH_SHORT).show();
        }
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
                    getContentResolver(),
                    bmpDrawable.getBitmap(),
                    String.valueOf(System.currentTimeMillis()),
                    "");
            Toast.makeText(this, "save success", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkWriteStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_EXTERNAL_STORAGE);
            return false;
        }
        return true;
    }

    protected void testTransferee() {
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
        options = new DisplayImageOptions
                .Builder()
                .showImageOnLoading(R.drawable.username)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .resetViewBeforeLoading(true)
                .build();

        config = TransferConfig.build()
                .setSourceImageList(sourceImageList)
                .setThumbnailImageList(thumbnailImageList)
                .setMissPlaceHolder(R.drawable.username)
                .setErrorPlaceHolder(R.mipmap.logo144)
                .setProgressIndicator(new ProgressPieIndicator())
                .setIndexIndicator(new NumberIndexIndicator())
                .setJustLoadHitImage(true)
                .setImageLoader(UniversalImageLoader.with(getApplicationContext()))
                .setOnLongClcikListener(new Transferee.OnTransfereeLongClickListener() {
                    @Override
                    public void onLongClick(ImageView imageView, int pos) {
                        saveImageByUniversal(imageView);
                    }
                })
                .create();

        gvImages.setAdapter(new NineGridAdapter());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode != WRITE_EXTERNAL_STORAGE) {
            Toast.makeText(this, "请允许获取相册图片文件写入权限", Toast.LENGTH_SHORT).show();
        }
    }

    private class NineGridAdapter extends CommonAdapter<String> {

        public NineGridAdapter() {
            super(T_ImageLoad.this, R.layout.item_grid_image, thumbnailImageList);
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

    private void bindTransferee(ImageView imageView, final int position) {
        // 如果指定了缩略图，那么缩略图一定要先加载完毕
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                config.setNowThumbnailIndex(position);
                config.setOriginImageList(wrapOriginImageViewList(thumbnailImageList.size()));
                transferee.apply(config).show();
            }
        });
    }

}