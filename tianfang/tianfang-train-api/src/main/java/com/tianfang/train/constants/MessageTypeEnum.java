package com.tianfang.train.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息类型枚举
 * 
 * @author wangxiang
 *
 * 2015年10月26日
 */
public enum MessageTypeEnum {
	System(1,"系统消息"),ComplainPlace(2,"投诉场馆"),ComplainUser(3,"投诉用户"),Complain(4,"投诉建议"),Judge(5,"评价消息"),
	Order(6,"订单消息"),Game(7,"赛事评论");
	private int index;
	private String value;
	
	MessageTypeEnum(int index, String value){
		this.index = index;
		this.value = value;
	}
	
	public static Map<String, String> messageTypeMap = new HashMap<String, String>();

    static{
    	for(MessageTypeEnum status : MessageTypeEnum.values()){
    		messageTypeMap.put(status.getIndex()+"", status.getValue());
    	}
    }
	
	public int getIndex() {
		return index;
	}
	public String getValue() {
		return value;
	}
}