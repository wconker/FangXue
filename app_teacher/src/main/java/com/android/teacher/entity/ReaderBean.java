package com.android.teacher.entity;

import java.util.List;

/**
 * Created by softsea on 17/12/14.
 */

public class ReaderBean {


    /**
     * cmd : message.readinfo
     * code : 1
     * msg : {"client":{"code":9002,"message":"信息阅读详情"},"system":{"code":2000,"message":"正常操作"}}
     * message : 信息阅读详情
     * data : [{"studentno":1,"studentname":"赵军","headerimg":"http://www.softsea.cn/fangxue/getimg/getSfzImg.php?imgname=upload/user_img/image/20171205/20171205174312670240.jpg","readflag":1,"studentid":9},{"studentno":1,"studentname":"赵军","headerimg":"http://www.softsea.cn/fangxue/getimg/getSfzImg.php?imgname=upload/user_img/image/20171205/20171205174312670240.jpg","readflag":0,"studentid":9},{"studentno":2,"studentname":"钱三","headerimg":null,"readflag":0,"studentid":11},{"studentno":3,"studentname":"孙斌","headerimg":null,"readflag":0,"studentid":12},{"studentno":4,"studentname":"李水","headerimg":"http://www.softsea.cn/fangxue/getimg/getSfzImg.php?imgname=upload/user_img/image/20171208/20171208150025378838.jpg","readflag":0,"studentid":13},{"studentno":511,"studentname":"王蔷","headerimg":"http://www.softsea.cn/fangxue/getimg/getSfzImg.php?imgname=upload/user_img/image/20171205/20171205174453850105.jpg","readflag":0,"studentid":14},{"studentno":6,"studentname":"周珊珊","headerimg":"http://www.softsea.cn/fangxue/getimg/getSfzImg.php?imgname=upload/user_img/image/20171121/20171121134430332306.jpg","readflag":0,"studentid":15},{"studentno":7,"studentname":"吴山石","headerimg":"http://www.softsea.cn/fangxue/getimg/getSfzImg.php?imgname=upload/user_img/image/20171120/20171120152732374535.jpg","readflag":0,"studentid":16},{"studentno":83,"studentname":"郑江山","headerimg":null,"readflag":0,"studentid":17},{"studentno":9,"studentname":"冯飞辉","headerimg":null,"readflag":0,"studentid":18},{"studentno":10,"studentname":"陈思思","headerimg":"http://www.softsea.cn/fangxue/getimg/getSfzImg.php?imgname=upload/user_img/image/20171120/20171120150546481661.png","readflag":0,"studentid":19},{"studentno":11,"studentname":"褚开可","headerimg":null,"readflag":0,"studentid":20},{"studentno":12,"studentname":"卫青","headerimg":null,"readflag":0,"studentid":21},{"studentno":13,"studentname":"蒋启军","headerimg":null,"readflag":0,"studentid":22},{"studentno":14,"studentname":"沈洋","headerimg":null,"readflag":0,"studentid":23},{"studentno":15,"studentname":"韩熙","headerimg":null,"readflag":0,"studentid":24},{"studentno":16,"studentname":"杨珊珊","headerimg":null,"readflag":0,"studentid":25},{"studentno":17,"studentname":"朱姗姗","headerimg":null,"readflag":0,"studentid":26},{"studentno":18,"studentname":"秦叔宝","headerimg":null,"readflag":0,"studentid":27},{"studentno":19,"studentname":"尤思丽","headerimg":null,"readflag":0,"studentid":28},{"studentno":20,"studentname":"许绍发","headerimg":null,"readflag":0,"studentid":29},{"studentno":21,"studentname":"何红飞","headerimg":null,"readflag":0,"studentid":30},{"studentno":22,"studentname":"吕方","headerimg":null,"readflag":0,"studentid":31},{"studentno":23,"studentname":"施清","headerimg":null,"readflag":0,"studentid":32},{"studentno":24,"studentname":"张生贵","headerimg":null,"readflag":0,"studentid":33},{"studentno":25,"studentname":"孔少进","headerimg":null,"readflag":0,"studentid":34},{"studentno":26,"studentname":"曹妃甸","headerimg":"http://www.softsea.cn/fangxue/getimg/getSfzImg.php?imgname=upload/user_img/image/20171117/20171117145034100646.jpg","readflag":0,"studentid":35},{"studentno":27,"studentname":"严子林","headerimg":null,"readflag":0,"studentid":36},{"studentno":28,"studentname":"华国锋","headerimg":null,"readflag":0,"studentid":37},{"studentno":29,"studentname":"金所炫","headerimg":null,"readflag":0,"studentid":38}]
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
         * client : {"code":9002,"message":"信息阅读详情"}
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
             * code : 9002
             * message : 信息阅读详情
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
         * studentno : 1
         * studentname : 赵军
         * headerimg : http://www.softsea.cn/fangxue/getimg/getSfzImg.php?imgname=upload/user_img/image/20171205/20171205174312670240.jpg
         * readflag : 1
         * studentid : 9
         */

        private int studentno;
        private String studentname;
        private String headerimg;
        private int readflag;
        private int studentid;

        public int getParentid() {
            return parentid;
        }

        public void setParentid(int parentid) {
            this.parentid = parentid;
        }

        private int parentid;

        public int getStudentno() {
            return studentno;
        }

        public void setStudentno(int studentno) {
            this.studentno = studentno;
        }

        public String getStudentname() {
            return studentname;
        }

        public void setStudentname(String studentname) {
            this.studentname = studentname;
        }

        public String getHeaderimg() {
            return headerimg;
        }

        public void setHeaderimg(String headerimg) {
            this.headerimg = headerimg;
        }

        public int getReadflag() {
            return readflag;
        }

        public void setReadflag(int readflag) {
            this.readflag = readflag;
        }

        public int getStudentid() {
            return studentid;
        }

        public void setStudentid(int studentid) {
            this.studentid = studentid;
        }
    }
}
