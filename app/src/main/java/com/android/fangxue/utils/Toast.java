package com.android.fangxue.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by softsea on 17/9/30.
 */

public class Toast {

    public static void FangXueToast(Context context, String Content) {
        android.widget.Toast.makeText(context, Content, android.widget.Toast.LENGTH_LONG).show();
    }

    public static void FangXueSnabar(View view, String Content) {
        Snackbar.make(view, Content, Snackbar.LENGTH_LONG).show();
    }

}
