package com.hik.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="SYS_ROLE")
public class SYS_ROLE {
public SYS_ROLE(){}
public SYS_ROLE(String trid, String pid, String name, String description, String create_time) {
		super();
		this.trid = trid;
		this.pid = pid;
		this.name = name;
		this.description = description;
		this.create_time = create_time;
	}
@Override
	public String toString() {
		return "SYS_ROLE [trid=" + trid + ", pid=" + pid + ", name=" + name + ", description=" + description
				+ ", create_time=" + create_time + "]";
	}
private String trid;
private String pid;
private String name;
private String description;
private String create_time;
@Id
public String getTrid() {
	return trid;
}
public void setTrid(String trid) {
	this.trid = trid;
}
@Column(length=100,nullable=true)
public String getPid() {
	return pid;
}
public void setPid(String pid) {
	this.pid = pid;
}
@Column(length=100,nullable=true)
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
@Column(length=300,nullable=true)
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
@Column(length=300,nullable=true)
public String getCreate_time() {
	return create_time;
}
public void setCreate_time(String create_time) {
	this.create_time = create_time;
}

}
