package com.mbiger.pc.controller;

import com.mbiger.pc.service.userManage.UserInfoService;
import com.mbiger.pc.web.base.AbstractBaseController;
import com.mbiger.common.web.SessionUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName LoginOutController
 * @Description 登出系统
 * @Author 徐赛平
 * @UpdateDate 2019/1/22 15:00
 */
@Controller
public class LoginOutController extends AbstractBaseController {

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping(value = {"/loginOut"},method = RequestMethod.GET)
    public String loginOut(HttpServletRequest request) {
        SessionUser sessionUser = getSessionUserBySid(request);
        if (sessionUser != null) {
            userInfoService.loginOut(sessionUser.getUserInfo().getId());
        }

        return "redirect:/";
    }

}
