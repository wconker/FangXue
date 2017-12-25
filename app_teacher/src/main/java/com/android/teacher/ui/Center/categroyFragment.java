package com.android.teacher.ui.Center;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.teacher.R;
import com.android.teacher.adapter.listlinkadapter.LeftAdapter;
import com.android.teacher.adapter.listlinkadapter.RightAdapter;
import com.android.teacher.widget.HaveHeaderListView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class categroyFragment extends Fragment {

    public static categroyFragment newInstance() {

        return new categroyFragment();
    }

    //左边的ListView
    private ListView lv_left;
    //左边ListView的Adapter
    private LeftAdapter leftAdapter;
    //左边的数据存储
    private List<String> leftStr;
    //左边数据的标志
    private List<Boolean> flagArray;
    //右边的ListView
    private HaveHeaderListView lv_right;
    //右边的ListView的Adapter
    private RightAdapter rightAdapter;
    //右边的数据存储
    private List<List<String>> rightStr;
    //是否滑动标志位
    private Boolean isScroll = false;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.ui_center_categroyfragment, container, false);
        //初始化控件
        initView(view);
        //初始化数据
        initData();
        lv_left.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                isScroll = false;
                for (int i = 0; i < leftStr.size(); i++) {
                    if (i == position) {
                        flagArray.set(i, true);
                    } else {
                        flagArray.set(i, false);
                    }
                }
                //更新
                leftAdapter.notifyDataSetChanged();
                int rightSection = 0;
                for (int i = 0; i < position; i++) {
                    //查找
                    rightSection += rightAdapter.getCountForSection(i) + 1;
                }
                //显示到rightSection所代表的标题
                lv_right.setSelection(rightSection);
            }
        });
        lv_right.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    // 当不滚动时
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        // 判断滚动到底部
                        if (lv_right.getLastVisiblePosition() == (lv_right.getCount() - 1)) {
                            lv_left.setSelection(ListView.FOCUS_DOWN);
                        }
                        // 判断滚动到顶部
                        if (lv_right.getFirstVisiblePosition() == 0) {
                            lv_left.setSelection(0);
                        }
                        break;
                }

            }

            int y = 0;
            int x = 0;

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (isScroll) {
                    for (int i = 0; i < rightStr.size(); i++) {
                        if (i == rightAdapter.getSectionForPosition(lv_right.getFirstVisiblePosition())) {
                            flagArray.set(i, true);
                            //获取当前标题的标志位
                            x = i;
                        } else {
                            flagArray.set(i, false);
                        }
                    }
                    if (x != y) {
                        leftAdapter.notifyDataSetChanged();
                        //将之前的标志位赋值给y，下次判断
                        y = x;
                    }
                } else {
                    isScroll = true;
                }
            }
        });

        return view;
    }

    private void initData() {
        //左边相关数据
        leftStr.add("菜系专区");
        leftStr.add("品牌专区");
        leftStr.add("鸭鹅专区");
        leftStr.add("鸡肉专区");
        leftStr.add("猪肉专区");
        leftStr.add("牛肉专区");
        leftStr.add("羊兔专区");
        leftStr.add("水产专区");
        leftStr.add("火锅专区");
        leftStr.add("面包专区");
        leftStr.add("调理专区");
        leftStr.add("烟熏专区");
        flagArray.add(true);
        flagArray.add(false);
        flagArray.add(false);
        flagArray.add(false);
        flagArray.add(false);
        flagArray.add(false);
        flagArray.add(false);
        flagArray.add(false);
        flagArray.add(false);
        flagArray.add(false);
        flagArray.add(false);
        flagArray.add(false);
        leftAdapter.notifyDataSetChanged();
        //右边相关数据
        //面食类
        List<String> food1 = new ArrayList<>();
        food1.add("热干面");
        food1.add("臊子面");
        food1.add("烩面");
        //盖饭
        List<String> food2 = new ArrayList<>();
        food2.add("番茄鸡蛋");
        food2.add("红烧排骨");
        food2.add("农家小炒肉");
        //寿司
        List<String> food3 = new ArrayList<>();
        food3.add("芝士");
        food3.add("丑小丫");
        food3.add("金枪鱼");
        //烧烤
        List<String> food4 = new ArrayList<>();
        food4.add("羊肉串");
        food4.add("烤鸡翅");
        food4.add("烤羊排");
        //酒水
        List<String> food5 = new ArrayList<>();
        food5.add("长城干红");
        food5.add("燕京鲜啤");
        food5.add("青岛鲜啤");
        //凉菜
        List<String> food6 = new ArrayList<>();
        food6.add("拌粉丝");
        food6.add("大拌菜");
        food6.add("菠菜花生");
        //小吃
        List<String> food7 = new ArrayList<>();
        food7.add("小食组");
        food7.add("紫薯");
        //粥
        List<String> food8 = new ArrayList<>();
        food8.add("小米粥");
        food8.add("大米粥");
        food8.add("南瓜粥");
        food8.add("玉米粥");
        food8.add("紫米粥");
        rightStr.add(food1);
        rightStr.add(food2);
        rightStr.add(food3);
        rightStr.add(food4);
        rightStr.add(food5);
        rightStr.add(food6);
        rightStr.add(food7);
        rightStr.add(food8);
        rightStr.add(food5);
        rightStr.add(food6);
        rightStr.add(food7);
        rightStr.add(food1);
        rightAdapter.notifyDataSetChanged();
    }

    private void initView(View view) {
        lv_left = (ListView) view.findViewById(R.id.lv_left);
        leftStr = new ArrayList<>();
        flagArray = new ArrayList<>();
        leftAdapter = new LeftAdapter(getActivity(), leftStr, flagArray);
        lv_left.setAdapter(leftAdapter);
        lv_right = (HaveHeaderListView) view.findViewById(R.id.lv_right);
        rightStr = new ArrayList<List<String>>();
        rightAdapter = new RightAdapter(getActivity(), leftStr, rightStr);
        lv_right.setAdapter(rightAdapter);
    }
}


