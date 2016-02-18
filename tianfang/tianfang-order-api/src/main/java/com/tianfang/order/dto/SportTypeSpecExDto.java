package com.tianfang.order.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 类型规格关联信息
 * @author Administrator
 *
 */
public class SportTypeSpecExDto implements Serializable {
	
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
		private String typeId;
		@Getter
		@Setter
		private Date createTime;
		@Getter
		@Setter
		private Date lastUpdateTime;
		@Getter
		@Setter
		private String specName;
		@Getter
		@Setter
		private int specOrder;
		@Getter
		@Setter
		private String typeName;
		@Getter
		@Setter
		private int typeOrder;
		@Getter
		@Setter
		private long start;
		@Getter
		@Setter
		private Integer limit;
}
