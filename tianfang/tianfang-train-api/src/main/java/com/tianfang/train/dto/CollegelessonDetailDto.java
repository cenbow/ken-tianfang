package com.tianfang.train.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


/**
 * 课程介绍详情
 * 
 * @author xiang_wang
 *
 * 2015年10月11日下午2:59:40
 */
public class CollegelessonDetailDto implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Setter
	@Getter
	private String courseId;		// 课程id
	
	@Setter
	@Getter
	private String name;			// 课程名称
	
	@Setter
	@Getter
	private Integer courseTime;		// 课时
	
	@Setter
	@Getter
	private String equip;			// 活动需要的装备
	
	@Setter
	@Getter
	private String courseDescription;		// 课程描述

	@Setter
	@Getter
	private String pic;				// 图片
	
	@Setter
	@Getter
	private String video;			// 视频
	
	@Setter
	@Getter
	private String microPic;		// 缩略图url
	
	@Setter
	@Getter
	private Integer isOpened;		// 1表示已开课；0表示还没开课
	
	@Setter
	@Getter
	private String addressId;		// 地区id
	
	@Setter
	@Getter
	private String place;			// 地名
	
	@Setter
	@Getter
	private BigDecimal longtitude; 	// 经度

	@Setter
	@Getter
	private BigDecimal latitude; 	// 维度
	
	@Setter
	@Getter
	private String address;			// 具体地址
	
	@Setter
	@Getter
	private String thumbnail;		// 场地缩略图
	
	@Setter
	@Getter
	private String addressDescription;		// 场地描述

	@Setter
	@Getter
	private String freeTimes; 		// 该场地所关联的空闲的(正常教学之外的)时间段.
	
	@Setter
	@Getter
	private String price;			// 初始化浮动价格(￥min~￥max)
	
	@Setter
	@Getter
	private List<CollegelessonClassDto> classes; // 时间段小节课程数据
	
	public CollegelessonDetailDto() {
		super();
	}	
	
	public CollegelessonDetailDto(String courseId, String name,
			Integer courseTime, String equip, String courseDescription,
			String pic, String video, String microPic, Integer isOpened, String addressId,
			String place, BigDecimal longtitude, BigDecimal latitude,
			String address, String thumbnail, String addressDescription,
			String freeTimes, List<CollegelessonClassDto> classes) {
		super();
		this.courseId = courseId;
		this.name = name;
		this.courseTime = courseTime;
		this.equip = equip;
		this.courseDescription = courseDescription;
		this.pic = pic;
		this.video = video;
		this.microPic = microPic;
		this.isOpened = isOpened;
		this.addressId = addressId;
		this.place = place;
		this.longtitude = longtitude;
		this.latitude = latitude;
		this.address = address;
		this.thumbnail = thumbnail;
		this.addressDescription = addressDescription;
		this.freeTimes = freeTimes;
		this.classes = classes;
	}
}