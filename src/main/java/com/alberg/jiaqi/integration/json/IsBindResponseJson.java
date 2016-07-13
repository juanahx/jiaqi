package com.alberg.jiaqi.integration.json;

public class IsBindResponseJson {
	private Integer RETCD;
	private String ERRORMSG;
	private String WCID;
	private String ID_TYPE;
	private String ID_NO;
	private String MOBILE;
	
	public Integer getRETCD() {
		return RETCD;
	}
	public void setRETCD(Integer rETCD) {
		RETCD = rETCD;
	}
	public String getERRORMSG() {
		return ERRORMSG;
	}
	public void setERRORMSG(String eRRORMSG) {
		ERRORMSG = eRRORMSG;
	}
	public String getWCID() {
		return WCID;
	}
	public void setWCID(String wCID) {
		WCID = wCID;
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
	public String getMOBILE() {
		return MOBILE;
	}
	public void setMOBILE(String mOBILE) {
		MOBILE = mOBILE;
	}
	@Override
	public String toString() {
		return "IsBindResponseJson [RETCD=" + RETCD + ", ERRORMSG=" + ERRORMSG
				+ ", WCID=" + WCID + ", ID_TYPE=" + ID_TYPE + ", ID_NO="
				+ ID_NO + ", MOBILE=" + MOBILE + "]";
	}
}
