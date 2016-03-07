package com.tianfang.order.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.order.dao.SportMProductSkuDao;
import com.tianfang.order.dao.SportMProductSpuDao;
import com.tianfang.order.dto.SportMEvaluateDto;
import com.tianfang.order.dto.SportMOrderInfoDto;
import com.tianfang.order.dto.SportMProductSpuDto;
import com.tianfang.order.pojo.SportMProductSku;
import com.tianfang.order.pojo.SportMProductSpu;
import com.tianfang.order.service.ISportMProductSpuService;
@Service
public class SportMProductSpuImpl implements ISportMProductSpuService{

	@Autowired
	private SportMProductSpuDao sportMProductSpuDao;

	@Autowired
	private SportMProductSkuDao sportMProductSkuDao;
	
	@Override
	public long save(SportMProductSpuDto spu) {
		SportMProductSpu pSpu = BeanUtils.createBeanByTarget(spu,SportMProductSpu.class);
		pSpu.setProductStatus(0);  //新增为下架状态     通过添加sku修改为上架状态 
		return sportMProductSpuDao.save(pSpu);
	}

	@Override
	public long edit(SportMProductSpuDto spu) {
		SportMProductSpu pSpu = BeanUtils.createBeanByTarget(spu,SportMProductSpu.class);
		SportMProductSku sportMProductSku = sportMProductSkuDao.findSkuByProductId(pSpu.getId());
		if (null != sportMProductSku) {
			sportMProductSkuDao.updateByPrimaryKeySelective(sportMProductSku);
		}
		return sportMProductSpuDao.edit(pSpu);
	}
	
	@Override
	public long spuStatus(SportMProductSpuDto spu) {
		SportMProductSpu pSpu = BeanUtils.createBeanByTarget(spu,SportMProductSpu.class);
		SportMProductSpu sportMProductSpu = sportMProductSpuDao.selectByPrimaryKey(spu.getId());
		if (null != spu.getProductStatus()) {
			List<SportMProductSku> sku = sportMProductSkuDao.findSkuByProductIdList(spu.getId());
			for (SportMProductSku sportmProductSku :sku) {
				sportmProductSku.setProductStatus(spu.getProductStatus());
				sportMProductSkuDao.updateByPrimaryKeySelective(sportmProductSku);
			}
		}		
		return sportMProductSpuDao.updateByPrimaryKeySelective(pSpu);
	}

	public List<SportMProductSpu> findSpuById(SportMProductSpuDto spu){
		return sportMProductSpuDao.findSpuById(spu);
	}
	
	@Override
	public long delete(String id) {
		String[] ids = id.split(",");
		long stat = 0;
		for (String str : ids) {
			stat= sportMProductSpuDao.delete(str);
			if(stat<0){
				return stat;
			}
		}
		return stat;
	}
	
	@Override
	public PageResult<SportMProductSpuDto> selectPageAll(SportMProductSpuDto spu, PageQuery page) {
		List<SportMProductSpuDto> lis = sportMProductSpuDao.selectPageAllEx(spu,page);
		long total = sportMProductSpuDao.countPageAll(spu);
		page.setTotal(total);
		return new PageResult<SportMProductSpuDto>(page,lis);
	}

	@Override
	public List<SportMProductSpuDto> selectByCriteria(SportMProductSpuDto spuDto) {
		List<SportMProductSpu> list = sportMProductSpuDao.selectByCriteria(spuDto,null);
		return BeanUtils.createBeanListByTarget(list, SportMProductSpuDto.class);
	}

	@Override
	public PageResult<SportMProductSpuDto> selectPageByCriteria(
			SportMProductSpuDto spuDto, PageQuery page) {
		List<SportMProductSpu> list = sportMProductSpuDao.selectByCriteria(spuDto,page);
		List<SportMProductSpuDto> lisDto = BeanUtils.createBeanListByTarget(list, SportMProductSpuDto.class);
		if(page!=null){
			long total = sportMProductSpuDao.countByCriteria(spuDto);
			page.setTotal(total);
			return new PageResult<SportMProductSpuDto>(page,lisDto);
		}
		return null;
	}
	
	public SportMProductSpuDto findProductById(String id) {
		SportMProductSpu sportMProductSpu = sportMProductSpuDao.selectByPrimaryKey(id);
		SportMProductSpuDto sportMProductSpuDto = BeanUtils.createBeanByTarget(sportMProductSpu, SportMProductSpuDto.class);
		return sportMProductSpuDto;
	}
	
	public PageResult<SportMOrderInfoDto> selectOrderBySpu(String id,PageQuery page) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<SportMOrderInfoDto> sportMOrderInfoDtos = sportMProductSkuDao.selectOrderBySpu(id, page);
		if (sportMOrderInfoDtos.size()>0) {
			for (SportMOrderInfoDto sportMOrderInfoDto : sportMOrderInfoDtos) {
				sportMOrderInfoDto.setCreateTimeStr(sdf.format(sportMOrderInfoDto.getCreateTime()));	
			}
		}
		long total = sportMProductSkuDao.selectOrderBySpuCount(id);
		page.setTotal(total);
		return new PageResult<SportMOrderInfoDto>(page,sportMOrderInfoDtos);
	}
	
	public PageResult<SportMEvaluateDto> selectEvaluate(String productId,Integer evaluateStatus,String pci,PageQuery page) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SportMEvaluateDto sportMEvaluateDto = new SportMEvaluateDto();
		sportMEvaluateDto.setEvaluateStatus(evaluateStatus);
		sportMEvaluateDto.setProductSkuId(productId);
		sportMEvaluateDto.setPic(pci);
		List<SportMEvaluateDto> sportMEvaluateDtos = sportMProductSkuDao.selectEvaluate(sportMEvaluateDto, page);
		if (sportMEvaluateDtos.size()>0) {
			for (SportMEvaluateDto sportmEvaluateDto : sportMEvaluateDtos) {
				sportmEvaluateDto.setCreateTimeStr(sdf.format(sportmEvaluateDto.getCreateTime()));
			}
		}
		long total = sportMProductSkuDao.selectEvaluateCount(sportMEvaluateDto);
		page.setTotal(total);
		return new PageResult<SportMEvaluateDto>(page,sportMEvaluateDtos);
	}

	
	@Override
	public SportMProductSpuDto selectSpuById(String spuId) {
		return BeanUtils.createBeanByTarget(sportMProductSpuDao.selectByPrimaryKey(spuId), SportMProductSpuDto.class);
	}
}
