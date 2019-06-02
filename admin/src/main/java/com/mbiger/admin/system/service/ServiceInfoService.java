package com.mbiger.admin.system.service;

import com.mbiger.common.model.serviceinfo.bean.ServiceInfo;

import java.util.List;
import java.util.Map;

public interface ServiceInfoService {

    void addServiceInfo(ServiceInfo serviceInfo);

    void updateServiceInfo(ServiceInfo serviceInfo);

    ServiceInfo getServiceInfoById(Integer id);

    /**
     * @Description 根据动态参数查询ServiceInfo列表
     * @auther: cyp
     * @UpadteDate: 2019/3/01 16:59
     */
    List<ServiceInfo> listServiceInfosByParams(Map<String, Object> params);


}
