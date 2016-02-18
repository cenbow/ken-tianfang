package com.tianfang.order.constants;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;


//2015年3月26日 上午9:53:05  by bake
public enum PaymentStatus {
	//订单支付类型 枚举的值
	NoPaid(0, "未支付"),
	
	HasPaid(1, "已支付");
	
	//以建 值的形式放入Map里
	private static Map<Integer, String> paymentStatusMap = new HashMap<Integer, String>();
	
	static{
		for (PaymentStatus status : PaymentStatus.values()) {
			paymentStatusMap.put(status.getValue(), status.getDescription());
		}
	}
	
	@Getter
	@Setter
	private Integer value;
	
	@Getter
	@Setter
	private String description;

	PaymentStatus(Integer value, String description) {
		this.value = value;
		this.description = description;
	}
	
	public static String getStatusDesc(Integer value){
		return paymentStatusMap.get(value);
	}
	
	

}
