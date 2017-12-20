package com.android.fangxue.ui.Detail;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.fangxue.R;
import com.android.fangxue.adapter.Holder.CommadHolder;
import com.android.fangxue.adapter.commonAdapter.RecycCommomAdapter;
import com.android.fangxue.adapter.commonAdapter.RecycCommomAdapter_Two;
import com.android.fangxue.base.BaseActivity;
import com.android.fangxue.base.BaseHolder;
import com.android.fangxue.base.BaseHolder_two;
import com.android.fangxue.callback.MessageCallBack;
import com.android.fangxue.entity.Homework;
import com.android.fangxue.entity.HomeworkDetail;
import com.android.fangxue.newwork.MessageCenter;
import com.android.fangxue.utils.JSONUtils;
import com.android.fangxue.utils.photoPickerUtil.PhotoT;
import com.android.fangxue.widget.CircleImageView;
import com.android.fangxue.widget.CommentDialog;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class NotifyInfo extends BaseActivity implements MessageCallBack {


    @Bind(R.id.back_btn)
    ImageView back;
    @Bind(R.id.toolbar_title)
    TextView title;
    @Bind(R.id.hoemwork_title)
    TextView hoemwork_title;
    @Bind(R.id.hoemwork_date)
    TextView hoemwork_date;
    @Bind(R.id.homework_author)
    TextView homework_author;
    @Bind(R.id.message_content)
    TextView message_content;
    @Bind(R.id.homework_teachername)
    TextView homework_teachername;
    @Bind(R.id.readNum)
    TextView readNum;
    @Bind(R.id.icon_font)
    CircleImageView icon_font;
    @Bind(R.id.photo)
    PhotoT photo;
    @Bind(R.id.reviewList)
    RecyclerView reviewList;
    @Bind(R.id.gv_images)
    GridView gvImages;
    @Bind(R.id.t2)
    LinearLayout t2;
    private NotifyAdapater adapater;
    List<String> list = new ArrayList<>();
    Context contex = this;

    private int notifyId = 0;
    private MessageCenter messageCenter;
    private Observer<String> observer;
    private ArrayList<String> imgc = new ArrayList<>();
    private ArrayList<String> preview = new ArrayList<>();

    @Override
    public void setButterKnife() {
        ButterKnife.bind(this);

    }

    @Override
    public int getLayoutId() {
        return R.layout.ui_info_activity_notify_info;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        title.setText("详情");
        notifyId = getIntent().getIntExtra("notify", 0);
        messageCenter = new MessageCenter();
        SimulationData();
        messageCenter.setCallBackInterFace(this);
        messageCenter.SendYouMessage(messageCenter.ChooseCommand().getmessageinfo_HomeWork(notifyId));
        adapater = new NotifyAdapater(this, list);

        reviewList.setAdapter(adapater);
        reviewList.setLayoutManager(new LinearLayoutManager(this));
        setObserver();
        initView();
        dialog = new CommentDialog(this);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getRepeatCount() == 0)
                    dialog.cancel();
                return false;
            }
        });
    }

    CommentDialog dialog;

    public void setObserver() {
        observer = new Observer<String>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(String s) {
                DealWithData(s);
            }
        };
    }

    /* 評論模塊內容預留*/
    void SimulationData() {
        list.add("张三");
        list.add("李四");
        list.add("王五");
        list.add("赵六");
        list.add("其他");
    }

    private void DealWithData(String s) {
        JSONObject cmd = JSONUtils.StringToJSON(s);

        if (JSONUtils.getString(cmd, "cmd").equals("message.getinfo")) {
            Gson gson = new Gson();
            Type type = new TypeToken<HomeworkDetail>() {
            }.getType();
            //设置具体信息
            HomeworkDetail homeworData = gson.fromJson(s, type);
            setValue(homeworData);
        }
    }

    private void setValue(HomeworkDetail homeworkBean) {
        HomeworkDetail.DataBean dataBean = homeworkBean.getData().get(0);
        hoemwork_title.setText(dataBean.getWorktitle());
        homework_author.setText(dataBean.getLesson());
        photo.setActivity(this);
        readNum.setText(dataBean.getTotal() + "");
        message_content.setText(dataBean.getWorkcontent());
        hoemwork_date.setText(dataBean.getReleasetime());
        homework_teachername.setText(dataBean.getAuthor());
        for (int i = 0; i < dataBean.getPic().size(); i++) {
            preview.add(dataBean.getPic().get(i).getPicpath());
            imgc.add(dataBean.getPic().get(i).getThumbnail());
        }
        a.notifyDataSetChanged();

        photo.setUrl(imgc);
        photo.setPreviewPhoto(preview);
        if (!dataBean.getHeaderimg().isEmpty() && dataBean.getHeaderimg() != null) {
            Glide.with(NotifyInfo.this).load(dataBean.getHeaderimg()).into(icon_font);
        } else {
            Glide.with(NotifyInfo.this).load(R.drawable.username).into(icon_font);
        }

    }

    @OnClick({R.id.back_btn,R.id.t2})
    void Onclick(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
                this.finish();
                break;
                case R.id.t2:

                    dialog.show();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onMessage(String str) {
        Log.e("NotifyInfo", str);
        DealMessageForMe(str, observer);
    }

    class NotifyAdapater extends RecycCommomAdapter_Two {
        private final Context mContext;

        NotifyAdapater(Context context, List<String> dataList) {
            super(context, dataList);
            this.mContext = context;
        }

        @Override
        public BaseHolder_two setViewHolder(ViewGroup parent) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.commad_item, parent, false);
            return new CommadHolder(view);
        }
    }


    private class NineGridAdapter extends CommonAdapter<String> {

        public NineGridAdapter() {
            super(NotifyInfo.this, R.layout.item_grid_image, imgc);
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

    protected Transferee transferee;
    protected TransferConfig config;
    private DisplayImageOptions options;

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
                .setSourceImageList(preview)
                .setThumbnailImageList(imgc)
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

        a = new NotifyInfo.NineGridAdapter();
        gvImages.setAdapter(a);
    }

    private NineGridAdapter a;
    protected static final int READ_EXTERNAL_STORAGE = 100;
    protected static final int WRITE_EXTERNAL_STORAGE = 101;

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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode != WRITE_EXTERNAL_STORAGE) {
            Toast.makeText(this, "请允许获取相册图片文件写入权限", Toast.LENGTH_SHORT).show();
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


    protected void initView() {

        transferee = Transferee.getDefault(this);
        testTransferee();
    }

}
