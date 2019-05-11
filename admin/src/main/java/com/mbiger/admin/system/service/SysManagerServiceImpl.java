package com.mbiger.admin.system.service;


import com.mbiger.admin.web.bean.CurrentManager;
import com.mbiger.common.constant.ApplicationSessionKeys;
import com.mbiger.common.model.sysFunction.bean.SysFunction;
import com.mbiger.common.model.sysFunction.dao.SysFunctionDao;
import com.mbiger.common.model.sysManager.bean.SysManager;
import com.mbiger.common.model.sysManager.dao.SysManagerDao;
import com.mbiger.common.model.sysManagerRole.dao.SysManagerRoleDao;
import com.mbiger.common.model.sysRole.bean.SysRole;
import com.mbiger.common.model.sysRole.dao.SysRoleDao;
import com.mbiger.common.util.IpHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("sysManagerService")
public class SysManagerServiceImpl implements SysManagerService {

    @Autowired
    SysManagerDao sysManagerDao;

    @Autowired
    SysRoleDao sysRoleDao;

    @Autowired
    SysManagerRoleDao sysManagerRoleDao;

    @Autowired
    SysFunctionDao sysFunctionDao;

    public void addSysManager(SysManager sysManager) {
        sysManagerDao.addSysManager(sysManager);
    }

    @Transactional
    public void deleteSysManagerById(Integer id) {
        sysManagerRoleDao.deleteSysManagerRoleByManagerId(id);
        sysManagerDao.deleteSysManagerById(id);
    }

    public int updateSysManager(SysManager sysManager) {
        return sysManagerDao.updateSysManager(sysManager);
    }

    public SysManager getSysManagerById(Integer id) {
        return sysManagerDao.getSysManagerById(id);
    }

    public SysManager getSysManagerByCode(String code) {
        return sysManagerDao.getSysManagerByCode(code);
    }

    public List<SysManager> listSysManagersByParams(Map<String, Object> params) {
        return sysManagerDao.listSysManagersByParams(params);
    }

    public void loginSuccessInit() {
        Subject subject = SecurityUtils.getSubject();
        SysManager sysManager  = (SysManager)subject.getPrincipal();
        //登录成功处理
        if(subject != null && subject.isAuthenticated()){
            Session session = subject.getSession();
            CurrentManager currentManager = new CurrentManager();
            currentManager.setSysManager(sysManager);
            currentManager.setSessionId(session.getId().toString());
            List<SysRole> roleList = sysRoleDao.listRoleByManagerId(sysManager.getId());
            currentManager.setRoles(roleList);
            //查询一级菜单
            List<SysFunction> functionList = sysFunctionDao.listSysFunctionByManagerId(sysManager.getId(),"root");
            currentManager.setLevelOneFunctions(functionList);
            session.setAttribute(ApplicationSessionKeys.CURRENT_USER,currentManager);
        }
    }
}
