package com.tianfang.train.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.digest.MD5Coder;
import com.tianfang.common.exception.SystemException;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.DateUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.common.util.UUIDGenerator;
import com.tianfang.train.dao.AssistantDao;
import com.tianfang.train.dao.CourseClassDao;
import com.tianfang.train.dao.TrainingAddressDao;
import com.tianfang.train.dao.TrainingAssistantAddressTimeDao;
import com.tianfang.train.dao.TrainingTimeDistrictDao;
import com.tianfang.train.dto.AddAssistantReqDto;
import com.tianfang.train.dto.AssistantDto;
import com.tianfang.train.dto.AssistantSpaceTimeRespDto;
import com.tianfang.train.dto.LocaleClass;
import com.tianfang.train.dto.LocaleIndexDto;
import com.tianfang.train.dto.TrainingAddressDto;
import com.tianfang.train.pojo.TrainingAssistant;
import com.tianfang.train.pojo.TrainingAssistantAddressTime;
import com.tianfang.train.service.IAssistantService;

@Service
public class AssistantServiceImpl implements IAssistantService {
	private Logger log = Logger.getLogger(getClass());

	@Autowired
	private AssistantDao assistantDao;
	@Autowired
	private TrainingAddressDao addressDao;
	@Autowired
	private CourseClassDao courseClassDao;
	@Autowired
    private TrainingTimeDistrictDao trainingTimeDistrictDao;    
	@Autowired
	private TrainingAssistantAddressTimeDao trainingAssistantAddressTimeDao;
	
	
	@Override
	public AssistantDto checkAccount(String loginName, String loginPassword) {
		TrainingAssistant assistant = null;
		if (!StringUtils.isEmpty(loginName)
				&& !StringUtils.isEmpty(loginPassword)) {
			try {
				loginPassword = MD5Coder.encodeMD5Hex(loginPassword);
			} catch (Exception e) {
				throw new SystemException(e.getMessage(), e);
			}
			assistant = assistantDao.findByAccountAndPassword(loginName, loginPassword);
		}
		AssistantDto dto = changToDto(assistant);
		
		return dto;
	}

	private AssistantDto changToDto(TrainingAssistant assistant) {
		if (assistant == null) {
			return null;
		}
		AssistantDto dto = new AssistantDto();
		BeanUtils.copyProperties(assistant, dto);
		return dto;
	}

	@Override
	public List<LocaleIndexDto> queryAddressByAssistantId(String assistantId) {
		if (StringUtils.isEmpty(assistantId)){
			log.error("对不起,负责人id为空!");
			throw new RuntimeException("对不起,负责人id为空!");
		}
		List<LocaleClass> courses = courseClassDao.findCourseClassByAssistantId(assistantId);	
		
		if (null != courses && courses.size() > 0){
			
			Map<String, List<LocaleClass>> maps = new HashMap<String, List<LocaleClass>>();
			List<LocaleClass> list;
			for (LocaleClass clas : courses){
				if (maps.containsKey(clas.getAddressId())){
					maps.get(clas.getAddressId()).add(clas);
				}else{
					list = new ArrayList<LocaleClass>();
					list.add(clas);
					maps.put(clas.getAddressId(), list);
				}
			}
			List<LocaleIndexDto> dtos = new ArrayList<LocaleIndexDto>();
			
			Iterator<Entry<String, List<LocaleClass>>> it = maps.entrySet().iterator();
			LocaleIndexDto dto;
			while (it.hasNext()){
				Entry<String, List<LocaleClass>> entry = it.next();
				dto = new LocaleIndexDto();
				dto.setPlace(entry.getValue().get(0).getPlace());
				dto.setCourses(entry.getValue());
				dtos.add(dto);
			}
			
			return dtos;
		}
		return null;
	}
	
