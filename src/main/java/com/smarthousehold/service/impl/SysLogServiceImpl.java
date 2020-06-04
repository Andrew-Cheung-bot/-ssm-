package com.smarthousehold.service.impl;

import com.github.pagehelper.PageHelper;

import com.smarthousehold.mapper.ISysLogDao;
import com.smarthousehold.pojo.SysLog;
import com.smarthousehold.service.ISysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysLogServiceImpl implements ISysLogService {
    @Autowired
    private ISysLogDao sysLogDao;
    @Override
    public void save(SysLog sysLog) throws Exception {
         sysLogDao.save(sysLog);
    }

    @Override
    public List<SysLog> findAll() throws Exception {
        return sysLogDao.findAll();
    }
}
