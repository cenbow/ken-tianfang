package com.tianfang.train.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.train.dto.SpaceDto;
import com.tianfang.train.dto.TrainingAddressDto;
import com.tianfang.train.dto.TrainingDistrictDto;
import com.tianfang.train.dto.TrainingSpaceManagerDto;
import com.tianfang.train.dto.TrainingTimeDistrictDto;
import com.tianfang.train.dto.TrainingTimeDistrictExDto;
/**
 * 后台场地信息管理模块
 * @author mr
 *
 */
@Service
public interface ITrainingSpaceInfoService {
	/**
	 *  场地信息列表
	 * @param spaceDto
	 * @param page
	 * @return
	 */
	PageResult<TrainingSpaceManagerDto> findAll(TrainingSpaceManagerDto spaceDto,PageQuery page);
	/**
	 * 区域信息列表
	 * @param districtDto
	 * @param changeToPageQuery
	 * @return
	 */
	PageResult<TrainingDistrictDto> findAllDistrict(TrainingDistrictDto districtDto, PageQuery changeToPageQuery);
	/**
	 * 场地时间段管理
	 * @param timeDistrictDto
	 * @param changeToPageQuery
	 * @return
	 */
	PageResult<TrainingTimeDistrictDto> findAllTiemDistrict(TrainingTimeDistrictDto timeDistrictDto, PageQuery changeToPageQuery);
	/**
	 * 查询一个场地关联的所有信息数据
	 * @param id
	 * @return
	 */
	List<TrainingAddressDto> selectAllAddress(String id);
	/**
	 *  新增场地时间信息
	 * @param timeDistrictDto
	 */
	int insertTimeDistrict(TrainingTimeDistrictDto timeDistrictDto);
	/**
	 * 新增区域
	 * @param districtDto
	 */
	int insertDistrict(TrainingDistrictDto districtDto);
	/**
	 * 删除区域
	 * @param distictId
	 * @return
	 */
	int updateOrDel(String distictId);
	/**
	 * 根据主键获取区域
	 * @param id
	 * @return
	 */
	TrainingDistrictDto getByIdTraining(String id);
	/**
	 * 修改区域
	 * @param districtDto
	 * @return
	 */
	int updateEdit(TrainingDistrictDto districtDto);
	/**
	 * 删除时间段
	 * @param ids
	 * @return
	 */
	int updateOrDelByTimeDitrict(String ids);
	/**
	 * 根据主键获取时间段
	 * @param id
	 * @return
	 */
	TrainingTimeDistrictDto getByIdTimeDistrict(String id);
	/**
	 * 修改时间段
	 * @param timeDistrictDto
	 * @return
	 */
	int updateEditByTimeDistrict(TrainingTimeDistrictDto timeDistrictDto);
	/**
	 * 新增场地
	 * @param addressDto
	 * @return
	 */
	int insertAddress(TrainingAddressDto addressDto);
	/**
	 * 查询所有时间段
	 * @return
	 */
	List<TrainingTimeDistrictExDto> findAllTime();
	/**
	 * 根据场地查询对应的时间段
	 * Ken  YIn
	 * @return
	 */
	List<TrainingTimeDistrictDto> findTimeDistrictBySpaceId(String spaceId);
	/**
	 * 根据主键查询场地
	 * @param id
	 * @return
	 */
	TrainingAddressDto findByIdTraAddress(String id);
	/**
	 * 修改场地信息
	 */
	int editAddressOrTime(TrainingAddressDto addressDto);
	/**
	 * 删除场地
	 * @param id
	 * @return
	 */
	int deleteByAddress(String id);
	/**
	 * 添加场地对应的时间段信息
	 * @param addressDto
	 * @return
	 */
	int insertBytimeAdd(TrainingAddressDto addressDto);
	/**
	 * 获取所有时间段  不分页
	 * @param timeDistrictDto
	 * @return
	 */
	List<TrainingTimeDistrictDto> findAllTiemDistrict(
			TrainingTimeDistrictDto timeDistrictDto);
	/**
	 * 获取所有场地信息
	 * @return
	 */
	List<TrainingDistrictDto> findAllDistrictNoPage();
	
	/**
	 * 查询所有满足添加场地的信息
	 * @author xiang_wang
	 * 2015年10月23日上午10:50:02
	 */
	List<SpaceDto> findAllSpace();
	/**
	 * 根据id查询场地
	 * @param spaceId
	 * @return
	 */
	TrainingAddressDto getByIdAddress(String spaceId);
}
