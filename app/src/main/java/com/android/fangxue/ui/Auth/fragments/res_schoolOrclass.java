package com.android.fangxue.ui.Auth.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.android.fangxue.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class res_schoolOrclass extends Fragment {


    @Bind(R.id.schoolList)
    public Spinner schoolList;
    @Bind(R.id.schoolBox)
    LinearLayout schoolBox;
    @Bind(R.id.gradeSpinner)
    public Spinner gradeSpinner;
    @Bind(R.id.ClassName_et)
    public EditText ClassNameEt;
    @Bind(R.id.person_companyLayout)
    LinearLayout personCompanyLayout;

    public res_schoolOrclass() {
        // Required empty public constructor
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            System.out.println("res_schoolOrclass不可见");
        } else {
            System.out.println("res_schoolOrclass当前可见");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_res_school_orclass, container, false);
        ButterKnife.bind(this, view);
        return view;

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
