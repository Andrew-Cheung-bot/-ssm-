package com.smarthousehold.service;


import com.smarthousehold.pojo.SysLog;

import java.util.List;

public interface ISysLogService {

    public void save(SysLog sysLog)throws Exception;



    List<SysLog> findAll(Integer page, Integer size)throws Exception;
}
