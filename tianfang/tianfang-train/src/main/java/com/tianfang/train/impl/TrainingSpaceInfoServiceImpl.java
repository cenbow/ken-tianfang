package com.tianfang.train.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.DateUtils;
import com.tianfang.train.dao.TrainingDistrictDao;
import com.tianfang.train.dao.TrainingSpaceInfoDao;
import com.tianfang.train.dao.TrainingTimeDistrictDao;
import com.tianfang.train.dto.SpaceDto;
import com.tianfang.train.dto.TrainingAddressDto;
import com.tianfang.train.dto.TrainingDistrictDto;
import com.tianfang.train.dto.TrainingSpaceManagerDto;
import com.tianfang.train.dto.TrainingTimeDistrictDto;
import com.tianfang.train.dto.TrainingTimeDistrictExDto;
import com.tianfang.train.pojo.TimeDistrict;
import com.tianfang.train.pojo.TrainingAddress;
import com.tianfang.train.pojo.TrainingAddressDistrictTime;
import com.tianfang.train.pojo.TrainingDistrict;
import com.tianfang.train.service.ITrainingSpaceInfoService;

@Service
public class TrainingSpaceInfoServiceImpl implements ITrainingSpaceInfoService {

	@Autowired
	private TrainingSpaceInfoDao spaceDao;
	@Autowired
	private TrainingDistrictDao distrticDao;
	@Autowired
	private TrainingTimeDistrictDao timeDistrictDao;		

	/**
	 * 场地管理信息
	 */
	@Override
	public PageResult<TrainingSpaceManagerDto> findAll(
			TrainingSpaceManagerDto spaceDto, PageQuery page) {
		List<TrainingAddress> traingAddress= spaceDao.findAll(spaceDto,page);
		List<TrainingSpaceManagerDto> traingAddressDto = new ArrayList<TrainingSpaceManagerDto>();
		for (int i = 0; i < traingAddress.size(); i++) {
			TrainingSpaceManagerDto traAdd = BeanUtils.createBeanByTarget(traingAddress.get(i), TrainingSpaceManagerDto.class);
			if (null != traAdd ) {
			    TrainingDistrict  district =findById(traAdd.getDistrictId());
			    if (null != district) {
			        traAdd.setArea(district.getCity()+"-"+district.getName());                          //将后台获取的区域id 转换成对应的 name 显示
	                traAdd.setCreateTimeStr(DateUtils.format(new Date(traAdd.getCreateTime()), "yyyy_mm-dd"));
	                traingAddressDto.add(traAdd);
			    }	            
			}
			
		}
		long total = spaceDao.count(spaceDto);
		page.setTotal(total);
		return new PageResult<TrainingSpaceManagerDto>(page,traingAddressDto);
	}

	/**
	 * 区域管理信息
	 */
	@Override
	public PageResult<TrainingDistrictDto> findAllDistrict(
			TrainingDistrictDto districtDto, PageQuery pageQuery) {
		List<TrainingDistrict> district = distrticDao.findAllDistrict(districtDto,pageQuery);
		List<TrainingDistrictDto> TrainingDistrictDto = new ArrayList<TrainingDistrictDto>();
		for (int i = 0; i < district.size(); i++) {
			TrainingDistrictDto traDistric = BeanUtils.createBeanByTarget(district.get(i),TrainingDistrictDto.class);
			TrainingDistrictDto.add(traDistric);
		}
		long total = distrticDao.count(districtDto);
		pageQuery.setTotal(total);;
		return new PageResult<TrainingDistrictDto>(pageQuery,TrainingDistrictDto);
	}
	/**
	 * 根据id查询区域对象
	 * @param  id
	 * @return
	 */
	public TrainingDistrict findById(String id){
		return distrticDao.findById(id); 
	}

