package com.hik.mobile.entity;
/**
 * 系统支付/充值 订单信息  实体
 * @author Administrator
 *
 */
public class OrderInfo {
	private String orderId; // 订单编号
	private String orderDesc;// 订单描述
	private float orderTotal;// 订单总额
	private float disRate;// 订单折扣率
	private float disNum;// 订单折扣金额
	private float finalNum;// 订单最终金额
	private String orderDate;// 订单日期
	private int moneyType = 1;// 货币类型 RMB 1，Dollar 2

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderDesc() {
		return orderDesc;
	}

	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}

	public float getOrderTotal() {
		return orderTotal;
	}

	public void setOrderTotal(float orderTotal) {
		this.orderTotal = orderTotal;
	}

	public float getDisRate() {
		return disRate;
	}

	public void setDisRate(float disRate) {
		this.disRate = disRate;
	}

	public float getDisNum() {
		return disNum;
	}

	public void setDisNum(float disNum) {
		this.disNum = disNum;
	}

	public float getFinalNum() {
		return finalNum;
	}

	public void setFinalNum(float finalNum) {
		this.finalNum = finalNum;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public int getMoneyType() {
		return moneyType;
	}

	public void setMoneyType(int moneyType) {
		this.moneyType = moneyType;
	}

	public OrderInfo(String orderDesc, float orderTotal, float disRate, String orderid, int moneyType) {
		this.orderDesc = orderDesc;
		this.orderTotal = orderTotal;
		this.disRate = disRate;
		this.moneyType = moneyType;
		this.orderId = orderid;
		this.disNum = disRate * orderTotal;
		this.finalNum = orderTotal - disNum;
	}

}
