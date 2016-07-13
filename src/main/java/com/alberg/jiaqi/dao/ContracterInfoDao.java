package com.alberg.jiaqi.dao;

import com.alberg.jiaqi.persistence.ContracterInfo;

/**
 * @author Administrator
 *
 */
public interface ContracterInfoDao {
	/**
	 * @param info
	 */
	void insertOrUpdateInfo(ContracterInfo info);
	/**
	 * @param id
	 */
	ContracterInfo queryInfoByOpenId(String openId);
}
