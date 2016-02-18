package com.tianfang.business.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 球队公告
 * 
 * @author xiang_wang
 *
 * 2015年11月14日下午2:57:59
 */
public class SportNoticeDto implements Serializable { 
	private static final long serialVersionUID = 96883971143295316L;
	
	@Getter
	@Setter
	private String id;
	
	@Getter
	@Setter
	private String teamId;			//	球队id
	
	@Getter
	@Setter
	private String teamName;		// 球队名称
	
	@Getter
	@Setter
	private String title;			// 标题
	
	@Getter
	@Setter
	private String subtract;		// 摘要
	
	@Getter
	@Setter
	private String content;			// 内容
	
	@Getter
	@Setter
	private Integer marked;			// 是否加精(是否置顶)
	
	@Getter
	@Setter
	private String publisher;		// 发布者
	
	@Getter
	@Setter
	private Integer pageRanking;	// 前端页面排序(保留暂不用)
	
	@Getter
	@Setter
	private Date createTime;		// 记录的创建时间
	
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
	private Date lastUpdateTime;	// 最近一次的更新时间

	@Getter
	@Setter
	private Integer stat;			// 状态是否有效：1表示有效；0表示无效
}