package com.tianfang.order.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.order.dao.SportMBrandDao;
import com.tianfang.order.dao.SportMCategoryDao;
import com.tianfang.order.dao.SportMProductSkuDao;
import com.tianfang.order.dao.SportMProductSpecDao;
import com.tianfang.order.dao.SportMProductSpecValuesDao;
import com.tianfang.order.dao.SportMProductSpuDao;
import com.tianfang.order.dao.SportMSpecDao;
import com.tianfang.order.dao.SportMSpecValuesDao;
import com.tianfang.order.dao.SportMTypeDao;
import com.tianfang.order.dao.SportMTypeSpecDao;
import com.tianfang.order.dto.SportMBrandDto;
import com.tianfang.order.dto.SportMCategoryDto;
import com.tianfang.order.dto.SportMProductSkuDto;
import com.tianfang.order.dto.SportMProductSkuSpecValuesDto;
import com.tianfang.order.dto.SportMProductSpecDto;
import com.tianfang.order.dto.SportMProductSpecValuesDto;
import com.tianfang.order.dto.SportMProductSpuDto;
import com.tianfang.order.dto.SportMSpecDto;
import com.tianfang.order.dto.SportMSpecProductDto;
import com.tianfang.order.dto.SportMSpecValuesDto;
import com.tianfang.order.dto.SportMTypeDto;
import com.tianfang.order.pojo.SportMProductSku;
import com.tianfang.order.pojo.SportMProductSpec;
import com.tianfang.order.pojo.SportMProductSpecValues;
import com.tianfang.order.pojo.SportMProductSpu;
import com.tianfang.order.pojo.SportMSpec;
import com.tianfang.order.pojo.SportMSpecValues;
import com.tianfang.order.service.ISportMProductSkuService;

@Service
public class SportMProductSkuImpl implements ISportMProductSkuService{

	@Autowired
	private SportMProductSkuDao sportMProductSkuDao;
	
	@Autowired
	private SportMProductSpuDao sportMProductSpuDao;
	
	@Autowired
	private SportMBrandDao sportMBrandDao;
	
	@Autowired
	private SportMTypeDao sportMTypeDao;
	
	@Autowired
	private SportMCategoryDao sportMCategoryDao;
	
	@Autowired
	private SportMTypeSpecDao sportMTypeSpecDao;
	
	@Autowired
	private SportMSpecValuesDao sportMSpecValuesDao;
	
	@Autowired
	private SportMProductSpecDao sportMProductSpecDao;
	
	@Autowired
	private SportMProductSpecValuesDao sportMProductSpecValuesDao;
	
	@Autowired
	private SportMSpecDao sportMSpecDao;	
	
	public PageResult<SportMProductSkuDto> findPage(SportMProductSkuDto sportMProductSkuDto, PageQuery page) {
		List<SportMProductSkuDto> results = sportMProductSkuDao.findPage(sportMProductSkuDto, page);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (SportMProductSkuDto sportmProductSkuDto:results) {
			sportmProductSkuDto.setCreateDate(sdf.format(sportmProductSkuDto.getCreateTime()));
		}
		page.setTotal(sportMProductSkuDao.count(sportMProductSkuDto));
		return new PageResult<SportMProductSkuDto>(page, results);
	}
	
	public List<SportMProductSpuDto> findAllSpu(){
		SportMProductSpuDto sportMProductSpuDto = new SportMProductSpuDto();
		List<SportMProductSpuDto> sportMProductSpuDtos = sportMProductSpuDao.selectPageAllEx(sportMProductSpuDto,null);
		return sportMProductSpuDtos;
	}
	
	public List<SportMBrandDto> findAllBrand(){
		List<SportMBrandDto> sportMBrandDtos = sportMBrandDao.findPage(null, null);
		return sportMBrandDtos;
	}
	
	public List<SportMTypeDto> findAllType() {
		List<SportMTypeDto> sportMTypeDtos = sportMTypeDao.findAllType();
		return sportMTypeDtos;
	}
	
