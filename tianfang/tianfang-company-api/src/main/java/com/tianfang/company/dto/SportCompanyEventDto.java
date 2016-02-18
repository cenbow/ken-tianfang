package com.tianfang.company.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class SportCompanyEventDto {
    
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
    private String thumbnail;

    @Getter
    @Setter
    private String pic;

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
    private String createDate;

    @Getter
    @Setter
    private Integer stat;

}