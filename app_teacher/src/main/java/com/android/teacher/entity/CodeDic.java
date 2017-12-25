package com.android.teacher.entity;

import java.util.List;

/**
 * Created by softsea on 17/12/7.
 */

public class CodeDic {


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
         * mc : 班主任
         * dm : null
         * setnumber : 21
         * remark :
         */

        private String mc;
        private Object dm;
        private int setnumber;
        private String remark;

        public String getMc() {
            return mc;
        }

        public void setMc(String mc) {
            this.mc = mc;
        }

        public Object getDm() {
            return dm;
        }

        public void setDm(Object dm) {
            this.dm = dm;
        }

        public int getSetnumber() {
            return setnumber;
        }

        public void setSetnumber(int setnumber) {
            this.setnumber = setnumber;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