	public List<SportMSpecDto> findAllSpec(){
		List<SportMSpec> sportMSpecs = sportMSpecDao.selectAll();
		return BeanUtils.createBeanListByTarget(sportMSpecs, SportMSpecDto.class);
	}
	
	public List<SportMCategoryDto> findByTypeId (String typeId) {
		List<SportMCategoryDto> categoryDtos = sportMCategoryDao.findByTypeId(typeId);
		return categoryDtos;
	}
	
	public List<SportMSpecDto> findSpecByTypeId(String typeId) {
		return sportMTypeSpecDao.findSpecByTypeId(typeId);		
	}
	
	public List<SportMSpecValuesDto> findValueBySpecId(String specId) {
		return sportMSpecValuesDao.findValueBySpecId(specId);
	}
	
	public List<SportMProductSkuSpecValuesDto> selectSkuSpecValues(String productSkuId) {
		 List<SportMProductSkuSpecValuesDto> specValuesDtos = sportMProductSkuDao.selectSkuSpecValues(productSkuId);
		 List<SportMProductSkuSpecValuesDto> sportMProductSkuSpecValuesDtos = new ArrayList<SportMProductSkuSpecValuesDto>();
		 for (SportMProductSkuSpecValuesDto sportMProductSkuSpecValuesDto : specValuesDtos) {
			 SportMSpecValuesDto specValuesDto = sportMSpecValuesDao.findByIdAndSpecId(sportMProductSkuSpecValuesDto.getSpecValuesId(), sportMProductSkuSpecValuesDto.getSpecId());
			 if (null != specValuesDto && StringUtils.isNotBlank(specValuesDto.getSpecId())) {
				 sportMProductSkuSpecValuesDtos.add(sportMProductSkuSpecValuesDto);
			 }
		 }
		 return sportMProductSkuSpecValuesDtos;
	}
	
