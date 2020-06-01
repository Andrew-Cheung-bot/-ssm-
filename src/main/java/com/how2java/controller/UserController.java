package com.how2java.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.how2java.pojo.*;
import com.how2java.service.CurtainService;
import com.how2java.service.UserService;
import com.how2java.service.impl.UserServiceImpl;
import com.how2java.util.MailUtils;
import com.how2java.util.pojo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONArray;
import com.how2java.service.CurtainService;
import com.alibaba.fastjson.JSONObject;

import com.how2java.websocket.MyWebSocketHandler;
import org.springframework.web.servlet.ModelAndView;
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
            info.setErrorMsg("Check Code ERROR!");
            //将info对象序列化为json
            String json = JSON.toJSONString(info);
            return json;
        }
        User user = new User();
        user.setUsername(user_get.getUsername());
        user.setPassword(user_get.getPassword());
        user.setEmail(user_get.getEmail());
        user.setRolename("USER");
        boolean flag = userService.regist(user);
        ResultInfo info = new ResultInfo();
        //响应结果
        if(flag){
            //注册成功
            info.setFlag(true);
        }else {
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("username exist! Please change your username");
        }
        //将info对象序列化为json
        String json = JSON.toJSONString(info);
        return json;
    }


    /**
     * 注册用户
     * @param session
     * @return
     */
    /*
    @RequestMapping(value = "/updateUser",method = RequestMethod.POST)
    @ResponseBody
    public String updateUser(HttpSession session,@RequestBody String param ){

        JSONObject jo=new JSONObject();
        JSONObject parseObject = jo.parseObject(param); //string转json类型
        System.out.println(parseObject);

        User user = new User();
        user.setUsername(parseObject.getString("username"));
        user.setPassword(parseObject.getString("changepassword"));

        boolean flag = userService.update(user);
        ResultInfo info = new ResultInfo();
        //响应结果
        if(flag){
            //注册成功
            info.setFlag(true);
        }else {
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("chang password failure!");
        }
        //将info对象序列化为json
        String json = JSON.toJSONString(info);
        return json;
    }
    */





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
    //@RequestMapping(value = "/loginUser",method = RequestMethod.POST)
    //@ResponseBody
    /*
    public String loginUser(@RequestBody User user_get){
        User user = new User();
        user.setUsername(user_get.getUsername());
        user.setPassword(user_get.getPassword());
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
    */

    /**
     * 登录
     */
    //@RequestMapping(value = "/loginUser",method = RequestMethod.POST)
    //@ResponseBody
    public ModelAndView loginUser(){
        ModelAndView mv = new ModelAndView("redirect:/login.html");
        return mv;
    }
}
