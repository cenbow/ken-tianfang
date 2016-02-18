package com.tianfang.order.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tianfang.order.dto.SportMShoppingCartDto;
import com.tianfang.order.pojo.SportMProductSku;

/**
 * 购物车
 * @author mr
 *
 */
public interface SportMShppingCarExMapper {

	List<SportMProductSku> selectByProduct(@Param("car")SportMShoppingCartDto car);

}
