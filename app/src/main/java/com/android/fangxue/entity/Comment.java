package com.android.fangxue.entity;

import java.util.List;

/**
 * Created by softsea on 17/12/21.
 */

public class Comment {


    /**
     * cmd : message.getCommentList
     * code : 1
     * msg : {"client":{"code":10012},"system":{"code":2000,"message":"正常操作"}}
     * message : 获取评论列表成功
     * data : [{"userid":1,"usertype":"P","delflag":1,"commenttime":"今天10:13:41","commentcontent":"好的","headerimg":"","username":"赵军爸爸"}]
     */

    private String cmd;
    private int code;
    private MsgBean msg;
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

    public MsgBean getMsg() {
        return msg;
    }

    public void setMsg(MsgBean msg) {
        this.msg = msg;
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

    public static class MsgBean {
        /**
         * client : {"code":10012}
         * system : {"code":2000,"message":"正常操作"}
         */

        private ClientBean client;
        private SystemBean system;

        public ClientBean getClient() {
            return client;
        }

        public void setClient(ClientBean client) {
            this.client = client;
        }

        public SystemBean getSystem() {
            return system;
        }

        public void setSystem(SystemBean system) {
            this.system = system;
        }

        public static class ClientBean {
            /**
             * code : 10012
             */

            private int code;

            public int getCode() {
                return code;
            }

            public void setCode(int code) {
                this.code = code;
            }
        }

        public static class SystemBean {
            /**
             * code : 2000
             * message : 正常操作
             */

            private int code;
            private String message;

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
        }
    }

    public static class DataBean {
        /**
         * userid : 1
         * usertype : P
         * delflag : 1
         * commenttime : 今天10:13:41
         * commentcontent : 好的
         * headerimg :
         * username : 赵军爸爸
         */

        private int userid;
        private String usertype;
        private int delflag;
        private String commenttime;
        private String commentcontent;
        private String headerimg;
        private String username;

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getUsertype() {
            return usertype;
        }

        public void setUsertype(String usertype) {
            this.usertype = usertype;
        }

        public int getDelflag() {
            return delflag;
        }

        public void setDelflag(int delflag) {
            this.delflag = delflag;
        }

        public String getCommenttime() {
            return commenttime;
        }

        public void setCommenttime(String commenttime) {
            this.commenttime = commenttime;
        }

        public String getCommentcontent() {
            return commentcontent;
        }

        public void setCommentcontent(String commentcontent) {
            this.commentcontent = commentcontent;
        }

        public String getHeaderimg() {
            return headerimg;
        }

        public void setHeaderimg(String headerimg) {
            this.headerimg = headerimg;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
