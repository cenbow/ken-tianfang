package com.tianfang.train.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class TrainingAddressDto  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
    private String id;

	@Getter
	@Setter
    private String districtId;

	@Getter
	@Setter
    private String place;

	@Getter
	@Setter
    private BigDecimal longtitude;

	@Getter
	@Setter
    private BigDecimal latitude;

	@Getter
	@Setter
    private String address;

	@Getter
	@Setter
    private Long createTime;

	@Getter
	@Setter
    private Long updateTime;

	@Getter
	@Setter
    private Integer status;
	
	@Getter
	@Setter
	private String city;	//城市
	
	@Getter
	@Setter
	private String name ;   //区域名称
	
	@Getter
	@Setter
	private String day_of_week; //星期
	
	@Getter
	@Setter
	private String start_time;  //时间段开始时间
	
	@Getter
	@Setter
	private String end_time;	//时间段结束时间
	@Getter
	@Setter
	private String timeId;		//时间段id
	@Getter
	@Setter
	private String timeIdTrain;  //时间段集合
	@Getter
	@Setter
	private String addressId;		//场地id
	@Getter
	@Setter
	private String traTimeAddress; //场地负责人与场地时间段表关联id
	
	@Getter
    @Setter
	private BigDecimal price;
	
	@Getter
    @Setter
    private Long openDate;
	
	@Getter
    @Setter
	private String thumbnail;

	@Getter
    @Setter
    private String description;

}