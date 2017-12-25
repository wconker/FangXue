package com.android.teacher.ui.Good;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.teacher.R;
import com.android.teacher.adapter.ExpandAdapter.ConstellationAdapter;
import com.android.teacher.adapter.ExpandAdapter.GirdDropDownAdapter;
import com.android.teacher.adapter.ExpandAdapter.ListDropDownAdapter;
import com.yyydjk.library.DropDownMenu;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GoodList extends AppCompatActivity {
    @Bind(R.id.dropDownMenu)
    DropDownMenu mDropDownMenu;
    @Bind(R.id.back_btn)
    ImageView backBtn;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.goodListView)
    RecyclerView goodListView;
    List<String> mData = new ArrayList<>();

    private int constellationPosition = 0;
    private String headers[] = {"排序", "品牌", "产地", "菜系"};
    private List<View> popupViews = new ArrayList<>();

    private GirdDropDownAdapter cityAdapter;
    private ListDropDownAdapter ageAdapter;
    private ListDropDownAdapter sexAdapter;
    private ConstellationAdapter constellationAdapter;

    private String citys[] = {"加个从高到低", "加个从低到高", "销量从低到高", "销量从高到低"};
    private String ages[] = {"不限", "长江桂柳", "六和食品", "安井食品", "正大食品", "新胜食品"};
    private String sexs[] = {"上海", "江苏", "浙江", "安徽", "福建", "江西", "山东", "宁波", "夏门", "青岛"};
    private String constellations[] = {"沙县专区", "沙县专区", "沙县专区", "沙县专区", "沙县专区", "沙县专区", "黄焖鸡店", "沙县专区", "黄焖鸡店", "黄焖鸡店", "黄焖鸡店", "黄焖鸡店",
            "黄焖鸡店"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_good_goodlist_main);
        ButterKnife.bind(this);
        toolbarTitle.setText("商品列表");
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        bindListView();
        initView();

    }

    private void initView() {
        //init city menu
        final ListView cityView = new ListView(this);
        cityAdapter = new GirdDropDownAdapter(this, Arrays.asList(citys));
        cityView.setDividerHeight(0);
        cityView.setAdapter(cityAdapter);

        //init age menu
        final ListView ageView = new ListView(this);
        ageView.setDividerHeight(0);
        ageAdapter = new ListDropDownAdapter(this, Arrays.asList(ages));
        ageView.setAdapter(ageAdapter);

        //init sex menu
        final ListView sexView = new ListView(this);
        sexView.setDividerHeight(0);
        sexAdapter = new ListDropDownAdapter(this, Arrays.asList(sexs));
        sexView.setAdapter(sexAdapter);

        //init constellation
        final View constellationView = getLayoutInflater().inflate(R.layout.custom_layout, null);
        GridView constellation = ButterKnife.findById(constellationView, R.id.constellation);
        constellationAdapter = new ConstellationAdapter(this, Arrays.asList(constellations));
        constellation.setAdapter(constellationAdapter);
        TextView ok = ButterKnife.findById(constellationView, R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDropDownMenu.setTabText(constellationPosition == 0 ? headers[3] : constellations[constellationPosition]);
                mDropDownMenu.closeMenu();
            }
        });

        //init popupViews
        popupViews.add(cityView);
        popupViews.add(ageView);
        popupViews.add(sexView);
        popupViews.add(constellationView);

        //add item click event
        cityView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cityAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[0] : citys[position]);
                mDropDownMenu.closeMenu();
            }
        });

        ageView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ageAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[1] : ages[position]);
                mDropDownMenu.closeMenu();
            }
        });

        sexView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sexAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[2] : sexs[position]);
                mDropDownMenu.closeMenu();
            }
        });

        constellation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                constellationAdapter.setCheckItem(position);
                constellationPosition = position;
            }
        });

        //init context view
        TextView contentView = new TextView(this);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contentView.setGravity(Gravity.CENTER);
        contentView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

        //init dropdownview
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, contentView);
    }


    void bindListView() {


        mData.add("ffff");
        mData.add("ddd");
        mData.add("ffff");
        mData.add("ddd");
        mData.add("ffff");
        mData.add("ddd");
        mData.add("ffff");
        mData.add("ddd");


        goodListView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        goodListView.setAdapter(new CommonAdapter<String>(this, R.layout.ui_good_goodlist_item, mData) {


            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setOnClickListener(R.id.goodInfoItme, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(GoodList.this, GoodInfo.class));
                    }
                });
            }
        });


    }

    @Override
    public void onBackPressed() {
        //退出activity前关闭菜单
        if (mDropDownMenu.isShowing()) {
            mDropDownMenu.closeMenu();
        } else {
            super.onBackPressed();
        }
    }
}
