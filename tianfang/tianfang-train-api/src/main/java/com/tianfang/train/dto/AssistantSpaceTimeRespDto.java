package com.tianfang.train.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class AssistantSpaceTimeRespDto implements Serializable {
	
	@Getter
	@Setter
	private String id;
	
	@Getter
	@Setter
    private String assistantId;

	@Getter
	@Setter
    private String timeDistrictId;

	@Getter
    @Setter
    private String addressId;	

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
    private Long createTime;

	@Getter
	@Setter
    private Long updateTime;

	@Getter
	@Setter
	private Integer status;
}