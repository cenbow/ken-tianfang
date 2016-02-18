package com.tianfang.admin.controller.sport;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.constants.SessionConstants;
import com.tianfang.common.model.Response;
import com.tianfang.common.util.StringUtils;
import com.tianfang.user.service.ISmsSendService;


/**
 * 此类描述的是：手机短信controller
 * @author: jam_yin
 * @version: 2015年3月31日 上午09:46
 */

@Controller
@RequestMapping(value = "/SMS")
public class SMSController {
	/**
	 * 此方法描述的是：短信发送方法
	 * @author: jam_yin
	 * @version: 2015年3月31日 上午09:46
	 */
	protected static final Log logger = LogFactory.getLog(SMSController.class);

	@Autowired
	private ISmsSendService iSmsSendService;
//	@Autowired
//	private IUserService iUserService;
	
	@Autowired
	private RedisTemplate<String, Integer> redisTemplate;

	@RequestMapping(value = "/send.do")
	@ResponseBody
	public Response<String> send(String mobilePhone, String editPhone,HttpSession session,HttpServletRequest request) {
		Response<String> result = new Response<String>();
		
//		LoginUserDto loginUserDto = SessionUtil.getLoginSession(session);
//		if (loginUserDto == null) {
//			result.setStatus(DataStatus.HTTP_FAILE);
//			result.setMessage("用户未登录");
//			return result;
//		}
//		
//		if (editPhone!=null) {
//			String userAccountId = SessionUtil.getLoginSession(session).getId();
//			UsersDto oldusersDto = iUserService
//					.selectUsersByUserAccountId(userAccountId);
//			mobilePhone=oldusersDto.getMobileNo();
//		}
		
		int randomNumber = (int) (Math.random() * 9000 + 1000);
		String content = "温馨提示，为了保护您的隐私，请您在90秒内输入" + randomNumber + "验证码。";// 短信内容
		String returnString = iSmsSendService.sendSms(randomNumber, mobilePhone, content);
//		String keyCode = SessionConstants.PHONE_NUMBER+loginUserDto.getId();
		String keyCode = SessionConstants.PHONE_NUMBER;
		redisTemplate.opsForValue().set(keyCode, randomNumber, 90, TimeUnit.SECONDS);
		System.out.println("mobilePhone:"+mobilePhone);
		System.out.println("keyCode:"+keyCode);
		System.out.println("randomNumber:"+randomNumber);
		
//		session.setAttribute(SessionConstants.SMS_VALIDAT_NUMBER, "");
//		session.setAttribute(SessionConstants.SMS_VALIDAT_NUMBER, randomNumber);
//		session.setAttribute(SessionConstants.PHONE_NUMBER, mobilePhone);
//		session.setMaxInactiveInterval(90); // 设置session有效期为90秒
		//return JsonUtil.getJsonStr(new RequestResult(true, returnString + " " + randomNumber));
		result.setStatus(DataStatus.HTTP_SUCCESS);
//		result.setMessage(returnString + " " + randomNumber);
		return result;
	}
	/**
	 * 注册发送验证码
	 * BY YIn 20150702
	 * */
	@RequestMapping(value = "/regMobileVal.do")
	@ResponseBody
	public Response<String> regMobileVal(String mobilePhone,HttpServletRequest request) {
		Response<String> result = new Response<String>();
		int randomNumber = (int) (Math.random() * 9000 + 1000);
		String content = "温馨提示，为了保护您的隐私，请您在90秒内输入" + randomNumber + "验证码。";// 短信内容
		String returnString = iSmsSendService.sendSms(randomNumber, mobilePhone, content);
		String keyCode = "reg"+mobilePhone ;
		redisTemplate.opsForValue().set(keyCode, randomNumber, 90, TimeUnit.SECONDS);;
		result.setStatus(DataStatus.HTTP_SUCCESS);
		return result;
	}

