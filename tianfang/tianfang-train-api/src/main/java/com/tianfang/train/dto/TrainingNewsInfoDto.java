package com.tianfang.train.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class TrainingNewsInfoDto implements Serializable {
		@Getter
		@Setter
		private String id;
		
		@Getter
		@Setter
	    private String title;

	    @Getter
		@Setter
	    private String subtract;

	    @Getter
		@Setter
	    private String content;

	    @Getter
		@Setter
	    private String microPic;
	    
	    @Getter
		@Setter
	    private String thumbnail;
	    
	    @Getter
		@Setter
	    private String pic;

	    @Getter
		@Setter
	    private Integer marked;

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
        private Integer pageRanking;
	    
	    @Getter
		@Setter
	    private String markedStr;
	    
	    @Getter
		@Setter
	    private String createTimeStr;

	    @Getter
		@Setter
	    private String startTimeStr;
	    
	    @Getter
		@Setter
	    private String endTimeStr;
	    
	    @Getter
		@Setter
	    private String newVideo;
}