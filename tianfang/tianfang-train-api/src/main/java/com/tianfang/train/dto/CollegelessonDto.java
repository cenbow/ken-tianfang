package com.tianfang.train.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class CollegelessonDto implements Serializable {
	@Setter
	@Getter
	private String courseId;	
	
	@Setter
	@Getter
	private String addressId;
	
	@Setter
	@Getter
	private String classId;
	
	@Setter
	@Getter
	private String name;
	
	@Setter
	@Getter	
	private Integer course_time;
	
	@Setter
	@Getter	
	private String tag_id;
	
	@Setter
	@Getter	
	private String equip;
	
	@Setter
	@Getter	
	private String micro_pic;
	
	@Setter
	@Getter	
	private Long price;
	
	@Setter
	@Getter	
	private Integer startdate;
	
	@Setter
	@Getter	
	private String place;
	
	@Setter
	@Getter	
	private String address;
	
	@Setter
	@Getter	
	private String tag_name;
	
    @Getter
    @Setter
    private Integer marked;    //是否加精：0不加，1加
    
	@Setter
	@Getter	
	private Long open_date;
	
	@Setter
	@Getter	
	private String openDate;
	
	@Setter
	@Getter	
	private String endDate;
	
}
