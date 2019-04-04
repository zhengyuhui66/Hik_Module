package com.hik.app.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SYS_RIGHT")
public class SYS_RIGHT  implements Serializable{
	
	
	public SYS_RIGHT(){}
	public SYS_RIGHT(String rRID, String pARENT_TR_ID, String rIGHT_NAME, String dESCRIPTION, int rOLE,
			int lEVE) {
		super();
		RRID = rRID;
		PARENT_TR_ID = pARENT_TR_ID;
		RIGHT_NAME = rIGHT_NAME;
		DESCRIPTION = dESCRIPTION;
//		ROLE = rOLE;
		LEVE = lEVE;
	}
	private String RRID;
	private String PARENT_TR_ID;
	private String RIGHT_NAME;
	private String DESCRIPTION;
//	private int ROLE;
	private int LEVE;
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	public String getRRID() {
		return RRID;
	}
	public void setRRID(String rRID) {
		RRID = rRID;
	}
	@Column(length=20,nullable=true)
	public String getPARENT_TR_ID() {
		return PARENT_TR_ID;
	}
	public void setPARENT_TR_ID(String pARENT_TR_ID) {
		PARENT_TR_ID = pARENT_TR_ID;
	}
	@Column(length=20,nullable=true)
	public String getRIGHT_NAME() {
		return RIGHT_NAME;
	}
	public void setRIGHT_NAME(String rIGHT_NAME) {
		RIGHT_NAME = rIGHT_NAME;
	}
	@Column(length=20,nullable=true)
	public String getDESCRIPTION() {
		return DESCRIPTION;
	}
	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}
//	@Column(length=20,nullable=true)
//	public int getROLE() {
//		return ROLE;
//	}
//	public void setROLE(int rOLE) {
//		ROLE = rOLE;
//	}
	@Column(length=20,nullable=true)
	public int getLEVE() {
		return LEVE;
	}
	public void setLEVE(int lEVE) {
		LEVE = lEVE;
	}
	@Override
	public String toString() {
		return "SYS_RIGHT [RRID=" + RRID + ", PARENT_TR_ID=" + PARENT_TR_ID + ", RIGHT_NAME=" + RIGHT_NAME
				+ ", DESCRIPTION=" + DESCRIPTION + ", LEVE=" + LEVE + "]";
	}
	
}
