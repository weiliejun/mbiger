package com.mbiger.pc.service.infoPublishManage;

import com.mbiger.common.model.websiteBulletin.bean.WebsiteBulletin;

import java.util.List;
import java.util.Map;

public interface WebsiteBulletinService {

    void addWebsiteBulletin(WebsiteBulletin websiteBulletin);

    WebsiteBulletin getWebsiteBulletinById(Integer id);

    void updateWebsiteBulletin(WebsiteBulletin websiteBulletin);

    int countWebsiteBulletinsByParams(Map<String, Object> params);

    public List<Map<String, Object>> listWebsiteBulletinsByParams(Map<String, Object> params);

}