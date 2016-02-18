package com.tianfang.train.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tianfang.train.pojo.TrainingMessageInfo;
import com.tianfang.train.pojo.TrainingMessageInfoExample;

public interface TrainingMessageInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_message_info
     *
     * @mbggenerated Tue Sep 22 14:40:22 CST 2015
     */
    int countByExample(TrainingMessageInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_message_info
     *
     * @mbggenerated Tue Sep 22 14:40:22 CST 2015
     */
    int deleteByExample(TrainingMessageInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_message_info
     *
     * @mbggenerated Tue Sep 22 14:40:22 CST 2015
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_message_info
     *
     * @mbggenerated Tue Sep 22 14:40:22 CST 2015
     */
    int insert(TrainingMessageInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_message_info
     *
     * @mbggenerated Tue Sep 22 14:40:22 CST 2015
     */
    int insertSelective(TrainingMessageInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_message_info
     *
     * @mbggenerated Tue Sep 22 14:40:22 CST 2015
     */
    List<TrainingMessageInfo> selectByExample(TrainingMessageInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_message_info
     *
     * @mbggenerated Tue Sep 22 14:40:22 CST 2015
     */
    TrainingMessageInfo selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_message_info
     *
     * @mbggenerated Tue Sep 22 14:40:22 CST 2015
     */
    int updateByExampleSelective(@Param("record") TrainingMessageInfo record, @Param("example") TrainingMessageInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_message_info
     *
     * @mbggenerated Tue Sep 22 14:40:22 CST 2015
     */
    int updateByExample(@Param("record") TrainingMessageInfo record, @Param("example") TrainingMessageInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_message_info
     *
     * @mbggenerated Tue Sep 22 14:40:22 CST 2015
     */
    int updateByPrimaryKeySelective(TrainingMessageInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table training_message_info
     *
     * @mbggenerated Tue Sep 22 14:40:22 CST 2015
     */
    int updateByPrimaryKey(TrainingMessageInfo record);
}