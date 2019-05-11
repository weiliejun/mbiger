package com.mbiger.admin.controller;

import com.mbiger.admin.service.Demo1Service;
import com.mbiger.common.model.user.bean.UserInfo;
import com.mbiger.common.redis.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/page")
public class PageController {
    @Autowired
    private Demo1Service demo1Service;

    @Autowired
    private CacheService cacheService;

    @RequestMapping("/index")
    public String pagetest(Model model, HttpServletRequest request){
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(cacheService.get("user.name"));
        model.addAttribute("name","hhhh");
        model.addAttribute("user",userInfo);
        System.out.println("name == " + userInfo.getUserName());
        return "/test";
    }
}
