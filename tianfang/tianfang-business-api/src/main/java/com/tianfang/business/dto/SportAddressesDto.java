package com.tianfang.business.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class SportAddressesDto implements Serializable {

		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		@Getter
		@Setter
		private Integer id;

		@Getter
		@Setter
	    private String parentId;

		@Getter
		@Setter
	    private String name;

		@Getter
		@Setter
	    private Integer level;

		@Getter
		@Setter
	    private Date createTime;

		@Getter
		@Setter
	    private Date lastUpdateTime;

		@Getter
		@Setter
	    private Integer stat;
}
