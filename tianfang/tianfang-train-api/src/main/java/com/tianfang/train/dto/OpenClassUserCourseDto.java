package com.tianfang.train.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

public class OpenClassUserCourseDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
    private String id;

	@Getter
	@Setter
    private String openId;	// 课程ID

	@Getter
	@Setter
    private BigDecimal totoFee;	// 课程费用

	@Getter
	@Setter
    private Integer depositIspayed;	// 定金是否已支付：1表示已支付；0表示未支付

	@Getter
	@Setter
    private String mobile;		// 手机号码

	@Getter
	@Setter
    private String courseName;	// 课程名称

	@Getter
	@Setter
    private String pname;		// 父母姓名

	@Getter
	@Setter
    private String cname;		// 孩子姓名

	@Getter
	@Setter
    private String spaceName;	// 场地名称

	@Getter
	@Setter
    private String orderNo;		// 订单号

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
	private BigDecimal discount;
	@Getter
	@Setter
	private String notice;
	@Getter
	@Setter
	private BigDecimal deposit;
}
