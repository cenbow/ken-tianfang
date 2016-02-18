package com.tianfang.train.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class AssistantDto implements Serializable {

	private static final long serialVersionUID = -4819839298090746960L;

	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private String name;

	@Getter
	@Setter
	private String account;

	@Getter
	@Setter
	private String password;

	@Getter
	@Setter
	private String address;

	@Getter
	@Setter
	private String placestr;

	@Getter
	@Setter
	private Long createtime;

	@Getter
	@Setter
	private String createTimeStr;

	@Getter
	@Setter
	private Long updatetime;

	@Getter
	@Setter
	private Integer status;
}