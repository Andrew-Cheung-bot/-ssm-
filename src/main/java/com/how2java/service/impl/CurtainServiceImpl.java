package com.how2java.service.impl;

import com.how2java.pojo.Data_Curtain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.how2java.mapper.CurtainMapper;
import com.how2java.pojo.Curtain;
import com.how2java.service.CurtainService;

import java.util.List;
import java.util.Map;

@Service
public class CurtainServiceImpl implements CurtainService {
    @Autowired
    private CurtainMapper curtainMapper;

    @Override
    public void addCurtainData(Data_Curtain data_curtain) {
        curtainMapper.addCurtainData(data_curtain);
    }

    @Override
    public List<Data_Curtain> getCurtainData(Map param) {
        return curtainMapper.getCurtainData(param);
    }

    @Override
    public Curtain getCurtain(String cid) {
        return curtainMapper.getCurtain(cid);
    }

    @Override
    public List<Curtain> getAllCurtain(){return curtainMapper.getAllCurtain();}

    @Override
    public void updateCurtain(Curtain curtain) {
        curtainMapper.updateCurtain(curtain);
    }

    @Override
    public void updateCurtainState(Curtain curtain) {
        curtainMapper.updateCurtainState(curtain);
    }

    @Override
    public void addCurtain(Curtain curtain) {curtainMapper.addCurtain(curtain);}

    @Override
    public void deleteCurtain(String cid){curtainMapper.deleteCurtain(cid);};
}
