package com.alberg.jiaqi.dao;

import com.alberg.jiaqi.persistence.BuyInfo;

public interface BuyInfoDao {
	public void insert(BuyInfo buyInfo);
	public BuyInfo load(String id);
}
