package com.android.fangxue.ui.Auth.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.fangxue.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class res_createChild extends Fragment {


    @Bind(R.id.studentName_et)
    public EditText studentNameEt;
    @Bind(R.id.person_companyLayout)
    LinearLayout personCompanyLayout;
    @Bind(R.id.studentno_et)
    public EditText studentnoEt;

    public res_createChild() {
        // Required empty public constructor
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            System.out.println("res_createChild不可见");
        } else {
            System.out.println("res_createChild当前可见");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_res_create_child, container, false);
        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
