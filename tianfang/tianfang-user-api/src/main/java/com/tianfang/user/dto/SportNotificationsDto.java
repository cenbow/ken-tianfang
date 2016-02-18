package com.tianfang.user.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;


public class SportNotificationsDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
    private String id;

	@Getter
	@Setter
    private Integer upPasswordStat;

	@Getter
	@Setter
    private Integer loginStat;

	@Getter
	@Setter
    private Integer nonlocalLoginStat;
	
	@Getter
	@Setter
	private String email;
	
	@Getter
	@Setter
	private String emailNew;

	@Getter
	@Setter
    private Date lastUpdateTime;

	@Getter
	@Setter
    private Date createTime;

    @Getter
	@Setter
    private Integer stat;
   
    @Getter
   	@Setter
    private String ownerId;
    
    @Getter
   	@Setter
    private String randomCar;//图片验证码
}