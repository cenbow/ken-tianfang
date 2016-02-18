package com.tianfang.user.constants;

import lombok.Getter;
import lombok.Setter;

public enum UserThirdEnum {
	WeChat(3, "微信"),
	QQ(2, "QQ");
	
   
	UserThirdEnum(){
	}
	@Getter
	@Setter
	private Integer index;
	
	@Getter
	@Setter
	private String valString;
	
	UserThirdEnum(Integer index, String valString) {
		this.index = index;
		this.valString = valString;
	}
	

}
