package com.tianfang.business.mapper;

import com.tianfang.business.pojo.SportAddresses;
import com.tianfang.business.pojo.SportAddressesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SportAddressesMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_addresses
     *
     * @mbggenerated Tue Nov 17 15:41:35 CST 2015
     */
    int countByExample(SportAddressesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_addresses
     *
     * @mbggenerated Tue Nov 17 15:41:35 CST 2015
     */
    int deleteByExample(SportAddressesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_addresses
     *
     * @mbggenerated Tue Nov 17 15:41:35 CST 2015
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_addresses
     *
     * @mbggenerated Tue Nov 17 15:41:35 CST 2015
     */
    int insert(SportAddresses record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_addresses
     *
     * @mbggenerated Tue Nov 17 15:41:35 CST 2015
     */
    int insertSelective(SportAddresses record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_addresses
     *
     * @mbggenerated Tue Nov 17 15:41:35 CST 2015
     */
    List<SportAddresses> selectByExample(SportAddressesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_addresses
     *
     * @mbggenerated Tue Nov 17 15:41:35 CST 2015
     */
    SportAddresses selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_addresses
     *
     * @mbggenerated Tue Nov 17 15:41:35 CST 2015
     */
    int updateByExampleSelective(@Param("record") SportAddresses record, @Param("example") SportAddressesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_addresses
     *
     * @mbggenerated Tue Nov 17 15:41:35 CST 2015
     */
    int updateByExample(@Param("record") SportAddresses record, @Param("example") SportAddressesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_addresses
     *
     * @mbggenerated Tue Nov 17 15:41:35 CST 2015
     */
    int updateByPrimaryKeySelective(SportAddresses record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_addresses
     *
     * @mbggenerated Tue Nov 17 15:41:35 CST 2015
     */
    int updateByPrimaryKey(SportAddresses record);
}