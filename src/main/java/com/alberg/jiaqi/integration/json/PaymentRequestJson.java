package com.alberg.jiaqi.integration.json;

import java.sql.Timestamp;
import java.util.Date;

public class PaymentRequestJson {
	//private String ORD_ID;
	private String WCID;
	private double POL_PREM;
	private String PAY_CHANNEL;
	private String PAY_TIME;
	private String ACNT_DATE;
	private String IS_SUCCESS;
	private String SIGN;
	// 增加的字段
	private String PROD_ID;
	private String TNS_TMSTP;
	private String ISSUE_DATE;
	private String ISSUE_TIME;
	private String POL_DAYS;
	private String POL_AMT;
	private String CUST_NM;
	private String ID_TYPE;
	private String ID_NO;
	private String GENDER;
	private String BIRTHDAY;
	private String MOBILE;
	private String EMAIL;
	private String COUNTRY;
	private String CITY;
	private String JOB_CAT;	
	private String ENT_CD;
	public String getWCID() {
		return WCID;
	}
	public void setWCID(String wCID) {
		WCID = wCID;
	}
	public double getPOL_PREM() {
		return POL_PREM;
	}
	public void setPOL_PREM(double pOL_PREM) {
		POL_PREM = pOL_PREM;
	}
	public String getPAY_CHANNEL() {
		return PAY_CHANNEL;
	}
	public void setPAY_CHANNEL(String pAY_CHANNEL) {
		PAY_CHANNEL = pAY_CHANNEL;
	}
	public String getPAY_TIME() {
		return PAY_TIME;
	}
	public void setPAY_TIME(String pAY_TIME) {
		PAY_TIME = pAY_TIME;
	}
	public String getACNT_DATE() {
		return ACNT_DATE;
	}
	public void setACNT_DATE(String aCNT_DATE) {
		ACNT_DATE = aCNT_DATE;
	}
	public String getIS_SUCCESS() {
		return IS_SUCCESS;
	}
	public void setIS_SUCCESS(String iS_SUCCESS) {
		IS_SUCCESS = iS_SUCCESS;
	}
	public String getSIGN() {
		return SIGN;
	}
	public void setSIGN(String sIGN) {
		SIGN = sIGN;
	}
	public String getPROD_ID() {
		return PROD_ID;
	}
	public void setPROD_ID(String pROD_ID) {
		PROD_ID = pROD_ID;
	}
	public String getTNS_TMSTP() {
		return TNS_TMSTP;
	}
	public void setTNS_TMSTP(String tNS_TMSTP) {
		TNS_TMSTP = tNS_TMSTP;
	}
	public String getISSUE_DATE() {
		return ISSUE_DATE;
	}
	public void setISSUE_DATE(String iSSUE_DATE) {
		ISSUE_DATE = iSSUE_DATE;
	}
	public String getISSUE_TIME() {
		return ISSUE_TIME;
	}
	public void setISSUE_TIME(String iSSUE_TIME) {
		ISSUE_TIME = iSSUE_TIME;
	}
	public String getPOL_DAYS() {
		return POL_DAYS;
	}
	public void setPOL_DAYS(String pOL_DAYS) {
		POL_DAYS = pOL_DAYS;
	}
	public String getPOL_AMT() {
		return POL_AMT;
	}
	public void setPOL_AMT(String pOL_AMT) {
		POL_AMT = pOL_AMT;
	}
	public String getCUST_NM() {
		return CUST_NM;
	}
	public void setCUST_NM(String cUST_NM) {
		CUST_NM = cUST_NM;
	}
	public String getID_TYPE() {
		return ID_TYPE;
	}
	public void setID_TYPE(String iD_TYPE) {
		ID_TYPE = iD_TYPE;
	}
	public String getID_NO() {
		return ID_NO;
	}
	public void setID_NO(String iD_NO) {
		ID_NO = iD_NO;
	}
	public String getGENDER() {
		return GENDER;
	}
	public void setGENDER(String gENDER) {
		GENDER = gENDER;
	}
	public String getBIRTHDAY() {
		return BIRTHDAY;
	}
	public void setBIRTHDAY(String bIRTHDAY) {
		BIRTHDAY = bIRTHDAY;
	}
	public String getMOBILE() {
		return MOBILE;
	}
	public void setMOBILE(String mOBILE) {
		MOBILE = mOBILE;
	}
	public String getEMAIL() {
		return EMAIL;
	}
	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}
	public String getCOUNTRY() {
		return COUNTRY;
	}
	public void setCOUNTRY(String cOUNTRY) {
		COUNTRY = cOUNTRY;
	}
	public String getCITY() {
		return CITY;
	}
	public void setCITY(String cITY) {
		CITY = cITY;
	}
	public String getJOB_CAT() {
		return JOB_CAT;
	}
	public void setJOB_CAT(String jOB_CAT) {
		JOB_CAT = jOB_CAT;
	}
	
	public String getENT_CD() {
		return ENT_CD;
	}
	public void setENT_CD(String eNT_CD) {
		ENT_CD = eNT_CD;
	}
	@Override
	public String toString() {
		return "PaymentRequestJson [WCID=" + WCID + ", POL_PREM=" + POL_PREM
				+ ", PAY_CHANNEL=" + PAY_CHANNEL + ", PAY_TIME=" + PAY_TIME
				+ ", ACNT_DATE=" + ACNT_DATE + ", IS_SUCCESS=" + IS_SUCCESS
				+ ", SIGN=" + SIGN + ", PROD_ID=" + PROD_ID + ", TNS_TMSTP="
				+ TNS_TMSTP + ", ISSUE_DATE=" + ISSUE_DATE + ", ISSUE_TIME="
				+ ISSUE_TIME + ", POL_DAYS=" + POL_DAYS + ", POL_AMT="
				+ POL_AMT + ", CUST_NM=" + CUST_NM + ", ID_TYPE=" + ID_TYPE
				+ ", ID_NO=" + ID_NO + ", GENDER=" + GENDER + ", BIRTHDAY="
				+ BIRTHDAY + ", MOBILE=" + MOBILE + ", EMAIL=" + EMAIL
				+ ", COUNTRY=" + COUNTRY + ", CITY=" + CITY + ", JOB_CAT="
				+ JOB_CAT + ", ENT_CD=" + ENT_CD + "]";
	}
}
