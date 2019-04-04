package com.hik.jpa;

import java.util.UUID;

/**
 * 条件
 * @author zyh
 *
 */
public class Condition {
	public enum Type {
		EQ, LIKE, LT, GT, LE, GE;
	}
	
	private String name;
	private Object val;
	private Type type;
	
	
	public Condition(){}
	
	public Condition(String name, Object val, Type type){
		this.name = name;
		this.val = val;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getVal() {
		return val;
	}

	public void setVal(Object val) {
		this.val = val;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	

}
