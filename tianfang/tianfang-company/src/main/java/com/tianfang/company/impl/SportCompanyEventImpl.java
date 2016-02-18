/**
 * 
 */
package com.tianfang.company.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.company.dao.SportCompanyEventDao;
import com.tianfang.company.dto.SportCompanyEventDto;
import com.tianfang.company.pojo.SportCompanyEvent;
import com.tianfang.company.service.ISportCompanyEventService;

/**		
 * <p>Title: SportCompanyEventImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年12月4日 上午10:46:30	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年12月4日 上午10:46:30</p>
 * <p>修改备注：</p>
 */
@Service
public class SportCompanyEventImpl implements ISportCompanyEventService
{
    @Autowired
    private SportCompanyEventDao sportCompanyEventDao;
    
    public PageResult<SportCompanyEventDto> findPage(SportCompanyEventDto sportCompanyEventDto,PageQuery page) {
        List<SportCompanyEventDto> result = sportCompanyEventDao.findPage(sportCompanyEventDto, page);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (SportCompanyEventDto sportcompanyEventDto : result) {
            sportcompanyEventDto.setCreateDate(sdf.format(sportcompanyEventDto.getCreateTime()));
        }
        page.setTotal(sportCompanyEventDao.count(sportCompanyEventDto));
        return new PageResult<SportCompanyEventDto>(page,result);
    }
    
    public Object save(SportCompanyEventDto sportCompanyEventDto) {
        SportCompanyEvent sportCompanyEvent = BeanUtils.createBeanByTarget(sportCompanyEventDto,SportCompanyEvent.class);
        Integer result = sportCompanyEventDao.insert(sportCompanyEvent);
        return result;
    }
    
    public SportCompanyEventDto findById (String id) {
        SportCompanyEvent sportCompanyEvent = sportCompanyEventDao.selectByPrimaryKey(id);
        SportCompanyEventDto sportCompanyEventDto = BeanUtils.createBeanByTarget(sportCompanyEvent, SportCompanyEventDto.class);
        return sportCompanyEventDto;
    }
    
    public Object edit (SportCompanyEventDto sportCompanyEventDto) {
        SportCompanyEvent sportCompanyEvent = BeanUtils.createBeanByTarget(sportCompanyEventDto,SportCompanyEvent.class);
        Integer result = sportCompanyEventDao.updateByPrimaryKeySelective(sportCompanyEvent);
        return result;
    }
    
    public Object delete(String ids) {
        String[] idStr = ids.split(",");
        if (idStr.length>0) {
            for (String id : idStr) {
                SportCompanyEvent sportCompanyEvent = sportCompanyEventDao.selectByPrimaryKey(id);
                sportCompanyEvent.setStat(DataStatus.DISABLED);
                sportCompanyEventDao.updateByPrimaryKeySelective(sportCompanyEvent);
            }
        } else {
            SportCompanyEvent sportCompanyEvent = sportCompanyEventDao.selectByPrimaryKey(ids);
            sportCompanyEvent.setStat(DataStatus.DISABLED);
            sportCompanyEventDao.updateByPrimaryKeySelective(sportCompanyEvent);
        }
        return null;
    }
}

