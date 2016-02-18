package com.tianfang.onteach.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;


public class OnTeachDto implements Serializable{
	@Setter
	@Getter
    private String id;

	@Setter
	@Getter
    private String courseName;

	@Setter
	@Getter
    private String courseImg;

	@Setter
	@Getter
    private String courseFullUrl;

	@Setter
	@Getter
    private String courseUrl;

	@Setter
	@Getter
    private Integer downCount;

	@Setter
	@Getter
    private Integer viewCount;

	@Setter
	@Getter
    private Date lastUpdateTime;

	@Setter
	@Getter
    private Date createTime;

	@Setter
	@Getter
    private Integer stat;
	
	@Getter
	@Setter
	private String subtract;
	
	@Getter
	@Setter
	private String context;
	
	@Getter
	@Setter
	private Integer courseType;
	
	@Getter
	@Setter
	private String video;
	
	@Getter
	@Setter
	private String columnName;
	
	@Getter
	@Setter
	private String columnId;

}
