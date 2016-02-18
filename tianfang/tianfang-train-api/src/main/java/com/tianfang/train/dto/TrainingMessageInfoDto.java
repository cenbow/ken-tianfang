package com.tianfang.train.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class TrainingMessageInfoDto implements Serializable {
	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private String userId;

	@Getter
	@Setter
	private String title;

	@Getter
	@Setter
	private String content;

	@Getter
	@Setter
	private Integer type;

	@Getter
	@Setter
	private Date createTime;

	@Getter
	@Setter
	private String releaseDate; // 发布日期

	@Getter
	@Setter
	private String startTimeStr;

	@Getter
	@Setter
	private String endTimeStr;

	@Getter
	@Setter
	private Date lastUpdateTime;

	@Getter
	@Setter
	private Integer stat;
}
