package com.hik.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SHOW_COUNT_LOG")	
public class SHOW_COUNT_LOG {
	
	
	
	public SHOW_COUNT_LOG() {
		super();
	}
	public SHOW_COUNT_LOG(String id, String show_time, String busid, String apmac, String phone, String phonemac,
			String modelid, String modelmodeid, String materid, String advert_url,String advertid,String lineid) {
		super();
		this.id = id;
		this.show_time = show_time;
		this.busid = busid;
		this.apmac = apmac;
		this.phone = phone;
		this.phonemac = phonemac;
		this.modelid = modelid;
		this.modelmodeid = modelmodeid;
		this.materid = materid;
		this.advert_url = advert_url;
		this.advertid=advertid;
		this.lineid=lineid;
	}
	private String id;
	private String show_time;
	private String busid;
	private String apmac;
	private String phone;
	private String phonemac;
	private String modelid;
	private String modelmodeid;
	private String materid;
	private String advert_url;
	private String advertid;
	private String lineid;
	@Column(length=20,nullable=true)
	public String getAdvertid() {
		return advertid;
	}
	public void setAdvertid(String advertid) {
		this.advertid = advertid;
	}
	@Id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(length=20,nullable=true)
	public String getShow_time() {
		return show_time;
	}
	public void setShow_time(String show_time) {
		this.show_time = show_time;
	}
	@Column(length=20,nullable=true)
	public String getBusid() {
		return busid;
	}
	@Column(length=20,nullable=true)
	public void setBusid(String busid) {
		this.busid = busid;
	}
	public String getApmac() {
		return apmac;
	}
	@Column(length=20,nullable=true)
	public void setApmac(String apmac) {
		this.apmac = apmac;
	}
	public String getPhone() {
		return phone;
	}
	@Column(length=20,nullable=true)
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPhonemac() {
		return phonemac;
	}
	@Column(length=20,nullable=true)
	public void setPhonemac(String phonemac) {
		this.phonemac = phonemac;
	}
	public String getModelid() {
		return modelid;
	}
	public void setModelid(String modelid) {
		this.modelid = modelid;
	}
	@Column(length=20,nullable=true)
	public String getModelmodeid() {
		return modelmodeid;
	}
	public void setModelmodeid(String modelmodeid) {
		this.modelmodeid = modelmodeid;
	}
	@Column(length=20,nullable=true)
	public String getMaterid() {
		return materid;
	}
	public void setMaterid(String materid) {
		this.materid = materid;
	}
	@Column(length=20,nullable=true)
	public String getAdvert_url() {
		return advert_url;
	}
	public void setAdvert_url(String advert_url) {
		this.advert_url = advert_url;
	}
	@Column(length=20,nullable=true)
	public String getLineid() {
		return lineid;
	}
	public void setLineid(String lineid) {
		this.lineid = lineid;
	}
	
	
}
