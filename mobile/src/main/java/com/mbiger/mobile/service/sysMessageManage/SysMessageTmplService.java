package com.mbiger.mobile.service.sysMessageManage;

import com.mbiger.common.model.sysMessageTmpl.bean.SysMessageTmpl;

import java.util.Map;

public interface SysMessageTmplService {

    SysMessageTmpl addSysMessageTmpl(SysMessageTmpl record);


    SysMessageTmpl getSysMessageTmplById(Integer id);


    SysMessageTmpl updateSysMessageTmpl(SysMessageTmpl record);

    /**
     * cyp
     *
     * @param busiType
     * @param type
     * @param values
     * @return 数据库获取模板
     */
    Map<String, String> tmplAssignment(String busiType, String type, Map<String, String> values);
}