package com.android.fangxue.adapter.MyAdapter;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.android.fangxue.base.BaseHolder;
import com.android.fangxue.callback.mClickInterface;
import com.android.fangxue.R;
import com.android.fangxue.entity.ProjectBean;

/**
 * Created by fangxue on 17/9/5.
 */

public class projectHolder extends BaseHolder<ProjectBean.DataBean> implements View.OnClickListener {

    Button vText;
    private int mpos=0;
    private mClickInterface mclick;
    LinearLayout textParent;
    View mview;

    public projectHolder(View itemView) {
        super(itemView);
        mview=itemView;
        vText = itemView.findViewById(R.id.proName);
        textParent = itemView.findViewById(R.id.proNameParent);
        vText.setOnClickListener(this);
    }
    @Override
    public void getData(ProjectBean.DataBean d) {
        vText.setText(d.getName());
        StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) textParent.getLayoutParams();
        lp.height = 100 + 7 * d.getName().length();
        textParent.setLayoutParams(lp);
    }

    public void getPost(int pos) {
        this.mpos=pos;
    }
    public void setOnclick(mClickInterface CClickInterface) {
        mclick = CClickInterface;
    }

    @Override
    public void onClick(View view) {
        mclick.doClick(this.mpos,vText);
    }
}
