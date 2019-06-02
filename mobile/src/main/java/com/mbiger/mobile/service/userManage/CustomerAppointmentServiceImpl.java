package com.mbiger.mobile.service.userManage;

import com.mbiger.common.model.customerAppointment.bean.CustomerAppointment;
import com.mbiger.common.model.customerAppointment.dao.CustomerAppointmentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("customerAppointmentService")
public class CustomerAppointmentServiceImpl implements CustomerAppointmentService {
    @Autowired
    private CustomerAppointmentDao customerAppointmentDao;

    public CustomerAppointment addCustomerAppointment(CustomerAppointment customerAppointment) {
        return customerAppointmentDao.addCustomerAppointment(customerAppointment);
    }

    public void updateCustomerAppointment(CustomerAppointment customerAppointment) {
        customerAppointmentDao.updateCustomerAppointment(customerAppointment);
    }

    public CustomerAppointment getCustomerAppointmentById(Integer id) {
        return customerAppointmentDao.getCustomerAppointmentById(id);
    }

    public int countCustomerAppointmentsByParams(Map<String, Object> params) {
        return customerAppointmentDao.countCustomerAppointmentsByParams(params);
    }
}
