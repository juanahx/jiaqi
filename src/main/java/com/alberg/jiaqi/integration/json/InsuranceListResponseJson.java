package com.alberg.jiaqi.integration.json;

public class InsuranceListResponseJson {
	private int RETCD;
	private String ERRMSG;
	private InsuranceDetailJson[] POL_LIST;
	
	public int getRETCD() {
		return RETCD;
	}
	public void setRETCD(int rETCD) {
		RETCD = rETCD;
	}
	public String getERRMSG() {
		return ERRMSG;
	}
	public void setERRMSG(String eRRMSG) {
		ERRMSG = eRRMSG;
	}
	public InsuranceDetailJson[] getPOL_LIST() {
		return POL_LIST;
	}
	public void setPOL_LIST(InsuranceDetailJson[] pOL_LIST) {
		POL_LIST = pOL_LIST;
	}

	
}
