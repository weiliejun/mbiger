package com.mbiger.mobile.service.infoPublishManage;

import com.mbiger.common.model.websiteBulletin.bean.WebsiteBulletin;
import com.mbiger.common.model.websiteBulletin.dao.WebsiteBulletinDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("websiteBulletinService")
public class WebsiteBulletinServiceImpl implements WebsiteBulletinService {

    @Autowired
    private WebsiteBulletinDao websiteBulletinDao;

    public void addWebsiteBulletin(WebsiteBulletin websiteBulletin) {
        websiteBulletinDao.addWebsiteBulletin(websiteBulletin);
    }

    public WebsiteBulletin getWebsiteBulletinById(Integer id) {
        return websiteBulletinDao.getWebsiteBulletinById(id);
    }

    public void updateWebsiteBulletin(WebsiteBulletin websiteBulletin) {
        websiteBulletinDao.updateWebsiteBulletin(websiteBulletin);
    }

    public int countWebsiteBulletinsByParams(Map<String, Object> params) {
        return websiteBulletinDao.countWebsiteBulletinsByParams(params);
    }

    public List<Map<String, Object>> listWebsiteBulletinsByParams(Map<String, Object> params) {
        return websiteBulletinDao.listWebsiteBulletinsByParams(params);
    }
}
