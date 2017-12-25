package com.android.teacher.entity;

import java.util.List;

/**
 * Created by softsea on 17/10/31.
 */

public class MessageDetailBean {


    /**
     * cmd : message.getlist
     * message : 获取通知消息列表成功
     * code : 1
     * data : [{"id":1044,"worktitle":"分享","author":"单于熊","releaser":8,"usertype":"T","classid":1,"noticetype":"3","workcontent":"分享","releasetime":"2017-12-01 09:51","show":0,"readnumber":0,"pic":[],"headerimg":"http://www.softsea.cn/fangxue/getimg/getSfzImg.php?imgname=upload/user_img/image/20171128/20171128132956650824.jpg","lesson":"美术","delflag":1}]
     */

    private String cmd;
    private String message;
    private int code;
    private List<DataBean> data;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1044
         * worktitle : 分享
         * author : 单于熊
         * releaser : 8
         * usertype : T
         * classid : 1
         * noticetype : 3
         * workcontent : 分享
         * releasetime : 2017-12-01 09:51
         * show : 0
         * readnumber : 0
         * pic : []
         * headerimg : http://www.softsea.cn/fangxue/getimg/getSfzImg.php?imgname=upload/user_img/image/20171128/20171128132956650824.jpg
         * lesson : 美术
         * delflag : 1
         */

        private int id;
        private String worktitle;
        private String author;
        private int releaser;
        private String usertype;
        private int classid;
        private String noticetype;
        private String workcontent;
        private String releasetime;
        private int show;
        private int readnumber;
        private String headerimg;
        private String lesson;
        private int delflag;
        private List<?> pic;

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

        public int getReleaser() {
            return releaser;
        }

        public void setReleaser(int releaser) {
            this.releaser = releaser;
        }

        public String getUsertype() {
            return usertype;
        }

        public void setUsertype(String usertype) {
            this.usertype = usertype;
        }

        public int getClassid() {
            return classid;
        }

        public void setClassid(int classid) {
            this.classid = classid;
        }

        public String getNoticetype() {
            return noticetype;
        }

        public void setNoticetype(String noticetype) {
            this.noticetype = noticetype;
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

        public int getShow() {
            return show;
        }

        public void setShow(int show) {
            this.show = show;
        }

        public int getReadnumber() {
            return readnumber;
        }

        public void setReadnumber(int readnumber) {
            this.readnumber = readnumber;
        }

        public String getHeaderimg() {
            return headerimg;
        }

        public void setHeaderimg(String headerimg) {
            this.headerimg = headerimg;
        }

        public String getLesson() {
            return lesson;
        }

        public void setLesson(String lesson) {
            this.lesson = lesson;
        }

        public int getDelflag() {
            return delflag;
        }

        public void setDelflag(int delflag) {
            this.delflag = delflag;
        }

        public List<?> getPic() {
            return pic;
        }

        public void setPic(List<?> pic) {
            this.pic = pic;
        }
    }
}
