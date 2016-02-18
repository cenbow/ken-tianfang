package com.tianfang.business.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tianfang.business.dto.SportNoticeDto;

/**
 * @author xiang_wang
 *
 * 2015年11月14日下午3:52:44
 */
public interface SportNoticeExMapper {

	 /**
	 * 重置该球队其他公告信息置顶状态
	 * @author xiang_wang
	 * 2015年11月14日下午3:52:49
	 */
	int resetTop(@Param("teamId") String teamId);
	
	/**
	 * 根据参数球队表和球队公告表联查
	 * @param params
	 * @return List<SportNoticeDto>
	 * @author wangxiang
	 * 2015年11月16日上午10:15:29
	 */
	List<SportNoticeDto> findNoticeByParams(@Param("params") Map<String, Object> params);
}
