package com.smarthousehold.service.impl;

import com.github.pagehelper.PageHelper;
import com.smarthousehold.pojo.Data_Curtain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smarthousehold.mapper.CurtainMapper;
import com.smarthousehold.pojo.Curtain;
import com.smarthousehold.service.CurtainService;

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
    public void deleteCurtain(String cid){
        curtainMapper.deleteCurtain_user(cid);
        curtainMapper.deleteCurtain_data(cid);
        curtainMapper.deleteCurtain(cid);
    }

    @Override
    public List<Curtain> findAll(Integer page, Integer size) {
        PageHelper.startPage(page,size);
        return curtainMapper.findAll();
    }

    @Override
    public List<Data_Curtain> findDetailByFid(Integer page, Integer size, String cid) {
        PageHelper.startPage(page,size);
        return curtainMapper.findDetailByFid(cid);
    }

    @Override
    public List<Curtain> findOtherCurtain(String user) {
        return  curtainMapper.findOtherCurtain(user);
    }

}
