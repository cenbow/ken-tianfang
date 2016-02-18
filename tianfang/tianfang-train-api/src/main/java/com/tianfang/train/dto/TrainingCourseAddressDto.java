package com.tianfang.train.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用于课程报名第二步,展示课程地点,以及剩余名额.
 * @author mengmeng
 *
 */
public class TrainingCourseAddressDto implements Serializable{
	private String course_id;			// 课程ID
	private String class_id;          //班级ID
	private BigDecimal price;	// 单价
	private Integer max_student;	// 最大报名人数
	private Integer actual_student;// 实际报名人数	

	private String address_id;    //课程地址ID
	private String district_id;  //区id
	private String  place;        //课程地点
	private String address;       //课程详细地址
	private Integer time_id;
	private String day_of_week;
	private String start_time;
	private String end_time;
	
	
	
	public String getClass_id() {
		return class_id;
	}
	public void setClass_id(String class_id) {
		this.class_id = class_id;
	}
	public Integer getMax_student() {
		return max_student;
	}
	public void setMax_student(Integer max_student) {
		this.max_student = max_student;
	}
	public Integer getActual_student() {
		return actual_student;
	}
	public void setActual_student(Integer actual_student) {
		this.actual_student = actual_student;
	}
	public String getCourse_id() {
		return course_id;
	}
	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}

	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getAddress_id() {
		return address_id;
	}
	public void setAddress_id(String address_id) {
		this.address_id = address_id;
	}
	public String getDistrict_id() {
		return district_id;
	}
	public void setDistrict_id(String district_id) {
		this.district_id = district_id;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getTime_id() {
		return time_id;
	}
	public void setTime_id(Integer time_id) {
		this.time_id = time_id;
	}
	public String getDay_of_week() {
		return day_of_week;
	}
	public void setDay_of_week(String day_of_week) {
		this.day_of_week = day_of_week;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	

}
