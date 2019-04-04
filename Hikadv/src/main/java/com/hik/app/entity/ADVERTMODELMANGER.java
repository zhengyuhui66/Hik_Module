package com.hik.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="ADVERTMODELMANGER")	
public class ADVERTMODELMANGER {
	
public ADVERTMODELMANGER(){}



private String ID;
private String NAME;
private String USERID;
private String URL;
private String CREATETIME;
private String UPDATETIME;
private double CYCID;
private String DESCRIPTION;
private String FILENAME;
private String MODELSKIN;
private String ADVPOSID1;
private String ADVPOSID2;
private String ADVPOSID3;
private String ADVPOSID4;
private String ADVPOSID5;
private String ADVPOSNAME1;
private String ADVPOSNAME2;
private String ADVPOSNAME3;
private String ADVPOSNAME4;
private String ADVPOSNAME5;
public ADVERTMODELMANGER(String iD, String nAME, String uSERID, String uRL, String cREATETIME, String uPDATETIME,double cYCID,
		String dESCRIPTION, String fILENAME, String mODELSKIN, String aDVPOSID1, String aDVPOSID2, String aDVPOSID3,
		String aDVPOSID4, String aDVPOSID5, String aDVPOSNAME1, String aDVPOSNAME2, String aDVPOSNAME3,
		String aDVPOSNAME4, String aDVPOSNAME5) {
	super();
	ID = iD;
	NAME = nAME;
	USERID = uSERID;
	URL = uRL;
	CREATETIME = cREATETIME;
	UPDATETIME = uPDATETIME;
	CYCID=cYCID;
	DESCRIPTION = dESCRIPTION;
	FILENAME = fILENAME;
	MODELSKIN = mODELSKIN;
	ADVPOSID1 = aDVPOSID1;
	ADVPOSID2 = aDVPOSID2;
	ADVPOSID3 = aDVPOSID3;
	ADVPOSID4 = aDVPOSID4;
	ADVPOSID5 = aDVPOSID5;
	ADVPOSNAME1 = aDVPOSNAME1;
	ADVPOSNAME2 = aDVPOSNAME2;
	ADVPOSNAME3 = aDVPOSNAME3;
	ADVPOSNAME4 = aDVPOSNAME4;
	ADVPOSNAME5 = aDVPOSNAME5;
}
@Id
public String getID() {
	return ID;
}
public void setID(String iD) {
	ID = iD;
}
@Column
public String getNAME() {
	return NAME;
}
public void setNAME(String nAME) {
	NAME = nAME;
}
@Column
public String getUSERID() {
	return USERID;
}
public void setUSERID(String uSERID) {
	USERID = uSERID;
}
@Column
public String getURL() {
	return URL;
}
public void setURL(String uRL) {
	URL = uRL;
}
@Column
public String getCREATETIME() {
	return CREATETIME;
}
public void setCREATETIME(String cREATETIME) {
	CREATETIME = cREATETIME;
}
@Column
public String getUPDATETIME() {
	return UPDATETIME;
}
public void setUPDATETIME(String uPDATETIME) {
	UPDATETIME = uPDATETIME;
}
@Column
public double getCYCID() {
	return CYCID;
}
public void setCYCID(double cYCID) {
	CYCID = cYCID;
}
@Column
public String getDESCRIPTION() {
	return DESCRIPTION;
}
public void setDESCRIPTION(String dESCRIPTION) {
	DESCRIPTION = dESCRIPTION;
}
@Column
public String getFILENAME() {
	return FILENAME;
}
public void setFILENAME(String fILENAME) {
	FILENAME = fILENAME;
}
@Column
public String getMODELSKIN() {
	return MODELSKIN;
}
public void setMODELSKIN(String mODELSKIN) {
	MODELSKIN = mODELSKIN;
}
@Column
public String getADVPOSID1() {
	return ADVPOSID1;
}
public void setADVPOSID1(String aDVPOSID1) {
	ADVPOSID1 = aDVPOSID1;
}
@Column
public String getADVPOSID2() {
	return ADVPOSID2;
}
public void setADVPOSID2(String aDVPOSID2) {
	ADVPOSID2 = aDVPOSID2;
}
@Column
public String getADVPOSID3() {
	return ADVPOSID3;
}
public void setADVPOSID3(String aDVPOSID3) {
	ADVPOSID3 = aDVPOSID3;
}
@Column
public String getADVPOSID4() {
	return ADVPOSID4;
}
public void setADVPOSID4(String aDVPOSID4) {
	ADVPOSID4 = aDVPOSID4;
}
@Column
public String getADVPOSID5() {
	return ADVPOSID5;
}
public void setADVPOSID5(String aDVPOSID5) {
	ADVPOSID5 = aDVPOSID5;
}
@Column
public String getADVPOSNAME1() {
	return ADVPOSNAME1;
}
public void setADVPOSNAME1(String aDVPOSNAME1) {
	ADVPOSNAME1 = aDVPOSNAME1;
}
@Column
public String getADVPOSNAME2() {
	return ADVPOSNAME2;
}
public void setADVPOSNAME2(String aDVPOSNAME2) {
	ADVPOSNAME2 = aDVPOSNAME2;
}
@Column
public String getADVPOSNAME3() {
	return ADVPOSNAME3;
}
public void setADVPOSNAME3(String aDVPOSNAME3) {
	ADVPOSNAME3 = aDVPOSNAME3;
}
@Column
public String getADVPOSNAME4() {
	return ADVPOSNAME4;
}
public void setADVPOSNAME4(String aDVPOSNAME4) {
	ADVPOSNAME4 = aDVPOSNAME4;
}
@Column
public String getADVPOSNAME5() {
	return ADVPOSNAME5;
}
public void setADVPOSNAME5(String aDVPOSNAME5) {
	ADVPOSNAME5 = aDVPOSNAME5;
}
@Override
public String toString() {
	return "ADVERTMODELMANGER [ID=" + ID + ", NAME=" + NAME + ", USERID=" + USERID + ", URL=" + URL + ", CREATETIME="
			+ CREATETIME + ", UPDATETIME=" + UPDATETIME + ", DESCRIPTION=" + DESCRIPTION + ", FILENAME=" + FILENAME
			+ ", MODELSKIN=" + MODELSKIN + ", ADVPOSID1=" + ADVPOSID1 + ", ADVPOSID2=" + ADVPOSID2 + ", ADVPOSID3="
			+ ADVPOSID3 + ", ADVPOSID4=" + ADVPOSID4 + ", ADVPOSID5=" + ADVPOSID5 + ", ADVPOSNAME1=" + ADVPOSNAME1
			+ ", ADVPOSNAME2=" + ADVPOSNAME2 + ", ADVPOSNAME3=" + ADVPOSNAME3 + ", ADVPOSNAME4=" + ADVPOSNAME4
			+ ", ADVPOSNAME5=" + ADVPOSNAME5 + "]";
}

}
