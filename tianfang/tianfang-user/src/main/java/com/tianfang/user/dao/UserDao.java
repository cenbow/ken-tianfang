package com.tianfang.user.dao;

import java.util.Date;
import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.user.dto.SportUserReqDto;
import com.tianfang.user.dto.TrainerLevel;
import com.tianfang.user.dto.UserType;
import com.tianfang.user.mapper.SportUserExMapper;
import com.tianfang.user.mapper.SportUserMapper;
import com.tianfang.user.pojo.SportUser;
import com.tianfang.user.pojo.SportUserExample;

/**
 * 前端用户DAO
 * @author YIn
 * @time:2015年11月19日 下午4:44:00
 * @ClassName: UserDao
 * @Description: TODO
 * @
 */
@Repository
public class UserDao  extends MyBatisBaseDao<SportUser>
{
	@Autowired
    @Getter
    private SportUserMapper mapper;
	
	@Autowired
    @Getter
    private SportUserExMapper mapperEx;
	
	/**
	 * @author YIn
	 * @time:2015年11月19日 下午4:58:07
	 */
	public int checkRepeat(SportUser sportUser) { 
		return mapperEx.checkRepeat(sportUser);
	}

	/**
	 * @author YIn
	 * @time:2015年11月19日 下午7:04:51
	 */
	public SportUser findByNameAndPassword(SportUserReqDto sportUserReqDto) {
		return mapperEx.findByNameAndPassword(sportUserReqDto);
	}

	/**
	 * @author YIn
	 * @time:2015年11月22日 上午11:50:15
	 */
	public int updateByObj(SportUser user) {
		user.setLastUpdateTime(new Date());    //修改更新时间
		user.setStat(DataStatus.ENABLED);
		SportUserExample example = new SportUserExample();
	    SportUserExample.Criteria criteria = example.createCriteria();
	    criteria.andIdEqualTo(user.getId());
	    return mapper.updateByExampleSelective(user, example);
	}

	/**
	 * 查询所有教练信息
	 * @return
	 * @author xiang_wang
	 * 2015年11月30日下午1:36:56
	 */
	public List<SportUser> findAllCoach() {
		SportUserExample example = new SportUserExample();
	    SportUserExample.Criteria criteria = example.createCriteria();
	    criteria.andUtypeEqualTo(UserType.COACH.getIndex());
	    criteria.andVisibleStatEqualTo(DataStatus.ENABLED);
	    criteria.andStatEqualTo(DataStatus.ENABLED);
	    example.setOrderByClause(" trainer_level asc, create_time asc");
	    List<SportUser> list = mapper.selectByExample(example);
		return list;
	}
	
	/**
	 * 查询所有顶级教练信息
	 * @return
	 * @author xiang_wang
	 * 2015年11月30日下午1:37:07
	 */
	public List<SportUser> findTopCoach() {
		SportUserExample example = new SportUserExample();
	    SportUserExample.Criteria criteria = example.createCriteria();
	    criteria.andUtypeEqualTo(UserType.COACH.getIndex());
	    criteria.andVisibleStatEqualTo(DataStatus.ENABLED);
	    criteria.andTrainerLevelEqualTo(TrainerLevel.TOP.getIndex());
	    criteria.andStatEqualTo(DataStatus.ENABLED);
	    example.setOrderByClause(" create_time asc");
	    List<SportUser> list = mapper.selectByExample(example);
		return list;
	}

	public List<SportUser> findCoachByParams(SportUserReqDto params) {
		SportUserExample example = new SportUserExample();
	    SportUserExample.Criteria criteria = example.createCriteria();
	    criteria.andUtypeEqualTo(UserType.COACH.getIndex());//用户教练类型
	    if(params.getLecturer()!=null){
	    	criteria.andLecturerEqualTo(params.getLecturer());//是否培训教练主讲人	
	    }
	    criteria.andVisibleStatEqualTo(DataStatus.ENABLED);//是否审核通过
	    criteria.andStatEqualTo(DataStatus.ENABLED);//是否有效数据
	    example.setOrderByClause(" create_time asc");
	    List<SportUser> list = mapper.selectByExample(example);
		return list;
	}
}