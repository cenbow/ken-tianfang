package com.tianfang.order.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class SportMBrandDto implements Serializable {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
    @Setter
    private String id;

	@Getter
    @Setter
    private String brandName;

	@Getter
    @Setter
    private String brandPic;

	@Getter
    @Setter
    private String brandUrl;

	@Getter
    @Setter
    private Integer brandOrder;

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
	private Integer deleteStat;

}