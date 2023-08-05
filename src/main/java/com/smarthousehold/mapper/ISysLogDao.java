package com.smarthousehold.mapper;


import com.smarthousehold.pojo.SysLog;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISysLogDao {

    void save(SysLog sysLog)throws Exception;

    List<SysLog> findAll()throws Exception;
}
