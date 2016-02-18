package com.tianfang.business.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class SportFutureStarBlogDto implements Serializable{
    
	private static final long serialVersionUID = 4773933363501073538L;

	@Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String userId;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String summary;

    @Getter
    @Setter
    private Integer readAmount;

    @Getter
    @Setter
    private Integer shareAmount;

    @Getter
    @Setter
    private Integer likeAmount;

    @Getter
    @Setter
    private Integer pageRanking;

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
    private String content;
    
    @Getter
    @Setter
    private Integer releaseStat;
    
    @Getter
    @Setter
    private String createDate;

}