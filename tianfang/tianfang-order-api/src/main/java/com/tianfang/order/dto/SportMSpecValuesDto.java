package com.tianfang.order.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class SportMSpecValuesDto implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String specId;

    @Getter
    @Setter
    private String specValue;

    @Getter
    @Setter
    private String specValuePic;

    @Getter
    @Setter
    private String specValueOrder;

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