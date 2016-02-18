package com.tianfang.train.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class LoginAssistantDto implements Serializable{
	private static final long serialVersionUID = 3086422863012583974L;

	@Setter
	@Getter
	private String id;
	
	@Getter
	@Setter
	private String name;
}