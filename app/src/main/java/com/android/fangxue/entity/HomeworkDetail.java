package com.android.fangxue.entity;

import java.util.List;

/**
 * Created by softsea on 17/10/25.
 */

public class HomeworkDetail {


    /**
     * message : 获取成功
     * data : [{"releasetime":"2017-10-27 16:40","author":"小白","id":7,"classid":1,"worktitle":"布置作业","workcontent":"抄写英文一百次","releaser":3,"pic":[{"picpath":"http://www.softsea.cn/fangxue/getimg/getSfzImg.php?imgname=upload/user_img/image/20171027/20171027163930510570.jpg","thumbnail":"http://www.softsea.cn/fangxue/getimg/getSfzImg.php?imgname=upload/user_img/image/20171027/201710271634511733182.jpg"},{"picpath":"http://www.softsea.cn/fangxue/getimg/getSfzImg.php?imgname=upload/user_img/image/20171027/20171027163948145356.jpg","thumbnail":"http://www.softsea.cn/fangxue/getimg/getSfzImg.php?imgname=upload/user_img/image/20171027/201710271634511733182.jpg"}],"total":3}]
     * code : 1
     * cmd : message.gethomeworkinfo
     */

    private String message;
    private int code;
    private String cmd;
    private List<DataBean> data;
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
         * releasetime : 2017-10-27 16:40
         * author : 小白
         * id : 7
         * classid : 1
         * worktitle : 布置作业
         * workcontent : 抄写英文一百次
         * releaser : 3
         * pic : [{"picpath":"http://www.softsea.cn/fangxue/getimg/getSfzImg.php?imgname=upload/user_img/image/20171027/20171027163930510570.jpg","thumbnail":"http://www.softsea.cn/fangxue/getimg/getSfzImg.php?imgname=upload/user_img/image/20171027/201710271634511733182.jpg"},{"picpath":"http://www.softsea.cn/fangxue/getimg/getSfzImg.php?imgname=upload/user_img/image/20171027/20171027163948145356.jpg","thumbnail":"http://www.softsea.cn/fangxue/getimg/getSfzImg.php?imgname=upload/user_img/image/20171027/201710271634511733182.jpg"}]
         * total : 3
         */

        private String releasetime;
        private String author;
        private int id;
        private int classid;
        private String worktitle;
        private String workcontent;
        private int releaser;
        private int total;
        private List<PicBean> pic;
        public String getHeaderimg() {
            return headerimg;
        }

        public void setHeaderimg(String headerimg) {
            this.headerimg = headerimg;
        }

        private String headerimg;
        public String getReleasetime() {
            return releasetime;
        }

        public void setReleasetime(String releasetime) {
            this.releasetime = releasetime;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getClassid() {
            return classid;
        }

        public void setClassid(int classid) {
            this.classid = classid;
        }

        public String getWorktitle() {
            return worktitle;
        }

        public void setWorktitle(String worktitle) {
            this.worktitle = worktitle;
        }

        public String getWorkcontent() {
            return workcontent;
        }

        public void setWorkcontent(String workcontent) {
            this.workcontent = workcontent;
        }

        public int getReleaser() {
            return releaser;
        }

        public void setReleaser(int releaser) {
            this.releaser = releaser;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<PicBean> getPic() {
            return pic;
        }

        public void setPic(List<PicBean> pic) {
            this.pic = pic;
        }

        public static class PicBean {
            /**
             * picpath : http://www.softsea.cn/fangxue/getimg/getSfzImg.php?imgname=upload/user_img/image/20171027/20171027163930510570.jpg
             * thumbnail : http://www.softsea.cn/fangxue/getimg/getSfzImg.php?imgname=upload/user_img/image/20171027/201710271634511733182.jpg
             */

            private String picpath;
            private String thumbnail;

            public String getPicpath() {
                return picpath;
            }

            public void setPicpath(String picpath) {
                this.picpath = picpath;
            }

            public String getThumbnail() {
                return thumbnail;
            }

            public void setThumbnail(String thumbnail) {
                this.thumbnail = thumbnail;
            }
        }
    }
}
