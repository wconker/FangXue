package com.android.fangxue.entity;

import java.util.List;

/**
 * Created by softsea on 17/10/30.
 */

public class SchoolBean {


    /**
     * code : 1
     * message : 取学校信息列表成功
     * data : [{"schoolname":"演示学校","schoolnm":"演示学校","contacts":"韩大启","creater":null,"contactnumber":"13312345678","address":"高新实验小区","schoolid":1},{"schoolname":"西兴职中","schoolnm":"西职","contacts":"吴康辉","creater":"曾思铭","contactnumber":"15658690695","address":"四川省,巴中市,平昌县,西兴镇","schoolid":2}]
     * cmd : school.getList
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
         * schoolname : 演示学校
         * schoolnm : 演示学校
         * contacts : 韩大启
         * creater : null
         * contactnumber : 13312345678
         * address : 高新实验小区
         * schoolid : 1
         */

        private String schoolname;
        private String schoolnm;
        private String contacts;
        private Object creater;
        private String contactnumber;
        private String address;
        private int schoolid;

        public String getSchoolname() {
            return schoolname;
        }

        public void setSchoolname(String schoolname) {
            this.schoolname = schoolname;
        }

        public String getSchoolnm() {
            return schoolnm;
        }

        public void setSchoolnm(String schoolnm) {
            this.schoolnm = schoolnm;
        }

        public String getContacts() {
            return contacts;
        }

        public void setContacts(String contacts) {
            this.contacts = contacts;
        }

        public Object getCreater() {
            return creater;
        }

        public void setCreater(Object creater) {
            this.creater = creater;
        }

        public String getContactnumber() {
            return contactnumber;
        }

        public void setContactnumber(String contactnumber) {
            this.contactnumber = contactnumber;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getSchoolid() {
            return schoolid;
        }

        public void setSchoolid(int schoolid) {
            this.schoolid = schoolid;
        }
    }
}
