package com.tianfang.business.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tianfang.business.dto.SportTeamMembersDto;
import com.tianfang.business.pojo.SportTeamMembersExample;

public interface SportTeamMembersMapperX {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_team_members
     *
     * @mbggenerated Fri Nov 13 13:36:20 CST 2015
     */
    int countByExample(SportTeamMembersDto example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_team_members
     *
     * @mbggenerated Fri Nov 13 13:36:20 CST 2015
     */
    int deleteByExample(SportTeamMembersDto example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_team_members
     *
     * @mbggenerated Fri Nov 13 13:36:20 CST 2015
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_team_members
     *
     * @mbggenerated Fri Nov 13 13:36:20 CST 2015
     */
    int insert(SportTeamMembersDto record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_team_members
     *
     * @mbggenerated Fri Nov 13 13:36:20 CST 2015
     */
    int insertSelective(SportTeamMembersDto record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_team_members
     *
     * @mbggenerated Fri Nov 13 13:36:20 CST 2015
     */
    List<SportTeamMembersDto> selectByExample(SportTeamMembersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_team_members
     *
     * @mbggenerated Fri Nov 13 13:36:20 CST 2015
     */
    SportTeamMembersDto selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_team_members
     *
     * @mbggenerated Fri Nov 13 13:36:20 CST 2015
     */
    int updateByExampleSelective(@Param("record") SportTeamMembersDto record, @Param("example") SportTeamMembersDto example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_team_members
     *
     * @mbggenerated Fri Nov 13 13:36:20 CST 2015
     */
    int updateByExample(@Param("record") SportTeamMembersDto record, @Param("example") SportTeamMembersDto example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_team_members
     *
     * @mbggenerated Fri Nov 13 13:36:20 CST 2015
     */
    int updateByPrimaryKeySelective(SportTeamMembersDto record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_team_members
     *
     * @mbggenerated Fri Nov 13 13:36:20 CST 2015
     */
    int updateByPrimaryKey(SportTeamMembersDto record);
    
    /**
     * 根据球队id查询队员信息
     * @param tid
     * @return
     * @2015年11月16日
     */
    List<SportTeamMembersDto> findMembersBytid(String tid);
    
    /**
     * 逻辑删除队员信息
     * @param record
     * @return
     */
    Integer deleteMember(SportTeamMembersDto record);
    
    /**
     * 查询符合条件的记录数
     * @param dto
     * @return
     */
    Long selectSum(SportTeamMembersDto dto);
    
    /**
     * 根据条件查询数据
     * @param map
     * @return
     */
    List<SportTeamMembersDto> findMemByCons(Map<String, Object> map);
}