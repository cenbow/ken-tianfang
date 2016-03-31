package com.tianfang.order.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class SportMProductSkuSpecValuesDto implements Serializable{

	private static final long serialVersionUID = 1L;

	@Setter
	@Getter
    private String id;

	@Setter
	@Getter
    private String productSkuId;
	
	@Setter
	@Getter
	private String specId;
	
	@Setter
	@Getter
	private String specName;

	@Setter
	@Getter
    private String specValuesId;
	
	@Setter
	@Getter
	private String specValue;

	@Setter
	@Getter
    private Date createTime;

	@Setter
	@Getter
    private Date lastUpdateTime;

	@Setter
	@Getter
    private Integer stat;

	@Setter
	@Getter
	private List<SportMSpecValuesDto> sportMSpecValuesDtos;
}