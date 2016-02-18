package com.tianfang.train.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class AddAssistantReqDto implements Serializable{
	
	private static final long serialVersionUID = -4819839298090746960L;


    @Getter
    @Setter
    private String id;
	
	@Getter
	@Setter
    private String traTimeAddresss;
	
	@Getter
	@Setter
    private String name;
	
	@Getter
	@Setter
    private String account;
	
	@Getter
	@Setter
    private String password;
	
}