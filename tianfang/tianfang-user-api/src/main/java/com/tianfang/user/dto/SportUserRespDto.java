package com.tianfang.user.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
	 * @author YIn
	 * @time:2015年12月25日 下午3:14:40
	 * @Fields serialVersionUID : TODO
	 */
public class SportUserRespDto implements Serializable{
   
	private static final long serialVersionUID = 1L;

	@Setter
    @Getter
    private String id;

    @Setter
    @Getter
    private String email;

    @Setter
    @Getter
    private String mobile;

    @Setter
    @Getter
    private String password;

    @Setter
    @Getter
    private String nickName;
    
    @Setter
    @Getter
    private String cname;  //中文名
    
    @Setter
    @Getter
    private String ename;  //英文名

    @Setter
    @Getter
    private Integer trainerLevel;

    @Setter
    @Getter
    private Integer gender;

    @Setter
    @Getter
    private Integer utype;

    @Setter
    @Getter
    private String papersPic;

    @Setter
    @Getter
    private String location;

    @Setter
    @Getter
    private String position;

    @Setter
    @Getter
    private String pic;

    @Setter
    @Getter
    private String microPic;

    @Setter
    @Getter
    private String description;

    @Setter
    @Getter
    private Integer visibleStat;
    
    @Setter
    @Getter
    private Date lastLoginTime;

    @Setter
    @Getter
    private Date lastUpdateTime;

    @Setter
    @Getter
    private Date createTime;

    @Setter
    @Getter
    private Integer stat;
    
    @Setter
    @Getter
    private String createDate;
    
    @Setter
    @Getter
    private Integer audit;//审核状态
    
    @Setter
    @Getter
    private Integer lecturer; //培训主讲人(0:不是;1:是培训主讲人)
}