package com.tianfang.business.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class SportGameDto implements Serializable {

	@Getter
	@Setter
    private String id;

	@Getter
	@Setter
    private String title;

	@Getter
	@Setter
    private String subtitle;

	@Getter
	@Setter
    private String microPic;

	@Getter
	@Setter
    private Integer pageRanking;

	@Getter
	@Setter
    private Date lastUpdateTime;
	
	@Getter
	@Setter
	private String lastUpdateTimeStr;

	@Getter
	@Setter
    private Date createTime;
	
	@Getter
	@Setter
	private String createTimeStr;

	@Getter
	@Setter
    private Integer stat;

	@Getter
	@Setter
    private String content;
	
	@Getter
	@Setter
    private Integer gameType;
}
