package com.tianfang.train.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

public class UserCourseDto implements Serializable{
	
	private static final long serialVersionUID = 1465983014818854837L;

	@Getter
	@Setter
    private String id;

	@Getter
	@Setter
    private String userId;		// 用户ID

	@Getter
	@Setter
    private String courseId;	// 课程ID
	@Getter
	@Setter
    private String openId;	// 课程ID
	@Getter
	@Setter
    private String classId;	// 用户选的课程(小节)ID

	@Getter
	@Setter
    private Integer usedTime;	// 已使用的学时

	@Getter
	@Setter	
    private Integer leftTime;

	@Getter
	@Setter
    private Long applyTime;		// 报名时间

	@Getter
	@Setter
    private String description;	// 课程介绍

	@Getter
	@Setter
    private BigDecimal totoFee;	// 课程费用

	@Getter
	@Setter
    private Integer isPayed;	// 学费是否已支付：1表示已支付；0表示未支付

	@Getter
	@Setter
    private Long createTime;	// 创建时间

	@Getter
	@Setter
    private Long updateTime;	// 更新时间

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
    private String tradeNo;		// 支付宝交易号

	@Getter
	@Setter
    private Integer status;		// 是否有效：1表示有效；0表示无效

	@Getter
	@Setter
	private String startTime;// 时间段
	
	@Getter
	@Setter
    private String endTime;	
	
	@Getter
	@Setter
    private Integer utype;	
	
	@Getter
	@Setter
    private String birthday;
	
	@Getter
	@Setter
    private String originSchool;
	@Getter
	@Setter
	private BigDecimal  deposit;  //定金
	
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
	private String notice;
	
	
	@Getter
	@Setter
	private String spaceId;
	
	
	@Getter
	@Setter
	private Integer isneedCname;
}