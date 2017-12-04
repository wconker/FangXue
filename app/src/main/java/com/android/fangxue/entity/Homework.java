package com.android.fangxue.entity;

import java.util.List;

/**
 * Created by softsea on 17/10/24.
 */

public class Homework {


    /**
     * cmd : message.getlist
     * message : 获取通知消息列表成功
     * code : 1
     * data : [{"id":11,"worktitle":"10.24作业","author":"林晓满","releaser":3,"noticetype":"2","workcontent":"作业如下:","releasetime":"2017-11-05 17:30","show":1,"readnumber":4,"pic":[{"thumbnail":"http://www.softsea.cn/fangxue/getimg/getSfzImg.php?imgname=upload/user_img/image/20171031/thum_20171031093055731769.jpg","picpath":"http://www.softsea.cn/fangxue/getimg/getSfzImg.php?imgname=upload/user_img/image/20171031/20171031093055731769.jpg"}],"headerimg":"http://www.softsea.cn/fangxue/getimg/getSfzImg.php?imgname=upload/user_img/image/20171102/20171102110330426337.jpg"},{"id":10,"worktitle":"10.30语文作业","author":"吕娇","releaser":3,"noticetype":"2","workcontent":"语文作业本p44到46页的1到8题。 默写从百草园到三味书屋的第二段，家长签字。作业本P52的1一3题。","releasetime":"2017-11-05 17:00","show":1,"readnumber":3,"pic":[],"headerimg":"http://www.softsea.cn/fangxue/getimg/getSfzImg.php?imgname=upload/user_img/image/20171102/20171102110330426337.jpg"},{"id":36,"worktitle":"11-1、语文回家作业","author":"吴康辉","releaser":2,"noticetype":"2","workcontent":"1.词语手册p80-94，红笔要订正\n2.复习《世说新语》，明早文言文测试\n3.带上名著《朝花夕拾》和pad，明天阅读指导课。","releasetime":"2017-11-05 16:48","show":1,"readnumber":3,"pic":[],"headerimg":null}]
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
         * id : 11
         * worktitle : 10.24作业
         * author : 林晓满
         * releaser : 3
         * noticetype : 2
         * workcontent : 作业如下:
         * releasetime : 2017-11-05 17:30
         * show : 1
         * readnumber : 4
         * pic : [{"thumbnail":"http://www.softsea.cn/fangxue/getimg/getSfzImg.php?imgname=upload/user_img/image/20171031/thum_20171031093055731769.jpg","picpath":"http://www.softsea.cn/fangxue/getimg/getSfzImg.php?imgname=upload/user_img/image/20171031/20171031093055731769.jpg"}]
         * headerimg : http://www.softsea.cn/fangxue/getimg/getSfzImg.php?imgname=upload/user_img/image/20171102/20171102110330426337.jpg
         */

        private int id;
        private String worktitle;
        private String author;
        private int releaser;
        private String noticetype;
        private String workcontent;
        private String releasetime;
        private int show;
        private int readnumber;

        public String getLesson() {
            return lesson;
        }

        public void setLesson(String lesson) {
            this.lesson = lesson;
        }

        private String lesson;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private String name;
        private String headerimg;
        private List<PicBean> pic;

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

        public List<PicBean> getPic() {
            return pic;
        }

        public void setPic(List<PicBean> pic) {
            this.pic = pic;
        }

        public static class PicBean {
            /**
             * thumbnail : http://www.softsea.cn/fangxue/getimg/getSfzImg.php?imgname=upload/user_img/image/20171031/thum_20171031093055731769.jpg
             * picpath : http://www.softsea.cn/fangxue/getimg/getSfzImg.php?imgname=upload/user_img/image/20171031/20171031093055731769.jpg
             */

            private String thumbnail;
            private String picpath;

            public String getThumbnail() {
                return thumbnail;
            }

            public void setThumbnail(String thumbnail) {
                this.thumbnail = thumbnail;
            }

            public String getPicpath() {
                return picpath;
            }

            public void setPicpath(String picpath) {
                this.picpath = picpath;
            }
        }
    }
}
