package com.tianfang.home.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.business.dto.SportHomeDto;
import com.tianfang.business.service.SportHomeService;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.model.Response;
import com.tianfang.common.util.StringUtils;
import com.tianfang.order.dto.SportMProductSkuDto;
import com.tianfang.order.dto.SportMProductSpecDto;
import com.tianfang.order.dto.SportMProductSpecValuesDto;
import com.tianfang.order.dto.SportMProductSpuDto;
import com.tianfang.order.dto.SportMShoppingCartDto;
import com.tianfang.order.dto.SportMSpecValuesDto;
import com.tianfang.order.dto.SportMTypeDto;
import com.tianfang.order.service.ISportMCategoryService;
import com.tianfang.order.service.ISportMProductSpuService;
import com.tianfang.order.service.ISportMShppingCardService;
import com.tianfang.order.service.ISportMTypeService;

/**
 * 商品信息 页面
 * @author mr
 *
 */
@Controller
@RequestMapping("/m/Product")
public class MProductHomeController extends BaseController {
	@Autowired
	private ISportMTypeService iSportMTypeService;
	@Autowired
	private SportHomeService homeServie;
	@Autowired
	private ISportMCategoryService categoryService;
	@Autowired
	private ISportMProductSpuService spuService;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private ISportMShppingCardService iSportMShppingCar;
	
