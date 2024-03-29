package com.mbiger.mobile.controller;

import com.mbiger.common.constant.ApplicationSessionKeys;
import com.mbiger.common.util.IpHelper;
import com.mbiger.common.web.SessionUser;
import com.mbiger.mobile.service.sysMessageManage.SysMessageService;
import com.mbiger.mobile.web.base.AbstractBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@Controller
public class LoginController extends AbstractBaseController {

    @Autowired
    private SysMessageService sysMessageService;

    /**
     * @author cyp
     * @time : 2019-01-14 16:11
     * @description 跳转到登录页面
     */
    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String loginIndex() {
        return "/user/portal/login";
    }

    /**
     * @author cyp
     * @time : 2019-01-14 16:14
     * @description 登录的方法
     */
    @RequestMapping(value = {"/login"}, method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map<String, ?> onLogin(HttpServletRequest request,
                                  @RequestParam(value = "userName", required = true) String userName,
                                  @RequestParam(value = "password", required = true) String password,
                                  @RequestParam(value = "validateCode", required = false) String validateCode) {

        String exitVerifyCode = (String) request.getSession().getAttribute(ApplicationSessionKeys.LOGIN_VERIFYCODE);
        // 处理登录逻辑，登录时重新生成session
        String sid = request.getSession().getId();
        String ip = IpHelper.getClientIpAddress(request);
        // 登录
        Map<String, String> result = userInfoService.login(userName, password, ip, sid, exitVerifyCode, validateCode);

        /**
         * session系统消息总数
         */
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("mobile", userName);
        int countUnreadMessageNum = sysMessageService.countSysMessageNums(params);
        request.getSession().setAttribute(ApplicationSessionKeys.SYS_MESSAGE_NUMS_COUNT, countUnreadMessageNum);

        return result;
    }

    /**
     * @author cyp
     * @time : 2019-01-16 13:29
     * @description 忘记密码
     */
    @RequestMapping("/user/forgotpassword")
    public String forgotPassword(Model model) {
        model.addAttribute("headText", "找回密码");
        return "/user/portal/forgotPassword";
    }

    /**
     * @author cyp
     * @time : 2019-01-16 13:29
     * @description 忘记密码-修改新密码页面跳转
     */
    @RequestMapping("/user/forgotPwdSuf")
    public String forgotNewPassword(String mobile, Model model) {
        model.addAttribute("headText", "找回密码");
        model.addAttribute("mobile", mobile);
        return "/user/portal/newPasswordSet";
    }

    /**
     * @author cyp
     * @time : 2019-01-16 13:29
     * @description 忘记密码-修改密码成功页面跳转
     */
    @RequestMapping("/user/forgotResetPwdSuf")
    public String forgotPasswordSuf(Model model) {
        model.addAttribute("headText", "找回密码");
        return "/user/portal/resetPwdSuf";
    }

    @RequestMapping("/islogin")
    @ResponseBody
    public boolean islogin(HttpServletRequest request) {
        boolean flag = false;
        SessionUser sessionUser = getSessionUserBySid(request);
        if (sessionUser != null) {
            flag = true;
        }
        return flag;
    }
}
