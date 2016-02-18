package com.tianfang.train.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.util.StringUtils;
import com.tianfang.train.dto.SpaceDto;
import com.tianfang.train.dto.TrainingAddressDto;
import com.tianfang.train.dto.TrainingSpaceManagerDto;
import com.tianfang.train.mapper.TraingAddressExMapper;
import com.tianfang.train.mapper.TrainingAddressDistrictTimeMapper;
import com.tianfang.train.mapper.TrainingAddressMapper;
import com.tianfang.train.pojo.TrainingAddress;
import com.tianfang.train.pojo.TrainingAddressDistrictTime;
import com.tianfang.train.pojo.TrainingAddressDistrictTimeExample;
import com.tianfang.train.pojo.TrainingAddressExample;
import com.tianfang.train.pojo.TrainingAddressExample.Criteria;

@Repository
public class TrainingSpaceInfoDao {
	
	@Autowired
	@Getter
	private TrainingAddressMapper mappers;
	@Autowired
	@Getter
	private TraingAddressExMapper exMappers;
	@Autowired
    @Getter
	private TrainingAddressDistrictTimeMapper mapperTimes;

	/**
	 * 场地信息列表
	 * @param spaceDto
	 * @param page
	 * @return
	 */
	public List<TrainingAddress> findAll(TrainingSpaceManagerDto spaceDto,
			PageQuery page) {
		TrainingAddressExample example = new TrainingAddressExample();
		Criteria criteria = example.createCriteria();
		byParameter(spaceDto,criteria);
		criteria.andStatusEqualTo(DataStatus.ENABLED);
		if(page!=null){
			example.setOrderByClause(" create_time desc limit "+page.getStartNum() +"," + page.getPageSize());
		}
		return mappers.selectByExample(example);
	}
	/**
	 * 查询当前总条数
	 * @param page
	 * @return
	 */
	public long count(TrainingSpaceManagerDto spaceDto) {
		TrainingAddressExample example = new TrainingAddressExample();
		Criteria criteria = example.createCriteria();
		byParameter(spaceDto,criteria);
		criteria.andStatusEqualTo(DataStatus.ENABLED);
		return mappers.countByExample(example);
	}
	/**
	 * 根据条件筛选
	 */
	public void byParameter(TrainingSpaceManagerDto spaceDto,Criteria criteria){
		if(!StringUtils.isEmpty(spaceDto.getDistrictId())){
			criteria.andDistrictIdEqualTo(spaceDto.getDistrictId());
		}
		if(!StringUtils.isEmpty(spaceDto.getPlace())){
			criteria.andPlaceLike("%"+spaceDto.getPlace()+"%");
		}
	}
	/**
	 * 验证场地信息
	 * @param spaceDto
	 * @param criteria
	 */
	public void validateBySpace(TrainingSpaceManagerDto spaceDto,Criteria criteria){
		if(!StringUtils.isEmpty(spaceDto.getAddress())){
			criteria.andAddressEqualTo(spaceDto.getAddress());
		}
		if(!StringUtils.isEmpty(spaceDto.getPlace())){
			criteria.andPlaceEqualTo(spaceDto.getPlace());
		}
		if(spaceDto.getLatitude()!= null && spaceDto.getLongtitude()!= null){
			criteria.andLongtitudeEqualTo(spaceDto.getLongtitude());
			criteria.andLatitudeEqualTo(spaceDto.getLatitude());
		}
	}
	/**
	 * 查询验证
	 * @param spaceDto
	 * @param page
	 * @return
	 */
	public List<TrainingAddress> validateBySpaceAll(TrainingSpaceManagerDto spaceDto,
			PageQuery page) {
		TrainingAddressExample example = new TrainingAddressExample();
		Criteria criteria = example.createCriteria();
		validateBySpace(spaceDto,criteria);
		criteria.andStatusEqualTo(DataStatus.ENABLED);
		return mappers.selectByExample(example);
	}
	/**
	 * 根据id获取一个场地所关联的信息    --多表查询
	 * @return
	 */
	public List<TrainingAddressDto> selectAllAddress(String id){
		List<TrainingAddressDto> result = exMappers.getAllAddressInfo(id);
		return result;
	}
	public TrainingAddress findById(String id) {
		TrainingAddress address = mappers.selectByPrimaryKey(id);
		return address;
	}
	/**
	 * 插入场地
	 * @param trainingAdd
	 * @return
	 */
	public int insertTraAdd(TrainingAddress trainingAdd) {
		//int line =exMappers.insert(trainingAdd);
		return mappers.insert(trainingAdd);
	}
	/**
	 * 插入场地  时间段关联表
	 * @param trainingADT
	 */
	public void insertTimeDis_TimeDao(TrainingAddressDistrictTime trainingADT) {
		 mapperTimes.insert(trainingADT);
	}
	/**
	 * 修改时间段关联表
	 * @param trainingADT
	 */
	public void updateTimeDis_TimeDao(TrainingAddressDistrictTime trainingADT) {
		 mapperTimes.updateByPrimaryKeySelective(trainingADT);
	}
	/**
	 * 查询时间段 场地 关联表
	 * @param trainingADT
	 * @return
	 */
	public List<TrainingAddressDistrictTime> findTimeAddress(TrainingAddressDistrictTime trainingADT){
		TrainingAddressDistrictTimeExample example = new TrainingAddressDistrictTimeExample();
		com.tianfang.train.pojo.TrainingAddressDistrictTimeExample.Criteria criteria= example.createCriteria();
		if(trainingADT.getAddressId()!=null && !"".equals(trainingADT.getAddressId())){
			criteria.andAddressIdEqualTo(trainingADT.getAddressId());
		}
		if(trainingADT.getDistrictTimeId()!=null && !"".equals(trainingADT.getDistrictTimeId())){
			criteria.andDistrictTimeIdEqualTo(trainingADT.getDistrictTimeId());
		}
		/*if(trainingADT.getStatus() != null && !"".equals(trainingADT.getStatus())){
			criteria.andStatusEqualTo(DataStatus.ENABLED);
		}*/
		return mapperTimes.selectByExample(example);
	}
	/**
	 * 修改场地
	 * @param trainingAddress
	 */
	public int updateAddress(TrainingAddress trainingAddress) {
		return mappers.updateByPrimaryKeySelective(trainingAddress);
	}
	/**
	 * 通过场地id更新掉 场地时间关联表信息
	 * @param id
	 */
	public int updateAddress_id(String id) {
		return  exMappers.update(id);
	}
	/**
	 * 场地时间段更新
	 * @param addOrTime
	 */
	public int updateByKey(TrainingAddressDistrictTime addOrTime) {
		return mapperTimes.updateByPrimaryKey(addOrTime);
	}
	/**
	 * 根据时间段id  场地id 判断是否有关联
	 * @param time_id
	 * @param address_id
	 * @return
	 */
	public TrainingAddressDistrictTime getByIdAddOrTime(String time_id,String address_id){
		TrainingAddressDistrictTimeExample time_add = new TrainingAddressDistrictTimeExample();
		com.tianfang.train.pojo.TrainingAddressDistrictTimeExample.Criteria criteria= time_add.createCriteria();
		criteria.andAddressIdEqualTo(address_id);
		criteria.andDistrictTimeIdEqualTo(time_id);
		List<TrainingAddressDistrictTime> listTra = mapperTimes.selectByExample(time_add);
		if(listTra.size() >0 ){
			return listTra.get(0);
		}else{
			return null;
		}
	}
	public int insertAddOrTime(TrainingAddressDistrictTime addOrTime) {
		return mapperTimes.insert(addOrTime);
	}
	/**
	 * 根据场地id对应时间段信息
	 * @param id
	 * @return
	 */
	public List<TrainingAddressDistrictTime> findByAIdToTime(String id) {
		TrainingAddressDistrictTimeExample texmaple = new TrainingAddressDistrictTimeExample();
		com.tianfang.train.pojo.TrainingAddressDistrictTimeExample.Criteria criteria= texmaple.createCriteria();
		criteria.andAddressIdEqualTo(id);
		criteria.andStatusEqualTo(DataStatus.ENABLED);
		return mapperTimes.selectByExample(texmaple);
	}
	
	public List<SpaceDto> findAllSpace(){
		return exMappers.findAllSpace();
	}
}
