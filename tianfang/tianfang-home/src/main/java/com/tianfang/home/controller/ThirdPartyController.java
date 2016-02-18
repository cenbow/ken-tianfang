package com.tianfang.home.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.constants.SessionConstants;
import com.tianfang.common.model.Response;
import com.tianfang.common.util.GetPostUtil;
import com.tianfang.common.util.JsonUtil;
import com.tianfang.common.util.StringUtils;
import com.tianfang.home.dto.QrcodeDto;
import com.tianfang.home.util.SessionUtil;
import com.tianfang.user.dto.LoginUserDto;
import com.tianfang.user.dto.SportUserReqDto;
import com.tianfang.user.service.IThirdPartyService;
import com.tianfang.user.service.IUserService;

/**
 * @author YIn
 * @time:2015年11月21日 下午5:52:29
 * @ClassName: ThirdPartyController
 * @
 */
@Controller
@RequestMapping(value = "/third")
public class ThirdPartyController extends BaseController{

	@Autowired
	IThirdPartyService iThirdPartyService;

	@Autowired
	private IUserService iUserService;

	/**
	 * QQ绑定
	 * 
	 * @param openid
	 */
	@RequestMapping("/qqBinding")
	@ResponseBody
	public  Response<SportUserReqDto> qqBinding(String openid,
			HttpSession session) {

		Response<SportUserReqDto> response = new Response<SportUserReqDto>();
		String userAccountId = null;
		if (SessionUtil.getLoginSession(session) == null) {
			// return JsonUtil.getJsonStr(new RequestResult(true,
			// "用户没有登陆或登陆失效！"));
			response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("用户没有登陆或登陆失效！");
			return response;

		}

		userAccountId = SessionUtil.getLoginSession(session).getId();
		// 通过openid获取用户信息
		SportUserReqDto sportUserReqDto = iThirdPartyService
				.selectOpenidByQQ(openid);
		if (sportUserReqDto != null) {
			response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("此QQ已经绑定");
		} else {
			sportUserReqDto = iUserService
					.selectUserAccountByUserId(userAccountId);
			sportUserReqDto.setQq(openid);
			int flag = iUserService.updateUserAccount(sportUserReqDto);
			if (flag > 0) {
				response.setStatus(DataStatus.HTTP_SUCCESS);
				response.setMessage("绑定成功");
			} else {
				response.setStatus(DataStatus.HTTP_FAILE);
				response.setMessage("绑定失败");
			}
		}

		return response;

	}

	/**
	 * 微信绑定
	 * 
	 * @param openid
	 * @param session
	 * @return
	 */
	@RequestMapping("/weixinBinding")
	@ResponseBody
	public  Response<SportUserReqDto> weixinBinding(String code,
			HttpSession session) {
		// 定义访问网站的路径,通过code获取微信的唯一标识
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx3b1d31427f9d2518&secret=f02987db881ac0cfb5fca930eb22d13a&code="
				+ code + "&grant_type=authorization_code";
		// 发送请求,获取微信服务器返回的数据
		String json = GetPostUtil.weiLink(url);

		// 进行数据封装
		QrcodeDto qrcodeDto = JsonUtil.getObjFromJson(json, QrcodeDto.class);

		// 获取微信的openid
		String openid = qrcodeDto.getOpenid();
		
		//获取微信的UnionID,多平台时用户的唯一标识
		//String unionid = qrcodeDto.getUnionid();

		Response<SportUserReqDto> response = new Response<SportUserReqDto>();
		String userAccountId = null;
		if (SessionUtil.getLoginSession(session) == null) {
			response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("用户没有登陆或登陆失效！");
			return response;

		}

		userAccountId = SessionUtil.getLoginSession(session).getId();
		// 通过openid获取用户信息
		SportUserReqDto sportUserReqDto = iThirdPartyService
				.selectOpenidByWeixin(openid);
		if (sportUserReqDto != null) {
			response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("此微信已经绑定");
		} else {
			sportUserReqDto = iUserService
					.selectUserAccountByUserId(userAccountId);
			sportUserReqDto.setWeixin(openid);
			int updateUserAccount = iUserService
					.updateUserAccount(sportUserReqDto);
			if (updateUserAccount > 0) {
				response.setStatus(DataStatus.HTTP_SUCCESS);
				response.setMessage("绑定成功");
			} else {
				response.setStatus(DataStatus.HTTP_FAILE);
				response.setMessage("绑定失败");
			}
		}

		return response;

	}

