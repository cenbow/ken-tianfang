package com.tianfang.train.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
/**
 * @author YIn
 * @time:2015年8月11日 下午3:28:09
 */
public class TrainInfoDto implements Serializable{

	private static final long serialVersionUID = 1L;

	@Getter
    @Setter
	private String id;
	
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
    private String trainTheme;
	
    @Getter
    @Setter
    private String trainAuthor;
	
    @Getter
    @Setter
    private String releaseTime;
	
    @Getter
    @Setter
    private String trainContent;
    
    @Getter
    @Setter
    private String trainThumbnail;
    
    @Getter
    @Setter
    private String trainPic;
    
    @Getter
    @Setter
    private Integer marked;    //是否加精：0不加，1加
    
}
