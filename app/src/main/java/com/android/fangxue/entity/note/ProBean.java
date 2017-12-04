package com.android.fangxue.entity.note;

import java.util.List;

/**
 * Created by wukanghui on 2017/9/3.
 */

public class ProBean {


    /**
     * cmd : getLastProjectList
     * code : 0
     * message : getLastProjectList success.
     * data : [{"xmbh":"2013248","xmmc":"新沂公路港"}]
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
         * xmbh : 2013248
         * xmmc : 新沂公路港
         */

        private String xmbh;
        private String xmmc;

        public String getXmbh() {
            return xmbh;
        }

        public void setXmbh(String xmbh) {
            this.xmbh = xmbh;
        }

        public String getXmmc() {
            return xmmc;
        }

        public void setXmmc(String xmmc) {
            this.xmmc = xmmc;
        }
    }
}