	/**
	 * 时间段管理
	 */
	@Override
	public PageResult<TrainingTimeDistrictDto> findAllTiemDistrict(
			TrainingTimeDistrictDto timeDistrictDto, PageQuery changeToPageQuery) {
		List<TimeDistrict> timeDistrict =  timeDistrictDao.findAllTimeDistrict(timeDistrictDto,changeToPageQuery);
		List<TrainingTimeDistrictDto> TrainTimeDistrictDto = new ArrayList<TrainingTimeDistrictDto>();
		for (int i = 0; i < timeDistrict.size(); i++) {
			TrainingTimeDistrictDto traTimeDis= BeanUtils.createBeanByTarget(timeDistrict.get(i), TrainingTimeDistrictDto.class);
			String dayOfWeekStr = traTimeDis.getStartTime() +" - "+traTimeDis.getEndTime();
			traTimeDis.setDayOfWeekStr(dayOfWeekStr);																//填充页面显示时间段值
			//traTimeDis.setDayOfWeek(DayOfWeekEnum.getByIndexValue(Integer.valueOf(traTimeDis.getDayOfWeek())));   	//填充星期值
			TrainTimeDistrictDto.add(traTimeDis);
		}
		long total = timeDistrictDao.count(timeDistrictDto);
		changeToPageQuery.setTotal(total);
		return new PageResult<TrainingTimeDistrictDto>(changeToPageQuery, TrainTimeDistrictDto);
	}

	/**
	 * 查询场地 时间段 关联信息
	 */
	@Override
	public List<TrainingAddressDto> selectAllAddress(String id) {
		return spaceDao.selectAllAddress(id);
	}

	@Override
	public int insertTimeDistrict(TrainingTimeDistrictDto timeDistrictDto) {
		timeDistrictDto.setStatus(2);    //status 不为null  不根据状态过滤
		List<TimeDistrict> listimeDistrict = timeDistrictDao.findAllTimeDistrict(timeDistrictDto, null);
		if(listimeDistrict.size()>0){					//修改
			TimeDistrict dis =listimeDistrict.get(0);
			dis.setStatus(DataStatus.ENABLED);
			dis.setCreateTime(dis.getCreateTime());
			dis.setUpdateTime(new Date().getTime()/1000);
			timeDistrictDao.update(dis);
			return 1;  						
		}
		timeDistrictDto.setId(UUID.randomUUID().toString());
		timeDistrictDao.saveTimeDistrict(timeDistrictDto);  //新增
		return 1;							//添加成功
	}

	@Override
	public int insertDistrict(TrainingDistrictDto districtDto) {
		districtDto.setStatus(2);                 //不根据stats查询
		List<TrainingDistrict> lis = distrticDao.findAllDistrict(districtDto, null);
		if(lis.size() >0){ //修改
			TrainingDistrict tra = lis.get(0);
			tra.setStatus(DataStatus.ENABLED);
			tra.setCreateTime(new Date().getTime()/1000);
			distrticDao.updateOrDel(tra);
				return 1;
		}
		districtDto.setId(UUID.randomUUID().toString());
		distrticDao.saveDistrict(districtDto);   //新增
		return 1;
	}


	@Override
	public int updateOrDel(String distictId) {
		String[] strs= distictId.split(",");
		for (String str : strs) {
			TrainingDistrict district = distrticDao.findById(str);
			if(district == null){
				return 0; //无记录
			}
			district.setUpdateTime(new Date().getTime()/1000);
			district.setStatus(DataStatus.DISABLED);
			distrticDao.updateOrDel(district);   //更新掉
		}
		return 1;
	}

	/**
	 * 根据id获取区域
	 * 
	 */
	@Override
	public TrainingDistrictDto getByIdTraining(String id) {
		TrainingDistrictDto district = distrticDao.getByDistrict(id);
		return district;
	}

	@Override
	public int updateEdit(TrainingDistrictDto districtDto) {
		List<TrainingDistrict> lsiTra = distrticDao.findAllDistrict(districtDto, null);
		if(lsiTra.size() >0){
			return 2;
		}
		TrainingDistrict discrict = distrticDao.findById(districtDto.getId());
		districtDto.setCreateTime(discrict.getCreateTime());
	    distrticDao.updateEdit(districtDto);
		return 1;										//更新成功
	}

