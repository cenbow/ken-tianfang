package com.tianfang.order.service;

import java.util.List;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.order.dto.SportMEvaluateDto;
import com.tianfang.order.dto.SportMOrderInfoDto;
import com.tianfang.order.dto.SportMProductSpuDto;

public interface ISportMProductSpuService {

	long save(SportMProductSpuDto spu);

	long edit(SportMProductSpuDto spu);

	long delete(String id);

	PageResult<SportMProductSpuDto> selectPageAll(SportMProductSpuDto spu,PageQuery page);

	List<SportMProductSpuDto> selectByCriteria(SportMProductSpuDto spuDto);

	PageResult<SportMProductSpuDto> selectPageByCriteria(
			SportMProductSpuDto spuDto, PageQuery changeToPageQuery);

	public SportMProductSpuDto findProductById(String id);
	
	public PageResult<SportMOrderInfoDto> selectOrderBySpu(String id,PageQuery page) ;
	
	public PageResult<SportMEvaluateDto> selectEvaluate(String productId,Integer evaluateStatus,String pci,PageQuery page);

	SportMProductSpuDto selectSpuById(String spuId);

	long spuStatus(SportMProductSpuDto spu);

}