	public Integer save(SportMProductSkuDto sportMProductSkuDto,List<SportMSpecValuesDto> specValuesDtos) {
		
		if (StringUtils.isNotBlank(sportMProductSkuDto.getId())) {
			SportMProductSku sportMProductSku = BeanUtils.createBeanByTarget(sportMProductSkuDto, SportMProductSku.class);
			StringBuffer specName = new StringBuffer();
			StringBuffer specValue = new StringBuffer();
			for (int j=0; j<specValuesDtos.size(); j++) {
				SportMSpec specMSpec = sportMSpecDao.selectById(specValuesDtos.get(j).getSpecId());
				SportMSpecValues specValues = sportMSpecValuesDao.selectByPrimaryKey(specValuesDtos.get(j).getId());
				specName.append(specMSpec.getSpecName());
				specValue.append(specValues.getSpecValue());
				if (j == specValuesDtos.size()-1) {
					break;
				}
				specName.append("/");
				specValue.append("/");
			}
			sportMProductSku.setSpecName(specName.toString());
			sportMProductSku.setSpecValue(specValue.toString());			
			List<SportMProductSpec> sportMProductSpecs = sportMProductSpecDao.findBySkuId(sportMProductSkuDto.getId());
			for (SportMProductSpec sportMProductSpec :sportMProductSpecs) {
				sportMProductSpecDao.deleteByPrimaryKey(sportMProductSpec.getId());
			}
			List<SportMProductSpecValues>  sportMProductSpecValuess = sportMProductSpecValuesDao.findBySukId(sportMProductSkuDto.getId());
			for (SportMProductSpecValues sportMProductSpecValues : sportMProductSpecValuess) {
				sportMProductSpecValuesDao.deleteByPrimaryKey(sportMProductSpecValues.getId());
			}
			for (SportMSpecValuesDto sportMSpecValuesDto : specValuesDtos) {
				SportMProductSpec sportMProductSpec = new SportMProductSpec();
				sportMProductSpec.setProductSkuId(sportMProductSku.getId());
				sportMProductSpec.setSpecId(sportMSpecValuesDto.getSpecId());
				sportMProductSpecDao.insert(sportMProductSpec);
				SportMProductSpecValues specValues = new SportMProductSpecValues();
				specValues.setSpecValuesId(sportMSpecValuesDto.getId());
				specValues.setProductSkuId(sportMProductSku.getId());
				sportMProductSpecValuesDao.insert(specValues);
			}
			SportMProductSpu sportMProductSpu = sportMProductSpuDao.selectByPrimaryKey(sportMProductSkuDto.getProductId());
			SportMProductSku sportmProductSku = sportMProductSkuDao.selectByPrimaryKey(sportMProductSku.getId());
			sportMProductSkuDao.updateByPrimaryKeySelective(sportMProductSku);
			sportMProductSpu.setProductStock(sportMProductSkuDto.getProductStock()-sportmProductSku.getProductStock()+sportMProductSpu.getProductStock());
			return sportMProductSpuDao.updateByPrimaryKeySelective(sportMProductSpu);
		}
		SportMProductSku sportMProductSku = BeanUtils.createBeanByTarget(sportMProductSkuDto, SportMProductSku.class);
		StringBuffer specName = new StringBuffer();
		StringBuffer specValue = new StringBuffer();
		for (int j=0; j<specValuesDtos.size(); j++) {
			SportMSpec specMSpec = sportMSpecDao.selectById(specValuesDtos.get(j).getSpecId());
			SportMSpecValues specValues = sportMSpecValuesDao.selectByPrimaryKey(specValuesDtos.get(j).getId());
			specName.append(specMSpec.getSpecName());
			specValue.append(specValues.getSpecValue());
			if (j == specValuesDtos.size()-1) {
				break;
			}
			specName.append("/");
			specValue.append("/");
		}
		sportMProductSku.setSpecName(specName.toString());
		sportMProductSku.setSpecValue(specValue.toString());
		sportMProductSkuDao.insert(sportMProductSku);
		for (SportMSpecValuesDto sportMSpecValuesDto : specValuesDtos) {
			SportMProductSpec sportMProductSpec = new SportMProductSpec();
			sportMProductSpec.setProductSkuId(sportMProductSku.getId());
			sportMProductSpec.setSpecId(sportMSpecValuesDto.getSpecId());
			sportMProductSpecDao.insert(sportMProductSpec);
			SportMProductSpecValues specValues = new SportMProductSpecValues();
			specValues.setSpecValuesId(sportMSpecValuesDto.getId());
			specValues.setProductSkuId(sportMProductSku.getId());
			sportMProductSpecValuesDao.insert(specValues);
		}
		SportMProductSpu sportMProductSpu = sportMProductSpuDao.selectByPrimaryKey(sportMProductSkuDto.getProductId());
		if (null != sportMProductSpu && null != sportMProductSpu.getProductStock()) {
			sportMProductSpu.setProductStock(sportMProductSpu.getProductStock()+sportMProductSkuDto.getProductStock());
		} else {
			sportMProductSpu.setProductStock(sportMProductSkuDto.getProductStock());
		}
		
		return sportMProductSpuDao.updateByPrimaryKeySelective(sportMProductSpu);		
	}
	
	public SportMProductSkuDto findById(String id) {
		SportMProductSku sportMProductSku = sportMProductSkuDao.selectByPrimaryKey(id);
		SportMProductSkuDto sportMProductSkuDto = BeanUtils.createBeanByTarget(sportMProductSku, SportMProductSkuDto.class);
		return sportMProductSkuDto;
	}
	
	public Object delete(String ids) {
		String[] idStr = ids.split(",");
		if (idStr.length > 0) {
			for (String id : idStr) {
				SportMProductSku sportMProductSku = sportMProductSkuDao.selectByPrimaryKey(id);
				sportMProductSku.setStat(DataStatus.DISABLED);
				sportMProductSkuDao.updateByPrimaryKeySelective(sportMProductSku);
				SportMProductSpu sportMProductSpu = sportMProductSpuDao.selectByPrimaryKey(sportMProductSku.getProductId());
				sportMProductSpu.setProductStock(sportMProductSpu.getProductStock()-1);
				sportMProductSpuDao.updateByPrimaryKeySelective(sportMProductSpu);
			}
		}
		return null;
	}

