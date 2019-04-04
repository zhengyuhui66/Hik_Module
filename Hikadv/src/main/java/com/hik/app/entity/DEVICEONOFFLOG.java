package com.hik.app.entity;

public class DEVICEONOFFLOG {
	private String id;
	private String deviceid;
	private String onlinetime;
	private String offlinetime;
	private String alarmtype;
	private String isoffline;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	public String getOnlinetime() {
		return onlinetime;
	}
	public void setOnlinetime(String onlinetime) {
		this.onlinetime = onlinetime;
	}
	public String getOfflinetime() {
		return offlinetime;
	}
	public void setOfflinetime(String offlinetime) {
		this.offlinetime = offlinetime;
	}
	public String getAlarmtype() {
		return alarmtype;
	}
	public void setAlarmtype(String alarmtype) {
		this.alarmtype = alarmtype;
	}
	public String getIsoffline() {
		return isoffline;
	}
	public void setIsoffline(String isoffline) {
		this.isoffline = isoffline;
	}
	
	
	
}
