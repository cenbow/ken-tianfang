package com.tianfang.business.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tianfang.business.dto.AlbumPictureDto;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
/**
 * 
 * @author YIn
 * @time:2015年11月14日 上午11:11:42
 * @ClassName: IAlbumService
 * @Description: TODO
 * @
 */
@Service("iAlbumPicService")
public interface IAlbumPicService {
	
	/**
	 * 球队首页相册轮播显示
	 * @author YIn
	 * @param albumPictureDto 
	 * @time:2015年11月14日 下午3:23:37
	 * @return
	 */
	//List<AlbumPictureDto> findIndexAlbum(AlbumPictureDto albumPictureDto);
	
	/**
	 * 球队相册列表分页显示
	 * @author YIn
	 * @time:2015年11月13日 下午5:53:30
	 */
	PageResult<AlbumPictureDto> findTeamAlbumPic(AlbumPictureDto albumPictureDto, PageQuery page);
	
	/**
	 * 添加球队相册
	 * @author YIn
	 * @time:2015年11月14日 下午5:11:00
	 * @param albumDto
	 * @return
	 */
	int addAlbumPic(AlbumPictureDto albumPictureDto);

	/**
	 * 修改球队相册
	 * @author YIn
	 * @time:2015年11月14日 下午5:11:00
	 * @param albumDto
	 * @return
	 */
	int updateAlbumPic(AlbumPictureDto albumPictureDto);

	/**
	 * 删除球队相册
	 * @author YIn
	 * @time:2015年11月14日 下午5:11:00
	 * @param albumDto
	 * @return
	 */
	int delAlbumPic(String id);
	
	/**
	 * 批量删除
	 * @author YIn
	 * @time:2015年11月16日 下午3:16:03
	 * @param ids
	 * @return
	 */
	Integer delByIds(String ids);

	/**
	 * 根据id查询相册
	 * @author YIn
	 * @time:2015年11月14日 下午5:17:13
	 * @param teamId
	 * @return
	 */
	AlbumPictureDto selectAlbumPicById(String teamId);
	/**
	 * 
		 * 此方法描述的是：list 相片查询
		 * @author: cwftalus@163.com
		 * @version: 2015年11月26日 上午9:10:59
	 */
	List<AlbumPictureDto> findTeamAlbumPic(AlbumPictureDto albumPictureDto);
	/**
	 * 
		 * 此方法描述的是：相片分页
		 * @author: cwftalus@163.com
		 * @version: 2015年11月28日 下午2:45:32
	 */
	PageResult<AlbumPictureDto> findTeamAlbumPicByPage(AlbumPictureDto albumPictureDto, PageQuery page);
	
	/**
	 * 
		 * 此方法描述的是：保存相册对应的图片信息
		 * @author: cwftalus@163.com
		 * @version: 2015年11月30日 上午9:49:11
	 */
	void insertPictures(AlbumPictureDto albumPicDto);
	
	/**
	 * 
		 * 此方法描述的是：
		 * @author: cwftalus@163.com
		 * @version: 2015年12月11日 下午3:36:58
	 */
	void updateAlbumPicList(String albumId);

	List<AlbumPictureDto> findTeamAlbumPicByRand(AlbumPictureDto albumPictureDto);

	AlbumPictureDto getAlbumPicByDto(AlbumPictureDto albumPicDto);
	
}
