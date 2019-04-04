package com.hik.mobile.entity;
/**
 * ϵͳ֧��/��ֵ ������Ϣ  ʵ��
 * @author Administrator
 *
 */
public class OrderInfo {
	private String orderId; // �������
	private String orderDesc;// ��������
	private float orderTotal;// �����ܶ�
	private float disRate;// �����ۿ���
	private float disNum;// �����ۿ۽��
	private float finalNum;// �������ս��
	private String orderDate;// ��������
	private int moneyType = 1;// �������� RMB 1��Dollar 2

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
