package com.tianfang.user.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class SportShippingAddressDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		@Getter
		@Setter
	    private String id;

		@Getter
		@Setter
	    private String userId;

		@Getter
		@Setter
	    private String locationDetails;

		@Getter
		@Setter
		private String userName;

		@Getter
		@Setter
	    private Integer idType;

		@Getter
		@Setter
	    private String idNumber;
		
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
