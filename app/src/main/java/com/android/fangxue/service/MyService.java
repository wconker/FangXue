package com.android.fangxue.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.SystemClock;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.android.fangxue.R;
import com.android.fangxue.callback.MessageCallBack;
import com.android.fangxue.callback.ServiceMessage;
import com.android.fangxue.newwork.HttpCenter;
import com.android.fangxue.newwork.MessageCenter;
import com.android.fangxue.ui.Center.ActivityCenter;
import com.android.fangxue.ui.Contact.ContactList;
import com.android.fangxue.ui.MainActivity;
import com.android.fangxue.utils.BadgeUtil;
import com.android.fangxue.utils.JSONUtils;
import com.android.fangxue.utils.SharedPrefsUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.leolin.shortcutbadger.ShortcutBadger;
import okhttp3.RequestBody;
import rx.schedulers.NewThreadScheduler;

import static okhttp3.ws.WebSocket.TEXT;

public class MyService extends Service implements ServiceMessage {
    public MyService() {
    }

    private PowerManager.WakeLock wakeLock;
    private MessageCenter messageCenter;
    private Context context = this;


    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("SERVICE", "服务启动了");

        CountDownTimer countDownTimer = new CountDownTimer(6000, 1000) {
            @Override
            public void onTick(long l) {
                Log.i("SERVICE", "服务启动了");
            }

            @Override
            public void onFinish() {
                messageCenter = new MessageCenter();
                messageCenter.setServiceMessage(MyService.this);
                messageCenter.setLoginChannel();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            try {
                                Thread.sleep(10000);//休眠3秒
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Log.i(".", "......");

                        }
                    }
                }).start();

            }
        }.start();
        acquireWakeLock();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY_COMPATIBILITY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseWakeLock();

    }

    /**
     * 获取电源锁，保持该服务在屏幕熄灭时仍然获取CPU时，保持运行
     */
    private void acquireWakeLock() {
        if (null == wakeLock) {
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK
                    | PowerManager.ON_AFTER_RELEASE, getClass()
                    .getCanonicalName());
            if (null != wakeLock) {

                wakeLock.acquire();
            }
        }
    }

    // 释放设备电源锁
    private void releaseWakeLock() {
        if (null != wakeLock && wakeLock.isHeld()) {
            wakeLock.release();
            wakeLock = null;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onServiceMessage(String msg) {

        //自启动时开启服务
        if (msg.equals("1000")) {
            String loginCmd = messageCenter.ChooseCommand().login(SharedPrefsUtil.getValue(context, "loginXML", "UserName", ""),
                    "",
                    SharedPrefsUtil.getValue(context, "loginXML", "mathinecode", ""),
                    "P");
            Log.e("服务从新登录发送", "手机号码:" + SharedPrefsUtil.getValue(context, "loginXML", "UserName", "") + loginCmd);
            messageCenter.SendYouMessage(loginCmd);
        } else {
            JSONObject cmd = JSONUtils.StringToJSON(msg);
            //教室一键放学
            if (JSONUtils.getString(cmd, "cmd").equals("system.notify")) {
                Intent broad = new Intent();
                broad.setAction("com.fangxue.b");
                broad.putExtra("msg", JSONUtils.getString(cmd, "message"));
                broad.putExtra("type", 1);
                sendBroadcast(broad);

            }
            //学生离校
            if (JSONUtils.getString(cmd, "cmd").equals("class.notify")) {
                Intent broad = new Intent();
                broad.setAction("com.fangxue.b");
                broad.putExtra("msg", JSONUtils.getString(cmd, "message"));
                broad.putExtra("type", 2);
                sendBroadcast(broad);

            }
            //通知未读
            if (JSONUtils.getString(cmd, "cmd").equals("message.notice")) {
                JSONObject ob = JSONUtils.getSingleJSON(cmd, "data", 0);
                Intent broad = new Intent();
                broad.setAction("com.fangxue.b");
                broad.putExtra("msg", JSONUtils.getString(ob, "title"));
                broad.putExtra("type", 3);
                broad.putExtra("notify", JSONUtils.getString(ob, "id"));
                sendBroadcast(broad);
            }

            //离线通知
            if (JSONUtils.getString(cmd, "cmd").equals("system.offline")) {
                Intent broad = new Intent();
                broad.setAction("com.fangxue.b");
                broad.putExtra("type", 123);
                sendBroadcast(broad);
            }
            if (JSONUtils.getString(cmd, "cmd").equals("system.badge")) {
                JSONObject br = JSONUtils.getSingleJSON(cmd, "data", 0);
                Log.e("badge", "Server: "+JSONUtils.getInt(br, "badge", 0) );
                BadgeUtil.setBadgeCount(context, JSONUtils.getInt(br, "badge", 0), R.drawable.command_icon);
            }



        }


    }
}
