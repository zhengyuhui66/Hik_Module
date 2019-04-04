package com.hik.mobile.entity;

public class OfficialAcc {
	private String mAppId;
	private String mAppSecret;
	public String getmAppId() {
		return mAppId;
	}
	public void setmAppId(String mAppId) {
		this.mAppId = mAppId;
	}
	public String getmAppSecret() {
		return mAppSecret;
	}
	public void setmAppSecret(String mAppSecret) {
		this.mAppSecret = mAppSecret;
	}
	public OfficialAcc(String mAppId, String mAppSecret) {
		super();
		this.mAppId = mAppId;
		this.mAppSecret = mAppSecret;
	}
	
}
