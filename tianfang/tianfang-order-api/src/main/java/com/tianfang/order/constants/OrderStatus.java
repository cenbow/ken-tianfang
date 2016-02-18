package com.tianfang.order.constants;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * 订单状态
 * @author rkzhang
 */
public enum OrderStatus {
	
	WaitingForPay(0, "等待买家付款"),
	
	HasPaid(1, "买家已付款"),
	
	Finished(2, "交易完成"),
	
	BuyerClosed(-1, "买家关闭"),
	
	SellerClosed(-2, "卖家关闭"),
	
	SellerRefund(-3, "卖家退款"),
	
	OrderFailed(-4, "订单失效"),
	
	OrderWait(3,"订单等待");
	
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
	
	
	OrderStatus(Integer value, String description) {
		this.value = value;
		this.description = description;
	}
	
	public static String getStatusDesc(Integer value) {
		return orderStatusMap.get(value);
	}
}
