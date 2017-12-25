package com.android.teacher.newwork;


import android.util.Log;

import com.android.teacher.BuildConfig;
import com.android.teacher.entity.SystemRegister;

import org.json.JSONArray;
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


    public String actual_getList(int classid, String day) {

        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        addData(jsonObj, "classid", classid);
        addData(jsonObj, "studyday", day);
        addCmd(jsonObjArr, "actual.getList", jsonObj);

        return jsonObjArr.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());

    }


    /**
     * 心跳包
     *
     * @return
     */
    public String heartbeat() {
        jsonObj = new JSONObject();
        addData(jsonObj, "cmd", "system.heartbeat");
        addData(jsonObj, "data", "");
        return jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());

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
     * 放学按钮
     *
     * @return
     */

    public String sendNotify() {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        addCmd(jsonObjArr, "class.leave", jsonObj);
        return jsonObjArr.toString();
    }


    /**
     * 获取学校
     *
     * @return
     */
    public String School_getList() {
        jsonObj = new JSONObject();
        addData(jsonObj, "cmd", "school.getList");
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
     * 验证码
     *
     * @return
     */
    public String message_addinfo(String worktitle, String workcontent, int workType) {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分
        addData(jsonObj, "worktitle", worktitle);
        addData(jsonObj, "workcontent", workcontent);
        addData(jsonObj, "noticetype", workType); //1是作业，2是通知

        //外测json
        addCmd(jsonObjArr, "message.addinfo", jsonObj);
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
        addData(jsonObj, "usertype", "T");//P 是家长，T 是老师
        //外测json
        addCmd(jsonObjArr, "system.login", jsonObj);

        return jsonObjArr.toString();
    }

    /**
     * 登录有密码
     *
     * @param mobile
     * @param code
     * @param userType
     * @return
     */
    public String login(String mobile, String password, String code, String finger, String userType) {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分
        addData(jsonObj, "mobile", mobile);
        addData(jsonObj, "code", code);
        addData(jsonObj, "finger", finger);
        addData(jsonObj, "password", password);
        addData(jsonObj, "version", BuildConfig.VERSION_NAME);
        addData(jsonObj, "source", "Android");
        addData(jsonObj, "usertype", "T");//P 是家长，T 是老师
        //外测json
        addCmd(jsonObjArr, "system.login", jsonObj);
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
     * 个人信息
     *
     * @return
     */
    public String getMyInfo() {
        jsonObj = new JSONObject();
        addData(jsonObj, "cmd", "getMyInfo");
        return jsonObj.toString();

    }


    // FIXME: 17/11/1  学生接口

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
     * 获取学生列表
     *
     * @return
     */
    public String getsutdentlist() {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分

        //外测json
        addCmd(jsonObjArr, "class.getstudentlist", jsonObj);
        return jsonObjArr.toString();
    }

    /**
     * 获取学生列表
     *
     * @return
     */
    public String student_getlist(int classid) {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分
        addData(jsonObj, "classid", classid);
        //外测json
        addCmd(jsonObjArr, "student.getList", jsonObj);
        return jsonObjArr.toString();
    }

    /**
     * 获取学生详情
     *
     * @return
     */

    public String student_getinfo(int studentid) {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分
        addData(jsonObj, "studentid", studentid);
        //外测json
        addCmd(jsonObjArr, "student.getinfo", jsonObj);
        return jsonObjArr.toString();
    }


    /**
     * 获取学生信息
     *
     * @return
     */
    public String student_getparentinfo(int studentid) {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分
        addData(jsonObj, "studentid", studentid);
        //外测json
        addCmd(jsonObjArr, "student.getParentList", jsonObj);
        return jsonObjArr.toString();
    }


    /**
     * 获取学生出勤信息
     *
     * @return
     */
    public String student_getCheckInfo() {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分
        //外测json
        addCmd(jsonObjArr, "student.getCheckInfo", jsonObj);
        return jsonObjArr.toString();
    }
    // FIXME: 17/11/1  老师接口

    /**
     * 获取班级列表
     *
     * @return
     */
    public String teacher_GetClassList() {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分
        //外测json
        addCmd(jsonObjArr, "teacher.getClassList", jsonObj);
        return jsonObjArr.toString();
    }

    /**
     * 获取老师列表
     *
     * @return
     */
    public String class_getteacherlist() {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分
        //外测json
        addCmd(jsonObjArr, "class.getteacherlist", jsonObj);
        return jsonObjArr.toString();
    }

    /**
     * 选择班级
     *
     * @return
     */
    public String teacher_SelectClass(int classid) {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分
        addData(jsonObj, "classid", classid);
        //外测json
        addCmd(jsonObjArr, "teacher.selectClass", jsonObj);
        return jsonObjArr.toString();
    }

    /**
     * 新增班级
     *
     * @return
     */
    public String class_addInfo(int schoolid, int grade, int teacherid, String classname) {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分
        addData(jsonObj, "schoolid", schoolid);
        addData(jsonObj, "classname", classname);
        addData(jsonObj, "grade", grade);
        addData(jsonObj, "teacherid", teacherid);

        //外测json
        addCmd(jsonObjArr, "class.addInfo", jsonObj);
        return jsonObjArr.toString();
    }

    /**
     * 新增班级（绑定机器）
     *
     * @return
     */
    public String school_addinfo(String schoolname) {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分

        addData(jsonObj, "schoolname", schoolname);

        //外测json
        addCmd(jsonObjArr, "school.addinfo", jsonObj);
        return jsonObjArr.toString();
    }

    /**
     * 延遲放學
     *
     * @return
     */
    public String delay(String time, String reason) {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分
        addData(jsonObj, "time", time);
        addData(jsonObj, "reason", reason);
        //外测json
        addCmd(jsonObjArr, "class.delay", jsonObj);
        return jsonObjArr.toString();
    }

    // FIXME: 17/11/1  信息接口

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
    public String getlist_HomeWork() {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分
        //外测json
        addCmd(jsonObjArr, "message.gethomeworklist", jsonObj);
        return jsonObjArr.toString();
    }


    /**
     * 获取通知列表
     *
     * @return
     */
    public String getmessagelist_HomeWork() {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分
        //外测json
        addCmd(jsonObjArr, "message.getnotifylist", jsonObj);
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
     * 删除
     *
     * @return
     */
    public String message_deleteitem(int id) {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分
        addData(jsonObj, "id", id);
        //外测json
        addCmd(jsonObjArr, "message.deleteinfo", jsonObj);
        return jsonObjArr.toString();
    }

    /**
     * 系统注册
     *
     * @return
     */
    public String System_regist(SystemRegister systemRegister) {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分
        addData(jsonObj, "mobile", systemRegister.getMobile());
        addData(jsonObj, "teachername", systemRegister.getTeachername());
        addData(jsonObj, "code", systemRegister.getCode());
        addData(jsonObj, "usertype", "T");
        addData(jsonObj, "finger", systemRegister.getFinger());
        //外测json
        addCmd(jsonObjArr, "system.regist", jsonObj);
        return jsonObjArr.toString();
    }

    /**
     * 新增家长
     *
     * @return
     */
    public String parent_addInfo(String mobile, int studentid, String parentname, String relationship) {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分
        addData(jsonObj, "mobile", mobile);
        addData(jsonObj, "studentid", studentid);
        addData(jsonObj, "parentname", parentname);
        addData(jsonObj, "relationship", relationship);
        //外测json
        addCmd(jsonObjArr, "parent.addInfo", jsonObj);
        return jsonObjArr.toString();
    }

    /**
     * 删除家长
     *
     * @return
     */
    public String parent_deleteInfo(int studentid, int parentid) {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分
        addData(jsonObj, "studentid", studentid);
        addData(jsonObj, "parentid", parentid);
        //外测json
        addCmd(jsonObjArr, "parent.deleteInfo", jsonObj);
        return jsonObjArr.toString();
    }

    /**
     * 删除学生
     *
     * @return
     */
    public String student_deleteInfo(int studentid) {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分
        addData(jsonObj, "studentid", studentid);

        //外测json
        addCmd(jsonObjArr, "student.deleteInfo", jsonObj);
        return jsonObjArr.toString();
    }


    /**
     * 新增学生
     *
     * @return
     */
    public String student_addInfo(String studentno, String studentname) {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分

        addData(jsonObj, "studentno", studentno);
        addData(jsonObj, "studentname", studentname);
        //外测json
        addCmd(jsonObjArr, "student.addInfo", jsonObj);
        return jsonObjArr.toString();
    }


    /**
     * 更新学生
     *
     * @return
     */
    public String student_updateInfo(int studentid, String studentno, String studentname) {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分

        addData(jsonObj, "studentno", studentno);
        addData(jsonObj, "studentid", studentid);
        addData(jsonObj, "studentname", studentname);
        //外测json
        addCmd(jsonObjArr, "student.updateInfo", jsonObj);
        return jsonObjArr.toString();
    }

    /**
     * 新增老师
     *
     * @return
     */
    public String teacher_addInfo(String classid, String teachername, String mobile, String lesson, String qq) {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分
        addData(jsonObj, "classid", classid);
        addData(jsonObj, "teachername", teachername);
        addData(jsonObj, "lesson", lesson);
        addData(jsonObj, "mobile", mobile);
        addData(jsonObj, "qq", qq);
        //外测json
        addCmd(jsonObjArr, "teacher.addInfo", jsonObj);
        return jsonObjArr.toString();
    }

    /**
     * 删除老师
     *
     * @return
     */
    public String teacher_deleteInfo(int teacherid) {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分
        addData(jsonObj, "teacherid", teacherid);

        //外测json
        addCmd(jsonObjArr, "teacher.deleteInfo", jsonObj);
        return jsonObjArr.toString();
    }

    /**
     * 删除课程表
     *
     * @return
     */
    public String schedule_deleteInfo(int id) {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分
        addData(jsonObj, "id", id);

        //外测json
        addCmd(jsonObjArr, "schedule.deleteInfo", jsonObj);
        return jsonObjArr.toString();
    }

    /**
     * 获取课程信息
     *
     * @return
     */
    public String schedule_getInfo(int id) {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分
        addData(jsonObj, "id", id);
        //外测json
        addCmd(jsonObjArr, "schedule.getinfo", jsonObj);
        return jsonObjArr.toString();
    }


    /**
     * 注销班级
     *
     * @return
     */
    public String class_deleteInfo(int classid) {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分
        addData(jsonObj, "classid", classid);
        //外测json
        addCmd(jsonObjArr, "class.deleteInfo", jsonObj);
        return jsonObjArr.toString();
    }

    /**
     * 更新老师信息
     *
     * @return
     */
    public String teacher_updateInfo(int teacherid, String teachername, String mobile, String lesson, String qq) {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分
        addData(jsonObj, "teacherid", teacherid);
        addData(jsonObj, "teachername", teachername);
        addData(jsonObj, "lesson", lesson);
        addData(jsonObj, "mobile", mobile);
        addData(jsonObj, "qq", qq);

        //外测json
        addCmd(jsonObjArr, "teacher.updateInfo", jsonObj);
        return jsonObjArr.toString();
    }

    /**
     * 更新老师信息
     *
     * @return
     */
    public String system_updateInfo(String teachername, String mobile, String lesson) {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分
        addData(jsonObj, "teachername", teachername);
        addData(jsonObj, "lesson", lesson);
        addData(jsonObj, "mobile", mobile);
        //外测json
        addCmd(jsonObjArr, "system.updateInfo", jsonObj);
        return jsonObjArr.toString();
    }

    /**
     * 获取老师详情信息
     *
     * @return
     */
    public String teacher_getinfo(int teacherid) {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分
        addData(jsonObj, "teacherid", teacherid);
        //外测json
        addCmd(jsonObjArr, "teacher.getinfo", jsonObj);
        return jsonObjArr.toString();
    }

    /**
     * 获取课程表
     *
     * @return
     */
    public String class_getSchduleList(String dayname) {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分
        addData(jsonObj, "dayname", dayname);
        //外测json
        addCmd(jsonObjArr, "class.getSchedulelist", jsonObj);
        return jsonObjArr.toString();
    }

    /**
     * 更新家长信息
     *
     * @return
     */
    public String parent_updateInfo(int parentid, int studentid, String mobile, String parentname, String relationship) {
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
     * 更新家长信息
     *
     * @return
     */
    public String parent_getInfo(int parentid, int studentid) {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分
        addData(jsonObj, "studentid", studentid);
        addData(jsonObj, "parentid", parentid);


        //外测json
        addCmd(jsonObjArr, "parent.getinfo", jsonObj);
        return jsonObjArr.toString();
    }

    /**
     * 添加课程表
     *
     * @return
     */
    public String schedule_addInfo(int teacherid, String dayname, String coursename, String fromtime, String totime, String sectionname) {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分
        addData(jsonObj, "teacherid", teacherid);
        addData(jsonObj, "dayname", dayname);
        addData(jsonObj, "coursename", coursename);
        addData(jsonObj, "fromtime", fromtime);
        addData(jsonObj, "totime", totime);
        addData(jsonObj, "sectionname", sectionname);


        //外测json
        addCmd(jsonObjArr, "schedule.addInfo", jsonObj);
        return jsonObjArr.toString();
    }

    /**
     * 更新课程表
     *
     * @return
     */
    public String schedule_updateInfo(int id, int teacherid, String dayname, String coursename, String fromtime, String totime, String sectionname) {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分
        addData(jsonObj, "teacherid", teacherid);
        addData(jsonObj, "id", id);
        addData(jsonObj, "dayname", dayname);
        addData(jsonObj, "coursename", coursename);
        addData(jsonObj, "fromtime", fromtime);
        addData(jsonObj, "totime", totime);
        addData(jsonObj, "sectionname", sectionname);

        //外测json
        addCmd(jsonObjArr, "schedule.updateInfo", jsonObj);
        return jsonObjArr.toString();
    }

    /**
     * 取已经维护好的课程表班级
     *
     * @return
     */
    public String schedule_getClassList() {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分


        //外测json
        addCmd(jsonObjArr, "schedule.getClassList", jsonObj);
        return jsonObjArr.toString();
    }


    /**
     * copy课程表
     *
     * @return
     */
    public String schedule_copyfromclass(int fromclassid) {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分

        addData(jsonObj, "fromclassid", fromclassid);
        //外测json
        addCmd(jsonObjArr, "schedule.copyfromclass", jsonObj);
        return jsonObjArr.toString();
    }


    /**
     * copy课程表from 周
     *
     * @return
     */
    public String schedule_copyfromday(int fromday, int today) {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分
        addData(jsonObj, "fromday", fromday);
        addData(jsonObj, "today", today);
        //外测json
        addCmd(jsonObjArr, "schedule.copyfromday", jsonObj);
        return jsonObjArr.toString();
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
        addData(jsonObj, "pictype", "3");
        addData(jsonObj, "userid", picid);
        //外测json
        addCmd(jsonObjArr, "system.token", jsonObj);
        return jsonObjArr.toString();
    }

    /**
     * 修改班级
     *
     * @return
     */
    public String class_updateInfo(String classname, String grade, String gradename, String sintimefrom, String sintimeto, String leavestart, String leaveend, String endstart, String endend) {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分
        addData(jsonObj, "classname", classname);
        addData(jsonObj, "gradename", gradename);
        addData(jsonObj, "grade", grade);
        addData(jsonObj, "sintimefrom", sintimefrom);
        addData(jsonObj, "sintimeto", sintimeto);
        addData(jsonObj, "souttimefrom", leavestart);
        addData(jsonObj, "souttimeto", leaveend);
        addData(jsonObj, "touttimefrom", endstart);
        addData(jsonObj, "touttimeto", endend);
        //外测json
        addCmd(jsonObjArr, "class.updateInfo", jsonObj);
        return jsonObjArr.toString();
    }

    /**
     * 获取班级详情
     *
     * @return
     */
    public String class_getclassInfo(String classid) {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分


        //外测json
        addCmd(jsonObjArr, "class.getinfo", jsonObj);
        return jsonObjArr.toString();
    }


    /**
     * 获取代码字典内容
     *
     * @return
     */
    public String code_getcodelist(String codevalue) {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分
        addData(jsonObj, "codevalue", codevalue);

        //外测json
        addCmd(jsonObjArr, "code.getcodelist", jsonObj);
        return jsonObjArr.toString();
    }

    /**
     * 阅读人员列表
     *
     * @return
     */
    public String message_readinfo(String id) {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分
        addData(jsonObj, "id", id);

        //外测json
        addCmd(jsonObjArr, "message.readinfo", jsonObj);
        return jsonObjArr.toString();
    }

    /**
     * 信息推送
     *
     * @return
     */
    public String message_pushnotice(JSONArray parentid, int id) {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分

        addData(jsonObj, "parentid", parentid);
        addData(jsonObj, "id", id);


        //外测json
        addCmd(jsonObjArr, "message.pushnotice", jsonObj);
        return jsonObjArr.toString();
    }

    /**
     * 修改密码
     *
     * @return
     */
    public String system_modifypassword(String password, String code) {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分

        addData(jsonObj, "password", password);
        addData(jsonObj, "code", code);
        //外测json
        addCmd(jsonObjArr, "system.modifypassword", jsonObj);
        return jsonObjArr.toString();
    }

    /**
     * 评论
     *
     * @return
     */
    public String message_addComment(int messageid, String commentContent) {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分

        addData(jsonObj, "messageid", messageid);
        addData(jsonObj, "commentContent", commentContent);
        //外测json
        addCmd(jsonObjArr, "message.addComment", jsonObj);
        return jsonObjArr.toString();
    }


    /**
     * 写评论
     *
     * @return
     */
    public String message_getCommentList(int messageid) {
        jsonObj = new JSONObject();
        jsonObjArr = new JSONObject();
        //data部分

        addData(jsonObj, "messageid", messageid);

        //外测json
        addCmd(jsonObjArr, "message.getCommentList", jsonObj);
        return jsonObjArr.toString();
    }


}
