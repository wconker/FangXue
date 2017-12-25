package com.android.teacher.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.compat.BuildConfig;

/**
 * Created by softsea on 17/10/27.
 */

public class PowerUtil {


    public void gotoMiuiPermission(Context context) {
        Intent i = new Intent("miui.intent.action.APP_PERM_EDITOR");
        ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
        i.setComponent(componentName);
        i.putExtra("extra_pkgname", "com.android.teacher");
        try {
            context.startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }


    /**
     * 跳转到魅族的权限管理系统
     */
    public void gotoMeizuPermission(Context context) {
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("com.android.teacher", BuildConfig.APPLICATION_ID);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    /**
     * 华为的权限管理页面
     */
    public void gotoHuaweiPermission(Context context) {
        try {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");//华为权限管理
            intent.setComponent(comp);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    /**
     * 跳转到权限设置界面
     */
    public void getAppDetailSettingIntent(Context context) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", "com.android.teacher", null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            intent.setAction(Intent.ACTION_VIEW);
            intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            intent.putExtra("com.android.settings.ApplicationPkgName", "com.android.teacher");
        }
        context.startActivity(intent);
    }

}
