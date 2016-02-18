package com.tianfang.order.constants;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;


//2015年3月30日 下午6:42:25  by bake
public enum Status {
	//0表示无效，1表示有效
	effective(0, "无效"),
	uneffective(1, "有效");
	
	private static Map<Integer, String> theStatusMap = new HashMap<Integer, String>();
	
	static{
		for (Status status : Status.values()) {
			theStatusMap.put(status.getValue(), status.getDescription());
		}
	}
	
	@Getter
	@Setter
	private Integer value;
	
	@Getter
	@Setter
	private String description;

	private Status(Integer value, String description) {
		this.value = value;
		this.description = description;
	}

	public static String getStatusDesc(Integer value){
		return theStatusMap.get(value);
	}

}
