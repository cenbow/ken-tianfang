package com.tianfang.user.dto;

public enum Lecturer {
	NUTRUE("否", 0), REALTRUE("是", 1);
   
	private String name;
    private int index;

    private Lecturer(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (Lecturer c : Lecturer.values()) {
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
