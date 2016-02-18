package com.tianfang.business.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
/**
 * 反馈意见 -结果
 * @author mr
 *
 */
public class SportSfResultExDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		@Getter
		@Setter
		private String id;
		@Getter
		@Setter
		private String userId;
		@Getter
		@Setter
		private String sfAnswer;
		@Getter
		@Setter
		private String sfQuestion;
		
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
	    private String sfQuestionContent;
	    
	    @Getter
		@Setter
	    private String answerName;
	    
	    @Setter
		@Getter
		private int limit=10;
		@Setter
		@Getter
		private long start=0;
}
