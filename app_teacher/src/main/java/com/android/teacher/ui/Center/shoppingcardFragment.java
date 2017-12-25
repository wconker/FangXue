package com.android.teacher.ui.Center;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.teacher.R;
import com.android.teacher.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class shoppingcardFragment extends BaseFragment {


    @Bind(R.id.back_btn)
    ImageView backBtn;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;

    public static shoppingcardFragment newInstance() {

        return new shoppingcardFragment();
    }


    @Override
    protected void initView(View view) {


    }

    @Override
    protected int getLayout() {
        return R.layout.ui_center_shoppingcardfragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        ButterKnife.bind(this, rootView);
        backBtn.setVisibility(View.GONE);
        toolbarTitle.setText("购物车");
        return rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
