package com.tianfang.train.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 现场管理首页数据
 * 
 * @author xiang_wang
 *
 * 2015年9月5日
 */
public class LocaleIndexDto implements Serializable{

	private static final long serialVersionUID = 3437932613291648635L;
	
	@Getter
	@Setter
	private String place;				// 场地名
	
	@Getter
	@Setter
	private List<LocaleClass> courses;	// 场地下的课程
}