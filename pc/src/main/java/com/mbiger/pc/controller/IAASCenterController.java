package com.mbiger.pc.controller;

import com.mbiger.common.model.serviceinfo.bean.ServiceInfo;
import com.mbiger.pc.service.mbigerServiceManage.MbigerService;
import com.mbiger.pc.web.base.AbstractBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName IAASCenterController
 * @Description 账户中心-云计算IAAS（预约咨询）
 * @Version 1.0
 * @Author Feng.yanmin
 * @UpdateDate 2019-1-21 16:43:54
 */
@Controller
@RequestMapping("/account")
public class IAASCenterController extends AbstractBaseController {
    @Autowired
    private MbigerService mbigerService;

    /**
     * 账户用心-iaas服务页面
     *
     * @param request
     * @param model
     * @param serviceType
     * @return
     */
    @RequestMapping("/iaasCenter/{serviceType}")
    public String iaasCenterIndex(HttpServletRequest request, Model model, @PathVariable String serviceType) {
        // 查询  服务信息
        ServiceInfo serviceInfo = mbigerService.getServiceInfoByCode(serviceType);
        model.addAttribute("serviceInfo", serviceInfo);
        model.addAttribute("serviceType", serviceType);
        return "userAccount/iaasCenter/consulting";
    }


}
