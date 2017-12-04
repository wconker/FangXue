package com.android.fangxue.entity;

import java.util.List;

/**
 * Created by softsea on 17/10/24.
 */

public class Children {
    /**
     * code : 1
     * message : 选择孩子成功
     * data : [{"studentid":4,"classname":"702","studentname":"韩星星","schoolnm":"高新实验","schoolname":"浙江省杭州高新实验学校","parentname":"沈婷","relationship":"妈妈","mobile":"15658690695","arrivetime":"-","classtime":"-","leavetime":"-"}]
     * cmd : parent.selectstudent
     * today : 2017-10-24
     */

    private int code;
    private String message;
    private String cmd;
    private String today;
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

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * studentid : 4
         * classname : 702
         * studentname : 韩星星
         * schoolnm : 高新实验
         * schoolname : 浙江省杭州高新实验学校
         * parentname : 沈婷
         * relationship : 妈妈
         * mobile : 15658690695
         * arrivetime : -
         * classtime : -
         * leavetime : -
         */

        private int studentid;
        private String classname;
        private String studentname;
        private String schoolnm;
        private String schoolname;
        private String parentname;


        private String headerimg;
        private String relationship;
        private String mobile;
        private String arrivetime;
        private String classtime;
        private String leavetime;

        public String getHeaderimg() {
            return headerimg;
        }

        public void setHeaderimg(String headerimg) {
            this.headerimg = headerimg;
        }

        public int getStudentid() {
            return studentid;
        }

        public void setStudentid(int studentid) {
            this.studentid = studentid;
        }

        public String getClassname() {
            return classname;
        }

        public void setClassname(String classname) {
            this.classname = classname;
        }

        public String getStudentname() {
            return studentname;
        }

        public void setStudentname(String studentname) {
            this.studentname = studentname;
        }

        public String getSchoolnm() {
            return schoolnm;
        }

        public void setSchoolnm(String schoolnm) {
            this.schoolnm = schoolnm;
        }

        public String getSchoolname() {
            return schoolname;
        }

        public void setSchoolname(String schoolname) {
            this.schoolname = schoolname;
        }

        public String getParentname() {
            return parentname;
        }

        public void setParentname(String parentname) {
            this.parentname = parentname;
        }

        public String getRelationship() {
            return relationship;
        }

        public void setRelationship(String relationship) {
            this.relationship = relationship;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getArrivetime() {
            return arrivetime;
        }

        public void setArrivetime(String arrivetime) {
            this.arrivetime = arrivetime;
        }

        public String getClasstime() {
            return classtime;
        }

        public void setClasstime(String classtime) {
            this.classtime = classtime;
        }

        public String getLeavetime() {
            return leavetime;
        }

        public void setLeavetime(String leavetime) {
            this.leavetime = leavetime;
        }
    }
}
