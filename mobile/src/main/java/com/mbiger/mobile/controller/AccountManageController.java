package com.mbiger.mobile.controller;

import com.mbiger.common.constant.ApplicationSessionKeys;
import com.mbiger.common.constant.ResultCode;
import com.mbiger.common.model.user.bean.UserInfo;
import com.mbiger.common.util.MD5Util;
import com.mbiger.common.util.StringHelper;
import com.mbiger.common.web.SessionUser;
import com.mbiger.mobile.service.sysMessageManage.SysMessageService;
import com.mbiger.mobile.service.userManage.UserInfoService;
import com.mbiger.mobile.web.base.AbstractBaseController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName AccountManageController
 * @Description 账户管理（密码修改/实名认）
 * @Version 1.0
 * @Author Feng.yanmin
 * @UpdateDate 2019-1-21 16:43:54
 */
@Controller
public class AccountManageController extends AbstractBaseController {

    private static Logger logger = org.apache.log4j.Logger.getLogger(AccountManageController.class);

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private SysMessageService sysMessageService;

    @RequestMapping("/accountManage")//账户设置界面跳转
    public String accountIndex(HttpServletRequest request, Model model) {
        SessionUser sessionUser = getSessionUserBySid(request);
        if (sessionUser == null) {
            return "redirect:/login";
        }
        return "user/account/accountSet";
    }

    @RequestMapping("/accountManage/reset")//APPSECRET重置界面跳转
    public String appReset(HttpServletRequest request, Model model) {
        UserInfo userInfo = getUserInfoBySid(request);
        SessionUser sessionUser = getSessionUserBySid(request);
        model.addAttribute("userInfo", userInfo);
        if (sessionUser == null) {
            return "redirect:/login";
        }
        return "user/account/appsecretReset";
    }

    @RequestMapping("/accountManage/resetSuf")//APPSECRET重置界面成功
    public String appResetSuf(HttpServletRequest request, Model model) {
        SessionUser sessionUser = getSessionUserBySid(request);
        if (sessionUser == null) {
            return "redirect:/login";
        }
        return "user/account/appsecretResetSuf";
    }

    @RequestMapping("/accountManage/resetLos")//APPSECRET重置界面失败
    public String appResetLos(HttpServletRequest request, Model model) {
        SessionUser sessionUser = getSessionUserBySid(request);
        if (sessionUser == null) {
            return "redirect:/login";
        }
        return "user/account/appsecretResetLos";
    }

    @RequestMapping("/accountManage/resetPwd")//修改密码成功
    public String upDatePasswordSuf() {
        return "user/account/modifyPasswordSuf";
    }

    //请求跳转界面
//    @RequestMapping("/accountManage/{operationType}")
//    public String registerIndex(HttpServletRequest request , Model model, @PathVariable String operationType){
//        SessionUser sessionUser =  getSessionUserBySid(request);
//        if(sessionUser == null){
//            return "redirect:/login";
//        }
//        if ("modifyMobile".equals(operationType)) {
//            model.addAttribute("oldMobile", sessionUser.getUserInfo().getMobile());
//        }
//        model.addAttribute("operationType", operationType);
//       return "user/account/"+operationType;
//    }

    @RequestMapping("/accountManage/modifyPassword")//修改登录密码界面
    public String registerIndex(HttpServletRequest request, Model model) {
        SessionUser sessionUser = getSessionUserBySid(request);
        if (sessionUser == null) {
            return "redirect:/login";
        }
        return "user/account/modifyPassword";
    }

