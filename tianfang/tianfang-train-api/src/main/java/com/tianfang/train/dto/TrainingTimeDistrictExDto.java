package com.tianfang.train.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class TrainingTimeDistrictExDto implements Serializable {
		@Getter
		@Setter
		private String id;

		@Getter
		@Setter
	    private String strTime;    //时间段显示

}
