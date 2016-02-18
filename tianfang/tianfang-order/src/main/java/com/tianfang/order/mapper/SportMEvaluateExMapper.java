package com.tianfang.order.mapper;

import org.apache.ibatis.annotations.Param;

public interface SportMEvaluateExMapper {
	
	long countStar(@Param("productSkuId") String productSkuId);
}
