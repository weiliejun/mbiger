package com.mbiger.mobile.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mbiger.common.constant.ApplicationSessionKeys;
import com.mbiger.common.model.serviceinfo.bean.ServiceInfo;
import com.mbiger.common.model.sysMessage.bean.SysMessage;
import com.mbiger.common.model.user.bean.UserInfo;
import com.mbiger.common.util.StringHelper;
import com.mbiger.common.web.SessionUser;
import com.mbiger.mobile.service.mbigerServiceManage.MbigerService;
import com.mbiger.mobile.service.sysMessageManage.SysMessageService;
import com.mbiger.mobile.service.userManage.UserExpenseService;
import com.mbiger.mobile.service.userManage.UserInfoService;
import com.mbiger.mobile.web.base.AbstractBaseController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/account")
public class UserAccountController extends AbstractBaseController {

    private static Logger logger = org.apache.log4j.Logger.getLogger(UserAccountController.class);

    @Autowired
    private UserExpenseService userExpenseService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private SysMessageService sysMessageService;
    @Autowired
    private MbigerService mbigerService;

    @RequestMapping("/index")
    /**
     * @Description 账户中心页面
     * @auther: xsp
     * @date: 2019/1/17 18:19
     */
    public String index(HttpServletRequest request, Model model, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "8") Integer pageSize, String loadingType) {
        UserInfo userInfo = getUserInfoBySid(request);

        //引入分页查询，使用PageHelper分页功能在查询之前传入当前页，然后多少记录
        PageHelper.startPage(1, pageSize * pageNum);
        //startPage后紧跟的这个查询就是分页查询
        List<Map<String, Object>> userExpenseList = userExpenseService.listUserExpensesRecently(userInfo.getId());
        //使用PageInfo包装查询结果，只需要将pageInfo交给页面就可以
        PageInfo pageInfo = new PageInfo<Map<String, Object>>(userExpenseList);

        model.addAttribute("userInfo", userInfo);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("pageSize", pageSize);

        String mobile = userInfo.getMobile();
        model.addAttribute("headText", StringHelper.replaceWithStr(mobile, "*", 3, 6));
        model.addAttribute("userAccountBalance", userInfo.getUserAccountBalance());

        if ("partLoad".equals(loadingType)) {
            return "user/account/recServiceList";
        } else {
            return "user/account/index";
        }
    }

    /**
     * @Description 动态查询服务列表（个人中心）
     * @auther: fym
     * @UpadteDate: 2019/2/21 15:46
     */
    @RequestMapping(value = {"/serviceCenterData"}, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map<String, ?> serviceCenter(HttpServletRequest request) {
        SessionUser sessionUser = getSessionUserBySid(request);
        Map<String, Object> result = new HashMap<String, Object>();
        //账户中心左边导航栏 - iaas 服务
        List<ServiceInfo> iaasServiceList = mbigerService.listServiceInfosByServiceModule("IAAS");
        result.put("iaasServiceList", iaasServiceList);
        // 账户中心左边导航栏 - paas 服务
        List<ServiceInfo> paasServiceList = mbigerService.listServiceInfosByServiceModule("PAAS");
        result.put("paasServiceList", paasServiceList);
        //账户中心左边导航栏 - 定制服务
        List<ServiceInfo> customizationServiceList = mbigerService.listServiceInfosByServiceModule("CUSTOMIZATION");
        result.put("customizationServiceList", customizationServiceList);

        return result;
    }

    /**
     * @Description 重置APPKEY
     * @auther: xsp
     * @UpadteDate: 2019/1/22 15:46
     */
    @RequestMapping("/resetAppSecret")
    @ResponseBody
    public Map<String, String> resetAppSecret(HttpServletRequest request) {
        SessionUser sessionUser = getSessionUserBySid(request);
        Map<String, String> result = new HashMap<String, String>();
        if (sessionUser != null) {
            result = userInfoService.updateUserAppSecret(sessionUser.getUserInfo().getId(), sessionUser.getSessionId());
        } else {
            result.put("flag", "false");
        }
        return result;
    }

    /**
     * @Description 个人系统消息
     * @auther: cyp
     * @UpadteDate: 2019-01-22 17:04
     */
    @RequestMapping(value = {"/sysMessage/index"}, method = RequestMethod.GET)
    public String sysMessageIndex(HttpServletRequest request, Model model, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        UserInfo userInfo = getUserInfoBySid(request);
        Map<String, Object> params = new HashMap<String, Object>();

        //引入分页查询，使用PageHelper分页功能在查询之前传入当前页，然后多少记录
        PageHelper.startPage(pageNum, pageSize);

        //startPage后紧跟的这个查询就是分页查询
        params.put("userId", userInfo.getId());
        params.put("type", "website");
        List<Map<String, Object>> sysMessagesList = sysMessageService.listSysMessagesByParams(params);

        //使用PageInfo包装查询结果，只需要将pageInfo交给页面就可以
        PageInfo pageInfo = new PageInfo<Map<String, Object>>(sysMessagesList);

        List<Map<String, Object>> sysMessagesMap = pageInfo.getList();
        //循环遍历，修改status为0；
        int countReadNum = 0;
        if (sysMessagesMap != null && sysMessagesMap.size() > 0) {
            for (Map<String, Object> sysMessages : sysMessagesMap) {
                int id = (Integer) sysMessages.get("id");
                SysMessage sysMessage = sysMessageService.getSysMessageById(id);
                String status = sysMessage.getStatus();
                if (status.equals("1")) {
                    sysMessage.setStatus("0");
                    sysMessageService.updateSysMessage(sysMessage);
                    countReadNum = countReadNum + 1;
                }
            }
        }

        //session取出系统消息的数量 - countReadNum；两数之差放入到session
        int messageNums = (Integer) request.getSession().getAttribute(ApplicationSessionKeys.SYS_MESSAGE_NUMS_COUNT);
        int countSysMessages = messageNums - countReadNum;
        if (countSysMessages <= 0) {
            countSysMessages = 0;
        }
        request.getSession().setAttribute(ApplicationSessionKeys.SYS_MESSAGE_NUMS_COUNT, countSysMessages);

        model.addAttribute("userInfo", userInfo);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("pageSize", pageSize);

        return "user/account/sysMessage";
    }

}
