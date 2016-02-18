package com.tianfang.train.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tianfang.train.pojo.TrainingAddress;
import com.tianfang.train.pojo.TrainingAddressExample;

public interface TrainingAddressMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_address
     *
     * @mbggenerated Sun Oct 11 11:37:44 CST 2015
     */
    int countByExample(TrainingAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_address
     *
     * @mbggenerated Sun Oct 11 11:37:44 CST 2015
     */
    int deleteByExample(TrainingAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_address
     *
     * @mbggenerated Sun Oct 11 11:37:44 CST 2015
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_address
     *
     * @mbggenerated Sun Oct 11 11:37:44 CST 2015
     */
    int insert(TrainingAddress record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_address
     *
     * @mbggenerated Sun Oct 11 11:37:44 CST 2015
     */
    int insertSelective(TrainingAddress record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_address
     *
     * @mbggenerated Sun Oct 11 11:37:44 CST 2015
     */
    List<TrainingAddress> selectByExample(TrainingAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_address
     *
     * @mbggenerated Sun Oct 11 11:37:44 CST 2015
     */
    TrainingAddress selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_address
     *
     * @mbggenerated Sun Oct 11 11:37:44 CST 2015
     */
    int updateByExampleSelective(@Param("record") TrainingAddress record, @Param("example") TrainingAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_address
     *
     * @mbggenerated Sun Oct 11 11:37:44 CST 2015
     */
    int updateByExample(@Param("record") TrainingAddress record, @Param("example") TrainingAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_address
     *
     * @mbggenerated Sun Oct 11 11:37:44 CST 2015
     */
    int updateByPrimaryKeySelective(TrainingAddress record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_address
     *
     * @mbggenerated Sun Oct 11 11:37:44 CST 2015
     */
    int updateByPrimaryKey(TrainingAddress record);
}