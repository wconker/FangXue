package com.android.fangxue.adapter.Holder;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.fangxue.R;
import com.android.fangxue.base.BaseHolder;
import com.android.fangxue.entity.TeacherBean;
import com.bumptech.glide.Glide;

/**
 * Created by softsea on 17/11/1.
 */

public class TeacherHolder extends BaseHolder<TeacherBean.DataBean> {


    private Context mcontext;
    private View item;
    private TextView t;
    int Count =0;

    public TeacherHolder(View itemView, Context context) {
        super(itemView);
        item = itemView;
        mcontext = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void getData(final TeacherBean.DataBean d) {

        Log.e("Conker", d.getTeachername() + "==" + d.getHeaderimg());
//
        t = item.findViewById(R.id.teacher_category);
//        ImageView phonecall = item.findViewById(R.id.call);
//        ImageView tx = item.findViewById(R.id.tx);
////        if (d.getHeaderimg() != null) {
////            Glide.with(mcontext).load(d.getHeaderimg()).into(tx);
////        } else {
////            tx.setImageDrawable(mcontext.getDrawable(R.drawable.username));
////        }
//        phonecall.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //用intent启动拨打电话
//                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + d.getMobile()));
//                if (ActivityCompat.checkSelfPermission(mcontext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                    // TODO: Consider calling
//                    //    ActivityCompat#requestPermissions
//                    // here to request the missing permissions, and then overriding
//                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                    //                                          int[] grantResults)
//                    // to handle the case where the user grants the permission. See the documentation
//                    // for ActivityCompat#requestPermissions for more details.
//                    return;
//                }
//                mcontext.startActivity(intent);
//            }
//        });

        if(d.getTeachername().equals("吴康辉"))
         t.setText(d.getTeachername());

    }
}
