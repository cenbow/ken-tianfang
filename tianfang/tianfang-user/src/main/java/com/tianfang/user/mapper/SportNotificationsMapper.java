package com.tianfang.user.mapper;

import com.tianfang.user.pojo.SportNotifications;
import com.tianfang.user.pojo.SportNotificationsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SportNotificationsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_notifications
     *
     * @mbggenerated Wed Dec 02 15:16:46 CST 2015
     */
    int countByExample(SportNotificationsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_notifications
     *
     * @mbggenerated Wed Dec 02 15:16:46 CST 2015
     */
    int deleteByExample(SportNotificationsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_notifications
     *
     * @mbggenerated Wed Dec 02 15:16:46 CST 2015
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_notifications
     *
     * @mbggenerated Wed Dec 02 15:16:46 CST 2015
     */
    int insert(SportNotifications record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_notifications
     *
     * @mbggenerated Wed Dec 02 15:16:46 CST 2015
     */
    int insertSelective(SportNotifications record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_notifications
     *
     * @mbggenerated Wed Dec 02 15:16:46 CST 2015
     */
    List<SportNotifications> selectByExample(SportNotificationsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_notifications
     *
     * @mbggenerated Wed Dec 02 15:16:46 CST 2015
     */
    SportNotifications selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_notifications
     *
     * @mbggenerated Wed Dec 02 15:16:46 CST 2015
     */
    int updateByExampleSelective(@Param("record") SportNotifications record, @Param("example") SportNotificationsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_notifications
     *
     * @mbggenerated Wed Dec 02 15:16:46 CST 2015
     */
    int updateByExample(@Param("record") SportNotifications record, @Param("example") SportNotificationsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_notifications
     *
     * @mbggenerated Wed Dec 02 15:16:46 CST 2015
     */
    int updateByPrimaryKeySelective(SportNotifications record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_notifications
     *
     * @mbggenerated Wed Dec 02 15:16:46 CST 2015
     */
    int updateByPrimaryKey(SportNotifications record);
}