/**
 * 
 */
package com.tianfang.train.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.dom4j.IllegalAddException;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.DateUtils;
import com.tianfang.common.util.JsonUtil;
import com.tianfang.common.util.StringUtils;
import com.tianfang.common.util.UUIDGenerator;
import com.tianfang.train.dao.CourseClassDao;
import com.tianfang.train.dao.CourseTagDao;
import com.tianfang.train.dao.CourseTypeDao;
import com.tianfang.train.dao.TrainingAddressDao;
import com.tianfang.train.dao.TrainingCourseDao;
import com.tianfang.train.dao.TrainingDistrictDao;
import com.tianfang.train.dto.CollegelessonClassDto;
import com.tianfang.train.dto.CollegelessonDetailDto;
import com.tianfang.train.dto.CollegelessonDto;
import com.tianfang.train.dto.CourseClassDtoX;
import com.tianfang.train.dto.CourseClassReqDto;
import com.tianfang.train.dto.CourseTagDto;
import com.tianfang.train.dto.TrainingCourseDto;
import com.tianfang.train.dto.TrainingCourseDtoX;
import com.tianfang.train.dto.TrainingCourseReqDto;
import com.tianfang.train.dto.TrainingDistrictDtoX;
import com.tianfang.train.dto.TrainingSpaceManagerDto;
import com.tianfang.train.pojo.CourseClass;
import com.tianfang.train.pojo.CourseTag;
import com.tianfang.train.pojo.TrainingAddress;
import com.tianfang.train.pojo.TrainingCourse;
import com.tianfang.train.service.ITrainingCourService;

/**
 * 
 * @author wk.s
 * @date 2015年9月1日
 */
@Service
public class ITrainingCourServiceImpl implements ITrainingCourService {

