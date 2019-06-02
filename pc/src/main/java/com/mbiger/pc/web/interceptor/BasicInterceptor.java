package com.mbiger.pc.web.interceptor;

import com.mbiger.common.constant.ApplicationSessionKeys;
import com.mbiger.common.web.SessionUser;
import com.mbiger.pc.service.sysMessageManage.SysMessageService;
import com.mbiger.pc.service.userManage.UserInfoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 基础拦截器
 */
@Component
public class BasicInterceptor extends HandlerInterceptorAdapter {
    private static final Log logger = LogFactory.getLog(BasicInterceptor.class);
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
    private Date processStartTime = null;

    @Autowired
    UserInfoService userInfoService;
    @Autowired
    SysMessageService sysMessageService;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        processStartTime = new Date();
        String path = request.getRequestURI() + (request.getQueryString() == null ? "" : "?" + request.getQueryString());
        logger.info("Execute Time：" + dateFormat.format(new Date()) + " Execute Request：" + path);
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        if (request.getServerPort() == 80) {
            basePath = request.getScheme() + "://" + request.getServerName() + request.getContextPath() + "/";
        }
        logger.info("basePath ：" + basePath);

        //添加登录标识
        SessionUser sessionUser = userInfoService.getSessionUserBySid(request.getSession().getId());
        if (sessionUser != null) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("mobile", sessionUser.getUserInfo().getMobile());
            int countUnreadMessageNum = sysMessageService.countSysMessageNums(params);
            request.getSession().setAttribute(ApplicationSessionKeys.SYS_MESSAGE_NUMS_COUNT, countUnreadMessageNum);
            request.getSession().setAttribute("isLogin", "true");
        } else {
            request.getSession().setAttribute("isLogin", "false");
        }
        return true;
    }

    public void afterCompletion(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, java.lang.Object handler, java.lang.Exception ex) throws java.lang.Exception {
        Date processEndTime = new Date();
        logger.info("Execute Duration：" + (processEndTime.getTime() - processStartTime.getTime()) + "ms");

        String path = request.getRequestURI() + (request.getQueryString() == null ? "" : "?" + request.getQueryString());
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String path = request.getRequestURI() + (request.getQueryString() == null ? "" : "?" + request.getQueryString());
    }

}
