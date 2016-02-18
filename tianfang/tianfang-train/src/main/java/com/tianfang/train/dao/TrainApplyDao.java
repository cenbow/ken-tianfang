package com.tianfang.train.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.StringUtils;
import com.tianfang.common.util.UUIDGenerator;
import com.tianfang.train.dto.TrainApplyDto;
import com.tianfang.train.mapper.UserCourseExMapper;
import com.tianfang.train.mapper.UserCourseMapper;
import com.tianfang.train.pojo.UserCourse;
import com.tianfang.train.pojo.UserCourseExample;
import com.tianfang.train.pojo.UserCourseExample.Criteria;

@Repository
public class TrainApplyDao extends MyBatisBaseDao<UserCourse>{
	
    @Autowired
    @Getter
    private UserCourseMapper mapper;
    
    @Autowired
    @Getter
    private UserCourseExMapper mapperEx;
    
	/**
	 * @author YIn
	 * @time:2015年9月1日 下午8:13:04
	 */
	public List<UserCourse> findByPage(TrainApplyDto trainApplyDto,
			PageQuery page) {
			UserCourseExample example = new UserCourseExample();
			Criteria criteria = example.createCriteria();
			criteria.andStatusEqualTo(DataStatus.ENABLED);
			if(StringUtils.isNotEmpty(trainApplyDto.getPname())){
			criteria.andPnameLike("%"+trainApplyDto.getPname()+"%");
			}
			if(StringUtils.isNotEmpty(trainApplyDto.getMobile())){
			criteria.andMobileLike("%"+trainApplyDto.getMobile()+"%");
			}
			if(StringUtils.isNotEmpty(trainApplyDto.getCourseName())){
			criteria.andCourseNameLike("%"+trainApplyDto.getCourseName()+"%");
			}
			if(StringUtils.isNotEmpty(trainApplyDto.getCname())){
			criteria.andCnameLike("%"+trainApplyDto.getCname()+"%");
			}
			if(StringUtils.isNotEmpty(trainApplyDto.getSpaceName())){
			criteria.andSpaceNameLike("%"+trainApplyDto.getSpaceName()+"%");
			}
			if(trainApplyDto.getIsPayed()!=null){
			criteria.andIsPayedEqualTo(trainApplyDto.getIsPayed());
			}
			if(trainApplyDto.getDepositIspayed()!=null){
			criteria.andDepositIspayedEqualTo(trainApplyDto.getDepositIspayed());
			}
			if(StringUtils.isNotEmpty(trainApplyDto.getStartTime()) && StringUtils.isNotEmpty(trainApplyDto.getEndTime())){
				SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
				Date start = null;
				try {
					start = sdf.parse(trainApplyDto.getStartTime());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				Date end = null;
				try {
					end = sdf.parse(trainApplyDto.getEndTime());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				criteria.andApplyTimeBetween(start.getTime()/1000, end.getTime()/1000); 
			}       //报名日期时间    
			
			example.setOrderByClause(" create_time desc limit "+page.getStartNum() +"," + page.getPageSize());
			return mapper.selectByExample(example);
	}
	
	/**
	 * @author YIn
	 * @time:2015年9月1日 下午8:13:20
	 */
	public long count(TrainApplyDto trainApplyDto) {
			UserCourseExample example = new UserCourseExample();
			Criteria criteria = example.createCriteria();
			criteria.andStatusEqualTo(DataStatus.ENABLED);
			if(StringUtils.isNotEmpty(trainApplyDto.getPname())){
			criteria.andPnameLike("%"+trainApplyDto.getPname()+"%");
			}
			if(StringUtils.isNotEmpty(trainApplyDto.getMobile())){
			criteria.andMobileLike("%"+trainApplyDto.getMobile()+"%");
			}
			if(StringUtils.isNotEmpty(trainApplyDto.getCourseName())){
			criteria.andCourseNameLike("%"+trainApplyDto.getCourseName()+"%");
			}
			if(StringUtils.isNotEmpty(trainApplyDto.getCname())){
			criteria.andCnameLike("%"+trainApplyDto.getCname()+"%");
			}
			if(StringUtils.isNotEmpty(trainApplyDto.getSpaceName())){
			criteria.andSpaceNameLike("%"+trainApplyDto.getSpaceName()+"%");
			}
			if(trainApplyDto.getIsPayed()!=null){
			criteria.andIsPayedEqualTo(trainApplyDto.getIsPayed());
			}
			if(trainApplyDto.getDepositIspayed()!=null){
			criteria.andDepositIspayedEqualTo(trainApplyDto.getDepositIspayed());
			}
			if(StringUtils.isNotEmpty(trainApplyDto.getStartTime()) && StringUtils.isNotEmpty(trainApplyDto.getEndTime())){
				SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
				Date start = null;
				try {
					start = sdf.parse(trainApplyDto.getStartTime());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				Date end = null;
				try {
					end = sdf.parse(trainApplyDto.getEndTime());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				criteria.andApplyTimeBetween(start.getTime()/1000, end.getTime()/1000); 
			}       //报名日期时间    
			return mapper.countByExample(example);
	}
	
	/**
	 * @author YIn
	 * @time:2015年9月1日 下午8:13:04
	 */
	public List<UserCourse> findApplyByPage(TrainApplyDto trainApplyDto,
			PageQuery page) {
			if(StringUtils.isNotEmpty(trainApplyDto.getStartTime())){
				SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
				Date start = null;
				try {
					start = sdf.parse(trainApplyDto.getStartTime());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				trainApplyDto.setStartTimeLong(start.getTime()/1000);
			}       
			if(StringUtils.isNotEmpty(trainApplyDto.getEndTime())){
				SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
				Date end = null;
				try {
					end = sdf.parse(trainApplyDto.getEndTime());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				trainApplyDto.setEndTimeLong(end.getTime()/1000);
			}
			return mapperEx.findApplyByPage(trainApplyDto ,page);
	}
	
	/**
	 * @author YIn
	 * @time:2015年9月1日 下午8:13:20
	 */
	public long countApply(TrainApplyDto trainApplyDto) {
		if(StringUtils.isNotEmpty(trainApplyDto.getStartTime())){
			SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
			Date start = null;
			try {
				start = sdf.parse(trainApplyDto.getStartTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			trainApplyDto.setStartTimeLong(start.getTime()/1000);
		}       
		if(StringUtils.isNotEmpty(trainApplyDto.getEndTime())){
			SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
			Date end = null;
			try {
				end = sdf.parse(trainApplyDto.getEndTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			trainApplyDto.setEndTimeLong(end.getTime()/1000);
		}
		return mapperEx.countApply(trainApplyDto);
	}
	
	
	public static String CreateDate(){
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sf.format(new Date());
	}
	
	
	/**
	 * 生成订单后五位
	 * @return
	 */
	private  String CreateNo(){
		int n=0;
		n=(int)(Math.random()*100000);
		while(n<10000 || !handle(n)){
			n=(int)(Math.random()*100000);
		}
		return n+"";
	}
	
	private boolean handle(int n){
		int[] list=new int[5];
		for(int i=0;i<5;i++){
			list[i]=n%10;
			n=n/10;
		}
		for(int i=0;i<5;i++){
			for(int j=i+1;j<5;j++){
				if(list[i]==list[j]) return false;
			}
		}
		return true;
	}
	
	/**
	 * 生成订单号
	 * @return
	 */
	private  String CreateOrderNo(){
		return CreateDate()+CreateNo();
	}
	
	/**
	 * @author YIn
	 * @return 
	 * @time:2015年9月5日 下午2:28:59
	 */
	public UserCourse save(UserCourse userCouser) {
		  userCouser.setId(UUIDGenerator.getUUID());
		  userCouser.setStatus(DataStatus.ENABLED);
		  userCouser.setOrderNo(CreateOrderNo());
		  userCouser.setCreateTime(new Date().getTime()/1000);
		  int flag = mapper.insert(userCouser); 
		  if(flag>0){
		  return userCouser;
		  }else{
			  return null;
		  }
	}
	
	/**
	 * @author YIn
	 * @time:2015年9月5日 下午6:06:53
	 */
	    public UserCourse findById(String id) {
	    	UserCourseExample example = new UserCourseExample();
	    	UserCourseExample.Criteria criteria = example.createCriteria();
	        if (null != id) {
	            criteria.andIdEqualTo(id);
	        }
	        criteria.andStatusEqualTo(DataStatus.ENABLED);
	        List<UserCourse> results = mapper.selectByExample(example); 
	        return CollectionUtils.isEmpty(results) ? null :results.get(0);
	    
	}

	/**
	 * @author YIn
	 * @time:2015年9月7日 下午8:13:58
	 */
	public int  update(UserCourse userCouser) {
		 userCouser.setUpdateTime(new Date().getTime()/1000);
		 return  mapper.updateByPrimaryKeySelective(userCouser);
		
	}


	}
