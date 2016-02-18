package com.tianfang.order.impl;

import java.util.List;

import org.apache.zookeeper.KeeperException.BadVersionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.order.dao.SportMSpecDao;
import com.tianfang.order.dao.SportMTypeSpecDao;
import com.tianfang.order.dto.SportMSpecDto;
import com.tianfang.order.pojo.SportMSpec;
import com.tianfang.order.pojo.SportMTypeSpec;
import com.tianfang.order.service.ISportMSpecService;

@Service
public class SportMSpecImpl implements ISportMSpecService{

	@Autowired
	private SportMSpecDao sportMSpecDao;
	@Autowired
	private SportMTypeSpecDao sportMTypeSpecDao;

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
	public long delete(String id) {
		String[] ids = id.split(",");
		long stat = 0;
		for (String str : ids) {
			stat = sportMSpecDao.delete(str);
			if(stat<0){
				return stat;
			}
		}
		return stat;
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
