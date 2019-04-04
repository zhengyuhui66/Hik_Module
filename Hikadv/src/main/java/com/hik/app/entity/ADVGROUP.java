package com.hik.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="ADVGROUP")	
public class ADVGROUP {
	
public ADVGROUP(){}

private String id;
private String advid1;
private String advid2;
private String advid3;
private String advid4;
private String advid5;
private String advid6;
private String advid7;
private String advid8;
private String advid9;
private String creator;
private String creatime;
private String modifier;
private String modifytime;
private String playstrager;
//private String playpristrager;
private String descr;
private String name;
public ADVGROUP(String iD,  String advid1, String advid2, String advid3, String advid4, String advid5,
		String advid6, String advid7, String advid8, String advid9, String creator, String creatime, String modifier,
		String modifytime, String playstrager/*, String playpristrager*/, String descr, String name) {
	super();
	id = iD;
	this.advid1 = advid1;
	this.advid2 = advid2;
	this.advid3 = advid3;
	this.advid4 = advid4;
	this.advid5 = advid5;
	this.advid6 = advid6;
	this.advid7 = advid7;
	this.advid8 = advid8;
	this.advid9 = advid9;
	this.creator = creator;
	this.creatime = creatime;
	this.modifier = modifier;
	this.modifytime = modifytime;
	this.playstrager = playstrager;
//	this.playpristrager = playpristrager;
	this.descr = descr;
	this.name = name;
}
@Id
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
@Column
public String getAdvid1() {
	return advid1;
}
public void setAdvid1(String advid1) {
	this.advid1 = advid1;
}
@Column
public String getAdvid2() {
	return advid2;
}
public void setAdvid2(String advid2) {
	this.advid2 = advid2;
}
@Column
public String getAdvid3() {
	return advid3;
}
public void setAdvid3(String advid3) {
	this.advid3 = advid3;
}
@Column
public String getAdvid4() {
	return advid4;
}
public void setAdvid4(String advid4) {
	this.advid4 = advid4;
}
@Column
public String getAdvid5() {
	return advid5;
}
public void setAdvid5(String advid5) {
	this.advid5 = advid5;
}
@Column
public String getAdvid6() {
	return advid6;
}
public void setAdvid6(String advid6) {
	this.advid6 = advid6;
}
@Column
public String getAdvid7() {
	return advid7;
}
public void setAdvid7(String advid7) {
	this.advid7 = advid7;
}
@Column
public String getAdvid8() {
	return advid8;
}
public void setAdvid8(String advid8) {
	this.advid8 = advid8;
}
@Column
public String getAdvid9() {
	return advid9;
}
public void setAdvid9(String advid9) {
	this.advid9 = advid9;
}
@Column
public String getCreator() {
	return creator;
}
public void setCreator(String creator) {
	this.creator = creator;
}
@Column
public String getCreatime() {
	return creatime;
}
public void setCreatime(String creatime) {
	this.creatime = creatime;
}
@Column
public String getModifier() {
	return modifier;
}
public void setModifier(String modifier) {
	this.modifier = modifier;
}
@Column
public String getModifytime() {
	return modifytime;
}
public void setModifytime(String modifytime) {
	this.modifytime = modifytime;
}
@Column
public String getPlaystrager() {
	return playstrager;
}
public void setPlaystrager(String playstrager) {
	this.playstrager = playstrager;
}
//@Column
//public String getPlaypristrager() {
//	return playpristrager;
//}
//public void setPlaypristrager(String playpristrager) {
//	this.playpristrager = playpristrager;
//}
@Column
public String getDescr() {
	return descr;
}
public void setDescr(String descr) {
	this.descr = descr;
}
@Column
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}



}
