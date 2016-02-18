package com.tianfang.train.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 * 课程介绍详情,时间段小节课程数据
 * 
 * @author xiang_wang
 *
 * 2015年10月11日下午2:58:51
 */
public class CollegelessonClassDto implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Setter
	@Getter
	private String classId;		// 课程小节id
	
	@Setter
	@Getter
	private String week;		// 周几
	
	@Setter
	@Getter
	private String startTime;	// 开始时间
	
	@Setter
	@Getter
	private String endTime;		// 结束时间
	
	@Setter
	@Getter
	private BigDecimal price;	// 价格
	
	@Setter
	@Getter
	private String notice;		//简单报名提示
	
	@Setter
	@Getter
	private Integer maxStudent;	// 可报名学生上限
	
	@Setter
	@Getter
	private Integer actualStudent;// 已报名学生数
	
	@Setter
	@Getter
	private Integer status;		// 是否有效
}