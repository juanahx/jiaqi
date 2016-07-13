package com.alberg.jiaqi.dao;

import java.sql.Types;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.alberg.jiaqi.persistence.ContracterInfo;

@Repository
public class ContracterInfoDaoImpl implements ContracterInfoDao {

	private static final String SQL_SELECT_BASE = "select Id, Name, IdentifyType, IdentitfyNO, Email, Birthday, Mobile, Sex, OpenId, Created from `contracterinfo`";
	private static final String SQL_QUERY_BYOPENID = SQL_SELECT_BASE + " where openId =?";
	private static final String SQL_INSERT = "INSERT INTO `guotai`.`contracterinfo`"
			+ " (`Name`,`IdentifyType`,`IdentitfyNO`,`Email`,`Birthday`,`Mobile`,`Sex`,`OpenId`,`Created`) VALUES"
			+ " (?,?,?,?,?,?,?,?,now());";
	
	private static final String SQL_UPADTE = "UPDATE `guotai`.`contracterinfo`"
							+ " SET "
							+ " `Name` = ?,`IdentifyType` = ?,`IdentitfyNO` = ?,`Email` = ?,`Birthday` = ?,`Mobile` = ?,`Sex` = ?,`OpenId` = ? "
							+ " WHERE Id = ? ";
	
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void insertOrUpdateInfo(ContracterInfo info) {
		if ( info != null ){
			// 检查是否有openId
			ContracterInfo existsInfo = queryInfoByOpenId(info.getOpenId());
			// 没有，新建
			if (existsInfo == null){
				jdbcTemplate.update(SQL_INSERT, new Object[]{info.getName(),info.getIdentifyType(), info.getIdentitfyNO(), info.getEmail(),info.getBirthday(), info.getMobile(), info.getSex(), info.getOpenId()}
					,new int[]{Types.VARCHAR, Types.INTEGER, Types.VARCHAR, Types.VARCHAR,Types.TIMESTAMP, Types.VARCHAR, Types.INTEGER, Types.VARCHAR});
			} 
			// 有，更新
			else {
				jdbcTemplate.update(SQL_UPADTE, new Object[]{info.getName(),info.getIdentifyType(), info.getIdentitfyNO(), info.getEmail(),info.getBirthday(), info.getMobile(), info.getSex(), info.getOpenId(), info.getId()}
					,new int[]{Types.VARCHAR, Types.INTEGER, Types.VARCHAR, Types.VARCHAR,Types.TIMESTAMP, Types.VARCHAR, Types.INTEGER, Types.VARCHAR, Types.INTEGER});
			}
		}
	}

	@Override
	public ContracterInfo queryInfoByOpenId(String openId) {
		try {
			return jdbcTemplate.queryForObject(SQL_QUERY_BYOPENID,
					new Object[] { openId }, ContracterInfo.class);	
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
