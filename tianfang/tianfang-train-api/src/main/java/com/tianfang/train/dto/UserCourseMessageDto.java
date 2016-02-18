package com.tianfang.train.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class UserCourseMessageDto implements Serializable {
	@Getter
	@Setter
	private String id;   //
	
	@Getter
	@Setter
	private String user_id;   //
	@Getter
	@Setter
	private String course_name;
	@Getter
	@Setter
	private String description;
	@Getter
	@Setter
	private String open_date;
	@Getter
	@Setter
	private String open_time;
	@Getter
	@Setter
	private Integer deposit_ispayed;
	@Getter
	@Setter
	private Integer is_payed;
	@Getter
	@Setter
	private String apply_time;
	@Getter
	@Setter
	private String update_time;
	@Getter
	@Setter
	private String order_no;
	
	@Getter
	@Setter
	private Integer open_status;  //1:已开课 0:未开课

}
