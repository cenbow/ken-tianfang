package com.tianfang.train.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class TrainingCourseReqDto implements Serializable {
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
	private String startTime;

	@Getter
	@Setter
	private String endTime;

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
	private String courseCycle; // 课程周期

	@Getter
	@Setter
	private String courseName; // 课程名称与描述

	@Getter
	@Setter
	private String tagName; // 标签名
	
	@Getter
	@Setter
	private String price;
	
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
	private String pic;//图片

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
    private Integer isOpened;//是否开课
	
	@Getter
    @Setter
	private String microPic;//缩略图
}