	@RequestMapping(value = "/validate.do")
	@ResponseBody
	public Response<String> validate(HttpSession session, String validateCode,String mobilePhone) {
		logger.debug("validateCode =" + validateCode);
		Response<String> result = new Response<String>();
		
//		LoginUserDto loginUserDto = SessionUtil.getLoginSession(session);
//		if (loginUserDto == null) {
//			result.setStatus(DataStatus.HTTP_FAILE);
//			result.setMessage("用户未登录");
//			return result;
//		}
		
		if(validateCode == null || validateCode.equals("")){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("验证码为空！");
			return result;
		}
//		if (SessionUtil.getLoginSession(session) == null) {
//			result.setStatus(DataStatus.HTTP_SUCCESS);
//			result.setMessage("用户未登录！");
//			return result;
//		}
		if (StringUtils.isEmpty(mobilePhone)) {
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("手机为空！");
			return result;
		}
		
//		String keyCode = SessionConstants.PHONE_NUMBER+loginUserDto.getId();
		String keyCode = SessionConstants.PHONE_NUMBER;
		if(redisTemplate.opsForValue().get(keyCode)==null || redisTemplate.opsForValue().get(keyCode).equals("")){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("验证码失效！");
			return result;
		}
		
		String checkCode = redisTemplate.opsForValue().get(keyCode).toString();
		if (validateCode.equals(checkCode)) {
//			//return JsonUtil.getJsonStr(new RequestResult(true, "验证码输入正确！"));	
//			String userAccountId = loginUserDto.getId();	
//			UsersDto usersDto = iUserService.selectUsersByUserAccountId(userAccountId);
//			usersDto.setMobileNo(mobilePhone);
//			usersDto.setUserAccountId(userAccountId);
//			int flag = iUserService.updateUsers(usersDto);
//			if(flag > 0){
				result.setStatus(DataStatus.HTTP_SUCCESS);
				result.setMessage("手机验证成功！");
//			}else{
//				result.setStatus(DataStatus.HTTP_FAILE);
//				result.setMessage("手机验证失败！");	
//			}
			return result;
		} else {
			//return JsonUtil.getJsonStr(new RequestResult(true, "验证码输入错误！"));
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("验证码错误！");
			return result;
		}

	}
//	
//	@RequestMapping(value = "/validatePhone.do")
//	@ResponseBody
//	public Response<String> validatePhone(HttpSession session, String validateCode) {
//		logger.debug("validateCode =" + validateCode);
//		Response<String> result = new Response<String>();
//		if(validateCode == null || validateCode.equals("")){
//			result.setStatus(DataStatus.HTTP_FAILE);
//			result.setMessage("验证码为空！");
//			return result;
//		}
////		if (SessionUtil.getLoginSession(session) == null) {
////			result.setStatus(DataStatus.HTTP_SUCCESS);
////			result.setMessage("用户未登录！");
////			return result;
////		}
//		if (session.getAttribute(SessionConstants.PHONE_NUMBER) == null) {
//			result.setStatus(DataStatus.HTTP_FAILE);
//			result.setMessage("手机为空！");
//			return result;
//		}
//		else if (validateCode.equals(session.getAttribute(SessionConstants.SMS_VALIDAT_NUMBER).toString())) {
//			//return JsonUtil.getJsonStr(new RequestResult(true, "验证码输入正确！"));
//			String phoneNum = session.getAttribute(SessionConstants.PHONE_NUMBER).toString();		
//			String	userAccountId = SessionUtil.getLoginSession(session).getId();	
//			UsersDto usersDto = iUserService.selectUsersByUserAccountId(userAccountId);
//			usersDto.setMobileNo(phoneNum);
//			usersDto.setUserAccountId(userAccountId);
//			int flag = iUserService.updateUsers(usersDto);
//			if(flag > 0){
//				result.setStatus(DataStatus.HTTP_SUCCESS);
//				result.setMessage("手机验证成功！");
//			}else{
//				result.setStatus(DataStatus.HTTP_FAILE);
//				result.setMessage("手机验证失败！");	
//			}
//			return result;
//		} else {
//			//return JsonUtil.getJsonStr(new RequestResult(true, "验证码输入错误！"));
//			result.setStatus(DataStatus.HTTP_FAILE);
//			result.setMessage("验证码错误！");
//			return result;
//		}
//
//	}
}
