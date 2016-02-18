package com.tianfang.train.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

public class TrainingAddressDtoX02 implements Serializable {
	
	@Setter
	@Getter
	private String id;
	@Setter
	@Getter
	private String name;
	@Setter
	@Getter
	private String addr;
	@Setter
	@Getter
	private BigDecimal lng;
	@Setter
	@Getter
	private BigDecimal lat;
}
