package com.android.fangxue.newwork;


import android.util.Log;

import com.android.fangxue.BuildConfig;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by fangxue on 17/9/1.
 * 主要封装请求命令，整合在一起方便调用
 */

public class CommandCenter {


    private JSONObject jsonObj;
    private JSONObject jsonObjArr;


    /////////////////////////////////////
    private JSONObject addData(JSONObject ob, String name, Object vlaue) {
        try {
            ob.put(name, vlaue);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ob;

    }

    private JSONObject addCmd(JSONObject cmd, String cmdName, JSONObject data) {

        try {
            cmd.put("cmd", cmdName);
            cmd.put("data", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return cmd;
    }


    public String actual_getList(String day) {

        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
//        addData(jsonObj, "classid", classid);
        addData(jsonObj, "studyday", day);
        addCmd(jsonObjArr, "actual.getList", jsonObj);
        Log.e("Conker", "-=-=-=-=" + jsonObjArr.toString());
        return jsonObjArr.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());

    }


    /**
     * 心跳包
     *
     * @return
     */
    public String heartbeat() {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        addData(jsonObj, "userid", "1");
        addCmd(jsonObjArr, "system.heartbeat", jsonObj);
        return jsonObjArr.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
    }

    /**
     * 机器码验证
     *
     * @return parentid : 1
     * parentname : 韩先武
     * mobile : 13396551997
     * weixinopenid : null
     * smsflag : 0
     * smsbalance : null
     * appflag : null
     * lasttime : null
     * finger : ffffffff-fc70-3e55-fd7d-d03526c0333e
     * student : null
     */
    public String machinecode(String mobile, String userType, String figner) {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        addData(jsonObj, "mobile", mobile);
        addData(jsonObj, "usertype", userType);// P 家长，T老师
        addData(jsonObj, "figner", figner);
        addCmd(jsonObjArr, "system.machinecode", jsonObj);
        return jsonObjArr.toString();
    }


    /**
     * 重新连接
     *
     * @param machineCode
     * @param userType
     * @param userid
     * @return
     */

    public String reconnect(String machineCode, String userType, String userid) {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        addData(jsonObj, "finger", machineCode);
        addData(jsonObj, "usertype", userType);// P 家长，T老师
        addData(jsonObj, "userid", userid);
        addCmd(jsonObjArr, "system.reconnect", jsonObj);
        return jsonObjArr.toString();
    }

    /**
     * 主动调起通知获取放学时间
     *
     * @return
     */

    public String getnotify() {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        addCmd(jsonObjArr, "system.getnotify", jsonObj);
        return jsonObjArr.toString();
    }


    /**
     * 获取项目
     *
     * @return
     */
    public String projectGetList() {
        jsonObj = new JSONObject();
        addData(jsonObj, "cmd", "projectGetList");
        return jsonObj.toString();

    }

    /**
     * 验证码
     *
     * @return
     */
    public String sendcode(String mobile, String userType) {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分
        addData(jsonObj, "mobile", mobile);
        addData(jsonObj, "usertype", userType);//P 是家长，T 是老师
        //外测json
        addCmd(jsonObjArr, "system.sendcode", jsonObj);
        return jsonObjArr.toString();
    }

    /**
     * 登录
     *
     * @param mobile
     * @param code
     * @param userType
     * @return
     */
    public String login(String mobile, String code, String finger, String userType) {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分
        addData(jsonObj, "mobile", mobile);
        addData(jsonObj, "code", code);
        addData(jsonObj, "finger", finger);
        addData(jsonObj, "version", BuildConfig.VERSION_NAME);
        addData(jsonObj, "source", "Android");
        addData(jsonObj, "usertype", userType);//P 是家长，T 是老师
        //外测json
        addCmd(jsonObjArr, "system.login", jsonObj);
        Log.e("登录", jsonObjArr.toString());
        return jsonObjArr.toString();
    }


    /**
     * 选择学生
     *
     * @return
     */
    public String selectStudent(int studentid) {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分
        addData(jsonObj, "studentid", studentid);
        //外测json
        addCmd(jsonObjArr, "parent.selectstudent", jsonObj);
        return jsonObjArr.toString();
    }

    /**
     * 修改家长信息
     *
     * @return
     */
    public String updateInfo(int studentid, int parentid, String parentname, String mobile, String relationship) {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分
        addData(jsonObj, "studentid", studentid);
        addData(jsonObj, "parentid", parentid);
        addData(jsonObj, "parentname", parentname);
        addData(jsonObj, "mobile", mobile);
        addData(jsonObj, "relationship", relationship);

        //外测json
        addCmd(jsonObjArr, "parent.updateInfo", jsonObj);
        return jsonObjArr.toString();
    }

    /**
     * 修改学生信息
     *
     * @return
     */
    public String updateChild(int studentid, int parentid, int studentno, String studentname, String finger) {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分
        addData(jsonObj, "studentid", studentid);
        addData(jsonObj, "studentno", studentno);
        addData(jsonObj, "studentname", studentname);
        addData(jsonObj, "finger", finger);


        //外测json
        addCmd(jsonObjArr, "student.updateInfo", jsonObj);
        return jsonObjArr.toString();
    }

    /**
     * 反馈意见
     *
     * @return
     */
    public String sendfeedback(String feedcontent) {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分
        addData(jsonObj, "feedcontent", feedcontent);


        //外测json
        addCmd(jsonObjArr, "system.sendfeedback", jsonObj);
        return jsonObjArr.toString();
    }

    /**
     * 获取学生列表
     *
     * @return
     */
    public String getstudentlist() {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分
        //外测json
        addCmd(jsonObjArr, "parent.getstudentlist", jsonObj);
        return jsonObjArr.toString();
    }


    /**
     * 获取首页新闻
     *
     * @return
     */
    public String getNews() {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分
        //外测json
        addCmd(jsonObjArr, "system.getnewslist", jsonObj);
        return jsonObjArr.toString();
    }

    /**
     * 获取作业详情
     *
     * @return
     */
    public String getinfo_Homework(int id) {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分
        addData(jsonObj, "id", id);
        //外测json
        addCmd(jsonObjArr, "message.gethomeworkinfo", jsonObj);
        return jsonObjArr.toString();
    }

    /**
     * 获取作业列表
     *
     * @return
     */
    public String getlist_message(int type, String keyword, String id, int Num) {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分
        addData(jsonObj, "keyword", keyword);
        addData(jsonObj, "id", id);
        addData(jsonObj, "noticetype", type);
        addData(jsonObj, "num", Num);
        //外测json
        addCmd(jsonObjArr, "message.getlist", jsonObj);
        return jsonObjArr.toString();
    }


    /**
     * 获取作业列表
     *
     * @return
     */
    public String getlist_HomeWork() {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分
        //外测json
        addCmd(jsonObjArr, "message.gethomeworklist", jsonObj);
        return jsonObjArr.toString();
    }

    /**
     * 获取老师列表
     *
     * @return
     */
    public String teacher_getList() {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分
        //外测json
        addCmd(jsonObjArr, "teacher.getList", jsonObj);
        return jsonObjArr.toString();
    }

    /**
     * 班级获取老师列表
     *
     * @return
     */
    public String getteacherlist() {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分
        //外测json
        addCmd(jsonObjArr, "class.getteacherlist", jsonObj);
        return jsonObjArr.toString();
    }

    /**
     * 获取通知列表
     *
     * @param keyword
     * @param id
     * @param Num
     * @return
     */
    public String getmessagelist_HomeWork(String keyword, String id, int Num) {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分
        addData(jsonObj, "keyword", keyword);
        addData(jsonObj, "id", id);
        addData(jsonObj, "num", Num);
        //外测json
        addCmd(jsonObjArr, "message.getnotifylist", jsonObj);
        return jsonObjArr.toString();
    }

    /**
     * 获取通知详情
     *
     * @return
     */
    public String getmessageinfo_HomeWork(int id) {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分
        addData(jsonObj, "id", id);
        //外测json
        addCmd(jsonObjArr, "message.getinfo", jsonObj);
        return jsonObjArr.toString();
    }


    /**
     * 个人信息
     *
     * @return
     */
    public String getMyInfo() {
        jsonObj = new JSONObject();
        addData(jsonObj, "cmd", "getMyInfo");
        return jsonObj.toString();

    }

    /**
     * 项目详情
     *
     * @return
     */
    public String project(String proId) {
        jsonObj = new JSONObject();
        addData(jsonObj, "cmd", "project");
        addData(jsonObj, "project_id", proId);
        return jsonObj.toString();
    }

    /**
     * 添加日志
     *
     * @param workContent
     * @param workdata
     * @param workLong
     * @param workPro
     * @return
     */
    public String addWorkingLog(String workContent, String workdata, String workLong, String workPro) {
        jsonObj = new JSONObject();
        addData(jsonObj, "cmd", "addWorkingLog");
        addData(jsonObj, "xmbh", workPro);
        addData(jsonObj, "khbh", "");
        addData(jsonObj, "gzrq", workdata);
        addData(jsonObj, "gznr", workContent);
        addData(jsonObj, "gs", workLong);
        addData(jsonObj, "cbfl", 0);
        return jsonObj.toString();
    }
    /**
     * 获取上传头像的teken
     *
     * @return
     */
    public String system_token(String picid) {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分
        addData(jsonObj, "pictype", "5");
        addData(jsonObj, "userid", picid);
        //外测json
        addCmd(jsonObjArr, "system.token", jsonObj);
        return jsonObjArr.toString();
    }

}
