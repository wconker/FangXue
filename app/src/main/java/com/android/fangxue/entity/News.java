package com.android.fangxue.entity;

import java.util.List;

/**
 * Created by softsea on 17/11/9.
 */

public class News {


    /**
     * cmd : system.getnewslist
     * code : 1
     * data : [{"author":1,"createtime":"2017-11-03 09:18:05","outline":"在生活中如何落实佛法；如何修行；如何体现佛法的精神？","pic":null,"title":"净慧长老：大小乘与生活禅","url":null},{"author":null,"createtime":"2017-11-02 11:38:34","outline":"原标题：","pic":null,"title":"新闻","url":null}]
     * message : 新闻列表成功
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
         * author : 1
         * createtime : 2017-11-03 09:18:05
         * outline : 在生活中如何落实佛法；如何修行；如何体现佛法的精神？
         * pic : null
         * title : 净慧长老：大小乘与生活禅
         * url : null
         */

        private int author;
        private String createtime;
        private String outline;
        private Object pic;
        private String title;
        private Object url;

        public int getAuthor() {
            return author;
        }

        public void setAuthor(int author) {
            this.author = author;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getOutline() {
            return outline;
        }

        public void setOutline(String outline) {
            this.outline = outline;
        }

        public Object getPic() {
            return pic;
        }

        public void setPic(Object pic) {
            this.pic = pic;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Object getUrl() {
            return url;
        }

        public void setUrl(Object url) {
            this.url = url;
        }
    }
}
