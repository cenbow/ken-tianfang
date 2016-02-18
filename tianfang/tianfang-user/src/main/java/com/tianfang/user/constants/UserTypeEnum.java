package com.tianfang.user.constants;

public enum UserTypeEnum {
    NORMALUSER(1,"普通会员"),
    CAPTAINUSER(2,"教练"),
    SITEUSER(3,"队长"),
    COACHUSER(4,"裁判");
    
    private int index;

    private String typeName;

    UserTypeEnum(int index, String typeName) {
        this.index = index;
        this.typeName = typeName;
    }

    public int getIndex(){
        return index;
    }
    public String getTypeName(){
        return typeName;
    }
}
