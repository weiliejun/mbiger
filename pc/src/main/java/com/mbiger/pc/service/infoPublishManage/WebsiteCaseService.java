package com.mbiger.pc.service.infoPublishManage;

import com.mbiger.common.model.websiteCase.bean.WebsiteCase;

import java.util.List;
import java.util.Map;

public interface WebsiteCaseService {

    WebsiteCase addCase(WebsiteCase record);

    int updateCase(WebsiteCase record);

    WebsiteCase getCaseById(Integer id);

    List<WebsiteCase> listCasesByParams(Map<String, String> params);


}