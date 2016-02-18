/**
 * 
 */
package com.tianfang.train.mapper;

import java.util.List;
import java.util.Map;

import com.tianfang.train.dto.TrainingAddressDtoX01;
import com.tianfang.train.dto.TrainingCourseAddressDto;
import com.tianfang.train.pojo.TrainingAddress;

/**
 * 
 * @author wk.s
 * @date 2015年9月2日
 */
public interface XTrainingAddressMapper {
	    
    /**
     * 通过条件（ID等）查询地址信息
     * @param map
     * @return
     * @2015年9月2日
     */
    TrainingAddress getAddrById(Map<String, Object> map);
    
    /**
     * 获取课程相关的上课地点和上课时间
     * @param courseAddressDto
     * @return
     */
	List<TrainingCourseAddressDto> getCourseAddressesTime(TrainingCourseAddressDto courseAddressDto);
	
	/**
	 * 查询所有场地的信息
	 * @return
	 * @2015年9月8日
	 */
	List<TrainingAddressDtoX01> getAllAddrs();
}
