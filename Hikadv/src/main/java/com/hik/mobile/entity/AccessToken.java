package com.hik.mobile.entity;

public class AccessToken {
	private String access_token;
	private int Expires_in;
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public int getExpires_in() {
		return Expires_in;
	}
	public void setExpires_in(int expires_in) {
		Expires_in = expires_in;
	}
	public AccessToken(String access_token, int expires_in) {
		super();
		this.access_token = access_token;
		Expires_in = expires_in;
	}
}
