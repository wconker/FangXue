package com.android.teacher.utils;

import android.content.Context;
import android.content.DialogInterface;

import com.android.teacher.callback.mClickInterface;

/**
 * Created by softsea on 17/10/31.
 */

public class Dialog {
    /**
     * 这是兼容的 AlertDialog
     */
    public static void showDialog(Context context, String title, String content, final mClickInterface clickListener) {
  /*
  这里使用了 android.support.v7.app.AlertDialog.Builder
  可以直接在头部写 import android.support.v7.app.AlertDialog
  那么下面就可以写成 AlertDialog.Builder
  */
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(content);
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                clickListener.doClick();
            }
        });
        builder.show();
    }
}
