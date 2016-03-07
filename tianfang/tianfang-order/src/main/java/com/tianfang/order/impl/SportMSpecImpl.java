package com.tianfang.order.impl;

import java.util.List;

import org.apache.zookeeper.KeeperException.BadVersionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.order.dao.SportMProductSpecDao;
import com.tianfang.order.dao.SportMSpecDao;
import com.tianfang.order.dao.SportMTypeSpecDao;
import com.tianfang.order.dto.SportMProductSpecDto;
import com.tianfang.order.dto.SportMSpecDto;
import com.tianfang.order.pojo.SportMProductSpec;
import com.tianfang.order.pojo.SportMSpec;
import com.tianfang.order.pojo.SportMTypeSpec;
import com.tianfang.order.service.ISportMSpecService;

@Service
public class SportMSpecImpl implements ISportMSpecService{

	@Autowired
	private SportMSpecDao sportMSpecDao;
	@Autowired
	private SportMTypeSpecDao sportMTypeSpecDao;
	
	@Autowired
	private SportMProductSpecDao sportMProductSpecDao;

	@Override
	public long save(SportMSpecDto sportMSpec) {
		SportMSpec spec= BeanUtils.createBeanByTarget(sportMSpec, SportMSpec.class);
		SportMTypeSpec typeSpec = new SportMTypeSpec();
		typeSpec.setTypeId(sportMSpec.getTypeId());			
		try {
			if(sportMSpecDao.save(spec) ==null){
				return 0;
			};
		//	stat =  sportMTypeSpecDao.save(typeSpec);						//添加类型  规格 关联表
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}

	@Override
	public long edit(SportMSpecDto sportMSpec) {
		SportMSpec spec= BeanUtils.createBeanByTarget(sportMSpec, SportMSpec.class);
		return sportMSpecDao.edit(spec);
	}

	@Override
	public Object delete(String id) {
		String[] ids = id.split(",");
		SportMSpecDto sportMSpecDto =  new SportMSpecDto();
		long stat = 0;
		for (String str : ids) {
			SportMProductSpecDto sport =  new SportMProductSpecDto();
			sport.setSpecId(str);
			List<SportMProductSpec> result = sportMProductSpecDao.findByProductSpec(sport);
			SportMSpec sportMspec = sportMSpecDao.selectByPrimaryKey(str);
			if (result.size()>0) {
				sportMSpecDto = BeanUtils.createBeanByTarget(sportMspec, SportMSpecDto.class);
				sportMSpecDto.setDeleteStat(DataStatus.DISABLED);
				return sportMSpecDto;
			}
			/*stat = sportMSpecDao.delete(str);
			if(stat<0){
				return stat;
			}*/
		}
		for (String str : ids) {
			SportMSpec sportMspec = sportMSpecDao.selectByPrimaryKey(str);
			sportMspec.setStat(DataStatus.DISABLED);
			sportMSpecDao.updateByPrimaryKeySelective(sportMspec);
		}
		sportMSpecDto.setDeleteStat(DataStatus.ENABLED);
		return sportMSpecDto;
	}

	@Override
	public PageResult<SportMSpecDto> selectPageAll(SportMSpecDto sportMSpec,PageQuery page) {
		List<SportMSpec> lis = sportMSpecDao.selectPageAll(sportMSpec,page);
		List<SportMSpecDto> lisDto = BeanUtils.createBeanListByTarget(lis, SportMSpecDto.class);
		if(page!=null){
			long total = sportMSpecDao.countByCriteria(sportMSpec);
			page.setTotal(total);
			return new PageResult<SportMSpecDto>(page,lisDto);
		}
		return null;
	}

	@Override
	public SportMSpecDto selectById(String id) {
		SportMSpec mspec = sportMSpecDao.selectById(id);
		if(mspec!=null)
			return BeanUtils.createBeanByTarget(mspec, SportMSpecDto.class) ;
		else
			return null;
	}

	@Override
	public List<SportMSpecDto> selectAll() {
		List<SportMSpec> spec_lis = sportMSpecDao.selectAll();
		if(spec_lis.size()>0){
			return BeanUtils.createBeanListByTarget(spec_lis, SportMSpecDto.class);
		}
		return null;
	} 
}
