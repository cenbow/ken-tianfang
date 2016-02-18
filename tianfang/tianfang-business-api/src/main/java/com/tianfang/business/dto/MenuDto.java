package com.tianfang.business.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class MenuDto implements Serializable{
	
	@Setter
	@Getter
	private SportHomeMenuDto sportHomeMenuDto;
	
	@Setter
	@Getter
	private List<SportHomeMenuDto> secondList;
}
