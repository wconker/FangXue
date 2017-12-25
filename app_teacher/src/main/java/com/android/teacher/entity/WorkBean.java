package com.android.teacher.entity;

import java.util.List;

/**
 * Created by fangxue on 17/9/1.
 */

public class WorkBean {


    /**
     * cmd : getWorkingDynamics
     * code : 0
     * message : getWorkingDynamics success.
     * data : [{"ctype":"考勤","ctime":"09:08","cname":"王峻名","content":"上班打卡"},{"ctype":"考勤","ctime":"08:50","cname":"李媛媛","content":"上班打卡"},{"ctype":"工作日志","ctime":"08:45","cname":"白耀强","content":"电视机服务的维护"}]
     */

    private String cmd;
    private int code;
    private String message;
    private List<DataBean> data;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * ctype : 考勤
         * ctime : 09:08
         * cname : 王峻名
         * content : 上班打卡
         */

        private String ctype;
        private String ctime;
        private String cname;
        private String content;

        public String getCtype() {
            return ctype;
        }

        public void setCtype(String ctype) {
            this.ctype = ctype;
        }

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getCname() {
            return cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
