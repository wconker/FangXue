package com.android.teacher.entity;

import java.util.List;

/**
 * Created by softsea on 17/11/1.
 */

public class TeacherBean {


    /**
     * data : [{"teachername":"吕乔","teacherid":1,"headerimg":"http://www.softsea.cn/fangxue/getimg/getSfzImg.php?imgname=upload/user_img/image/20171102/20171102152826487384.jpg","lesson":"语文老师","mobile":"17766889999"},{"teachername":"韩大启","teacherid":5,"headerimg":"http://www.softsea.cn/fangxue/getimg/getSfzImg.php?imgname=upload/user_img/image/20171120/20171120145055230658.jpg","lesson":"英语老师","mobile":"13312345678"},{"teachername":"张新","teacherid":6,"headerimg":null,"lesson":"音乐老师","mobile":"12812457896"},{"teachername":"易建军","teacherid":7,"headerimg":null,"lesson":"体育老师","mobile":"12811111111"},{"teachername":"单于熊","teacherid":8,"headerimg":"http://www.softsea.cn/fangxue/getimg/getSfzImg.php?imgname=upload/user_img/image/20171123/20171123173047606826.jpg","lesson":"美术老师","mobile":"12822222222"},{"teachername":"罗峰","teacherid":9,"headerimg":null,"lesson":"信息技术老师","mobile":"12866666666"},{"teachername":"韩飞来","teacherid":16,"headerimg":null,"lesson":"老师","mobile":"13396551999"},{"teachername":"陈奕迅","teacherid":12,"headerimg":null,"lesson":"班主任老师","mobile":"10211111111"},{"teachername":"韩红","teacherid":17,"headerimg":null,"lesson":"老师","mobile":"13396551996"}]
     * message : 成功
     * code : 1
     * cmd : class.getteacherlist
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
         * teachername : 吕乔
         * teacherid : 1
         * headerimg : http://www.softsea.cn/fangxue/getimg/getSfzImg.php?imgname=upload/user_img/image/20171102/20171102152826487384.jpg
         * lesson : 语文老师
         * mobile : 17766889999
         */

        private String teachername;
        private int teacherid;
        private String headerimg;
        private String lesson;
        private String mobile;

        public String getTeachername() {
            return teachername;
        }

        public void setTeachername(String teachername) {
            this.teachername = teachername;
        }

        public int getTeacherid() {
            return teacherid;
        }

        public void setTeacherid(int teacherid) {
            this.teacherid = teacherid;
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

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }
}
