package com.tianfang.order.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class SportMEvaluateDto {
	
	private static final long serialVersionUID = 1L;
	
	@Getter
	@Setter
    private String id;

	@Getter
	@Setter
    private String userId;
	
	@Getter
	@Setter
    private String userPic;
	
	@Getter
	@Setter
    private String nickName;
	
	@Getter
	@Setter
    private String productSkuId;
	
	@Getter
	@Setter
	private Integer star;
	
	@Getter
	@Setter
    private String pic;

	@Getter
	@Setter
    private Integer evaluateStatus;

	@Getter
	@Setter
	private String evaluateContent;

	@Getter
	@Setter
    private Date createTime;

	@Getter
	@Setter
    private Date lastUpdateTime;

	@Getter
	@Setter
    private Integer stat;	
	
	@Getter
	@Setter
	private String createTimeStr;

}