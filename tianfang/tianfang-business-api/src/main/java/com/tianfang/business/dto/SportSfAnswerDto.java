package com.tianfang.business.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class SportSfAnswerDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Getter
    @Setter
	private String id;

    @Getter
    @Setter
    private String answerName;

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
    private String question;
    
    @Getter
    @Setter
    private long number;
    
    @Getter
    @Setter
    private double percent;   // 投票百分比
}
