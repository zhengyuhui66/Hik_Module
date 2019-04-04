package com.hik.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ROUTE")	
public class ROUTE {
	private String id;
	private String name;
	private String state;
	private String creator;
	private String creatime;
	private String modifier;
	private String modifytime;
	private String mark;
	
	@Id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(length=20,nullable=true)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getCreatime() {
		return creatime;
	}
	public void setCreatime(String creatime) {
		this.creatime = creatime;
	}
	
	@Column(length=20,nullable=true)
	public String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier) {
		this.modifier = modifier;
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
	public ROUTE(String id, String name, String state, String creator, String creatime, String modifier,
			String modifytime, String mark) {
		super();
		this.id = id;
		this.name = name;
		this.state = state;
		this.creator = creator;
		this.creatime = creatime;
		this.modifier = modifier;
		this.modifytime = modifytime;
		this.mark = mark;
	}
	
	
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
