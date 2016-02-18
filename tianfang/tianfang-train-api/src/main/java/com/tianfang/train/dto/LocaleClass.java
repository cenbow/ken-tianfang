package com.tianfang.train.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class LocaleClass implements Serializable{

	private static final long serialVersionUID = 6993624889716430070L;
	
	@Getter
	@Setter
	private String classId;	// 课程小节ID
	
	@Getter
	@Setter
	private String addressId;	// 场地id
	
	@Getter
	@Setter
	private String assistantId; // 负责人id
	
	@Getter
	@Setter
	private String timeId;		// 时间段id
	
	@Getter
	@Setter
	private String week;		// 周几
	
	@Getter
	@Setter
	private String startTime;	// 开始时间
	
	@Getter
	@Setter
	private String endTime;		// 结束时间
	
	@Getter
	@Setter
	private String place;		// 场地名
}