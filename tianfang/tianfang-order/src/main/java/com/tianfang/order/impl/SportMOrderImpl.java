package com.tianfang.order.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.order.constants.OrderStatus;
import com.tianfang.order.constants.PaymentStatus;
import com.tianfang.order.dao.SportMEvaluateDao;
import com.tianfang.order.dao.SportMOrderDao;
import com.tianfang.order.dao.SportMOrderInfoDao;
import com.tianfang.order.dao.SportMProductSkuDao;
import com.tianfang.order.dao.SportMProductSpuDao;
import com.tianfang.order.dto.SportMEvaluateDto;
import com.tianfang.order.dto.SportMOrderDto;
import com.tianfang.order.dto.SportMOrderInfoDto;
import com.tianfang.order.dto.SportMOrderToolsDto;
import com.tianfang.order.dto.SportMUserOrderDto;
import com.tianfang.order.pojo.SportMEvaluate;
import com.tianfang.order.pojo.SportMOrder;
import com.tianfang.order.pojo.SportMOrderInfo;
import com.tianfang.order.pojo.SportMProductSku;
import com.tianfang.order.pojo.SportMProductSpu;
import com.tianfang.order.service.ISportMOrderService;
import com.tianfang.user.dao.SportUserDao;
import com.tianfang.user.pojo.SportUser;

@Service
public class SportMOrderImpl implements ISportMOrderService{

	@Autowired
	private SportMOrderDao sportMOrderDao;
	
	@Autowired
	private SportMOrderInfoDao sportMOrderInfoDao;
	
	@Autowired
	private SportMProductSkuDao sportMProductSkuDao;
	
	@Autowired
	private SportMProductSpuDao sportMProductSpuDao;
	
	@Autowired
	private SportUserDao sportUserDao;
	
	@Autowired
	private SportMEvaluateDao SportMEvaluateDao;

	@Override
	public long save(SportMOrderDto sportMOrderDto) {
		SportMOrder order = BeanUtils.createBeanByTarget(sportMOrderDto, SportMOrder.class);
		if(StringUtils.isBlank(order.getId())){
			return sportMOrderDao.insert(order);
		}
		return sportMOrderDao.updateByPrimaryKey(order);
	}

	@Override
	public PageResult<SportMOrderDto> selectAllOrder(SportMOrderDto orderDto,PageQuery page) {
		List<SportMOrder> orderList= sportMOrderDao.selectPageAll(orderDto,page);
		List<SportMOrderDto> orderDtoList= BeanUtils.createBeanListByTarget(orderList, SportMOrderDto.class);
		if(page!=null){
			long total= sportMOrderDao.countPageAll(orderDto);
			page.setTotal(total);
			return new PageResult<SportMOrderDto>(page,orderDtoList);
		}
		return null;
	}

	@Override
	public List<SportMOrderDto> selectAll() {
		List<SportMOrder> orderLis = sportMOrderDao.selectAll();
		if(orderLis.size()>0){
			return BeanUtils.createBeanListByTarget(orderLis, SportMOrderDto.class);
		}
		return null;
	}

	@Override
	public PageResult<SportMOrderDto> selectOrder(SportMOrderDto orderDto,PageQuery page) {
		List<SportMOrderDto> lisOrder = sportMOrderDao.selectOrder(orderDto,page);
		if(page!=null){
			long total= sportMOrderDao.countOrder(orderDto);
			page.setTotal(total);
			return new PageResult<SportMOrderDto>(page,lisOrder);
		}
		return null;
	}
	
	
	public PageResult<SportMUserOrderDto> findOrderByUser(String userId,PageQuery page) {
		SportMOrderDto orderDto = new SportMOrderDto(); 
		orderDto.setUserId(userId);
		List<SportMOrder> sportMOrders = sportMOrderDao.selectPageAll(orderDto, page);
		List<SportMOrderDto> sportMOrderDtos = BeanUtils.createBeanListByTarget(sportMOrders, SportMOrderDto.class);
		long count = sportMOrderDao.countPageAll(orderDto);
		List<SportMUserOrderDto> sportMUserOrderDtos = BeanUtils.createBeanListByTarget(sportMOrderDtos, SportMUserOrderDto.class);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		for (SportMUserOrderDto sportMUserOrderDto : sportMUserOrderDtos) {
			sportMUserOrderDto.setCreateTimeStr(sdf.format(sportMUserOrderDto.getCreateTime()));
		}
		List<SportMOrderInfoDto> sportMOrderInfoDtos = sportMOrderDao.selectOrderInfo(userId);
		for (SportMUserOrderDto sportMUserOrderDto : sportMUserOrderDtos) {
			List<SportMOrderInfoDto> sportmOrderInfoDtos = new ArrayList<SportMOrderInfoDto>();
			for (SportMOrderInfoDto sportMOrderInfoDto: sportMOrderInfoDtos) {
				if (sportMUserOrderDto.getId().equals(sportMOrderInfoDto.getOrderId())) {
					SportMEvaluateDto sportMEvaluateDto = new SportMEvaluateDto();
					sportMEvaluateDto.setUserId(userId);
					sportMEvaluateDto.setProductOrderInfoId(sportMOrderInfoDto.getId());
					List<SportMEvaluate> sportMEvaluates = SportMEvaluateDao.getAllEvaluate(sportMEvaluateDto);
					if (sportMEvaluates.size()>0) {
						sportMOrderInfoDto.setEvaluateStat(DataStatus.ENABLED);
					}
					else {
						sportMOrderInfoDto.setEvaluateStat(DataStatus.DISABLED);
					}
					SportMProductSku sportMProductSku = sportMProductSkuDao.selectByPrimaryKey(sportMOrderInfoDto.getProductSkuId());
					sportMOrderInfoDto.setProductSpuId(sportMProductSku.getProductId());
					sportmOrderInfoDtos.add(sportMOrderInfoDto);
				}				
			}
			sportMUserOrderDto.setSportMOrderInfoDtos(sportmOrderInfoDtos);
		}
		page.setTotal(count);
		return new PageResult<>(page, sportMUserOrderDtos);
	}

