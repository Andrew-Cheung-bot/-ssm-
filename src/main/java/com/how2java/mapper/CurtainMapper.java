package com.how2java.mapper;

import com.how2java.pojo.Curtain;
import com.how2java.pojo.Data_Curtain;

import java.lang.String;
import java.util.List;
import java.util.Map;

public interface CurtainMapper {
    //添加某时刻温湿度的数据
    void addCurtainData(Data_Curtain  data_curtain);
    //获取某一段时间的温湿度
    List<Data_Curtain> getCurtainData(Map param);
    //获取窗帘默认的温湿度值和状态
    Curtain  getCurtain(String cid);
    //获取所有窗帘温湿度和状态
    List<Curtain>  getAllCurtain();
    //更新窗帘的默认温湿度值
    void updateCurtain(Curtain curtain);
    //更新窗帘的开关状态
    void updateCurtainState(Curtain curtain);
    //增加窗帘
    void addCurtain(Curtain curtain);
    //删除窗帘
    void deleteCurtain(String cid);
}