	@Override
	public SportMProductSpuDto selectById(String id) {
		return sportMProductSpuDao.findAllSpuById(id);
	}

	@Override
	public List<SportMProductSkuDto> findAllSku() {
		List<SportMProductSku> sukLis= sportMProductSkuDao.findAllSku();
		return BeanUtils.createBeanListByTarget(sukLis, SportMProductSkuDto.class);
	}
	
	public List<SportMSpecProductDto> findAllSkuByProductId(String productId){
		List<SportMProductSkuDto> sportMProductSkuDtos = sportMProductSkuDao.findAllSkuByProductId(productId);
		Set<String> setSpecId = new HashSet<String>();
		Set<String> setSpecValueId = new HashSet<String>();
		if (sportMProductSkuDtos.size() > 0) {
			for (SportMProductSkuDto sportMProductSkuDto : sportMProductSkuDtos) {
				List<SportMProductSpecDto> sportMProductSpecDtos = sportMProductSkuDao.selectSkuSpecGroup(sportMProductSkuDto.getId());
				for (SportMProductSpecDto sportMProductSpecDto :sportMProductSpecDtos) {
					setSpecId.add(sportMProductSpecDto.getSpecId());
				}
				List<SportMProductSpecValuesDto> sportMProductSpecValuesDtos =  sportMProductSkuDao.selectSkuSpecValuesGroup(sportMProductSkuDto.getId());
				for (SportMProductSpecValuesDto sportMProductSpecValuesDto : sportMProductSpecValuesDtos) {
					setSpecValueId.add(sportMProductSpecValuesDto.getSpecValuesId());
				}
			}
		}
		List<SportMSpecProductDto> specProductDtos = new ArrayList<SportMSpecProductDto>();
		for (String specId : setSpecId) {
			SportMSpec sportMSpec = sportMSpecDao.selectByPrimaryKey(specId);
//			List<SportMSpecValuesDto> specValues = sportMSpecValuesDao.findValueBySpecId(specId);
			SportMSpecProductDto sportMSpecProductDto = BeanUtils.createBeanByTarget(sportMSpec, SportMSpecProductDto.class);
			List<SportMSpecValuesDto> sportMSpecValuesDtos = new ArrayList<SportMSpecValuesDto>();
			for (String specVauleId : setSpecValueId) {
				SportMSpecValuesDto sportMSpecValuesDto = sportMSpecValuesDao.findByIdAndSpecId(specVauleId, specId);
				if (null != sportMSpecValuesDto & StringUtils.isNotBlank(sportMSpecValuesDto.getSpecId())) {
					sportMSpecValuesDtos.add(sportMSpecValuesDto);
				}
			}
			sportMSpecProductDto.setSportMSpecValuesDtos(sportMSpecValuesDtos);
			specProductDtos.add(sportMSpecProductDto);
		}
//		List<SportMSpecValuesDto> specValuesDtos = BeanUtils.createBeanListByTarget(sportMSpecValues, SportMSpecValuesDto.class);
		return specProductDtos;
	}

	@Override
	public List<SportMProductSkuDto> findSkuByProduct(String productId) {
		List<SportMProductSku> sku = sportMProductSkuDao.findSkuByProductIdList(productId);
		if(sku != null && sku.size() >0){
			return BeanUtils.createBeanListByTarget(sku, SportMProductSku.class);
		}
		return null;
	}

	@Override
	public boolean selectSkuByCriteria(SportMProductSkuDto skuDto) {
		SportMProductSku sku = BeanUtils.createBeanByTarget(skuDto, SportMProductSku.class);
		List<SportMProductSku> skuLis = sportMProductSkuDao.findSkuByCriteria(sku);
		if(skuLis!=null && skuLis.size()>0){
			return true;
		}
		return false;
	}
	
}
