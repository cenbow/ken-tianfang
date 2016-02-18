package com.tianfang.news.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 新闻
 * 
 * @author xiang_wang
 *
 * 2015年11月14日下午2:58:25
 */
public class SportNewsInfoDto implements Serializable {
	private static final long serialVersionUID = 5847025548015799439L;

	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private String title;		// 标题

	@Getter
	@Setter
	private String subtract;	// 摘要

	@Getter
	@Setter
	private String content;		// 内容

	@Getter
	@Setter
	private String microPic;	// 缩略图地址

	@Getter
	@Setter
	private String thumbnail;	

	@Getter
	@Setter
	private String pic;			// 原图地址

	@Getter
	@Setter
	private Integer marked;		// 是否加精

	@Getter
	@Setter
	private Date createTime;	// 记录的创建时间

	@Getter
	@Setter
	private Date lastUpdateTime;	// 最近一次的更新时间

	@Getter
	@Setter
	private Integer stat;		// 状态是否有效：1表示有效；0表示无效
	
	@Getter
    @Setter
	private Integer releaseStat;//发布状态:0表示不发布，1表示发布

	@Getter
	@Setter
	private Integer pageRanking; // 前端页面排序

	@Getter
	@Setter
	private String markedStr;

	@Getter
	@Setter
	private String createTimeStr;

	@Getter
	@Setter
	private String startTimeStr;

	@Getter
	@Setter
	private String endTimeStr;

	@Getter
	@Setter
	private String newVideo; // 新闻视频
	
	@Getter
    @Setter
    private Integer resultStat;
	
	@Getter
    @Setter
    private Integer clickRate;
    
    @Getter
    @Setter
    private Integer browseQuantity;
    
    @Getter
    @Setter
    private int orderType;
    
}