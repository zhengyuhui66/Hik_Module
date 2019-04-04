package com.hik.mobile.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="ADVERTMODELMANGER")	
public class CONVERT_RATE implements Serializable{
	
	public CONVERT_RATE(){}
	
	public CONVERT_RATE(int accType, long cnvRate) {
		super();
		this.accType = accType;
		this.cnvRate = cnvRate;
	}
	/**
	 * 计费类型
	 */
	private int accType;
	/**
	 * 计费率
	 */
	private long cnvRate;
	public int getAccType() {
		return accType;
	}
	public void setAccType(int accType) {
		this.accType = accType;
	}
	public long getCnvRate() {
		return cnvRate;
	}
	public void setCnvRate(long cnvRate) {
		this.cnvRate = cnvRate;
	}
}
