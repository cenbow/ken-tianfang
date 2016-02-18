package com.tianfang.train.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

public class TrainingSpaceManagerDto implements Serializable { 
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
	    private String createTimeStr;
	    
	    @Getter
	    @Setter
	    private String updateTimeStr;
	    
	    @Getter
	    @Setter
	    private String area; //区域
	    
	    @Getter
	    @Setter
	    private String thumbnail;

	    @Getter
	    @Setter
	    private String description;

	
}
