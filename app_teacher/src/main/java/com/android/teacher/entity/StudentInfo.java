package com.android.teacher.entity;

import java.util.List;

/**
 * Created by wukanghui on 2017/11/13.
 */

public class StudentInfo {



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
         * relationship : 父亲
         * parentid : 1
         * parentname : 赵世杰
         * mobile : 13312345678
         * weixinopenid :
         * smsflag : 1
         * smsbalance : null
         * appflag : null
         * lasttime : Nov 23 2017 05:33:58:500AM
         * finger :
         * pushtoken : b312f544d64c0e068ca2b92969c5b0a60a25c8d12b4d06c31e042f0df65c8859
         * arrivepushflag : 1
         * classpushflag : 1
         * leavepushflag : 1
         * homeworkpushflag : 1
         * notifypushflag : 1
         * qq : 1603903167
         */

        private String relationship;
        private int parentid;
        private String parentname;
        private String mobile;
        private String weixinopenid;
        private int smsflag;
        private Object smsbalance;
        private Object appflag;
        private String lasttime;
        private String finger;
        private String pushtoken;
        private int arrivepushflag;
        private int classpushflag;
        private int leavepushflag;
        private int homeworkpushflag;
        private int notifypushflag;
        private int qq;

        public String getRelationship() {
            return relationship;
        }

        public void setRelationship(String relationship) {
            this.relationship = relationship;
        }

        public int getParentid() {
            return parentid;
        }

        public void setParentid(int parentid) {
            this.parentid = parentid;
        }

        public String getParentname() {
            return parentname;
        }

        public void setParentname(String parentname) {
            this.parentname = parentname;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getWeixinopenid() {
            return weixinopenid;
        }

        public void setWeixinopenid(String weixinopenid) {
            this.weixinopenid = weixinopenid;
        }

        public int getSmsflag() {
            return smsflag;
        }

        public void setSmsflag(int smsflag) {
            this.smsflag = smsflag;
        }

        public Object getSmsbalance() {
            return smsbalance;
        }

        public void setSmsbalance(Object smsbalance) {
            this.smsbalance = smsbalance;
        }

        public Object getAppflag() {
            return appflag;
        }

        public void setAppflag(Object appflag) {
            this.appflag = appflag;
        }

        public String getLasttime() {
            return lasttime;
        }

        public void setLasttime(String lasttime) {
            this.lasttime = lasttime;
        }

        public String getFinger() {
            return finger;
        }

        public void setFinger(String finger) {
            this.finger = finger;
        }

        public String getPushtoken() {
            return pushtoken;
        }

        public void setPushtoken(String pushtoken) {
            this.pushtoken = pushtoken;
        }

        public int getArrivepushflag() {
            return arrivepushflag;
        }

        public void setArrivepushflag(int arrivepushflag) {
            this.arrivepushflag = arrivepushflag;
        }

        public int getClasspushflag() {
            return classpushflag;
        }

        public void setClasspushflag(int classpushflag) {
            this.classpushflag = classpushflag;
        }

        public int getLeavepushflag() {
            return leavepushflag;
        }

        public void setLeavepushflag(int leavepushflag) {
            this.leavepushflag = leavepushflag;
        }

        public int getHomeworkpushflag() {
            return homeworkpushflag;
        }

        public void setHomeworkpushflag(int homeworkpushflag) {
            this.homeworkpushflag = homeworkpushflag;
        }

        public int getNotifypushflag() {
            return notifypushflag;
        }

        public void setNotifypushflag(int notifypushflag) {
            this.notifypushflag = notifypushflag;
        }

        public int getQq() {
            return qq;
        }

        public void setQq(int qq) {
            this.qq = qq;
        }
    }
}