	@Override
	public int updateOrDelByTimeDitrict(String ids) {
		String[] id = ids.split(",");
		for (String str : id) {
			TimeDistrict timeDistrict = timeDistrictDao.findById(str);
			if(timeDistrict==null){
				return 2;					//无记录  
			}
			timeDistrict.setStatus(DataStatus.DISABLED);
			timeDistrict.setUpdateTime(new Date().getTime()/1000);
			timeDistrictDao.update(timeDistrict);
		}
		return 1;						//成功
	}

	/**
	 * id获取时间段对象
	 */
	@Override
	public TrainingTimeDistrictDto getByIdTimeDistrict(String id) {
		TimeDistrict timeDistrict = timeDistrictDao.findById(id);
		TrainingTimeDistrictDto timeDistrictDto = BeanUtils.createBeanByTarget(timeDistrict, TrainingTimeDistrictDto.class);
		return timeDistrictDto;
	}

	@Override
	public int updateEditByTimeDistrict(TrainingTimeDistrictDto timeDistrictDto) {
		TimeDistrict timeDistrict = timeDistrictDao.findById(timeDistrictDto.getId());
		List<TimeDistrict> listTime = timeDistrictDao.findAllTimeDistrict(timeDistrictDto, null);
		if(listTime.size()>0){
			return 2; 
		}
		timeDistrictDto.setCreateTime(timeDistrict.getCreateTime());
		timeDistrictDto.setStatus(DataStatus.ENABLED);
		timeDistrictDto.setUpdateTime(new Date().getTime()/1000);
		timeDistrictDao.updateTimeDrict(timeDistrictDto);
		return 1;		//修改成功
	}

	@Override
	public int insertAddress(TrainingAddressDto addressDto) {
		
		//判断地址是否存在
		TrainingSpaceManagerDto spaceDtoOrAddress = new TrainingSpaceManagerDto();
		spaceDtoOrAddress.setAddress(addressDto.getAddress());
		List<TrainingAddress> traAddIsNo = spaceDao.validateBySpaceAll(spaceDtoOrAddress, null);
		if(traAddIsNo.size()>0){
			return 3;			
		}
		//判断场地名称是否存在
		TrainingSpaceManagerDto spaceDtoOrPla = new TrainingSpaceManagerDto();
		spaceDtoOrPla.setPlace(addressDto.getPlace());
		List<TrainingAddress> traPlaIsNo = spaceDao.validateBySpaceAll(spaceDtoOrPla, null);
		if(traPlaIsNo.size()>0){
			return 4;			
		}
		
		//经纬度是否唯一
		TrainingSpaceManagerDto spaceDtoOrprinciple = new TrainingSpaceManagerDto();
		spaceDtoOrprinciple.setLatitude(addressDto.getLatitude());
		spaceDtoOrprinciple.setLongtitude(addressDto.getLongtitude());
		List<TrainingAddress> traPleIsNo = spaceDao.validateBySpaceAll(spaceDtoOrprinciple, null);
		if(traPleIsNo.size()>0){
			return 5;			
		}
		//----------------------------验证结束--------------
		
		TrainingAddressDistrictTime trainingADT = new TrainingAddressDistrictTime();
		//填充场地信息
		TrainingAddress trainingAdd = BeanUtils.createBeanByTarget(addressDto, TrainingAddress.class);
		trainingAdd.setId(UUID.randomUUID().toString());
		trainingAdd.setCreateTime(new Date().getTime()/1000);
		trainingAdd.setStatus(DataStatus.ENABLED);
		spaceDao.insertTraAdd(trainingAdd);
		// int a_id = spaceDao.insertTraAdd(trainingAdd);			//插入场地 id直接返回给对象
		
		String[] t_id = addressDto.getTimeIdTrain().split(",");		//时间段集合 	
		for (String str : t_id) {
			trainingADT.setId(UUID.randomUUID().toString());
			trainingADT.setAddressId(trainingAdd.getId());
			trainingADT.setDistrictTimeId(str);
			trainingADT.setCreateTime(new Date().getTime()/1000);
			trainingADT.setStatus(DataStatus.ENABLED);
			spaceDao.insertTimeDis_TimeDao(trainingADT);
		}
		return 1;
		//return 0;
	}

