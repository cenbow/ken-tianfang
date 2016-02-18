package com.tianfang.train.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tianfang.train.pojo.TrainingAuthorization;
import com.tianfang.train.pojo.TrainingAuthorizationExample;

public interface TrainingAuthorizationMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_authorization
     *
     * @mbggenerated Tue Sep 22 11:03:29 CST 2015
     */
    int countByExample(TrainingAuthorizationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_authorization
     *
     * @mbggenerated Tue Sep 22 11:03:29 CST 2015
     */
    int deleteByExample(TrainingAuthorizationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_authorization
     *
     * @mbggenerated Tue Sep 22 11:03:29 CST 2015
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_authorization
     *
     * @mbggenerated Tue Sep 22 11:03:29 CST 2015
     */
    int insert(TrainingAuthorization record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_authorization
     *
     * @mbggenerated Tue Sep 22 11:03:29 CST 2015
     */
    int insertSelective(TrainingAuthorization record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_authorization
     *
     * @mbggenerated Tue Sep 22 11:03:29 CST 2015
     */
    List<TrainingAuthorization> selectByExample(TrainingAuthorizationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_authorization
     *
     * @mbggenerated Tue Sep 22 11:03:29 CST 2015
     */
    TrainingAuthorization selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_authorization
     *
     * @mbggenerated Tue Sep 22 11:03:29 CST 2015
     */
    int updateByExampleSelective(@Param("record") TrainingAuthorization record, @Param("example") TrainingAuthorizationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_authorization
     *
     * @mbggenerated Tue Sep 22 11:03:29 CST 2015
     */
    int updateByExample(@Param("record") TrainingAuthorization record, @Param("example") TrainingAuthorizationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_authorization
     *
     * @mbggenerated Tue Sep 22 11:03:29 CST 2015
     */
    int updateByPrimaryKeySelective(TrainingAuthorization record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_authorization
     *
     * @mbggenerated Tue Sep 22 11:03:29 CST 2015
     */
    int updateByPrimaryKey(TrainingAuthorization record);
}