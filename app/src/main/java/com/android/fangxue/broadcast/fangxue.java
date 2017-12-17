package com.android.fangxue.broadcast;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.WindowManager;
import android.widget.RemoteViews;

import com.android.fangxue.Default.FinalField;
import com.android.fangxue.R;
import com.android.fangxue.service.MyService;
import com.android.fangxue.ui.Auth.Login;
import com.android.fangxue.ui.Center.ActivityCenter;
import com.android.fangxue.ui.Detail.NotifyInfo;
import com.android.fangxue.utils.SharedPrefsUtil;
import com.android.fangxue.utils.Toast;

public class fangxue extends BroadcastReceiver {

    @Override
    public void onReceive( Context context, Intent intent) {



        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            Toast.FangXueToast(context, "广播服务开启了！！！");
            Intent i = new Intent(context, MyService.class);
            // i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startService(i);

        }
        String name = intent.getExtras().getString("msg");
        int type = intent.getExtras().getInt("type");

        int noId = 0;
        if (intent.getExtras().getString("notify") != null) {
            noId = Integer.valueOf(intent.getExtras().getString("notify"));
        }

        showNotifictionIcon(context, name, type, noId);
    }


    public void showNotifictionIcon(Context context, String msg, int type, int id) {

        if (type==123)
        {
            loginout(context,msg);
        }
        else {

            if (!SharedPrefsUtil.getValue(context, "systemXML", "notify", FinalField.POWER_OPEN)) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                builder.setAutoCancel(true);//点击后消失
                builder.setSmallIcon(R.mipmap.logo144);//设置通知栏消息标题的头像
                builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.logo144));
                builder.setTicker("放学神器");
                builder.setContentText(msg);//通知内容
                builder.setContentTitle("放学通知");
                Intent intent;
                builder.setAutoCancel(true);
                if (type == 1) {

                    intent = new Intent(context, ActivityCenter.class);
                    builder.setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.ding));
                } else if (type == 2) {
                    intent = new Intent(context, ActivityCenter.class);
                    builder.setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.leave));
                } else {
                    intent = new Intent(context, NotifyInfo.class);
                    intent.putExtra("notify", id);
                    builder.setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.leave));
                }

                //利用PendingIntent来包装我们的intent对象,使其延迟跳转
                PendingIntent intentPend = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                builder.setContentIntent(intentPend);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder.setFullScreenIntent(intentPend, true);
                    builder.setAutoCancel(true);
                }
                NotificationManager manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

                manager.notify(0, builder.build());

            }
        }
    }

    void loginout(final Context context, String mes) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context, R.style.MyAlertDialogStyle);
        dialogBuilder.setTitle("下线通知");
        dialogBuilder.setMessage("你的账户在其他地方登录 Over");

        dialogBuilder.setPositiveButton("重新登录",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent1 = new Intent(context, Login.class);
                        //在广播接受者中启动活动需要添加FLAG_ACTIVITY_NEW_TASK标志
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent1);
                    }
                });
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alertDialog.show();
    }

    void Pop(Context context, String mes) {

        Notification.Builder builder2 = new Notification.Builder(context);
        Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.jianshu.com/p/82e249713f1b"));
        PendingIntent pendingIntent2 = PendingIntent.getActivity(context, 0, intent2, 0);
        builder2.setContentIntent(pendingIntent2);
        builder2.setSmallIcon(R.mipmap.ic_launcher);
        builder2.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
        builder2.setAutoCancel(true);
        builder2.setContentTitle(mes);

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.utils_gridview_item);
        Notification notification = builder2.build();
        notification.bigContentView = remoteViews;
        NotificationManager manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        manager.notify(1, notification);
    }

}
