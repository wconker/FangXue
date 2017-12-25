package com.android.teacher.ui.Center;


import android.support.v4.app.Fragment;
import android.view.View;

import com.android.teacher.R;
import com.android.teacher.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class rechargeFragment extends BaseFragment {


    public static rechargeFragment newInstance() {

        return new rechargeFragment();
    }




    @Override
    protected void initView(View view) {

    }

    @Override
    protected int getLayout() {
        return R.layout.ui_center_rechargefragment;
    }

}
