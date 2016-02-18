package com.tianfang.train.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lombok.Getter;

import org.apache.log4j.Logger;
import org.dom4j.IllegalAddException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.JsonUtil;
import com.tianfang.train.dto.CollegelessonClassDto;
import com.tianfang.train.dto.CollegelessonDto;
import com.tianfang.train.dto.CourseClassAdressTimeDto;
import com.tianfang.train.dto.CourseClassDtoX;
import com.tianfang.train.dto.CourseClassRespDto;
import com.tianfang.train.dto.LocaleClass;
import com.tianfang.train.mapper.CourseClassExMapper;
import com.tianfang.train.mapper.CourseClassMapper;
import com.tianfang.train.mapper.TrainingDistrictMapper;
import com.tianfang.train.mapper.XCourseClassMapper;
import com.tianfang.train.mapper.XTrainingUserMapper;
import com.tianfang.train.pojo.CourseClass;
import com.tianfang.train.pojo.CourseClassExample;
import com.tianfang.train.pojo.TimeDistrict;
import com.tianfang.train.pojo.TrainingAddress;
import com.tianfang.train.pojo.TrainingDistrict;

@Repository
public class CourseClassDao extends MyBatisBaseDao<CourseClass> {

	@Autowired
    @Getter
    private CourseClassMapper mapper;
	@Autowired
	@Getter
	private CourseClassExMapper exMapper;
	@Resource
	private TrainingDistrictMapper districtMapper;
	@Resource
	private XCourseClassMapper xmapper;
	@Resource
	private XTrainingUserMapper xumapper;
	@Resource
	private TrainingAddressDao trainingAddressDao;
	@Resource
	private TrainingTimeDistrictDao trainingTimeDistrictDao;
	private Logger log = Logger.getLogger(getClass());
	
	
	/**
	 * 
		 * 此方法描述的是：学院-->课程介绍 培训课程数据展示
		 * @author: cwftalus@163.com
		 * @version: 2015年10月10日 上午9:31:42
	 */
	public List<CollegelessonDto> findByPage(PageQuery page,Integer marked) {
		// TODO Auto-generated method stub
		return exMapper.findByPage(page,marked);
	}

	/**
	 * 
		 * 此方法描述的是：学院-->课程介绍 培训课程数据展示--数量
		 * @author: cwftalus@163.com
		 * @version: 2015年10月10日 上午9:31:42
	 */
	public long count(Integer marked){
		return exMapper.count(marked);
	}
	
	
	public List<LocaleClass> findCourseClassByAssistantId(String assistantId){
		
		List<LocaleClass> dtos = exMapper.findCourseClassByAssistantId(assistantId);
		
		return dtos;
	}
	
	public List<LocaleClass> findCourseClassByClassIds(List<String> ids){
		
		List<LocaleClass> dtos = exMapper.findCourseClassByClassIds(ids);
		
		return dtos;
	}
	
	public List<CourseClass> findByIds(String courseId, String timeDistrictId,String addressId) {
	    CourseClassExample example = new CourseClassExample();
	    CourseClassExample.Criteria criteria = example.createCriteria();
	    if (null != courseId) {
	        criteria.andCourseIdEqualTo(courseId);
	    }
	    if (null != timeDistrictId) {
	        criteria.andTimeDistrictIdEqualTo(timeDistrictId);
	    }
	    if (null != addressId) {
	        criteria.andAddressIdEqualTo(addressId);
	    }
	    List<CourseClass> results = mapper.selectByExample(example);
	    return CollectionUtils.isEmpty(results) ? null : results;
	}
	
	public List<CourseClass> findByIdss(String courseId, String timeDistrictId,String addressId) {
        CourseClassExample example = new CourseClassExample();
        CourseClassExample.Criteria criteria = example.createCriteria();
        if (null != courseId) {
            criteria.andCourseIdEqualTo(courseId);
        }
        if (null != timeDistrictId) {
            criteria.andTimeDistrictIdEqualTo(timeDistrictId);
        }
        if (null != addressId) {
            criteria.andAddressIdEqualTo(addressId);
        }
        List<CourseClass> results = mapper.selectByExample(example);
        return CollectionUtils.isEmpty(results) ? null : results;
    }
    
	
	public List<CourseClass> findByIds(String courseId) {
        CourseClassExample example = new CourseClassExample();
        CourseClassExample.Criteria criteria = example.createCriteria();
        if (null != courseId) {
            criteria.andCourseIdEqualTo(courseId);
        }
        criteria.andStatusEqualTo(DataStatus.ENABLED);
        List<CourseClass> results = mapper.selectByExample(example);
        return CollectionUtils.isEmpty(results) ? null : results;
    }
	
