package com.smarthousehold.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.smarthousehold.pojo.*;
import com.smarthousehold.service.UserService;
import com.smarthousehold.util.pojo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

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

        //使用bcrypt加密
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(user_get.getPassword());
        //放入经过加密的密码
        user.setPassword(password);
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
     * @param user_get
     * @return
     */
    @RequestMapping(value = "/registUserformobile",method = RequestMethod.POST)
    @ResponseBody
    public String addUserformobile(@RequestBody User user_get){

        User user = new User();
        user.setUsername(user_get.getUsername());

        //使用bcrypt加密
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(user_get.getPassword());
        //放入经过加密的密码
        user.setPassword(password);
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
     * 修改用户密码
     * @param session
     * @return
     */
    @RequestMapping(value = "/updateUser",method = RequestMethod.POST)
    @ResponseBody
    public String updateUser(HttpSession session,@RequestBody String param ){

        JSONObject jo=new JSONObject();
        JSONObject parseObject = jo.parseObject(param); //string转json类型

        User user = new User();
        user.setUsername(parseObject.getString("username"));
        user.setPassword(parseObject.getString("changepassword"));

        UserDetails userDetails = userService.loadUserByUsername(parseObject.getString("username"));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        ResultInfo info = new ResultInfo();
        if(passwordEncoder.matches(parseObject.getString("password"),userDetails.getPassword())){
            boolean flag = userService.update(user);
            //响应结果
            if(flag){
                //注册成功
                info.setFlag(true);
            }else {
                //注册失败
                info.setFlag(false);
                info.setErrorMsg("chang password failure!");
            }
        }else{
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("old password error!");
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
     * 查询所有用户
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/findAll",produces = "application/json;charset=utf-8",method = RequestMethod.POST)
    @ResponseBody
    public String findAll(@RequestParam(name = "page",required = true,defaultValue = "1")Integer page,
                          @RequestParam(name = "size",required = true,defaultValue = "10")Integer size){
        List<User> userList = userService.findAll(page, size);
        PageInfo pageInfo = new PageInfo(userList);
        String string = JSON.toJSONString(pageInfo);
        return string;
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    @ResponseBody
    public String addUser(@RequestBody User user){
        boolean flag = userService.addUser(user);
        ResultInfo info = new ResultInfo();
        //响应结果
        if(flag){
            //添加成功
            info.setFlag(true);
        }else {
            //添加失败
            info.setFlag(false);
            info.setErrorMsg("username exist! Please change your username");
        }
        //将info对象序列化为json
        String json = JSON.toJSONString(info);
        return json;
    }

    /**
     * 根据名称删除用户
     * @param username
     * @return
     */
    @RequestMapping(value = "/delUserByUsername",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String delUserByUsername(@RequestParam("username") String username){
        boolean flag = userService.delUserByUsername(username);
        ResultInfo info = new ResultInfo();
        if(flag){
            //添加成功
            info.setFlag(true);
        }else {
            //添加失败
            info.setFlag(false);
            info.setErrorMsg("  delete user fail! Please check again");
        }
        //将info对象序列化为json
        String json = JSON.toJSONString(info);
        return json;
    }

    @RequestMapping(value = "/reback",method = RequestMethod.GET)
    @ResponseBody
    public String reback(){
        return null;
    }

    /**
     * 根据用户名称查找用户信息
     * @param user
     * @return
     */
    @RequestMapping(value = "/findUserByUsername",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String findUserByUsername(@RequestBody User user){
        User findUser = userService.findByUsername(user.getUsername());
        String string = JSON.toJSONString(findUser);
        return string;
    }

    /**
     * 根据用户名修改用户信息
     * @param user
     * @return
     */
    @RequestMapping(value = "/editUserByUsername",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String editUserByUsername(@RequestBody User user){
        boolean flag = userService.editUserByUsername(user);
        ResultInfo info = new ResultInfo();
        if(flag){
            //添加成功
            info.setFlag(true);
        }else {
            //添加失败
            info.setFlag(false);
            info.setErrorMsg(" edit user fail! Please check again");
        }
        //将info对象序列化为json
        String json = JSON.toJSONString(info);
        return json;
    }

    /**
     * 模糊查询用户名
     * @param key
     * @return
     */
    @RequestMapping(value = "/searchAutoPrompt",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String searchAutoPrompt(String key){
        List list = userService.searchAutoPrompt(key);
        ResultInfo info = new ResultInfo();
        info.setData(list);
        String string = JSON.toJSONString(info);
        return string;
    }

    /**
     * 根据模糊查询用户名查询用户信息
     * @param key
     * @return
     */
    @RequestMapping(value = "/searchByUsername",method = RequestMethod.POST,produces ="application/json;charset=utf-8" )
    @ResponseBody
    public String searchByUsername(@RequestBody String key){
        JSONObject json = new JSONObject();
        JSONObject jsonObject = json.parseObject(key);
        String string = jsonObject.getString("key");
        List<User> userList = userService.searchByUsername(string);
        String users = JSON.toJSONString(userList);
        return users;
    }

    /**
     * 添加用户设备
     * @param params
     * @return
     */
    @RequestMapping(value = "/addUserCurtain",method = RequestMethod.POST,produces ="application/json;charset=utf-8" )
    @ResponseBody
    public String addUserCurtain(@RequestBody String params){
        JSONObject json = new JSONObject();
        JSONObject jsonObject = json.parseObject(params);
        String username = jsonObject.getString("username");
        String ids = jsonObject.getString("ids");
        String[] id=ids.split(",");
        if (id.length == 1) {
            id[0]=id[0].replace("[","").replace("]","");
            id[0]=id[0].replace("\"","").replace("\"","");
        }
        for(int i=0;i<id.length;i++){
            if(i==0){
                id[i]=id[i].replace("[","");
            }else if(i==id.length-1){
                id[i]=id[i].replace("]","");
            }
            id[i]=id[i].replace("\"","").replace("\"","");
            //System.out.println(id[i]);
        }
        ResultInfo info = new ResultInfo();
        Boolean flag=userService.addUserCurtain(username,id);
        if(flag){
            //添加成功
            info.setFlag(true);
        }else {
            //添加失败
            info.setFlag(false);
            info.setErrorMsg(" addUserCurtain user fail! Please check again");
        }
        String string = JSON.toJSONString(info);
        return string;
    }

    /**
     * 添加用户设备
     * @param params
     * @return
     */
    @RequestMapping(value = "/addUserFan",method = RequestMethod.POST,produces ="application/json;charset=utf-8" )
    @ResponseBody
    public String addUserFan(@RequestBody String params){
        JSONObject json = new JSONObject();
        JSONObject jsonObject = json.parseObject(params);
        String username = jsonObject.getString("username");
        String ids = jsonObject.getString("ids");
        String[] id=ids.split(",");
        for(int i=0;i<id.length;i++){
            if(i==0){
                id[i]=id[i].replace("[","");
            }else if(i==id.length-1){
                id[i]=id[i].replace("]","");
            }
            id[i]=id[i].replace("\"","").replace("\"","");
            //System.out.println(id[i]);
        }
        ResultInfo info = new ResultInfo();
        Boolean flag=userService.addUserFan(username,id);
        if(flag){
            //添加成功
            info.setFlag(true);
        }else {
            //添加失败
            info.setFlag(false);
            info.setErrorMsg(" addUserCurtain user fail! Please check again");
        }
        String string = JSON.toJSONString(info);
        return string;
    }

    /**
     * 查找用户所光联的设备信息
     * @param param
     * @return
     */
    @RequestMapping(value = "/findDetail",method = RequestMethod.POST,produces ="application/json;charset=utf-8")
    @ResponseBody
    public String findDetail(@RequestBody String param){
        JSONObject jo = new JSONObject();
        JSONObject jsonObject = jo.parseObject(param);
        String username = jsonObject.getString("username");
        UserInfo userInfo=userService.findDetail(username);
        String json = JSON.toJSONString(userInfo);
        return json;
    }
}
