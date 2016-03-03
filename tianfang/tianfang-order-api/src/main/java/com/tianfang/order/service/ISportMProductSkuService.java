package com.tianfang.order.service;

import java.util.List;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.order.dto.SportMBrandDto;
import com.tianfang.order.dto.SportMCategoryDto;
import com.tianfang.order.dto.SportMProductSkuDto;
import com.tianfang.order.dto.SportMProductSkuSpecValuesDto;
import com.tianfang.order.dto.SportMProductSpuDto;
import com.tianfang.order.dto.SportMSpecDto;
import com.tianfang.order.dto.SportMSpecProductDto;
import com.tianfang.order.dto.SportMSpecValuesDto;
import com.tianfang.order.dto.SportMTypeDto;

public interface ISportMProductSkuService {

	public PageResult<SportMProductSkuDto> findPage(SportMProductSkuDto sportMProductSkuDto, PageQuery page);
	
	public Integer save(SportMProductSkuDto sportMProductSkuDto,List<SportMSpecValuesDto> specValuesDtos);
	
	public SportMProductSkuDto findById(String id);
	
	public Object delete(String ids);
	
	public List<SportMProductSkuDto> findAllSku();
	
	public List<SportMProductSpuDto> findAllSpu();
	
	public List<SportMSpecDto> findAllSpec();
	
	public List<SportMBrandDto> findAllBrand();
	
	public List<SportMTypeDto> findAllType();
	
	public List<SportMCategoryDto> findByTypeId (String typeId);
	
	public List<SportMSpecDto> findSpecByTypeId(String typeId);
	
	public List<SportMSpecValuesDto> findValueBySpecId(String specId);

	public SportMProductSpuDto selectById(String id);
	
	public List<SportMSpecProductDto> findAllSkuByProductId(String productId);
	
	public List<SportMProductSkuSpecValuesDto> selectSkuSpecValues(String productSkuId) ;
	
	public List<SportMProductSkuDto> findSkuByProduct(String productId);

	public boolean selectSkuByCriteria(SportMProductSkuDto skuDto);
	
}

