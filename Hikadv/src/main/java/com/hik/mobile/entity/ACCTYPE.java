package com.hik.mobile.entity;

/**
 * 按cpc计费，转化率就是点击率了，点击率=点击数/展示数 
 * 按cpm计费，那么就看有效展示率，就是展示数/请求数
 * 按cpa计费，那么需要看点击到效果这一步的转化，效果数/点击数
 * 
 * @author Administrator
 *
 */
public enum ACCTYPE {
	BYCPT("cpt", 1),BYCPM("cpm",2),BYCPA("cpa",3);

	private String name;
	private int flag;
	
	 // 构造方法
    private ACCTYPE(String name, int flag) {
        this.name = name;
        this.flag = flag;
    }
    
    // 普通方法
    public static String getName(int index) {
        for (ACCTYPE c : ACCTYPE.values()) {
            if (c.getFlag() == index) {
                return c.name;
            }
        }
        return null;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
}