	public List<CourseClassRespDto> findClassByCourseId(String courseId) {
	    return exMapper.findClassByCourseId(courseId);
	}
	/**
	 * 通过课程Id：courseId、区域Id：districtId查询可报名的地点
	 * @return
	 * @2015年9月2日
	 */
	public List<CourseClassDtoX> getClassByCDId(String courseId, String districtId)
	{
		List<CourseClassDtoX> lst = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>(2);
			map.put("courseId", courseId);
			map.put("districtId", districtId);
			lst = xmapper.getClassByCDId(map);
			int size = lst.size();
			if(size > 0){
				TrainingDistrict district = districtMapper.selectByPrimaryKey(districtId);
				for (CourseClassDtoX ccDtoX : lst) {
					fillDataV02(ccDtoX,district.getName());
				}
			}
		} catch (Exception e) {
			log.error("根据课程id和区域id查询可报名的地点发生异常", e);
		}
		return lst;
	}
	
	/**
	 * 根据各个表的关联Id进行数据填充
	 * @param ccDtoX
	 * @return
	 * @2015年9月6日
	 */
	public void fillData(CourseClassDtoX ccDtoX){
		TrainingAddress address = trainingAddressDao.getAddrById(ccDtoX.getAddressId());
		TimeDistrict timeDistrict = trainingTimeDistrictDao.findById(ccDtoX.getTimeDistrictId());
		ccDtoX.setPlace(address.getPlace());
		ccDtoX.setLatitude(address.getLatitude());
		ccDtoX.setLongtitude(address.getLongtitude());
		ccDtoX.setAddressValue(address.getAddress());
		ccDtoX.setDayOfWeek(timeDistrict.getDayOfWeek());
		ccDtoX.setStartTime(timeDistrict.getStartTime());
		ccDtoX.setEndTime(timeDistrict.getEndTime());
	}
	
	public void fillDataV02(CourseClassDtoX ccDtoX, String district){
		TrainingAddress address = trainingAddressDao.getAddrById(ccDtoX.getAddressId());
		TimeDistrict timeDistrict = trainingTimeDistrictDao.findById(ccDtoX.getTimeDistrictId());
		ccDtoX.setPlace(address.getPlace());
		ccDtoX.setLatitude(address.getLatitude());
		ccDtoX.setLongtitude(address.getLongtitude());
		ccDtoX.setAddressValue(address.getAddress());
		ccDtoX.setDayOfWeek(timeDistrict.getDayOfWeek());
		ccDtoX.setStartTime(timeDistrict.getStartTime());
		ccDtoX.setEndTime(timeDistrict.getEndTime());
		ccDtoX.setDistrict(district);
		ccDtoX.setOpenDateFmt(JsonUtil.parseTimestamp(ccDtoX.getOpenDate(), "yyyy-MM-dd"));
	}
	
	/**
	 * 根据条件查询课程小节信息，
	 * 并根据小节上课所在地进行分组
	 * @param courseId
	 * @param districtId
	 * @return
	 * @2015年10月11日
	 */
	public List<CourseClassDtoX> getClassGroup(String courseId, String districtId){
		
		List<CourseClassDtoX> relst = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>(2);
			map.put("courseId", courseId);
			map.put("districtId", districtId);
			relst = xmapper.getClassByCDGroupByAID(map);
			int size = relst.size();
			if(size > 0){
				TrainingDistrict district = districtMapper.selectByPrimaryKey(districtId);
				for (CourseClassDtoX ccDtoX : relst) {
					fillDataV02(ccDtoX,district.getName());
					CourseClassDtoX dto = getMinMaxOpenDate(ccDtoX.getCourseId(), ccDtoX.getAddressId());
					if(dto != null){
						ccDtoX.setMinOpenDateFmt(JsonUtil.parseTimestamp(dto.getMinOpenDate(), "yyyy-MM-dd"));
						ccDtoX.setMaxOpenDateFmt(JsonUtil.parseTimestamp(dto.getMaxOpenDate(), "yyyy-MM-dd"));
					}
				}
			}
		} catch (Exception e) {
			log.error("根据courseID,district查询小节信息，根据上课地点进行分组", e);
		}
		return relst;
	}
	
	/**
	 * 根据courseId和addressId查询小节信息
	 * @param courseId
	 * @param addressId
	 * @return
	 * @2015年10月11日
	 */
	public List<CourseClassDtoX> getClassList(String courseId, String addressId){
		
		List<CourseClassDtoX> relst = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>(2);
			map.put("courseId", courseId);
			map.put("addressId", addressId);
			relst = xmapper.getClassByAddressID(map);
		} catch (Exception e) {
			log.error("根据courseID,addressId查询小节信息", e);
		}
		return relst;
	}
	
	public CourseClassDtoX findCourseClassByClassId(String id){
		CourseClass courseClass = mapper.selectByPrimaryKey(id);
		if(null != courseClass){
			return BeanUtils.createBeanByTarget(courseClass, CourseClassDtoX.class);
		}
		return null;
		
	}
	
	public List<CourseClassDtoX> findAvailableCourseClassByClassId(String id){
		List<CourseClassDtoX> dtos = xmapper.findAvailableCourseClassByClassId(id);
		return dtos;
	}

	public CourseClassAdressTimeDto getCourseClassAdressTimeInfoByClassid(String id) {
		return xmapper.getAvailableCourseClassAdressTimeInfoByClassid(id);
	}
	
	/**
	 * 通过courseId和addressId查询课程（小节）
	 * @param couserId
	 * @param addressId
	 * @return
	 * @2015年9月5日
	 */
	public List<CourseClassDtoX> getClassByCAId(String couserId, String addressId){
		List<CourseClassDtoX> ccdtoLst = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>(2);
			map.put("courseId", couserId);
			map.put("addressId", addressId);
			ccdtoLst = xmapper.getClassByCAId(map);
			if(ccdtoLst != null){
				for (CourseClassDtoX ccdto : ccdtoLst) {
					fillData(ccdto);
				}
			}
		} catch (Exception e) {
			log.error("根据courseId和classId查询课程（小节）发生异常", e);
		}
		return ccdtoLst;
	}
	
	/**
	 * 根据条件更新订单信息
	 * @param orderNo
	 * @param tradeNo
	 * @param status
	 * @return
	 * @2015年9月5日
	 */
	public boolean updateOrder(String orderNo, String tradeNo, Integer status)
	{
		boolean flag = false;
		try {
			Integer state = 0;
			if(status == 1){ // 支付成功
				state = 1;
			}
			if(status == 2){ // 支付失败
				state = 2;
			}
			Map<String, Object> map = new HashMap<String, Object>(3);
			map.put("state", state);
			map.put("orderNo", orderNo);
			map.put("tradeNo", tradeNo);
			xumapper.updateOrder(map);
			flag = true;
		} catch (Exception e) {
			log.error("更新订单信息是发生异常", e);
		}
		return flag;
	}
	
	/**
	 * @author YIn
	 * @time:2015年9月6日 下午5:50:13
	 */
	public List<TrainingAddress> findSpaceByCourseId(String courseId) {
		List<TrainingAddress> list =  exMapper.findSpaceByCourseId(courseId);
		return list;
	}
	
	/**
	 * 获取指定课程的总价
	 * @param courseId
	 * @return
	 * @2015年9月7日
	 */
	public double getSumPrice(String courseId){
		Double sumPrice = new Double(0.0);
		try {
			Map<String, Object> map = new HashMap<String, Object>(1);
			map.put("courseId", courseId);
			CourseClassDtoX dto = xmapper.getSumPrice(map);
			if(dto != null){
				sumPrice = dto.getSumPrice();
			}else{
				sumPrice = 0.0;
			}
		} catch (Exception e) {
			log.error("查询课程的总价发生异常", e);
		}
		return sumPrice;
	}
	
	/**
	 * 通过courseId查询课程（小节）列表
	 * @param courseId
	 * @return
	 * @2015年9月7日
	 */
	public List<CourseClassDtoX> getClassesByCId(String courseId){
		List<CourseClassDtoX> lst = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>(1);
			map.put("courseId", courseId);
			lst = xmapper.getClassesByCId(map);
			int size = lst.size();
			if(size > 0){
				for (CourseClassDtoX ccDtoX : lst) {
					fillData(ccDtoX);
				}
			}
		} catch (Exception e) {
			log.error("根据courseId查询class过程发生异常", e);
		}
		return lst;
	}
	
	/**
	 * 通过classId查询课程（小节）详情
	 * @param classId
	 * @return
	 * @2015年9月7日
	 */
	public CourseClassDtoX getCCDet(String classId){
		CourseClassDtoX ccdtox = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>(1);
			map.put("id", classId);
			ccdtox = xmapper.getCCDet(map);
		} catch (Exception e) {
			log.error("根据id查询课程（小节）发生异常", e);
		}
		return ccdtox;
	}
	
	/**
	 * 根据课程id和地区id查询课程(小节)信息
	 * @param courseId 课程id
	 * @param addressId 地区id
	 * @author xiang_wang
	 * 2015年10月11日下午3:20:18
	 */
	public List<CollegelessonClassDto> queryLessonClass(String courseId, String addressId){
		if (null == courseId || "".equals(courseId.trim())){
			throw new IllegalAddException("对不起,queryLessonClass方法courseId访问参数为空");
		}
		if (null == addressId || "".equals(addressId.trim())){
			throw new IllegalAddException("对不起,queryLessonClass方法addressId访问参数为空");
		}
		Map<String, Object> param = new HashMap<String, Object>(2);
		param.put("courseId", courseId);
		param.put("addressId", addressId);
		return xmapper.queryLessonClass(param);
	}
	
	/**
	 * 根据条件查询某课程在某地的课程安排的起止日期
	 * @param courseId
	 * @param districtId
	 * @return
	 * @2015年10月12日
	 */
	public CourseClassDtoX getMinMaxOpenDate(String courseId, String addressID){
		CourseClassDtoX openDateObj = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>(2);
			map.put("courseID", courseId);
			map.put("addressID", addressID);
			openDateObj = xmapper.getMinMaxOpenDate(map);
		} catch (Exception e) {
			log.error("根据courseID,address查询某课程在某地的最早、最迟开课时间发生异常", e);
		}
		return openDateObj;
	}
}