	/**
	 * 拼接时间格式
	 */
	@Override
	public List<TrainingTimeDistrictExDto> findAllTime() {
		List<TrainingTimeDistrictExDto> result = new ArrayList<TrainingTimeDistrictExDto>();
		TrainingTimeDistrictDto t = new TrainingTimeDistrictDto();
		List<TimeDistrict> list = timeDistrictDao.findAllTimeDistrict(t, null);
		for (TimeDistrict time : list) {
			TrainingTimeDistrictExDto exDto = new TrainingTimeDistrictExDto();
			exDto.setStrTime(time.getDayOfWeek() +time.getStartTime()+"-"+time.getEndTime());
			exDto.setId(time.getId());
			result.add(exDto);
		}
		return result;
	}

	/**
	 * @author YIn
	 * @time:2015年9月7日 下午2:42:35
	 */
	@Override
	public List<TrainingTimeDistrictDto> findTimeDistrictBySpaceId(
			String spaceId) {
		//List<TimeDistrict> list = timeDistrictDao.findTimeDistrictBySpaceId(spaceId);
		List<TrainingTimeDistrictDto> list = BeanUtils.createBeanListByTarget(timeDistrictDao.findTimeDistrictBySpaceId(spaceId), TrainingTimeDistrictDto.class);
		return list;
	}

	@Override
	public TrainingAddressDto findByIdTraAddress(String id) {
		TrainingAddress traAdd = spaceDao.findById(id);
		TrainingAddressDto traAddDto = BeanUtils.createBeanByTarget(traAdd, TrainingAddressDto.class);
		List<TrainingAddressDistrictTime> listTime =spaceDao.findByAIdToTime(id);	//根据场地id查询对象时间段
		String timeId ="";
		for (TrainingAddressDistrictTime tra : listTime) {							//拼接场地对应时间段id
			timeId += tra.getDistrictTimeId() +",";
		}
		if(timeId!=null&&!timeId.equals("")){
			timeId =timeId.substring(0,timeId.length()-1);
		}
		traAddDto.setTimeIdTrain(timeId);
		return traAddDto;
	}

	@Override
	public int editAddressOrTime(TrainingAddressDto addressDto) {
		TrainingAddress addressPojo = spaceDao.findById(addressDto.getId());
		if(addressPojo==null){
			return 2;
		}
		//没有判断重复
		TrainingAddress trainingAddress =BeanUtils.createBeanByTarget(addressDto, TrainingAddress.class);
		if(addressDto.getThumbnail()==null ||addressDto.getThumbnail().equals("")){
			addressDto.setThumbnail(addressPojo.getThumbnail());
		}
		trainingAddress.setStatus(DataStatus.ENABLED);
		trainingAddress.setUpdateTime(new Date().getTime()/1000);
		trainingAddress.setCreateTime(addressPojo.getCreateTime());
		int addStat = spaceDao.updateAddress(trainingAddress);							//更新场地信息
		if(addStat<=0){
			return 0;
		}
		int timeAddSta = spaceDao.updateAddress_id(trainingAddress.getId());			//跟新掉所有关联信息
		/*if(timeAddSta<=0){
			return 0;
		}*/
		if(addressDto.getTimeIdTrain()!=null && !addressDto.getTimeIdTrain().equals("")){		//时间段集合 为null 不执行
			String[] id = addressDto.getTimeIdTrain().split(",");
			for (String str : id) {
				TrainingAddressDistrictTime addOrTimeOld = spaceDao.getByIdAddOrTime(str,addressDto.getId());			//str时间段id
				if(addOrTimeOld == null){					//不存在新增
					TrainingAddressDistrictTime addOrTime = new TrainingAddressDistrictTime();
					addOrTime.setId(UUID.randomUUID().toString());
					addOrTime.setStatus(DataStatus.ENABLED);
					addOrTime.setAddressId(addressDto.getId()); 
					addOrTime.setDistrictTimeId(str);
					addOrTime.setCreateTime(new Date().getTime()/1000);
					spaceDao.insertAddOrTime(addOrTime);					//最新场地时间段关联信息 插入
				}else {										//存在修改
					TrainingAddressDistrictTime addOrTime = new TrainingAddressDistrictTime();
					addOrTime.setId(addOrTimeOld.getId());
					addOrTime.setStatus(DataStatus.ENABLED);
					addOrTime.setLastUpdateTime(new Date().getTime()/1000);
					addOrTime.setAddressId(addressDto.getId());
					addOrTime.setDistrictTimeId(str);
					addOrTime.setCreateTime(addOrTimeOld.getCreateTime()/1000);
					spaceDao.updateByKey(addOrTime);						//最新的场地时间段关联信息更新进去
				}
			}
		}
		return 1;
	}