	public PageResult<AssistantDto> findPage(AssistantDto assistantDto,PageQuery page){
	    TrainingAddressDto addressDto = new TrainingAddressDto();
	    addressDto.setPlace(assistantDto.getAddress());
	    if (StringUtils.isBlank(assistantDto.getAccount())) {
	        assistantDto.setAccount(null);
	    }
	    if (StringUtils.isBlank(assistantDto.getAddress())) {
	        assistantDto.setAddress(null);
	    }
	    if (StringUtils.isBlank(assistantDto.getName())) {
	        assistantDto.setName(null);
	    }
//	    List<TrainingAddress> trainingAddressdto = addressDao.find(addressDto);
	    List<AssistantDto> results = assistantDao.findPage(assistantDto, page);
	    for (AssistantDto assistantdto : results) {
	        StringBuffer sb = new StringBuffer();
	        List<TrainingAssistantAddressTime> trainingassistantAddressTimes = trainingAssistantAddressTimeDao.findByAssistantId(assistantdto.getId());
	        List<String> list=new ArrayList<String>();  
	        for (TrainingAssistantAddressTime trainingAssistantAddressTime : trainingassistantAddressTimes) {
	            list.add(trainingAssistantAddressTime.getAddressId());	            
	        }
	        List<String> tempList= new ArrayList<String>();  
            for(String i:list){  
                if(!tempList.contains(i)){  
                    tempList.add(i);  
                }  
            }  
            if (tempList.size()>0) {
                for (int i = 0,len = tempList.size(); i < len; i++) {
                    TrainingAddressDto trainingAddressDto = addressDao.findById(tempList.get(i));
                    sb.append(trainingAddressDto.getPlace());
                    if (i < len-1){
                    	sb.append(";");
                    }
                }   
            }
            assistantdto.setCreateTimeStr(DateUtils.format(new Date(assistantdto.getCreatetime() * 1000), DateUtils.YMD_DASH));       
	        assistantdto.setPlacestr(sb.toString());
	    }
//	    if (null != results && results.size()>0) {
//	        page.setTotal(results.size());
//	    }
//	    else {
//	        page.setTotal(0);
//	    }
	    page.setTotal(assistantDao.findAssistantByPageCount(assistantDto));
	    
	    return new PageResult<AssistantDto>(page,results);
	}
	
	public Object save(AddAssistantReqDto assistantDto, String jsonClasss){            
        TrainingAssistant trainingassistant = assistantDao.findByAccountAndPassword(assistantDto.getAccount(),null);
        if (null != trainingassistant) {
            return 0;//用户名已存在
        }
        List<TrainingAssistantAddressTime> trainingAssistantAddressTimes = new Gson().fromJson(jsonClasss, new TypeToken<List<TrainingAssistantAddressTime>>(){}.getType()); 
//        List<TrainingAssistantAddressTime> trainingAssistantAddressTimes= BeanUtils.createBeanListByTarget(jsonClasssToObject, TrainingAssistantAddressTime.class);
        TrainingAssistant trainingAssistant = BeanUtils.createBeanByTarget(assistantDto, TrainingAssistant.class);
        trainingAssistant.setId(UUIDGenerator.getUUID());
        trainingAssistant.setCreateTime(new Date().getTime()/1000);
        trainingAssistant.setStatus(DataStatus.ENABLED);
        assistantDao.save(trainingAssistant);
        for (TrainingAssistantAddressTime trainingAssistantAddressTime : trainingAssistantAddressTimes) {
            trainingAssistantAddressTime.setAssistantId(trainingAssistant.getId());
            trainingAssistantAddressTimeDao.insert(trainingAssistantAddressTime);
        }
        return 1;
    }
	
