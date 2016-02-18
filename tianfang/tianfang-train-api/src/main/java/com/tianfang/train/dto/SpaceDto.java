package com.tianfang.train.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 现在负责人.添加场地所需数据
 * 
 * @author xiang_wang
 *
 * 2015年10月23日上午10:48:18
 */
public class SpaceDto implements Serializable {
	@Getter
	@Setter
	private String addressDistrictTimeId; // 场地时间段关联表id
	
	@Getter
	@Setter
	private String place; 	// 场地名称
	
	@Getter
	@Setter
	private String week;	// 周几
	
	@Getter
	@Setter
	private String startTime;	// 开始时间
	
	@Getter
	@Setter
	private String endTime;		// 结束时间
	
	@Getter
	@Setter
	private String addressId;	// 场地id
}