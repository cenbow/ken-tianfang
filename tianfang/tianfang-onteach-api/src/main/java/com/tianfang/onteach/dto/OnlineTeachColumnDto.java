package com.tianfang.onteach.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class OnlineTeachColumnDto implements Serializable{
	@Setter
	@Getter
    private String id;

	@Setter
	@Getter
    private String columnName;

	@Setter
	@Getter
    private String courseImg;

	@Setter
	@Getter
    private Date lastUpdateTime;

	@Setter
	@Getter
    private Date createTime;

	@Setter
	@Getter
    private Integer stat;

}