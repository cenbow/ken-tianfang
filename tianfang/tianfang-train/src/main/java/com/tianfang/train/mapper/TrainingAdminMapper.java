package com.tianfang.train.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tianfang.train.pojo.TrainingAdmin;
import com.tianfang.train.pojo.TrainingAdminExample;

public interface TrainingAdminMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_admin
     *
     * @mbggenerated Fri Sep 18 15:04:43 CST 2015
     */
    int countByExample(TrainingAdminExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_admin
     *
     * @mbggenerated Fri Sep 18 15:04:43 CST 2015
     */
    int deleteByExample(TrainingAdminExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_admin
     *
     * @mbggenerated Fri Sep 18 15:04:43 CST 2015
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_admin
     *
     * @mbggenerated Fri Sep 18 15:04:43 CST 2015
     */
    int insert(TrainingAdmin record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_admin
     *
     * @mbggenerated Fri Sep 18 15:04:43 CST 2015
     */
    int insertSelective(TrainingAdmin record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_admin
     *
     * @mbggenerated Fri Sep 18 15:04:43 CST 2015
     */
    List<TrainingAdmin> selectByExample(TrainingAdminExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_admin
     *
     * @mbggenerated Fri Sep 18 15:04:43 CST 2015
     */
    TrainingAdmin selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_admin
     *
     * @mbggenerated Fri Sep 18 15:04:43 CST 2015
     */
    int updateByExampleSelective(@Param("record") TrainingAdmin record, @Param("example") TrainingAdminExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_admin
     *
     * @mbggenerated Fri Sep 18 15:04:43 CST 2015
     */
    int updateByExample(@Param("record") TrainingAdmin record, @Param("example") TrainingAdminExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_admin
     *
     * @mbggenerated Fri Sep 18 15:04:43 CST 2015
     */
    int updateByPrimaryKeySelective(TrainingAdmin record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_admin
     *
     * @mbggenerated Fri Sep 18 15:04:43 CST 2015
     */
    int updateByPrimaryKey(TrainingAdmin record);
}