package com.android.teacher.entity;

import java.util.List;

/**
 * Created by softsea on 17/11/27.
 */

public class scheduleofclass {
    /**
     * code : 1
     * message : 取课程表班级列表成功
     * data : [{"classid":1,"classname":"702"},{"classid":8,"classname":"6"}]
     * cmd : schedule.getClassList
     */

    private int code;
    private String message;
    private String cmd;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * classid : 1
         * classname : 702
         */

        private int classid;
        private String classname;

        public int getClassid() {
            return classid;
        }

        public void setClassid(int classid) {
            this.classid = classid;
        }

        public String getClassname() {
            return classname;
        }

        public void setClassname(String classname) {
            this.classname = classname;
        }
    }
}
