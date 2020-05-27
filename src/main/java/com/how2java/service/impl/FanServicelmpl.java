package com.how2java.service.impl;

import com.how2java.mapper.FanMapper;
import com.how2java.pojo.Fan;
import com.how2java.pojo.Data_Fan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.how2java.service.FanService;
import java.util.List;
import java.util.Map;

@Service
public class FanServicelmpl implements FanService {
    @Autowired
    private FanMapper fanMapper;

    @Override
    public void addFanData(Data_Fan data_fan) {
        fanMapper.addFanData(data_fan);
    }

    @Override
    public List<Data_Fan> getFanData(Map map) {
        return fanMapper.getFanData(map);
    }

    @Override
    public Fan getFan(String fid) {
        return fanMapper.getFan(fid);
    }

    @Override
    public List<Fan> getAllFan(){return  fanMapper.getAllFan();}

    @Override
    public void updateFan(Fan fan) {
        fanMapper.updateFan(fan);
    }

    @Override
    public void updateFanState(Fan fan) {
        fanMapper.updateFanState(fan);
    }

    @Override
    public void addFan(Fan fan) {fanMapper.addFan(fan);}

    @Override
    public void deleteFan(String fid){fanMapper.deleteFan(fid);};
}
