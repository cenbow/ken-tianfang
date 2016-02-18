package com.tianfang.home.dto;

import java.io.Serializable;

public enum RaceStatus implements Serializable{
    UNSTART("比赛未开始", 0), START("比赛进行中", 1), END("比赛结束", 2);
    // 成员变量
    private String name;
    private int index;

    // 构造方法
    private RaceStatus(String name, int index) {
        this.name = name;
        this.index = index;
    }

    // 普通方法
    public static String getName(int index) {
        for (RaceStatus c : RaceStatus.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    // get set 方法
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
