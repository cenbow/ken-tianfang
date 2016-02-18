package com.tianfang.business.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class SportSuggestionUserDto implements Serializable {

	private static final long serialVersionUID = 1L;
		@Getter
		@Setter
		private String id;

		@Getter
		@Setter
	    private Integer sfType;

		@Getter
		@Setter
	    private String sfUname;

		@Getter
		@Setter
	    private String sfPhone;

		@Getter
		@Setter
	    private String sfEmail;

		@Getter
		@Setter
	    private String sfFeedback;

		@Getter
		@Setter
	    private String sfPicture;

	    @Getter
		@Setter
	    private Date createTime;

	    @Getter
		@Setter
	    private Date lastUpdateTime;

	    @Getter
		@Setter
	    private Integer stat;

}
