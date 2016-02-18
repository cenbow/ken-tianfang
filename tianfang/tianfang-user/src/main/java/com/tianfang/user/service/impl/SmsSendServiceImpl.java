package com.tianfang.user.service.impl;

import org.springframework.stereotype.Service;

import com.bcloud.msg.http.HttpSender;
import com.tianfang.common.model.RequestResult;
import com.tianfang.common.util.JsonUtil;
import com.tianfang.common.util.PropertiesUtils;
import com.tianfang.user.service.ISmsSendService;

@Service
public class SmsSendServiceImpl implements ISmsSendService {

	@Override
	public String sendSms(int randomNumber, String mobilePhone, String content) {
		String uri = PropertiesUtils.getProperty("msg.url");// 应用地址
		String account = PropertiesUtils.getProperty("msg.account");// 账号
		String pswd = PropertiesUtils.getProperty("msg.password");// 密码
		String product = PropertiesUtils.getProperty("msg.product");// "132903592";//
																	// 产品ID
		String extno = PropertiesUtils.getProperty("msg.extno");// "08";// 扩展码

		boolean needstatus = true;// 是否需要状态报告，需要true，不需要false

		String returnString = "";
		try {
			returnString = HttpSender.batchSend(uri, account, pswd, mobilePhone,
					content, needstatus, product, extno);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonUtil.getJsonStr(new RequestResult(true, returnString + " "
				+ randomNumber));

		// String mobiles = "13800210021,13800138000";//手机号码，多个号码使用","分割
		// String mobiles =request.getParameter(mobilephone) ;

		// int randomNumber = (int) (Math.random() * 9000 + 1000);
		// logger.debug("randomNumber =" + randomNumber);
		// String content = "【聚运动】温馨提示，为了保护您的隐私，请您在90秒内输入" + randomNumber +
		// "验证码。";// 短信内容

		// String product = "132903592";// 产品ID
		// String extno = "08";// 扩展码
		// session.setAttribute(SessionConstants.VALIDAT_NUMBER, "");
		// session.setAttribute(SessionConstants.VALIDAT_NUMBER, randomNumber);
		// session.setMaxInactiveInterval(90); // 设置session有效期为90秒
	}

}