	/**
	 * 商品首页显示数据
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/homeInfo")
	public ModelAndView getMHomeInfo(SportMProductSpuDto  spuDto,SportHomeDto sportHome,ExtPageQuery page){
		ModelAndView mv = this.getModelAndView();
		//获取首页轮播图
		sportHome.setType(10);					// 10  商品页面轮播图效果
		sportHome.setPicMarked(sportHome.getType());   
		List<SportHomeDto> homePic_list = homeServie.selcetByMarked(sportHome);
		
		String keyCode = "SportMTypeDto";
		List<SportMTypeDto> type_list =new ArrayList<SportMTypeDto>();
		if(redisTemplate.opsForValue().get(keyCode)!=null){
			type_list =  (List<SportMTypeDto>) redisTemplate.opsForValue().get(keyCode);
		}else{
			type_list = iSportMTypeService.selectMTypeAll();
			redisTemplate.opsForValue().set(keyCode, type_list);
		}
		page.setLimit(8);
		//获取spu商品信息
		PageResult<SportMProductSpuDto> spu_list = spuService.selectPageByCriteria(spuDto,page.changeToPageQuery());
		try {
			mv.addObject("picList",homePic_list);
			mv.addObject("typelist",type_list);
			mv.addObject("dataList",spu_list);
			mv.addObject("spuDto",spuDto);
			mv.setViewName("/m_order/estore");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;	
		}
	
	@SuppressWarnings({"unused" })
	@RequestMapping("/checkGoods")
	@ResponseBody
	public Map<String,Object> checkGoods(SportMShoppingCartDto car,HttpSession session){
		//获取当前登录用户
		String userId = this.getSessionUserId();
		//截取前台的spectId 和 specValueId
		String[] idStr = car.getIds().split(",");
		List<SportMSpecValuesDto> specValuesDtos = new ArrayList<SportMSpecValuesDto>(); 
		for (String ids : idStr) {
			String[] id = ids.split("\\*");
			SportMSpecValuesDto specValuesDto = new SportMSpecValuesDto();
			specValuesDto.setSpecId(id[0]);
			specValuesDto.setId(id[1]);
			specValuesDtos.add(specValuesDto);
		}	
		//查询商品基本信息
		SportMProductSpuDto spu = spuService.selectSpuById(car.getSpuId());
		//查询spu对应的sku  循环取出相对应规格的对象
		List<SportMProductSkuDto> lis = iSportMShppingCar.selectByProduct(car);
		//添加购物车 对象
		int i = specValuesDtos.size();
		for (SportMProductSkuDto sku : lis) {
			int j=0;
			for (SportMSpecValuesDto sportMSpecValuesDto : specValuesDtos) {
				//product对应specId[]的记录都存在
				SportMProductSpecDto pspec= iSportMShppingCar.selectBySpec(sportMSpecValuesDto.getSpecId(),sku.getId());
				if(pspec!=null){
						//product对应specValueId[]的记录都存在  库存存在 返回sku 
						SportMProductSpecValuesDto vspec = iSportMShppingCar.selectBySpecValue(sportMSpecValuesDto.getId(),sku.getId());
						if(vspec!=null){
							j++;							
						}
				}
			}
			if (i == j) {
				return MessageResp.getMessage(true, sku);			
			}			
		}
		return MessageResp.getMessage(false,"无此商品");
	}
	
	/**
	 * 新增购物车
	 * @param car
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/toMShppingCard")
	@ResponseBody
	public Map<String,Object> toShoppingCard(SportMShoppingCartDto car,HttpSession session){
		//获取当前登录用户
		String userId = this.getSessionUserId();
		String key = userId+"Card";
		//获取购物车集合  userId key
		Map<String,Object> map =  (Map<String, Object>) redisTemplate.opsForValue().get(key);
		if(map == null){
			map= new  HashMap<String, Object>();
		}
		//截取前台的spectId 和 specValueId
		String spec_Id="";
		String spec_value_id = "";
		if(StringUtils.isNotBlank(car.getIds())){
			String[] ids=car.getIds().split(","); 
			for (String str : ids) {
				String[] idsStr = str.split("\\*");
				spec_Id += idsStr[0]+",";
				spec_value_id += idsStr[1]+",";
			}
		}
		//查询商品基本信息
		SportMProductSpuDto spu = spuService.selectSpuById(car.getSpuId());
		//查询spu对应的sku  循环取出相对应规格的对象
		List<SportMProductSkuDto> lis = iSportMShppingCar.selectByProduct(car);
		//添加购物车 对象
		for (SportMProductSkuDto sku : lis) {
				//查询sku与specId[]的记录是否存在
				if(iSportMShppingCar.selectBySpec(spec_Id.split(","),sku.getId())){
						//product对应specValueId[]的记录都存在 
						if(iSportMShppingCar.selectBySpecValue(spec_value_id.split(","),sku.getId())){
							//默认sku图片  sku==null 用spu图片
							if(StringUtils.isNotBlank(sku.getThumbnail())){
								car.setImgUrl(sku.getThumbnail());
							}else{
								car.setImgUrl(spu.getThumbnail());
							}
							car.setProductName(spu.getProductName());
							car.setProductId(spu.getId());
							//pspec,vspec 都存在 证明sku数据存在
							car.setSkuId(sku.getId());
							car.setProductStock(sku.getProductStock());
						}
					continue;
				}
		}
		//购物车集合
		List<SportMShoppingCartDto> carList = new ArrayList<SportMShoppingCartDto>();
		//判断购物车 商品是否 存在
		Iterator entries = map.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry entry = (Map.Entry) entries.next();
			//判断商品是否存在  通过
			SportMShoppingCartDto shopping= (SportMShoppingCartDto) entry.getValue();
			//判断商品是否 存在   循环pid
			if(car.getIds().equals(shopping.getIds()) && car.getSkuId().equals(shopping.getSkuId()) ){
				car.setId(shopping.getId());
				SportMShoppingCartDto shop = (SportMShoppingCartDto) map.get(entry.getKey());
			/*	System.out.println("最大库存"+car.getProductStock());
				System.out.println("商品已经存在购物车中的数量"+shop.getNumberOf());
				System.out.println("商品重新订购的数量"+car.getNumberOf());*/
				if(articleNumber(car.getProductStock(),shop.getNumberOf()) < car.getNumberOf()){
					return MessageResp.getMessage(false,"库存不足,请重新选购");
				}
				car.setNumberOf(shop.getNumberOf()+car.getNumberOf());			//修改数量
				map.put(car.getId(), car);
				//库存量要大于当前条数
				redisTemplate.opsForValue().set(key, map);
				return MessageResp.getMessage(true,"添加成功");
			}
			carList.add(shopping);
		}
		if(car.getProductStock() < car.getNumberOf()){
			return MessageResp.getMessage(false,"库存不足,请重新选购");
		}
		car.setId(UUID.randomUUID()+"");
		map.put(car.getId(), car);
		redisTemplate.opsForValue().set(key, map);
        return MessageResp.getMessage(true,"添加成功");
	}
	/**
	 * 去购物车页面
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes"})
	@RequestMapping("/getShoppingCard")
	public ModelAndView getShoppingCard(){
		ModelAndView mv = this.getModelAndView();
		List<SportMShoppingCartDto> carList = new ArrayList<SportMShoppingCartDto>();
		String userId = this.getSessionUserId();
		String key = userId+"Card";
		//获取购物车数据
		Map<String,Object> map =  (Map<String, Object>) redisTemplate.opsForValue().get(key);
		if(map==null){
			mv.addObject("carList", carList);
			mv.setViewName("/m_order/mycart");
			return mv;
		}
		Iterator entries = map.entrySet().iterator();
		//遍历购物车map 转换list
		while (entries.hasNext()) {
			Map.Entry entry = (Map.Entry) entries.next();
			SportMShoppingCartDto shopping= (SportMShoppingCartDto) entry.getValue();
			carList.add(shopping);
		}
		try {
			mv.addObject("carList", carList);
			mv.setViewName("/m_order/mycart");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	/**
	 * 删除购物车信息
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	@RequestMapping("/delShoppingCar")
	@ResponseBody
	public Map<String,Object> delShoppingCar(String ids){
		String[] id = ids.split(",");
		String userId = this.getSessionUserId();
		if(userId==null){
			return MessageResp.getMessage(false,"请先登录~~");
		}
		String key = userId+"Card";
		Map<String,Object> map  = (Map<String, Object>) redisTemplate.opsForValue().get(key);
		try {
			for (String str : id) {
				map.remove(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return MessageResp.getMessage(false,"删除失败~~");
		}
		redisTemplate.opsForValue().set(key, map);
		return MessageResp.getMessage(true,"删除成功~~");
	}
	/**
	 * 随机获取4条商品记录
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/randomFour")
	public Response<List<SportMProductSpuDto>> getByRandomFour(SportMProductSpuDto spu){
		Response<List<SportMProductSpuDto>> result = new Response<List<SportMProductSpuDto>>();
		String key = "SportMProductSpuDto";
		List<SportMProductSpuDto> spuList = (List<SportMProductSpuDto>) redisTemplate.opsForValue().get(key);
		if(spuList.size()<1){
			spuList = spuService.selectByCriteria(spu);
			redisTemplate.opsForValue().set(key, spuList);
		}
		List<SportMProductSpuDto> newSpuList =   new ArrayList<SportMProductSpuDto>();
		//集合小于5直接返回
		if(spuList.size()<5){
			result.setData(spuList);
			result.setStatus(DataStatus.ENABLED);
			return result;
		}
		//在spu集合中随机拿四条记录  保证不重复
		int max=spuList.size();           //max小于4将出现死循环 
	    int min=0;
	    Random random = new Random();
	    int[] str = new int[4];
		int i=0;
		while (i<4) {
			boolean flag = true;
			int s = random.nextInt(max)%(max-min+1) + min;
			for (int st : str) {
				if(st == s){
					flag = false;
					break;
				}
			}
			if(!flag){
				continue;
			}
			str[i] = s;
			newSpuList.add(spuList.get(s));
			i++;
		}
		result.setData(newSpuList);
		result.setStatus(DataStatus.ENABLED);
		return result;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/cardAddOrRem")
	@ResponseBody
	public Map<String,Object> cardAddOrRem(SportMShoppingCartDto shopping){
		String userId = this.getSessionUserId();
		String key = userId+"Card";
		//获取购物车数据
		Map<String,Object> map =  (Map<String, Object>) redisTemplate.opsForValue().get(key);
		SportMShoppingCartDto oldshopp = (SportMShoppingCartDto) map.get(shopping.getId());
		//修改数量
		oldshopp.setNumberOf(shopping.getNumberOf());
		map.put(shopping.getId(), oldshopp);
		redisTemplate.opsForValue().set(key, map);
		return MessageResp.getMessage(true,"购物车保存成功~~");
	}
	
	/**
	 * 
	 * @param totalCount  最大库存
	 * @param number 购物车 已经存在的商品数量
	 * @return
	 */
	public Integer articleNumber(int totalCount , int number){
		return (totalCount - number);
	}

	
}
 