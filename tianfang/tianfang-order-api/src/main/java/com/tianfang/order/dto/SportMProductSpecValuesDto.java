package com.tianfang.order.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class SportMProductSpecValuesDto implements Serializable{

	private static final long serialVersionUID = 1L;

	@Setter
	@Getter
    private String id;

	@Setter
	@Getter
    private String productSkuId;

	@Setter
	@Getter
    private String specValuesId;

	@Setter
	@Getter
    private Date createTime;

	@Setter
	@Getter
    private Date lastUpdateTime;

	@Setter
	@Getter
    private Integer stat;

}