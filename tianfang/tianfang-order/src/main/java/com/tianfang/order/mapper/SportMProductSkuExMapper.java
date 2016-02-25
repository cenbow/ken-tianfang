package com.tianfang.order.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tianfang.common.model.PageQuery;
import com.tianfang.order.dto.SportMEvaluateDto;
import com.tianfang.order.dto.SportMOrderInfoDto;
import com.tianfang.order.dto.SportMProductSkuDto;
import com.tianfang.order.dto.SportMProductSkuSpecValuesDto;
import com.tianfang.order.dto.SportMProductSpecDto;
import com.tianfang.order.dto.SportMProductSpecValuesDto;

public interface SportMProductSkuExMapper {
	
	List<SportMProductSkuDto> selectProductSkuByPage(@Param("sportMProductSkuDto") SportMProductSkuDto sportMProductSkuDto,@Param("page") PageQuery page);	
	
	long selectProductSkuCount(@Param("sportMProductSkuDto") SportMProductSkuDto sportMProductSkuDto);
	
	List<SportMOrderInfoDto> selectOrderBySpu(@Param("productId") String productId,@Param("page") PageQuery page);
	
	long selectOrderBySpuCount(@Param("productId") String productId);
	
	List<SportMEvaluateDto> selectEvaluate(@Param("sportMEvaluateDto") SportMEvaluateDto sportMEvaluateDto,@Param("page") PageQuery page);
	
	long selectEvaluateCount(@Param("sportMEvaluateDto") SportMEvaluateDto sportMEvaluateDto);
	
	List<SportMProductSkuSpecValuesDto> selectSkuSpecValues(@Param("productSkuId") String productSkuId);
	
	List<SportMProductSpecDto> selectSkuSpecGroup(@Param("productSkuId") String productSkuId);
	
	List<SportMProductSpecValuesDto> selectSkuSpecValuesGroup(@Param("productSkuId") String productSkuId);
}
