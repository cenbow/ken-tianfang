package com.tianfang.order.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class SportMCategoryDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
    @Setter
    private String id;

	@Getter
    @Setter
    private String typeId;

	@Getter
    @Setter
    private String parentId;
	
	@Getter
    @Setter
    private String parentName;

	@Getter
    @Setter
    private String categoryName;

	@Getter
    @Setter
    private String categoryPic;

	@Getter
    @Setter
    private Integer categoryOrder;

	@Getter
    @Setter
    private Integer isShow;

	@Getter
    @Setter
    private Date createTime;

	@Getter
    @Setter
    private Date lastUpdateTime;

	@Getter
    @Setter
    private Integer stat;
	
	@Getter
	@Setter
	private String createDate;
	
	@Getter
    @Setter
    private String typeName;
}