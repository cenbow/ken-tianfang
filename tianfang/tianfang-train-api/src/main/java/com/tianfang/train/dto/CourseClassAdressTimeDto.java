package com.tianfang.train.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

public class CourseClassAdressTimeDto implements Serializable {
	
	@Getter
	@Setter
	private String class_id;
	@Getter
	@Setter	
	private String course_id;
	@Getter
	@Setter
	private Integer course_time;
	@Getter
	@Setter
	private BigDecimal total_fee;
	@Getter
	@Setter
	private String course_name;
	@Getter
	@Setter
	private String address_name;
	@Getter
	@Setter
	private String start_time;
	@Getter
	@Setter
	private String end_time;
	
	@Getter
	@Setter
	private BigDecimal deposit;  //定金额
	
	@Getter
	@Setter
	private BigDecimal  discount;  //折扣
	
	@Getter
	@Setter
	private Long openDate;  //开课日期
	@Getter
	@Setter
	private String openDateFmt;  //XX月XX日
	@Getter
	@Setter
	private String openTime;
	@Getter
	@Setter
	private String day_of_week;  //上课星期几
	
	@Getter
	@Setter
	private Long applyTime;
	
	@Getter
	@Setter
	private String spaceId;
}
