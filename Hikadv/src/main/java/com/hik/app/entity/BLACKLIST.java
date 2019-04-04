package com.hik.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="BLACKLIST")	
public class BLACKLIST {
	private String id;
	private String phone;
	private String creator;
	private String modifier;
	private String createtime;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
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
	public BLACKLIST(String id, String phone, String creator, String modifier, String createtime, String modifytime,
			String mark) {
		super();
		this.id = id;
		this.phone = phone;
		this.creator = creator;
		this.modifier = modifier;
		this.createtime = createtime;
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
