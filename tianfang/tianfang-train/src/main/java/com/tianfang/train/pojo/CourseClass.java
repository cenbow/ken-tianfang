package com.tianfang.train.pojo;

import java.math.BigDecimal;

public class CourseClass {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column training_course_class.id
     *
     * @mbggenerated Tue Sep 22 09:56:01 CST 2015
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column training_course_class.course_id
     *
     * @mbggenerated Tue Sep 22 09:56:01 CST 2015
     */
    private String courseId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column training_course_class.time_district_id
     *
     * @mbggenerated Tue Sep 22 09:56:01 CST 2015
     */
    private String timeDistrictId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column training_course_class.address_id
     *
     * @mbggenerated Tue Sep 22 09:56:01 CST 2015
     */
    private String addressId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column training_course_class.open_date
     *
     * @mbggenerated Tue Sep 22 09:56:01 CST 2015
     */
    private Long openDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column training_course_class.time_district
     *
     * @mbggenerated Tue Sep 22 09:56:01 CST 2015
     */
    private String timeDistrict;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column training_course_class.price
     *
     * @mbggenerated Tue Sep 22 09:56:01 CST 2015
     */
    private BigDecimal price;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column training_course_class.deposit
     *
     * @mbggenerated Tue Sep 22 09:56:01 CST 2015
     */
    private BigDecimal deposit;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column training_course_class.discount
     *
     * @mbggenerated Tue Sep 22 09:56:01 CST 2015
     */
    private BigDecimal discount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column training_course_class.notice
     *
     * @mbggenerated Tue Sep 22 09:56:01 CST 2015
     */
    private String notice;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column training_course_class.max_student
     *
     * @mbggenerated Tue Sep 22 09:56:01 CST 2015
     */
    private Integer maxStudent;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column training_course_class.actual_student
     *
     * @mbggenerated Tue Sep 22 09:56:01 CST 2015
     */
    private Integer actualStudent;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column training_course_class.create_time
     *
     * @mbggenerated Tue Sep 22 09:56:01 CST 2015
     */
    private Long createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column training_course_class.update_time
     *
     * @mbggenerated Tue Sep 22 09:56:01 CST 2015
     */
    private Long updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column training_course_class.startdate
     *
     * @mbggenerated Tue Sep 22 09:56:01 CST 2015
     */
    private Long startdate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column training_course_class.status
     *
     * @mbggenerated Tue Sep 22 09:56:01 CST 2015
     */
    private Integer status;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column training_course_class.id
     *
     * @return the value of training_course_class.id
     *
     * @mbggenerated Tue Sep 22 09:56:01 CST 2015
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column training_course_class.id
     *
     * @param id the value for training_course_class.id
     *
     * @mbggenerated Tue Sep 22 09:56:01 CST 2015
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column training_course_class.course_id
     *
     * @return the value of training_course_class.course_id
     *
     * @mbggenerated Tue Sep 22 09:56:01 CST 2015
     */
    public String getCourseId() {
        return courseId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column training_course_class.course_id
     *
     * @param courseId the value for training_course_class.course_id
     *
     * @mbggenerated Tue Sep 22 09:56:01 CST 2015
     */
    public void setCourseId(String courseId) {
        this.courseId = courseId == null ? null : courseId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column training_course_class.time_district_id
     *
     * @return the value of training_course_class.time_district_id
     *
     * @mbggenerated Tue Sep 22 09:56:01 CST 2015
     */
    public String getTimeDistrictId() {
        return timeDistrictId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column training_course_class.time_district_id
     *
     * @param timeDistrictId the value for training_course_class.time_district_id
     *
     * @mbggenerated Tue Sep 22 09:56:01 CST 2015
     */
    public void setTimeDistrictId(String timeDistrictId) {
        this.timeDistrictId = timeDistrictId == null ? null : timeDistrictId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column training_course_class.address_id
     *
     * @return the value of training_course_class.address_id
     *
     * @mbggenerated Tue Sep 22 09:56:01 CST 2015
     */
    public String getAddressId() {
        return addressId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column training_course_class.address_id
     *
     * @param addressId the value for training_course_class.address_id
     *
     * @mbggenerated Tue Sep 22 09:56:01 CST 2015
     */
    public void setAddressId(String addressId) {
        this.addressId = addressId == null ? null : addressId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column training_course_class.open_date
     *
     * @return the value of training_course_class.open_date
     *
     * @mbggenerated Tue Sep 22 09:56:01 CST 2015
     */
    public Long getOpenDate() {
        return openDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column training_course_class.open_date
     *
     * @param openDate the value for training_course_class.open_date
     *
     * @mbggenerated Tue Sep 22 09:56:01 CST 2015
     */
    public void setOpenDate(Long openDate) {
        this.openDate = openDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column training_course_class.time_district
     *
     * @return the value of training_course_class.time_district
     *
     * @mbggenerated Tue Sep 22 09:56:01 CST 2015
     */
    public String getTimeDistrict() {
        return timeDistrict;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column training_course_class.time_district
     *
     * @param timeDistrict the value for training_course_class.time_district
     *
     * @mbggenerated Tue Sep 22 09:56:01 CST 2015
     */
    public void setTimeDistrict(String timeDistrict) {
        this.timeDistrict = timeDistrict == null ? null : timeDistrict.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column training_course_class.price
     *
     * @return the value of training_course_class.price
     *
     * @mbggenerated Tue Sep 22 09:56:01 CST 2015
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column training_course_class.price
     *
     * @param price the value for training_course_class.price
     *
     * @mbggenerated Tue Sep 22 09:56:01 CST 2015
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column training_course_class.deposit
     *
     * @return the value of training_course_class.deposit
     *
     * @mbggenerated Tue Sep 22 09:56:01 CST 2015
     */
    public BigDecimal getDeposit() {
        return deposit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column training_course_class.deposit
     *
     * @param deposit the value for training_course_class.deposit
     *
     * @mbggenerated Tue Sep 22 09:56:01 CST 2015
     */
    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column training_course_class.discount
     *
     * @return the value of training_course_class.discount
     *
     * @mbggenerated Tue Sep 22 09:56:01 CST 2015
     */
    public BigDecimal getDiscount() {
        return discount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column training_course_class.discount
     *
     * @param discount the value for training_course_class.discount
     *
     * @mbggenerated Tue Sep 22 09:56:01 CST 2015
     */
    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column training_course_class.notice
     *
     * @return the value of training_course_class.notice
     *
     * @mbggenerated Tue Sep 22 09:56:01 CST 2015
     */
    public String getNotice() {
        return notice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column training_course_class.notice
     *
     * @param notice the value for training_course_class.notice
     *
     * @mbggenerated Tue Sep 22 09:56:01 CST 2015
     */
    public void setNotice(String notice) {
        this.notice = notice == null ? null : notice.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column training_course_class.max_student
     *
     * @return the value of training_course_class.max_student
     *
     * @mbggenerated Tue Sep 22 09:56:01 CST 2015
     */
    public Integer getMaxStudent() {
        return maxStudent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column training_course_class.max_student
     *
     * @param maxStudent the value for training_course_class.max_student
     *
     * @mbggenerated Tue Sep 22 09:56:01 CST 2015
     */
    public void setMaxStudent(Integer maxStudent) {
        this.maxStudent = maxStudent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column training_course_class.actual_student
     *
     * @return the value of training_course_class.actual_student
     *
     * @mbggenerated Tue Sep 22 09:56:01 CST 2015
     */
    public Integer getActualStudent() {
        return actualStudent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column training_course_class.actual_student
     *
     * @param actualStudent the value for training_course_class.actual_student
     *
     * @mbggenerated Tue Sep 22 09:56:01 CST 2015
     */
    public void setActualStudent(Integer actualStudent) {
        this.actualStudent = actualStudent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column training_course_class.create_time
     *
     * @return the value of training_course_class.create_time
     *
     * @mbggenerated Tue Sep 22 09:56:01 CST 2015
     */
    public Long getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column training_course_class.create_time
     *
     * @param createTime the value for training_course_class.create_time
     *
     * @mbggenerated Tue Sep 22 09:56:01 CST 2015
     */
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column training_course_class.update_time
     *
     * @return the value of training_course_class.update_time
     *
     * @mbggenerated Tue Sep 22 09:56:01 CST 2015
     */
    public Long getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column training_course_class.update_time
     *
     * @param updateTime the value for training_course_class.update_time
     *
     * @mbggenerated Tue Sep 22 09:56:01 CST 2015
     */
    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column training_course_class.startdate
     *
     * @return the value of training_course_class.startdate
     *
     * @mbggenerated Tue Sep 22 09:56:01 CST 2015
     */
    public Long getStartdate() {
        return startdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column training_course_class.startdate
     *
     * @param startdate the value for training_course_class.startdate
     *
     * @mbggenerated Tue Sep 22 09:56:01 CST 2015
     */
    public void setStartdate(Long startdate) {
        this.startdate = startdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column training_course_class.status
     *
     * @return the value of training_course_class.status
     *
     * @mbggenerated Tue Sep 22 09:56:01 CST 2015
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column training_course_class.status
     *
     * @param status the value for training_course_class.status
     *
     * @mbggenerated Tue Sep 22 09:56:01 CST 2015
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}