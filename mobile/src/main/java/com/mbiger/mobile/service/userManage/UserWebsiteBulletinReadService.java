package com.mbiger.mobile.service.userManage;

import com.mbiger.common.model.userWebsiteBulletinRead.bean.UserWebsiteBulletinRead;

import java.util.Map;

public interface UserWebsiteBulletinReadService {

    void addUserWebsiteBulletinRead(UserWebsiteBulletinRead userWebsiteBulletinRead);

    UserWebsiteBulletinRead getUserWebsiteBulletinReadById(Integer id);

    UserWebsiteBulletinRead getUserWebsiteBulletinReadByBulletinId(Integer bulletinId, Integer userId);

    void updateUserWebsiteBulletinRead(UserWebsiteBulletinRead record);

    int countUserWebsiteBulletinReadByParam(Map<String, Object> params);


}