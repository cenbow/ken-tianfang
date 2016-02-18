package com.tianfang.business.mapper;

import com.tianfang.business.pojo.SportTeamType;
import com.tianfang.business.pojo.SportTeamTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SportTeamTypeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_team_type
     *
     * @mbggenerated Tue Nov 17 15:40:03 CST 2015
     */
    int countByExample(SportTeamTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_team_type
     *
     * @mbggenerated Tue Nov 17 15:40:03 CST 2015
     */
    int deleteByExample(SportTeamTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_team_type
     *
     * @mbggenerated Tue Nov 17 15:40:03 CST 2015
     */
    int deleteByPrimaryKey(String typeId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_team_type
     *
     * @mbggenerated Tue Nov 17 15:40:03 CST 2015
     */
    int insert(SportTeamType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_team_type
     *
     * @mbggenerated Tue Nov 17 15:40:03 CST 2015
     */
    int insertSelective(SportTeamType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_team_type
     *
     * @mbggenerated Tue Nov 17 15:40:03 CST 2015
     */
    List<SportTeamType> selectByExample(SportTeamTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_team_type
     *
     * @mbggenerated Tue Nov 17 15:40:03 CST 2015
     */
    SportTeamType selectByPrimaryKey(String typeId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_team_type
     *
     * @mbggenerated Tue Nov 17 15:40:03 CST 2015
     */
    int updateByExampleSelective(@Param("record") SportTeamType record, @Param("example") SportTeamTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_team_type
     *
     * @mbggenerated Tue Nov 17 15:40:03 CST 2015
     */
    int updateByExample(@Param("record") SportTeamType record, @Param("example") SportTeamTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_team_type
     *
     * @mbggenerated Tue Nov 17 15:40:03 CST 2015
     */
    int updateByPrimaryKeySelective(SportTeamType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_team_type
     *
     * @mbggenerated Tue Nov 17 15:40:03 CST 2015
     */
    int updateByPrimaryKey(SportTeamType record);
}