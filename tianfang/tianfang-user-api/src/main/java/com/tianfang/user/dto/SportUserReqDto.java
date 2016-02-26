package com.tianfang.user.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class SportUserReqDto implements Serializable{
    
	private static final long serialVersionUID = 984109228310769418L;

	@Setter
    @Getter
    private String id;

    @Setter
    @Getter
    private String email;

    @Setter
    @Getter
    private String mobile;

    @Setter
    @Getter
    private String password;

    @Setter
    @Getter
    private String nickName;

    @Setter
    @Getter
    private String cname;
    
    @Setter
    @Getter
    private String ename;

    @Setter
    @Getter
    private Integer trainerLevel;
    
    @Setter
    @Getter
    private Integer gender;

    @Setter
    @Getter
    private Integer utype;  //身份类型：1，普通会员；2，教练；3，队长；4，裁判

    @Setter
    @Getter
    private String papersPic;

    @Setter
    @Getter
    private String location;
    
    @Setter
    @Getter
    private String area;

    @Setter
    @Getter
    private String position;

    @Setter
    @Getter
    private String pic;

    @Setter
    @Getter
    private String microPic;

    @Setter
    @Getter
    private String description;

    @Setter
    @Getter
    private Integer visibleStat;
    
    @Setter
    @Getter
    private Date lastLoginTime;
    
    @Setter
    @Getter
    private String lastLoginTimeStr;  //展示上次登录时间

    @Setter
    @Getter
    private Date lastUpdateTime;

    @Setter
    @Getter
    private Date createTime;

    @Setter
    @Getter
    private Integer stat;
    
    @Setter
    @Getter
    private String qq;    //第三方登录类型：QQ
    
    @Setter
    @Getter
    private String weixin; //第三方登录类型:微信
    
    @Setter
    @Getter
    private String sina; //第三方登录类型:新浪
    
    @Setter
    @Getter
    private String userAccount; //登录时的用户账号
    
    @Setter
    @Getter
    private String province;   //省份
    
    @Setter
    @Getter
    private String telephone;   //电话

    @Setter
    @Getter
    private String postcode;  //邮编

    @Setter
    @Getter
    private String detailedAddress;  //地址详情
    
    @Setter
    @Getter
    private Integer audit; //审核状态:1表示通过,0表示未通过
    
    
    @Setter
    @Getter
    private Integer lecturer; //设置是否为主讲人 1 0
    
    @Setter
    @Getter
    private String content; //同 description

}