	@Override
	public SportMOrderDto addOrder(SportMOrderToolsDto orderTools) {
		int stat  = 1;
		//数据库查询sku信息 统计总价
		String[] skuId = orderTools.getSkuId().split(",");
		String[] number = orderTools.getNumber().split(",");
		String[] prices = orderTools.getPrice().split(",");
		//前台选中商品总价
		Double toolsTotalPrices = 0d;
		for (int i = 0; i < number.length; i++) {
			toolsTotalPrices += Integer.valueOf(number[i])* Double.valueOf(prices[i]);
		}
		orderTools.setTotalPrices(toolsTotalPrices);
		//后台选中商品总价
		Double totalPrices = 0d;
		for (int i = 0; i < skuId.length; i++) {
			SportMProductSku m_sku = sportMProductSkuDao.selectByPrimaryKey(skuId[i]);
			totalPrices += Integer.valueOf(number[i]) * Double.valueOf(m_sku.getProductPrice()) ;
		}
		// 选中的商品  数据库中总价 与 前台总价相等    生成订单
		SportMOrder order = new SportMOrder();
		if(totalPrices.doubleValue() == orderTools.getTotalPrices().doubleValue()){
			try {
				//完善订单信息 新增订单
				//订单编号 生成三位随机数 拼接 时间戳
				order.setOrderNo((new Random().nextInt(900)+100)+""+System.currentTimeMillis());
				order.setOrderStatus(OrderStatus.WaitingForPay.getValue());
				order.setShippingAddressId(orderTools.getSId());
				order.setPaymentStatus(PaymentStatus.NoPaid.getValue());
				order.setTotalPrice(Double.valueOf(orderTools.getTotalPrices()));
				order.setUserId(orderTools.getUserId());
				order.setOrderTime(new Date());
				order.setStat(DataStatus.ENABLED);
				stat= sportMOrderDao.insert(order);
				if(stat<1){
					return null;
				}
				//完善订单详情信息 新怎订单详情
				for (int i = 0; i < skuId.length; i++) {
					SportMProductSku m_sku = sportMProductSkuDao.selectByPrimaryKey(skuId[i]);
					SportMProductSpu m_spu = sportMProductSpuDao.selectByPrimaryKey(m_sku.getProductId());
					SportMOrderInfo orderInfo = new SportMOrderInfo();
					orderInfo.setOrderInfoNo((new Random().nextInt(900)+100)+""+System.currentTimeMillis()+"");
					orderInfo.setOrderStatus(OrderStatus.WaitingForPay.getValue());
					orderInfo.setProductSkuId(skuId[i]);
					orderInfo.setNumber(Integer.valueOf(number[i]));
					orderInfo.setSkuName(m_spu.getProductName());
					orderInfo.setSkuProductPrice(m_sku.getProductPrice());
					orderInfo.setOrderId(order.getId());
					orderInfo.setProductStock(m_sku.getProductStock());
					orderInfo.setStat(DataStatus.ENABLED);
					stat = sportMOrderInfoDao.insert(orderInfo);
					if(stat<1){
						return null;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			return null; 
		}
		return BeanUtils.createBeanByTarget(order, SportMOrderDto.class);
	}
	
	/*public SportMOrderDto findOrderById(String orderId,String orderNo) {
		SportMOrderDto sportMOrderDto = sportMOrderDao.findOrderById(orderId, orderNo);
		if (StringUtils.isNotBlank(sportMOrderDto.getUserId())) {
			SportUser sportUser = sportUserDao.selectByPrimaryKey(sportMOrderDto.getUserId()); 
			if (StringUtils.isNotBlank(sportUser.getMobile())) {
				sportMOrderDto.setUname(sportUser.getMobile());
			} 
			if (StringUtils.isBlank(sportUser.getMobile()) && StringUtils.isNotBlank(sportUser.getEmail())) {
				sportMOrderDto.setUname(sportUser.getEmail());
			}
		}
		return sportMOrderDto;
	}*/
	
	public int saveOrderStatus(String orderId,Integer orderStatus) {
		SportMOrderDto sportMOrderDto = sportMOrderDao.findOrderById(orderId, null);
		SportMOrderInfoDto sportMOrderInfoDto = new SportMOrderInfoDto();
		sportMOrderInfoDto.setOrderId(sportMOrderDto.getId());
		List<SportMOrderInfo> sportMOrderInfos = sportMOrderInfoDao.selectOrderInfo(sportMOrderInfoDto, null);
		if (sportMOrderInfos.size()>0) {
			for (SportMOrderInfo sportMOrderInfo : sportMOrderInfos) {
				sportMOrderInfo.setOrderStatus(orderStatus);
				sportMOrderInfoDao.updateByPrimaryKeySelective(sportMOrderInfo);
			}
		}
		sportMOrderDto.setOrderStatus(orderStatus);
		SportMOrder sportMOrder = BeanUtils.createBeanByTarget(sportMOrderDto, SportMOrder.class);
		return sportMOrderDao.updateByPrimaryKeySelective(sportMOrder);
	}
	
	public SportMOrderDto findOrderById(String orderId,String orderNo) {
		SportMOrderDto sportMOrderDto = sportMOrderDao.findOrderById(orderId, orderNo);
		if (StringUtils.isNotBlank(sportMOrderDto.getUserId())) {
			SportUser sportUser = sportUserDao.selectByPrimaryKey(sportMOrderDto.getUserId()); 
			if (StringUtils.isNotBlank(sportUser.getMobile())) {
				sportMOrderDto.setUname(sportUser.getMobile());
			} 
			if (StringUtils.isBlank(sportUser.getMobile()) && StringUtils.isNotBlank(sportUser.getEmail())) {
				sportMOrderDto.setUname(sportUser.getEmail());
			}
		}
		SportMOrderInfoDto sportMOrderInfoDto = new SportMOrderInfoDto();
		sportMOrderInfoDto.setOrderId(sportMOrderDto.getId());
		List<SportMOrderInfo> sportMOrderInfos = sportMOrderInfoDao.selectOrderInfo(sportMOrderInfoDto, null);
		SportMProductSku sportMProductSku = sportMProductSkuDao.selectByPrimaryKey(sportMOrderInfos.get(0).getProductSkuId());
		SportMProductSpu sportMProductSpu = sportMProductSpuDao.selectByPrimaryKey(sportMProductSku.getProductId());
		if (null != sportMProductSpu && StringUtils.isNotBlank(sportMProductSpu.getProductName())) { 
			sportMOrderDto.setAname(sportMProductSpu.getProductName());
		}		
		return sportMOrderDto;
	}
	
	
	public void updateOrderById (String orderId,String orderNo,Integer orderStatus,Integer paymentStatus) {
		SportMOrderDto sportMOrderDto = sportMOrderDao.findOrderById(orderId, orderNo);
		if (null != orderStatus) {
			sportMOrderDto.setOrderStatus(orderStatus);
		}
		if (null != paymentStatus) {
			sportMOrderDto.setPaymentStatus(paymentStatus);
		}
		SportMOrder sportMOrder = BeanUtils.createBeanByTarget(sportMOrderDto, SportMOrder.class);
		sportMOrderDao.updateByPrimaryKeySelective(sportMOrder);
		SportMOrderInfoDto sportMOrderInfoDto = new SportMOrderInfoDto();
		sportMOrderInfoDto.setOrderId(sportMOrderDto.getId());
		List<SportMOrderInfo>  sportMOrderInfos = sportMOrderInfoDao.selectAll(sportMOrderInfoDto);
		if (sportMOrderInfos.size()>0) {
			for (SportMOrderInfo sportMOrderInfo : sportMOrderInfos) {
				sportMOrderInfo.setOrderStatus(orderStatus);
				sportMOrderInfoDao.updateByPrimaryKeySelective(sportMOrderInfo);
			}
		}
	}
	
	public Integer orderDelete(String orderId) {
		SportMOrderDto sportMOrderDto = sportMOrderDao.findOrderById(orderId, null);
		if (null != sportMOrderDto) {
			sportMOrderDto.setStat(DataStatus.DISABLED);
			SportMOrder sportMOrder = BeanUtils.createBeanByTarget(sportMOrderDto, SportMOrder.class);
			sportMOrderDao.updateByPrimaryKeySelective(sportMOrder);
			SportMOrderInfoDto sportMOrderInfoDto = new SportMOrderInfoDto();
			sportMOrderInfoDto.setOrderId(orderId);
			List<SportMOrderInfo> sportMOrderInfos = sportMOrderInfoDao.selectAll(sportMOrderInfoDto);
			if (sportMOrderInfos.size()>0) {
				for (SportMOrderInfo sportMOrderInfo :sportMOrderInfos) {
					sportMOrderInfo.setStat(DataStatus.DISABLED);
					sportMOrderInfoDao.updateByPrimaryKeySelective(sportMOrderInfo);
				}
			}
			return 1;
		}
		return 0;
	}
}
