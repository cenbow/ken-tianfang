package com.tianfang.train.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

public class CourseClassDto implements Serializable {
	
	@Getter
	@Setter
	private String id;
	
	@Getter
	@Setter
    private String courseId;

	@Getter
	@Setter
    private String timeDistrict;

	@Getter
	@Setter
    private Integer maxStudent;

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
	private BigDecimal price;
}