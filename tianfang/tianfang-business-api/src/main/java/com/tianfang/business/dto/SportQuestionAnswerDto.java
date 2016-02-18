package com.tianfang.business.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author wk.s
 *
 */
public class SportQuestionAnswerDto {
    
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
    private Date createTime;

    @Getter
    @Setter
    private Date lastUpdateTime;

    @Getter
    @Setter
    private Integer stat;

}