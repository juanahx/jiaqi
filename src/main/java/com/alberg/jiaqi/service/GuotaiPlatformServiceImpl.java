package com.alberg.jiaqi.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alberg.jiaqi.dao.BuyInfoDao;
import com.alberg.jiaqi.dao.ContracterInfoDao;
import com.alberg.jiaqi.dao.GuotaiPlatformDao;
import com.alberg.jiaqi.exceptions.UserAlreadyBindedException;
import com.alberg.jiaqi.integration.json.InsuranceBuyRequestJson;
import com.alberg.jiaqi.persistence.BuyInfo;
import com.alberg.jiaqi.persistence.ContracterInfo;
import com.alberg.jiaqi.util.UUIDGenerator;

@Service
public class GuotaiPlatformServiceImpl implements GuotaiPlatformService{
	
	private static final Logger logger = LogManager.getLogger(GuotaiPlatformServiceImpl.class);
	
	@Autowired
	private GuotaiPlatformDao guotaiPlatformDao;
	@Autowired
	private ContracterInfoDao contracterInfoDao;
	@Autowired
	private BuyInfoDao buyInfoDao;
	
	@Override
	public void userBind(String openId, String userId)
			throws UserAlreadyBindedException {
		guotaiPlatformDao.insertUserBind(openId, userId);
		
	}

	@Override
	public boolean isUserBind(String openId, String userId) {
		return guotaiPlatformDao.isUserBind(openId, userId);
	}

	@Override
	public String getUserIdByOpenId(String openId) {
		return guotaiPlatformDao.getUserIdByOpenId(openId);
	}

	@Override
	public ContracterInfo queryContracterInfoByOpenId(String openId) {
		if ( openId == null || openId.trim().equals("") ){
			logger.error("[queryContracterInfoByOpenId] openId is null or empty");
			return null; 
		}
		logger.info("openId = " + openId);
		return contracterInfoDao.queryInfoByOpenId(openId);
	}

	@Override
	public String saveBuyInfo(InsuranceBuyRequestJson buyInfo) {
		if (buyInfo != null){
			BuyInfo buy = new BuyInfo();
			buy.setBIRTHDAY(buyInfo.getBIRTHDAY());
			buy.setCITY(buyInfo.getCITY());
			buy.setCNFM(buyInfo.getCNFM());
			buy.setCOUNTRY(buyInfo.getCOUNTRY());
			buy.setCUST_NM(buyInfo.getCUST_NM());
			buy.setEMAIL(buyInfo.getEMAIL());
			buy.setGENDER(buyInfo.getGENDER());
			buy.setID_NO(buyInfo.getID_NO());
			buy.setID_TYPE(buyInfo.getID_TYPE());
			buy.setISSUE_DATE(buyInfo.getISSUE_DATE());
			buy.setISSUE_TIME(buyInfo.getISSUE_TIME());
			buy.setJOB_CAT(buyInfo.getJOB_CAT());
			buy.setMOBILE(buyInfo.getMOBILE());
			String viewId = UUIDGenerator.getUUID();
			buy.setOutOrderKey(viewId);
			buy.setPOL_AMT(buyInfo.getPOL_AMT());
			buy.setPOL_DAYS(buyInfo.getPOL_DAYS());
			buy.setPOL_PREM(buyInfo.getPOL_PREM());
			buy.setPROD_ID(buyInfo.getPROD_ID());
			buy.setSIGN(buyInfo.getSIGN());
			buy.setTNS_TMSTP(buyInfo.getTNS_TMSTP());
			buy.setWCID(buyInfo.getWCID());
			buy.setReferee(buyInfo.getReferee());
			buy.setEMAIL(buyInfo.getEMAIL());
			logger.info("buy info =" + buy);
			buyInfoDao.insert(buy);
			return viewId;
		}
		
		return null;
	}

	@Override
	public InsuranceBuyRequestJson queryBuyInfoByViewId(String viewId) {
		BuyInfo buyInfo = buyInfoDao.load(viewId);
		if ( buyInfo != null ){
			InsuranceBuyRequestJson buy = new InsuranceBuyRequestJson();
			buy.setBIRTHDAY(buyInfo.getBIRTHDAY());
			buy.setCITY(buyInfo.getCITY());
			buy.setCNFM(buyInfo.getCNFM());
			buy.setCOUNTRY(buyInfo.getCOUNTRY());
			buy.setCUST_NM(buyInfo.getCUST_NM());
			buy.setEMAIL(buyInfo.getEMAIL());
			buy.setGENDER(buyInfo.getGENDER());
			buy.setID_NO(buyInfo.getID_NO());
			buy.setID_TYPE(buyInfo.getID_TYPE());
			buy.setISSUE_DATE(buyInfo.getISSUE_DATE());
			buy.setISSUE_TIME(buyInfo.getISSUE_TIME());
			buy.setJOB_CAT(buyInfo.getJOB_CAT());
			buy.setMOBILE(buyInfo.getMOBILE());
			buy.setPOL_AMT(buyInfo.getPOL_AMT());
			buy.setPOL_DAYS(buyInfo.getPOL_DAYS());
			buy.setPOL_PREM(buyInfo.getPOL_PREM());
			buy.setPROD_ID(buyInfo.getPROD_ID());
			buy.setSIGN(buyInfo.getSIGN());
			buy.setTNS_TMSTP(buyInfo.getTNS_TMSTP());
			buy.setWCID(buyInfo.getWCID());
			buy.setReferee(buyInfo.getReferee());
			return buy;
		}
		return null;
	}

}