	/**
	 * 新浪绑定
	 * 
	 * @param openid
	 */
	@RequestMapping("/sinaBinding")
	@ResponseBody
	public  Response<SportUserReqDto> sinaBinding(String openid,
			HttpSession session) {
		Response<SportUserReqDto> response = new Response<SportUserReqDto>();
		String userAccountId = null;
		if (SessionUtil.getLoginSession(session) == null) {
			// return JsonUtil.getJsonStr(new RequestResult(true,
			// "用户没有登陆或登陆失效！"));
			response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("用户没有登陆或登陆失效！");
			return response;

		}

		userAccountId = SessionUtil.getLoginSession(session).getId();

		// 通过openid获取用户信息
		SportUserReqDto sportUserReqDto = iThirdPartyService
				.selectOpenidBySina(openid);
		if (sportUserReqDto != null) {
			response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("此新浪账号已经绑定");
		} else {
			sportUserReqDto = iUserService
					.selectUserAccountByUserId(userAccountId);
			sportUserReqDto.setSina(openid);
			int updateUserAccount = iUserService
					.updateUserAccount(sportUserReqDto);
			if (updateUserAccount > 0) {
				response.setStatus(DataStatus.HTTP_SUCCESS);
				response.setMessage("绑定成功");
			} else {
				response.setStatus(DataStatus.HTTP_FAILE);
				response.setMessage("绑定失败");
			}
		}

		return response;
	}

	/**
	 * QQ登录
	 * @param openid
	 */
	@RequestMapping("/qqLogin")
	@ResponseBody
	public  Response<LoginUserDto> qqLogin(String openid,
			HttpSession session) {
		System.out.println(openid);
		// 获取用户信息
		SportUserReqDto sportUserReqDto = iThirdPartyService
				.selectUserAccountByQQ(openid);
		Response<LoginUserDto> response = new Response<LoginUserDto>();

		// 获取登陆用户信息
		LoginUserDto loginUserDto = new LoginUserDto();
		loginUserDto.setId(sportUserReqDto.getId());
		loginUserDto.setType(DataStatus.USERTYPE);  

		// 将用户信息放置到session中
		session.removeAttribute(SessionConstants.LOGIN_USER_INFO);
		session.setAttribute(SessionConstants.LOGIN_USER_INFO, loginUserDto);
		response.setStatus(DataStatus.HTTP_SUCCESS);
		response.setMessage("200");
		response.setData(loginUserDto);
		return response;
	}

	/**
	 * 微信登录
	 * @param openid
	 * @param session
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/weixinLogin")
	public ModelAndView weixinLogin(HttpServletRequest request,HttpServletResponse response,String code,
			HttpSession session) throws IOException {		
		// 定义访问网站的路径,通过code获取微信的唯一标识
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx3b1d31427f9d2518&secret=f02987db881ac0cfb5fca930eb22d13a&code="
				+ code + "&grant_type=authorization_code";
		// 发送请求,获取微信服务器返回的数据
		String json = GetPostUtil.weiLink(url);

		// 进行数据封装
		QrcodeDto qrcodeDto = JsonUtil.getObjFromJson(json, QrcodeDto.class);

		// 获取微信的openid
		String openid = qrcodeDto.getOpenid();
		System.out.println(openid);
		if(!StringUtils.isEmpty(openid)){
			// 获取用户信息
			SportUserReqDto sportUserReqDto = iThirdPartyService.selectUserAccountByWeiXin(openid);
//			Response<LoginUserDto> response = new Response<LoginUserDto>();

			// 获取登陆用户信息
			LoginUserDto loginUserDto = new LoginUserDto();
			loginUserDto.setId(sportUserReqDto.getId());
			loginUserDto.setType(DataStatus.USERTYPE);

			// 将用户信息放置到session中
			session.removeAttribute(SessionConstants.LOGIN_USER_INFO);
			session.setAttribute(SessionConstants.LOGIN_USER_INFO, loginUserDto);
			return new ModelAndView("redirect:/userMan/userInfo.htm");
//			response.sendRedirect(PropertiesUtils.getProperty("wwwdomain") + "/userMan/userInfo.htm");
		}else{
			return new ModelAndView("redirect:/index.htm");
//			response.sendRedirect(PropertiesUtils.getProperty("wwwdomain") + "/index.htm");
		}
		
//		return "";
		
//		response.setStatus(DataStatus.HTTP_SUCCESS);
//		response.setMessage("200");
//		response.setData(loginUserDto);
//		return response;
	}

	/**
	 * 新浪登录
	 * 
	 * @param openid
	 */
	@RequestMapping("/sinaLogin")
	@ResponseBody
	public  Response<LoginUserDto> sinaLogin(String openid,
			HttpSession session) {
		System.out.println(openid);
		// 获取用户信息
		SportUserReqDto sportUserReqDto = iThirdPartyService.selectUserAccountBySina(openid);
		Response<LoginUserDto> response = new Response<LoginUserDto>();

		// 获取登陆用户信息
		LoginUserDto loginUserDto = new LoginUserDto();
		loginUserDto.setId(sportUserReqDto.getId());
		loginUserDto.setType(DataStatus.USERTYPE);
		// 将用户信息放置到session中
		session.removeAttribute(SessionConstants.LOGIN_USER_INFO);
		session.setAttribute(SessionConstants.LOGIN_USER_INFO, loginUserDto);
		response.setStatus(DataStatus.HTTP_SUCCESS);
		response.setData(loginUserDto);
		return response;
	}

}
