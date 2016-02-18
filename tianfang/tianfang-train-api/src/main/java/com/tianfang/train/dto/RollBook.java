package com.tianfang.train.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 点名册
 * 
 * @author xiang_wang
 *
 * 2015年9月4日
 */
public class RollBook implements Serializable {

	private static final long serialVersionUID = -3055107032751794925L;
	
	@Getter
	@Setter
	private String id;			// 点名记录主键id(如果是新生成的点名册,则没有)
	
	@Getter
	@Setter
	private String name;		// 姓名
	
	@Getter
	@Setter
	private String orderId; 	// 订单id
	
	@Getter
	@Setter
	private String userId;		// 用户id 
	
	@Getter
	@Setter
	private String openId;		// 第三方平台openId
	
	@Getter
	@Setter
	private String courseId;	// 课程id
	
	@Getter
	@Setter
	private String classId;	// 课程小节id
	
	@Getter
	@Setter
	private Integer type;		// 1表示请假；2表示旷课；3到了
}