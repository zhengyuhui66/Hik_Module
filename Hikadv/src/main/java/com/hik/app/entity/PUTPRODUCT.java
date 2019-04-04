package com.hik.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="PUTPRODUCT")	
public class PUTPRODUCT {
	
public PUTPRODUCT(){}
private String id; 
private String proname;
private String modelid;
private String advposid1;
private String advposid2;
private String advposid3;
private String advposid4;
private String advposid5;
private String advgroup1;
private String advgroup2;
private String advgroup3;
private String advgroup4;
private String advgroup5;
private String createtime; 
private String creator;
private String modifier; 
private String modifytime; 
private String isdelete; 
private String descr;


public PUTPRODUCT(String id, String proname,String modelid, String advposid1, String advposid2, String advposid3, String advposid4,
		String advposid5, String advgroup1, String advgroup2, String advgroup3, String advgroup4, String advgroup5,
		String createtime, String creator, String modifier, String modifytime, String isdelete, String descr) {
	super();
	this.id = id;
	this.proname=proname;
	this.modelid = modelid;
	this.advposid1 = advposid1;
	this.advposid2 = advposid2;
	this.advposid3 = advposid3;
	this.advposid4 = advposid4;
	this.advposid5 = advposid5;
	this.advgroup1 = advgroup1;
	this.advgroup2 = advgroup2;
	this.advgroup3 = advgroup3;
	this.advgroup4 = advgroup4;
	this.advgroup5 = advgroup5;
	this.createtime = createtime;
	this.creator = creator;
	this.modifier = modifier;
	this.modifytime = modifytime;
	this.isdelete = isdelete;
	this.descr = descr;
}

@Id
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
@Column(length=20,nullable=true)
public String getProname() {
	return proname;
}
public void setProname(String proname) {
	this.proname = proname;
}
@Column(length=20,nullable=true)
public String getModelid() {
	return modelid;
}
public void setModelid(String modelid) {
	this.modelid = modelid;
}

@Column(length=20,nullable=true)
public String getAdvposid1() {
	return advposid1;
}
public void setAdvposid1(String advposid1) {
	this.advposid1 = advposid1;
}
@Column(length=20,nullable=true)
public String getAdvposid2() {
	return advposid2;
}
public void setAdvposid2(String advposid2) {
	this.advposid2 = advposid2;
}
@Column(length=20,nullable=true)
public String getAdvposid3() {
	return advposid3;
}
public void setAdvposid3(String advposid3) {
	this.advposid3 = advposid3;
}
@Column(length=20,nullable=true)
public String getAdvposid4() {
	return advposid4;
}
public void setAdvposid4(String advposid4) {
	this.advposid4 = advposid4;
}
@Column(length=20,nullable=true)
public String getAdvposid5() {
	return advposid5;
}
public void setAdvposid5(String advposid5) {
	this.advposid5 = advposid5;
}
@Column(length=20,nullable=true)
public String getAdvgroup1() {
	return advgroup1;
}
public void setAdvgroup1(String advgroup1) {
	this.advgroup1 = advgroup1;
}
@Column(length=20,nullable=true)
public String getAdvgroup2() {
	return advgroup2;
}
public void setAdvgroup2(String advgroup2) {
	this.advgroup2 = advgroup2;
}
@Column(length=20,nullable=true)
public String getAdvgroup3() {
	return advgroup3;
}
public void setAdvgroup3(String advgroup3) {
	this.advgroup3 = advgroup3;
}
@Column(length=20,nullable=true)
public String getAdvgroup4() {
	return advgroup4;
}
public void setAdvgroup4(String advgroup4) {
	this.advgroup4 = advgroup4;
}
@Column(length=20,nullable=true)
public String getAdvgroup5() {
	return advgroup5;
}
public void setAdvgroup5(String advgroup5) {
	this.advgroup5 = advgroup5;
}
@Column(length=20,nullable=true)
public String getCreatetime() {
	return createtime;
}
public void setCreatetime(String createtime) {
	this.createtime = createtime;
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
public String getModifytime() {
	return modifytime;
}
public void setModifytime(String modifytime) {
	this.modifytime = modifytime;
}
@Column(length=20,nullable=true)
public String getIsdelete() {
	return isdelete;
}
public void setIsdelete(String isdelete) {
	this.isdelete = isdelete;
}
@Column(length=20,nullable=true)
public String getDescr() {
	return descr;
}
public void setDescr(String descr) {
	this.descr = descr;
}


}
