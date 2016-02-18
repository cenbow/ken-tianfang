package com.tianfang.order.dao;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.Getter;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.StringUtils;
import com.tianfang.order.dto.SportMProductSpuDto;
import com.tianfang.order.mapper.SportMProductSpuExMapper;
import com.tianfang.order.mapper.SportMProductSpuMapper;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.order.pojo.SportMProductSpu;
import com.tianfang.order.pojo.SportMProductSpuExample;
import com.tianfang.order.pojo.SportMProductSpuExample.Criteria;

@Repository
public class SportMProductSpuDao extends MyBatisBaseDao<SportMProductSpu>{

	@Getter
	@Autowired
	private SportMProductSpuMapper mappers;
	@Getter
	@Autowired
	private SportMProductSpuExMapper mappersEx;

	@Override
	public Object getMapper() {
		return mappers;
	}

	public long save(SportMProductSpu spu) {
		spu.setId(UUID.randomUUID()+"");
		spu.setCreateTime(new Date());
		spu.setLastUpdateTime(new Date());
		spu.setStat(DataStatus.ENABLED);
		long stat = 0;
		try {
			stat= mappers.insert(spu);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stat;
	}

	public long edit(SportMProductSpu pSpu) {
		SportMProductSpu oldPSpu = mappers.selectByPrimaryKey(pSpu.getId());
		long stat = 0;
		checkUp(pSpu,oldPSpu);
		pSpu.setLastUpdateTime(new Date());
		pSpu.setStat(DataStatus.ENABLED);
		try {
			stat = mappers.updateByPrimaryKey(pSpu);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stat;
	}

	private void checkUp(SportMProductSpu pSpu, SportMProductSpu oldPSpu) {
		if(StringUtils.isBlank(pSpu.getProductName())){
			pSpu.setProductName(oldPSpu.getProductName());
		}
		if(StringUtils.isBlank(pSpu.getBrandId())){
			pSpu.setBrandId(oldPSpu.getBrandId());
		}
		if(StringUtils.isBlank(pSpu.getTypeId())){
			pSpu.setTypeId(oldPSpu.getTypeId());
		}
		if(StringUtils.isBlank(pSpu.getCategoryId())){
			pSpu.setCategoryId(oldPSpu.getCategoryId());
		}
		if(pSpu.getProductPrice()==null){
			pSpu.setProductPrice(oldPSpu.getProductPrice());
		}
		if(pSpu.getProductMarketPrice() == null){
			pSpu.setProductMarketPrice(oldPSpu.getProductMarketPrice());
		}
		if(pSpu.getProductCostPrice() == null){
			pSpu.setProductCostPrice(oldPSpu.getProductCostPrice());
		}
		if(pSpu.getProductStock() == null){
			pSpu.setProductStock(oldPSpu.getProductStock());
		}
		if(pSpu.getProductStatus() == null){
			pSpu.setProductStatus(oldPSpu.getProductStatus());
		}
		if(pSpu.getCreateTime() == null){
			pSpu.setCreateTime(oldPSpu.getCreateTime());
		}
		if(StringUtils.isBlank(pSpu.getPic())){
			pSpu.setPic(oldPSpu.getPic());
		}
		if(StringUtils.isBlank(pSpu.getThumbnail())){
			pSpu.setThumbnail(oldPSpu.getThumbnail());
		}
	}

	public long delete(String id) {
		SportMProductSpu pSpu = mappers.selectByPrimaryKey(id);
		pSpu.setStat(DataStatus.DISABLED);
		long stat = 0;
		try {
			stat= mappers.updateByPrimaryKey(pSpu);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stat;
	}

	public List<SportMProductSpuDto> selectPageAllEx(SportMProductSpuDto spu) {
		return mappersEx.selectPageAll(spu);
	}

	public long countPageAll(SportMProductSpuDto spu) {
		return mappersEx.countPageAll(spu);
	}

	
	public List<SportMProductSpuDto> findAllSpu(){
		SportMProductSpuExample example = new SportMProductSpuExample();
		SportMProductSpuExample.Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(DataStatus.ENABLED);
		List<SportMProductSpu> sportMProductSpus = mappers.selectByExample(example);
		List<SportMProductSpuDto> results = BeanUtils.createBeanListByTarget(sportMProductSpus, SportMProductSpu.class);
		return results;
	}
	
	public SportMProductSpuDto findAllSpuById(String id){
		return BeanUtils.createBeanByTarget(mappers.selectByPrimaryKey(id), SportMProductSpuDto.class);
	}

	public List<SportMProductSpu> selectByCriteria(SportMProductSpuDto spuDto, PageQuery page) {
		SportMProductSpuExample example = new SportMProductSpuExample();
		Criteria criteria = example.createCriteria();
		byCriteria(spuDto,criteria);
		criteria.andProductStatusEqualTo(DataStatus.ENABLED);
		criteria.andStatEqualTo(DataStatus.ENABLED);
		if(page!=null){
		//价格排序
		if(spuDto.getPriceStat()!=null){
			if(spuDto.getPriceStat() == 1 ){
				example.setOrderByClause("product_price limit "+ page.getStartNum()+","+page.getPageSize());
			}else if(spuDto.getPriceStat() == 2){
				example.setOrderByClause("product_price desc limit "+ page.getStartNum()+","+page.getPageSize());
			}
			return mappers.selectByExample(example);
		}
		//好评排序
		if(spuDto.getCountEvaluate()!=null){
			if(spuDto.getCountEvaluate() == 1){
				example.setOrderByClause("count_evaluate limit "+ page.getStartNum()+","+page.getPageSize());
			}else if(spuDto.getCountEvaluate() ==2){
				example.setOrderByClause("count_evaluate desc limit "+ page.getStartNum()+","+page.getPageSize());
			}
			return mappers.selectByExample(example);
		}
		if(spuDto.getNewStat() != null){
			if(spuDto.getNewStat() ==1){
				example.setOrderByClause("create_time limit "+ page.getStartNum()+","+page.getPageSize());
			}else if(spuDto.getNewStat() ==2){
				example.setOrderByClause("create_time desc limit "+ page.getStartNum()+","+page.getPageSize());
			}
			return mappers.selectByExample(example);
		}
		}
		if(page!=null){
			example.setOrderByClause("create_time desc limit "+ page.getStartNum()+","+page.getPageSize());
		}
		return mappers.selectByExample(example);
	}

	private void byCriteria(SportMProductSpuDto spuDto, Criteria criteria) {
		if(StringUtils.isNotBlank(spuDto.getId())){
			criteria.andIdEqualTo(spuDto.getId());
		}
		if(StringUtils.isNotBlank(spuDto.getProductName())){
			criteria.andProductNameLike("%"+spuDto.getProductName()+"%");
		}
		if(StringUtils.isNotBlank(spuDto.getBrandId())){
			criteria.andBrandIdEqualTo(spuDto.getBrandId());
		}
		if(StringUtils.isNotBlank(spuDto.getTypeId())){
			criteria.andTypeIdEqualTo(spuDto.getTypeId());
		}
		if(StringUtils.isNotBlank(spuDto.getCategoryId())){
			criteria.andCategoryIdEqualTo(spuDto.getCategoryId());
		}
	}

	public long countByCriteria(SportMProductSpuDto spuDto) {
		SportMProductSpuExample example = new SportMProductSpuExample();
		Criteria criteria = example.createCriteria();
		byCriteria(spuDto,criteria);
		criteria.andProductStatusEqualTo(DataStatus.ENABLED);
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return  mappers.countByExample(example);
	}
}
