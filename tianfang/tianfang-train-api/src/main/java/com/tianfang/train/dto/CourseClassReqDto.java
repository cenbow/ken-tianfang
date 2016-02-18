package com.tianfang.train.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

public class CourseClassReqDto implements Serializable {
	
	@Getter
	@Setter
	private String id;
	
	@Getter
	@Setter
    private String courseId;

	@Getter
	@Setter
    private String timeDistrictId;

	@Getter
    @Setter
    private String addressId;
	
	@Getter
	@Setter
    private Integer maxStudent;

	@Getter
    @Setter
	private String openDate;
	
	@Getter
    @Setter
    private String place;
	
	@Getter
    @Setter
    private String startTime;
	
	@Getter
    @Setter
    private String dayOfWeek;
	
	@Getter
    @Setter
    private String endTime;
	
	@Getter
	@Setter
    private Integer actualStudent;

	@Getter
	@Setter
    private Long createTime;

	@Getter
	@Setter
    private Long updateTime;

	@Getter
	@Setter
	private Integer status;
	
	@Getter
	@Setter
	private Double price;
	
	@Getter
    @Setter
	private Double deposit;
	
	@Getter
    @Setter
    private Double discount;
}