	@Resource
	private CourseTypeDao courseTypeDao;
	@Resource
	private CourseClassDao courseClassDao;
	@Resource
	private TrainingCourseDao trainingCourseDao;
	@Resource
	private TrainingAddressDao trainingAddressDao;
	@Resource
	private TrainingDistrictDao trainingDistrictDao;
	@Resource
	private CourseTagDao courseTagDao;
	
	
	/**
		 * 此方法描述的是：学院-->课程介绍 培训课程数据展示
		 * @author: cwftalus@163.com
		 * @version: 2015年10月10日 上午9:29:22
	 */
	public PageResult<CollegelessonDto> findByPage(PageQuery page,Integer marked){
		PageResult<CollegelessonDto> dataList = new PageResult<CollegelessonDto>();
		List<CollegelessonDto> resultList = courseClassDao.findByPage(page,marked);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for(CollegelessonDto collegeDto : resultList){
			String openDtS = JsonUtil.parseTimestamp(collegeDto.getOpen_date(), DateUtils.YMD_DASH);
			collegeDto.setOpenDate(openDtS);
			
			Date openDtD = DateUtils.StringToDate(openDtS, DateUtils.YMD_DASH);
			
			Date endDtD = DateUtils.dayOffset(openDtD, collegeDto.getCourse_time()*7);
			
			collegeDto.setEndDate(DateUtils.format(endDtD, DateUtils.YMD_DASH));
		}
		dataList.setResults(resultList);
		dataList.setTotal(courseClassDao.count(marked));
		return dataList;
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.juju.sport.train.api.ITrainingCourService#getACourseLst()
	 */
	@Override
	public List<TrainingCourseDto> getACourseLst(String ava, String marked) 
	{
		List<TrainingCourseDto> dlst = null;
		List<TrainingCourseDtoX> lst = trainingCourseDao.getACourseLst(ava, marked);
		int size = lst.size();
		if (size > 0) {
			dlst = new ArrayList<TrainingCourseDto>(size);
			for (TrainingCourseDtoX tc : lst) {
//				Integer typeID = tc.getType();
				TrainingCourseDto dto = new TrainingCourseDto();
//				CourseTypeDtoX ct = courseTypeDao.getCTByID(typeID);
//				if (ct != null) {
//					dto.setTypeX(ct.getName());
//					BeanUtils.copyProperties(tc, dto);
//					dlst.add(dto);
//				} else {
//					throw new RuntimeException("查询到的数据不完整");
//				}
				// 计算平均价格
				Double sumPrice = courseClassDao.getSumPrice(tc.getId());
				DecimalFormat df = new DecimalFormat("######0.00");
				Double avgPrice = Double.parseDouble(df.format(sumPrice / tc.getCourseTime()));
				dto.setAvgPrice(avgPrice);
				BeanUtils.copyProperties(tc, dto);
				dto.setStartTimeFmt(JsonUtil.parseTimestamp(dto.getStartTime(), "MM") + "月");
				dto.setEndTimeFmt(JsonUtil.parseTimestamp(dto.getEndTime(), "MM") + "月");
				CourseTagDto ctdto = courseTagDao.getCTBId(tc.getTagId());
				dto.setTagName(ctdto.getTagName());
				dlst.add(dto);
			}
		}
		return dlst;
	}

	@Override
	public TrainingCourseDtoX getTrainCourse(String id,String courseId) {
		TrainingCourseDtoX tc = new TrainingCourseDtoX();
		tc = trainingCourseDao.getCourse(id,courseId);
		CourseTagDto ctdto = courseTagDao.getCTBId(tc.getTagId());
		tc.setTagName(ctdto.getTagName());
		return tc;
	}

	@Override
    public TrainingCourseDtoX getCourseDet(String courseId) {
        TrainingCourseDtoX tc = new TrainingCourseDtoX();
        tc = trainingCourseDao.getCourseById(courseId);
        CourseTagDto ctdto = courseTagDao.getCTBId(tc.getTagId());
        tc.setTagName(ctdto.getTagName());
        return tc;
    }
	
	public TrainingCourseDtoX getTrainingCourse(String courseId) {
	    TrainingCourse tc = trainingCourseDao.findById(courseId);
	    TrainingCourseDtoX tcx = BeanUtils.createBeanByTarget(tc, TrainingCourseDtoX.class);
	    CourseTag courseTag = courseTagDao.findById(tcx.getTagId());
	    tcx.setTagName(courseTag.getTagName());
        return tcx;
	}
	
	@Override
	public List<TrainingDistrictDtoX> getADistrict() {
		List<TrainingDistrictDtoX> lst = trainingDistrictDao.getADistrict();
		return lst;
	}

	@Override
	public List<CourseClassDtoX> getClassByCDId(String courseId, String districtId) {
		List<CourseClassDtoX> lst = courseClassDao.getClassByCDId(courseId, districtId);
		return lst;
	}
	
	public List<CourseClassDtoX> getClassGroup(String courseId, String districtId){
		List<CourseClassDtoX> lst = courseClassDao.getClassGroup(courseId, districtId); 
		return lst;
	}

	public List<CourseClassDtoX> getClassList(String courseId, String addressId){
		List<CourseClassDtoX> lst = courseClassDao.getClassList(courseId, addressId); 
		return lst;
	}
	
	@Override
	public PageResult<TrainingCourseDtoX> findByPage(
			TrainingCourseDtoX trainingCourseDto, PageQuery page) {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
		List<TrainingCourseDtoX> results = trainingCourseDao.findByPage(
				trainingCourseDto, page);
		if (null != results && results.size()>0) {
		    for(TrainingCourseDtoX trainingCourseDtoX : results) {
	            Date dateS = new Date(trainingCourseDtoX.getStartTime() * 1000);
	            Date dateE = new Date(trainingCourseDtoX.getEndTime() * 1000);
	            trainingCourseDtoX.setTimeQuantum(trainingCourseDtoX.getDayOfWeek()+"/"+sdf.format(dateS)+"-"+sdf.format(dateE));           
	        }
		}		
		page.setTotal(trainingCourseDao.count(trainingCourseDto));
//		if (null != results && results.size()>0) {
//		    page.setTotal(results.size());
//		}
//		else{
//		    page.setTotal(0);
//		}
		return new PageResult<TrainingCourseDtoX>(page, results);
	}

	@Override
	public List<CourseTagDto> findCourseTag(CourseTagDto courseTagDto) {
		return courseTagDao.findAllCourseTag(courseTagDto);

	}

	@Override
	public List<CourseClassDtoX> getClassByCAId(String couserId, String addressId) {
		List<CourseClassDtoX> ccdto = courseClassDao.getClassByCAId(couserId, addressId);
		return ccdto;
	}

	@Override
	public boolean updateOrder(String orderNo, String tradeNo, Integer status) {
		boolean flag = courseClassDao.updateOrder(orderNo, tradeNo, status);
		return flag;
	}

	/**
	 * @author YIn
	 * @time:2015年9月6日 下午5:49:32
	 */
	@Override
	public List<TrainingSpaceManagerDto> findSpaceByCourseId(String courseId) {
		List<TrainingAddress> tc = courseClassDao.findSpaceByCourseId(courseId);
		List<TrainingSpaceManagerDto> tcDto = BeanUtils.createBeanListByTarget( tc,TrainingSpaceManagerDto.class);
		return tcDto;
	}

	@Override
	public Object deleteCourseInfo(String delId,String courseClassIds) {
		String[] idArr = delId.split(",");
		String[] courseClassIdArr = null;
		if (StringUtils.isNotBlank(courseClassIds)){
		    courseClassIdArr = courseClassIds.split(",");
		    for (int i=0;  i<courseClassIdArr.length; i++) {
	            CourseClass courseClass = trainingCourseDao.findByIdCourseId(courseClassIdArr[i], idArr[i]);
	            if (null != courseClass) {
//	              return 0;// 无此条记录
	                courseClass.setUpdateTime(new Date().getTime()/1000);
	                courseClass.setStatus(DataStatus.DISABLED);
	                courseClassDao.updateByPrimaryKey(courseClass);
	            }
	           
	        }
		}		
//		Integer courseClassId=null;
		
		for (String ids : idArr) {
			TrainingCourse trainingCourse = trainingCourseDao.findById(ids);
			if (trainingCourse == null) {
			    return 0;// 无此条记录
			}
			trainingCourse.setLastUpdateTime(new Date().getTime()/1000);
			trainingCourse.setStatus(DataStatus.DISABLED);
			trainingCourseDao.updateByTrainingCourse(trainingCourse);
		}
		return 1;
	}

	@Override
	public List<CourseClassDtoX> getClassesByCId(String courseId) {
		List<CourseClassDtoX> lst = courseClassDao.getClassesByCId(courseId);
		return lst;
	}
	
//	public TrainingCourseReqDto save(TrainingCourseReqDto trainingCourseDtoX, Integer addressId,
//        String timeDistrictIds, List<CourseClassReqDto> courseClassReqDto)  throws Exception {
//	    return null;
//	    SimpleDateFormat result_form = new SimpleDateFormat("yyyy-MM-dd");
//	    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");
//	    TrainingCourse trainingCourse = new TrainingCourse();
//	    trainingCourse = BeanUtils.createBeanByTarget(trainingCourseReqDto, TrainingCourse.class);  
//        TrainingCourse trainingcourse = trainingCourseDao.findByNameCourseTime(trainingCourseReqDto.getName(), trainingCourseReqDto.getCourseTime());
//        if (null != trainingcourse) {
//            trainingCourseReqDto.setAddress(String.valueOf(1));
//            return trainingCourseReqDto;//课程已经有此条记录
//        }
//        trainingCourse.setStartTime(sf.parse(trainingCourseReqDto.getStartTime().substring(0, 8)).getTime()/1000);
//        trainingCourse.setEndTime(sf.parse(trainingCourseReqDto.getEndTime().substring(0, 8)).getTime()/1000);
//        trainingCourse.setActualStudent(DataStatus.DISABLED);
//        trainingCourse.setCreateTime(new Date().getTime()/1000);
//        trainingCourse.setStatus(DataStatus.ENABLED);
//        trainingCourseDao.save(trainingCourse);
////        TrainingCourseReqDto trainingCourseDtoXs = BeanUtils.createBeanByTarget(trainingCourse, TrainingCourseReqDto.class);      
////        if (null != addressId && StringUtils.isNotBlank(timeDistrictIds) && !StringUtils.isEmpty(trainingCourseReqDto.getOpenDate()) ) {
////            String[] timeDistrictIdArr = timeDistrictIds.split(","); 
////            for (String timeDistrictId : timeDistrictIdArr) {
////                List<CourseClass> courseClasss = courseClassDao.findByIdss(trainingCourse.getId(), Integer.valueOf(timeDistrictId), addressId);
//////                    if (null != courseClasss && courseClasss.size()>0) {
//////                        courseClass =  courseClasss.get(0);
//////                    }
////                if (null == courseClasss ) {
////                    CourseClass courseclass = new CourseClass();
////                    courseclass.setOpenDate(result_form.parse(trainingCourseReqDto.getOpenDate().substring(0, 10)).getTime()/1000);
////                    courseclass.setAddressId(addressId);
////                    courseclass.setTimeDistrictId(Integer.valueOf(timeDistrictId));
////                    courseclass.setCourseId(trainingCourse.getId());
////                    courseclass.setPrice(BigDecimal.valueOf(Double.valueOf(trainingCourseReqDto.getPrice())));
////                    courseclass.setCreateTime(new Date().getTime()/1000);
////                    courseclass.setActualStudent(DataStatus.DISABLED);
////                    courseclass.setMaxStudent(trainingCourse.getMaxStudent());
////                    courseclass.setStatus(DataStatus.ENABLED);
////                    courseClassDao.insert(courseclass);
////                }
////            }
//            List<CourseClass> courseclasss = courseClassDao.findByIds(trainingCourse.getId());
//            Integer courseMaxStudent = 0;
//            for(CourseClass courseClass1 : courseclasss) {
//                courseMaxStudent =courseMaxStudent+ courseClass1.getMaxStudent();
//            }
//            TrainingCourse trainingCourse1 = trainingCourseDao.findById(trainingCourse.getId());
//            trainingCourse1.setMaxStudent(courseMaxStudent);
//            trainingCourseDao.updateByTrainingCourse(trainingCourse1);
//            return null;
////        }
////        return null;//新增成功
//	}
	
	public static void main(String[] args) throws Exception
    {
//        String dd = "[{'timeDistrictId': '61','addressId': '42','price': '0.01','openDate': '2015-9-5','maxStudent': '1'},{'timeDistrictId': '61','addressId': '42','price': '0.01','openDate': '2015-9-5','maxStudent': '1'}]";
//        List<CourseClassReqDto> ps = new Gson().fromJson(dd, new TypeToken<List<CourseClassReqDto>>(){}.getType());  
//      System.out.println(ps);
        String SOURCE = "Tue Sep 29 2015 00:00:00"; 
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss", new Locale("ENGLISH", "CHINA")); 
        Date myDate = sdf.parse(SOURCE); 
        System.out.println(myDate); 

    }
	
    @Override
    public TrainingCourseReqDto save(TrainingCourseReqDto trainingCourseDtoX,  String jsonClasss) throws Exception
    {        
        List<CourseClassReqDto> courseClassReqDtos = new Gson().fromJson(jsonClasss, new TypeToken<List<CourseClassReqDto>>(){}.getType()); 
        SimpleDateFormat result_form = new SimpleDateFormat("yyyy-MM-dd");
//          SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");
          SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss", new Locale("ENGLISH", "CHINA")); 
          TrainingCourse trainingCourse = new TrainingCourse();
          trainingCourse = BeanUtils.createBeanByTarget(trainingCourseDtoX, TrainingCourse.class);  
            TrainingCourse trainingcourse = trainingCourseDao.findByNameCourseTime(trainingCourseDtoX.getName(), null);
            if (null != trainingcourse) {
                trainingCourseDtoX.setAddress(String.valueOf(1));
                return trainingCourseDtoX;//课程已经有此条记录
            }
            trainingCourse.setId(UUIDGenerator.getUUID());
//            trainingCourse.setStartTime(sdf.parse(trainingCourseDtoX.getStartTime().substring(0, 24)).getTime()/1000);
//            trainingCourse.setEndTime(sdf.parse(trainingCourseDtoX.getEndTime().substring(0, 24)).getTime()/1000);
            trainingCourse.setStartTime(result_form.parse(trainingCourseDtoX.getStartTime()).getTime()/1000);
            trainingCourse.setEndTime(result_form.parse(trainingCourseDtoX.getEndTime()).getTime()/1000);
            trainingCourse.setActualStudent(DataStatus.DISABLED);
            trainingCourse.setCreateTime(new Date().getTime()/1000);
            trainingCourse.setStatus(DataStatus.ENABLED);
//            trainingCourse.setMarked(trainingCourseDtoX.getMarked().byteValue());
            trainingCourseDao.save(trainingCourse);
            if (StringUtils.isNotBlank(jsonClasss)) {
                for (CourseClassReqDto courseClassReqDto : courseClassReqDtos) {
                    List<CourseClass> courseClasss = courseClassDao.findByIdss(trainingCourse.getId(), courseClassReqDto.getTimeDistrictId(), courseClassReqDto.getAddressId());
                    if (null != courseClasss && courseClasss.size()>0) {
                        trainingCourseDtoX.setAddress(String.valueOf(0));
                        return trainingCourseDtoX;//课程场地已有此条记录
                    }
                    courseClassReqDto.setMaxStudent(trainingCourseDtoX.getMaxStudent());
                    CourseClass courseclass = BeanUtils.createBeanByTarget(courseClassReqDto, CourseClass.class);                
                    courseclass.setCourseId(trainingCourse.getId());
                    courseclass.setDeposit(BigDecimal.valueOf(courseClassReqDto.getDeposit()));
                    courseclass.setPrice(BigDecimal.valueOf(Double.valueOf(courseClassReqDto.getPrice().toString())));
                    courseclass.setDiscount(BigDecimal.valueOf(courseClassReqDto.getDiscount()));
                    courseclass.setOpenDate(result_form.parse(courseClassReqDto.getOpenDate()).getTime()/1000);
                    courseclass.setCreateTime(new Date().getTime()/1000);
                    courseclass.setActualStudent(DataStatus.DISABLED);
                    courseclass.setStatus(DataStatus.ENABLED);
                    courseClassDao.insert(courseclass);
                }
            }            
            List<CourseClass> courseclasss = courseClassDao.findByIds(trainingCourse.getId());
            Integer courseMaxStudent = 0;
            if (null != courseclasss) {
                for(CourseClass courseClass1 : courseclasss) {
                    courseMaxStudent =courseMaxStudent+ courseClass1.getMaxStudent();
                }
            }            
            TrainingCourse trainingCourse1 = trainingCourseDao.findById(trainingCourse.getId());
            trainingCourse1.setMaxStudent(courseMaxStudent);
            trainingCourseDao.updateByTrainingCourse(trainingCourse1);
            return trainingCourseDtoX;
    }
	
	public TrainingCourseReqDto edit(TrainingCourseReqDto trainingCourseReqDto, String jsonClasss) throws Exception {
          SimpleDateFormat result_form = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");
//        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss", new Locale("ENGLISH", "CHINA")); 
        TrainingCourse trainingCourse = new TrainingCourse();
        
        trainingCourse = BeanUtils.createBeanByTarget(trainingCourseReqDto, TrainingCourse.class);  
        List<CourseClassReqDto> courseClassReqDtos = new ArrayList<CourseClassReqDto>();
        if (StringUtils.isNotBlank(jsonClasss)) {
            courseClassReqDtos = new Gson().fromJson(jsonClasss, new TypeToken<List<CourseClassReqDto>>(){}.getType()); 
        }
        if (null != trainingCourseReqDto.getId()) {
            TrainingCourse trainingCourse1 = trainingCourseDao.findById(trainingCourseReqDto.getId());
            if (null == trainingCourse1) {
                 trainingCourseReqDto.setAddress(String.valueOf(0));
                 return trainingCourseReqDto;//不存在此条记录
            }
            if (courseClassReqDtos.size()<=0) {
                List<CourseClass> courseClasss = courseClassDao.findByIdss(trainingCourseReqDto.getId(), null, null);
                if (null != courseClasss && courseClasss.size()>0) {
                    for (CourseClass courseClass:courseClasss) {
                        courseClass.setStatus(DataStatus.DISABLED);
                        courseClassDao.updateByPrimaryKeySelective(courseClass);
                    }
                }
//                return trainingCourseReqDto;    
            }

            for (CourseClassReqDto courseClassReqDto:courseClassReqDtos) {
                if(null!= courseClassReqDto) {
                    List<CourseClass> courseClasss = courseClassDao.findByIdss(trainingCourseReqDto.getId(), null, courseClassReqDto.getAddressId());
                    if (null != courseClasss && courseClasss.size()>0) {
                        for (CourseClass courseClass:courseClasss) {
                            courseClass.setStatus(DataStatus.DISABLED);
                            courseClassDao.updateByPrimaryKeySelective(courseClass);
                        }
                    }
                }
                
            }
            for (CourseClassReqDto courseClassReqDto:courseClassReqDtos) {
//                List<CourseClass> courseClasss = courseClassDao.findByIdss(trainingCourseReqDto.getId(), null, courseClassReqDto.getAddressId());
//                if (null != courseClasss && courseClasss.size()>0) {
//                    for (CourseClass courseClass:courseClasss) {
//                        courseClass.setStatus(DataStatus.DISABLED);
//                        courseClassDao.updateByPrimaryKeySelective(courseClass);
//                    }
//                }
                if(null != courseClassReqDto) {
                    List<CourseClass> courseClassss = courseClassDao.findByIds(trainingCourseReqDto.getId(), courseClassReqDto.getTimeDistrictId(), courseClassReqDto.getAddressId());
                    if (null != courseClassss && courseClassss.size()>0) {
                        CourseClass ourseclass = courseClassss.get(0);
                        ourseclass.setPrice(BigDecimal.valueOf(courseClassReqDto.getPrice()));
                        ourseclass.setDeposit(BigDecimal.valueOf(courseClassReqDto.getDeposit()));
                        ourseclass.setDiscount(BigDecimal.valueOf(courseClassReqDto.getDiscount()));
                        ourseclass.setOpenDate(result_form.parse(courseClassReqDto.getOpenDate()).getTime()/1000);
                        ourseclass.setUpdateTime(new Date().getTime()/1000);
                        ourseclass.setStatus(DataStatus.ENABLED);
                        courseClassDao.updateByPrimaryKeySelective(ourseclass);
                    }
                    if (null == courseClassss) {
                        CourseClass courseclass = BeanUtils.createBeanByTarget(courseClassReqDto, CourseClass.class);
                        courseclass.setPrice(BigDecimal.valueOf(courseClassReqDto.getPrice()));
                        courseclass.setDeposit(BigDecimal.valueOf(courseClassReqDto.getDeposit()));
                        courseclass.setDiscount(BigDecimal.valueOf(courseClassReqDto.getDiscount()));
                        courseclass.setCreateTime(new Date().getTime()/1000);
                        courseclass.setOpenDate(result_form.parse(courseClassReqDto.getOpenDate()).getTime()/1000);
                        courseclass.setCourseId(trainingCourseReqDto.getId());
                        courseclass.setActualStudent(DataStatus.DISABLED);
                        courseclass.setStatus(DataStatus.ENABLED);
                        courseClassDao.insert(courseclass);
                    }
                }
                
            }
            List<CourseClass> courseclasss = courseClassDao.findByIds(trainingCourse.getId());
          Integer courseMaxStudent = 0;
          if (null != courseclasss && courseclasss.size()>0) {
              for(CourseClass courseClass1 : courseclasss) {
                  courseMaxStudent =courseMaxStudent+ courseClass1.getMaxStudent();
              }
          }          
//          trainingCourse.setActualStudent(DataStatus.DISABLED);
          trainingCourse.setMaxStudent(courseMaxStudent);
          trainingCourse.setStartTime(sf.parse(trainingCourseReqDto.getStartTime()).getTime()/1000);
          trainingCourse.setEndTime(sf.parse(trainingCourseReqDto.getEndTime()).getTime()/1000);
          trainingCourse.setLastUpdateTime(new Date().getTime()/1000);
          trainingCourse.setStatus(DataStatus.ENABLED);
          trainingCourseDao.updateByTrainingCourse(trainingCourse);  
          return trainingCourseReqDto;     
            
        }
//        if (null != trainingCourseReqDto.getId()) {  
//            TrainingCourse trainingCourse1 = trainingCourseDao.findById(trainingCourseReqDto.getId());
//            if (null == trainingCourse1) {
//                trainingCourseReqDto.setAddress(String.valueOf(0));
//                return trainingCourseReqDto;//不存在此条记录
//            }
//            if(null != addressId  && null != trainingCourseReqDto.getPrice()) {
//                List<CourseClass> courseClasss = courseClassDao.findByIds(trainingCourseReqDto.getId(), null, addressId);
//                if (null == courseClasss) {
//                    String[] timeDistrictIdArr = timeDistrictIds.split(",");               
//                    for (String timeDistrictId : timeDistrictIdArr) {
//                        CourseClass courseClass = new CourseClass();
//                        List<CourseClass> courseclasss = courseClassDao.findByIds(trainingCourseReqDto.getId(), Integer.valueOf(timeDistrictId), addressId);
//                        if (null != courseclasss && courseclasss.size()>0){
//                            courseClass = courseclasss.get(0);
//                        }
//                        if (null != courseClass && null != courseClass.getId()) {
//                            trainingCourseReqDto.setAddress(String.valueOf(1));
//                            return trainingCourseReqDto;//已有此课程
//                        }
//                        if (null == courseClass.getId()) {
//                            CourseClass courseclass = new CourseClass();
//                            courseclass.setOpenDate(result_form.parse(trainingCourseReqDto.getOpenDate().substring(0, 10)).getTime()/1000);
//                            courseclass.setAddressId(addressId);
//                            courseclass.setTimeDistrictId(Integer.valueOf(timeDistrictId));
//                            courseclass.setCourseId(trainingCourseReqDto.getId());
//                            courseclass.setPrice(BigDecimal.valueOf(Double.valueOf(trainingCourseReqDto.getPrice())));
//                            courseclass.setCreateTime(new Date().getTime()/1000);
//                            courseclass.setActualStudent(DataStatus.DISABLED);
//                            courseclass.setMaxStudent(trainingCourse.getMaxStudent());
//                            courseclass.setStatus(DataStatus.ENABLED);
//                            courseClassDao.insert(courseclass);
////                          TrainingCourseDtoX trainingCourseDtoXs = BeanUtils.createBeanByTarget(trainingCourseDtoX, TrainingCourseDtoX.class);      
//                        }
//                    }
//                }
//                if (null != courseClasss && courseClasss.size()>0) {                   
//                    for (CourseClass courseClass : courseClasss) {
//                        courseClass.setStatus(DataStatus.DISABLED);
//                        courseClass.setUpdateTime(new Date().getTime()/1000);
//                        courseClassDao.updateByPrimaryKey(courseClass);
//                    }               
//                    if (StringUtils.isBlank(timeDistrictIds)) {
//                        return trainingCourseReqDto;
//                        
//                    }
//                    String[] timeDistrictIdArr = timeDistrictIds.split(",");               
//                    for (String timeDistrictId : timeDistrictIdArr) {
//                        CourseClass courseClass = new CourseClass();
//                        List<CourseClass> courseclasss = courseClassDao.findByIds(trainingCourseReqDto.getId(), Integer.valueOf(timeDistrictId), addressId);
//                        
//                        if (null != courseclasss && courseclasss.size()>0){
//                            courseClass = courseclasss.get(0);
//                        }
//                        if (null != courseClass && null != courseClass.getId() && 1 == courseClass.getStatus()) {
//                            trainingCourseReqDto.setAddress(String.valueOf(1));
//                            return trainingCourseReqDto;//已有此课程
//                        }
//                        if (null != courseClass && null != courseClass.getId() && 0 == courseClass.getStatus()) {
//                            courseClass.setStatus(DataStatus.ENABLED);
//                            courseClass.setUpdateTime(new Date().getTime()/1000);
//                            courseClassDao.updateByPrimaryKey(courseClass);
//                        }
//                        if (null == courseClass.getId()) {
//                            CourseClass courseclass = new CourseClass();
//                            courseclass.setOpenDate(result_form.parse(trainingCourseReqDto.getOpenDate().substring(0, 10)).getTime()/1000);
//                            courseclass.setAddressId(addressId);
//                            courseclass.setTimeDistrictId(Integer.valueOf(timeDistrictId));
//                            courseclass.setCourseId(trainingCourseReqDto.getId());
//                            courseclass.setPrice(BigDecimal.valueOf(Double.valueOf(trainingCourseReqDto.getPrice())));
//                            courseclass.setCreateTime(new Date().getTime()/1000);
//                            courseclass.setActualStudent(DataStatus.DISABLED);
//                            courseclass.setMaxStudent(trainingCourse.getMaxStudent());
//                            courseclass.setStatus(DataStatus.ENABLED);
//                            courseClassDao.insert(courseclass);
//    //                      TrainingCourseDtoX trainingCourseDtoXs = BeanUtils.createBeanByTarget(trainingCourseDtoX, TrainingCourseDtoX.class);      
//                        }
//                    }
//                }
//                
//            }
//            List<CourseClass> courseclasss = courseClassDao.findByIds(trainingCourse.getId());
//            Integer courseMaxStudent = 0;
//            for(CourseClass courseClass1 : courseclasss) {
//                courseMaxStudent =courseMaxStudent+ courseClass1.getMaxStudent();
//            }
////            trainingCourse.setActualStudent(DataStatus.DISABLED);
//            trainingCourse.setMaxStudent(courseMaxStudent);
//            trainingCourse.setStartTime(sf.parse(trainingCourseReqDto.getStartTime().substring(0, 8)).getTime()/1000);
//            trainingCourse.setEndTime(sf.parse(trainingCourseReqDto.getEndTime().substring(0, 8)).getTime()/1000);
//            trainingCourse.setStatus(DataStatus.ENABLED);
//            trainingCourseDao.updateByTrainingCourse(trainingCourse);  
//            return trainingCourseReqDto;            
//        }   
        return null;
    }
	
	/**
	 * @author YIn
	 * @time:2015年9月8日 下午3:28:53
	 */
	@Override
	public List<TrainingCourseDto> findAllCourse() {
			List<TrainingCourse> trainingCourseList = trainingCourseDao.findAllCourse();
			List<TrainingCourseDto> dtoList =BeanUtils.createBeanListByTarget(trainingCourseList, TrainingCourseDto.class);
			return dtoList;
	}


	@Override
	public CollegelessonDetailDto getLessonDetail(String courseId, String addressId) throws Exception {
		if (null == courseId || "".equals(courseId.trim())){
			throw new IllegalAddException("对不起,getLessonDetail方法courseId访问参数为空");
		}
		if (null == addressId || "".equals(addressId.trim())){
			throw new IllegalAddException("对不起,getLessonDetail方法addressId访问参数为空");
		}
		
		TrainingCourse courser = trainingCourseDao.findById(courseId);
		
		TrainingAddress addr = trainingAddressDao.getAddrById(addressId);
		
		List<CollegelessonClassDto> classes= courseClassDao.queryLessonClass(courseId, addressId);
		
		CollegelessonDetailDto dto = new CollegelessonDetailDto(courseId, courser.getName(), courser.getCourseTime(), courser.getEquip(),
				courser.getDescription(), courser.getPic(), courser.getVideo(),courser.getMicroPic(), courser.getIsOpened(), addressId, addr.getPlace(),
				addr.getLongtitude(), addr.getLatitude(), addr.getAddress(), addr.getThumbnail(), 
				addr.getDescription(), addr.getFreeTimes(), classes);
		
		initPrice(classes, dto);
		
		return dto;
	}

	private void initPrice(List<CollegelessonClassDto> classes,
			CollegelessonDetailDto dto) {
		if (null != classes && classes.size() > 0){
			BigDecimal min = null;
			BigDecimal max = null;
			BigDecimal temp;
			for (CollegelessonClassDto obj : classes){
				temp = obj.getPrice();
				if (min == null && max == null){
					max = min = temp;	
				}else{
					if (temp.compareTo(min) < 0){
						min = temp;
					}
					if (temp.compareTo(max) > 0){
						max = temp;
					} 
				}
			}
			String price;
			if (min.compareTo(max) == 0){
				price = "￥"+min;
			}else{
				price = "￥"+min+"~"+"￥"+max;
			}
			dto.setPrice(price);
		}
	}


	@Override
	public TrainingCourseDtoX findUserCourceBy(String classId) {
		// TODO Auto-generated method stub
		return trainingCourseDao.findUserCourceBy(classId);
	} 
}