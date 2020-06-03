package com.smarthousehold.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.smarthousehold.service.CurtainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.smarthousehold.pojo.Curtain;
import com.smarthousehold.pojo.Data_Curtain;
import com.alibaba.fastjson.JSONObject;

import com.smarthousehold.websocket.MyWebSocketHandler;
import org.springframework.web.socket.TextMessage;

// 告诉spring mvc这是一个控制器类
@Controller
@RequestMapping(value = "/Curtain")
public class CurtainController {
    @Autowired
    CurtainService curtainService;

    /*
        //获取硬件发送每小时发送过来的数据
        @RequestMapping(value="/submitInfo",method=RequestMethod.POST)
        @ResponseBody
        public String paramget(@RequestBody String param){

            JSONObject jo=new JSONObject();

            //传过来的通过requestbody转为string类型，
            //通过jsonobject包转为map<String,BigDecimal>类型数据以及json数据方便后续调用
            //Map<String, BigDecimal> mapObject = (Map<String, BigDecimal>) jo.parse(param); //string转map类型
            //System.out.println("这是map类型"+mapObject);
            JSONObject parseObject = jo.parseObject(param); //string转json类型
            System.out.println("这是json类型"+parseObject);

            Data_Curtain data = new Data_Curtain();

            data.setHumidity(parseObject.getBigDecimal("humidity").floatValue());
            data.setTem(parseObject.getBigDecimal("tem").floatValue());
            curtainService.addData(data);

            return "Data upload is successfully!";

        }
    */
    //获取前台发送的数据请求以及返回
    //数据格式：
    //"settime":"05-%"获取一整个5月数据
    //"settime":"05-01,%"获取5月1号数据
    //"settime":"05-01,19:%"获取5月1号晚上7点数据
    //"settime":"05-01,19:23"获取5月1号晚上7点23分数据
    //"cid":窗帘的id
    //需要传入settime，cid参数
    @RequestMapping(value="/getData",method=RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getCurtainData(@RequestBody String param){

        JSONObject jo=new JSONObject();

        JSONObject parseObject = jo.parseObject(param); //string转json类型
        System.out.println("这是json类型"+parseObject);

        Map map = new HashMap();
        map.put("settime",parseObject.getString("settime"));
        map.put("cid",parseObject.getString("cid"));
        List<Data_Curtain> datalist = curtainService.getCurtainData(map);
        String string_data_list = JSONArray.parseArray(JSON.toJSONString(datalist)).toString();

        return string_data_list;

    }

    //获取前台发送的设置curtain数据
    //在前台发送setCurtain的时候，服务器会自动发送数据到硬件updateCurtain
    //需要传入Stem，Shumidity，cid参数
    @RequestMapping(value="/setCurtain",method=RequestMethod.POST)
    @ResponseBody
    public String setCurtain(@RequestBody String param) throws IOException {

        JSONObject jo=new JSONObject();

        JSONObject parseObject = jo.parseObject(param); //string转json类型
        System.out.println("这是json类型"+parseObject);

        Curtain curtain = new Curtain();

        curtain.setStem(parseObject.getString("Stem"));
        curtain.setShumidity(parseObject.getString("Shumidity"));
        curtain.setCid(parseObject.getString("cid"));
        curtainService.updateCurtain(curtain);

        //在前台发送setCurtain的时候，服务器会自动发送数据到硬件
        Curtain curtain_information = curtainService.getCurtain(parseObject.getString("cid"));
        //2表示此为修改
        curtain_information.setAction("3");
        String string_curtain = JSON.parse(JSON.toJSONString(curtain_information)).toString();
        MyWebSocketHandler send = new MyWebSocketHandler();
        send.sendMessageToUser(123456, new TextMessage(string_curtain));

        return "setting is successfully!";

    }

    //获取前台发送的设置curtain数据
    //在前台发送setState的时候，服务器会自动发送数据到硬件updateCurtainState
    //需要传入cid,state参数
    @RequestMapping(value = "/setState",method = RequestMethod.POST)
    @ResponseBody
    public String setState(@RequestBody String param) throws IOException {

        JSONObject jo=new JSONObject();

        JSONObject parseObject = jo.parseObject(param); //string转json类型
        System.out.println("这是json类型"+parseObject);

        Curtain curtain = new Curtain();

        curtain.setState(parseObject.getString("state"));
        curtain.setCid(parseObject.getString("cid"));
        curtainService.updateCurtainState(curtain);

        //在前台发送setCurtainState的时候，服务器会自动发送数据到硬件
        Curtain curtain_information = curtainService.getCurtain(parseObject.getString("cid"));
        //2表示此为修改
        curtain_information.setAction("2");
        String string_curtain = JSON.parse(JSON.toJSONString(curtain_information)).toString();
        MyWebSocketHandler send = new MyWebSocketHandler();
        send.sendMessageToUser(123456, new TextMessage(string_curtain));

        return "setting is successfully!";

    }

    //获取curtain设置数据接口
    //需要传入cid参数
    @RequestMapping(value="/getCurtain",method=RequestMethod.POST)
    @ResponseBody
    public String getCurtain(@RequestBody String param) {

        JSONObject jo=new JSONObject();

        JSONObject parseObject = jo.parseObject(param); //string转json类型
        System.out.println("这是json类型"+parseObject);

        Curtain curtain = curtainService.getCurtain(parseObject.getString("cid"));
        String string_curtain = JSON.parse(JSON.toJSONString(curtain)).toString();
        System.out.println(JSON.toJSONString(curtain));

        return string_curtain;

    }

    //获取curtain设置数据接口
    //无需参数
    @RequestMapping(value="/getAllCurtain",method=RequestMethod.GET,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getAllCurtain(Principal principal) {

        List<Curtain> allcurtain = curtainService.getAllCurtain();

        String string_curtain = JSONArray.parseArray(JSON.toJSONString(allcurtain)).toString();
        System.out.println(JSON.toJSONString(string_curtain));

        return string_curtain;

    }

    //增加curtain接口
    //需要传入Stem，Shumidity，cid参数
    @RequestMapping(value = "/addCurtain",method = RequestMethod.POST)
    @ResponseBody
    public String addCurtain(@RequestBody String param) throws IOException {

        JSONObject jo=new JSONObject();

        JSONObject parseObject = jo.parseObject(param); //string转json类型
        System.out.println("这是json类型"+parseObject);

        Curtain curtain = new Curtain();

        curtain.setStem(parseObject.getString("Stem"));
        curtain.setShumidity(parseObject.getString("Shumidity"));
        curtain.setState("0");
        curtain.setCid(parseObject.getString("cid"));
        curtainService.addCurtain(curtain);

        //在前台发送setCurtain的时候，服务器会自动发送数据到硬件
        Curtain curtain_information = curtainService.getCurtain(parseObject.getString("cid"));
        //1表示此为增加
        curtain_information.setAction("1");
        String string_curtain = JSON.parse(JSON.toJSONString(curtain_information)).toString();
        MyWebSocketHandler send = new MyWebSocketHandler();
        send.sendMessageToUser(123456, new TextMessage(string_curtain));

        return "adding curtain successful";
    }

    //删除curtain接口
    //需要传入cid参数
    @RequestMapping(value = "/deleteCurtain",method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteCurtain(@RequestBody String param) throws IOException {
        JSONObject jo=new JSONObject();

        JSONObject parseObject = jo.parseObject(param); //string转json类型
        System.out.println("这是json类型"+parseObject);


        String cid = parseObject.getString("cid");
        if(!cid.equals(null)){
            //在前台发送setCurtain的时候，服务器会自动发送数据到硬件
            Curtain curtain_information = curtainService.getCurtain(parseObject.getString("cid"));
            //-1表示此为删除
            curtain_information.setAction("4");
            String string_curtain = JSON.parse(JSON.toJSONString(curtain_information)).toString();
            MyWebSocketHandler send = new MyWebSocketHandler();
            send.sendMessageToUser(123456, new TextMessage(string_curtain));
            curtainService.deleteCurtain(cid);
            return "delete cid="+cid+" curtain seccessful";
        }else {
            return "delete failure because CID do not exist or CID is 0";
        }
    }

}
