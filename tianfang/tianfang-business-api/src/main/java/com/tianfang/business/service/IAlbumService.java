package com.tianfang.business.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tianfang.business.dto.AlbumDto;
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
@Service("iAlbumService")
public interface IAlbumService {
	
	/**
	 * 球队首页相片轮播显示
	 * @author YIn
	 * @param albumPictureDto 
	 * @time:2015年11月14日 下午3:23:37
	 * @return
	 */
	//List<AlbumPictureDto> findIndexAlbum(AlbumPictureDto albumPictureDto);
	
	/**
	 * 球队相片列表分页显示
	 * @author YIn
	 * @time:2015年11月13日 下午5:53:30
	 */
	PageResult<AlbumDto> findTeamAlbum(AlbumDto albumDto, PageQuery page);
	
	/**
	 * 查询所有相册，无分页
	 * @author YIn
	 * @time:2015年11月18日 下午5:31:48
	 * @return
	 */
	List<AlbumDto> findAlbumList();
	
	/**
	 * 添加球队相片
	 * @author YIn
	 * @time:2015年11月14日 下午5:11:00
	 * @param albumDto
	 * @return
	 */
	int addAlbum(AlbumDto albumDto);

	/**
	 * 修改球队相片
	 * @author YIn
	 * @time:2015年11月14日 下午5:11:00
	 * @param albumDto
	 * @return
	 */
	int updateAlbum(AlbumDto albumDto);

	/**
	 * 删除球队相片
	 * @author YIn
	 * @time:2015年11月14日 下午5:11:00
	 * @param albumDto
	 * @return
	 */
	int delAlbum(String id);

	/**
	 * 根据id查询相片
	 * @author YIn
	 * @time:2015年11月14日 下午5:17:13
	 * @param albumId
	 * @return
	 */
	AlbumDto getAlbumById(String albumId);
	
	/**
	 * 批量删除
	 * @author YIn
	 * @time:2015年11月16日 下午3:16:03
	 * @param ids
	 * @return
	 */
	Integer delByIds(String ids);
	
	/**
	 * 
	 * findPage：获取相册list
	 * @param albumDto
	 * @param page
	 * @return
	 * @exception	
	 * @author Administrator
	 * @date 2015年11月23日 上午11:25:54
	 */
	public PageResult<AlbumDto> findPage (AlbumDto albumDto, PageQuery page); 
	
	public List<AlbumPictureDto> findByAlbumId(String AlbumId);
	
	/**
	 * 
		 * 此方法描述的是：根据条件查询相册 无分页
		 * @author: cwftalus@163.com
		 * @version: 2015年11月27日 下午4:58:24
	 */
	List<AlbumDto> findAlbumList(AlbumDto albumDto);

	List<AlbumDto> findTeamAlbumByRand(AlbumDto albumDto);
}
