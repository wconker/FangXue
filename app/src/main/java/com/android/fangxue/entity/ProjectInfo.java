package com.android.fangxue.entity;

import java.util.List;

/**
 * Created by fangxue on 17/9/13.
 */

public class ProjectInfo {


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
         * summary : 个人信息设置
         * date_submitted : 2017-02-09 15:27:26
         * reportername : 韩先武
         */

        private String summary;
        private String date_submitted;
        private String reportername;

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getDate_submitted() {
            return date_submitted;
        }

        public void setDate_submitted(String date_submitted) {
            this.date_submitted = date_submitted;
        }

        public String getReportername() {
            return reportername;
        }

        public void setReportername(String reportername) {
            this.reportername = reportername;
        }
    }
}
