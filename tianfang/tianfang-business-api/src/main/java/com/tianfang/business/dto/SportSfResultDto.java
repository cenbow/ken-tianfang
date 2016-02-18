package com.tianfang.business.dto;

import java.util.Date;

public class SportSfResultDto {
   
    private String id;
    private String sfUserId;
    private String sfAnswer;
    private String sfQuestion;
    private Date createTime;
    private Date lastUpdateTime;
    private Integer stat;
   
    public String getId() {
        return id;
    }
   
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }
    
    public String getSfUserId() {
        return sfUserId;
    }
   
    public void setSfUserId(String sfUserId) {
        this.sfUserId = sfUserId == null ? null : sfUserId.trim();
    }
    
    public String getSfAnswer() {
        return sfAnswer;
    }
  
    public void setSfAnswer(String sfAnswer) {
        this.sfAnswer = sfAnswer == null ? null : sfAnswer.trim();
    }
   
    public String getSfQuestion() {
        return sfQuestion;
    }
 
    public void setSfQuestion(String sfQuestion) {
        this.sfQuestion = sfQuestion == null ? null : sfQuestion.trim();
    }
  
    public Date getCreateTime() {
        return createTime;
    }
  
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
   
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getStat() {
        return stat;
    }

    public void setStat(Integer stat) {
        this.stat = stat;
    }
}