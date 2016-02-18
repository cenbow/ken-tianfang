package com.tianfang.order.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class SportMShippingAddressDto {
	
	private static final long serialVersionUID = 1L;
	
	@Setter
	@Getter
    private String id;

	@Setter
	@Getter
    private String name;

	@Setter
	@Getter
    private String province;
	
	@Getter
	@Setter
	private String area;

	@Setter
	@Getter
    private String distruct;

	@Setter
	@Getter
    private String addressInfo;

	@Setter
	@Getter
    private String phone;

	@Setter
	@Getter
    private String email;

	@Setter
	@Getter
    private String coding;

	@Setter
	@Getter
    private String addressEntryName;

	@Setter
	@Getter
    private String userId;

	@Setter
	@Getter
    private Integer maker;

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