package com.tianfang.train.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tianfang.train.pojo.TrainingAddressDistrictTime;
import com.tianfang.train.pojo.TrainingAddressDistrictTimeExample;

public interface TrainingAddressDistrictTimeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_address_district_time
     *
     * @mbggenerated Fri Sep 18 15:04:43 CST 2015
     */
    int countByExample(TrainingAddressDistrictTimeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_address_district_time
     *
     * @mbggenerated Fri Sep 18 15:04:43 CST 2015
     */
    int deleteByExample(TrainingAddressDistrictTimeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_address_district_time
     *
     * @mbggenerated Fri Sep 18 15:04:43 CST 2015
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_address_district_time
     *
     * @mbggenerated Fri Sep 18 15:04:43 CST 2015
     */
    int insert(TrainingAddressDistrictTime record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_address_district_time
     *
     * @mbggenerated Fri Sep 18 15:04:43 CST 2015
     */
    int insertSelective(TrainingAddressDistrictTime record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_address_district_time
     *
     * @mbggenerated Fri Sep 18 15:04:43 CST 2015
     */
    List<TrainingAddressDistrictTime> selectByExample(TrainingAddressDistrictTimeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_address_district_time
     *
     * @mbggenerated Fri Sep 18 15:04:43 CST 2015
     */
    TrainingAddressDistrictTime selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_address_district_time
     *
     * @mbggenerated Fri Sep 18 15:04:43 CST 2015
     */
    int updateByExampleSelective(@Param("record") TrainingAddressDistrictTime record, @Param("example") TrainingAddressDistrictTimeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_address_district_time
     *
     * @mbggenerated Fri Sep 18 15:04:43 CST 2015
     */
    int updateByExample(@Param("record") TrainingAddressDistrictTime record, @Param("example") TrainingAddressDistrictTimeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_address_district_time
     *
     * @mbggenerated Fri Sep 18 15:04:43 CST 2015
     */
    int updateByPrimaryKeySelective(TrainingAddressDistrictTime record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_address_district_time
     *
     * @mbggenerated Fri Sep 18 15:04:43 CST 2015
     */
    int updateByPrimaryKey(TrainingAddressDistrictTime record);
}