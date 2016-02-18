package com.tianfang.train.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 我的学院数据客户课程考勤数据
 * 
 * @author xiang_wang
 *
 * 2015年9月5日
 */
public class MineCollegeDto implements Serializable{

	private static final long serialVersionUID = -1722308278901479773L;
	
	@Getter
	@Setter
	private String courseName;			// 课程名称
	
	@Getter
	@Setter
	private Integer usedTime;			// 已参加课时
	
	@Getter
	@Setter
	private Integer leftTime;			// 未参加课时
	
	@Getter
	@Setter
	private Integer leaveTime;			// 请假课时
	
	@Getter
	@Setter
	private Integer truantTime;			// 旷课课时
	
	@Getter
	@Setter
	private String courseId;			// 课程id
	
	@Getter
	@Setter
	private String classId;				// 课程小节id
	
	@Getter
	@Setter
	private String addressId;			// 地区id
	
	@Getter
	@Setter
	private String description;			// 课程描述
}