package com.tianfang.company.mapper;

import com.tianfang.company.pojo.SportCompanyEvent;
import com.tianfang.company.pojo.SportCompanyEventExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SportCompanyEventMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_company_event
     *
     * @mbggenerated Fri Dec 04 11:21:50 CST 2015
     */
    int countByExample(SportCompanyEventExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_company_event
     *
     * @mbggenerated Fri Dec 04 11:21:50 CST 2015
     */
    int deleteByExample(SportCompanyEventExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_company_event
     *
     * @mbggenerated Fri Dec 04 11:21:50 CST 2015
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_company_event
     *
     * @mbggenerated Fri Dec 04 11:21:50 CST 2015
     */
    int insert(SportCompanyEvent record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_company_event
     *
     * @mbggenerated Fri Dec 04 11:21:50 CST 2015
     */
    int insertSelective(SportCompanyEvent record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_company_event
     *
     * @mbggenerated Fri Dec 04 11:21:50 CST 2015
     */
    List<SportCompanyEvent> selectByExample(SportCompanyEventExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_company_event
     *
     * @mbggenerated Fri Dec 04 11:21:50 CST 2015
     */
    SportCompanyEvent selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_company_event
     *
     * @mbggenerated Fri Dec 04 11:21:50 CST 2015
     */
    int updateByExampleSelective(@Param("record") SportCompanyEvent record, @Param("example") SportCompanyEventExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_company_event
     *
     * @mbggenerated Fri Dec 04 11:21:50 CST 2015
     */
    int updateByExample(@Param("record") SportCompanyEvent record, @Param("example") SportCompanyEventExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_company_event
     *
     * @mbggenerated Fri Dec 04 11:21:50 CST 2015
     */
    int updateByPrimaryKeySelective(SportCompanyEvent record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_company_event
     *
     * @mbggenerated Fri Dec 04 11:21:50 CST 2015
     */
    int updateByPrimaryKey(SportCompanyEvent record);
}