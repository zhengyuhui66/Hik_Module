package com.hik.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="DEVICE")	
public class DEVICE {
	private String id;
	private String equipmentid;
	private String apmac;
	private String vehicleid;
	private String isonline;
	private String lastonlinetime;
	private String lastreporttime;
	private String reporttime;
	private String state;
	private String creator;
	private String modifier;
	private String createtime;
	private String modifytime;
	private String mark;
	private String var;
	private String speed;
	private String timeout;
	
	@Id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(length=20,nullable=true)
	public String getEquipmentid() {
		return equipmentid;
	}
	public void setEquipmentid(String equipmentid) {
		this.equipmentid = equipmentid;
	}
	
	@Column(length=20,nullable=true)
	public String getApmac() {
		return apmac;
	}
	public void setApmac(String apmac) {
		this.apmac = apmac;
	}
	
	@Column(length=20,nullable=true)
	public String getVehicleid() {
		return vehicleid;
	}
	public void setVehicleid(String vehicleid) {
		this.vehicleid = vehicleid;
	}
	@Column(length=20,nullable=true)
	public String getIsonline() {
		return isonline;
	}
	public void setIsonline(String isonline) {
		this.isonline = isonline;
	}
	@Column(length=20,nullable=true)
	public String getLastonlinetime() {
		return lastonlinetime;
	}
	public void setLastonlinetime(String lastonlinetime) {
		this.lastonlinetime = lastonlinetime;
	}
	
	@Column(length=20,nullable=true)
	public String getLastreporttime() {
		return lastreporttime;
	}
	public void setLastreporttime(String lastreporttime) {
		this.lastreporttime = lastreporttime;
	}
	
	@Column(length=20,nullable=true)
	public String getReporttime() {
		return reporttime;
	}
	public void setReporttime(String reporttime) {
		this.reporttime = reporttime;
	}
	
	@Column(length=20,nullable=true)
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	@Column(length=20,nullable=true)
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	@Column(length=20,nullable=true)
	public String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	
	@Column(length=20,nullable=true)
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	
	@Column(length=20,nullable=true)
	public String getModifytime() {
		return modifytime;
	}
	public void setModifytime(String modifytime) {
		this.modifytime = modifytime;
	}
	
	@Column(length=20,nullable=true)
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	
	@Column(length=20,nullable=true)
	public String getVar() {
		return var;
	}
	public void setVar(String var) {
		this.var = var;
	}
	
	@Column(length=20,nullable=true)
	public String getSpeed() {
		return speed;
	}
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	
	@Column(length=20,nullable=true)
	public String getTimeout() {
		return timeout;
	}
	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}
	public DEVICE(String id, String equipmentid, String apmac, String vehicleid, String isonline, String lastonlinetime,
			String lastreporttime, String reporttime, String state, String creator, String modifier, String createtime,
			String modifytime, String mark, String var, String speed, String timeout) {
		super();
		this.id = id;
		this.equipmentid = equipmentid;
		this.apmac = apmac;
		this.vehicleid = vehicleid;
		this.isonline = isonline;
		this.lastonlinetime = lastonlinetime;
		this.lastreporttime = lastreporttime;
		this.reporttime = reporttime;
		this.state = state;
		this.creator = creator;
		this.modifier = modifier;
		this.createtime = createtime;
		this.modifytime = modifytime;
		this.mark = mark;
		this.var = var;
		this.speed = speed;
		this.timeout = timeout;
	}
	
	
	
//	public DEVICE(String id, String equipmentid, String apmac, String vehicleid, String lastonlinetime,
//			String lastreporttime, String reporttime, String state, String creator, String modifier, String createtime,
//			String modifytime, String mark, String var, String speed, String timeout) {
//		super();
//		this.id = id;
//		this.equipmentid = equipmentid;
//		this.apmac = apmac;
//		this.vehicleid = vehicleid;
//		this.lastonlinetime = lastonlinetime;
//		this.lastreporttime = lastreporttime;
//		this.reporttime = reporttime;
//		this.state = state;
//		this.creator = creator;
//		this.modifier = modifier;
//		this.createtime = createtime;
//		this.modifytime = modifytime;
//		this.mark = mark;
//		this.var = var;
//		this.speed = speed;
//		this.timeout = timeout;
//	}
	
	
	
//	@Id
//	public String getId() {
//		return id;
//	}
//	public void setId(String id) {
//		this.id = id;
//	}
//	
//	@Column(length=20,nullable=true)
//	public String getName() {
//		return name;
//	}
//	public void setName(String name) {
//		this.name = name;
//	}

	
}
