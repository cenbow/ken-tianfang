package com.tianfang.business.dto;

import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

public class SportTeamTypeDto implements Serializable {

	@Getter
	@Setter
	private String typeId;

	@Getter
	@Setter
    private String typeName;

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
