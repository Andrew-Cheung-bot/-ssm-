package com.smarthousehold.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.smarthousehold.pojo.Fan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/Main")
public class MainController {
    @RequestMapping(value = "/getUser",method = RequestMethod.GET)
    @ResponseBody
    public String getUser(Principal principal){
        return principal.getName();
    }
}
