package com.alberg.jiaqi.service;

import com.alberg.jiaqi.exceptions.UserAlreadyBindedException;
import com.alberg.jiaqi.integration.json.InsuranceBuyRequestJson;
import com.alberg.jiaqi.persistence.ContracterInfo;

public interface GuotaiPlatformService {
	public void userBind(String openId, String userId) throws UserAlreadyBindedException;
	public boolean isUserBind(String openId, String userId);
	public String getUserIdByOpenId(String openId);
	public ContracterInfo queryContracterInfoByOpenId(String openId);
	public String saveBuyInfo(InsuranceBuyRequestJson buyInfo);
	public InsuranceBuyRequestJson queryBuyInfoByViewId(String viewId);
}
