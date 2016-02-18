package com.tianfang.home.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tianfang.common.alipay.util.AlipayNotify;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.order.dto.SportMOrderDto;
import com.tianfang.order.service.ISportMOrderInfoService;
import com.tianfang.order.service.ISportMOrderService;
import com.tianfang.user.service.ISmsSendService;

@Controller
@RequestMapping(value = "/api")
public class AlipayController extends BaseController{

	protected static final Log logger = LogFactory.getLog(AlipayController.class);

	@Autowired
	private ISportMOrderService iSportMOrderService;

	@Autowired
	private ISportMOrderInfoService iSportMOrderInfoService;
	
	@Autowired
	private ISmsSendService iSmsSendService;

/*	@RequestMapping(value = "/success.do")
	public String success(HttpServletRequest request, HttpSession session) throws UnsupportedEncodingException {
		// 商户订单号
		String orderId = new String(request.getParameter("orderId").getBytes("ISO-8859-1"), "UTF-8");
		List<OrderItemsDto> itemDtoList = new ArrayList<OrderItemsDto>();
		OrderDto orderDto = iOrderService.findByOrderId(orderId, null);
		itemDtoList = updateOrderStatus("free",orderDto.getOrderNo());
		request.setAttribute("itemDtoList", itemDtoList);
		request.setAttribute("orderDto", orderDto);
		return "/pay/success";
	}
*/	
	
	@RequestMapping(value = "/notifyUrl")
	public String notifyUrl(HttpServletRequest request, HttpSession session) throws UnsupportedEncodingException {
		// 获取支付宝POST过来反馈信息
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			// valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		// 商户订单号
		String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
		// 支付宝交易号
		String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
		// 交易状态
		String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");

		logger.debug("out_trade_no"+out_trade_no);
		logger.debug("trade_no"+trade_no);
		logger.debug("trade_status"+trade_status);

		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
//		List<SportMOrderInfoDto> itemDtoList = new ArrayList<SportMOrderInfoDto>();
		if (AlipayNotify.verify(params)) {// 验证成功
			if(trade_status.equals("TRADE_FINISHED")){
				//判断该笔订单是否在商户网站中已经做过处理
					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					//如果有做过处理，不执行商户的业务程序
					
				//注意：
				//退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
			} else if (trade_status.equals("TRADE_SUCCESS")){
				//判断该笔订单是否在商户网站中已经做过处理
					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					//如果有做过处理，不执行商户的业务程序
				iSportMOrderService.updateOrderById(null, trade_no, DataStatus.ENABLED, DataStatus.ENABLED);
//				itemDtoList = updateOrderStatus("pay",out_trade_no);
				//注意：
				//付款完成后，支付宝系统发送该交易状态通知
			}
		}
		SportMOrderDto orderDto = iSportMOrderService.findOrderById(null, out_trade_no);
//		request.setAttribute("itemDtoList", itemDtoList);
		request.setAttribute("orderDto", orderDto);
		return "/m_order/pay_success";
	}
	
	
	@RequestMapping(value = "/returnUrl")
	public String returnUrl(HttpServletRequest request, HttpSession session) throws UnsupportedEncodingException {
		//获取支付宝GET过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		//商户订单号
		String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
		//支付宝交易号
		String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
		//交易状态
		String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
		//计算得出通知验证结果
//		List<OrderItemsDto> itemDtoList = new ArrayList<OrderItemsDto>();
		boolean verify_result = AlipayNotify.verify(params);
		if(verify_result){//验证成功
			//////////////////////////////////////////////////////////////////////////////////////////
			//请在这里加上商户的业务逻辑程序代码
			//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
			if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
				iSportMOrderService.updateOrderById(null, trade_no, DataStatus.ENABLED, DataStatus.ENABLED);
				//判断该笔订单是否在商户网站中已经做过处理
					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					//如果有做过处理，不执行商户的业务程序
			}
		}else{
			//该页面可做页面美工编辑
//			out.println("验证失败");
		}
		SportMOrderDto orderDto = iSportMOrderService.findOrderById(null, out_trade_no);
//		request.setAttribute("itemDtoList", itemDtoList);
		request.setAttribute("orderDto", orderDto);
		return "/m_order/pay_success";
	}
	
//	public static void main(String[] args) {
//		String ddd = "Oddddddd";
//		System.out.println(ddd.substring(0,1));
//		System.out.println(ddd.substring(1));
//	}
}
