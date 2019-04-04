package com.hik.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="AUDIT_INFO")	
public class AUDIT_INFO {
	private String AUDIT_ID;
	private String MATER_ID;
	private String AUDIT_PROCE;
	private String AUDIT_STIME;
	private String AUDIT_STATE;
	private String AUDIT_DESC;
	private String AUDIT_ETIME;
	private String AUDIT_RESULT;
	private String USER_ID;
	public AUDIT_INFO(){}
	public AUDIT_INFO(String aUDIT_ID, String mATER_ID, String aUDIT_PROCE, String aUDIT_STIME, String aUDIT_STATE,
			String aUDIT_DESC, String aUDIT_ETIME, String aUDIT_RESULT, String uSER_ID){
		super();
		AUDIT_ID = aUDIT_ID;
		MATER_ID = mATER_ID;
		AUDIT_PROCE = aUDIT_PROCE;
		AUDIT_STIME = aUDIT_STIME;
		AUDIT_STATE = aUDIT_STATE;
		AUDIT_DESC = aUDIT_DESC;
		AUDIT_ETIME = aUDIT_ETIME;
		AUDIT_RESULT = aUDIT_RESULT;
		USER_ID = uSER_ID;
	}
	@Override
	public String toString() {
		return "AUDIT_INFO [AUDIT_ID=" + AUDIT_ID + ", MATER_ID=" + MATER_ID + ", AUDIT_PROCE=" + AUDIT_PROCE
				+ ", AUDIT_STIME=" + AUDIT_STIME + ", AUDIT_STATE=" + AUDIT_STATE + ", AUDIT_DESC=" + AUDIT_DESC
				+ ", AUDIT_ETIME=" + AUDIT_ETIME + ", AUDIT_RESULT=" + AUDIT_RESULT + ", USER_ID=" + USER_ID
				+ ", getAUDIT_ID()=" + getAUDIT_ID() + ", getMATER_ID()=" + getMATER_ID() + ", getAUDIT_PROCE()="
				+ getAUDIT_PROCE() + ", getAUDIT_STIME()=" + getAUDIT_STIME() + ", getAUDIT_STATE()=" + getAUDIT_STATE()
				+ ", getAUDIT_DESC()=" + getAUDIT_DESC() + ", getAUDIT_ETIME()=" + getAUDIT_ETIME()
				+ ", getAUDIT_RESULT()=" + getAUDIT_RESULT() + ", getUSER_ID()=" + getUSER_ID() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	@Id
	public String getAUDIT_ID() {
		return AUDIT_ID;
	}
	public void setAUDIT_ID(String aUDIT_ID) {
		AUDIT_ID = aUDIT_ID;
	}
	@Column
	public String getMATER_ID() {
		return MATER_ID;
	}
	public void setMATER_ID(String mATER_ID) {
		MATER_ID = mATER_ID;
	}
	@Column
	public String getAUDIT_PROCE() {
		return AUDIT_PROCE;
	}
	public void setAUDIT_PROCE(String aUDIT_PROCE) {
		AUDIT_PROCE = aUDIT_PROCE;
	}
	@Column
	public String getAUDIT_STIME() {
		return AUDIT_STIME;
	}
	public void setAUDIT_STIME(String aUDIT_STIME) {
		AUDIT_STIME = aUDIT_STIME;
	}
	@Column
	public String getAUDIT_STATE() {
		return AUDIT_STATE;
	}
	public void setAUDIT_STATE(String aUDIT_STATE) {
		AUDIT_STATE = aUDIT_STATE;
	}
	@Column
	public String getAUDIT_DESC() {
		return AUDIT_DESC;
	}
	public void setAUDIT_DESC(String aUDIT_DESC) {
		AUDIT_DESC = aUDIT_DESC;
	}
	@Column
	public String getAUDIT_ETIME() {
		return AUDIT_ETIME;
	}
	public void setAUDIT_ETIME(String aUDIT_ETIME) {
		AUDIT_ETIME = aUDIT_ETIME;
	}
	@Column
	public String getAUDIT_RESULT() {
		return AUDIT_RESULT;
	}
	public void setAUDIT_RESULT(String aUDIT_RESULT) {
		AUDIT_RESULT = aUDIT_RESULT;
	}
	@Column
	public String getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}
	
}
