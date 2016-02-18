package com.tianfang.train.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class AddressDto implements Serializable {
	
	@Getter
	@Setter
	private String id;
	
	@Getter
	@Setter
    private String districtId;
	
	@Getter
	@Setter
    private String place;
	
	@Getter
	@Setter
    private BigDecimal longtitude;
	
	@Getter
	@Setter
    private BigDecimal latitude;
	
	@Getter
	@Setter
    private String address;
	
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
	private List<CourseClassDto> courses;
}