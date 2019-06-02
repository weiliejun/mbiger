package com.mbiger.admin.service;

import com.mbiger.common.model.user.bean.UserInfo;
import com.mbiger.common.model.user.dao.UserInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Demo1Service {
    @Autowired
    private UserInfoDao userInfoDao;

    public UserInfo findUserInfo(Integer uid) {
        UserInfo userInfo = userInfoDao.getUserInfoById(uid);
        return userInfo;
    }
}
