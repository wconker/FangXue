package com.android.teacher.entity;

import java.util.List;

/**
 * Created by softsea on 17/10/24.
 */

public class Homework {
    /**
     * code : 1
     * message : 取家庭作业列表成功
     * data : [{"id":1,"worktitle":"今日作业","author":"吕娇","workcontent":"语文：\n组合训练 P22\n2. 预习杞人忧天","releasetime":"2017-10-14 15:52"}]
     * cmd : homework.getlist
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
         * id : 1
         * worktitle : 今日作业
         * author : 吕娇
         * workcontent : 语文：
         * 组合训练 P22
         * 2. 预习杞人忧天
         * releasetime : 2017-10-14 15:52
         */

        private int id;
        private String worktitle;
        private String author;

        public int getShow() {
            return show;
        }

        public void setShow(int show) {
            this.show = show;
        }

        private int show;
        private String workcontent;
        private String releasetime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getWorktitle() {
            return worktitle;
        }

        public void setWorktitle(String worktitle) {
            this.worktitle = worktitle;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getWorkcontent() {
            return workcontent;
        }

        public void setWorkcontent(String workcontent) {
            this.workcontent = workcontent;
        }

        public String getReleasetime() {
            return releasetime;
        }

        public void setReleasetime(String releasetime) {
            this.releasetime = releasetime;
        }
    }
}
