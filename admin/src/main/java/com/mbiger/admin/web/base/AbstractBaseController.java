package com.mbiger.admin.web.base;

import com.mbiger.admin.web.bean.CurrentManager;
import com.mbiger.admin.web.util.WebUtil;
import com.mbiger.common.constant.ApplicationSessionKeys;
import com.mbiger.common.model.sysManager.bean.SysManager;
import com.mbiger.common.util.StringHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: controller基类
 */
public abstract class AbstractBaseController {
    private static final Log logger = LogFactory.getLog(AbstractBaseController.class);

    /**
     * @Description 表单查询条记忆功能
     * @auther: zhangkele
     * @UpadteDate: 2019/2/22 9:05
     */
    protected Map<String, Object> formQueryRemenber(HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        //查询路径
        String queryURI = request.getRequestURI();
        HttpSession session = request.getSession();

        Enumeration enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String paraName = (String) enu.nextElement();
            String paraValue = request.getParameter(paraName).trim();
            if (StringHelper.isNotEmpty(paraValue)) {
                params.put(paraName, paraValue);
            } else {
                if (params.containsKey(paraName)) {
                    params.remove(paraName);
                }
            }
        }
        //分页信息默认值设置
        String pageSize = request.getParameter("pageSize") == null ? "5" : request.getParameter("pageSize");
        String currentPage = request.getParameter("currentPage") == null ? "1" : request.getParameter("currentPage");
        if (params.get("pageSize") == null) {
            params.put("pageSize", pageSize);
        }
        if (params.get("currentPage") == null) {
            params.put("currentPage", currentPage);
        }
        session.setAttribute(queryURI, params);
        return params;
    }

    protected Map<String, Object> getQureyParams(Map<String, Object> requestParams) {
        Map<String, Object> params = new HashMap<String, Object>();
        //移除分页信息
        requestParams.remove("pageSize");
        requestParams.remove("currentPage");

        params.putAll(requestParams);
        return params;
    }

    /**
     * @Description 从session中获取用户
     * @auther: zhangkele
     * @UpadteDate: 2019/3/4 15:05
     */
    protected SysManager getSessionSysManager() {
        Subject subject = SecurityUtils.getSubject();
        subject.getSession();
        CurrentManager currentManager = (CurrentManager) subject.getSession().getAttribute(ApplicationSessionKeys.CURRENT_USER);
        if (currentManager != null) {
            return currentManager.getSysManager();
        }
        return null;
    }

    /**
     * ajax保存失败时，重置token
     */
    protected void resetSessionToken(Map<String, Object> resultMap) {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        String token = WebUtil.generateToken(session.getId().toString());
        session.setAttribute("token", token);
        resultMap.put("token", token);
    }


}
