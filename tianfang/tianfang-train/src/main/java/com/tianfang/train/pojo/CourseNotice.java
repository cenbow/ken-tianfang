package com.tianfang.train.pojo;

public class CourseNotice {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column training_course_notice.id
     *
     * @mbggenerated Fri Sep 18 15:04:43 CST 2015
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column training_course_notice.course_id
     *
     * @mbggenerated Fri Sep 18 15:04:43 CST 2015
     */
    private String courseId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column training_course_notice.notice_info
     *
     * @mbggenerated Fri Sep 18 15:04:43 CST 2015
     */
    private String noticeInfo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column training_course_notice.create_time
     *
     * @mbggenerated Fri Sep 18 15:04:43 CST 2015
     */
    private Long createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column training_course_notice.update_time
     *
     * @mbggenerated Fri Sep 18 15:04:43 CST 2015
     */
    private Long updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column training_course_notice.status
     *
     * @mbggenerated Fri Sep 18 15:04:43 CST 2015
     */
    private Integer status;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column training_course_notice.id
     *
     * @return the value of training_course_notice.id
     *
     * @mbggenerated Fri Sep 18 15:04:43 CST 2015
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column training_course_notice.id
     *
     * @param id the value for training_course_notice.id
     *
     * @mbggenerated Fri Sep 18 15:04:43 CST 2015
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column training_course_notice.course_id
     *
     * @return the value of training_course_notice.course_id
     *
     * @mbggenerated Fri Sep 18 15:04:43 CST 2015
     */
    public String getCourseId() {
        return courseId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column training_course_notice.course_id
     *
     * @param courseId the value for training_course_notice.course_id
     *
     * @mbggenerated Fri Sep 18 15:04:43 CST 2015
     */
    public void setCourseId(String courseId) {
        this.courseId = courseId == null ? null : courseId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column training_course_notice.notice_info
     *
     * @return the value of training_course_notice.notice_info
     *
     * @mbggenerated Fri Sep 18 15:04:43 CST 2015
     */
    public String getNoticeInfo() {
        return noticeInfo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column training_course_notice.notice_info
     *
     * @param noticeInfo the value for training_course_notice.notice_info
     *
     * @mbggenerated Fri Sep 18 15:04:43 CST 2015
     */
    public void setNoticeInfo(String noticeInfo) {
        this.noticeInfo = noticeInfo == null ? null : noticeInfo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column training_course_notice.create_time
     *
     * @return the value of training_course_notice.create_time
     *
     * @mbggenerated Fri Sep 18 15:04:43 CST 2015
     */
    public Long getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column training_course_notice.create_time
     *
     * @param createTime the value for training_course_notice.create_time
     *
     * @mbggenerated Fri Sep 18 15:04:43 CST 2015
     */
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column training_course_notice.update_time
     *
     * @return the value of training_course_notice.update_time
     *
     * @mbggenerated Fri Sep 18 15:04:43 CST 2015
     */
    public Long getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column training_course_notice.update_time
     *
     * @param updateTime the value for training_course_notice.update_time
     *
     * @mbggenerated Fri Sep 18 15:04:43 CST 2015
     */
    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column training_course_notice.status
     *
     * @return the value of training_course_notice.status
     *
     * @mbggenerated Fri Sep 18 15:04:43 CST 2015
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column training_course_notice.status
     *
     * @param status the value for training_course_notice.status
     *
     * @mbggenerated Fri Sep 18 15:04:43 CST 2015
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}