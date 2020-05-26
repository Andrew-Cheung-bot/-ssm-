package com.how2java.websocket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * websocket握手拦截器
 * 拦截握手前，握手后的两个切面
 */
@Component
public class MyHandShakeInterceptor implements HandshakeInterceptor {

    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        int uid = Integer.valueOf(((ServletServerHttpRequest) serverHttpRequest).getServletRequest().getParameter("uid"));
        ((ServletServerHttpRequest) serverHttpRequest).getServletRequest().getSession(true).setAttribute("user",uid);
        System.out.println("Websocket:用户[ID:" + ((ServletServerHttpRequest) serverHttpRequest).getServletRequest().getSession(false).getAttribute("user") + "]已经建立连接");
        if (serverHttpRequest instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) serverHttpRequest;
            HttpSession session = servletRequest.getServletRequest().getSession(false);
            // 标记用户
            //Data_Curtain user = new Data_Curtain();
            //user.setUserId((Integer) session.getAttribute("user"));
            map.put("uid", uid);//为服务器创建WebSocketSession做准备
            System.out.println("用户id："+uid+" 被加入");
        }
        return true;
    }

    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
    }
}

