package com.mbiger.common.model.sysManagerRole.dao;

import com.mbiger.common.db.AbstractBaseDao;
import com.mbiger.common.model.sysManagerRole.bean.SysManagerRole;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class SysManagerRoleDao extends AbstractBaseDao {

    public void addSysManagerRole(SysManagerRole sysManagerRole) {
         insert("sysManagerRole.addSysManagerRole",sysManagerRole);
    }

    public void updateSysManagerRole(SysManagerRole sysManagerRole) {
        update("sysManagerRole.updateSysManagerRole",sysManagerRole);
    }
    public void deleteSysManagerRoleByManagerId(Integer managerId) {
        delete("sysManagerRole.deleteSysManagerRoleByManagerId",managerId);
    }

    public void deleteSysManagerRoleByRoleId(Integer roleId) {
        delete("sysManagerRole.deleteSysManagerRoleByRoleId",roleId);
    }

    public SysManagerRole getaysManagerRoleById(Integer id) {
        return (SysManagerRole)queryForObject("sysManagerRole.getSysManagerRoleById",id);
    }
    public List<SysManagerRole> listSysManagerRoleByManagerId(Integer managerId) {
        return (List<SysManagerRole>)queryForList("sysManagerRole.listSysManagerRoleByManagerId",managerId);
    }

}