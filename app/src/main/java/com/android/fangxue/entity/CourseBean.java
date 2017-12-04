package com.android.fangxue.entity;

import java.util.List;

/**
 * Created by softsea on 17/9/28.
 */

public class CourseBean {

    private int code;
    private String message;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {

        private String fromtime;
        private String totime;
        private String sectionname;
        private String coursename;

        public String getStudyday() {
            return studyday;
        }

        public void setStudyday(String studyday) {
            this.studyday = studyday;
        }

        private String studyday;

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

        public String getSectionname() {
            return sectionname;
        }

        public void setSectionname(String sectionname) {
            this.sectionname = sectionname;
        }

        public String getCoursename() {
            return coursename;
        }

        public void setCoursename(String coursename) {
            this.coursename = coursename;
        }
    }
}
