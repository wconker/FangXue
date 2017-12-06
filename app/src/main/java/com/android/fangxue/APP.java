package com.android.fangxue;

import android.app.Application;
import android.content.Context;
import android.os.PowerManager;
import android.os.StrictMode;
import android.util.Log;

import com.android.fangxue.newwork.HttpCenter;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by wukanghui on 2017/8/9.
 */

public class APP extends Application {
    private PowerManager.WakeLock wakeLock;

    @Override
    public void onCreate() {
        super.onCreate();

        CrashReport.initCrashReport(getApplicationContext(), "5082de40ae", false);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
        HttpCenter.InstancesOkhttp();
        HttpCenter.context = this;


    }

    /**
     * 获取电源锁，保持该服务在屏幕熄灭时仍然获取CPU时，保持运行
     */
    private void acquireWakeLock() {

        Log.i("Wake", " Wake  Wake");
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
            Log.i("WakeLock", "Wake启动了不锁屏");
            wakeLock.release();
            wakeLock = null;
        }
    }

}
