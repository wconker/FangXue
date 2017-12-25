package com.android.teacher.entity;

import java.util.List;

/**
 * Created by softsea on 17/11/1.
 */

public class StudentBean {


    /**
     * data : [{"studentno":1710010,"studentid":9,"headerimg":null,"leavetime":"00:00","arrivetime":"00:00","studentname":"曾贵芬"},{"studentno":119911,"studentid":11,"headerimg":null,"leavetime":"00:00","arrivetime":"00:00","studentname":"杨宗保"}]
     * message : 取学生列表成功
     * code : 1
     * cmd : class.getstudentlist
     */

    private String message;
    private int code;
    private String cmd;
    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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
         * studentno : 1710010
         * studentid : 9
         * headerimg : null
         * leavetime : 00:00
         * arrivetime : 00:00
         * studentname : 曾贵芬
         */

        private int studentno;
        private int studentid;
        private Object headerimg;
        private String leavetime;
        private String arrivetime;
        private String studentname;

        public int getStudentno() {
            return studentno;
        }

        public void setStudentno(int studentno) {
            this.studentno = studentno;
        }

        public int getStudentid() {
            return studentid;
        }

        public void setStudentid(int studentid) {
            this.studentid = studentid;
        }

        public Object getHeaderimg() {
            return headerimg;
        }

        public void setHeaderimg(Object headerimg) {
            this.headerimg = headerimg;
        }

        public String getLeavetime() {
            return leavetime;
        }

        public void setLeavetime(String leavetime) {
            this.leavetime = leavetime;
        }

        public String getArrivetime() {
            return arrivetime;
        }

        public void setArrivetime(String arrivetime) {
            this.arrivetime = arrivetime;
        }

        public String getStudentname() {
            return studentname;
        }

        public void setStudentname(String studentname) {
            this.studentname = studentname;
        }
    }
}
