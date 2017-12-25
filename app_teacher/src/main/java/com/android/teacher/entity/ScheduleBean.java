package com.android.teacher.entity;

import java.util.List;

/**
 * Created by softsea on 17/11/24.
 */

public class ScheduleBean {
    /**
     * code : 1
     * message : 取课程表信息列表成功
     * data : [{"list":[{"sectionname":"一","teachername":"吕乔","teacherid":1,"dayname":1,"fromtime":"08:00","totime":"08:40","coursename":"英语"},{"sectionname":"二","teachername":null,"teacherid":null,"dayname":1,"fromtime":"08:50","totime":"09:30","coursename":"数学"},{"sectionname":"三","teachername":null,"teacherid":null,"dayname":1,"fromtime":"10:20","totime":"11:00","coursename":"体育"},{"sectionname":"四","teachername":null,"teacherid":null,"dayname":1,"fromtime":"11:15","totime":"11:55","coursename":"语文"},{"sectionname":"五","teachername":null,"teacherid":null,"dayname":1,"fromtime":"13:10","totime":"13:50","coursename":"科学"},{"sectionname":"六","teachername":null,"teacherid":null,"dayname":1,"fromtime":"14:05","totime":"14:45","coursename":"文综"},{"sectionname":"七","teachername":null,"teacherid":null,"dayname":1,"fromtime":"14:55","totime":"15:35","coursename":"信息"},{"sectionname":"八","teachername":null,"teacherid":null,"dayname":1,"fromtime":"15:45","totime":"16:25","coursename":"活动"}],"daynum":1,"dayname":"星期一"}]
     * cmd : class.getSchedulelist
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
         * list : [{"sectionname":"一","teachername":"吕乔","teacherid":1,"dayname":1,"fromtime":"08:00","totime":"08:40","coursename":"英语"},{"sectionname":"二","teachername":null,"teacherid":null,"dayname":1,"fromtime":"08:50","totime":"09:30","coursename":"数学"},{"sectionname":"三","teachername":null,"teacherid":null,"dayname":1,"fromtime":"10:20","totime":"11:00","coursename":"体育"},{"sectionname":"四","teachername":null,"teacherid":null,"dayname":1,"fromtime":"11:15","totime":"11:55","coursename":"语文"},{"sectionname":"五","teachername":null,"teacherid":null,"dayname":1,"fromtime":"13:10","totime":"13:50","coursename":"科学"},{"sectionname":"六","teachername":null,"teacherid":null,"dayname":1,"fromtime":"14:05","totime":"14:45","coursename":"文综"},{"sectionname":"七","teachername":null,"teacherid":null,"dayname":1,"fromtime":"14:55","totime":"15:35","coursename":"信息"},{"sectionname":"八","teachername":null,"teacherid":null,"dayname":1,"fromtime":"15:45","totime":"16:25","coursename":"活动"}]
         * daynum : 1
         * dayname : 星期一
         */

        private int daynum;
        private String dayname;
        private List<ListBean> list;

        public int getDaynum() {
            return daynum;
        }

        public void setDaynum(int daynum) {
            this.daynum = daynum;
        }

        public String getDayname() {
            return dayname;
        }

        public void setDayname(String dayname) {
            this.dayname = dayname;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * sectionname : 一
             * teachername : 吕乔
             * teacherid : 1
             * dayname : 1
             * fromtime : 08:00
             * totime : 08:40
             * coursename : 英语
             */

            private String sectionname;
            private String teachername;
            private int teacherid;


            private int id;
            private int dayname;
            private String fromtime;
            private String totime;
            private String coursename;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getSectionname() {
                return sectionname;
            }

            public void setSectionname(String sectionname) {
                this.sectionname = sectionname;
            }

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

            public int getDayname() {
                return dayname;
            }

            public void setDayname(int dayname) {
                this.dayname = dayname;
            }

            public String getFromtime() {
                return fromtime;
            }

            public void setFromtime(String fromtime) {
                this.fromtime = fromtime;
            }

            public String getTotime() {
                return totime;
            }

            public void setTotime(String totime) {
                this.totime = totime;
            }

            public String getCoursename() {
                return coursename;
            }

            public void setCoursename(String coursename) {
                this.coursename = coursename;
            }
        }
    }
}
