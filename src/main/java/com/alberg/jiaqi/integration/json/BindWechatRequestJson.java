package com.alberg.jiaqi.integration.json;

public class BindWechatRequestJson {
	private String WCID;
	private String USNM;
	private String CUST_NM;
	private String ID_TYPE;
	private String ID_NO;
	private String CNFM;
	private String SIGN;
	
	public String getWCID() {
		return WCID;
	}
	public void setWCID(String wCID) {
		WCID = wCID;
	}
	public String getUSNM() {
		return USNM;
	}
	public void setUSNM(String uSNM) {
		USNM = uSNM;
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
	public String getCNFM() {
		return CNFM;
	}
	public void setCNFM(String cNFM) {
		CNFM = cNFM;
	}
	public String getSIGN() {
		return SIGN;
	}
	public void setSIGN(String sIGN) {
		SIGN = sIGN;
	}
	@Override
	public String toString() {
		return "BindWechatRequestJson [WCID=" + WCID + ", USNM=" + USNM
				+ ", CUST_NM=" + CUST_NM + ", ID_TYPE=" + ID_TYPE + ", ID_NO="
				+ ID_NO + ", CNFM=" + CNFM + ", SIGN=" + SIGN + "]";
	}
}
