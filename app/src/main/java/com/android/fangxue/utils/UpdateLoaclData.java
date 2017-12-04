package com.android.fangxue.utils;

import android.content.Context;

/**
 * Created by softsea on 17/10/26.
 */

public class UpdateLoaclData {

    public static void setParentInfoForName(Context context, String parentName) {
        if (!parentName.isEmpty())
            SharedPrefsUtil.putValue(context, "userXML", "parentname", parentName); //家长姓名

    }

    public static void setParentInfoForMoble(Context context, String mobile) {

        if (!mobile.isEmpty())
            SharedPrefsUtil.putValue(context, "userXML", "mobile", mobile); //家长电话

    }

    public static void setParentInfoForRelationship(Context context, String relationship) {

        if (!relationship.isEmpty())
            SharedPrefsUtil.putValue(context, "userXML", "relationship", relationship); //家长关系

    }

    public static void setParentInfo(Context context, String relationship, String mobile, String parentName) {

        UpdateLoaclData.setParentInfoForRelationship(context, relationship);
        UpdateLoaclData.setParentInfoForMoble(context, mobile);
        UpdateLoaclData.setParentInfoForName(context, parentName);

    }

    public static void setChildInfoForName(Context context, String ChildName) {
        if (!ChildName.isEmpty())
            SharedPrefsUtil.putValue(context, "userXML", "studentName", ChildName); //家长姓名

    }

    public static void setChildInfoForSchool(Context context, String School) {

        if (!School.isEmpty())
            SharedPrefsUtil.putValue(context, "userXML", "studentSchoolnm", School); //家长电话

    }

    public static void setChildInfoForClass(Context context, String className) {

        if (!className.isEmpty())
            SharedPrefsUtil.putValue(context, "userXML", "studentClassname", className); //家长关系

    }

    public static void setChildInfo(Context context, String relationship, String mobile, String parentName) {

        UpdateLoaclData.setParentInfoForRelationship(context, relationship);
        UpdateLoaclData.setParentInfoForMoble(context, mobile);
        UpdateLoaclData.setParentInfoForName(context, parentName);

    }


}
