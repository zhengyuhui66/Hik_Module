package com.hik.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="MATERIEL_INFO")	
public class MATERIEL_INFO {
	
public MATERIEL_INFO(){}

public MATERIEL_INFO(String mATER_ID, String cUSTOMER_ID, String mATER_NAME, String mATER_SIZE, String mATER_TYPE,
		String cREATE_TIME, String uPDATE_TIME, String mATER_PATH, String mATER_STATE, String mATER_WIDTH,
		String mATER_HEIGHT) {
	super();
	MATER_ID = mATER_ID;
	CUSTOMER_ID = cUSTOMER_ID;
	MATER_NAME = mATER_NAME;
	MATER_SIZE = mATER_SIZE;
	MATER_TYPE = mATER_TYPE;
	CREATE_TIME = cREATE_TIME;
	UPDATE_TIME = uPDATE_TIME;
	MATER_PATH = mATER_PATH;
	MATER_STATE = mATER_STATE;
	MATER_WIDTH = mATER_WIDTH;
	MATER_HEIGHT = mATER_HEIGHT;
}

@Override
public String toString() {
	return "MATERIEL_INFO [MATER_ID=" + MATER_ID + ", CUSTOMER_ID=" + CUSTOMER_ID + ", MATER_NAME=" + MATER_NAME
			+ ", MATER_SIZE=" + MATER_SIZE + ", MATER_TYPE=" + MATER_TYPE + ", CREATE_TIME=" + CREATE_TIME
			+ ", UPDATE_TIME=" + UPDATE_TIME + ", MATER_PATH=" + MATER_PATH + ", MATER_STATE=" + MATER_STATE
			+ ", MATER_WIDTH=" + MATER_WIDTH + ", MATER_HEIGHT=" + MATER_HEIGHT + "]";
}

private String MATER_ID;
private String CUSTOMER_ID;
private String MATER_NAME;
private String MATER_SIZE;
private String MATER_TYPE;
private String CREATE_TIME;
private String UPDATE_TIME;
private String MATER_PATH;
private String MATER_STATE;
private String MATER_WIDTH;
private String MATER_HEIGHT;
@Column(length=20,nullable=true)
public String getMATER_WIDTH() {
	return MATER_WIDTH;
}
public void setMATER_WIDTH(String mATER_WIDTH) {
	MATER_WIDTH = mATER_WIDTH;
}

public String getMATER_HEIGHT() {
	return MATER_HEIGHT;
}

public void setMATER_HEIGHT(String mATER_HEIGHT) {
	MATER_HEIGHT = mATER_HEIGHT;
}

@Id
public String getMATER_ID() {
	return MATER_ID;
}
public void setMATER_ID(String mATER_ID) {
	MATER_ID = mATER_ID;
}
@Column(length=20,nullable=true)
public String getCUSTOMER_ID() {
	return CUSTOMER_ID;
}
public void setCUSTOMER_ID(String cUSTOMER_ID) {
	CUSTOMER_ID = cUSTOMER_ID;
}
@Column(length=20,nullable=true)
public String getMATER_NAME() {
	return MATER_NAME;
}
public void setMATER_NAME(String mATER_NAME) {
	MATER_NAME = mATER_NAME;
}
@Column(length=20,nullable=true)
public String getMATER_SIZE() {
	return MATER_SIZE;
}
public void setMATER_SIZE(String mATER_SIZE) {
	MATER_SIZE = mATER_SIZE;
}
@Column(length=20,nullable=true)
public String getMATER_TYPE() {
	return MATER_TYPE;
}
public void setMATER_TYPE(String mATER_TYPE) {
	MATER_TYPE = mATER_TYPE;
}
@Column(length=20,nullable=true)
public String getCREATE_TIME() {
	return CREATE_TIME;
}
public void setCREATE_TIME(String cREATE_TIME) {
	CREATE_TIME = cREATE_TIME;
}
public String getUPDATE_TIME() {
	return UPDATE_TIME;
}
public void setUPDATE_TIME(String uPDATE_TIME) {
	UPDATE_TIME = uPDATE_TIME;
}
@Column(length=20,nullable=true)
public String getMATER_PATH() {
	return MATER_PATH;
}
public void setMATER_PATH(String mATER_PATH) {
	MATER_PATH = mATER_PATH;
}
@Column(length=20,nullable=true)
public String getMATER_STATE() {
	return MATER_STATE;
}
public void setMATER_STATE(String mATER_STATE) {
	MATER_STATE = mATER_STATE;
}


}