	public Object edit(AddAssistantReqDto assistantDto, String jsonClasss) {
        TrainingAssistant trainingassistant = assistantDao.findById(assistantDto.getId());
        if (null == trainingassistant) {
            return 0;//此负责人不存在
        }
        List<TrainingAssistantAddressTime> trainingAssistantAddressTimes = new Gson().fromJson(jsonClasss, new TypeToken<List<TrainingAssistantAddressTime>>(){}.getType()); 
        TrainingAssistant trainingAssistant = BeanUtils.createBeanByTarget(assistantDto, TrainingAssistant.class);
        List<TrainingAssistantAddressTime> trainingassistantAddresstimes = trainingAssistantAddressTimeDao.findByAssistantId(assistantDto.getId());
        for (TrainingAssistantAddressTime trainingAssistantAddressTime : trainingassistantAddresstimes) {
            trainingAssistantAddressTime.setStatus(DataStatus.DISABLED);
            trainingAssistantAddressTimeDao.updateByPrimaryKey(trainingAssistantAddressTime);
        }
        for (TrainingAssistantAddressTime trainingAssistantAddressTime : trainingAssistantAddressTimes) {
            TrainingAssistantAddressTime trainingAssistantAddresstime = trainingAssistantAddressTimeDao.findByAccountAll(trainingAssistantAddressTime.getAddressDistrictTimeId(), trainingAssistant.getId());
            if (null != trainingAssistantAddresstime) {
                trainingAssistantAddresstime.setStatus(DataStatus.ENABLED);
                trainingAssistantAddressTimeDao.updateByPrimaryKey(trainingAssistantAddresstime);
            }
            if (null == trainingAssistantAddresstime) {
                trainingAssistantAddressTime.setAssistantId(trainingAssistant.getId());
                trainingAssistantAddressTimeDao.insert(trainingAssistantAddressTime);
            }
        }
        return 1;
        
    }
	
	
	@SuppressWarnings({"unused" })
    public Object save(AddAssistantReqDto assistantDto,String traTimeAddresss, String addressId){	    
	    AssistantDto assistantdto =  assistantDao.findByAssistantDto(assistantDto);
	    TrainingAssistantAddressTime trainingAssistantAddressTime = new TrainingAssistantAddressTime();
	    if (null == assistantdto) {
	        TrainingAssistant trainingassistant = assistantDao.findByAccountAndPassword(assistantDto.getAccount(),null);
	        if (null != trainingassistant) {
	            return 0;//用户名已存在
	        }else {	            
	            TrainingAssistant trainingAssistant = new TrainingAssistant();
	            trainingAssistant.setAccount(assistantDto.getAccount());
	            trainingAssistant.setPassword(assistantDto.getPassword());
	            trainingAssistant.setName(assistantDto.getName());
	            trainingAssistant.setCreateTime(new Date().getTime());
	            trainingAssistant.setStatus(DataStatus.ENABLED);
	            assistantDao.insert(trainingAssistant);	            
	            if (StringUtils.isNotBlank(traTimeAddresss)) {
	                AssistantDto assistantdtos =  assistantDao.findByAssistantDto(assistantDto);
	                String[] idArr = traTimeAddresss.split(",");
	                for (String id : idArr) {
//	                    TrainingAssistantAddressTime trainingassistantAddressTime = trainingAssistantAddressTimeDao.findByAccount(addressDistrictTimeId, assistantId)
	                    trainingAssistantAddressTime.setAddressDistrictTimeId(id);
	                    trainingAssistantAddressTime.setAssistantId(assistantdtos.getId());
	                    trainingAssistantAddressTime.setAddressId(addressId);
	                    trainingAssistantAddressTime.setCreateTime(new Date().getTime());
	                    trainingAssistantAddressTime.setStatus(DataStatus.ENABLED);
	                    trainingAssistantAddressTimeDao.insert(trainingAssistantAddressTime);
	                }
	                return 1;//新增成功	                
	            }	
	            return 1;//新增成功
	        }
	    }
	    else {
	        if (StringUtils.isNotBlank(traTimeAddresss)) {
                String[] idArr = traTimeAddresss.split(",");
                for (String id : idArr) {
                    TrainingAssistantAddressTime trainingassistantAddresstime = trainingAssistantAddressTimeDao.findByAccount(id, assistantdto.getId());
                    if (null == trainingassistantAddresstime) {
                        TrainingAssistantAddressTime trainingassistantAddressTime = new TrainingAssistantAddressTime();
                        trainingAssistantAddressTime.setAddressDistrictTimeId(id);
                        trainingAssistantAddressTime.setAssistantId(assistantdto.getId());
                        trainingAssistantAddressTime.setAddressId(addressId);
                        trainingAssistantAddressTime.setCreateTime(new Date().getTime());
                        trainingAssistantAddressTime.setStatus(DataStatus.ENABLED);
                        trainingAssistantAddressTimeDao.insert(trainingAssistantAddressTime);
                    } else{
                        return 2;//不能重复提交
                    }
                }
                return 1;//新增成功                 
            }   
            return 1;//新增成功
	    }
	}
	
