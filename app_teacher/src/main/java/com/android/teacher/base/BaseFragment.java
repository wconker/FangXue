package com.android.teacher.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by fangxue on 17/8/9.
 */

public abstract class BaseFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(getLayout(), container, false);
        initView(view);

        return view;
    }

    protected abstract void initView(View view);

    protected abstract int getLayout();


}
