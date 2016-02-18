package com.tianfang.company.mapper;

import java.util.Map;

public interface SportCompanyRelatedExMapper {
    
	/**
	 * 批量逻辑删除数据
	 * @param map
	 * @return
	 */
    int deleteByIds(Map<String, Object> map);
}