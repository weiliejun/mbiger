package com.mbiger.common.model.sysLoginLog.dao;

import com.mbiger.common.db.AbstractBaseDao;
import com.mbiger.common.model.sysLoginLog.bean.SysLoginLog;
import org.springframework.stereotype.Repository;

@Repository
public class SysLoginLogDao extends AbstractBaseDao {
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    public int insert(SysLoginLog record) {
        return 0;
    }

    public int insertSelective(SysLoginLog record) {
        return 0;
    }

    public SysLoginLog selectByPrimaryKey(Integer id) {
        return null;
    }

    public int updateByPrimaryKeySelective(SysLoginLog record) {
        return 0;
    }

    public int updateByPrimaryKey(SysLoginLog record) {
        return 0;
    }
}