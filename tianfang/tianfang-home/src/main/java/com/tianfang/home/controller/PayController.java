package com.tianfang.home.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianfang.common.alipay.config.AlipayConfig;
import com.tianfang.common.alipay.util.AlipaySubmit;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.util.PropertiesUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.home.dto.PayDto;
import com.tianfang.home.util.SessionUtil;
import com.tianfang.order.dto.SportMOrderDto;
import com.tianfang.order.dto.SportMOrderInfoDto;
import com.tianfang.order.service.ISportMOrderInfoService;
import com.tianfang.order.service.ISportMOrderService;
import com.tianfang.user.dto.LoginUserDto;


/**
 * 
 * 此类描述的是：到达支付页面
 * 
 * @author: cwftalus@163.com
 * @version: 2015年4月21日 下午1:37:19
 */
@Controller
@RequestMapping(value = "/pay") 
public class PayController {
	
	@Autowired
	private ISportMOrderService iSportMOrderService; 
	
	@Autowired
	private ISportMOrderInfoService iSportMOrderInfoService;
	
	/**
	 * 进行支付处理
	 */
	@RequestMapping(value = "/index")
	@ResponseBody
	public String index(PayDto payDto,HttpServletResponse response,HttpSession session) {
		LoginUserDto loginUserDto = SessionUtil.getLoginSession(session);
		// 只允许登录用户进行操作
		if (loginUserDto == null) {
			return String.valueOf(DataStatus.DISABLED);
		}
		Integer result = iSportMOrderService.getOrderStatus(payDto.getOut_trade_no());
		if (null != result && result == -4) {
			return String.valueOf(result);
		}
		// //////////////////////////////////请求参数//////////////////////////////////////
		// 支付类型
		String payment_type = "1";
		// 必填，不能修改
		// 服务器异步通知页面路径
		//String notify_url = "http://商户网关地址/create_direct_pay_by_user-JAVA-UTF-8/notify_url.jsp";
		String notify_url = PropertiesUtils.getProperty("notify_url");//"http://www.aimingpin.cn/notifyUrl.do";
		// 需http://格式的完整路径，不能加?id=123这类自定义参数
		// 页面跳转同步通知页面路径
		//String return_url = "http://商户网关地址/create_direct_pay_by_user-JAVA-UTF-8/return_url.jsp";
		String return_url = PropertiesUtils.getProperty("notify_url");//"http://www.aimingpin.cn/returnUrl.do";
		// 需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/
		// 商户订单号
		String out_trade_no = new String(payDto.getOut_trade_no());
		
		SportMOrderDto orderDto = iSportMOrderService.findOrderById(null, out_trade_no);
		List<SportMOrderInfoDto> lis_oInfo = iSportMOrderInfoService.findOrderInfoById(orderDto.getId());
		Double totalPrice = 0d;
		//计算订单的总价
		if(lis_oInfo !=null && lis_oInfo.size()>0){
			for (SportMOrderInfoDto oInfo : lis_oInfo) {
				totalPrice += Double.valueOf(oInfo.getNumber()) * Double.valueOf(oInfo.getSkuProductPrice());
			}
		}
		//计算后的总价与页面总价对比
		if(totalPrice != Double.valueOf(payDto.getTotal_fee()).doubleValue()){
			totalPrice=null;
		}
		// 商户网站订单系统中唯一订单号，必填
		// 订单名称
		String subject = new String(orderDto.getAname());
		// 必填
		// 付款金额
		String total_fee = String.valueOf(totalPrice);
		// 必填
		// 订单描述
		String body = new String("");
		// 商品展示地址
		String show_url = new String("");
		// 需以http://开头的完整路径，例如：http://www.商户网址.com/myorder.html
		// 防钓鱼时间戳
		String anti_phishing_key = "";
		// 若要使用请调用类文件submit中的query_timestamp函数
		// 客户端的IP地址
		String exter_invoke_ip = "";
		// 非局域网的外网IP地址，如：221.0.0.1
		// 把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "create_direct_pay_by_user");
		sParaTemp.put("partner", AlipayConfig.partner);
		sParaTemp.put("seller_email", AlipayConfig.seller_email);
		sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("payment_type", payment_type);
		sParaTemp.put("notify_url", notify_url);
		sParaTemp.put("return_url", return_url);
		sParaTemp.put("out_trade_no", out_trade_no);
		sParaTemp.put("subject", subject);
		sParaTemp.put("total_fee", total_fee);
		sParaTemp.put("body", body);
		sParaTemp.put("show_url", show_url);
		sParaTemp.put("anti_phishing_key", anti_phishing_key);
		sParaTemp.put("exter_invoke_ip", exter_invoke_ip);

		if (StringUtils.isEmpty(total_fee) || total_fee.equals("0")) {
			return String.valueOf(DataStatus.ENABLED);//1 直接进入支付成功页面
		}
		
//		//物流费用
//		String logistics_fee = "0.00";
//		//必填，即运费
//		//物流类型
//		String logistics_type = "EXPRESS";
//		//必填，三个值可选：EXPRESS（快递）、POST（平邮）、EMS（EMS）
//		//物流支付方式
//		String logistics_payment = "SELLER_PAY";
//		//必填，两个值可选：SELLER_PAY（卖家承担运费）、BUYER_PAY（买家承担运费）
//		sParaTemp.put("service", "create_partner_trade_by_buyer");
//        sParaTemp.put("partner", AlipayConfig.partner);
//        sParaTemp.put("seller_email", AlipayConfig.seller_email);
//        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
//		sParaTemp.put("payment_type", payment_type);
//		sParaTemp.put("notify_url", notify_url);
//		sParaTemp.put("return_url", return_url);
//		sParaTemp.put("out_trade_no", out_trade_no);
//		sParaTemp.put("subject", subject);
//		sParaTemp.put("price", total_fee);
//		sParaTemp.put("quantity", "1");
//		sParaTemp.put("logistics_fee", logistics_fee);
//		sParaTemp.put("logistics_type", logistics_type);
//		sParaTemp.put("logistics_payment", logistics_payment);
//		sParaTemp.put("body", body);
//		sParaTemp.put("show_url", show_url);
//		sParaTemp.put("receive_name", "");
//		sParaTemp.put("receive_address", "");
//		sParaTemp.put("receive_zip", "");
//		sParaTemp.put("receive_phone", "");
//		sParaTemp.put("receive_mobile", "");
		
		// 建立请求
		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
		return sHtmlText;
	}
}
