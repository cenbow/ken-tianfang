package com.tianfang.train.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 * 现场管理支付操作返回数据
 * 
 * @author xiang_wang
 *
 * 2015年9月2日
 */
public class PayResultDto implements Serializable{

	private static final long serialVersionUID = -3212146483872835987L;
	
	@Getter
	@Setter
	private String id;				// 用户课程关系主键id
	
	@Getter
	@Setter
	private Integer stauts;			// 支付结果状态  0-支付失败,1-支付成功,2-登记孩子出生日期和孩子原学校
	
	@Getter
	@Setter
	private Boolean isChild;		// 是否孩子用户
	
	@Getter
	@Setter
	private String pname;			// 父母姓名
	
	@Getter
	@Setter
	private String cname;			// 孩子姓名
	
	@Getter
	@Setter
	private BigDecimal totoFee;		// 支付费用
	
	public PayResultDto() {
		super();
	}
	
	public PayResultDto(String id, Integer stauts, Boolean isChild,
			String pname, String cname, BigDecimal totoFee) {
		super();
		this.id = id;
		this.stauts = stauts;
		this.isChild = isChild;
		this.pname = pname;
		this.cname = cname;
		this.totoFee = totoFee;
	}
}