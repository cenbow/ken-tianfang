/**
 * 
 */
package com.tianfang.company.service;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.company.dto.SportCompanyEventDto;

/**		
 * <p>Title: ISportCompanyEventService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年12月4日 上午10:48:53	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年12月4日 上午10:48:53</p>
 * <p>修改备注：</p>
 */
public interface ISportCompanyEventService
{
    public PageResult<SportCompanyEventDto> findPage(SportCompanyEventDto sportCompanyEventDto,PageQuery page);
    
    public Object save(SportCompanyEventDto sportCompanyEventDto);
    
    public SportCompanyEventDto findById (String id);
    
    public Object edit (SportCompanyEventDto sportCompanyEventDto);
    
    public Object delete(String ids);
}

