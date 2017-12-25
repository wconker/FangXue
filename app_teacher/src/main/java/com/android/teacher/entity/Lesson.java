package com.android.teacher.entity;

/**
 * Created by softsea on 17/11/24.
 */

public class Lesson {
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
    private int dayname;
    private String fromtime;
    private String totime;
    private String coursename;

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
