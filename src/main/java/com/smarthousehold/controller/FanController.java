package com.smarthousehold.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.smarthousehold.pojo.Curtain;
import com.smarthousehold.pojo.User;
import com.smarthousehold.service.FanService;
import com.smarthousehold.util.pojo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONArray;
import com.smarthousehold.pojo.Fan;
import com.smarthousehold.pojo.Data_Fan;
import com.alibaba.fastjson.JSONObject;

import com.smarthousehold.websocket.MyWebSocketHandler;
import org.springframework.web.socket.TextMessage;

// 告诉spring mvc这是一个控制器类
@Controller
@RequestMapping(value = "/Fan")
public class FanController {
    @Autowired
    FanService fanService;

    //获取前台发送的数据请求以及返回
    //数据格式：
    //"settime":"05-%"获取一整个5月数据
    //"settime":"05-01,%"获取5月1号数据
    //"settime":"05-01,19:%"获取5月1号晚上7点数据
    //"settime":"05-01,19:23"获取5月1号晚上7点23分数据
    //"fid":风扇的id
    //需要传入settime，fid参数
    @RequestMapping(value = "/getData", method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getFanData(@RequestBody String param) {

        JSONObject jo = new JSONObject();

        JSONObject parseObject = jo.parseObject(param); //string转json类型
        System.out.println("这是json类型" + parseObject);

        Map map = new HashMap();
        map.put("settime",parseObject.getString("settime"));
        map.put("fid",parseObject.getString("fid"));
        List<Data_Fan> datalist = fanService.getFanData(map);
        String string_data_list = JSONArray.parseArray(JSON.toJSONString(datalist)).toString();

        return string_data_list;

    }

    //获取前台发送的设置fan数据
    //在前台发送setFan的时候，服务器会自动发送数据到硬件updateFan
    //需要传入Stem，Shumidity，fid参数
    @RequestMapping(value = "/setFan", method = RequestMethod.POST)
    @ResponseBody
    public String setFan(@RequestBody String param) throws IOException {

        JSONObject jo = new JSONObject();

        JSONObject parseObject = jo.parseObject(param); //string转json类型
        System.out.println("这是json类型" + parseObject);

        Fan fan = new Fan();

        fan.setStem(parseObject.getString("Stem"));
        fan.setShumidity(parseObject.getString("Shumidity"));
        fan.setFid(parseObject.getString("fid"));
        fanService.updateFan(fan);

        //在前台发送setCurtain的时候，服务器会自动发送数据到硬件
        Fan fan_information = fanService.getFan(parseObject.getString("fid"));
        //2表示此为修改
        fan_information.setAction("3");
        String string_fan = JSON.parse(JSON.toJSONString(fan_information)).toString();
        MyWebSocketHandler send = new MyWebSocketHandler();
        send.sendMessageToUser(123456, new TextMessage(string_fan));

        return "setting is successfully!";

    }

    //获取前台发送的设置fan数据
    //在前台发送setState的时候，服务器会自动发送数据到硬件updateFanState
    //需要传入fid,state参数
    @RequestMapping(value = "/setState",method = RequestMethod.POST)
    @ResponseBody
    public String setState(@RequestBody String param) throws IOException {

        JSONObject jo=new JSONObject();

        JSONObject parseObject = jo.parseObject(param); //string转json类型
        System.out.println("这是json类型"+parseObject);

        Fan fan = new Fan();

        fan.setState(parseObject.getString("state"));
        fan.setFid(parseObject.getString("fid"));
        fanService.updateFanState(fan);

        //在前台发送setCurtainState的时候，服务器会自动发送数据到硬件
        Fan fan_information = fanService.getFan(parseObject.getString("fid"));
        //2表示此为修改
        fan_information.setAction("2");
        String string_fan = JSON.parse(JSON.toJSONString(fan_information)).toString();
        MyWebSocketHandler send = new MyWebSocketHandler();
        send.sendMessageToUser(123456, new TextMessage(string_fan));

        return "setting is successfully!";

    }

    //获取fan设置数据接口
    //需要传入fid参数
    @RequestMapping(value = "/getFan", method = RequestMethod.POST,produces ="application/json;charset=utf-8" )
    @ResponseBody
    public String getFan(@RequestBody String param) {

        JSONObject jo = new JSONObject();

        JSONObject parseObject = jo.parseObject(param); //string转json类型
        System.out.println("这是json类型" + parseObject);

        Fan curtain = fanService.getFan(parseObject.getString("fid"));
        String string_fan = JSON.parse(JSON.toJSONString(curtain)).toString();
        String string = JSON.toJSONString(curtain);

        return string;

    }

    //获取curtain设置数据接口
    //无需参数
    @RequestMapping(value="/getAllFan",method=RequestMethod.GET,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getAllFan() {

        List<Fan> allfan = fanService.getAllFan();

        String string_fan = JSONArray.parseArray(JSON.toJSONString(allfan)).toString();
        System.out.println(JSON.toJSONString(string_fan));

        return string_fan;

    }

    //增加fan接口
    //需要传入Stem，Shumidity，fid参数
    @RequestMapping(value = "/addFan", method = RequestMethod.POST)
    @ResponseBody
    public String addFan(@RequestBody String param) throws IOException {

        JSONObject jo = new JSONObject();

        JSONObject parseObject = jo.parseObject(param); //string转json类型
        System.out.println("这是json类型" + parseObject);
        Fan findFan = fanService.getFan(parseObject.getString("fid"));
        ResultInfo info = new ResultInfo();
        //响应结果
        if(findFan==null){
            Fan fan = new Fan();
            fan.setStem(parseObject.getString("Stem"));
            fan.setShumidity(parseObject.getString("Shumidity"));
            fan.setState("0");
            fan.setFid(parseObject.getString("fid"));
            fanService.addFan(fan);
            //在前台发送setCurtain的时候，服务器会自动发送数据到硬件
            Fan fan_information = fanService.getFan(parseObject.getString("fid"));
            //1表示此为增加
            fan_information.setAction("1");
            String string_fan = JSON.parse(JSON.toJSONString(fan_information)).toString();
            MyWebSocketHandler send = new MyWebSocketHandler();
            send.sendMessageToUser(123456, new TextMessage(string_fan));
            //添加成功
            info.setFlag(true);
        }else {
            //添加失败
            info.setFlag(false);
            info.setErrorMsg("fanId exist! Please change your fanId");
        }
        //将info对象序列化为json
        String json = JSON.toJSONString(info);
        return json;
    }

    //删除fan接口
    //需要传入fid参数
    @RequestMapping(value = "/deleteFan", method = RequestMethod.POST)
    @ResponseBody
    public String deleteFan(@RequestBody String param) throws IOException {
        JSONObject jo = new JSONObject();

        JSONObject parseObject = jo.parseObject(param); //string转json类型
        System.out.println("这是json类型" + parseObject);


        String fid = parseObject.getString("fid");
        if(!fid.equals(null)){
            //在前台发送setCurtain的时候，服务器会自动发送数据到硬件
            Fan fan_information = fanService.getFan(parseObject.getString("fid"));
            //-1表示此为删除
            fan_information.setAction("4");
            String string_fan = JSON.parse(JSON.toJSONString(fan_information)).toString();
            MyWebSocketHandler send = new MyWebSocketHandler();
            send.sendMessageToUser(123456, new TextMessage(string_fan));
            fanService.deleteFan(fid);
            return "delete fid="+fid+" fan seccessful";
        }else {
            return "delete failure because FID do not exist or FID is 0";
        }
    }

    /**
     * 分页查询全部设备
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/findAll",produces = "application/json;charset=utf-8",method = RequestMethod.POST)
    @ResponseBody
    public String findAll(@RequestParam(name = "page",required = true,defaultValue = "1")Integer page,
                          @RequestParam(name = "size",required = true,defaultValue = "10")Integer size){
        List<Fan> funList = fanService.findAll(page, size);
        PageInfo pageInfo = new PageInfo(funList);
        String string = JSON.toJSONString(pageInfo);
        return string;
    }

    /**
     * 查找设备对应的数据
     * @param page
     * @param size
     * @param fid
     * @return
     */
    @RequestMapping(value = "/findDetailByFid",produces = "application/json;charset=utf-8",method = RequestMethod.POST)
    @ResponseBody
    public String findDetailByFid(@RequestParam(name = "page",required = true,defaultValue = "1")Integer page,
                                  @RequestParam(name = "size",required = true,defaultValue = "10")Integer size,@RequestParam("fid") String fid){
        List<Data_Fan> funDetail=fanService.findDetailByFid(page, size,fid);
        PageInfo pageInfo = new PageInfo(funDetail);
        String string = JSON.toJSONString(pageInfo);
        return string;
    }

    /**
     * 查找该用户未绑定的设备
     * @param username
     * @return
     */
    @RequestMapping(value = "/findFanByUsername",produces = "application/json;charset=utf-8",method = RequestMethod.POST)
    @ResponseBody
    public String  findFanByUsername(@RequestBody String username){
        JSONObject  jo= new JSONObject();
        JSONObject jsonObject = jo.parseObject(username);
        String user = jsonObject.getString("username");
        List<Fan> curtainList=fanService.findOtherFan(user);
        String json = JSON.toJSONString(curtainList);
        return json;
    }
}