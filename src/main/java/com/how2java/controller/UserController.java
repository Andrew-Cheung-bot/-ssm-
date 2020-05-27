package com.how2java.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.how2java.pojo.Fan;
import com.how2java.pojo.User;
import com.how2java.service.CurtainService;
import com.how2java.service.UserService;
import com.how2java.service.impl.UserServiceImpl;
import com.how2java.util.MailUtils;
import com.how2java.util.pojo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONArray;
import com.how2java.pojo.Curtain;
import com.how2java.pojo.Data_Curtain;
import com.how2java.service.CurtainService;
import com.alibaba.fastjson.JSONObject;

import com.how2java.websocket.MyWebSocketHandler;
import org.springframework.web.socket.TextMessage;
import sun.applet.resources.MsgAppletViewer;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/User")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 注册用户
     * @param session
     * @param user_get
     * @param check
     * @return
     */
    @RequestMapping(value = "/registUser",method = RequestMethod.POST)
    @ResponseBody
    public String addUser(HttpSession session,@RequestBody User user_get,@RequestParam(value = "check") String check){

        //验证校验
        //从sesion中获取验证码
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        System.out.println(checkcode_server);
        System.out.println(check);
        //session.removeAttribute("CHECKCODE_SERVER");//为了保证验证码只能使用一次
        //比较
        if(checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)){
            //验证码错误
            ResultInfo info = new ResultInfo();
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("验证码错误,请重新输入验证码");
            //将info对象序列化为json
            String json = JSON.toJSONString(info);
            return json;
        }
        User user = new User();
        user.setUsername(user_get.getUsername());
        user.setPwd(user_get.getPwd());
        user.setEmail(user_get.getEmail());

        boolean flag = userService.regist(user);
        ResultInfo info = new ResultInfo();
        //响应结果
        if(flag){
            //注册成功
            info.setFlag(true);
        }else {
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("用户名已存在,注册失败,请重新注册");
        }
        //将info对象序列化为json
        String json = JSON.toJSONString(info);
        return json;
    }

    /**
     * 激活
     * @param code
     * @return
     */
    @RequestMapping(value = "/activeUser",produces = "application/json;charset=utf-8",method = RequestMethod.GET)
    @ResponseBody
    public String  activeUser( String code){
        String msg=null;
        if(code!=null){
            boolean flag = userService.active(code);
            if(flag){
                //激活成功
              msg = "激活成功，请点击链接进行登录:http://129.204.232.202:8080/ssm/login.html";
            }else {
                //激活失败
                msg = "激活失败，请联系管理员!";
            }
        }else {
            msg="激活失败，请联系管理员!";
        }
        return msg;
    }

    /**
     * 登录
     */
    @RequestMapping(value = "/loginUser",method = RequestMethod.POST)
    @ResponseBody
    public String loginUser(@RequestBody User user_get){
        User user = new User();
        user.setUsername(user_get.getUsername());
        user.setPwd(user_get.getPwd());
        User u = userService.login(user);
        ResultInfo info = new ResultInfo();
        //4.判断用户对象是否为null
        if(u == null){
            //用户名密码或错误
            info.setFlag(false);
            info.setErrorMsg("用户名密码或错误,请重新输入");
        }
        //5.判断用户是否激活
        if(u != null && !"Y".equals(u.getActivate())){
            //用户尚未激活
            info.setFlag(false);
            info.setErrorMsg("您尚未激活，请激活");
        }
        //6.判断登录成功
        if(u != null && "Y".equals(u.getActivate())){
            //登录成功
            info.setFlag(true);
        }
        //将info对象序列化为json
        String json = JSON.toJSONString(info);
        return json;
    }

}
