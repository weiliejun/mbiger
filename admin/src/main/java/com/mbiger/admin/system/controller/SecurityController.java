package com.mbiger.admin.system.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mbiger.admin.system.service.SecurityService;
import com.mbiger.admin.web.base.AbstractBaseController;
import com.mbiger.common.model.sysFunction.bean.SysFunction;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Description 权限管理
 * @auther: zhangkele
 * @UpadteDate: 2019/2/21 11:07
 */
@Controller
@RequestMapping("/security")
public class SecurityController extends AbstractBaseController {

    @Autowired
    private SecurityService securityService;

    /**
     * 加载权限菜单
     */
    @ResponseBody
    @RequestMapping("/load/sysfunction")
    public String loadMenuInfo(@RequestParam String parentCode) {
        List<SysFunction> sysFunctions = securityService.listSysFunctionsByParentCode(parentCode);
        JSONArray jsonArray = JSON.parseArray(JSON.toJSONString(sysFunctions));
        JSONObject resultJson = new JSONObject();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject sysFunction = (JSONObject) jsonArray.get(i);
            String code = sysFunction.getString("code");
            List<SysFunction> sysFunctionTwo = securityService.listSysFunctionsByParentCode(code);
            JSONArray jsonArrayTwo = new JSONArray();
            for (SysFunction sysfunction : sysFunctionTwo) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", sysfunction.getCode()); // 节点id
                jsonObject.put("title", sysfunction.getName()); // 节点名称
                jsonObject.put("spread", false); // 不展开
                jsonObject.put("icon", sysfunction.getIcon());
                if (StringUtils.isNotEmpty(sysfunction.getUrl())) {
                    jsonObject.put("href", sysfunction.getUrl()); // 菜单请求地址
                }
                jsonArrayTwo.add(jsonObject);
            }
            resultJson.put(code, jsonArrayTwo);
        }
        return JSON.toJSONString(resultJson);
    }
}
