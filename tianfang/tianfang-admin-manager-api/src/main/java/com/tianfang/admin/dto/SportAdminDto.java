package com.tianfang.admin.dto;

import java.io.Serializable;

public class SportAdminDto implements Serializable {
    private String id;

    private String account;
    private String passWord;
    
    private String confirmPassWord;
    
    private Long createTime;

    private Long updateTime;
    
    private String createDate;

    private Integer stat;

    /**
     * @return the id
     */
    public String getId()
    {
        return id;
    }

    /**   
     * @param id the id to set   
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @return the account
     */
    public String getAccount()
    {
        return account;
    }

    /**   
     * @param account the account to set   
     */
    public void setAccount(String account)
    {
        this.account = account;
    }

    /**
     * @return the passWord
     */
    public String getPassWord()
    {
        return passWord;
    }

    /**   
     * @param passWord the passWord to set   
     */
    public void setPassWord(String passWord)
    {
        this.passWord = passWord;
    }

    /**
     * @return the createTime
     */
    public Long getCreateTime()
    {
        return createTime;
    }

    /**   
     * @param createTime the createTime to set   
     */
    public void setCreateTime(Long createTime)
    {
        this.createTime = createTime;
    }

    /**
     * @return the updateTime
     */
    public Long getUpdateTime()
    {
        return updateTime;
    }

    /**   
     * @param updateTime the updateTime to set   
     */
    public void setUpdateTime(Long updateTime)
    {
        this.updateTime = updateTime;
    }

    /**
     * @return the status
     */
    public Integer getStat()
    {
        return stat;
    }

    /**
     * @return the createDate
     */
    public String getCreateDate()
    {
        return createDate;
    }

    /**   
     * @param createDate the createDate to set   
     */
    public void setCreateDate(String createDate)
    {
        this.createDate = createDate;
    }

    /**   
     * @param status the status to set   
     */
    public void setStat(Integer stat)
    {
        this.stat = stat;
    }

    /**
     * @return the confirmPassWord
     */
    public String getConfirmPassWord()
    {
        return confirmPassWord;
    }

    /**   
     * @param confirmPassWord the confirmPassWord to set   
     */
    public void setConfirmPassWord(String confirmPassWord)
    {
        this.confirmPassWord = confirmPassWord;
    }

}