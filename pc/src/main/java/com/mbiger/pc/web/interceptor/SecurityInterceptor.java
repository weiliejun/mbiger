package com.mbiger.pc.web.interceptor;


import com.mbiger.common.web.SessionUser;
import com.mbiger.pc.service.userManage.UserInfoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Enumeration;

/**
 * 权限验证	拦截器
 */
@Component
public class SecurityInterceptor extends HandlerInterceptorAdapter {
    private static final Log logger = LogFactory.getLog(SecurityInterceptor.class);

    @Autowired
    UserInfoService userInfoService;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        SessionUser sessionUser = userInfoService.getSessionUserBySid(request.getSession().getId());
        if (sessionUser == null) {
            String returnUrl = null;
            if (isAjaxRequest(request)) {
                returnUrl = getReferer(request);
            } else {
                returnUrl = request.getRequestURI() + (request.getQueryString() == null ? "" : "?" + request.getQueryString());
            }
            StringBuilder redirectUrl = new StringBuilder();
            redirectUrl.append(request.getContextPath());
            redirectUrl.append("/login");
            if (returnUrl != null) {
                redirectUrl.append("?returnUrl=");
                redirectUrl.append(URLEncoder.encode(returnUrl.replaceFirst(request.getContextPath(), "")));
            }
            response.sendRedirect(redirectUrl.toString());
            return false;
        }
        return true;
    }

    public void afterCompletion(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, java.lang.Object handler, java.lang.Exception ex) throws java.lang.Exception {
    }

    /**
     * 判断是否是ajax请求
     */
    private boolean isAjaxRequest(HttpServletRequest request) {
        boolean isAjax = false;
        String XRequested = request.getHeader("X-Requested-With");
        if ("XMLHttpRequest".equals(XRequested)) {
            isAjax = true;
        }
        return isAjax;
    }

    /**
     * 获取referer
     */
    private String getReferer(HttpServletRequest request) {
        Enumeration e = request.getHeaders("Referer");
        String referer = null;
        if (e.hasMoreElements()) {
            referer = (String) e.nextElement();
        }
        return referer;
    }


}
