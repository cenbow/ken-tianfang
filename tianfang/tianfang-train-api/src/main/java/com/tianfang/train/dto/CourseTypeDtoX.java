package com.tianfang.train.dto;

import java.io.Serializable;

public class CourseTypeDtoX implements Serializable {
	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column training_course_type.id
     *
     * @mbggenerated Wed Sep 02 10:44:01 CST 2015
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column training_course_type.code
     *
     * @mbggenerated Wed Sep 02 10:44:01 CST 2015
     */
    private Integer code;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column training_course_type.name
     *
     * @mbggenerated Wed Sep 02 10:44:01 CST 2015
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column training_course_type.description
     *
     * @mbggenerated Wed Sep 02 10:44:01 CST 2015
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column training_course_type.create_time
     *
     * @mbggenerated Wed Sep 02 10:44:01 CST 2015
     */
    private Long createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column training_course_type.update_time
     *
     * @mbggenerated Wed Sep 02 10:44:01 CST 2015
     */
    private Long updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column training_course_type.status
     *
     * @mbggenerated Wed Sep 02 10:44:01 CST 2015
     */
    private Integer status;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column training_course_type.id
     *
     * @return the value of training_course_type.id
     *
     * @mbggenerated Wed Sep 02 10:44:01 CST 2015
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column training_course_type.id
     *
     * @param id the value for training_course_type.id
     *
     * @mbggenerated Wed Sep 02 10:44:01 CST 2015
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column training_course_type.code
     *
     * @return the value of training_course_type.code
     *
     * @mbggenerated Wed Sep 02 10:44:01 CST 2015
     */
    public Integer getCode() {
        return code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column training_course_type.code
     *
     * @param code the value for training_course_type.code
     *
     * @mbggenerated Wed Sep 02 10:44:01 CST 2015
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column training_course_type.name
     *
     * @return the value of training_course_type.name
     *
     * @mbggenerated Wed Sep 02 10:44:01 CST 2015
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column training_course_type.name
     *
     * @param name the value for training_course_type.name
     *
     * @mbggenerated Wed Sep 02 10:44:01 CST 2015
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column training_course_type.description
     *
     * @return the value of training_course_type.description
     *
     * @mbggenerated Wed Sep 02 10:44:01 CST 2015
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column training_course_type.description
     *
     * @param description the value for training_course_type.description
     *
     * @mbggenerated Wed Sep 02 10:44:01 CST 2015
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column training_course_type.create_time
     *
     * @return the value of training_course_type.create_time
     *
     * @mbggenerated Wed Sep 02 10:44:01 CST 2015
     */
    public Long getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column training_course_type.create_time
     *
     * @param createTime the value for training_course_type.create_time
     *
     * @mbggenerated Wed Sep 02 10:44:01 CST 2015
     */
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column training_course_type.update_time
     *
     * @return the value of training_course_type.update_time
     *
     * @mbggenerated Wed Sep 02 10:44:01 CST 2015
     */
    public Long getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column training_course_type.update_time
     *
     * @param updateTime the value for training_course_type.update_time
     *
     * @mbggenerated Wed Sep 02 10:44:01 CST 2015
     */
    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column training_course_type.status
     *
     * @return the value of training_course_type.status
     *
     * @mbggenerated Wed Sep 02 10:44:01 CST 2015
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column training_course_type.status
     *
     * @param status the value for training_course_type.status
     *
     * @mbggenerated Wed Sep 02 10:44:01 CST 2015
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}
