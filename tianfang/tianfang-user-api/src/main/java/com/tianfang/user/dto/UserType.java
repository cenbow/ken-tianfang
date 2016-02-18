package com.tianfang.user.dto;

/**
 * 用户身份类型
 * 
 * @author xiang_wang
 *
 * 2015年11月30日上午10:05:17
 */
public enum UserType {
	DEFAULT("普通会员", 1), COACH("教练", 2), TEAMLEADER("队长", 3),REFEREE("裁判", 4);
   
	private String name;
    private int index;

    private UserType(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (UserType c : UserType.values()) {
            if (c.getIndex() == index) {
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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}