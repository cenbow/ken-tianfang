package com.tianfang.business.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
/**
 * 反馈意见 - 问题
 * @author mr
 *
 */
public class SportSfQuestionDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		@Getter
		@Setter
		private String id;

		@Getter
		@Setter
	    private String sfType;

		@Getter
		@Setter
	    private Integer sfSelectType;

		@Getter
		@Setter
	    private String sfQuestionContent;

		@Getter
		@Setter
		private List<SportSfAnswerDto> sfAnswers;
		
		@Getter
		@Setter
	    private Date lastUpdateTime;

		@Getter
		@Setter
	    private Date createTime;

		@Getter
		@Setter
	    private Integer stat;

}
