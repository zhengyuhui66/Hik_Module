package com.hik.mobile.entity;

/**
 * ��cpc�Ʒѣ�ת���ʾ��ǵ�����ˣ������=�����/չʾ�� 
 * ��cpm�Ʒѣ���ô�Ϳ���Чչʾ�ʣ�����չʾ��/������
 * ��cpa�Ʒѣ���ô��Ҫ�������Ч����һ����ת����Ч����/�����
 * 
 * @author Administrator
 *
 */
public enum ACCTYPE {
	BYCPT("cpt", 1),BYCPM("cpm",2),BYCPA("cpa",3);

	private String name;
	private int flag;
	
	 // ���췽��
    private ACCTYPE(String name, int flag) {
        this.name = name;
        this.flag = flag;
    }
    
    // ��ͨ����
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
