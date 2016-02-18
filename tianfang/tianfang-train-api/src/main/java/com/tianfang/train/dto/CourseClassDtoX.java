package com.tianfang.train.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

public class CourseClassDtoX implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Getter
	@Setter
	private String id;
	@Getter
	@Setter	
	private String name;
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
    private String timeDistrict;

	@Getter
	@Setter
    private BigDecimal price;
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
    private Long startdate;
	@Getter
	@Setter
    private String place;
	@Getter
	@Setter
    private String addressValue;
	@Getter
	@Setter
    private BigDecimal longtitude;
	@Getter
	@Setter
    private BigDecimal latitude;
	@Getter
	@Setter
    private String dayOfWeek;
	@Getter
	@Setter
    private String startTime;
	@Getter
	@Setter
    private String endTime;
	@Getter
	@Setter
    private Double sumPrice;
	@Getter
	@Setter
    private Long openDate;
	@Getter
	@Setter
    private String openDateFmt;
   
	@Getter
	@Setter
	private String district;
	
	@Getter
	@Setter
	private BigDecimal deposit;

	@Getter
	@Setter
	private BigDecimal discount;
	
	@Getter
	@Setter
	private String classId;
	
	@Getter
	@Setter
	private Long minOpenDate;
	
	@Getter
	@Setter
	private String minOpenDateFmt;
	
	@Getter
	@Setter
	private Long maxOpenDate;
	
	@Getter
	@Setter
	private String maxOpenDateFmt;
}
