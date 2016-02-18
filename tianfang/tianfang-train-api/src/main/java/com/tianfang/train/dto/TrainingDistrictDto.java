package com.tianfang.train.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
/**
 * 场地 区域信息
 * @author Administrator
 *
 */
public class TrainingDistrictDto implements Serializable {
	@Getter
	@Setter
	private String id;
	@Getter
	@Setter
	private String city;
	@Getter
	@Setter
    private String name;
	@Getter
	@Setter
    private Long createTime;
	@Getter
	@Setter
    private Long updateTime;
	@Getter
	@Setter
    private Integer status;
}
