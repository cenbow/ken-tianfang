package com.tianfang.business.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class SportHonorReqDto implements Serializable {

	@Getter
	@Setter
    private String id;//唯一标识

	@Setter
	@Getter
	private String teamId;//球队ID
	
	@Getter
	@Setter
    private String title;//荣誉标题

	@Getter
	@Setter
	private Integer honorStatus;//荣誉状态(0,已审核   1.待审核)
	
	@Getter
	@Setter
    private String publishPeople;//发布人
	
	@Getter
    @Setter
	private Integer publisherType;//0：前台用户，1：后台用户
	
	@Getter
	@Setter
    private String content;//发布内容

	@Getter
	@Setter
    private Date createTime;//创建时间

	@Getter
    @Setter
    private Date lastUpdateTime;//最新更新时间
	
	@Getter
    @Setter
    private Integer stat;//状态是否有效：1表示有效；0表示无效
}