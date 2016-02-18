package com.tianfang.business.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 未来之星
 * 
 * @author xiang_wang
 *
 * 2015年11月24日上午9:16:03
 */
public class SportFutureStarDto implements Serializable {

	private static final long serialVersionUID = -2399677784494342392L;
	
	@Getter
	@Setter
    private String id;

	@Getter
	@Setter
    private String title;			// 标题

	@Getter
	@Setter
    private String summary;			// 简单介绍

	@Getter
	@Setter
    private String content;			// 描述富文本

	@Getter
	@Setter
    private String picture;			// 图片介绍

	@Getter
	@Setter
    private String button;			// 按钮名字
	
	@Getter
	@Setter
	private Integer type; 			// 0-未来之星,1-主题活动
	
	@Getter
	@Setter
	private String url;				// 跳转地址
	
	@Getter
	@Setter
	private Integer pageRanking; 	// 前端页面排序

	@Getter
	@Setter
    private Date createTime;
	
	@Getter
	@Setter
	private String createTimeStr;
	
	@Getter
	@Setter
	private String createTimeS;		// 开始时间开始
	
	@Getter
	@Setter
	private String createTimeE;		// 开始时间结束

	@Getter
	@Setter
    private Date lastUpdateTime;

	@Getter
	@Setter
	private String lastUpdateTimeStr;
	
	@Getter
	@Setter
    private Integer stat;
}