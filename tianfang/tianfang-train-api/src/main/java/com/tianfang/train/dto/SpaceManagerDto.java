package com.tianfang.train.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

public class SpaceManagerDto implements Serializable {
	@Getter
	@Setter
	private String id;
	@Getter
	@Setter
	private String districtid;
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
	private Long createtime;
	@Getter
	@Setter
    private Long updatetime;
	@Getter
	@Setter
    private Integer status;
}
