package com.tianfang.train.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

public class TrainingAddressDtoX01 implements Serializable {

    private String id;
    private String districtId;
    private String place;
    private BigDecimal longtitude;
    private BigDecimal latitude;
    private String address;
    private Long createTime;
    private Long updateTime;
    private Integer status;
    private String freeTimes;
    private String thumbnail;
    private String description;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDistrictId() {
		return districtId;
	}
	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public BigDecimal getLongtitude() {
		return longtitude;
	}
	public void setLongtitude(BigDecimal longtitude) {
		this.longtitude = longtitude;
	}
	public BigDecimal getLatitude() {
		return latitude;
	}
	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getFreeTimes() {
		return freeTimes;
	}
	public void setFreeTimes(String freeTimes) {
		this.freeTimes = freeTimes;
	}
    /**
     * @return the thumbnail
     */
    public String getThumbnail()
    {
        return thumbnail;
    }
    /**   
     * @param thumbnail the thumbnail to set   
     */
    public void setThumbnail(String thumbnail)
    {
        this.thumbnail = thumbnail;
    }
    /**
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }
    /**   
     * @param description the description to set   
     */
    public void setDescription(String description)
    {
        this.description = description;
    }
	
}
