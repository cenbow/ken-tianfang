package com.tianfang.order.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class SportMTypeDto implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String typeName;

    @Getter
    @Setter
    private Integer typeOrder;

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

}