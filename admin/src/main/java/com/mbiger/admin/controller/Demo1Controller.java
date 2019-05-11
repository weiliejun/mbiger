package com.mbiger.admin.controller;

import com.alibaba.fastjson.JSON;
import com.mbiger.admin.service.Demo1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Demo1Controller {
    @Autowired
    private Demo1Service demo1Service;
    @RequestMapping("/testget")
    public String testadmin(@RequestParam(name = "test",defaultValue = "1", required = true) String a){
        /*return "admin" + JSON.toJSONString(demo1Service.findUserInfo("17062611140171996313"));*/
        return "admin" + JSON.toJSONString(demo1Service.findUserInfo(12));
    }
}
