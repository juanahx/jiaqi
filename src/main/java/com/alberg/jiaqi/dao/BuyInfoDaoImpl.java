package com.alberg.jiaqi.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.alberg.jiaqi.persistence.BuyInfo;

@Repository
public class BuyInfoDaoImpl implements BuyInfoDao {

	private static final String SQL_INSERT = "INSERT INTO `buyinfo`"
			+ " (`outOrderKey`,`WCID`,`PROD_ID`,`TNS_TMSTP`,`ISSUE_DATE`,`ISSUE_TIME`,`POL_DAYS`,`POL_AMT`,`POL_PREM`,`CUST_NM`,`ID_TYPE`,`ID_NO`,`GENDER`,`BIRTHDAY`,`MOBILE`,`EMAIL`,`COUNTRY`,`CITY`,`JOB_CAT`,`CNFM`,`SIGN`,`ENT_CD`,`Created`) VALUES"
			+ " (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now());";
	
	private static final String SQL_LOAD = "select `outOrderKey`,`WCID`,`PROD_ID`,`TNS_TMSTP`,`ISSUE_DATE`,`ISSUE_TIME`,`POL_DAYS`,`POL_AMT`,`POL_PREM`,`CUST_NM`,`ID_TYPE`,`ID_NO`,`GENDER`,`BIRTHDAY`,`MOBILE`,`EMAIL`,`COUNTRY`,`CITY`,`JOB_CAT`,`CNFM`,`SIGN`,`ENT_CD`,`Created` from `buyinfo` where `outOrderKey` = ?";
	
	private JdbcTemplate jdbcTemplate;
	
	
	public void init(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public void insert(BuyInfo buyInfo) {
		if ( buyInfo != null){
			jdbcTemplate.update(SQL_INSERT, new Object[]{buyInfo.getOutOrderKey(),buyInfo.getWCID(),buyInfo.getPROD_ID(),buyInfo.getTNS_TMSTP(),buyInfo.getISSUE_DATE(),buyInfo.getISSUE_TIME(),buyInfo.getPOL_DAYS(),buyInfo.getPOL_AMT(),buyInfo.getPOL_PREM(),buyInfo.getCUST_NM(),buyInfo.getID_TYPE(),buyInfo.getID_NO(),buyInfo.getGENDER(),buyInfo.getBIRTHDAY(),buyInfo.getMOBILE(),buyInfo.getEMAIL(), buyInfo.getCOUNTRY(),buyInfo.getCITY(),buyInfo.getJOB_CAT(),buyInfo.getCNFM(),buyInfo.getSIGN(),buyInfo.getReferee()});
		}
	}

	@Override
	public BuyInfo load(String id) {
		if ( id != null ){
			return jdbcTemplate.queryForObject(SQL_LOAD,
					new Object[] { id }, new RowMapper<BuyInfo>() {
						@Override
						public BuyInfo mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							BuyInfo buyInfo = new BuyInfo();
							buyInfo.setOutOrderKey(rs.getString("outOrderKey"));
							buyInfo.setWCID(rs.getString("WCID"));
							buyInfo.setPROD_ID(rs.getString("PROD_ID"));
							buyInfo.setTNS_TMSTP(rs.getString("TNS_TMSTP"));
							buyInfo.setISSUE_DATE(rs.getString("ISSUE_DATE"));
							buyInfo.setISSUE_TIME(rs.getString("ISSUE_TIME"));
							buyInfo.setPOL_DAYS(rs.getString("POL_DAYS"));
							buyInfo.setPOL_AMT(rs.getString("POL_AMT"));
							buyInfo.setPOL_PREM(rs.getString("POL_PREM"));
							buyInfo.setCUST_NM(rs.getString("CUST_NM"));
							buyInfo.setID_TYPE(rs.getString("ID_TYPE"));
							buyInfo.setID_NO(rs.getString("ID_NO"));
							buyInfo.setGENDER(rs.getString("GENDER"));
							buyInfo.setBIRTHDAY(rs.getString("BIRTHDAY"));
							buyInfo.setMOBILE(rs.getString("MOBILE"));
							buyInfo.setEMAIL(rs.getString("EMAIL"));
							buyInfo.setCOUNTRY(rs.getString("COUNTRY"));
							buyInfo.setCITY(rs.getString("CITY"));
							buyInfo.setJOB_CAT(rs.getString("JOB_CAT"));
							buyInfo.setCNFM(rs.getString("CNFM"));
							buyInfo.setSIGN(rs.getString("SIGN"));
							buyInfo.setReferee(rs.getString("ENT_CD"));
							buyInfo.setCreated(rs.getDate("Created"));
							return buyInfo;
						}
				
			}
					);
		}
		return null;
	}

}
