package com.tianfang.train.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.libs.com.zwitserloot.cmdreader.Sequential;

public class TrainingUserDto implements Serializable {
	@Getter
	@Setter
	private String id;

	@Setter
	@Getter
	private String openId;

	@Setter
	@Getter
	private String pname;

	@Setter
	@Getter
	private Integer utype;

	@Setter
	@Getter
	private String cname;

	@Setter
	@Getter
	private Long birthday;
	
	@Setter
	@Getter
	private String fmbirthday;

	@Getter
	@Setter
	private String birthdays;

	@Getter
	@Setter
	private String birthdayStr;

	@Setter
	@Getter
	private String originSchool;

	@Setter
	@Getter
	private String mobile;

	@Setter
	@Getter
	private Integer status;

	@Setter
	@Getter
	private Long createTime;
	
	@Getter
	@Setter
	private String fmCreateTime;

	@Setter
	@Getter
	private Long updateTime;

	@Setter
	@Getter
	private String name;

	@Setter
	@Getter
	private String address; // 场地

	@Setter
	@Getter
	private String spaceTime; // 场地时间

	@Getter
	@Setter
	private String dayOfWeek; // 星期

	@Getter
	@Setter
	private String startTime; // 日期开始时间

	@Getter
	@Setter
	private String endTime; // 日期结束时间

	@Getter
	@Setter
	private String courseId; // 课程ID

	@Getter
	@Setter
	private String addressId; // 场地ID

	@Getter
	@Setter
	private String spaceTimeId;

	@Getter
	@Setter
	private Integer courseAbove; // 课程以上统计

	@Getter
	@Setter
	private Integer notCourse; // 课程未上统计

	@Getter
	@Setter
	private Integer leaveCourse; // 请假统计

	@Getter
	@Setter
	private Integer absenteeismCourse; // 旷课统计

	@Getter
	@Setter
	private String classId; // 课程小结ID

	@Getter
	@Setter
	private String location; // 用户所在地

	@Getter
	@Setter
	private String position; // 用户位置（中锋等）

	@Getter
	@Setter
	private String pic; // 头像url

	@Getter
	@Setter
	private String microPic; // 头像缩略图

	@Getter
	@Setter
	private String description; // 用户描述
	
	@Getter
	@Setter
	private Long lastLoginTime; // 上次登录时间
	
	@Getter
	@Setter
	private String fmLastLoginTime; // 格式化（yyyy-MM-dd HH:mm:ss）后的时间戳
	
	@Getter
	@Setter
	private String captcha;    //短信验证码
	
}