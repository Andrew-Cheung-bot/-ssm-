package com.smarthousehold.mapper;

import com.smarthousehold.pojo.Curtain;
import com.smarthousehold.pojo.Fan;
import com.smarthousehold.pojo.Data_Fan;

import java.util.List;
import java.lang.String;
import java.util.Map;

public interface FanMapper {
    //添加某时刻温湿度的数据
    void addFanData(Data_Fan data_fan);
    //获取某一段时间的温湿度
    List<Data_Fan> getFanData(Map param);
    //获取风扇默认的温湿度值
    Fan getFan(String fid);
    //获取所有风扇的温湿度值和状态
    List<Fan> getAllFan();
    //更新风扇的默认温湿度值
    void updateFan(Fan fan);
    //更新风扇的开关状态
    void updateFanState(Fan fan);
    //增加风扇
    void addFan(Fan fan);
    //删除风扇
    void deleteFan(String fid);

    List<Fan> findAll();

    List<Data_Fan> findDetailByFid(String fid);

    void deleteFan_data(String fid);

    void deleteFan_user(String fid);

    List<Fan> findOtherFan(String user);

    List<Fan> findFanByUsername(String username);
}
