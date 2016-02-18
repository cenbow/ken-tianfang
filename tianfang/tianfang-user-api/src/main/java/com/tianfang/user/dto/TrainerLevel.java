package com.tianfang.user.dto;


/**
 * 教练级别
 * 
 * @author xiang_wang
 *
 * 2015年11月30日上午10:06:36
 */
public enum TrainerLevel {
	TOP("顶级教练", 0), DEFAULT("教练", 1),VIP("VIP", 2);
   
	private String name;
    private int index;

    private TrainerLevel(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (TrainerLevel c : TrainerLevel.values()) {
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