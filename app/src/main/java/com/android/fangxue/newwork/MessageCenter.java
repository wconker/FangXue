package com.android.fangxue.newwork;

import android.content.Context;
import android.util.Log;

import com.android.fangxue.callback.MessageCallBack;
import com.android.fangxue.callback.ServiceMessage;

import okhttp3.ws.WebSocket;

/**
 * Created by fangxue on 17/9/1.
 */

public class MessageCenter {


    private WebSocket socket;
    private CommandCenter commandCenter;
    private HttpCenter httpCenter;


    public MessageCenter(MessageCallBack messageCenter) {

        httpCenter = new HttpCenter();
        HttpCenter.messageCallBack = messageCenter;
        httpCenter.setCallBack(messageCenter);
        commandCenter = new CommandCenter();
        setSocket("");
    }

    public MessageCenter() {
        commandCenter = new CommandCenter();
        httpCenter = new HttpCenter();
        setSocket("");
    }


    public CommandCenter ChooseCommand() {
        if (commandCenter != null)
            return commandCenter;
        else {
            commandCenter = new CommandCenter();
            return commandCenter;
        }
    }

    public void setLoginChannel() {
        httpCenter.Channel = 100;
    }

    public void setSocket(String str) {
        if (HttpCenter.webSocket != null) {
            if (!str.isEmpty()) {
                socket = HttpCenter.webSocket;
                httpCenter.send(str);
            }
        } else {

            httpCenter.initWebsocket();

        }
    }

    public void SendYouMessage(String str) {
        setSocket(str);
    }

    public void SendYouMessage(String str, MessageCallBack messageCallBack) {
        setCallBackInterFace(messageCallBack);
        setSocket(str);
    }

    public void setCallBackInterFace(MessageCallBack messageCenter) {
        setHttpCallBack(messageCenter);
    }

    private void setHttpCallBack(MessageCallBack messageCenter) {
        HttpCenter.messageCallBack = messageCenter;
        httpCenter.setCallBack(HttpCenter.messageCallBack);
    }

    public void setServiceMessage(ServiceMessage serviceMessage) {
        httpCenter.setServiceMessage(serviceMessage);

    }
}
