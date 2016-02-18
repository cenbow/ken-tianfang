package com.tianfang.train.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

public class TrainApplyDto implements Serializable {
	
	@Getter
	@Setter
	private String id;
	
	@Getter
	@Setter
	private String openId;
	
	@Getter
	@Setter
	private String userId;
	
	@Getter
	@Setter
	private String classId;
	
	@Getter
	@Setter
	private Integer usedTime;
	
	@Getter
	@Setter
	private Integer leftTime;
	
	@Getter
	@Setter
	private String description;
	
	@Getter
	@Setter
	private BigDecimal totoFee;
	
	@Getter
	@Setter
	private Byte payType;
	
	@Getter
	@Setter
    private String pname;  
	
	@Getter
	@Setter
    private String cname;  //孩子姓名
	
	@Getter
	@Setter
    private String courseId; //课程
	
	@Getter
	@Setter
    private String courseName;
	
	@Getter
	@Setter
	private String mobile;
	
	@Getter
	@Setter
	private String spaceId;
	
	@Getter
	@Setter
	private String spaceName;  
	
	
	@Getter   
	@Setter
	private String startTime; 
	
	@Getter
	@Setter
    private String endTime;
	
	@Getter   
	@Setter
	private long startTimeLong; 
	
	@Getter
	@Setter
    private long endTimeLong;
	
	@Getter
	@Setter
	private Long  applyTime; //报名日期
	
	@Getter
	@Setter
	private String  applyTimeAdd; //报名日期
	
	@Getter
	@Setter
	private Integer isPayed;  //学费是否支付
	
	@Getter
	@Setter
	private Long createTime;
	
	@Getter
	@Setter
	private Long updateTime;
	
	@Getter
	@Setter
	private Integer depositIspayed;  //定金是否支付
	
	@Getter
	@Setter
	private String dayOfWeek;  //星期几
	
	@Getter
	@Setter
	private String districtTime;  //星期几
	
	@Getter
	@Setter
	private Integer utype;
	
	@Getter
	@Setter
	private String birthday;
	
	@Getter
	@Setter
	private String originSchool;
	
	@Getter
	@Setter
	private String orderNo;
	
	@Getter
	@Setter
	private String tradeNo;
}
