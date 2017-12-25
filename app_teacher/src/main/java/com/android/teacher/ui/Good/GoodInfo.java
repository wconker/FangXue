package com.android.teacher.ui.Good;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.teacher.R;
import com.android.teacher.base.BaseActivity;
import com.android.teacher.widget.AmountView;
import com.android.teacher.widget.GoodsViewGroup;
import com.android.teacher.widget.banner.BannerEntity;
import com.android.teacher.widget.banner.BannerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GoodInfo extends BaseActivity implements View.OnClickListener, GoodsViewGroup.OnGroupItemClickListener {


    private AmountView mAmountView;
    private GoodsViewGroup<TextView> mGroup;

    private ArrayList<String> viewtexts = new ArrayList<>();

    private int chooseID = -1;
    private String chooseText;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    private BannerView bannerView;
    private List<BannerEntity> bannerEntitys = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.ui_good_goodinfo;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        mAmountView = (AmountView) findViewById(R.id.amount_view);
        mAmountView.setGoods_storage(50);
        mAmountView.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {
                Toast.makeText(getApplicationContext(), "Amount=>  " + amount, Toast.LENGTH_SHORT).show();
            }
        });

        mGroup = (GoodsViewGroup) findViewById(R.id.viewGroup);

        String text;
        for (int i = 0; i < 5; i++) {
            text = "抄码/" + i + "公斤(多退少补)";
            viewtexts.add(text);
        }

        mGroup.addItemViews(viewtexts, GoodsViewGroup.TEV_MODE);
        mGroup.setGroupClickListener(this);

    }

    void setBanner() {
        BannerEntity a = new BannerEntity("", "this is c", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1502767947193&di=979b6dc70284125f1db26cb7acb841e4&imgtype=0&src=http%3A%2F%2Fwww.lax.gov.cn%2Fupload%2Fimages%2F2015%2F7%2F1_20150724080730_odo3l.jpg");
        BannerEntity b = new BannerEntity("", "this is b", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503362715&di=974e1c52befaf3aec0d54644813f054c&imgtype=jpg&er=1&src=http%3A%2F%2Fwww.ytcnc.net%2Fuploadfile%2F2015%2F1201%2F20151201105720701.jpg");
        BannerEntity c = new BannerEntity("", "this is a", "http://i0.hdslb.com/bfs/face/3a2f09a300c61b1321f46d027f01838a080b1d6b.jpg");
        bannerEntitys.add(a);
        bannerEntitys.add(b);
        bannerEntitys.add(c);

        bannerView = (BannerView) this.findViewById(R.id.bannerView);
        bannerView.delayTime(5).build(bannerEntitys);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        toolbarTitle.setText("商品详情");
        setBanner();
    }

    @Override
    public void setButterKnife() {

    }

    @OnClick({R.id.back_btn})
    public void onClick(View view) {
        finish();
    }

    @Override
    public void onGroupItemClick(int item) {

    }
}
