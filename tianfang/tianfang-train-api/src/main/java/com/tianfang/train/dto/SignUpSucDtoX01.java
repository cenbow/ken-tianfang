/**
 * 
 */
package com.tianfang.train.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;


/**
 * 
 * @author wk.s
 * @date 2015年10月11日
 */
public class SignUpSucDtoX01 implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private String dayOfWeek;
	
	@Getter
	@Setter
	private BigDecimal totalFee;
	
	@Getter
	@Setter
	private String courseName;
	
	@Getter
	@Setter
	private String location;
	
	@Getter
	@Setter
	private String timeDistrict;
	
	@Getter
	@Setter
	private String pname;
	
	@Getter
	@Setter
	private Long mobile;
	
	@Getter
	@Setter
	private String childName;
	
	@Getter
	@Setter
	private BigDecimal discount;
	
	@Getter
	@Setter
	private BigDecimal deposit;
	
	@Getter
	@Setter
	private String orderNo;
}
