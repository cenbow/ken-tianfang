package com.tianfang.user.dto;


/**
 * 教练级别
 * 
 * @author 
 *
 * 
 */
public enum Notifications {
	phone("手机", 1), email("邮箱", 2),noprompt("不提醒",3);
   
	private String name;
    private int index;

    private Notifications(String name, int index) {
        this.name = name;
        this.index = index;
    }
    
    public static String getName(int index) {
        for (Notifications c : Notifications.values()) {
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