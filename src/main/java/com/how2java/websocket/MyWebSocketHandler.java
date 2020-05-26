package com.how2java.websocket;

import com.google.gson.Gson;

import com.how2java.pojo.Data_Curtain;
import com.how2java.pojo.Data_Fan;
import com.how2java.service.FanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import com.how2java.service.CurtainService;

@Component
public class MyWebSocketHandler implements WebSocketHandler{

    @Autowired
    private CurtainService curtainservice;
    @Autowired
    private FanService fanservice;

    //当MyWebSocketHandler类被加载时就会创建该Map，随类而生
    public static final Map<Integer, WebSocketSession> userSocketSessionMap;

    static {
        userSocketSessionMap = new HashMap<Integer, WebSocketSession>();
    }

    //握手实现连接后
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        int uid = (Integer) webSocketSession.getAttributes().get("uid");
        //sendMessageToUser(uid, new TextMessage("test"));
        //if (userSocketSessionMap.get(uid) == null) {
        userSocketSessionMap.put(uid, webSocketSession);
        //}
    }

    //发送信息前的处理
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        if((Integer)webSocketSession.getAttributes().get("uid") == 123456) {
            if (webSocketMessage.getPayloadLength() == 0) return;

            System.out.println(webSocketMessage.getPayload().toString());

            //得到Socket通道中的数据并转化为Data对象
            //要有tem，humidity，cid(fid)三个参数
            Data_Curtain curtain_data = new Gson().fromJson(webSocketMessage.getPayload().toString(), Data_Curtain.class);
            if(curtain_data.getCid()!=0){
                //将信息保存至数据库
                try {
                    curtainservice.addCurtainData(curtain_data);
                    sendMessageToUser((Integer) webSocketSession.getAttributes().get("uid"), new TextMessage("Curtain_data upload completed!"));
                } catch (Exception arg0) {
                    sendMessageToUser((Integer) webSocketSession.getAttributes().get("uid"), new TextMessage(
                            "have same KEY_PRIMARY(settime&id) in MySQL or CID do not exist!"));
                }
            }else{
                Data_Fan fan_data = new Gson().fromJson(webSocketMessage.getPayload().toString(), Data_Fan.class);
                //将信息保存至数据库
                try {
                    fanservice.addFanData(fan_data);
                    sendMessageToUser((Integer) webSocketSession.getAttributes().get("uid"), new TextMessage("Fan_data upload completed!"));
                } catch (Exception arg1) {
                    sendMessageToUser((Integer) webSocketSession.getAttributes().get("uid"), new TextMessage(
                            "have same KEY_PRIMARY(settime&id) in MySQL or FID do not exist!"));
                }
            }

        }
    }

    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {

    }

    /**
     * 在此刷新页面就相当于断开WebSocket连接,原本在静态变量userSocketSessionMap中的
     * WebSocketSession会变成关闭状态(close)，但是刷新后的第二次连接服务器创建的
     * 新WebSocketSession(open状态)又不会加入到userSocketSessionMap中,所以这样就无法发送消息
     * 因此应当在关闭连接这个切面增加去除userSocketSessionMap中当前处于close状态的WebSocketSession，
     * 让新创建的WebSocketSession(open状态)可以加入到userSocketSessionMap中
     * @param webSocketSession
     * @param closeStatus
     * @throws Exception
     */
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {

        System.out.println("WebSocket:"+webSocketSession.getAttributes().get("uid")+"close connection");
        Iterator<Map.Entry<Integer,WebSocketSession>> iterator = userSocketSessionMap.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<Integer,WebSocketSession> entry = iterator.next();
            if(entry.getValue().getAttributes().get("uid")==webSocketSession.getAttributes().get("uid")){
                userSocketSessionMap.remove(webSocketSession.getAttributes().get("uid"));
                System.out.println("WebSocket in staticMap:" + webSocketSession.getAttributes().get("uid") + "removed");
            }
        }
    }

    public boolean supportsPartialMessages() {
        return false;
    }

    //发送信息的实现
    //uid为硬件本身id
    public void sendMessageToUser(int uid, TextMessage message)
            throws IOException {
        WebSocketSession session = userSocketSessionMap.get(uid);
        if (session != null && session.isOpen()) {
            session.sendMessage(message);
        }
    }
}
