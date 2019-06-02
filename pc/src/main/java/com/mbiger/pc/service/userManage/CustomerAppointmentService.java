package com.mbiger.pc.service.userManage;


import com.mbiger.common.model.customerAppointment.bean.CustomerAppointment;

import java.util.Map;

public interface CustomerAppointmentService {
    CustomerAppointment addCustomerAppointment(CustomerAppointment customerAppointment);

    void updateCustomerAppointment(CustomerAppointment customerAppointment);

    CustomerAppointment getCustomerAppointmentById(Integer id);

    int countCustomerAppointmentsByParams(Map<String, Object> params);

}