	 /** 
     * (non-Javadoc)   
     * @see com.tianfang.train.service.IAssistantService#edit(com.tianfang.train.dto.AddAssistantReqDto, java.lang.String)   
     */
    @Override
    public Object edit(AssistantDto assistantDto, String traTimeAddresss, String addressId)
    {
        // TODO 添加方法注释
        TrainingAssistant trainingAssistant =  assistantDao.findById(assistantDto.getId());
        if (null == trainingAssistant) {
            return 0;// 用户不存在
        }
        if(null == addressId) {
            trainingAssistant.setAccount(assistantDto.getAccount());
            trainingAssistant.setName(assistantDto.getName());
            if (StringUtils.isNotBlank(assistantDto.getPassword())) {
                trainingAssistant.setPassword(assistantDto.getPassword());
            }
            assistantDao.updateByPrimaryKey(trainingAssistant);
            return 1;
        }       
        List<TrainingAssistantAddressTime> trainingassistantAddresstimes = trainingAssistantAddressTimeDao.findByAddAssId(addressId, assistantDto.getId());//把此场地的ID所有记录设置0
        if (null != trainingassistantAddresstimes && trainingassistantAddresstimes.size()>0) {
            for (TrainingAssistantAddressTime trainingAssistantAddressTime:trainingassistantAddresstimes) {
                trainingAssistantAddressTime.setStatus(DataStatus.DISABLED);
                trainingAssistantAddressTime.setUpdateTime(new Date().getTime());
                trainingAssistantAddressTimeDao.updateByPrimaryKey(trainingAssistantAddressTime);
            }
        }
        
        if (StringUtils.isNotBlank(traTimeAddresss)) {        
            String[] idArr = traTimeAddresss.split(",");
            for (String id : idArr) {            
                TrainingAssistantAddressTime trainingassistantAddresstime = trainingAssistantAddressTimeDao.findByAccountAll(id, assistantDto.getId());//查询是否之前保存过记录，若保存过该状态，没有则添加insert
                if (null == trainingassistantAddresstime) {
                    TrainingAssistantAddressTime trainingassistantaddresstime = new TrainingAssistantAddressTime();
                    trainingassistantaddresstime.setAddressDistrictTimeId(id);
                    trainingassistantaddresstime.setAddressId(addressId);
                    trainingassistantaddresstime.setAssistantId(assistantDto.getId());
                    trainingassistantaddresstime.setCreateTime(new Date().getTime());
                    trainingassistantaddresstime.setStatus(DataStatus.ENABLED);
                    trainingAssistantAddressTimeDao.insert(trainingassistantaddresstime);                
                }
                if (null != trainingassistantAddresstime){
                    trainingassistantAddresstime.setStatus(DataStatus.ENABLED);
                    trainingassistantAddresstime.setUpdateTime(new Date().getTime());
                    trainingAssistantAddressTimeDao.updateByPrimaryKey(trainingassistantAddresstime);
                }
            }
        }
        return 1;
    }
	
	public Object delAsIds(String  delAsIds) {
	    String[] idArr = delAsIds.split(",");
        for (String id : idArr) {
            TrainingAssistant trainingassistant = assistantDao.findById(id);
            if (null == trainingassistant) {
                return 0;//无此条记录
            }
            trainingassistant.setUpdateTime(new Date().getTime());
            trainingassistant.setStatus(DataStatus.DISABLED);
            assistantDao.updateByPrimaryKey(trainingassistant);
            List<TrainingAssistantAddressTime> trainingassistantAddresstimes= trainingAssistantAddressTimeDao.findByAssistantId(id);
            for (TrainingAssistantAddressTime trainingAssistantAddressTime : trainingassistantAddresstimes) {
                trainingAssistantAddressTime.setUpdateTime(new Date().getTime());
                trainingAssistantAddressTime.setStatus(DataStatus.DISABLED);
                trainingAssistantAddressTimeDao.updateByPrimaryKey(trainingAssistantAddressTime);
                
            }
        }
        return 1;
	}
	
	public AssistantDto getAssistant(String id) {
	    AssistantDto assistantDto = BeanUtils.createBeanByTarget(assistantDao.findById(id), AssistantDto.class);
	    return assistantDto;
	}   
    
	public List<AssistantSpaceTimeRespDto> findAssistantAddressTimeById(String id){
        return assistantDao.findAssistantAddressTimeById(id);
    }
}