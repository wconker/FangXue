package com.android.teacher.adapter.Holder;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.teacher.R;
import com.android.teacher.base.BaseHolder;
import com.android.teacher.callback.mClickInterface;
import com.android.teacher.entity.StudentInfo;
import com.android.teacher.ui.Info.AddParent;
import com.android.teacher.ui.Info.AddTeacher;
import com.android.teacher.utils.Dialog;
import com.android.teacher.utils.JSONUtils;
import com.android.teacher.utils.SharedPrefsUtil;
import com.android.teacher.utils.Toast;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

/**
 * Created by softsea on 17/11/14.
 */

public class StudentInfoHolder extends BaseHolder<StudentInfo.DataBean> {

    private View v;
    private Context mContext;
    private mClickInterface cClick;
    private int pos;
    private int studentid;

    public StudentInfoHolder(View itemView, int Studentid) {
        super(itemView);
        this.v = itemView;
        this.studentid = Studentid;
    }

    public void setInterFace(mClickInterface interFace) {
        cClick = interFace;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    @Override
    public int getPost(int Pos) {
        this.pos = Pos;
        return super.getPost(Pos);
    }

    public int getMyPos() {

        if (this.pos > 0)

            return pos;
        else
            return 0;
    }

    @Override
    public void getData(final StudentInfo.DataBean d) {

        TextView name = v.findViewById(R.id.info_name);
        TextView phone = v.findViewById(R.id.info_phone);
        TextView relationship = v.findViewById(R.id.info_relationship);
        Button btnEdit = v.findViewById(R.id.btnEdit);
        Button btnDelete = v.findViewById(R.id.btnDelete);
        SwipeMenuLayout SwipeMenuLayout = v.findViewById(R.id.SwipeMenuLayout);
        if (SharedPrefsUtil.getValue(mContext, "teacherXML", "roly", "").equals("1")) {
            SwipeMenuLayout.setSwipeEnable(true);
        } else {
            SwipeMenuLayout.setSwipeEnable(false);
        }
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cClick != null)
                    Dialog.showDialog(mContext, "提示", "是否确认删除？", cClick);
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (studentid > 0) {
                    Intent i = new Intent(mContext, AddParent.class);
                    i.putExtra("parentid", d.getParentid());
//                    i.putExtra("parentname", d.getParentname());
//                    i.putExtra("relationship", d.getRelationship());
//                    i.putExtra("parentmobile", d.getMobile());
                    i.putExtra("studentid", studentid);


                    mContext.startActivity(i);
                } else {
                    Toast.FangXueToast(mContext, "未知错误，请重试！");
                }
            }
        });

        name.setText(d.getParentname());

        String s = "拨打电话：" + d.getMobile();
        phone.setText(s);
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + d.getMobile()));
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mContext.startActivity(intent);
            }
        });
        relationship.setText(d.getRelationship());
    }
}
