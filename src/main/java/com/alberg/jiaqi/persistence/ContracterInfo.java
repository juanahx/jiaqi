package com.alberg.jiaqi.persistence;

import java.util.Date;

public class ContracterInfo {
	private int id;
	private String name;
	private int identifyType;
	private String identitfyNO;
	private String email;
	private Date birthday;
	private String mobile;
	private int sex;
	private String openId;
	private Date created;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIdentifyType() {
		return identifyType;
	}
	public void setIdentifyType(int identifyType) {
		this.identifyType = identifyType;
	}
	public String getIdentitfyNO() {
		return identitfyNO;
	}
	public void setIdentitfyNO(String identitfyNO) {
		this.identitfyNO = identitfyNO;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
}
