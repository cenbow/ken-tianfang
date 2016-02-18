package com.tianfang.business.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class SportInformationReqDto {
    
    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String subtract;

    @Getter
    @Setter
    private String content;

    @Getter
    @Setter
    private String microPic;

    @Getter
    @Setter
    private String pic;

    @Getter
    @Setter
    private Integer marked;

    @Getter
    @Setter
    private Integer type;

    @Getter
    @Setter
    private String infoVideo;

    @Getter
    @Setter
    private String label;
    
    @Getter
    @Setter
    private Integer clickRate;
    
    @Getter
    @Setter
    private Integer browseQuantity;

    @Getter
    @Setter
    private Integer pageRanking;
    
    @Getter
    @Setter
    private Integer releaseStat;

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
    private int orderType;
    
}