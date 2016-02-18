package com.tianfang.business.mapper;

import com.tianfang.business.pojo.SportHomeMenu;
import com.tianfang.business.pojo.SportHomeMenuExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SportHomeMenuMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_home_menu
     *
     * @mbggenerated Wed Dec 23 09:52:50 CST 2015
     */
    int countByExample(SportHomeMenuExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_home_menu
     *
     * @mbggenerated Wed Dec 23 09:52:50 CST 2015
     */
    int deleteByExample(SportHomeMenuExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_home_menu
     *
     * @mbggenerated Wed Dec 23 09:52:50 CST 2015
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_home_menu
     *
     * @mbggenerated Wed Dec 23 09:52:50 CST 2015
     */
    int insert(SportHomeMenu record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_home_menu
     *
     * @mbggenerated Wed Dec 23 09:52:50 CST 2015
     */
    int insertSelective(SportHomeMenu record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_home_menu
     *
     * @mbggenerated Wed Dec 23 09:52:50 CST 2015
     */
    List<SportHomeMenu> selectByExample(SportHomeMenuExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_home_menu
     *
     * @mbggenerated Wed Dec 23 09:52:50 CST 2015
     */
    SportHomeMenu selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_home_menu
     *
     * @mbggenerated Wed Dec 23 09:52:50 CST 2015
     */
    int updateByExampleSelective(@Param("record") SportHomeMenu record, @Param("example") SportHomeMenuExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_home_menu
     *
     * @mbggenerated Wed Dec 23 09:52:50 CST 2015
     */
    int updateByExample(@Param("record") SportHomeMenu record, @Param("example") SportHomeMenuExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_home_menu
     *
     * @mbggenerated Wed Dec 23 09:52:50 CST 2015
     */
    int updateByPrimaryKeySelective(SportHomeMenu record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_home_menu
     *
     * @mbggenerated Wed Dec 23 09:52:50 CST 2015
     */
    int updateByPrimaryKey(SportHomeMenu record);
}