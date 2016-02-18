package com.tianfang.train.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class TrainingCourseDtoX implements Serializable {
	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private String name;

	@Getter
	@Setter
	private Integer courseTime;

	@Getter
	@Setter
	private String tagId;

	@Getter
	@Setter
	private Integer type;

	@Getter
	@Setter
	private String description;

	@Getter
	@Setter
	private Long startTime;

	@Getter
	@Setter
	private Long endTime;

	@Getter
	@Setter
	private Integer maxStudent;

	@Getter
	@Setter
	private Integer actualStudent;

	@Getter
	@Setter
	private Integer isneedCname;

	@Getter
	@Setter
	private Long createTime;

	@Getter
	@Setter
	private Long lastUpdateTime;

	@Getter
	@Setter
	private Integer status;

	@Getter
	@Setter
	private String address; // 场地
	
	@Getter
    @Setter
    private String place; // 场地
	
	@Getter
	@Setter
	private String courseCycle; // 课程周期

	@Getter
	@Setter
	private String courseName; // 课程名称与描述

	@Getter
	@Setter
	private String tagName; // 标签名

	@Getter
	@Setter
	private double price;

	@Getter
	@Setter
	private String openDate;

	@Getter
	@Setter
	private String addressId; // 场地ID

	@Getter
	@Setter
	private String courseId; // 课程ID

	@Getter
	@Setter
	private String courseClassId; // 课程小结ID
	
	@Getter
    @Setter
    private String startDate; // 前端显示开始时间

    @Getter
    @Setter
    private String endDate; // 前端显示开始时间
    
    @Getter
    @Setter
    private String dayOfWeek;//周几
    
    @Getter
    @Setter
    private String timeQuantum;//时间段
    
    @Getter
    @Setter
    private Double deposit;//定金
    
    @Getter
    @Setter
    private Double discount;//定金
    
    @Getter
    @Setter
    private String pic;//图片
    
    @Getter
    @Setter
    private String microPic;//图片
    
    @Getter
    @Setter
    private String video;//视频
    
    @Getter
    @Setter
    private String equip;//装备
    
    
    @Getter
    @Setter
    private Byte marked;//加精
    
    @Getter
    @Setter
    private Integer isOpened; //是否开课：1表示已开课；0表示没开课
}
