package com.tianfang.business.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class SportTeamDto implements Serializable {
	
	private static final long serialVersionUID = 2008253694436496579L;

	@Getter
	@Setter
    private String id;

	@Getter
	@Setter
    private String gameId;

	@Getter
	@Setter
    private String gameStr;
	
	@Getter
	@Setter
    private String name;

	@Getter
	@Setter
    private String logo;

	@Getter
	@Setter
    private String contact;

	@Getter
	@Setter
    private String distruct;
	
	@Getter
	@Setter
    private String distructStr;

	@Getter
	@Setter
    private Integer members;

	@Getter
	@Setter
    private String creater;
	
	@Getter
	@Setter
    private String homeCourt;

	@Getter
	@Setter
    private Date setUpTime;
	
	@Getter
	@Setter
    private String setUpTimeStr;

	@Getter
	@Setter
    private String grade;

	@Getter
	@Setter
    private Integer win;

	@Getter
	@Setter
    private Integer drew;

	@Getter
	@Setter
    private Integer lost;

	@Getter
	@Setter
    private Date lastUpdateTime;

	@Getter
	@Setter
    private Date createTime;
	
	@Getter
	@Setter
    private String lastUpdateTimeStr;

	@Getter
	@Setter
    private String createTimeStr;

	@Getter
	@Setter
    private Integer stat;
	
	@Getter
	@Setter
	private String teamType;
	
	@Getter
	@Setter
	private String teamTypeStr;
	
	@Getter
	@Setter
	private String teamIntroduce;
	
	@Getter
	@Setter
	private String lineup;
	
	@Getter
	@Setter
	private Double winRate;
	
	@Getter
	@Setter
    private String setUpTimeVo;
	
	@Getter
	@Setter
	private String picStr;	//验证码
	
	@Getter
	@Setter
    private String ownerId;
	
	@Getter
	@Setter
    private Boolean owner = false;//是否当前用户所有
	
	@Getter
	@Setter
	private String province; //省份
	
	@Getter
	@Setter
	private String pageCover; //省份
}
