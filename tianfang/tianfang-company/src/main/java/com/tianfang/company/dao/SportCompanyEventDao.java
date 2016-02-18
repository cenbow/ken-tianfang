/**
 * 
 */
package com.tianfang.company.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.company.dto.SportCompanyEventDto;
import com.tianfang.company.mapper.SportCompanyEventMapper;
import com.tianfang.company.pojo.SportCompanyEvent;
import com.tianfang.company.pojo.SportCompanyEventExample;

/**		
 * <p>Title: SportCompanyEventDao </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年12月4日 上午10:44:56	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年12月4日 上午10:44:56</p>
 * <p>修改备注：</p>
 */

@Repository
public class SportCompanyEventDao extends MyBatisBaseDao<SportCompanyEvent>
{
    @Getter
    @Autowired
    private SportCompanyEventMapper mapper;
    
    
    public List<SportCompanyEventDto> findPage(SportCompanyEventDto sportCompanyEventDto,PageQuery page){
        SportCompanyEventExample example = new SportCompanyEventExample();
        SportCompanyEventExample.Criteria criteria = example.createCriteria();
        assemblyParams(sportCompanyEventDto, criteria);
        if (null != page) {
            example.setOrderByClause(" create_time desc limit "+page.getStartNum() +"," + page.getPageSize());
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        return BeanUtils.createBeanListByTarget(mapper.selectByExample(example), SportCompanyEventDto.class);
    }

    public long count(SportCompanyEventDto sportCompanyEventDto) {
        SportCompanyEventExample example = new SportCompanyEventExample();
        SportCompanyEventExample.Criteria criteria = example.createCriteria();
        assemblyParams(sportCompanyEventDto, criteria);
        criteria.andStatEqualTo(DataStatus.ENABLED);
        return mapper.countByExample(example);
    }
    
    private void assemblyParams(SportCompanyEventDto dto,
			SportCompanyEventExample.Criteria criteria) {
		if (StringUtils.isNotBlank(dto.getTitle())) {
            criteria.andTitleLike("%"+dto.getTitle()+"%");
        }
		if (null != dto.getReleaseStat()){
			criteria.andReleaseStatEqualTo(dto.getReleaseStat());
		}
	}
}