	@Override
	public int deleteByAddress(String id) {
		String[] ids = id.split(",");
		for (String str : ids) {
			TrainingAddress tra=spaceDao.findById(str);
			if(tra == null){
				return 2;
			}
			tra.setStatus(DataStatus.DISABLED);
			spaceDao.updateAddress(tra);
			spaceDao.updateAddress_id(str);  //删除关联信息
		}
		return 1;
	}

	@Override
	public int insertBytimeAdd(TrainingAddressDto addressDto) {
		String[] ids = addressDto.getTimeIdTrain().split(",");
		if(ids.length>0){
			for (String str : ids) {
				TrainingAddressDistrictTime tra = new TrainingAddressDistrictTime();
				tra.setAddressId(addressDto.getId());					//场地对应id
				tra.setDistrictTimeId(str);
				List<TrainingAddressDistrictTime> lisTra =  spaceDao.findTimeAddress(tra);
				if(lisTra.size()>0){
					TrainingAddressDistrictTime traUp = lisTra.get(0);
					traUp.setStatus(DataStatus.ENABLED);
					traUp.setLastUpdateTime(new Date().getTime()/1000);
					spaceDao.updateTimeDis_TimeDao(traUp);			//更新状态
				}else{
					tra.setId(UUID.randomUUID().toString());
					tra.setCreateTime(new Date().getTime()/1000);
					spaceDao.insertTimeDis_TimeDao(tra);			//添加
				}
			}
			return 1;
		}
		return 0;
	}

	@Override
	public List<SpaceDto> findAllSpace() {
		return spaceDao.findAllSpace();
	}
	@Override
	public List<TrainingTimeDistrictDto> findAllTiemDistrict(
			TrainingTimeDistrictDto timeDistrictDto) {
		List<TrainingTimeDistrictDto> timeDtoList = new ArrayList<TrainingTimeDistrictDto>();
		List<TimeDistrict> timeListAll = timeDistrictDao.findAllTimeDistrict(timeDistrictDto,null);
		timeDtoList = BeanUtils.createBeanListByTarget(timeListAll, TrainingTimeDistrictDto.class);
		return timeDtoList;
	}

	@Override
	public List<TrainingDistrictDto> findAllDistrictNoPage() {
		TrainingDistrictDto isdto = new TrainingDistrictDto();
		List<TrainingDistrict> lis =distrticDao.findAllDistrict(isdto,null);
		List<TrainingDistrictDto> lisDisDto = BeanUtils.createBeanListByTarget(lis, TrainingDistrictDto.class);
		return lisDisDto;
	}

	@Override
	public TrainingAddressDto getByIdAddress(String spaceId) {
		TrainingAddress tra = spaceDao.findById(spaceId);
		TrainingAddressDto traSpace= BeanUtils.createBeanByTarget(tra, TrainingAddressDto.class);
		return traSpace;
	}
}
