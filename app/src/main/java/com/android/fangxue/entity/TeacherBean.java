package com.android.fangxue.entity;

import java.util.List;

/**
 * Created by softsea on 17/11/1.
 */

public class TeacherBean {


    /**
     * code : 1
     * message : 取老师信息列表成功
     * data : [{"teachername":"吕乔","teacherid":1,"mobile":"17766889999"},{"teachername":"韩先武","teacherid":2,"mobile":"18258416062"},{"teachername":"白耀强","teacherid":3,"mobile":"18258416061"},{"teachername":"吴康辉","teacherid":4,"mobile":"15658690695"},{"teachername":"韩先武","teacherid":5,"mobile":"13396551997"}]
     * cmd : teacher.getList
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
         * teachername : 吕乔
         * teacherid : 1
         * mobile : 17766889999
         */

        private String teachername;
        private int teacherid;



        private String lesson;
        private String mobile;


        private String headerimg;


        public String getLesson() {
            return lesson;
        }

        public void setLesson(String lesson) {
            this.lesson = lesson;
        }

        public String getHeaderimg() {
            return headerimg;
        }

        public void setHeaderimg(String headerimg) {
            this.headerimg = headerimg;
        }

        public String getTeachername() {
            return teachername;
        }

        public void setTeachername(String teachername) {
            this.teachername = teachername;
        }

        public int getTeacherid() {
            return teacherid;
        }

        public void setTeacherid(int teacherid) {
            this.teacherid = teacherid;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }
}
