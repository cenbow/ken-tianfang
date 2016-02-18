package com.tianfang.business.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
/**
 * 反馈意见 -关联表信息
 * @author mr
 *
 */
public class SportSfQuestionExDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		@Getter
		@Setter
		private String id;
		@Getter
		@Setter
		private String sfAnswer;
		@Getter
		@Setter
		private String sfQuestion;
		@Getter
		@Setter
		private String sfQuestionContent;
		@Getter
		@Setter
		private String answerName;
}
