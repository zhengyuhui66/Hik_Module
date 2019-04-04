package com.hik.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="AUDIT_USERINFO")
public class AUDIT_USERINFO {
	public AUDIT_USERINFO(){}
	public AUDIT_USERINFO(String pUSERID, String aUDIT_ID, String uSERID) {
		super();
		PUSERID = pUSERID;
		AUDIT_ID = aUDIT_ID;
		USERID = uSERID;
	}
	private String PUSERID;
	private String AUDIT_ID;
	private String USERID;
	@Id
	public String getPUSERID() {
		return PUSERID;
	}
	public void setPUSERID(String pUSERID) {
		PUSERID = pUSERID;
	}
	@Column
	public String getAUDIT_ID() {
		return AUDIT_ID;
	}
	public void setAUDIT_ID(String aUDIT_ID) {
		AUDIT_ID = aUDIT_ID;
	}
	@Column
	public String getUSERID() {
		return USERID;
	}
	public void setUSERID(String uSERID) {
		USERID = uSERID;
	}

}
