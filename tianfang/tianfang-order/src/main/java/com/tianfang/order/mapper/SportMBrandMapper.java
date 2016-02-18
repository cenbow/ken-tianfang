package com.tianfang.order.mapper;

import com.tianfang.order.pojo.SportMBrand;
import com.tianfang.order.pojo.SportMBrandExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SportMBrandMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_m_brand
     *
     * @mbggenerated Wed Jan 06 09:43:02 CST 2016
     */
    int countByExample(SportMBrandExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_m_brand
     *
     * @mbggenerated Wed Jan 06 09:43:02 CST 2016
     */
    int deleteByExample(SportMBrandExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_m_brand
     *
     * @mbggenerated Wed Jan 06 09:43:02 CST 2016
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_m_brand
     *
     * @mbggenerated Wed Jan 06 09:43:02 CST 2016
     */
    int insert(SportMBrand record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_m_brand
     *
     * @mbggenerated Wed Jan 06 09:43:02 CST 2016
     */
    int insertSelective(SportMBrand record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_m_brand
     *
     * @mbggenerated Wed Jan 06 09:43:02 CST 2016
     */
    List<SportMBrand> selectByExample(SportMBrandExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_m_brand
     *
     * @mbggenerated Wed Jan 06 09:43:02 CST 2016
     */
    SportMBrand selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_m_brand
     *
     * @mbggenerated Wed Jan 06 09:43:02 CST 2016
     */
    int updateByExampleSelective(@Param("record") SportMBrand record, @Param("example") SportMBrandExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_m_brand
     *
     * @mbggenerated Wed Jan 06 09:43:02 CST 2016
     */
    int updateByExample(@Param("record") SportMBrand record, @Param("example") SportMBrandExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_m_brand
     *
     * @mbggenerated Wed Jan 06 09:43:02 CST 2016
     */
    int updateByPrimaryKeySelective(SportMBrand record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_m_brand
     *
     * @mbggenerated Wed Jan 06 09:43:02 CST 2016
     */
    int updateByPrimaryKey(SportMBrand record);
}