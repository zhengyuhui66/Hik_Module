package com.hik.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="VEHICLE")
public class VEHICLE {
	
	private String ID;
	public VEHICLE(){
		super();
	}
	
	public VEHICLE(String iD, String cODE, String rOUTENAME, String bRAND, String mODEL, String cOLOR, String uSEDATE,
			String sTATE, String cREATOR, String mODIFIER, String cREATETIME, String mODIFYTIME, String mARK,
			String oWERCODE, String rOUTEID, String cORPID, String cORPNAME, String iFADVERT) {
		super();
		ID = iD;
		CODE = cODE;
		ROUTENAME = rOUTENAME;
		BRAND = bRAND;
		MODEL = mODEL;
		COLOR = cOLOR;
		USEDATE = uSEDATE;
		STATE = sTATE;
		CREATOR = cREATOR;
		MODIFIER = mODIFIER;
		CREATETIME = cREATETIME;
		MODIFYTIME = mODIFYTIME;
		MARK = mARK;
		OWERCODE = oWERCODE;
		ROUTEID = rOUTEID;
		CORPID = cORPID;
		CORPNAME = cORPNAME;
		IFADVERT = iFADVERT;
	}
	private String CODE;
	@Id
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	@Column
	public String getCODE() {
		return CODE;
	}
	public void setCODE(String cODE) {
		CODE = cODE;
	}
	@Column
	public String getROUTENAME() {
		return ROUTENAME;
	}
	public void setROUTENAME(String rOUTENAME) {
		ROUTENAME = rOUTENAME;
	}
	@Column
	public String getBRAND() {
		return BRAND;
	}
	public void setBRAND(String bRAND) {
		BRAND = bRAND;
	}
	@Column
	public String getMODEL() {
		return MODEL;
	}
	public void setMODEL(String mODEL) {
		MODEL = mODEL;
	}
	@Column
	public String getCOLOR() {
		return COLOR;
	}
	public void setCOLOR(String cOLOR) {
		COLOR = cOLOR;
	}
	@Column
	public String getUSEDATE() {
		return USEDATE;
	}
	public void setUSEDATE(String uSEDATE) {
		USEDATE = uSEDATE;
	}
	@Column
	public String getSTATE() {
		return STATE;
	}
	public void setSTATE(String sTATE) {
		STATE = sTATE;
	}
	@Column
	public String getCREATOR() {
		return CREATOR;
	}
	public void setCREATOR(String cREATOR) {
		CREATOR = cREATOR;
	}
	@Column
	public String getMODIFIER() {
		return MODIFIER;
	}
	public void setMODIFIER(String mODIFIER) {
		MODIFIER = mODIFIER;
	}
	@Column
	public String getCREATETIME() {
		return CREATETIME;
	}
	public void setCREATETIME(String cREATETIME) {
		CREATETIME = cREATETIME;
	}
	@Column
	public String getMODIFYTIME() {
		return MODIFYTIME;
	}
	public void setMODIFYTIME(String mODIFYTIME) {
		MODIFYTIME = mODIFYTIME;
	}
	@Column
	public String getMARK() {
		return MARK;
	}
	public void setMARK(String mARK) {
		MARK = mARK;
	}
	@Column
	public String getOWERCODE() {
		return OWERCODE;
	}
	public void setOWERCODE(String oWERCODE) {
		OWERCODE = oWERCODE;
	}
	@Column
	public String getROUTEID() {
		return ROUTEID;
	}
	public void setROUTEID(String rOUTEID) {
		ROUTEID = rOUTEID;
	}
	@Column
	public String getCORPID() {
		return CORPID;
	}
	public void setCORPID(String cORPID) {
		CORPID = cORPID;
	}
	@Column
	public String getCORPNAME() {
		return CORPNAME;
	}
	public void setCORPNAME(String cORPNAME) {
		CORPNAME = cORPNAME;
	}
	@Column
	public String getIFADVERT() {
		return IFADVERT;
	}
	public void setIFADVERT(String iFADVERT) {
		IFADVERT = iFADVERT;
	}
	private String ROUTENAME;
	private String BRAND;
	private String MODEL;
	private String COLOR;
	private String USEDATE;
	private String STATE;
	private String CREATOR;
	private String MODIFIER;
	private String CREATETIME;	
	private String MODIFYTIME;
	private String MARK;
	private String OWERCODE;
	private String ROUTEID;
	private String CORPID;
	private String CORPNAME;
	private String IFADVERT;
}