    @RequestMapping("/accountManage/checkPassword")
    public @ResponseBody
    boolean checkMobile(HttpServletRequest request, String oldPassword) {
        boolean flag = false;
        try {
            SessionUser sessionUser = getSessionUserBySid(request);
            // 如果是未登录，暂时让通过校验，提交时二次校验
            if (sessionUser == null) {
                return true;
            }
            UserInfo userInfo = sessionUser.getUserInfo();
            if (userInfo != null) {
                if (userInfo.getPassword().equals(MD5Util.MD5(oldPassword))) {
                    flag = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
        return flag;
    }

    /**
     * [账户中心] - [修改密码]
     *
     * @param request
     * @param newPassword
     * @return
     */
    @RequestMapping("/accountManage/modifyPassword/submit")
    public @ResponseBody
    Map<String, ?> register(HttpServletRequest request, String newPassword) {
        String flag = "true";
        String message = "提交成功！";
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            SessionUser sessionUser = getSessionUserBySid(request);
            if (sessionUser == null) {
                resultMap.put("flag", "false");
                resultMap.put("msg", "未登录，请登录后操作！");
                return resultMap;
            }
            // 修改密码
            resultMap = userInfoService.updatePassword(sessionUser.getUserInfo().getId(), newPassword);

        } catch (Exception e) {
            logger.error("msg", e);
            flag = "false";
            message = "系统异常！";
        }
        resultMap.put("flag", flag);
        resultMap.put("msg", message);
        return resultMap;
    }

    @RequestMapping("/accountManage/modifyMobile")//修改手机号界面1跳转
    public String checkMobile01(HttpServletRequest request, Model model) {
        SessionUser sessionUser = getSessionUserBySid(request);
        if (sessionUser == null) {
            return "redirect:/login";
        }
        UserInfo userById = userInfoService.getUserById(sessionUser.getUserInfo().getId());
        model.addAttribute("userById", userById);
        return "user/account/modifyMobileOne";
    }

    @RequestMapping("/accountManage/modifyMobileTwo")//修改手机号界面2跳转
    public String checkMobile02(HttpServletRequest request, Model model) {
        SessionUser sessionUser = getSessionUserBySid(request);
        if (sessionUser == null) {
            return "redirect:/login";
        }
        UserInfo userById = userInfoService.getUserById(sessionUser.getUserInfo().getId());
        model.addAttribute("user", userById);
        return "user/account/modifyMobileTwo";
    }

    @RequestMapping("/accountManage/modifyMobileSuf")//修改手机号界面跳转-成功
    public String checkMobileSuf(HttpServletRequest request, Model model) {
        SessionUser sessionUser = getSessionUserBySid(request);
        if (sessionUser == null) {
            return "redirect:/login";
        }
        UserInfo userById = userInfoService.getUserById(sessionUser.getUserInfo().getId());
        model.addAttribute("user", userById);
        return "user/account/modifyMobileSuf";
    }

    /**
     * [账户中心] - [修改手机号] - [第一步]
     *
     * @param request
     * @param oldSmsCode
     * @return
     */
    @RequestMapping("/accountManage/modifyMobile/checkOldMobile")
    @ResponseBody
    public Map<String, ?> checkOldMobile(HttpServletRequest request, String oldSmsCode) {
        String flag = "true";
        String message = "校验通过！";
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            SessionUser sessionUser = getSessionUserBySid(request);
            if (sessionUser == null) {
                resultMap.put("flag", "false");
                resultMap.put("msg", "未登录，请登录后操作！");
                return resultMap;
            }
            String exitSmsCodeSession = (String) request.getSession().getAttribute(ApplicationSessionKeys.SMS_VERIFY_CODE);
            String mobile = sessionUser.getUserInfo().getMobile();
            // 校验：短信验证码是否正确
            resultMap = sysMessageService.validateSmsCodeByParams(mobile, "mobileEdit", oldSmsCode, exitSmsCodeSession);
            String flag1 = (String) resultMap.get("flag");
            if (!"true".equals(flag1)) {
                return resultMap;
            }
        } catch (Exception e) {
            logger.error("msg", e);
            flag = "false";
            message = "系统异常！";
        }
        resultMap.put("flag", flag);
        resultMap.put("msg", message);
        return resultMap;
    }

    /**
     * [账户中心] - [修改手机号] - [第二步]
     *
     * @param request
     * @param mobile
     * @param graphicValidateCode
     * @return
     */
    @RequestMapping("/accountManage/modifyMobile/checkNewMobile")
    @ResponseBody
    public Map<String, ?> checkNewMobile(HttpServletRequest request, String mobile, String graphicValidateCode) {
        String flag = "true";
        String message = "校验通过！";
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            SessionUser sessionUser = getSessionUserBySid(request);
            if (sessionUser == null) {
                resultMap.put("flag", "false");
                resultMap.put("msg", "未登录，请登录后操作！");
                return resultMap;
            }
            // 校验：手机号是否已被注册
            UserInfo userInfo = userInfoService.getUserByMobile(mobile);
            if (userInfo != null) {
                resultMap.put("flag", "false");
                resultMap.put("msg", "您输入的手机号已被注册");
                return resultMap;
            }
            String exitValidateCode = (String) request.getSession().getAttribute(ApplicationSessionKeys.LOGIN_VERIFYCODE);
            // 校验3：图形验证码
            if (StringHelper.isEmpty(exitValidateCode) || !exitValidateCode.equalsIgnoreCase(graphicValidateCode)) {
                resultMap.put("flag", ResultCode.VALIDATE_CODE_ERROR.code());
                resultMap.put("msg", ResultCode.VALIDATE_CODE_ERROR.message());
                return resultMap;
            }
            String exitSmsCodeSession = (String) request.getSession().getAttribute(ApplicationSessionKeys.SMS_VERIFY_CODE);
            // 校验：短信验证码是否正确
            resultMap = sysMessageService.validateSmsCodeByParams(mobile, "mobileBind", graphicValidateCode, exitSmsCodeSession);
            String flag1 = (String) resultMap.get("flag");
            if (!"true".equals(flag1)) {
                return resultMap;
            }
            UserInfo user = new UserInfo();
            user.setId(sessionUser.getUserInfo().getId());
            user.setMobile(mobile);
            user.setUpdateTime(new Date());
            userInfoService.updateUserInfo(user);
        } catch (Exception e) {
            logger.error("msg", e);
            flag = "false";
            message = "系统异常！";
        }
        resultMap.put("flag", flag);
        resultMap.put("msg", message);
        return resultMap;
    }

}
