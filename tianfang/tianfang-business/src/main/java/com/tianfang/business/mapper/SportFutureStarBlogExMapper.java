package com.tianfang.business.mapper;

public interface SportFutureStarBlogExMapper {
	
	/**
	 * 教练博文阅读量+1
	 * @param id
	 * @author wangxiang
	 * 2015年11月30日下午5:50:21
	 */
	void updateRead(String id);

	/**
	 * 教练博文分享量+1
	 * @param id
	 * @author wangxiang
	 * 2015年11月30日下午5:50:23
	 */
	void updateShare(String id);

	/**
	 * 教练博文点赞量+1
	 * @param id
	 * @author wangxiang
	 * 2015年11月30日下午5:50:26
	 */
	void updateLike(String id);
}