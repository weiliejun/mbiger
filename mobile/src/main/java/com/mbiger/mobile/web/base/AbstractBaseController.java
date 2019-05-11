package com.mbiger.mobile.web.base;

import com.mbiger.common.model.user.bean.UserInfo;
import com.mbiger.common.redis.service.CacheService;
import com.mbiger.mobile.service.userManage.UserInfoService;
import com.mbiger.common.web.SessionUser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;

/**
  * @Description:  controller基类
  */
public abstract class AbstractBaseController {
	private static final Log logger = LogFactory.getLog(AbstractBaseController.class);

	@Autowired
	protected CacheService cacheService;

	@Autowired
	protected UserInfoService userInfoService;

	/**
	 * 根据sessionId获取CurrentUser
	 */
	protected SessionUser getSessionUserBySid(HttpServletRequest request) {
		String sid = request.getSession().getId();
		return userInfoService.getSessionUserBySid(sid);
	}

	/**
	 * 根据sessionId获取UserInfo
	 */
	protected UserInfo getUserInfoBySid(HttpServletRequest request) {
		SessionUser sessionUser = getSessionUserBySid(request);
		if(sessionUser != null){
			return sessionUser.getUserInfo();
		}
		return null;
	}
	/**
	 * 根据userId获取CurrentUser
	 */
	protected SessionUser getSessionUserByUid(Integer uid) {
		return userInfoService.getSessionUserByUid(uid);
	}




}
