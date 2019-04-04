package com.hik.app.entity;

import javax.persistence.Column;
import javax.persistence.Id;

public class WechatManger {
	
  private String id;
  private String name;
  private String wechatid;
  private String type;
  private String introduce;
  private String identify;
  private String phone;
  private String address;
  private String maininfo;
  private String owner;
  private String email;
  private String orgid;
  private String appid;
  private String appsecret;          
  private String accesstoken;        
  private String creator;            
  private String creatime;           
  private String modifyer;          
  private String modifytime;
  private String usreid;
  private String propertys;
  private String property;
  
	public WechatManger(String id, String name, String wechatid, String type, String introduce, String identify,
		String phone, String address, String maininfo, String owner, String email, String orgid, String appid,
		String appsecret, String accesstoken, String creator, String creatime, String modifyer, String modifytime,String user_id,String propertys,String property) {
	super();
	this.id = id;
	this.name = name;
	this.wechatid = wechatid;
	this.type = type;
	this.introduce = introduce;
	this.identify = identify;
	this.phone = phone;
	this.address = address;
	this.maininfo = maininfo;
	this.owner = owner;
	this.email = email;
	this.orgid = orgid;
	this.appid = appid;
	this.appsecret = appsecret;
	this.accesstoken = accesstoken;
	this.creator = creator;
	this.creatime = creatime;
	this.modifyer = modifyer;
	this.modifytime = modifytime;
	this.usreid=user_id;
	this.propertys=propertys;
	this.property=property;
}
	@Id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(length=500,nullable=true)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(length=500,nullable=true)
	public String getWechatid() {
		return wechatid;
	}
	public void setWechatid(String wechatid) {
		this.wechatid = wechatid;
	}
	
	@Column(length=500,nullable=true)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Column(length=500,nullable=true)
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	
	@Column(length=500,nullable=true)
	public String getIdentify() {
		return identify;
	}
	public void setIdentify(String identify) {
		this.identify = identify;
	}
	
	@Column(length=500,nullable=true)
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Column(length=500,nullable=true)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Column(length=500,nullable=true)
	public String getMaininfo() {
		return maininfo;
	}
	public void setMaininfo(String maininfo) {
		this.maininfo = maininfo;
	}
	
	@Column(length=500,nullable=true)
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	@Column(length=500,nullable=true)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(length=500,nullable=true)
	public String getOrgid() {
		return orgid;
	}
	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}
	
	@Column(length=500,nullable=true)
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	
	@Column(length=500,nullable=true)
	public String getAppsecret() {
		return appsecret;
	}
	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}
	
	@Column(length=500,nullable=true)
	public String getAccesstoken() {
		return accesstoken;
	}
	public void setAccesstoken(String accesstoken) {
		this.accesstoken = accesstoken;
	}
	
	@Column(length=500,nullable=true)
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	@Column(length=500,nullable=true)
	public String getCreatime() {
		return creatime;
	}
	public void setCreatime(String creatime) {
		this.creatime = creatime;
	}
	
	@Column(length=500,nullable=true)
	public String getModifyer() {
		return modifyer;
	}
	public void setModifyer(String modifyer) {
		this.modifyer = modifyer;
	}
	
	@Column(length=500,nullable=true)
	public String getModifytime() {
		return modifytime;
	}
	public void setModifytime(String modifytime) {
		this.modifytime = modifytime;
	}
	
	@Column(length=500,nullable=true)
	public String getUsreid() {
		return usreid;
	}
	public void setUsreid(String usreid) {
		this.usreid = usreid;
	}
	
	@Column(length=500,nullable=true)
	public String getPropertys() {
		return propertys;
	}
	public void setPropertys(String propertys) {
		this.propertys = propertys;
	}
	
	@Column(length=500,nullable=true)
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}  
	  
	  
}
