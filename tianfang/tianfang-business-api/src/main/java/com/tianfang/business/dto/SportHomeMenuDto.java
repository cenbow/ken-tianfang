package com.tianfang.business.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class SportHomeMenuDto implements Serializable {
	private static final long serialVersionUID = -2508879705930372981L;

	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
    private String parentId;			// 父菜单id

	@Getter
	@Setter
    private String cname;				// 菜单中文名称

	@Getter
	@Setter
    private String ename;				// 菜单英文名称

	@Getter
	@Setter
    private Integer menuType;			// 几级菜单

	@Getter
	@Setter
    private Integer menuOrder;			// 菜单排序

	@Getter
	@Setter
    private String menuUrl;				// 跳转url
	
	@Getter
	@Setter
	private String summary;				// 简单描述
	
	@Getter
	@Setter
    private String content;				// 描述

	@Getter
	@Setter
    private Integer issingle;			// 是否单页（0：否，1：是）

	@Getter
	@Setter
    private String picture;				// 背景图片

	@Getter
	@Setter
    private Integer activityType;		// 菜单类型(0-未来之星,1-主题活动,2-精英训练营,3-课程内容,4-学院理念)

	@Getter
	@Setter
    private Integer videoType;			// 视频类型(1-Happy Football,2-精英训练营,3-原创视频)

	@Getter
	@Setter
    private Date lastUpdateTime;

	@Getter
	@Setter
    private Date createTime;

	@Getter
	@Setter
    private Integer stat;
}