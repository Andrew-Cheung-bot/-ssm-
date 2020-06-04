package com.smarthousehold.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;

import com.smarthousehold.pojo.SysLog;
import com.smarthousehold.service.ISysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/sysLog")
public class SysLogController {
    @Autowired
    private ISysLogService sysLogService;

    @RequestMapping(value = "/findAll",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String findAll(@RequestParam(name = "page",required = true,defaultValue = "1") Integer page,
                                @RequestParam(name = "size",required = true,defaultValue = "10") Integer size)throws Exception{
        List<SysLog> sysLogs=sysLogService.findAll(page,size);
        PageInfo pageInfo = new PageInfo(sysLogs);
        String string = JSON.toJSONString(pageInfo);
        return string;
    }

}
