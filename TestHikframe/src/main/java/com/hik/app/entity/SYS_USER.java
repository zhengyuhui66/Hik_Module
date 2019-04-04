package com.hik.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="SYS_USER")
public class SYS_USER {
	private int USERID;
	private String PWORD;
	private String USER_NAME;
	private String TELPHONE;
	private String EMAIL;
	private String CREATE_TIME;
	private String LOGIN_TIME;
	private String LAST_LOGIN_TIME;
	private String LOGIN_TIMES;
	private String DESCRIPTION;
	private String ISLOGOUT;
public SYS_USER(){}
	public SYS_USER(int uSERID, String pWORD, String uSER_NAME, String tELPHONE, String eMAIL,
			String cREATE_TIME, String lOGIN_TIME, String lAST_LOGIN_TIME, String lOGIN_TIMES, String dESCRIPTION,String ISLOGOUT) {
		super();
		USERID = uSERID;
		PWORD = pWORD;
		USER_NAME = uSER_NAME;
		TELPHONE = tELPHONE;
		EMAIL = eMAIL;
		CREATE_TIME = cREATE_TIME;
		LOGIN_TIME = lOGIN_TIME;
		LAST_LOGIN_TIME = lAST_LOGIN_TIME;
		LOGIN_TIMES = lOGIN_TIMES;
		DESCRIPTION = dESCRIPTION;
		ISLOGOUT=ISLOGOUT;
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getUSERID() {
		return USERID;
	}
	public void setUSERID(int uSERID) {
		USERID = uSERID;
	}
	@Column(length=20,nullable=true)
	public String getPWORD() {
		return PWORD;
	}
	
	public void setPWORD(String pWORD) {
		PWORD = pWORD;
	}
	@Column(length=20,nullable=true)
	public String getUSER_NAME() {
		return USER_NAME;
	}
	public void setUSER_NAME(String uSER_NAME) {
		USER_NAME = uSER_NAME;
	}
	@Column(length=20,nullable=true)
	public String getTELPHONE() {
		return TELPHONE;
	}
	public void setTELPHONE(String tELPHONE) {
		TELPHONE = tELPHONE;
	}
	@Column(length=20,nullable=true)
	public String getEMAIL() {
		return EMAIL;
	}
	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}
	@Column(length=20,nullable=true)
	public String getCREATE_TIME() {
		return CREATE_TIME;
	}
	public void setCREATE_TIME(String cREATE_TIME) {
		CREATE_TIME = cREATE_TIME;
	}
	@Column(length=20,nullable=true)
	public String getLOGIN_TIME() {
		return LOGIN_TIME;
	}
	public void setLOGIN_TIME(String lOGIN_TIME) {
		LOGIN_TIME = lOGIN_TIME;
	}
	@Column(length=20,nullable=true)
	public String getLAST_LOGIN_TIME() {
		return LAST_LOGIN_TIME;
	}
	public void setLAST_LOGIN_TIME(String lAST_LOGIN_TIME) {
		LAST_LOGIN_TIME = lAST_LOGIN_TIME;
	}
	@Column(length=20,nullable=true)
	public String getLOGIN_TIMES() {
		return LOGIN_TIMES;
	}
	public void setLOGIN_TIMES(String lOGIN_TIMES) {
		LOGIN_TIMES = lOGIN_TIMES;
	}
	@Column(length=30,nullable=true)
	public String getDESCRIPTION() {
		return DESCRIPTION;
	}
	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}
	@Column(length=10,nullable=true)
	public String getISLOGOUT() {
		return ISLOGOUT;
	}
	public void setISLOGOUT(String iSLOGOUT) {
		ISLOGOUT = iSLOGOUT;
	}
	
	
	
}
