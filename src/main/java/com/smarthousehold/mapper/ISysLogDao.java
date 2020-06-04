package com.smarthousehold.mapper;


import com.smarthousehold.pojo.SysLog;

import java.util.List;

public interface ISysLogDao {

    void save(SysLog sysLog)throws Exception;

    List<SysLog> findAll()throws Exception;
}
