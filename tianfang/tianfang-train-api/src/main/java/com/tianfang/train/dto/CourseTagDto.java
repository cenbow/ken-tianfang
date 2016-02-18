package com.tianfang.train.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class CourseTagDto implements Serializable {
	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private String tagName;

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
