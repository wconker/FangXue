package com.android.fangxue.newwork;

import android.content.Context;
import android.util.Log;

import com.android.fangxue.callback.MessageCallBack;
import com.android.fangxue.callback.ServiceMessage;
import com.android.fangxue.utils.FileUtil;
import com.android.fangxue.utils.JSONUtils;
import com.android.fangxue.utils.SharedPrefsUtil;
import com.android.fangxue.utils.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.ws.WebSocket;
import okhttp3.ws.WebSocketCall;
import okhttp3.ws.WebSocketListener;
import okio.Buffer;

import static okhttp3.ws.WebSocket.TEXT;

/**
 * Created by conker on 2017/8/31.
 */

public class HttpCenter {
    public static OkHttpClient okHttpClient;
    public static WebSocket webSocket = null;
    public static MessageCallBack messageCallBack;
    public static ServiceMessage serviceMessage;
    public static String TempStringMessage = "";
    public int Channel = 0;
    public static Context context;
    private FileUtil fileUtil = new FileUtil();
    private static int ifErrorDisConnect = 0;
    private CommandCenter commandCenter = new CommandCenter();
    ExecutorService mExecutor = Executors.newFixedThreadPool(8);
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/jpg");

    private Thread Ding = null;

    public static synchronized void InstancesOkhttp() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient().newBuilder().
                    readTimeout(3000, TimeUnit.SECONDS)//设置读取超时时间
                    .writeTimeout(3000, TimeUnit.SECONDS)//设置写的超时时间
                    .connectTimeout(3000, TimeUnit.SECONDS)//设置连接超时时间
                    .build();
        }
    }


    public void setCallBack(MessageCallBack callBack) {
        messageCallBack = callBack;
    }

    public void setServiceMessage(ServiceMessage rserviceMessage) {
        serviceMessage = rserviceMessage;
    }

    private long prelongTim = 0;//定义上一次单击的时间

    private long curTime = 1;


    private void DealRun(final String str) {
        final String m = str;
        Log.e("Android发送了", str + "");
        Runnable runnable;
        runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    try {
                        HttpCenter.webSocket.sendMessage(RequestBody.create(TEXT, m));
                    } catch (IOException e) {
                        HttpCenter.webSocket = null;
                        initWebsocket();
                        TempStringMessage = m;

                    }
                } catch (Exception ex) {
                    Log.e("webscoket 出现问题", ex.getMessage());
                }
            }

        };
        mExecutor.execute(runnable);
    }


    public void send(final String str) {
        Log.e("Android发送了", "于发送");

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (HttpCenter.webSocket != null) {
                    curTime = (new Date()).getTime();//本地单击的时间

                    if ((curTime - prelongTim) < 2000) {
                        try {
                            Thread.sleep(1000);
                            DealRun(str);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        DealRun(str);
                    }
                }

            }
        }).start();

    }


    //文件图片上传发送接口
    public static void send(File files, Map<String, String> params) {

        MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
        multipartBodyBuilder.setType(MultipartBody.FORM);
        //遍历map中所有参数到builder
        if (params != null) {
            for (String key : params.keySet()) {
                multipartBodyBuilder.addFormDataPart(key, params.get(key));
            }
        }
        multipartBodyBuilder.addFormDataPart("file", files.getName(), RequestBody.create(MEDIA_TYPE_PNG, files));

        //遍历paths中所有图片绝对路径到builder，并约定key如“upload”作为后台接受多张图片的key
        //        if (files != null) {
        //            for (File file : files) {
        //
        //            }
        //        }
        //构建请求体
        RequestBody requestBody = multipartBodyBuilder.build();
        Request.Builder RequestBuilder = new Request.Builder();
        RequestBuilder.url("http://fangxue.56pt.cn/api/Upload/upload");// 添加URL地址
        RequestBuilder.post(requestBody);
        Request request = RequestBuilder.build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
                Log.v("File", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String Data = response.body().string();
                JSONObject obj;
                try {
                    obj = new JSONObject(Data);
                    String cmd = JSONUtils.getString(obj, "cmd");
                    if (messageCallBack != null) {
                        messageCallBack.onMessage(Data);
                    }

                    Log.v("File", Data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    //重连
    private void ResonseReconnect(String s) {
        JSONObject cmd = JSONUtils.StringToJSON(s);


        if (JSONUtils.getString(cmd, "cmd").equals("system.login")) {
            //登录之后发送心跳包
            RunHeart();
            if (JSONUtils.getInt(cmd, "code", -1) == 1) {
                String studid = SharedPrefsUtil.getValue(context, "userXML", "studentid", "");
                if (!studid.isEmpty()) {
                    String Str = commandCenter.selectStudent(Integer.valueOf(studid));
                    send(Str);
                }
            } else if (JSONUtils.getInt(cmd, "code", -1) == -1) {


//                try {
//                    String Phone = SharedPrefsUtil.getValue(context, "loginXML", "UserName", "");
//                    String reConnectStr = commandCenter.login(Phone,
//                            "",
//                            SharedPrefsUtil.getValue(context, "loginXML", "mathinecode", ""),
//                            "P");
//                    HttpCenter.webSocket.sendMessage(RequestBody.create(TEXT, reConnectStr));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }
        } else {
            if (JSONUtils.getString(cmd, "message").equals("账号未登录!")) {
                String Phone = SharedPrefsUtil.getValue(context, "loginXML", "UserName", "");
                String reConnectStr = commandCenter.login(Phone,
                        "",
                        SharedPrefsUtil.getValue(context, "loginXML", "mathinecode", ""),
                        "P");

                send(reConnectStr);
            }

        }
    }

    private void Reconnect() throws IOException {
        //重连登录部分
        if (ifErrorDisConnect == 1) {
            String Phone = SharedPrefsUtil.getValue(context, "loginXML", "UserName", "");
            String reConnectStr = commandCenter.login(Phone,
                    "",
                    SharedPrefsUtil.getValue(context, "loginXML", "mathinecode", ""),
                    "P");

            Log.e("重连发送登录信息", "手机号码:" + Phone + reConnectStr);
            send(reConnectStr);
            HttpCenter.ifErrorDisConnect = 0;
        } else if (Channel == 100) {
            //连接服务部分
            if (serviceMessage != null)
                serviceMessage.onServiceMessage("1000");
            else
                Log.v("messageCenter", serviceMessage + "===");
        }
    }


    //发送心跳包
    private void RunHeart() {
        if (Ding == null) {
            Ding = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(30000);//休眠3秒
                            String reConnectStr = commandCenter.heartbeat();
                            send(reConnectStr);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            Ding.start();
        }
    }

    public void initWebsocket() {
        String url = "ws://180.153.88.58:7017";
        final Request request = new Request.Builder()
                .url(url).build();
        WebSocketCall webSocketCall = WebSocketCall.create(okHttpClient, request);
        webSocketCall.enqueue(new WebSocketListener() {
            @Override
            public void onOpen(WebSocket WebSocket, Response response) {
                HttpCenter.webSocket = WebSocket;
                try {
                    Reconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //检查断开前是否有需要发送的数据
                if (!TempStringMessage.isEmpty()) {
//                    try {
//                        HttpCenter.webSocket.sendMessage(RequestBody.create(TEXT, TempStringMessage));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                    TempStringMessage = "";
                }

            }

            @Override
            public void onFailure(IOException e, Response response) {
                Log.e("onFailure", "websocket 执行onFailure");
                HttpCenter.ifErrorDisConnect = 1;
                initWebsocket();
            }

            @Override
            public void onMessage(ResponseBody message) throws IOException {
                String msg = message.string();
                Log.e("服务端返回", msg);
                JSONObject cmd = JSONUtils.StringToJSON(msg);



                if (serviceMessage != null) {
                    serviceMessage.onServiceMessage(msg);
                }
                if (messageCallBack != null) {
                    messageCallBack.onMessage(msg);
                    ResonseReconnect(msg);
                }

            }

            @Override
            public void onPong(Buffer payload) {

            }

            @Override
            public void onClose(int code, String reason) {
                Log.v("onClose", "websocket onClose");
            }
        });
    }
}
