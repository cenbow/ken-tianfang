package com.tianfang.order.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
/**
 * 规格表
 * @author mr.w
 *
 */
public class SportMSpecProductDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
    private String id;

	@Getter
	@Setter
    private String specName;

	@Getter
	@Setter
    private Integer specOrder;

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
	private String typeId;
	
	@Getter
	@Setter
	List<SportMSpecValuesDto> sportMSpecValuesDtos;
}
