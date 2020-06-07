package com.smarthousehold.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.smarthousehold.pojo.Data_Fan;
import com.smarthousehold.pojo.Fan;
import com.smarthousehold.service.CurtainService;
import com.smarthousehold.util.pojo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    @RequestMapping(value="/getCurtain",method=RequestMethod.POST,produces ="application/json;charset=utf-8")
    @ResponseBody
    public String getCurtain(@RequestBody String param) {

        JSONObject jo=new JSONObject();

        JSONObject parseObject = jo.parseObject(param); //string转json类型
        System.out.println("这是json类型"+parseObject);

        Curtain curtain = curtainService.getCurtain(parseObject.getString("cid"));
        String string_curtain = JSON.parse(JSON.toJSONString(curtain)).toString();
        String string = JSON.toJSONString(curtain);

        return string;

    }

    //获取curtain设置数据接口
    //无需参数
    @RequestMapping(value="/getAllCurtain",method=RequestMethod.GET,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getAllCurtain() {

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
        Curtain findCurtain = curtainService.getCurtain(parseObject.getString("cid"));
        ResultInfo info = new ResultInfo();
        //响应结果
        if(findCurtain==null){
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

    //删除curtain接口
    //需要传入cid参数
    @RequestMapping(value = "/deleteCurtain",method = RequestMethod.POST)
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
        List<Curtain> funList = curtainService.findAll(page, size);
        PageInfo pageInfo = new PageInfo(funList);
        String string = JSON.toJSONString(pageInfo);
        return string;
    }

    /**
     * 查找设备对应的数据
     * @param page
     * @param size
     * @param cid
     * @return
     */
    @RequestMapping(value = "/findDetailByCid",produces = "application/json;charset=utf-8",method = RequestMethod.POST)
    @ResponseBody
    public String findDetailByCid(@RequestParam(name = "page",required = true,defaultValue = "1")Integer page,
                                  @RequestParam(name = "size",required = true,defaultValue = "10")Integer size,@RequestParam("cid") String cid){
        List<Data_Curtain> funDetail=curtainService.findDetailByFid(page, size,cid);
        PageInfo pageInfo = new PageInfo(funDetail);
        String string = JSON.toJSONString(pageInfo);
        return string;
    }

    /**
     * 查找该用户未绑定的设备
     * @param username
     * @return
     */
    @RequestMapping(value = "/findCurtainByUsername",produces = "application/json;charset=utf-8",method = RequestMethod.POST)
    @ResponseBody
    public String  findDetailByCid(@RequestBody String username){
        JSONObject  jo= new JSONObject();
        JSONObject jsonObject = jo.parseObject(username);
        String user = jsonObject.getString("username");
        List<Curtain> curtainList=curtainService.findOtherCurtain(user);
        String json = JSON.toJSONString(curtainList);
        return json;
    }
}
