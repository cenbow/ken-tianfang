package com.tianfang.train.dto;

/**
 * 
 */

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author wk.s
 * @date 2015年9月1日
 */
public class TrainingCourseDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id; // 课程ID
	private String name; // 课程名
	private Integer address; // 活动地址编号
	private String addressX; // 活动地址
	private Integer courseTime; // 学时
	private BigDecimal price; // 单价
	private String tag; // 标签（留作他用）
	private String tagName; //标签
	private String tagId; // 标签
	private Integer type; // 类型编码
	private String typeX; // 类型
	private String description; // 类型描述
	private Long startTime; // 开始时间
	private String startTimeFmt;
	private Long endTime; // 结束时间
	private String endTimeFmt;
	private Integer maxStudent; // 最大报名人数
	private Integer actualStudent;// 实际报名人数
	private Integer isneedCname; // 是否需要孩子姓名
	private Double avgPrice; // 该课程的平均价
	private String equip;	//所需装备
	private String pic; // 图片url
    private String microPic;//缩略图
    private Integer isOpened; // 是否开课：1表示已开课；0表示没开课

	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAddress() {
		return address;
	}

	public void setAddress(Integer address) {
		this.address = address;
	}

	public String getAddressX() {
		return addressX;
	}

	public void setAddressX(String addressX) {
		this.addressX = addressX;
	}

	public Integer getCourseTime() {
		return courseTime;
	}

	public void setCourseTime(Integer courseTime) {
		this.courseTime = courseTime;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTypeX() {
		return typeX;
	}

	public void setTypeX(String typeX) {
		this.typeX = typeX;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public Integer getMaxStudent() {
		return maxStudent;
	}

	public void setMaxStudent(Integer maxStudent) {
		this.maxStudent = maxStudent;
	}

	public Integer getActualStudent() {
		return actualStudent;
	}

	public void setActualStudent(Integer actualStudent) {
		this.actualStudent = actualStudent;
	}

	public Integer getIsneedCname() {
		return isneedCname;
	}

	public void setIsneedCname(Integer isneedCname) {
		this.isneedCname = isneedCname;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getAvgPrice() {
		return avgPrice;
	}

	public void setAvgPrice(Double avgPrice) {
		this.avgPrice = avgPrice;
	}

	public String getStartTimeFmt() {
		return startTimeFmt;
	}

	public void setStartTimeFmt(String startTimeFmt) {
		this.startTimeFmt = startTimeFmt;
	}

	public String getEndTimeFmt() {
		return endTimeFmt;
	}

	public void setEndTimeFmt(String endTimeFmt) {
		this.endTimeFmt = endTimeFmt;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getEquip() {
		return equip;
	}

	public void setEquip(String equip) {
		this.equip = equip;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

    /**
     * @return the microPic
     */
    public String getMicroPic()
    {
        return microPic;
    }

    /**   
     * @param microPic the microPic to set   
     */
    public void setMicroPic(String microPic)
    {
        this.microPic = microPic;
    }

	public Integer getIsOpened() {
		return isOpened;
	}

	public void setIsOpened(Integer isOpened) {
		this.isOpened = isOpened;
	}

}
