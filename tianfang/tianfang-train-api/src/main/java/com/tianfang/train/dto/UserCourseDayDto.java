package com.tianfang.train.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class UserCourseDayDto implements Serializable {
	@Getter
	@Setter
	private Integer count; // 上课情况统计

	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private String courseId;// 课程ID

	@Getter
	@Setter
	private String timeDistrictId; // 课程对应的时间表ID

	@Getter
	@Setter
	private String classId; // 课程小结ID

}
