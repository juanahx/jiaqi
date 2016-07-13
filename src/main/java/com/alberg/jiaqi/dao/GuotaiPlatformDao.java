package com.alberg.jiaqi.dao;

import com.alberg.jiaqi.exceptions.UserAlreadyBindedException;

public interface GuotaiPlatformDao {

	public abstract void insertUserBind(String openId, String userId)
			throws UserAlreadyBindedException;

	public abstract boolean isUserBind(String openId, String userId);

	public abstract String getUserIdByOpenId(String openId);

}