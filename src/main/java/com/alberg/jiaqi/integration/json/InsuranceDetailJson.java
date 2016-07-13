package com.alberg.jiaqi.integration.json;

public class InsuranceDetailJson {
	private int orderid;
	private String PROD_NAME;
	private String ISSUE_DATE;
	private String END_DATE;
	private int POL_AMT;
	private double POL_PREM;
	private String CUST_NM;
	private String ID_TYPE;
	private String ID_NO;
	private String GENDER;
	private String BIRTHDAY;
	
	public String getPROD_NAME() {
		return PROD_NAME;
	}
	public void setPROD_NAME(String pROD_NAME) {
		PROD_NAME = pROD_NAME;
	}
	public String getISSUE_DATE() {
		return ISSUE_DATE;
	}
	public void setISSUE_DATE(String iSSUE_DATE) {
		ISSUE_DATE = iSSUE_DATE;
	}
	public String getEND_DATE() {
		return END_DATE;
	}
	public void setEND_DATE(String eND_DATE) {
		END_DATE = eND_DATE;
	}
	public int getPOL_AMT() {
		return POL_AMT;
	}
	public void setPOL_AMT(int pOL_AMT) {
		POL_AMT = pOL_AMT;
	}
	public double getPOL_PREM() {
		return POL_PREM;
	}
	public void setPOL_PREM(double pOL_PREM) {
		POL_PREM = pOL_PREM;
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
	public int getOrderid() {
		return orderid;
	}
	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}
}
