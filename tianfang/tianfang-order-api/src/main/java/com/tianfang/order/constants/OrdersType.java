package com.tianfang.order.constants;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

public enum OrdersType {
	PersonType(1,"个人预订"),
	
	TeamType(2,"订单等待");
	
	 private static Map<Integer, String> orderStatusMap = new HashMap<Integer, String>();

    static{
    	for(OrderStatus status : OrderStatus.values()){
    		orderStatusMap.put(status.getValue(), status.getDescription());
    	}
    }
	
	@Getter
	@Setter
	private Integer value;
	
	@Getter
	@Setter
	private String description;
	
	
	OrdersType(Integer value, String description) {
		this.value = value;
		this.description = description;
	}
	
	public static String getStatusDesc(Integer value) {
		return orderStatusMap.get(value);
	}
}
