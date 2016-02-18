package com.tianfang.business.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class PlayerDto implements Serializable {

	@Getter
	@Setter
    private Integer plyNo;//序号

	@Getter
	@Setter
    private String plyUuid;//唯一标识

	@Getter
	@Setter

    private String plyId;

	@Getter
	@Setter

    private String plyImei;//快速登录用设备号
	
	@Getter
	@Setter
    private String plyUid;//登录名

	@Getter
	@Setter
    private String plyPwd;//密码（密文）

	@Getter
	@Setter
    private String plyNick;

	@Getter
	@Setter
    private String plyMobile;

	@Getter
	@Setter
    private String plyEmail;

	@Getter
	@Setter
    private String plyWeixin;

	@Getter
	@Setter
    private String plyQq;

	@Getter
	@Setter
    private String plySignature;

	@Getter
	@Setter
    private String plyFullname;

	@Getter
	@Setter
    private Integer plyGender;

	@Getter
	@Setter
    private String plyBirthday;

	@Getter
	@Setter
    private String plyNation;

	@Getter
	@Setter
    private String plyIdno;

	@Getter
	@Setter
    private String plyAddress;

	@Getter
	@Setter
    private String plyAddresszip;

	@Getter
	@Setter
    private String plyStartdate;

	@Getter
	@Setter
    private Date plyEnddate;

	@Getter
	@Setter
    private Integer plyStyle;

	@Getter
	@Setter
    private Integer plyState;

	@Getter
	@Setter
    private Integer plyLevel;

	@Getter
	@Setter
    private Double plyMoneymax;

	@Getter
	@Setter
    private Double plyMoney;

	@Getter
	@Setter
    private Integer plyCoin;

	@Getter
	@Setter
    private String plyVerifycode;

	@Getter
	@Setter
    private String plyCar;

	@Getter
	@Setter
    private String plyUserid;

	@Getter
	@Setter
    private String plyChannelid;

	@Getter
	@Setter
    private String plyImage;

	@Getter
	@Setter
    private Double plyGivemoney;

}